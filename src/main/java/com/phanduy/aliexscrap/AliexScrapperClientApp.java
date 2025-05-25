package com.phanduy.aliexscrap;

import com.phanduy.aliexscrap.utils.ThreadManager;
import com.phanduy.aliexscrap.utils.VersionUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AliexScrapperClientApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/phanduy/aliexscrap/OldHomePanel.fxml"));
        Parent root = loader.load();

//        JMetro jMetro = new JMetro(Style.LIGHT); // hoặc Style.DARK
//        jMetro.setScene(new Scene(root, 500, 350)); // tạo scene có style


        primaryStage.setTitle("Aliexpress Scraper - version " + VersionUtils.getAppVersionFromResource());
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/image/aliexscrap.png")));
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setResizable(false);

        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            ThreadManager.getInstance().shutdown();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            try (java.io.FileWriter fw = new java.io.FileWriter("error.log", true)) {
                fw.write("Uncaught exception in thread " + t.getName() + ": " + e + "\n");
                for (StackTraceElement ste : e.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
                Throwable cause = e.getCause();
                while (cause != null) {
                    fw.write("Caused by: " + cause + "\n");
                    for (StackTraceElement ste : cause.getStackTrace()) {
                        fw.write("    at " + ste.toString() + "\n");
                    }
                    cause = cause.getCause();
                }
            } catch (Exception ex) {}
        });
        launch(AliexScrapperClientApp.class, args);
    }

    @Override
    public void stop() throws Exception {
        Platform.exit();
        System.exit(0);
    }
}
