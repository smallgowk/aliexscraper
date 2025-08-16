package com.phanduy.aliexscrap;

import com.phanduy.aliexscrap.api.ApiClient;
import com.phanduy.aliexscrap.api.ApiService;
import com.phanduy.aliexscrap.utils.AlertUtil;
import com.phanduy.aliexscrap.utils.VersionUtils;
import com.phanduy.aliexscrap.utils.ComputerIdentifier;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.prefs.Preferences;

public class AboutPanelController {
    @FXML private Label versionLabel;
    @FXML private TextField computerSerial;
    @FXML private Label latestVersionLabel;
    @FXML private Button updateButton;
    private final ApiService apiService = ApiClient.getClient().create(ApiService.class);

    private Preferences prefs;

    @FXML
    private void initialize() {
        prefs = Preferences.userNodeForPackage(AboutPanelController.class);
        versionLabel.setText("Version: " + VersionUtils.getAppVersionFromResource());
        computerSerial.setText(ComputerIdentifier.diskSerial);
        if (prefs.getBoolean("Latest", false)) {
            latestVersionLabel.setVisible(true);
            updateButton.setVisible(false);
        } else {
            latestVersionLabel.setVisible(false);
            updateButton.setVisible(true);
        }
    }

    @FXML
    private void onUpdateVersion() {
        openDownloadInBrowser(prefs.get("LatestVersion", ""));
    }

    private void openDownloadInBrowser(String latestVersion) {

        String url = "https://iamhere.vn/AliexScrapInstaller-" + latestVersion + ".exe";
        String downloads = System.getProperty("user.home") + File.separator + "Downloads";
        File downloadsDir = new File(downloads);
        if (!downloadsDir.exists()) downloadsDir.mkdirs();
        String installer = downloads + File.separator + "AliexScrapInstaller-" + latestVersion + ".exe";

        DownloadTask task = new DownloadTask(url, installer);
        ProgressBar progressBar = new ProgressBar();
        progressBar.progressProperty().bind(task.progressProperty());
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Đang tải bản cập nhật...");
        VBox vbox = new VBox(10, new Label("Đang tải bản cập nhật..."), progressBar);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        dialogStage.setScene(new Scene(vbox, 350, 100));
        dialogStage.setResizable(false);

        task.setOnSucceeded(e -> {
            dialogStage.close();
            try {
                String absPath = new java.io.File(installer).getAbsolutePath();
                Runtime.getRuntime().exec("cmd /c start \"\" \"" + absPath + "\"");
                System.exit(0);
            } catch (Exception ex) {
                AlertUtil.showError("Lỗi", "Không thể chạy file cài đặt: " + ex.getMessage());
                Platform.exit();
            }
        });
        task.setOnFailed(e -> {
            dialogStage.close();
            Throwable ex = task.getException();
            if (ex != null) ex.printStackTrace();
            AlertUtil.showError("Lỗi", "Không thể tải file cập nhật!");
            Platform.exit();
        });
        new Thread(task).start();
        dialogStage.showAndWait();
    }

    private static class DownloadTask extends Task<Void> {
        private final String fileURL;
        private final String savePath;
        public DownloadTask(String fileURL, String savePath) {
            this.fileURL = fileURL;
            this.savePath = savePath;
        }
        @Override
        protected Void call() throws Exception {
            try {
                URL url = new URL(fileURL);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(30000);
                int responseCode = conn.getResponseCode();
                if (responseCode != 200) throw new IOException("HTTP error: " + responseCode);
                long fileSize = conn.getContentLengthLong();
                try (InputStream in = new BufferedInputStream(conn.getInputStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {
                    byte[] dataBuffer = new byte[8192];
                    int bytesRead;
                    long totalBytes = 0;
                    while ((bytesRead = in.read(dataBuffer, 0, 8192)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        totalBytes += bytesRead;
                        if (fileSize > 0) {
                            updateProgress(totalBytes, fileSize);
                        }
                    }
                }
                return null;
            } catch (Exception ex) {
                ex.printStackTrace(); // Log chi tiết lỗi download
                throw ex;
            }
        }
    }
}
