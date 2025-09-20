package com.phanduy.aliexscrap.controller;

import javafx.application.Platform;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Quản lý WebSocket connection và auto reconnect - Singleton pattern
 */
public class SocketManager {
    private static volatile SocketManager instance;
    private static final Object lock = new Object();
    private static final int RECONNECT_INTERVAL_SECONDS = 2;
    private static final int MAX_RECONNECT_ATTEMPTS = -1; // -1 = unlimited
    
    private final String socketUrl;
    private final List<SocketCallback> callbacks = new ArrayList<>();
    private final AtomicBoolean isReconnecting = new AtomicBoolean(false);
    private final AtomicBoolean shouldReconnect = new AtomicBoolean(false);
    private final AtomicBoolean isConnected = new AtomicBoolean(false);
    private final AtomicInteger reconnectAttempts = new AtomicInteger(0);
    
    private ScheduledExecutorService reconnectScheduler;
    private WebSocketClient client;
    private NetworkMonitor networkMonitor;
    
    public interface SocketCallback {
        void onConnectionEstablished();
        void onConnectionLost();
        void onReconnectAttempt(int attemptNumber);
        void onReconnectFailed(String reason);
        void onMessage(String message);
        void onError(Exception ex);
    }
    
    private SocketManager(String socketUrl) {
        this.socketUrl = socketUrl;
        initNetworkMonitor();
    }
    
    /**
     * Khởi tạo NetworkMonitor
     */
    private void initNetworkMonitor() {
        networkMonitor = NetworkMonitor.getInstance();
        networkMonitor.startMonitoring(new NetworkMonitor.NetworkCallback() {
            @Override
            public void onNetworkLost() {
                System.out.println("Network lost detected, closing socket immediately");
                // Đóng socket ngay lập tức khi mất mạng
                disconnect();
            }
            
            @Override
            public void onNetworkRestored() {
                System.out.println("Network restored, reinitializing connection");
                // Khởi tạo lại kết nối khi có mạng trở lại
                if (shouldReconnect.get()) {
                    try {
                        connect();
                        startAutoReconnect();
                    } catch (Exception e) {
                        System.err.println("Error reinitializing connection: " + e.getMessage());
                    }
                }
            }
        });
    }
    
    /**
     * Lấy instance của SocketManager (Singleton)
     */
    public static SocketManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new SocketManager("ws://localhost:8080/websocket");
                }
            }
        }
        return instance;
    }
    
    /**
     * Lấy instance với custom URL
     */
    public static SocketManager getInstance(String socketUrl) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new SocketManager(socketUrl);
                }
            }
        }
        return instance;
    }
    
    /**
     * Đăng ký callback để nhận thông báo từ socket
     */
    public void registerCallback(SocketCallback callback) {
        synchronized (callbacks) {
            if (!callbacks.contains(callback)) {
                callbacks.add(callback);
            }
        }
    }
    
    /**
     * Hủy đăng ký callback
     */
    public void unregisterCallback(SocketCallback callback) {
        synchronized (callbacks) {
            callbacks.remove(callback);
        }
    }
    
    /**
     * Gửi thông báo đến tất cả callbacks
     */
    private void notifyCallbacks(Runnable callbackAction) {
        synchronized (callbacks) {
            for (SocketCallback callback : callbacks) {
                try {
                    callbackAction.run();
                } catch (Exception e) {
                    System.err.println("Error notifying callback: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Kết nối WebSocket lần đầu
     */
    public void connect() throws URISyntaxException {
        if (client != null && client.isOpen()) {
            return; // Đã kết nối rồi
        }
        
        // Bắt đầu ở trạng thái connecting
        isReconnecting.set(true);
        
        createWebSocketClient();
        client.connect();
    }
    
    /**
     * Bắt đầu auto reconnect
     */
    public void startAutoReconnect() {
        if (shouldReconnect.get()) {
            return; // Đã đang auto reconnect
        }
        
        shouldReconnect.set(true);
        isReconnecting.set(true); // Bắt đầu ở trạng thái connecting
        reconnectAttempts.set(0);
        
        // Tạo scheduler nếu chưa có
        if (reconnectScheduler == null || reconnectScheduler.isShutdown()) {
            reconnectScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "WebSocket-Reconnect-Thread");
                t.setDaemon(true);
                return t;
            });
        }
        
        // Bắt đầu reconnect ngay lập tức
        attemptReconnect();
    }
    
    /**
     * Dừng auto reconnect
     */
    public void stopAutoReconnect() {
        shouldReconnect.set(false);
        isReconnecting.set(false);
        
        if (reconnectScheduler != null && !reconnectScheduler.isShutdown()) {
            reconnectScheduler.shutdown();
        }
    }
    
    /**
     * Đóng kết nối WebSocket
     */
    public void disconnect() {
        stopAutoReconnect();
        
        if (client != null && client.isOpen()) {
            client.close();
        }
        client = null;
        isConnected.set(false);
    }
    
    /**
     * Dừng tất cả monitoring và cleanup
     */
    public void shutdown() {
        stopAutoReconnect();
        disconnect();
        
        if (networkMonitor != null) {
            networkMonitor.stopMonitoring();
        }
    }
    
    /**
     * Gửi message qua WebSocket
     */
    public void send(String message) {
        if (client != null && client.isOpen()) {
            client.send(message);
        }
    }
    
    /**
     * Kiểm tra xem có đang kết nối không
     */
    public boolean isConnected() {
        return isConnected.get() && client != null && client.isOpen();
    }
    
    /**
     * Kiểm tra xem có đang reconnect không
     * Trả về true nếu đang trong quá trình kết nối ban đầu hoặc reconnect
     */
    public boolean isReconnecting() {
        return isReconnecting.get() || (!isConnected.get() && shouldReconnect.get());
    }
    
    /**
     * Lấy số lần đã thử reconnect
     */
    public int getReconnectAttempts() {
        return reconnectAttempts.get();
    }
    
    /**
     * Tạo WebSocket client mới
     */
    private void createWebSocketClient() throws URISyntaxException {
        client = new WebSocketClient(new URI(socketUrl)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("WebSocket Connected");
                markConnected();
                Platform.runLater(() -> notifyCallbacks(() -> {
                    for (SocketCallback callback : callbacks) {
                        callback.onConnectionEstablished();
                    }
                }));
            }
            
            @Override
            public void onMessage(String message) {
                Platform.runLater(() -> notifyCallbacks(() -> {
                    for (SocketCallback callback : callbacks) {
                        callback.onMessage(message);
                    }
                }));
            }
            
            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket Closed: " + reason);
                // Reset reconnecting flag trước khi mark disconnected
                isReconnecting.set(false);
                markDisconnected();
                Platform.runLater(() -> notifyCallbacks(() -> {
                    for (SocketCallback callback : callbacks) {
                        callback.onConnectionLost();
                    }
                }));
            }
            
            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket Error: " + ex.getMessage());
                Platform.runLater(() -> notifyCallbacks(() -> {
                    for (SocketCallback callback : callbacks) {
                        callback.onError(ex);
                    }
                }));
                
                // Đóng client hiện tại nếu còn mở
                if (client != null && client.isOpen()) {
                    try {
                        client.close();
                    } catch (Exception e) {
                        System.err.println("Error closing client: " + e.getMessage());
                    }
                }
                
                // Reset reconnecting flag trước khi mark disconnected
                isReconnecting.set(false);
                
                // Bắt đầu auto reconnect nếu cần
                if (shouldReconnect.get()) {
                    markDisconnected();
                }
            }
        };
    }
    
    /**
     * Thực hiện reconnect attempt
     */
    private void attemptReconnect() {
        System.out.println("Attempting reconnect... shouldReconnect=" + shouldReconnect.get() + ", isReconnecting=" + isReconnecting.get());
        
        if (!shouldReconnect.get()) {
            System.out.println("Auto reconnect disabled, stopping");
            return;
        }
        
        if (isReconnecting.get()) {
            System.out.println("Already reconnecting, skipping");
            return;
        }
        
        isReconnecting.set(true);
        int attempt = reconnectAttempts.incrementAndGet();
        
        System.out.println("Starting reconnect attempt #" + attempt);
        
        // Kiểm tra giới hạn số lần reconnect
        if (MAX_RECONNECT_ATTEMPTS > 0 && attempt > MAX_RECONNECT_ATTEMPTS) {
            shouldReconnect.set(false);
            isReconnecting.set(false);
            System.out.println("Max reconnect attempts reached, stopping");
            Platform.runLater(() -> notifyCallbacks(() -> {
                for (SocketCallback callback : callbacks) {
                    callback.onReconnectFailed("Đã vượt quá số lần thử kết nối tối đa");
                }
            }));
            return;
        }
        
        Platform.runLater(() -> notifyCallbacks(() -> {
            for (SocketCallback callback : callbacks) {
                callback.onReconnectAttempt(attempt);
            }
        }));
        
        try {
            // Đóng client cũ nếu còn mở
            if (client != null && client.isOpen()) {
                System.out.println("Closing old client...");
                try {
                    client.close();
                } catch (Exception e) {
                    System.err.println("Error closing old client: " + e.getMessage());
                }
            }
            
            // Tạo WebSocket client mới
            System.out.println("Creating new WebSocket client...");
            createWebSocketClient();
            System.out.println("Connecting to " + socketUrl + "...");
            client.connect();
            
        } catch (Exception e) {
            System.err.println("Lỗi khi tạo WebSocket client: " + e.getMessage());
            e.printStackTrace();
            isReconnecting.set(false);
            scheduleReconnect();
        }
    }
    
    /**
     * Lên lịch reconnect attempt tiếp theo
     */
    private void scheduleReconnect() {
        System.out.println("scheduleReconnect called - shouldReconnect=" + shouldReconnect.get() + ", isReconnecting=" + isReconnecting.get());
        
        if (!shouldReconnect.get()) {
            System.out.println("Auto reconnect disabled, skipping schedule");
            return;
        }
        
        if (isReconnecting.get()) {
            System.out.println("Already reconnecting, skipping schedule");
            return;
        }
        
        if (reconnectScheduler != null && !reconnectScheduler.isShutdown()) {
            System.out.println("Scheduling reconnect in " + RECONNECT_INTERVAL_SECONDS + " seconds");
            reconnectScheduler.schedule(this::attemptReconnect, RECONNECT_INTERVAL_SECONDS, TimeUnit.SECONDS);
        } else {
            System.out.println("Reconnect scheduler not available");
        }
    }
    
    /**
     * Đánh dấu connection đã thành công
     */
    private void markConnected() {
        isConnected.set(true);
        isReconnecting.set(false);
        reconnectAttempts.set(0);
    }
    
    /**
     * Đánh dấu connection bị mất
     */
    private void markDisconnected() {
        System.out.println("markDisconnected called - shouldReconnect=" + shouldReconnect.get() + ", isReconnecting=" + isReconnecting.get());
        isConnected.set(false);
        
        // Reset reconnecting flag để đảm bảo có thể schedule reconnect
        isReconnecting.set(false);
        
        if (shouldReconnect.get()) {
            // Bắt đầu reconnect sau khi connection bị mất
            System.out.println("Starting reconnect process...");
            scheduleReconnect();
        } else {
            System.out.println("Auto reconnect disabled, not scheduling");
        }
    }
    
    /**
     * Force reset reconnecting flag (for debugging)
     */
    public void forceResetReconnecting() {
        System.out.println("Force resetting reconnecting flag");
        isReconnecting.set(false);
    }
    
    /**
     * Lấy trạng thái kết nối dạng string
     */
    public String getConnectionStatus() {
        if (isConnected()) {
            return "Socket connected ✓";
        } else if (isReconnecting()) {
            return "Socket reconnecting... (attempt " + getReconnectAttempts() + ")";
        } else if (shouldReconnect.get()) {
            return "Socket disconnected, auto-reconnect enabled";
        } else {
            return "Socket disconnected";
        }
    }
}
