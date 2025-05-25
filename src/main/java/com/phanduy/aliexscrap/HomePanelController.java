package com.phanduy.aliexscrap;

import com.google.gson.Gson;
import com.models.request.CheckConfigsReq;
import com.phanduy.aliexscrap.api.ApiClient;
import com.phanduy.aliexscrap.api.ApiExecutor;
import com.phanduy.aliexscrap.api.ApiResponse;
import com.phanduy.aliexscrap.api.ApiService;
import com.phanduy.aliexscrap.model.*;
import com.phanduy.aliexscrap.model.request.*;
import com.phanduy.aliexscrap.model.response.*;
import com.phanduy.aliexscrap.utils.*;
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
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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
//    @FXML private ComboBox<String> profileComboBox;

    @FXML private Label processingLabel;
//    @FXML private TextArea logArea;

    private final ApiService apiService = ApiClient.getClient().create(ApiService.class);

    // Preferences API để cache setting
    private Preferences prefs;

    private DataHolder dataHolder;

//    private HashMap<String, String> profileMap;

    @FXML
    public void initialize() {
        prefs = Preferences.userNodeForPackage(HomePanelController.class);


//        profileMap = new HashMap<>();
//
        String originalProfilePath = System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data/";
        System.out.println(originalProfilePath);
        File folder = new File(originalProfilePath);
//        ArrayList<String> listProfileNames = new ArrayList<>();
//        String[] fileNames = folder.list();
//        for (String fileName : fileNames) {
//            if (fileName.equals("Default") || fileName.startsWith("Profile")) {
//                String profileName = ScrapUtil.readProfileName(originalProfilePath, fileName);
//                System.out.println(fileName + " -> " + profileName);
//                listProfileNames.add(profileName);
//                profileMap.put(profileName, fileName);
//            }
//        }

//        profileComboBox.setItems(FXCollections.observableArrayList(listProfileNames));
//        String savedProfile = prefs.get("selectProfile", "Default");
//        System.out.println("savedProfile: " + savedProfile);
//        if (profileMap.containsKey(savedProfile)) {
//            profileComboBox.setValue(savedProfile);
//        } else {
//            profileComboBox.setValue(listProfileNames.get(0));
//        }

//        profileComboBox.setOnAction(event -> {
//            String selectProfile = profileComboBox.getValue();
//            prefs.put("selectProfile", selectProfile);
//            System.out.println("selectProfile: " + profileMap.get(selectProfile));
//        });

        dataHolder = new DataHolder();
        loadSettings();

    }

    private void updateTemplatePath() {
        String savedTemplate = prefs.get("selectTemplate", "Template 1");
        if (savedTemplate.equals("Template 1")) {
            prefs.put("savedTemplatePath", amzProductTemplate1Field.getText());
        } else {
            prefs.put("savedTemplatePath", amzProductTemplate2Field.getText());
        }
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
    private void onOpenAmzProductTempFile2() {
        FileOpener.openFileOrFolder(amzProductTemplate2Field.getText());
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

        templateComboBox.setItems(FXCollections.observableArrayList("Template 1", "Template 2"));
        String savedTemplate = prefs.get("selectTemplate", "Template 1");
        templateComboBox.setValue(savedTemplate);
        updateTemplatePath();

        templateComboBox.setOnAction(event -> {
            String selectTemplate = templateComboBox.getValue();
            prefs.put("selectTemplate", selectTemplate);
            updateTemplatePath();
        });

        System.out.println("Settings Loaded!");
    }

    @FXML
    private void onCrawlClick() {
//        String profile = profileMap.get(profileComboBox.getValue());
//        try {
//            AliexScraper.getInstance().initDriver(profile);
//        } catch (Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//            AlertUtil.showError("", e.getMessage());
//            return;
//        }

//        if (true) {
//            AliexScraper.getInstance().initDriver();
//            AliexScraper.getInstance().crawlProduct("3256806026959280");
//            return;
//        }
        String configFilePath = prefs.get("configFileField", "");
        System.out.println(configFilePath);

        SettingInfo settingInfo = null;
        try {
            settingInfo = ExcelReader.readExcelFile(configFilePath);
            System.out.println(settingInfo.getData());
            System.out.println(settingInfo.getSettings());
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        final SettingInfo setting = settingInfo;
        processingLabel.setVisible(true);

        CheckConfigsReq checkConfigsReq = new CheckConfigsReq();
        checkConfigsReq.configs = (HashMap<String, String>) (setting.getSettings());

        ApiExecutor.executeSync(
                apiService.checkConfig(checkConfigsReq),
                response -> {
                    if (response.isSuccess()) {
                        if (!response.getData().success) {
                            AlertUtil.showError("", response.getData().message);
                        } else {
                            if (setting.getKeyword().isEmpty()) {
                                startCrawlingStoreThread(setting);
                            }
//                            else {
//                                startCrawlingKeywordThread(setting);
//                            }
                        }
                    } else {
                        System.out.println("API Fail: " + response.getMessage());
                        AlertUtil.showError("", response.getMessage());
                    }
                    processingLabel.setVisible(false);
                },
                throwable -> {
                    AlertUtil.showError("", throwable.getMessage());
                    processingLabel.setVisible(false);
                }
        );
    }

    public void startCrawlingStoreThread(SettingInfo setting) {
        ThreadManager.getInstance().submitTask(
                () -> {
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    ApiService apiServiceNoLog = ApiClient.getClientNoLog().create(ApiService.class);
                    GetStoreInfoRapidData storeInfo = getStoreInfo(apiService, setting);
                    if (storeInfo != null) {
//                        ArrayList<GetPageDataResponse> listPages = new ArrayList<>();
                        GetPageDataResponse page1 = getListProductByPage(apiService, setting, storeInfo, 1);
                        int page = 1;
                        int totalPage = page1.totalPages;
//                        listPages.add(page1);
                        ThreadManager.getInstance().submitTask(
                                () -> {
                                    processPage(apiServiceNoLog, setting, storeInfo, page1);
                                }
                        );
//                        while (page <= totalPage) {
//                            page ++;
//                            GetPageDataResponse nextPage = getListProductByPage(apiService, setting, storeInfo, page);
//                            if (nextPage != null) {
//                                ThreadManager.getInstance().submitTask(
//                                        () -> {
//                                            processPage(apiServiceNoLog, setting, storeInfo, nextPage);
//                                        }
//                                );
//                            }
//                        }
                    }
                }
        );
    }

    private void processPage(ApiService apiService, SettingInfo setting, GetStoreInfoRapidData storeInfo, GetPageDataResponse page) {
        System.out.println("Process page " + page.currentPage + ": " + (new Gson().toJson(page)));
        ArrayList<NewTransformCrawlResponse> listData = new ArrayList<>();
//        int countStop = 3;
//        int count = 0;
        for (String item: page.items) {
            NewTransformRapidDataReq transformRapidDataReq = new NewTransformRapidDataReq(
                    ComputerIdentifier.getDiskSerialNumber(),
                    item,
                    storeInfo.storeId,
                    setting.getSettings(),
                    setting.getData()
            );
            NewTransformCrawlResponse response = getProductInfo(apiService, transformRapidDataReq);
            if (response != null) {
                listData.add(response);
//                count++;
//                if (count > countStop) {
//                    break;
//                }
            }
        }

        String templatePath = prefs.get("savedTemplatePath", "");
        if (templatePath.isEmpty()) return;

        File templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            return;
        }

        String outputFolderPath = prefs.get("outputField", "");
        if (outputFolderPath.isEmpty()) return;

        File outputFolder = new File(outputFolderPath);
        if (!outputFolder.exists()) {
            return;
        }

        outputFolder = new File(outputFolderPath + "\\" + storeInfo.storeId);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }

        File outerFile = new File(outputFolder.getAbsolutePath() + "\\page_" + page.currentPage + ".xlsx");

        try {
            System.out.println("Start export page " + page.currentPage);
            ExcelWriter.writeDataToTemplate(templateFile, outerFile, listData, setting);
            System.out.println("Complete!");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        System.out.println("Total: " + listData.size());
    }

    private NewTransformCrawlResponse getProductInfo(ApiService apiService, NewTransformRapidDataReq transformRapidDataReq) {
        Call<ApiResponse<NewTransformCrawlResponse>> call = apiService.getProductOld(transformRapidDataReq);
        Response<ApiResponse<NewTransformCrawlResponse>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            if (response.body().status == 200) {
                NewTransformCrawlResponse data = response.body().getData();
                return data;
            } else {
                System.out.println("" + transformRapidDataReq.id + " error: " + response.body().error);
                return null;
            }
        } else {
            System.err.println("API failed: " + response.code() + " - " + response.message());
            return null;
        }
    }


    private GetPageDataResponse getListProductByPage(ApiService apiService, SettingInfo setting, GetStoreInfoRapidData storeInfo, int page) {
        GetListProductByPageReq getListProductByPageReq = new GetListProductByPageReq(
                storeInfo.storeId,
                storeInfo.sellerId,
                ComputerIdentifier.getDiskSerialNumber(),
                "USD",
                setting.getRegion(),
                setting.getLocale(),
                page
        );

        Call<ApiResponse<GetPageDataResponse>> call = apiService.getListProductByPage(getListProductByPageReq);
        Response<ApiResponse<GetPageDataResponse>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (response.isSuccessful() && response.body() != null) {
            GetPageDataResponse data = response.body().getData();
            System.out.println("Data page " + page + ": " + (new Gson().toJson(data)));
            return data;
        } else {
            System.err.println("API failed: " + response.code() + " - " + response.message());
            return null;
        }
    }

    private GetStoreInfoRapidData getStoreInfo(ApiService apiService, SettingInfo setting) {
        GetStoreInfoRapidDataReq request = new GetStoreInfoRapidDataReq(
                setting.getProductId(),
                ComputerIdentifier.getDiskSerialNumber(),
                "USD",
                setting.getRegion(),
                setting.getLocale()
        );

        Call<ApiResponse<GetStoreInfoRapidData>> call = apiService.getStoreInfo(request);
        Response<ApiResponse<GetStoreInfoRapidData>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response.isSuccessful() && response.body() != null) {
            GetStoreInfoRapidData storeInfo = response.body().getData();
            System.out.println("Store Info: " + (new Gson().toJson(storeInfo)));
            return storeInfo;
        } else {
            System.err.println("API failed: " + response.code() + " - " + response.message());
            return null;
        }
    }

    public void startCrawlingInThread(String storeId, String productGroupId, String spm) {
        ThreadManager.getInstance().submitTask(
                () -> crawlingProductIds(storeId, productGroupId, spm)
        );
    }


    private void crawlingProductIds(String storeId, String productGroupId, String spm) {
//        String url = AliexScraper.getInstance().buildCategoryUrl(
//                storeId,
//                productGroupId,
//                spm
//        );
//        String categoryFolder = productGroupId.isEmpty() ? storeId + "_items" : productGroupId;
//        ItemListResponse itemListResponse = AliexScraper.getInstance().crawlData(
//                storeId,
//                categoryFolder,
//                url
//        );
//        System.out.println("SellerId: " +  (new Gson().toJson(itemListResponse)));
    }

    private void processStoreInfo(SettingInfo settingInfo, StoreInfoResponseData storeInfo) {
        Gson gson = new Gson();
        System.out.println("storeInfo: " + gson.toJson(storeInfo));
        dataHolder.storeInfo = storeInfo.storeInfo;
        if (storeInfo.categoryList != null && !storeInfo.categoryList.isEmpty()) {
            dataHolder.categoryList = storeInfo.categoryList;
            int totalTasks = dataHolder.categoryList.stream()
                    .mapToInt(cat -> Math.max(1, cat.getSubList() != null ? cat.getSubList().size() : 1))
                    .sum();
            CountDownLatch latch = new CountDownLatch(totalTasks);
            for (Category category : dataHolder.categoryList) {
                List<SubCategory> subCategories = category.getSubList();
                if (subCategories != null && !subCategories.isEmpty()) {
                    for (SubCategory subCategory : subCategories) {
                        processCategoryAndSubCategory(settingInfo, storeInfo.storeInfo, category, subCategory, latch);
                    }
                } else {
                    processCategoryAndSubCategory(settingInfo, storeInfo.storeInfo, category, null, latch);
                }
            }

            new Thread(() -> {
                try {
                    latch.await();  // Đợi tất cả task xong
                    Platform.runLater(() -> {
                        System.out.println("🎉 All category/sub-category processes done!");
                        System.out.println("Last data: " + gson.toJson(dataHolder));
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            processCategoryAndSubCategory(settingInfo, storeInfo.storeInfo, null, null, new CountDownLatch(1));
        }
    }

//    private void processCategory(SettingInfo settingInfo, StoreInfo storeInfo, Category category) {
//        if (category.getSubList() == null) {
//            processCategoryAndSubCategory(settingInfo, storeInfo, category, null);
//        } else {
//            category.getSubList().forEach(subCategory ->
//                    processCategoryAndSubCategory(settingInfo, storeInfo, category, subCategory)
//            );
//        }
//    }

    private void processCategoryAndSubCategory(SettingInfo settingInfo, StoreInfo storeInfo, Category category, SubCategory subCategory, CountDownLatch latch) {
        GetItemsByCategoryReq request = new GetItemsByCategoryReq(
                ComputerIdentifier.getDiskSerialNumber(),
                settingInfo.getStoreId(),
                category != null ? category.getId() : null,
                subCategory != null ? subCategory.getId() : null,
                storeInfo.getWidgetId(),
                storeInfo.getModuleName(),
                category != null ? category.getIndex() : 0,
                subCategory != null ? subCategory.getIndex() : -1
        );

        Gson gson = new Gson();
        System.out.println("request: " + gson.toJson(request));

        ApiExecutor.executeSync(
                apiService.getItemsByCategory(request),
                response -> {
                    if (response.isSuccess()) {
                        if (subCategory != null) {
                            subCategory.items = response.getData().items;
                        } else if (category != null) {
                            category.items = response.getData().items;
                        } else {
                            storeInfo.items = response.getData().items;
                        }
//                        for (String itemId : response.getData().items) {
//                            processProduct(settingInfo, storeInfo, category, subCategory, itemId);
//                        }
                    } else {
                        System.out.println("Fail category fetch: " + response.getMessage());
                    }
                    latch.countDown();
                },
                throwable -> {
                    System.out.println("Error fetching category: " + throwable.getMessage());
                    latch.countDown();
                }
        );
    }

    private void processProduct(SettingInfo settingInfo, StoreInfo storeInfo, Category category, SubCategory subCategory, String itemId) {
        TransformRapidDataReqV3 request = new TransformRapidDataReqV3(
                itemId,
                storeInfo.storeName,
                settingInfo.getKeywordLink(),
                settingInfo,
                ComputerIdentifier.getDiskSerialNumber()
        );

        Gson gson = new Gson();
        System.out.println("processProduct request: " + gson.toJson(request));

        ApiExecutor.executeSync(
                apiService.getProductInfo(request),
                response -> {
                    if (response.isSuccess()) {
                        System.out.println("apiResponse processProduct: " + gson.toJson(response.getData()));
                    } else {
                        System.out.println("Fail processProduct fetch: " + response.getMessage());
                    }
                },
                throwable -> System.out.println("Error fetching processProduct: " + throwable.getMessage())
        );
    }
}
