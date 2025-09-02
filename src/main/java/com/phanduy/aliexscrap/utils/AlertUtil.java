package com.phanduy.aliexscrap.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Objects;
import java.util.Optional;

public class AlertUtil {

    public static boolean showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static boolean showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static boolean showConfirmDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Chỉ hiện OK và Cancel
        alert.getButtonTypes().setAll(new ButtonType("Update"), ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && Objects.equals(result.get().getText(), "Update");
    }
}
