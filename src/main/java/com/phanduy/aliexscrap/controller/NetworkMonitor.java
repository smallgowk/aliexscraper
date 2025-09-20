package com.phanduy.aliexscrap.controller;

import javafx.application.Platform;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Monitor network connectivity status
 */
public class NetworkMonitor {
    private static volatile NetworkMonitor instance;
    private static final Object lock = new Object();
    
    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean isNetworkAvailable = new AtomicBoolean(true);
    private final AtomicBoolean isMonitoring = new AtomicBoolean(false);
    private volatile boolean lastCallbackState = true; // Trạng thái cuối cùng đã gọi callback
    
    private NetworkCallback callback;
    
    public interface NetworkCallback {
        void onNetworkLost();
        void onNetworkRestored();
    }
    
    private NetworkMonitor() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "Network-Monitor-Thread");
            t.setDaemon(true);
            return t;
        });
    }
    
    public static NetworkMonitor getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new NetworkMonitor();
                }
            }
        }
        return instance;
    }
    
    /**
     * Bắt đầu monitor network
     */
    public void startMonitoring(NetworkCallback callback) {
        this.callback = callback;
        
        if (isMonitoring.get()) {
            System.out.println("Network monitoring already running, updating callback only");
            return; // Đã đang monitor
        }
        
        isMonitoring.set(true);
        this.lastCallbackState = isNetworkAvailable.get(); // Reset trạng thái callback
        
        System.out.println("Network monitoring started with initial state: " + lastCallbackState);
        
        // Check network status ngay lập tức
        checkNetworkStatus();
        
        // Monitor mỗi 2 giây
        scheduler.scheduleWithFixedDelay(this::checkNetworkStatus, 2, 2, TimeUnit.SECONDS);
    }
    
    /**
     * Dừng monitor network
     */
    public void stopMonitoring() {
        if (!isMonitoring.get()) {
            return;
        }
        
        isMonitoring.set(false);
        scheduler.shutdown();
        
        System.out.println("Network monitoring stopped");
    }
    
    /**
     * Kiểm tra trạng thái network
     */
    private void checkNetworkStatus() {
        boolean networkAvailable = checkNetworkConnectivity();
        boolean currentStatus = isNetworkAvailable.get();
        
        // Chỉ log khi có thay đổi hoặc mỗi 10 lần check
        if (networkAvailable != currentStatus || (System.currentTimeMillis() % 10000 < 1000)) {
            System.out.println("Network check: available=" + networkAvailable + ", current=" + currentStatus + ", lastCallback=" + lastCallbackState + ", callback=" + (callback != null));
        }
        
        if (networkAvailable != currentStatus) {
            System.out.println("Network status changed from " + currentStatus + " to " + networkAvailable);
            isNetworkAvailable.set(networkAvailable);
            
            // Chỉ gọi callback nếu trạng thái thực sự thay đổi
            if (callback != null && networkAvailable != lastCallbackState) {
                System.out.println("Calling callback for network change from " + lastCallbackState + " to " + networkAvailable);
                lastCallbackState = networkAvailable;
                
                Platform.runLater(() -> {
                    System.out.println("Platform.runLater executed - networkAvailable: " + networkAvailable);
                    if (networkAvailable) {
                        System.out.println("Network restored - calling callback");
                        callback.onNetworkRestored();
                    } else {
                        System.out.println("Network lost - calling callback");
                        callback.onNetworkLost();
                    }
                });
            } else if (callback == null) {
                System.out.println("No callback registered!");
            } else {
                System.out.println("Callback already called for this state: " + networkAvailable + " (lastCallbackState: " + lastCallbackState + ")");
            }
        }
    }
    
    /**
     * Kiểm tra xem có kết nối mạng không
     */
    private boolean checkNetworkConnectivity() {
        try {
            // Thử ping Google DNS
            InetAddress address = InetAddress.getByName("8.8.8.8");
            return address.isReachable(3000); // Timeout 3 giây
        } catch (Exception e) {
            // Nếu không ping được, thử kiểm tra network interfaces
            try {
                return NetworkInterface.getNetworkInterfaces().hasMoreElements();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    /**
     * Kiểm tra xem có đang monitor không
     */
    public boolean isMonitoring() {
        return isMonitoring.get();
    }
    
    /**
     * Kiểm tra trạng thái network hiện tại
     */
    public boolean isNetworkAvailable() {
        return isNetworkAvailable.get();
    }
    
    /**
     * Reset trạng thái callback (dùng khi cần force callback)
     */
    public void resetCallbackState() {
        this.lastCallbackState = isNetworkAvailable.get();
        System.out.println("Callback state reset to: " + lastCallbackState);
    }
}
