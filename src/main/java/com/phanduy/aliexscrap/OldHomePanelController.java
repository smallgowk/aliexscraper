package com.phanduy.aliexscrap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.controller.DownloadManager;
import com.phanduy.aliexscrap.controller.SocketManager;
import com.phanduy.aliexscrap.controller.inputprocess.InputDataConfig;
import com.phanduy.aliexscrap.controller.inputprocess.SnakeReadOrderInfoSvs;
import com.phanduy.aliexscrap.controller.thread.CrawlExecutor;
import com.phanduy.aliexscrap.controller.thread.ExportFileExecutor;
import com.phanduy.aliexscrap.controller.thread.ProcessCrawlRapidNoCrawlThread;
import com.phanduy.aliexscrap.interfaces.CrawlProcessListener;
import com.phanduy.aliexscrap.interfaces.DownloadListener;
import com.phanduy.aliexscrap.model.ProductPage;
import com.phanduy.aliexscrap.model.aliex.store.inputdata.BaseStoreOrderInfo;
import com.phanduy.aliexscrap.model.request.CheckInfoReq;
import com.phanduy.aliexscrap.model.request.UpdateCrawlSignatureReq;
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
import javafx.scene.image.ImageView;
import javafx.scene.control.Tooltip;
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
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.ArrayList;
import java.util.Comparator;
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
import javafx.scene.layout.Region;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import org.jetbrains.annotations.Nullable;

import static com.phanduy.aliexscrap.api.ApiClient.SOCKET_URL;

import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.util.stream.Stream;

public class OldHomePanelController {
    // Static reference để lưu trữ instance hiện tại
    private static OldHomePanelController instance;
    
    @FXML private TextField amzProductTemplate1Field;
    @FXML private TextField outputField;
    @FXML private TextField configFileField;
    @FXML private TextField diskField;
    @FXML private TextField productIdsField;

    @FXML private Button browseOutput;
    @FXML private Button browseConfigFile;
    @FXML private Button browseTemplate1;
    @FXML private Button startButton;
    @FXML private Button clearButton;
    @FXML private Button browseproductIdsFile;
    @FXML private Button fetchButton;
    @FXML private Button syncCache;

    @FXML private Label remainRequest;
    @FXML private Label remainRequestLabel;
    @FXML private Label downloadImageLabel;
    @FXML private Label statusLabel;
    @FXML private ImageView socketStatusIcon;

    @FXML private TableView<CrawlTaskStatus> crawlTable;
    @FXML private TableColumn<CrawlTaskStatus, String> signatureCol;
    @FXML private TableColumn<CrawlTaskStatus, Number> pageCol;
    @FXML private TableColumn<CrawlTaskStatus, String> progressCol;

    @FXML private Region leftSpacer;
    @FXML private Region rightSpacer;

    public static final String START_LABEL = "Start";
    public static final String STOP_LABEL = "Stop";

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

    private SocketManager socketManager;
    private boolean isRunning = false;
    
    // Socket status icons - chỉ có 2 trạng thái
    private static final String ICON_CONNECTED_PATH = "/com/phanduy/aliexscrap/icons/socket_connected.png";
    private static final String ICON_CONNECTING_PATH = "/com/phanduy/aliexscrap/icons/socket_connecting.png";

//    ProcessCrawlRapidNoCrawlThread processCrawlThread;

    @FXML
    public void initialize() throws URISyntaxException {
        // Gán instance hiện tại
        instance = this;
        
        prefs = Preferences.userNodeForPackage(OldHomePanelController.class);
        String version = VersionUtils.getAppVersionFromResource();
        prefs.put("Version", version);
        diskField.setText(ComputerIdentifier.getDiskSerialNumber());

        loadSettings();
        DownloadManager.getInstance().setListener(downloadListener);
        
        // Khởi tạo SocketManager
        initSocketManager();
        
        // Hiển thị trạng thái connecting ngay từ đầu
        updateSocketStatusIcon();
        
        startButton.setDisable(true);
        fetchButton.setDisable(true);
        syncCache.setDisable(true);
        clearButton.setDisable(true);
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
                        if (checkInfoResponse == null) {
                            Platform.runLater(() -> {
                                updateSocketStatus();
                            });
                        } else {
                            Platform.runLater(() -> {
                                int code = checkInfoResponse.getResultCode();
                                if (code != 1) {
                                    switch (code) {
                                        case CheckInfoResponse.SERIAL_INVALID:
                                            statusLabel.setVisible(true);
                                            statusLabel.setText("Máy tính cài đặt không hợp lệ. Liên hệ 0972071089 để được xác thực!");
                                            break;
                                        case CheckInfoResponse.TIME_LIMIT:
                                            statusLabel.setVisible(true);
                                            statusLabel.setText("Máy tính đã hết thời gian sử dụng. Liên hệ 0972071089 để được xử lý!");
                                            break;
                                        case CheckInfoResponse.PRODUCT_LIMIT:
                                            statusLabel.setVisible(true);
                                            statusLabel.setText("Gói sử dụng đã hết lưu lượng sử dụng. Liên hệ 0972071089 để được xử lý!");
                                            break;
                                        case CheckInfoResponse.PRODUCT_LIMIT_IN_DAY:
                                            statusLabel.setVisible(true);
                                            statusLabel.setText("Quá hạn request cho một ngày!");
                                            break;
                                        case CheckInfoResponse.VERSION_INVALID:
                                            prefs.putBoolean("Latest", checkInfoResponse.isLatest());
                                            prefs.put("LatestVersion", checkInfoResponse.getLatestVersion());
                                            showInvalidVersion(
                                                    "Vui lòng cập nhật version mới để sử dụng!",
                                                    checkInfoResponse.getLatestVersion()
                                            );
                                            break;
                                        default:
                                            showInvalidInfo("Server error!. Liên hệ 0972071089 để được xử lý!");
                                    }
                                } else {
                                    prefs.putBoolean("Latest", checkInfoResponse.isLatest());
                                    prefs.put("LatestVersion", checkInfoResponse.getLatestVersion());
                                    prefs.put("owner", checkInfoResponse.getOwner());

                                    remainRequest.setText("" + checkInfoResponse.getRemainRequest());
                                    remainRequest.setVisible(true);
                                    remainRequestLabel.setVisible(true);

                                    CrawlExecutor.initExecutor(checkInfoResponse.getMaxThreads());
                                    ExportFileExecutor.initExecutor(1);

                                    try {
                                        socketManager.connect();
                                        // Bắt đầu auto reconnect
                                        socketManager.startAutoReconnect();
                                    } catch (Exception e) {
                                        System.out.println("Error" + e.getMessage());
                                        Platform.runLater(() -> {
                                            updateSocketStatus();
                                        });
                                    }


                                }
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Error" + e.getMessage());
//                        showInvalidInfo("Có lỗi xảy ra!");
                        Platform.runLater(() -> {
                            updateSocketStatus();
                        });
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

        // Đảm bảo layout statusLabel đúng khi khởi tạo
        updateStatusLabelLayout();
        // Theo dõi thay đổi visibility của startButton
        startButton.visibleProperty().addListener((obs, oldVal, newVal) -> updateStatusLabelLayout());

        // Listener để disable clearButton khi table rỗng
        crawlTaskList.addListener((javafx.collections.ListChangeListener<CrawlTaskStatus>) change -> {
            if (crawlTaskList.isEmpty()) {
                clearButton.setDisable(true);
            }
        });
    }

    private void initSocketManager() {
        socketManager = new SocketManager(SOCKET_URL, new SocketManager.SocketCallback() {
            @Override
            public void onConnectionEstablished() {
                System.out.println("WebSocket connected successfully");
                setupWebSocketHandlers();
                
                Platform.runLater(() -> {
                    startButton.setDisable(false);
                    updateSocketStatus();
                });
            }
            
            @Override
            public void onConnectionLost() {
                System.out.println("WebSocket connection lost, attempting to reconnect...");
                Platform.runLater(() -> {
                    if (isRunning) {
                        updateSocketStatus();
                    }
                });
            }
            
            @Override
            public void onReconnectAttempt(int attemptNumber) {
                System.out.println("Reconnect attempt #" + attemptNumber);
                Platform.runLater(() -> {
                    if (isRunning) {
                        updateSocketStatus();
                    }
                });
            }
            
            @Override
            public void onReconnectFailed(String reason) {
                System.out.println("Reconnect failed: " + reason);
                Platform.runLater(() -> {
                    updateSocketStatus();
                });
            }
            
            @Override
            public void onMessage(String message) {
                // Xử lý message từ WebSocket
                handleWebSocketMessage(message);
            }
            
            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket error: " + ex.getMessage());
                Platform.runLater(() -> {
                    updateSocketStatus();
                });
            }
        });
    }

    private void setupWebSocketHandlers() {
        // Gửi frame CONNECT STOMP
        String connectFrame = "CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\u0000";
        socketManager.send(connectFrame);
    }
    
    
    private void handleWebSocketMessage(String message) {
        System.out.println("Duyuno Received: " + message);
        if (message.startsWith("CONNECTED")) {
            // Sau khi nhận CONNECTED, gửi SUBSCRIBE tới /topic/messages
            String subscribeFrame = "SUBSCRIBE\nid:sub-0\ndestination:/topic/messages\n\n\u0000";
            socketManager.send(subscribeFrame);

            String subscribeBroadcastFrame = "SUBSCRIBE\nid:sub-broadcast\ndestination:/topic/broadcast\n\n\u0000";
            socketManager.send(subscribeBroadcastFrame);

            // Subscribe để nhận response đăng ký
            String subscribeRegistrationFrame = "SUBSCRIBE\nid:sub-registration\ndestination:/queue/registration\n\n\u0000";
            socketManager.send(subscribeRegistrationFrame);

            // Gửi đăng ký machine với machineId và linkSheetId
            Platform.runLater(() -> {
                updateSocketStatus();
            });
        } else if (message.startsWith("MESSAGE")) {
            // Xử lý message thực tế từ topic
            System.out.println("Nhận message từ /topic/messages: " + message);
            // Bóc tách phần JSON cuối cùng của message
            int jsonStart = message.lastIndexOf("\n{\"");
            if (jsonStart != -1) {
                String json = message.substring(jsonStart + 1).trim();
                try {
                    JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                    if (obj.has("action")) {
                        String action = obj.get("action").getAsString();
                        if (action.equalsIgnoreCase("CRAWLING") && isRunning) {
                            String diskSerialNumber = obj.has("diskSerialNumber") ? obj.get("diskSerialNumber").getAsString() : null;
                            String signature = obj.has("signature") ? obj.get("signature").getAsString() : null;
                            String linkSheetId = obj.has("linkSheetId") ? obj.get("linkSheetId").getAsString() : null;
//                                    String linkSheetName = obj.has("linkSheetName") ? obj.get("linkSheetName").getAsString() : null;
                            String pageNumber = obj.has("pageNumber") ? obj.get("pageNumber").getAsString() : null;
                            ArrayList<String> listProducts = parseListProducts(obj);
                            prefs.put("linkSheetId", linkSheetId);

                            String machineId = ComputerIdentifier.getDiskSerialNumber(); // Thay bằng ID thực tế của máy

                            String registerFrame = "SEND\ndestination:/app/register\ncontent-type:application/json\n\n" +
                                    "{\"machineId\":\"" + machineId + "\",\"linkSheetId\":\"" + linkSheetId + "\"}\u0000";
                            socketManager.send(registerFrame);

                            if (ComputerIdentifier.getDiskSerialNumber().equals(diskSerialNumber) && signature != null && pageNumber != null) {
                                startCrawling(signature, linkSheetId, pageNumber, listProducts, true);
                            }
                        } else if (action.equalsIgnoreCase("UPDATE_REQUEST")){
                            String owner = obj.has("owner") ? obj.get("owner").getAsString() : null;
                            if (!StringUtils.isEmpty(owner) && owner.equalsIgnoreCase(prefs.get("owner", null))) {
                                String remainRequestValue = obj.has("remainRequest") ? obj.get("remainRequest").getAsString() : null;
                                try {
                                    long value = Long.parseLong(remainRequestValue);
                                    String current = remainRequest.getText();
                                    long currentValue = Long.parseLong(current);
                                    if (currentValue > value) {
                                        Platform.runLater(() -> {
                                                    remainRequest.setText(value >= 0 ? remainRequestValue : "0");
                                                }
                                        );
                                    }
                                } catch (NumberFormatException ex) {
                                    System.out.println(message + ": " + ex.getMessage());
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("Lỗi parse JSON từ message: " + e.getMessage());
                }
            }
        }
    }
    

    private @Nullable String extractLastJsonObject(String message) {
        if (message == null) return null;
        int start = message.lastIndexOf('{');
        if (start < 0) return null;
        // Cắt từ dấu '{' cuối cùng tới hết chuỗi
        return message.substring(start).trim();
    }

    private @Nullable String optString(JsonObject o, String key) {
        if (o == null || key == null || !o.has(key) || o.get(key).isJsonNull()) return null;
        try { return o.get(key).getAsString(); } catch (Exception ignore) { return null; }
    }

    private ArrayList<String> parseListProducts(JsonObject obj) {
        ArrayList<String> out = new ArrayList<>();
        if (obj == null || !obj.has("listProducts") || obj.get("listProducts").isJsonNull()) return out;

        JsonElement lp = obj.get("listProducts");
        try {
            if (lp.isJsonArray()) {
                for (JsonElement el : lp.getAsJsonArray()) {
                    if (!el.isJsonNull()) out.add(el.getAsString());
                }
                return out;
            }
            // Trường hợp server gửi dưới dạng String: "[...]" hoặc "a, b, c"
            if (lp.isJsonPrimitive()) {
                String s = lp.getAsString();
                if (s != null) {
                    s = s.trim();
                    // Thử parse như JSON array string
                    if (s.startsWith("[") && s.endsWith("]")) {
                        try {
                            JsonArray arr = JsonParser.parseString(s).getAsJsonArray();
                            for (JsonElement el : arr) {
                                if (!el.isJsonNull()) out.add(el.getAsString());
                            }
                            return out;
                        } catch (Exception ignore) {
                            // rơi xuống split tay
                        }
                        // fallback: strip [] rồi split
                        s = s.substring(1, s.length() - 1);
                    }
                    // split theo dấu phẩy, trim từng phần tử
                    for (String part : s.split(",")) {
                        String v = part.trim();
                        if (!v.isEmpty()) {
                            // bỏ dấu ngoặc kép nếu có
                            if ((v.startsWith("\"") && v.endsWith("\"")) || (v.startsWith("'") && v.endsWith("'"))) {
                                v = v.substring(1, v.length() - 1);
                            }
                            out.add(v);
                        }
                    }
                }
            }
        } catch (Exception ignore) { /* để out rỗng */ }
        return out;
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
                        // Auto-update logic: tải về và chạy file installer mới, có progress dialog
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
                    } else {
                        Platform.exit();
                    }
                }
        );
    }

    // Task download có progress
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

    @FXML
    private void onOpenProductIdsFile() {
        FileOpener.openFileOrFolder(productIdsField.getText());
    }

    @FXML
    private void onOpenConfigFile() {
        FileOpener.openFileOrFolder(configFileField.getText());
    }

    @FXML
    private void onClearButton() {
        crawlTaskList.clear();
        crawlTaskMap.clear();
        clearButton.setDisable(true);
        CrawlExecutor.shutdownNow();
    }

    @FXML
    private void onClearCacheButton() {
        clearDirectoryKeepRoot(Configs.CACHE_PATH);
        AlertUtil.showAlert("Xóa cache", "Cache đã xóa!");
    }

    public boolean clearDirectoryKeepRoot(String dirPath) {
        Path root = Paths.get(dirPath);
        if (Files.notExists(root)) return true; // không có gì để xóa

        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(p -> !p.equals(root))                 // bỏ qua thư mục gốc
                    .sorted(Comparator.reverseOrder())           // xóa file trước, rồi đến folder
                    .forEach(p -> {
                        try {
                            tryDelete(p);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void tryDelete(Path p) throws IOException {
        try {
            Files.delete(p);
        } catch (IOException first) {
            // Trên Windows, file/folder read-only sẽ không xóa được -> bỏ cờ readonly rồi xóa lại
            DosFileAttributeView view = Files.getFileAttributeView(p, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            if (view != null) {
                try { view.setReadOnly(false); } catch (IOException ignore) {}
            }
            Files.delete(p);
        }
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
		try {
			File init = getFallbackInitialDirectory(folderPath);
			directoryChooser.setInitialDirectory(init);
		} catch (Exception ignore) { }

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
		try {
			File init = getFallbackInitialDirectory(folderPath);
			fileChooser.setInitialDirectory(init);
		} catch (Exception ignore) { }

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
    private void onSyncCache() {
        ArrayList<ProductPage> pages = Utils.loadCacheData();
        if (pages != null && !pages.isEmpty()) {
            // Sort by signature first, then by pageNumber
            pages.sort((p1, p2) -> {
                // First compare by signature
                int signatureCompare = p1.getSignature().compareTo(p2.getSignature());
                if (signatureCompare != 0) {
                    return signatureCompare;
                }
                // If signatures are equal, compare by pageNumber
                return Integer.compare(p1.getPageNumberInt(), p2.getPageNumberInt());
            });
            
            String linkSheetId = prefs.get("linkSheetId", null);
            if (!StringUtils.isEmpty(linkSheetId)) {
                for (ProductPage productPage : pages) {
                    startCrawling(productPage.getSignature(), linkSheetId, productPage.getPageNumber(), productPage.getIds(), false);
                }
            }
        } else {
            AlertUtil.showAlert("Cache Data", "Không có dữ liệu cache!");
        }
    }

    @FXML
    private void onFetchProductIdsFile() {
        String productIdsFilePath = productIdsField.getText();
        
        if (StringUtils.isEmpty(productIdsFilePath)) {
            AlertUtil.showError("", "Product IDs file not selected!");
            return;
        }
        
        File checkFile = new File(productIdsFilePath);
        if (!checkFile.exists()) {
            AlertUtil.showError("", "Product IDs file does not exist!");
            return;
        }
        
        try {
            // Sử dụng SimpleExcelReader để đọc file
            ArrayList<ProductPage> pages = ExcelReader.readProductIdsFromExcel(productIdsFilePath);
            String fileName = new File(productIdsFilePath).getName().replaceFirst("[.][^.]+$", "");
            
            if (pages.isEmpty()) {
                AlertUtil.showAlert("Info", "No product IDs found in the file!");
                return;
            }

            for (ProductPage productPage : pages) {
                startCrawling("Local_" + fileName, null, productPage.getPageNumber(), productPage.getIds(), false);
            }
        } catch (Exception e) {
            AlertUtil.showError("Error", "Failed to read product IDs file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onBrowserProductIdsFile() {
        String currentPath = productIdsField.getText();
        String folderPath = null;
        if (currentPath.isEmpty()) {
            folderPath = ".";
        } else {
            folderPath = currentPath.substring(0, currentPath.lastIndexOf("\\"));
        }
        FileChooser fileChooser = new FileChooser();
		try {
			File init = getFallbackInitialDirectory(folderPath);
			fileChooser.setInitialDirectory(init);
		} catch (Exception ignore) { }

        // Set the title for the FileChooser dialog
        fileChooser.setTitle("Select Excel File");

        // Restrict the selection to Excel files
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx")
        );

        // Show the dialog and get the selected file
        Stage stage = (Stage) browseproductIdsFile.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Process the selected file
        if (selectedFile != null) {
            productIdsField.setText(selectedFile.getAbsolutePath());
            prefs.put("productIdsField", selectedFile.getAbsolutePath());
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
		try {
			File init = getFallbackInitialDirectory(folderPath);
			fileChooser.setInitialDirectory(init);
		} catch (Exception ignore) { }

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

    private File getFallbackInitialDirectory(String preferredPath) {
        ArrayList<String> candidates = new ArrayList<>();
        if (preferredPath != null && !preferredPath.isEmpty()) {
            candidates.add(preferredPath);
        }
        String userHome = System.getProperty("user.home");
        candidates.add(userHome + File.separator + "Desktop");
        candidates.add(userHome + File.separator + "Documents");
        candidates.add(userHome);
        for (String p : candidates) {
            try {
                if (p == null) continue;
                File f = new File(p);
                if (f.exists() && f.isDirectory() && f.canRead()) {
                    return f;
                }
            } catch (SecurityException ignore) { }
        }
        return new File(userHome);
    }

    private void saveSettings() {
        prefs.put("outputField", outputField.getText());
        prefs.put("configFileField", configFileField.getText());

        System.out.println("Settings Saved!");
    }

    private void loadSettings() {
        outputField.setText(prefs.get("outputField", ""));
        configFileField.setText(prefs.get("configFileField", ""));
        productIdsField.setText(prefs.get("productIdsField", ""));
        amzProductTemplate1Field.setText(prefs.get("amzProductTemplate1Field", ""));
        System.out.println("Settings Loaded!");
    }

    // Method public để đóng socket từ bên ngoài
    public void closeSocketConnection() {
        if (socketManager != null) {
            socketManager.disconnect();
        }
    }
    
    // Method để bật/tắt auto reconnect
    public void setAutoReconnect(boolean enabled) {
        if (socketManager != null) {
            if (enabled) {
                socketManager.startAutoReconnect();
            } else {
                socketManager.stopAutoReconnect();
            }
        }
    }
    
    // Method để kiểm tra trạng thái auto reconnect
    public boolean isAutoReconnectEnabled() {
        return socketManager != null && socketManager.isReconnecting();
    }
    
    // Method để lấy thông tin reconnect
    public String getReconnectStatus() {
        if (socketManager == null) {
            return "Socket not initialized";
        }
        return socketManager.getConnectionStatus();
    }
    
    // Method để cập nhật socket status trên UI
    public void updateSocketStatus() {
        Platform.runLater(() -> {
            updateSocketStatusIcon();
        });
    }
    
    // Method để cập nhật icon socket status - chỉ có 2 trạng thái
    private void updateSocketStatusIcon() {
        if (socketManager == null) {
            setSocketStatusIcon(ICON_CONNECTING_PATH, "Socket initializing...");
            return;
        }
        
        if (socketManager.isConnected()) {
            setSocketStatusIcon(ICON_CONNECTED_PATH, "Socket connected");
        } else {
            // Tất cả các trạng thái khác đều hiển thị connecting
            String tooltip = "Socket connecting...";
            if (socketManager.isReconnecting()) {
                tooltip = "Socket reconnecting... (attempt " + socketManager.getReconnectAttempts() + ")";
            }
            setSocketStatusIcon(ICON_CONNECTING_PATH, tooltip);
        }
    }
    
    // Method để set icon và tooltip
    private void setSocketStatusIcon(String iconPath, String tooltip) {
        try {
            // Load image từ resources
            Image image = new Image(getClass().getResourceAsStream(iconPath));
            socketStatusIcon.setImage(image);
            socketStatusIcon.setFitWidth(12);
            socketStatusIcon.setFitHeight(12);
            socketStatusIcon.setPreserveRatio(true);
            socketStatusIcon.setSmooth(true);
            
            // Set tooltip
            Tooltip.install(socketStatusIcon, new Tooltip(tooltip));
            
        } catch (Exception e) {
            System.err.println("Error loading icon: " + iconPath + " - " + e.getMessage());
            // Fallback: sử dụng text nếu không load được icon
            socketStatusIcon.setImage(null);
            // Tạm thời sử dụng text fallback
            System.out.println("Using text fallback for socket status: " + tooltip);
        }
    }
    
    // Method để force reset reconnecting flag (for debugging)
    public void forceResetReconnecting() {
        if (socketManager != null) {
            socketManager.forceResetReconnecting();
        }
    }
    
    // Method static để lấy instance hiện tại
    public static OldHomePanelController getInstance() {
        return instance;
    }

    @FXML
    private void onStart() throws URISyntaxException {
//        if (client != null && client.isOpen()) {
//            client.close();
//            client = null;
//            CrawlExecutor.shutdownNow();
//            startButton.setText("Listen");
//            fetchButton.setDisable(true);
//            syncCache.setDisable(true);
//            // Enable clearButton nếu table có data
//            if (!crawlTaskList.isEmpty()) {
//                clearButton.setDisable(false);
//            }
//            return;
//        }
        if (isRunning) {
            isRunning = false;
            startButton.setText(START_LABEL);
            fetchButton.setDisable(true);
            syncCache.setDisable(true);
            updateSocketStatus();
            CrawlExecutor.shutdownNow();
            
            // Dừng auto reconnect khi stop
            if (socketManager != null) {
                socketManager.stopAutoReconnect();
            }
            
            if (!crawlTaskList.isEmpty()) {
                clearButton.setDisable(false);
            }
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

        startButton.setText(STOP_LABEL);
        statusLabel.setVisible(true);
        statusLabel.setText("Chờ nhận tín hiệu từ extension hoặc run local cache!");
        updateSocketStatus();
        fetchButton.setDisable(false);
        syncCache.setDisable(false);
        isRunning = true;
        
        // Bật auto reconnect khi start
        setAutoReconnect(true);
//        initSocket();
//        client.connect();
    }

    private void startCrawling(String signature, String linkSheetId, String pageNumber, ArrayList<String> listProducts, boolean fromRemote) {
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
        if (!crawlTaskMap.containsKey(key)) {
            CrawlTaskStatus status = new CrawlTaskStatus(signature, Integer.parseInt(pageNumber), "Waiting");
            crawlTaskMap.put(key, status);
            Platform.runLater(() -> crawlTaskList.add(status));
            if (fromRemote) {
                Utils.saveProducts(signature, pageNumber, listProducts);
            }
        }

        try {
            CrawlExecutor.executeThread(
                    new ProcessCrawlRapidNoCrawlThread(
                            storeOrderInfo,
                            inputDataConfig.params,
                            signature,
                            linkSheetId,
                            pageNumber,
                            crawlProcessListener,
                            listProducts,
                            fromRemote
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

        @Override
        public void onStop(String result) {
            if (socketManager != null) {
                socketManager.disconnect();
                CrawlExecutor.shutdownNow();
            }

            Platform.runLater(() -> {
                startButton.setText(START_LABEL);
                showInvalidInfo(result);
            });

        }

        @Override
        public void updateRemainRequest(int remainRequestCount) {
//            Platform.runLater(() -> {
//                remainRequest.setText("" + remainRequestCount);
//            });
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

    private void updateStatusLabelLayout() {
        boolean startVisible = startButton.isVisible();
        leftSpacer.setVisible(true);
        rightSpacer.setVisible(startVisible);
        leftSpacer.setManaged(true);
        rightSpacer.setManaged(startVisible);
    }
}
