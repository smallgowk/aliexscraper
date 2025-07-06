package com.phanduy.aliexscrap.controller.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlExecutor {

    private static ExecutorService executor = null;
    public static int maxThread = 1;

    public static void initExecutor(int threads) {
        maxThread = threads;
        executor = Executors.newFixedThreadPool(maxThread);
        System.out.println("Inited executor with max thead " + maxThread);
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
        if (executor.isShutdown() || executor.isTerminated()) {
            executor = Executors.newFixedThreadPool(maxThread);
        }
    }
}
