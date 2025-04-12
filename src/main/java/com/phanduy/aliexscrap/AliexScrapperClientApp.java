package com.phanduy.aliexscrap;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/phanduy/aliexscrap/HomePanel.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Aliexpress Scrap to Amazon - version " + VersionUtils.getAppVersionFromResource());
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/image/aliexscrap.png")));
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.setResizable(false);

        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(AliexScrapperClientApp.class, args);
    }

    @Override
    public void stop() throws Exception {
        Platform.exit();
        System.exit(0);
    }
}
