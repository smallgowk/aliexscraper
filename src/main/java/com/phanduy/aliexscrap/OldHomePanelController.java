package com.phanduy.aliexscrap;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.controller.DownloadManager;
import com.phanduy.aliexscrap.controller.inputprocess.InputDataConfig;
import com.phanduy.aliexscrap.controller.inputprocess.SnakeReadOrderInfoSvs;
import com.phanduy.aliexscrap.controller.thread.CrawlExecutor;
import com.phanduy.aliexscrap.controller.thread.ProcessCrawlRapidNoCrawlThread;
import com.phanduy.aliexscrap.interfaces.CrawlProcessListener;
import com.phanduy.aliexscrap.interfaces.DownloadListener;
import com.phanduy.aliexscrap.model.aliex.store.inputdata.BaseStoreOrderInfo;
import com.phanduy.aliexscrap.model.request.CheckInfoReq;
import com.phanduy.aliexscrap.model.response.CheckInfoResponse;
import com.phanduy.aliexscrap.model.response.ResponseObj;
import com.phanduy.aliexscrap.api.ApiCall;
import com.phanduy.aliexscrap.utils.*;
import com.phanduy.aliexscrap.utils.ComputerIdentifier;
import com.phanduy.aliexscrap.utils.ExcelUtils;
import com.phanduy.aliexscrap.utils.DataUtils;
import com.phanduy.aliexscrap.utils.StringUtils;
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
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.prefs.Preferences;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

public class OldHomePanelController {
    @FXML private TextField amzProductTemplate1Field;
    @FXML private TextField outputField;
    @FXML private TextField configFileField;
    @FXML private TextField diskField;

    @FXML private Button browseOutput;
    @FXML private Button browseConfigFile;
    @FXML private Button browseTemplate1;
    @FXML private Button startButton;

    @FXML private Label downloadImageLabel;

    @FXML private TableView<CrawlTaskStatus> crawlTable;
    @FXML private TableColumn<CrawlTaskStatus, String> signatureCol;
    @FXML private TableColumn<CrawlTaskStatus, Number> pageCol;
    @FXML private TableColumn<CrawlTaskStatus, String> progressCol;

    private ObservableList<CrawlTaskStatus> crawlTaskList = FXCollections.observableArrayList();
    private Map<String, CrawlTaskStatus> crawlTaskMap = new ConcurrentHashMap<>();

    public static class CrawlTaskStatus {
        private final SimpleStringProperty signature;
        private final SimpleIntegerProperty pageNumber;
        private final SimpleStringProperty progress;

        public CrawlTaskStatus(String signature, int pageNumber, String progress) {
            this.signature = new SimpleStringProperty(signature);
            this.pageNumber = new SimpleIntegerProperty(pageNumber);
            this.progress = new SimpleStringProperty(progress);
        }
        public String getSignature() { return signature.get(); }
        public void setSignature(String value) { signature.set(value); }
        public SimpleStringProperty signatureProperty() { return signature; }
        public int getPageNumber() { return pageNumber.get(); }
        public void setPageNumber(int value) { pageNumber.set(value); }
        public SimpleIntegerProperty pageNumberProperty() { return pageNumber; }
        public String getProgress() { return progress.get(); }
        public void setProgress(String value) { progress.set(value); }
        public SimpleStringProperty progressProperty() { return progress; }
    }

    // Preferences API để cache setting
    private Preferences prefs;

    WebSocketClient client;

//    ProcessCrawlRapidNoCrawlThread processCrawlThread;

    @FXML
    public void initialize() throws URISyntaxException {
        prefs = Preferences.userNodeForPackage(OldHomePanelController.class);
        String version = VersionUtils.getAppVersionFromResource();
        prefs.put("Version", version);
        diskField.setText(ComputerIdentifier.getDiskSerialNumber());

        loadSettings();
        DownloadManager.getInstance().setListener(downloadListener);
        startButton.setVisible(false);
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
                            startButton.setVisible(true);
//                            initSocket();
                        }

                    } catch (Exception e) {
                        System.out.println("" + e.getMessage());
                        showInvalidInfo("Có lỗi xảy ra!");
                    }
                }
        );

        // --- WebSocket STOMP logic ---

        // TableView binding
        signatureCol.setCellValueFactory(cellData -> cellData.getValue().signatureProperty());
        pageCol.setCellValueFactory(cellData -> cellData.getValue().pageNumberProperty());
        progressCol.setCellValueFactory(cellData -> cellData.getValue().progressProperty());
        crawlTable.setItems(crawlTaskList);

        // Căn giữa nội dung các cell cho 3 cột
        Callback<TableColumn<CrawlTaskStatus, String>, TableCell<CrawlTaskStatus, String>> centerStringCellFactory = col -> new TableCell<CrawlTaskStatus, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item);
                setStyle("-fx-alignment: CENTER;");
            }
        };
        Callback<TableColumn<CrawlTaskStatus, Number>, TableCell<CrawlTaskStatus, Number>> centerNumberCellFactory = col -> new TableCell<CrawlTaskStatus, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.toString());
                setStyle("-fx-alignment: CENTER;");
            }
        };
        signatureCol.setCellFactory(centerStringCellFactory);
        pageCol.setCellFactory(centerNumberCellFactory);
        progressCol.setCellFactory(centerStringCellFactory);
    }

    private void initSocket() throws URISyntaxException {
        client = new WebSocketClient(new URI("ws://iamhere.vn:89/ws/websocket")) {
            private boolean isConnected = false;
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Duyuno Connected");
                // Gửi frame CONNECT STOMP
                String connectFrame = "CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\u0000";
                this.send(connectFrame);
                Platform.runLater(() -> {
                    startButton.setText("Stop");
                });
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Duyuno Received: " + message);
                if (message.startsWith("CONNECTED")) {
                    // Sau khi nhận CONNECTED, gửi SUBSCRIBE tới /topic/messages
                    String subscribeFrame = "SUBSCRIBE\nid:sub-0\ndestination:/topic/messages\n\n\u0000";
                    this.send(subscribeFrame);
                    isConnected = true;
                } else if (message.startsWith("MESSAGE")) {
                    // Xử lý message thực tế từ topic
                    System.out.println("Nhận message từ /topic/messages: " + message);
                    // Bóc tách phần JSON cuối cùng của message
                    int jsonStart = message.lastIndexOf("\n{\"");
                    if (jsonStart != -1) {
                        String json = message.substring(jsonStart + 1).trim();
                        try {
                            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                            String diskSerialNumber = obj.has("diskSerialNumber") ? obj.get("diskSerialNumber").getAsString() : null;
                            String signature = obj.has("signature") ? obj.get("signature").getAsString() : null;
                            String pageNumber = obj.has("pageNumber") ? obj.get("pageNumber").getAsString() : null;
                            if (ComputerIdentifier.getDiskSerialNumber().equals(diskSerialNumber) && signature != null && pageNumber != null) {
                                startCrawling(signature, pageNumber);
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi parse JSON từ message: " + e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Duyuno Closed: " + reason);
                Platform.runLater(() -> {
                    startButton.setText("Start");
                });
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    startButton.setText("Start");
                });
            }
        };
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
    private void onStart() throws URISyntaxException {
        if (client != null && client.isOpen()) {
            client.close();
            client = null;
            CrawlExecutor.shutdown();
            startButton.setText("Start");
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
        initSocket();
        client.connect();
    }

    private void startCrawling(String signature, String pageNumber) {
        downloadImageLabel.setText("");
        DownloadManager.getInstance().clearData();

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

        String templateFilePath = amzProductTemplate1Field.getText();
        boolean isOldTemplate = ExcelUtils.isOldTemplate(templateFilePath);
        if (isOldTemplate) {
            inputDataConfig.params.put("template", "");
        } else {
            inputDataConfig.params.put("template", "NewTemplate");
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

        String key = signature + "_" + pageNumber;
        CrawlTaskStatus status = new CrawlTaskStatus(signature, Integer.parseInt(pageNumber), "Waiting");
        crawlTaskMap.put(key, status);
        Platform.runLater(() -> crawlTaskList.add(status));

        try {
            CrawlExecutor.executeThread(
                    new ProcessCrawlRapidNoCrawlThread(
                            storeOrderInfo,
                            inputDataConfig.params,
                            signature,
                            pageNumber,
                            crawlProcessListener
                    )
            );
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
        public void onPushState(String signature, String pageNumber, String status) {
            String key = signature + "_" + pageNumber;
            CrawlTaskStatus taskStatus = crawlTaskMap.get(key);
            if (taskStatus != null) {
                Platform.runLater(() -> {
                    taskStatus.setProgress(status);
                    taskStatus.setSignature(signature);
                    taskStatus.setPageNumber(Integer.parseInt(pageNumber));
                });
            }
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
