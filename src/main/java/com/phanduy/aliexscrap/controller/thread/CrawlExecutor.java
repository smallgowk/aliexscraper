package com.phanduy.aliexscrap.controller.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlExecutor {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void executeAsync(Runnable task) {
        executor.submit(task);
    }

    public static void shutdown() {
        executor.shutdown();
    }

    public static void executeThread(Thread thread) {
        executor.submit(() -> {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
