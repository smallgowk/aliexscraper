package com.phanduy.aliexscrap.controller;

import javafx.application.Platform;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Quản lý WebSocket connection và auto reconnect
 */
public class SocketManager {
    private static final int RECONNECT_INTERVAL_SECONDS = 2;
    private static final int MAX_RECONNECT_ATTEMPTS = -1; // -1 = unlimited
    
    private final String socketUrl;
    private final SocketCallback callback;
    private final AtomicBoolean isReconnecting = new AtomicBoolean(false);
    private final AtomicBoolean shouldReconnect = new AtomicBoolean(false);
    private final AtomicBoolean isConnected = new AtomicBoolean(false);
    private final AtomicInteger reconnectAttempts = new AtomicInteger(0);
    
    private ScheduledExecutorService reconnectScheduler;
    private WebSocketClient client;
    
    public interface SocketCallback {
        void onConnectionEstablished();
        void onConnectionLost();
        void onReconnectAttempt(int attemptNumber);
        void onReconnectFailed(String reason);
        void onMessage(String message);
        void onError(Exception ex);
    }
    
    public SocketManager(String socketUrl, SocketCallback callback) {
        this.socketUrl = socketUrl;
        this.callback = callback;
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
                Platform.runLater(() -> callback.onConnectionEstablished());
            }
            
            @Override
            public void onMessage(String message) {
                Platform.runLater(() -> callback.onMessage(message));
            }
            
            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket Closed: " + reason);
                // Reset reconnecting flag trước khi mark disconnected
                isReconnecting.set(false);
                markDisconnected();
                Platform.runLater(() -> callback.onConnectionLost());
            }
            
            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket Error: " + ex.getMessage());
                Platform.runLater(() -> callback.onError(ex));
                
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
            Platform.runLater(() -> callback.onReconnectFailed("Đã vượt quá số lần thử kết nối tối đa"));
            return;
        }
        
        Platform.runLater(() -> callback.onReconnectAttempt(attempt));
        
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
