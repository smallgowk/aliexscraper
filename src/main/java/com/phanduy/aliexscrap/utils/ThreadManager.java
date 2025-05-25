package com.phanduy.aliexscrap.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private static ThreadManager instance;
    private final ExecutorService executor;

    private ThreadManager() {
        // Tạo thread pool với số lượng luồng cố định (có thể điều chỉnh)
        executor = Executors.newFixedThreadPool(5);
    }

    public static synchronized ThreadManager getInstance() {
        if (instance == null) {
            instance = new ThreadManager();
        }
        return instance;
    }

    public void submitTask(Runnable task) {
        executor.submit(task);
    }

    public void shutdown() {
        executor.shutdownNow(); // hoặc .shutdown() nếu muốn đợi task hiện tại hoàn thành
        System.out.println("ThreadManager: Executor shutdown.");
    }

    public boolean isShutdown() {
        return executor.isShutdown();
    }
}

