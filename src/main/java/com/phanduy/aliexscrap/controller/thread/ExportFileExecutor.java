package com.phanduy.aliexscrap.controller.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExportFileExecutor {

    private static ExecutorService executor = null;

    public static void initExecutor(int threads) {
        executor = Executors.newFixedThreadPool(1);
    }

    public static void executeAsync(Runnable task) {
        ensureExecutor();
        executor.submit(task);
    }

    public static void shutdown() {
        executor.shutdown();
    }

    public static void shutdownNow() {
        executor.shutdownNow();
    }

    public static void executeThread(Thread thread) {
        ensureExecutor();
        executor.submit(() -> {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private static void ensureExecutor() {
        if (executor == null || executor.isShutdown() || executor.isTerminated()) {
            executor = Executors.newFixedThreadPool(1);
        }
    }
}
