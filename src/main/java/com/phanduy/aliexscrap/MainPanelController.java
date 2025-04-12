package com.phanduy.aliexscrap;

import com.google.gson.Gson;
import com.phanduy.aliexscrap.api.ApiClient;
import com.phanduy.aliexscrap.api.ApiResponse;
import com.phanduy.aliexscrap.api.ApiService;
import com.phanduy.aliexscrap.model.Category;
import com.phanduy.aliexscrap.model.SettingInfo;
import com.phanduy.aliexscrap.model.StoreInfo;
import com.phanduy.aliexscrap.model.SubCategory;
import com.phanduy.aliexscrap.model.request.GetItemsByCategoryReq;
import com.phanduy.aliexscrap.model.response.GetItemsByCategoryResponseData;
import com.phanduy.aliexscrap.model.response.StoreInfoResponseData;
import com.phanduy.aliexscrap.utils.ComputerIdentifier;
import com.phanduy.aliexscrap.utils.ExcelReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainPanelController {
    @FXML private TextField storeIdField;
    @FXML private ComboBox<String> languageComboBox;
    @FXML private Label processingLabel;
    @FXML private TextArea logArea;

    private final ApiService apiService = ApiClient.getClient().create(ApiService.class);

    private Preferences prefs;

    @FXML
    private void initialize() {
        prefs = Preferences.userNodeForPackage(HomePanelController.class);
        // Đặt giá trị mặc định cho ComboBox
        languageComboBox.getItems().addAll("English", "Arabic");
        String savedLanguage = prefs.get("language", "English");
        languageComboBox.setValue(savedLanguage);

        languageComboBox.setOnAction(event -> {
            String selectedLanguage = languageComboBox.getValue();
            prefs.put("language", selectedLanguage);
        });

        storeIdField.setText(prefs.get("storeId", ""));

        loadSettings();

        prefs.addPreferenceChangeListener(evt -> Platform.runLater(this::updateSettings));
    }

    private void loadSettings() {
        String priceRate = prefs.get("priceRate", "1.0");
        String maxRow = prefs.get("maxRow", "100");
        String vpsIp = prefs.get("vpsIp", "192.168.1.1");
        String otherMethods = prefs.get("otherMethods", "");
        String amzProductTemplate1Field = prefs.get("amzProductTemplate1Field", "");
        String amzProductTemplate2Field = prefs.get("amzProductTemplate2Field", "");
        String outputField = prefs.get("outputField", "");
        String configFileField = prefs.get("configFileField", "");
        String selectTemplate = prefs.get("selectTemplate", "Template 1");
        boolean shipUs = prefs.getBoolean("shipUs", false);
        boolean stopByNonShip = prefs.getBoolean("stopByNonShip", false);
        boolean autoDownloadImage = prefs.getBoolean("autoDownloadImage", false);
        boolean ePacket = prefs.getBoolean("ePacket", false);
        boolean aliStandard = prefs.getBoolean("aliStandard", false);
        boolean aliDirect = prefs.getBoolean("aliDirect", false);
        boolean ups = prefs.getBoolean("ups", false);

        System.out.println("Settings Loaded: ");
        System.out.println("Price Rate: " + priceRate);
        System.out.println("maxRow: " + maxRow);
        System.out.println("vpsIp: " + vpsIp);
        System.out.println("otherMethods: " + otherMethods);
        System.out.println("shipUs: " + shipUs);
        System.out.println("stopByNonShip: " + stopByNonShip);
        System.out.println("autoDownloadImage: " + autoDownloadImage);
        System.out.println("ePacket: " + ePacket);
        System.out.println("aliStandard: " + aliStandard);
        System.out.println("aliDirect: " + aliDirect);
        System.out.println("ups: " + ups);
    }

    private void updateSettings() {
        // Cập nhật các giá trị từ Preferences vào giao diện
        String priceRate = prefs.get("priceRate", "1.0");
        String maxRow = prefs.get("maxRow", "100");
        String vpsIp = prefs.get("vpsIp", "192.168.1.1");
        String otherMethods = prefs.get("otherMethods", "");
        String amzProductTemplate1Field = prefs.get("amzProductTemplate1Field", "");
        String amzProductTemplate2Field = prefs.get("amzProductTemplate2Field", "");
        String outputField = prefs.get("outputField", "");
        String configFileField = prefs.get("configFileField", "");
        String selectTemplate = prefs.get("selectTemplate", "Template 1");
        boolean shipUs = prefs.getBoolean("shipUs", false);
        boolean stopByNonShip = prefs.getBoolean("stopByNonShip", false);
        boolean autoDownloadImage = prefs.getBoolean("autoDownloadImage", false);
        boolean ePacket = prefs.getBoolean("ePacket", false);
        boolean aliStandard = prefs.getBoolean("aliStandard", false);
        boolean aliDirect = prefs.getBoolean("aliDirect", false);
        boolean ups = prefs.getBoolean("ups", false);

        // In ra log để kiểm tra
        System.out.println("Settings Updated:");
        System.out.println("Price Rate: " + priceRate);
        System.out.println("Max Row: " + maxRow);
        System.out.println("VPS IP: " + vpsIp);
        System.out.println("Other Methods: " + otherMethods);
        System.out.println("ShipUS: " + shipUs);
        System.out.println("Stop by non-ship: " + stopByNonShip);
        System.out.println("Auto download Image: " + autoDownloadImage);
        System.out.println("ePacket: " + ePacket);
        System.out.println("AliStandard: " + aliStandard);
        System.out.println("AliDirect: " + aliDirect);
        System.out.println("UPS: " + ups);
    }


    @FXML
    private void onCrawlClick() {
        String storeId = storeIdField.getText();
        if (storeId.isEmpty()) {
            logMessage("Store ID cannot be empty!");
            return;
        }

        prefs.put("storeId", storeId);

        String configFilePath = prefs.get("configFileField", "");
        System.out.println(configFilePath);

        SettingInfo settingInfo = null;
        try {
            settingInfo = ExcelReader.readExcelFile(configFilePath);
            System.out.println(settingInfo.getData());
            System.out.println(settingInfo.getSettings());
        } catch (IOException e) {
            System.out.println(e);
        }


//        logMessage("Starting get data for Store ID: " + storeId);
//        processingLabel.setVisible(true);

//        GetStoreInfoRequest request = new GetStoreInfoRequest(storeId, ComputerIdentifier.getDiskSerialNumber());

//        apiService.getFullStoreInfo(request).enqueue(new Callback<ApiResponse<StoreInfoResponseData>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<StoreInfoResponseData>> call, Response<ApiResponse<StoreInfoResponseData>> response) {
//                Platform.runLater(() -> {
//                    processingLabel.setVisible(false);
//                    if (response.isSuccessful() && response.body() != null) {
//                        ApiResponse<StoreInfoResponseData> apiResponse = response.body();
//                        if (apiResponse.isSuccess()) {
//                            processStoreInfo(storeId, apiResponse.getData());
//                        } else {
//                            logMessage("Failed: " + apiResponse.getMessage());
//                        }
//                    } else {
//                        logMessage("Error: Server error!");
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<StoreInfoResponseData>> call, Throwable t) {
//                Platform.runLater(() -> {
//                    processingLabel.setVisible(false);
//                    logMessage("Request failed: " + t.getMessage());
//                });
//            }
//        });
    }

    private void processStoreInfo(String storeId, StoreInfoResponseData storeInfo) {
        Gson gson = new Gson();
        System.out.println("storeInfo: " + gson.toJson(storeInfo));
        storeInfo.categoryList.forEach(category ->
                processCategory(storeId, storeInfo.storeInfo, category)
        );
    }

    private void processCategory(String storeId, StoreInfo storeInfo, Category category) {
        if (category.getSubList() == null) {
            processCategoryAndSubCategory(storeId, storeInfo, category, null);
        } else {
            category.getSubList().forEach(subCategory ->
                    processCategoryAndSubCategory(storeId, storeInfo, category, subCategory)
            );
        }
    }

    private void processCategoryAndSubCategory(String storeId, StoreInfo storeInfo, Category category, SubCategory subCategory) {
        GetItemsByCategoryReq request = new GetItemsByCategoryReq(
                ComputerIdentifier.getDiskSerialNumber(),
                storeId,
                category.getId(),
                subCategory != null ? subCategory.getId() : null,
                storeInfo.getWidgetId(),
                storeInfo.getModuleName(),
                category.getIndex(),
                subCategory != null ? subCategory.getIndex() : -1
        );

        Gson gson = new Gson();
        System.out.println("request: " + gson.toJson(request));

        apiService.getItemsByCategory(request).enqueue(new Callback<ApiResponse<GetItemsByCategoryResponseData>>() {
            @Override
            public void onResponse(Call<ApiResponse<GetItemsByCategoryResponseData>> call, Response<ApiResponse<GetItemsByCategoryResponseData>> response) {
                Platform.runLater(() -> {
                    processingLabel.setVisible(false);
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse<GetItemsByCategoryResponseData> apiResponse = response.body();
                        if (apiResponse.isSuccess()) {
                            System.out.println("apiResponse: " + gson.toJson(apiResponse.getData()));
                        } else {
                            logMessage("Failed: " + apiResponse.getMessage());
                        }
                    } else {
                        logMessage("Error: Server error!");
                    }
                });
            }

            @Override
            public void onFailure(Call<ApiResponse<GetItemsByCategoryResponseData>> call, Throwable t) {
                Platform.runLater(() -> {
                    processingLabel.setVisible(false);
                    logMessage("Request failed: " + t.getMessage());
                });
            }
        });
    }

    private void logMessage(String message) {
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }

    @FXML
    private void showMainPanel() {
    }

    @FXML
    private void showSettingPanel() {
        try {
            // Load HomePanel.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/phanduy/aliexscrap/HomePanel.fxml"));
            Parent root = loader.load();


            // Tạo cửa sổ mới (Stage)
            Stage settingStage = new Stage();
            settingStage.setTitle("Settings");
            settingStage.setScene(new Scene(root));

            // Căn chỉnh kích thước cửa sổ
            settingStage.setMinWidth(600);
            settingStage.setMinHeight(400);
            settingStage.setResizable(false);

            // Hiển thị cửa sổ (floating panel)
            settingStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void showAboutPanel() {
    }
}
