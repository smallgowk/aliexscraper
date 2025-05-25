package com.phanduy.aliexscrap;

import com.config.Configs;
import com.controller.DownloadManager;
import com.controller.inputprocess.InputDataConfig;
import com.controller.inputprocess.SnakeReadOrderInfoSvs;
import com.controller.thread.ProcessCrawlRapidNoCrawlThread;
import com.interfaces.CrawlProcessListener;
import com.interfaces.DownloadListener;
import com.models.aliex.store.inputdata.SnakeBaseStoreOrderInfo;
import com.models.response.ResponseObj;
import com.phanduy.aliexscrap.utils.*;
import com.utils.ExcelUtils;
import com.view.DataUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
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
            AlertUtil.showError("", "Chưa chọn file config!");
            return;
        }

        File checkFile = new File(configFile);
        if (!checkFile.exists()) {
            AlertUtil.showError("", "File config không tồn tại!");
            return;
        }

        String templateFilePath = amzProductTemplate1Field.getText();
        if (StringUtils.isEmpty(templateFilePath)) {
            AlertUtil.showError("", "Chưa chọn amazon template!");
            return;
        }

        checkFile = new File(templateFilePath);
        if (!checkFile.exists()) {
            AlertUtil.showError("", "File template không tồn tại!");
            return;
        }

        Configs.excelSampleFilePath = templateFilePath;

        String output = FileOpener.getFileNameWithoutExtension(outputField.getText());
        if (StringUtils.isEmpty(output)) {
            AlertUtil.showError("", "Chưa chọn output folder!");
            return;
        }

        Configs.TOOL_DATA_PATH = outputField.getText();
        Configs.updateDataPath();

        InputDataConfig inputDataConfig = SnakeReadOrderInfoSvs.getInstance().readStoreOrderLinks(configFileField.getText());
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

        processCrawlThread = new ProcessCrawlRapidNoCrawlThread(
                (SnakeBaseStoreOrderInfo) inputDataConfig.listStores.get(0),
                inputDataConfig.params,
                crawlProcessListener
        );
        processCrawlThread.start();
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

}
