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
import com.phanduy.aliexscrap.model.request.GetStoreInfosReq;
import com.phanduy.aliexscrap.model.response.GetItemsByCategoryResponseData;
import com.phanduy.aliexscrap.model.response.StoreInfoResponseData;
import com.phanduy.aliexscrap.utils.ComputerIdentifier;
import com.phanduy.aliexscrap.utils.ExcelReader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class HomePanelController {
    @FXML private TextField amzProductTemplate1Field;
    @FXML private TextField amzProductTemplate2Field;
    @FXML private TextField outputField;
    @FXML private TextField configFileField;

//    @FXML private Button btnCancel;
//    @FXML private Button btnApply;
//    @FXML private Button btnOk;
    @FXML private Button browseTemplate1;
    @FXML private Button browseTemplate2;
    @FXML private Button browseOutput;
    @FXML private Button browseConfigFile;

    @FXML private ComboBox<String> templateComboBox;

    @FXML private Label processingLabel;
//    @FXML private TextArea logArea;

    private final ApiService apiService = ApiClient.getClient().create(ApiService.class);

    // Preferences API để cache setting
    private Preferences prefs;

    @FXML
    public void initialize() {
        prefs = Preferences.userNodeForPackage(HomePanelController.class);
        templateComboBox.setItems(FXCollections.observableArrayList("Template 1", "Template 2"));
        String savedTemplate = prefs.get("selectTemplate", "Template 1");
        templateComboBox.setValue(savedTemplate);

        templateComboBox.setOnAction(event -> {
            String selectTemplate = templateComboBox.getValue();
            prefs.put("selectTemplate", selectTemplate);
        });
        loadSettings();
    }

//    @FXML
//    private void onCancel() {
//        Stage stage = (Stage) btnCancel.getScene().getWindow();
//        stage.close();
//    }
//
//    @FXML
//    private void onApply() {
//        saveSettings();
//        System.out.println("Settings Applied!");
//    }
//
//    @FXML
//    private void onOk() {
//        saveSettings();
//        Stage stage = (Stage) btnOk.getScene().getWindow();
//        stage.close();
//    }

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

    @FXML
    private void onBrowserAmzProductTempFile2() {
        String currentPath = amzProductTemplate2Field.getText();
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
        Stage stage = (Stage) browseTemplate2.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Process the selected file
        if (selectedFile != null) {
            amzProductTemplate2Field.setText(selectedFile.getAbsolutePath());
            prefs.put("amzProductTemplate2Field", selectedFile.getAbsolutePath());
        }
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

    private void saveSettings() {
        prefs.put("amzProductTemplate1Field", amzProductTemplate1Field.getText());
        prefs.put("amzProductTemplate1Field", amzProductTemplate2Field.getText());
        prefs.put("outputField", outputField.getText());
        prefs.put("configFileField", configFileField.getText());

        System.out.println("Settings Saved!");
    }

    private void loadSettings() {
        amzProductTemplate1Field.setText(prefs.get("amzProductTemplate1Field", ""));
        amzProductTemplate2Field.setText(prefs.get("amzProductTemplate2Field", ""));
        outputField.setText(prefs.get("outputField", ""));
        configFileField.setText(prefs.get("configFileField", ""));

        System.out.println("Settings Loaded!");
    }

    @FXML
    private void onCrawlClick() {
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

        if (settingInfo == null) return;

        processingLabel.setVisible(true);

        final String storeId = settingInfo.getStoreId();

        GetStoreInfosReq request = new GetStoreInfosReq(
                storeId,
                settingInfo.getKeywordLink(),
                ComputerIdentifier.getDiskSerialNumber()
        );

        apiService.getFullStoreInfo(request).enqueue(new Callback<ApiResponse<StoreInfoResponseData>>() {
            @Override
            public void onResponse(Call<ApiResponse<StoreInfoResponseData>> call, Response<ApiResponse<StoreInfoResponseData>> response) {
                Platform.runLater(() -> {
                    processingLabel.setVisible(false);
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse<StoreInfoResponseData> apiResponse = response.body();
                        if (apiResponse.isSuccess()) {
                            processStoreInfo(storeId, apiResponse.getData());
                        } else {
                            System.out.println("Failed: " + apiResponse.getMessage());
                        }
                    } else {
                        System.out.println("Error: Server error!");
                    }
                });
            }

            @Override
            public void onFailure(Call<ApiResponse<StoreInfoResponseData>> call, Throwable t) {
                Platform.runLater(() -> {
                    processingLabel.setVisible(false);
                    System.out.println("Request failed: " + t.getMessage());
                });
            }
        });
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
                            System.out.println("Failed: " + apiResponse.getMessage());
                        }
                    } else {
                        System.out.println("Error: Server error!");
                    }
                });
            }

            @Override
            public void onFailure(Call<ApiResponse<GetItemsByCategoryResponseData>> call, Throwable t) {
                Platform.runLater(() -> {
                    processingLabel.setVisible(false);
                    System.out.println("Request failed: " + t.getMessage());
                });
            }
        });
    }
}
