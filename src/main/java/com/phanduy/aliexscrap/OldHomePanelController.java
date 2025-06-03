package com.phanduy.aliexscrap;

import com.config.Configs;
import com.controller.DownloadManager;
import com.controller.inputprocess.InputDataConfig;
import com.controller.inputprocess.SnakeReadOrderInfoSvs;
import com.controller.thread.ProcessCrawlRapidNoCrawlThread;
import com.interfaces.CrawlProcessListener;
import com.interfaces.DownloadListener;
import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import com.models.request.CheckInfoReq;
import com.models.response.CheckInfoResponse;
import com.models.response.ResponseObj;
import com.phanduy.aliexscrap.api.ApiCall;
import com.phanduy.aliexscrap.api.ApiClient;
import com.phanduy.aliexscrap.api.ApiExecutor;
import com.phanduy.aliexscrap.api.ApiService;
import com.phanduy.aliexscrap.model.response.GetPageDataResponse;
import com.phanduy.aliexscrap.model.response.GetStoreInfoRapidData;
import com.phanduy.aliexscrap.utils.*;
import com.utils.ExcelUtils;
import com.view.DataUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.prefs.Preferences;

public class OldHomePanelController {
    @FXML private TextField amzProductTemplate1Field;
    @FXML private TextField outputField;
    @FXML private TextField configFileField;

    @FXML private Button browseOutput;
    @FXML private Button browseConfigFile;
    @FXML private Button browseTemplate1;
    @FXML private Button crawlButton;

    @FXML private Label crawlSignLabel;
    @FXML private Label statusLabel;
    @FXML private Label downloadImageLabel;

    // Preferences API để cache setting
    private Preferences prefs;

    ProcessCrawlRapidNoCrawlThread processCrawlThread;

    @FXML
    public void initialize() {
        prefs = Preferences.userNodeForPackage(OldHomePanelController.class);
        String version = VersionUtils.getAppVersionFromResource();
        prefs.put("Version", version);

        loadSettings();
        DownloadManager.getInstance().setListener(downloadListener);
//        DownloadManager.getInstance().testDownload(
//                "https://ae01.alicdn.com/kf/S1371cc3fa6474ca9b04cf15a8643b75eI/4PCS-Privacy-Screen-Protector-For-iPhone-14-Pro-Max-16-Pro-Anti-Spy-Glass-For-iPhone.jpg",
//                    "D:\\Data\\Dropship\\Products\\Images\\Jewelry\\iphone\\3256806491907393\\1.jpg"
//                );
//
//        DownloadManager.getInstance().testDownload(
//                "https://cdn.britannica.com/69/177069-050-5F685982/Anne-Bonny-Calico-Jack-Mary-Read-crew.jpg",
//                "D:\\Data\\Dropship\\Products\\Images\\Jewelry\\iphone\\3256806491907393\\2.jpg"
//        );

//        String imageUrl = "https://ae01.alicdn.com/kf/S1371cc3fa6474ca9b04cf15a8643b75eI/4PCS-Privacy-Screen-Protector-For-iPhone-14-Pro-Max-16-Pro-Anti-Spy-Glass-For-iPhone.jpg";
//        String targetPath = "downloaded_image.jpg";
//        String key = "download_001";
//
//        DownloadListener listener = new DownloadListener() {
//            @Override
//            public void onComplete(String key) {
//                System.out.println("Download hoàn thành cho key: " + key);
//            }
//        };
//
//        ImageDownloader downloader = new ImageDownloader(imageUrl, targetPath, key, listener);
//
//        // Chạy trong thread mới
//        Thread downloadThread = new Thread(downloader);
//        downloadThread.start();
        ThreadManager.getInstance().submitTask(
                () -> {
                    try {
                        CheckInfoResponse checkInfoResponse = ApiCall.getInstance().checkInfo(
                                new CheckInfoReq(
                                        version,
                                        ComputerIdentifier.getDiskSerialNumber(),
                                        "newpltool"
                                )
                        );
                        int code = checkInfoResponse.getResultCode();
                        if (code != 1) {
                            switch (code) {
                                case CheckInfoResponse.SERIAL_INVALID:
                                    showInvalidInfo("Máy tính cài đặt không hợp lệ. Liên hệ 0972071089 để được xác thực!");
                                    break;
                                case CheckInfoResponse.TIME_LIMIT:
                                    showInvalidInfo("Máy tính đã hết thời gian sử dụng. Liên hệ 0972071089 để được xử lý!");
                                    break;
                                case CheckInfoResponse.PRODUCT_LIMIT:
                                    showInvalidInfo("Gói sử dụng đã hết lưu lượng sử dụng. Liên hệ 0972071089 để được xử lý!");
                                    break;
                                case CheckInfoResponse.VERSION_INVALID:
                                    showInvalidVersion("Version app đã quá cũ! Vui lòng cập nhật version mới để sử dụng!", checkInfoResponse.getLatestVersion());
                                    break;
                                default:
                                    showInvalidInfo("Server error!. Liên hệ 0972071089 để được xử lý!");
                            }
                        } else {
                            prefs.putBoolean("Latest", checkInfoResponse.isLatest());
                            prefs.put("LatestVersion", checkInfoResponse.getLatestVersion());
                        }

                    } catch (Exception e) {
                        showInvalidInfo("Có lỗi xảy ra!");
                    }
                }
        );
    }

    private void showInvalidInfo(String message) {
        Platform.runLater(
                () -> {
                    boolean ok = AlertUtil.showError("", message);
                    if (ok) {
                        Platform.exit();
                    }
                }
        );
    }

    private void showInvalidVersion(String message, String latestVersion) {
        Platform.runLater(
                () -> {
                    boolean confirmed = AlertUtil.showConfirmDialog("", message);
                    if (confirmed) {
                        openDownloadInBrowser(latestVersion);
                        Platform.exit();
                    } else {
                        Platform.exit();
                    }
                }
        );
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


    @FXML
    private void onOpenConfigFile() {
        FileOpener.openFileOrFolder(configFileField.getText());
    }

    @FXML
    private void onOpenOutputFolder() {
        FileOpener.openFileOrFolder(outputField.getText());
    }

    @FXML
    private void onBrowserOutputFolder() {
        DirectoryChooser directoryChooser = getDirectoryChooser(outputField);

        // Show the dialog and get the selected directory
        Stage stage = (Stage) browseOutput.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        // Process the selected directory
        if (selectedDirectory != null) {
            outputField.setText(selectedDirectory.getAbsolutePath());
            prefs.put("outputField", selectedDirectory.getAbsolutePath());
        }
    }

    @NotNull
    private DirectoryChooser getDirectoryChooser(TextField outputField) {
        String currentPath = outputField.getText();
        String folderPath = null;
        if (currentPath.isEmpty()) {
            folderPath = ".";
        } else {
            folderPath = currentPath.substring(0, currentPath.lastIndexOf("\\"));
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(folderPath));

        // Set the title for the DirectoryChooser dialog
        directoryChooser.setTitle("Select Output Folder");
        return directoryChooser;
    }

    @FXML
    private void onBrowserConfigFile() {
        String currentPath = configFileField.getText();
        String folderPath = null;
        if (currentPath.isEmpty()) {
            folderPath = ".";
        } else {
            folderPath = currentPath.substring(0, currentPath.lastIndexOf("\\"));
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(folderPath));

        // Set the title for the FileChooser dialog
        fileChooser.setTitle("Select Excel File");

        // Restrict the selection to Excel files
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx")
        );

        // Show the dialog and get the selected file
        Stage stage = (Stage) browseConfigFile.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Process the selected file
        if (selectedFile != null) {
            configFileField.setText(selectedFile.getAbsolutePath());
            prefs.put("configFileField", selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void onOpenAmzProductTempFile1() {
        FileOpener.openFileOrFolder(amzProductTemplate1Field.getText());
    }

    @FXML
    private void onBrowserAmzProductTempFile1() {
        String currentPath = amzProductTemplate1Field.getText();
        String folderPath = null;
        if (currentPath.isEmpty()) {
            folderPath = ".";
        } else {
            folderPath = currentPath.substring(0, currentPath.lastIndexOf("\\"));
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(folderPath));

        // Set the title for the FileChooser dialog
        fileChooser.setTitle("Select Excel File");

        // Restrict the selection to Excel files
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx")
        );

        // Show the dialog and get the selected file
        Stage stage = (Stage) browseTemplate1.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Process the selected file
        if (selectedFile != null) {
            amzProductTemplate1Field.setText(selectedFile.getAbsolutePath());
            prefs.put("amzProductTemplate1Field", selectedFile.getAbsolutePath());
        }
    }

    private void saveSettings() {
        prefs.put("outputField", outputField.getText());
        prefs.put("configFileField", configFileField.getText());

        System.out.println("Settings Saved!");
    }

    private void loadSettings() {
        outputField.setText(prefs.get("outputField", ""));
        configFileField.setText(prefs.get("configFileField", ""));
        amzProductTemplate1Field.setText(prefs.get("amzProductTemplate1Field", ""));
        System.out.println("Settings Loaded!");
    }

    @FXML
    private void onCrawlClick() {
        if (processCrawlThread != null && !processCrawlThread.isStop) {
            processCrawlThread.doStop();
            return;
        }

        String configFile = configFileField.getText();
        if (StringUtils.isEmpty(configFile)) {
            AlertUtil.showError("", "Config file not selected!");
            return;
        }

        File checkFile = new File(configFile);
        if (!checkFile.exists()) {
            AlertUtil.showError("", "Config file does not exist!");
            return;
        }

        String templateFilePath = amzProductTemplate1Field.getText();
        if (StringUtils.isEmpty(templateFilePath)) {
            AlertUtil.showError("", "Amazon template not selected!");
            return;
        }

        checkFile = new File(templateFilePath);
        if (!checkFile.exists()) {
            AlertUtil.showError("", "Template file does not exist!");
            return;
        }

        Configs.excelSampleFilePath = templateFilePath;

        String output = FileOpener.getFileNameWithoutExtension(outputField.getText());
        if (StringUtils.isEmpty(output)) {
            AlertUtil.showError("", "Output folder not selected!");
            return;
        }

        Configs.TOOL_DATA_PATH = outputField.getText();
        Configs.updateDataPath();

        InputDataConfig inputDataConfig = null;
        try {
            inputDataConfig = SnakeReadOrderInfoSvs.getInstance().readStoreOrderLinks(configFileField.getText());
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("error.log", true)) {
                fw.write("Exception while reading inputDataConfig: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            AlertUtil.showError("", "Error reading config file!");
            return;
        }
        DataUtils.updateAllStores(inputDataConfig.listStores);

        boolean isOldTemplate = ExcelUtils.isOldTemplate(templateFilePath);
        if (isOldTemplate) {
            inputDataConfig.params.put("template", "");
        } else {
            inputDataConfig.params.put("template", "NewTemplate");
        }

        if (processCrawlThread != null) {
            processCrawlThread.doStop();
        }

        BaseStoreOrderInfo storeOrderInfo = null;
        try {
            storeOrderInfo = inputDataConfig.listStores.get(0);
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("error.log", true)) {
                fw.write("Exception when getting storeOrderInfo: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            AlertUtil.showError("", "Error getting storeOrderInfo!");
            return;
        }
        try {
            processCrawlThread = new ProcessCrawlRapidNoCrawlThread(
                    storeOrderInfo,
                    inputDataConfig.params,
                    crawlProcessListener
            );
            processCrawlThread.start();
        } catch (Exception ex) {
            try (java.io.FileWriter fw = new java.io.FileWriter("error.log", true)) {
                fw.write("Exception when creating or starting thread crawl: " + ex.toString() + "\n");
                for (StackTraceElement ste : ex.getStackTrace()) {
                    fw.write("    at " + ste.toString() + "\n");
                }
            } catch (Exception e) {}
            AlertUtil.showError("", "Error initializing crawl!");
        }
    }

    CrawlProcessListener crawlProcessListener = new CrawlProcessListener() {

        @Override
        public void onPushState(String storeSign, String state) {
            DataUtils.updateStatus(storeSign, state);
            Platform.runLater(() -> {
                statusLabel.setText(state);
            });
        }

        @Override
        public void onPushErrorRequest(String storeSign, ResponseObj responseObj) {

        }

        @Override
        public void onStop(String storeSign) {
            Platform.runLater(() -> {
                crawlButton.setText("Start");
            });
        }

        @Override
        public void onStartProcess(String storeSign, String info) {
            Platform.runLater(() -> {
                crawlSignLabel.setText(info);
                crawlButton.setText("Stop");
            });
        }

        @Override
        public void onStopToLogin(String currentUrl, String storeSign) {
        }

        @Override
        public void onFinishPage(String storeSign) {
            Platform.runLater(() -> {
                crawlButton.setText("Start");
            });
        }

        @Override
        public void onExit() {
            Platform.runLater(() -> {
                crawlButton.setText("Start");
            });
        }
    };

    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onComplete(String key) {
            Platform.runLater(() -> {
                updateDownloadState();
            });
        }
    };

    public void updateDownloadState() {
//        if (StringUtils.isEmpty(Configs.vpsIp)) {
//            lblDownloadState.setText("");
//        } else {
//            lblDownloadState.setText("" + DownloadManager.getInstance().getTotalComplete() + "/" + DownloadManager.getInstance().getTotalDownload());
//        }
        downloadImageLabel.setText("Downloaded Images: " + DownloadManager.getInstance().getTotalComplete());
    }

    @FXML
    private void showAboutPanel() {
        try {
            // Load HomePanel.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/phanduy/aliexscrap/AboutPanel.fxml"));
            Parent root = loader.load();


            // Tạo cửa sổ mới (Stage)
            Stage settingStage = new Stage();
            settingStage.getIcons().add(new Image(getClass().getResourceAsStream("/image/aliexscrap.png")));
            settingStage.setTitle("About");
            settingStage.setScene(new Scene(root));

            // Căn chỉnh kích thước cửa sổ
            settingStage.setMinWidth(300);
            settingStage.setMinHeight(200);
            settingStage.setResizable(false);

            // Hiển thị cửa sổ (floating panel)
            settingStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
