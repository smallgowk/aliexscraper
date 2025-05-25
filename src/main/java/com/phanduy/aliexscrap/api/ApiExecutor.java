package com.phanduy.aliexscrap.api;

import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ApiExecutor {

    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static <T> void executeSync(Call<T> call, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        executor.submit(() -> {
            try {
                Response<T> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    Platform.runLater(() -> onSuccess.accept(response.body()));
                } else {
                    Platform.runLater(() -> onError.accept(new IOException("Server error")));
                }
            } catch (Exception e) {
                Platform.runLater(() -> onError.accept(e));
            }
        });
    }
}

