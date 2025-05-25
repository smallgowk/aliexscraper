package com.phanduy.aliexscrap.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {
    public static void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // hoặc đặt header nếu muốn
        alert.setContentText(message);
        alert.showAndWait();
    }
}
