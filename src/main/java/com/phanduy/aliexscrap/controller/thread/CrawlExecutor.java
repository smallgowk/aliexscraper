package com.phanduy.aliexscrap.controller.thread;

import com.phanduy.aliexscrap.controller.transform.ProcessStoreInfoSvs;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlExecutor {

    private static ExecutorService executor = null;
    public static int maxThread = 1;

    public static HashSet<String> bannedSignature = new HashSet<>();
    public static boolean isAuthen = true;
    public static boolean isStop = true;

    public static void initExecutor(int threads) {
        maxThread = threads;
        executor = Executors.newFixedThreadPool(maxThread);
        System.out.println("Inited executor with max thead " + maxThread + " - " + threads);
    }

    public static void executeAsync(Runnable task) {
        ensureExecutor();
        executor.submit(task);
    }

    public static void shutdown() {
        executor.shutdown();
    }

    public static void shutdownNow() {
        ProcessStoreInfoSvs.clearMapData();
        isStop = true;
        executor.shutdownNow();
    }

    public static void addSignatureToBanned(String signature) {
        bannedSignature.add(signature);
    }

    public static boolean isBannedSignature(String signature) {
        return bannedSignature.contains(signature);
    }

    public static String checkState(String signature) {
        if (!isAuthen) {
            return "Auth";
        } else {
            if (isBannedSignature(signature)) {
                return "Post.";
            } else {
                return null;
            }
        }
    }

    public static void executeThread(Thread thread) {
        ensureExecutor();
        isStop = false;
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
            executor = Executors.newFixedThreadPool(maxThread);
        }
    }
}
