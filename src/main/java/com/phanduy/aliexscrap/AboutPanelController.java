package com.phanduy.aliexscrap;

import com.phanduy.aliexscrap.api.ApiClient;
import com.phanduy.aliexscrap.api.ApiService;
import com.phanduy.aliexscrap.utils.AlertUtil;
import com.phanduy.aliexscrap.utils.FileOpener;
import com.phanduy.aliexscrap.utils.VersionUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URI;
import java.util.prefs.Preferences;

public class AboutPanelController {
    @FXML private Label versionLabel;
    @FXML private Label latestVersionLabel;
    @FXML private Button updateButton;
    private final ApiService apiService = ApiClient.getClient().create(ApiService.class);

    private Preferences prefs;

    @FXML
    private void initialize() {
        prefs = Preferences.userNodeForPackage(AboutPanelController.class);
        versionLabel.setText("Version: " + VersionUtils.getAppVersionFromResource());
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
        try {
            String downloadUrl = "http://iamhere.vn/AliexScrapInstaller-" + latestVersion + ".zip";

            // Mở URL trong trình duyệt mặc định
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(downloadUrl));
            }

        } catch (Exception e) {
            AlertUtil.showError("Lỗi", "Không thể mở trình duyệt: " + e.getMessage());
        }
    }
}
