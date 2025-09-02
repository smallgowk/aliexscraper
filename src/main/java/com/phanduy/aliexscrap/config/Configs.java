/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.config;

import com.phanduy.aliexscrap.model.response.ConfigInfo;
import com.phanduy.aliexscrap.utils.OSUtil;
import com.phanduy.aliexscrap.utils.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author duyuno
 */
public class Configs {
    
    public static String version = "2.0";

//    private static final String CONFIG_FILE = "/config.conf";
//    public static final String CONFIG_FILE_EXCEL_PATH = "/pathConfig.conf";
    private static final String CONTENT_CONFIG_FILE = "aliexConf.conf";
    private static final String PRODUCT_CONFIG_FILE = "pathConfig.conf";
    
//    private static final String COOKIE_CACHE_FILE = "Cookie.txt";
    private static final String COOKIE_CACHE_FILE = "us.txt";

//    public static final String DEFAUL_TOOL_DIRECTORY = "AppConfig";
    public static final String STORE_PRODUCT_DIRECTORY = "StoreProducts";
    public static final String CACHE_DIRECTORY = "CacheAliex";
    public static final String ERROR_PRODUCT_DIRECTORY = "ErrorProducts";
    public static final String DEFAULT_PRODUCT_DIRECTORY = "Products";
    public static final String BRANCH_PRODUCT_DIRECTORY = "BranchProducts";
    public static final String FIXING_PRODUCT_DIRECTORY = "FixingProducts";
    public static final String IMAGE_DIRECTORY = "Images";
    
    public static final String CONFIGS_DIRECTORY = "Configs";
    public static final String DATA_DIRECTORY = "Data";
    
    public static final String STORE_INFO_CACHE_DIR = "StoreInfoCache";
//    public static final String MERCHANT_CACHE_DIR = "Merchant";
//    public static final String PRODUCT_CACHE_DIR = "Products";
    public static final String PRODUCT_CACHE_DIR_V2 = "ProductsV2";
    public static final String RELATED_CACHE_DIR = "Related";
    
    public static String PRODUCT_DATA_PATH;
    public static String IMAGE_DATA_PATH;

    public static final String DEFAULT_TEMPLATE_NAME = "AmazonProductTemplate";

    public static final String DEFAULT_EXCEL_SAMPLE_FILE = "AmazonProductTemplate.xlsx";
    public static final String EXCEL_CUSTOME_VALUE_FILE = "custom_value.xlsx";
    public static final String EXCEL_CUSTOM_ACTION = "custom_action.xlsx";
//    public static final String DEFAULT_STORE_FILE = "storeInfo.xlsx";
    public static final String DEFAULT_UPC_FILE = "upccodes.xlsx";
    public static final String SHEET_NAME = "Worksheet";
    public static final String ACCOUNT_FILE_NAME = "login_account.txt";

    public static String TOOL_DATA_PATH;
//    public static String PRODUCT_DATA_PATH;
//    public static String STORE_PRODUCT_PATH;
//    public static String BRANCH_PRODUCT_PATH;
//    public static String FIXING_PRODUCT_PATH;
    public static String CACHE_DIR;
    public static String CACHE_PATH;
    public static String ERROR_PRODUCT_PATH;
    public static String COOKIE_PATH;
    public static String LOG_PATH;
    public static String LOG_CONFIG_PATH;

    public static String CONFIG_FOLDER_PATH;
    public static String CONFIG_FOLDER_BTG_PATH;
    public static String DEFAULT_SAMPLE_XLSX_FILE_PATH;
//    public static String DEFAULT_STORE_FILE_PATH;
    public static String DEFAULT_UPC_FILE_PATH;

    public static String pathChar;

    public static String appIconPath;
    public static String excelSampleFilePath;
    public static String excelCustomValueFilePath;
    public static String excelCustomActionFilePath;
    public static String storeFilePath;
    public static String profilePath;
    public static String vpsIp;
    public static int maxRow;
    public static String template;
    public static boolean ai;
//    public static String apiKeys;
//    public static String merchantUser;
//    public static String merchantPass;
//    public static int lastPriceLimitIndex;
//    public static int lastPriceRateIndex;
    
    public static float priceLimit;
    public static float priceRate;

//    public static boolean isOnlyUS;
//    public static int fetchingAliexKeywords;
//    public static int usingFeatureFromDes;
//    public static int fetchingImageFromDes;
    public static int dataSaveType;
    public static int downloadAllImage = 0;
//    public static int dataLevel;
    public static int port = 89;
    
    public static int filterEpacket = 1;
    public static int filterAliexpress = 1;
    public static int filterAliDirect = 1;
    
    public static boolean isStopByNoShipping = true;
    
    public static HashMap<String, String> regionMap = new HashMap<String, String>() {
        {
            put("US", "en_US");
            put("SA", "ar_MA");
        }
    };
    
//    public static String[] listPrice = new String[]{"10$", "20$", "30$", "40$", "50$", "80$", "100$", "150$", "200$", "500$", "1000$"};
//    public static float[] listPriceValue = new float[]{10, 20, 30, 40, 50, 80, 100, 150, 200, 500, 1000};
//
//    public static String[] listPriceRate = new String[]{"x1.5","x1.8", "x2","x2.2", "x2.5","x2.8", "x3", "x3.5", "x4", "x4.5", "x5"};
//    public static float[] listPriceRateValue = new float[]{1.5f, 1.8f, 2f, 2.2f, 2.5f, 2.8f, 3f, 3.5f, 4f, 4.5f, 5f};
    
    public static ArrayList<String[]> listAccount;
    public static HashMap<String, String> hashCustomValue = new HashMap<>();
    public static HashMap<String, Boolean> hashMapAccountState;
    
    public static String downloadUrl = "http://iamhere.vn";
    
    public static boolean isClickedLoadProfile = false;

    static {

        if (OSUtil.isWindows()) {
            pathChar = "\\";
        } else {
            pathChar = "/";
        }

//        appIconPath = "Images" + pathChar + "appIcon.png";
//
////        TOOL_DATA_PATH = System.getProperty("user.home") + pathChar + DEFAUL_TOOL_DIRECTORY;
//
//        String dir = System.getProperty("user.dir");
//
//        if(dir == null || dir.isEmpty()) {
//            dir = Paths.get("").toAbsolutePath().toString();
//        }
//
//
//        CONFIG_FOLDER_PATH = dir + pathChar + "etc" + pathChar + CONFIGS_DIRECTORY + pathChar;
////        CONFIG_FOLDER_PATH = String.valueOf(TOOL_DATA_PATH + pathChar + CONFIGS_DIRECTORY + pathChar);
//        LOG_CONFIG_PATH = String.valueOf(CONFIG_FOLDER_PATH + pathChar + "log4j.properties");
//
//
//
////        TOOL_DATA_PATH = dir + pathChar + "etc" + pathChar + DATA_DIRECTORY + pathChar;
//
////        CACHE_PATH = String.valueOf(TOOL_DATA_PATH + pathChar + CACHE_DIRECTORY + pathChar);
////        ERROR_PRODUCT_PATH = String.valueOf(TOOL_DATA_PATH + pathChar + ERROR_PRODUCT_DIRECTORY + pathChar);
////        COOKIE_PATH = String.valueOf(CACHE_PATH + COOKIE_CACHE_FILE);
////        LOG_PATH = String.valueOf(TOOL_DATA_PATH + pathChar + CACHE_DIRECTORY + pathChar);
//
//
////        PRODUCT_DATA_PATH = String.valueOf(TOOL_DATA_PATH + pathChar + DEFAULT_PRODUCT_DIRECTORY + pathChar);
////        IMAGE_DATA_PATH = String.valueOf(TOOL_DATA_PATH + pathChar + IMAGE_DIRECTORY + pathChar);
//
//        CONFIG_FOLDER_BTG_PATH = String.valueOf(CONFIG_FOLDER_PATH + "BTG" + pathChar);
//        DEFAULT_SAMPLE_XLSX_FILE_PATH = String.valueOf(CONFIG_FOLDER_PATH + DEFAULT_EXCEL_SAMPLE_FILE);
//        excelCustomValueFilePath = String.valueOf(CONFIG_FOLDER_PATH + EXCEL_CUSTOME_VALUE_FILE);
//        excelCustomActionFilePath = String.valueOf(CONFIG_FOLDER_PATH + EXCEL_CUSTOM_ACTION);
////        DEFAULT_STORE_FILE_PATH = String.valueOf(CONFIG_FOLDER_PATH + DEFAULT_STORE_FILE);
//        DEFAULT_UPC_FILE_PATH = String.valueOf(CONFIG_FOLDER_PATH + DEFAULT_UPC_FILE);
//
//        File configDirectory = new File(CONFIG_FOLDER_PATH);
//        if (!configDirectory.exists()) {
//            configDirectory.mkdir();
//        }
//
//        loadAllConfigs(dir);
//
//        updateDataPath();
//
////        com.pong.config.Configs.init();
//        AWSUtil.init();
//
//        BTGManager.getInstance().initBTG();
        initCacheFolder();
    }

    public static void initCacheFolder() {
        String dir = System.getProperty("user.home");
        if (dir == null || dir.isEmpty()) {
            dir = System.getProperty("user.dir");
            if (dir == null || dir.isEmpty()) {
                dir = Paths.get("").toAbsolutePath().toString();
            }
        }

        System.out.println("User home path: " + dir);

        CACHE_DIR  = dir + pathChar + ".aliexscrap";
        ERROR_PRODUCT_PATH = String.valueOf(CACHE_DIR + pathChar + ERROR_PRODUCT_DIRECTORY + pathChar);
        CACHE_PATH = String.valueOf(CACHE_DIR + pathChar + CACHE_DIRECTORY + pathChar);
        LOG_PATH = CACHE_PATH;
        COOKIE_PATH = String.valueOf(CACHE_PATH + COOKIE_CACHE_FILE);

        File directory = new File(CACHE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File errorDirectory = new File(ERROR_PRODUCT_PATH);
        if (!errorDirectory.exists()) {
            errorDirectory.mkdirs();
        }

        File cacheDirectory = new File(CACHE_PATH);
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }

        cacheDirectory = new File(CACHE_PATH + STORE_INFO_CACHE_DIR);
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }

        cacheDirectory = new File(CACHE_PATH + PRODUCT_CACHE_DIR_V2);
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }
    }
    
    public static void updateDataPath() {
        // Sử dụng thư mục người dùng thay vì thư mục làm việc hiện tại
        PRODUCT_DATA_PATH = String.valueOf(TOOL_DATA_PATH + pathChar);
        IMAGE_DATA_PATH = String.valueOf(TOOL_DATA_PATH + pathChar + IMAGE_DIRECTORY + pathChar);

        File directory = new File(TOOL_DATA_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (downloadAllImage == 1) {
            initImageDir();
        }
    }

    public static void initImageDir() {
        File directory = new File(IMAGE_DATA_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

//    public static void loadProductPathFolder() {
//        Properties cnfParamsTmp = new Properties();
//        FileInputStream propsFile = null;
//        try {
//            propsFile = new FileInputStream(CONFIG_FOLDER_PATH + PRODUCT_CONFIG_FILE);
//
//            String dataPath = cnfParamsTmp.getProperty("dataPath", "");
//
//            if (isEmptyPath(dataPath)) {
//                initDefaulPaths();
//                changeProductFolderConfig(PRODUCT_DATA_PATH);
//            } else {
//
//                PRODUCT_DATA_PATH = String.valueOf(dataPath);
//                boolean isEndWithPathChar = PRODUCT_DATA_PATH.endsWith(pathChar);
//                STORE_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + STORE_PRODUCT_DIRECTORY + pathChar);
//                BRANCH_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + BRANCH_PRODUCT_DIRECTORY + pathChar);
//                FIXING_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + FIXING_PRODUCT_DIRECTORY + pathChar);
//
//            }
//            createProductFolders();
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (propsFile != null) {
//                try {
//                    propsFile.close();
//                } catch (IOException e) {
//                    // thong bao dong file loi
//                }
//            }
//        }
//    }
//    public static void loadProductConfig() {
//        Properties cnfParamsTmp = new Properties();
//        FileInputStream propsFile = null;
//        try {
//            propsFile = new FileInputStream(CONFIG_FOLDER_PATH + PRODUCT_CONFIG_FILE);
////            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
////            propsFile = new FileInputStream(new File(jframe.getClass().getClassLoader().getResource(CONFIG_FILE_EXCEL_PATH).getFile()));
//
////            InputStream inputStream = Configs.class.getResourceAsStream(CONFIG_FILE_EXCEL_PATH);
////            InputStreamReader clientSecretReader = new InputStreamReader(inputStream);
//            cnfParamsTmp.load(propsFile);
//            String dataPath = cnfParamsTmp.getProperty("dataPath", "");
//
//            if (isEmptyPath(dataPath)) {
//                initDefaulPaths();
//                changeProductFolderConfig(PRODUCT_DATA_PATH);
//            } else {
//
//                PRODUCT_DATA_PATH = String.valueOf(dataPath);
//                boolean isEndWithPathChar = PRODUCT_DATA_PATH.endsWith(pathChar);
////                STORE_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + STORE_PRODUCT_DIRECTORY + pathChar);
////                BRANCH_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + BRANCH_PRODUCT_DIRECTORY + pathChar);
////                FIXING_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + FIXING_PRODUCT_DIRECTORY + pathChar);
//
//            }
//            createProductFolders();
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (propsFile != null) {
//                try {
//                    propsFile.close();
//                } catch (IOException e) {
//                    // thong bao dong file loi
//                }
//            }
//        }
//    }

    public static void loadContentConfig(String dir) {
        Properties cnfParamsTmp = new Properties();
        FileInputStream propsFile = null;
        try {
            propsFile = new FileInputStream(CONFIG_FOLDER_PATH + CONTENT_CONFIG_FILE);
//            URL url = Thread.currentThread().getContextClassLoader().getResource("Configs");
//            String path = url.getPath();
//            
//            System.out.println("" + path);
//            propsFile = new FileInputStream(CONFIG_FILE);
//            InputStream inputStream = Configs.class.getResourceAsStream(CONFIG_FILE);
//            InputStreamReader clientSecretReader = new InputStreamReader(inputStream);
//            InputStreamReader clientSecretReader = new InputStreamReader(Configs.class.getResourceAsStream("/Configs/config.conf"));

//            propsFile = new FileInputStream(clientSecretReader);
//            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//            
//            propsFile = (FileInputStream) classloader.getResourceAsStream(CONFIG_FILE);
//            propsFile = new FileInputStream(CONFIG_FILE);
//            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//            propsFile = new FileInputStream(new File(jframe.getClass().getClassLoader().getResource(CONFIG_FILE).getFile()));
//            cnfParamsTmp.load(clientSecretReader);
            cnfParamsTmp.load(propsFile);
//            String portStr = cnfParamsTmp.getProperty("serverPort", "89");

            storeFilePath = cnfParamsTmp.getProperty(KeyConfigs.STORE_FILE_PATH, "");
            profilePath = cnfParamsTmp.getProperty(KeyConfigs.PROFILE_PATH, "");
            TOOL_DATA_PATH = cnfParamsTmp.getProperty(KeyConfigs.DATA_FOLDER_PATH, dir + pathChar + "etc" + pathChar + DATA_DIRECTORY + pathChar);
            
//            String apiKeyEn = cnfParamsTmp.getProperty("apiKey", "6C0FD11E49F6125C74BACB32D35C67449BA1592E68AE220B0A42035647AE11EA");
//            String merchantUserEn = cnfParamsTmp.getProperty("merchantUser", "804373CFF37E764C5D2A505B5047991DD501BF679B5A24C21A980BE778B53A8B");
//            String merchantPassEn = cnfParamsTmp.getProperty("merchantPass", "905DDA0FA5FAFC65C9C76A208C495D3E");
            
//            System.out.println("" + apiKeyEn);
//            System.out.println("" + merchantUserEn);
//            System.out.println("" + merchantPassEn);
            
//            try {
//                apiKeys = FuncUtil.decrypt(apiKeyEn);
//                merchantUser = FuncUtil.decrypt(merchantUserEn);
//                merchantPass = FuncUtil.decrypt(merchantPassEn);
//            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | DecoderException ex) {
//                Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
//            System.out.println("" + apiKeys);
//            System.out.println("" + merchantUser);
//            System.out.println("" + merchantPass);
            
//            try {
//                port = Integer.parseInt(portStr);
//            } catch (NumberFormatException ex) {
//                port = 89;
//            }

           
            
//            try {
//                fetchingAliexKeywords = Integer.parseInt(fetchingKeyword);
//            } catch (NumberFormatException ex) {
//                fetchingAliexKeywords = 1;
//            }
            
//            try {
//                usingFeatureFromDes = Integer.parseInt(usingFeatureFromDesStr);
//            } catch (NumberFormatException ex) {
//                usingFeatureFromDes = 0;
//            }
            
//            try {
//                fetchingImageFromDes = Integer.parseInt(fetchingImageFromDesStr);
//            } catch (NumberFormatException ex) {
//                fetchingImageFromDes = 0;
//            }
            
//            try {
//                dataLevel = Integer.parseInt(dataLevelStr);
//            } catch (NumberFormatException ex) {
//                dataLevel = 1;
//            }
            
//            try {
//                lastPriceLimitIndex = Integer.parseInt(lastPriceLimitIndexStr);
//            } catch (NumberFormatException ex) {
//                lastPriceLimitIndex = 5;
//            }
            
//            Configs.priceLimit = listPriceValue[lastPriceLimitIndex];

//            try {
//                lastPriceRateIndex = Integer.parseInt(lastPriceRateIndexStr);
//            } catch (NumberFormatException ex) {
//                lastPriceRateIndex = 2;
//            }
            
//            Configs.priceRate = listPriceRateValue[lastPriceRateIndex];
            
//            try {
//                regionIndex = Integer.parseInt(regionIndexStr);
//            } catch (NumberFormatException ex) {
//                regionIndex = 0;
//            }
//            
//            Configs.region = listRegion[regionIndex];
//            Configs.locale = listLocale[regionIndex];

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (propsFile != null) {
                try {
                    propsFile.close();
                } catch (IOException e) {
                    // thong bao dong file loi
                }
            }
        }
    }
    
    public static void createProductFolders() {

        File directory = new File(PRODUCT_DATA_PATH);

        if (!directory.exists()) {
            directory.mkdir();
        }
        
        directory = new File(IMAGE_DATA_PATH);

        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    
    
    public static void loadAccountAuthen() {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FOLDER_PATH + ACCOUNT_FILE_NAME);
            InputStreamReader clientSecretReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(clientSecretReader);

            String st;
            while ((st = br.readLine()) != null) {
                if (st != null && !st.isEmpty()) {
                    String[] parts = st.split(Pattern.quote(","));
                    if(parts.length == 2) {
                        if(listAccount == null) {
                            listAccount = new ArrayList<>();
                        }
                        String[] data = new String[2];
                        data[0] = parts[0];
                        data[1] = parts[1]; 
                        listAccount.add(data);
                    }
                }
            }
            
            hashMapAccountState = new HashMap<>();
            
            for(String[] account : listAccount) {
                hashMapAccountState.put(account[0], false);
            }

        } catch (IOException ex) {

        }
    }
    
    public static void changeProfilePath(String path) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.PROFILE_PATH, path});
            profilePath = path;
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void changeProductFolderConfig(String path) throws FileNotFoundException, IOException {
        changeConfigValues(PRODUCT_CONFIG_FILE, new String[]{"dataPath", path});
        TOOL_DATA_PATH = path;
    }
    
    public static void changeUserEmailConfig(String email) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{"userEmail", email});
//            userEmail = email;
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void changeDataFolderPathConfig(String path) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.DATA_FOLDER_PATH, path});
            TOOL_DATA_PATH = path;
            updateDataPath();
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void changeDataSaveType(int mode) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{"dataSaveType", "" + mode});
            dataSaveType = mode;
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
//    public static void changeALiexFetchingKey(int mode) {
//        try {
//            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.ALIEX_FETCHING_KEYS, "" + mode});
//            fetchingAliexKeywords = mode;
//        } catch (IOException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    public static void changeUsingFeatureFromDes(int mode) {
//        try {
//            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.USING_FEATURE_FROM_DES, "" + mode});
//            usingFeatureFromDes = mode;
//        } catch (IOException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    public static void changeFetchingImageFromDes(int mode) {
//        try {
//            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.FETCHING_IMAGE_FROM_DES, "" + mode});
//            fetchingImageFromDes = mode;
//        } catch (IOException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static void changeDownloadAllImage(int mode) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.DOWNLOAD_ALL_IMAGES, "" + mode});
            downloadAllImage = mode;
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void changeFilterEpacket(int mode) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.SHIPPING_METHOD_EPACKET, "" + mode});
            filterEpacket = mode;
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void changeFilterAliexpress(int mode) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.SHIPPING_METHOD_ALIEX_STANDARD, "" + mode});
            filterAliexpress = mode;
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void changeFilterAliDirect(int mode) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.SHIPPING_METHOD_ALIEX_DIRECT, "" + mode});
            filterAliDirect = mode;
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void changeDataLevel(int level) {
//        try {
//            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{"dataLevel", "" + level});
//            dataLevel = level;
//        } catch (IOException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
    
    public static void changeLicenseConfig(String lic) {
        try {
            changeConfigValues(CONTENT_CONFIG_FILE, new String[]{"license", lic});
//            license = lic;
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void changeMaxRow(int maxRow) throws FileNotFoundException, IOException {
        if (maxRow == Configs.maxRow) {
            return;
        }

        changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.MAX_ROW, "" + maxRow});

        Configs.maxRow = maxRow;
    }
    
    public static void changeVpsIpConfig(String ip) throws FileNotFoundException, IOException {
        changeNullableConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.VPS_IP, ip});
        vpsIp = ip;
    }

//    public static void changeExcelSamplePathConfig(String path) throws FileNotFoundException, IOException {
//        changeConfigValues(CONTENT_CONFIG_FILE, new String[]{"excelSamplePath", path});
//        excelSampleFilePath = path;
//    }

    public static void changeStoreFilePathConfig(String path) throws FileNotFoundException, IOException {
        changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.STORE_FILE_PATH, path});
        storeFilePath = path;
    }

//    public static void updatePriceLimitConfig(int index) throws FileNotFoundException, IOException {
//        if (index == Configs.lastPriceLimitIndex) {
//            return;
//        }
//
//        changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.LAST_PRICE_LIMIT_SELECT, "" + index});
//
//        lastPriceLimitIndex = index;
//    }
    
//    public static void updateRegionConfig(int index) throws FileNotFoundException, IOException {
//        if (index == Configs.regionIndex) {
//            return;
//        }
//
//        changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.REGION, "" + index});
//
//        regionIndex = index;
//    }

//    public static void updatePriceRateConfig(int index) throws FileNotFoundException, IOException {
//        if (index == Configs.lastPriceRateIndex) {
//            return;
//        }
//
//        changeConfigValues(CONTENT_CONFIG_FILE, new String[]{KeyConfigs.LAST_PRICE_RATE_SELECT, "" + index});
//
//        lastPriceRateIndex = index;
//    }

//    public static void changeProductPath(String path) throws IOException {
//
//        PRODUCT_DATA_PATH = String.valueOf(path);
//        boolean isEndWithPathChar = PRODUCT_DATA_PATH.endsWith(pathChar);
//        STORE_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + STORE_PRODUCT_DIRECTORY + pathChar);
//        BRANCH_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + BRANCH_PRODUCT_DIRECTORY + pathChar);
//        FIXING_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + (isEndWithPathChar ? "" : pathChar) + FIXING_PRODUCT_DIRECTORY + pathChar);
//        createProductFolders();
//        changeProductFolderConfig(path);
//    }

    public static boolean isEmptyPath(String path) {
        return path == null || path.trim().isEmpty();
    }

//    public static void initDefaulPaths() {
//        boolean isEndWithPathChar = TOOL_DATA_PATH.endsWith(pathChar);
//        PRODUCT_DATA_PATH = String.valueOf(TOOL_DATA_PATH + (TOOL_DATA_PATH.endsWith(pathChar) ? "" : pathChar) + DEFAULT_PRODUCT_DIRECTORY + pathChar);
//        STORE_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + STORE_PRODUCT_DIRECTORY + pathChar);
//        BRANCH_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + BRANCH_PRODUCT_DIRECTORY + pathChar);
//        FIXING_PRODUCT_PATH = String.valueOf(PRODUCT_DATA_PATH + FIXING_PRODUCT_DIRECTORY + pathChar);
//    }

    public static void changeConfigValues(String fileName, String[]... listConfigs) throws IOException {
        if (listConfigs == null || listConfigs.length == 0) {
            return;
        }
        FileInputStream in = new FileInputStream(CONFIG_FOLDER_PATH + fileName);
        Properties props = new Properties();
        props.load(in);
        in.close();
        FileOutputStream out = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
        for (String[] pair : listConfigs) {
            if (StringUtils.isEmpty(pair[0]) || StringUtils.isEmpty(pair[1])) {
                continue;
            }
            props.setProperty(pair[0], pair[1]);
        }
        props.store(out, null);
        out.close();
    }
    
    public static void changeNullableConfigValues(String fileName, String[]... listConfigs) throws IOException {
        if (listConfigs == null || listConfigs.length == 0) {
            return;
        }
        FileInputStream in = new FileInputStream(CONFIG_FOLDER_PATH + fileName);
        Properties props = new Properties();
        props.load(in);
        in.close();
        FileOutputStream out = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
        for (String[] pair : listConfigs) {
            if (StringUtils.isEmpty(pair[0])) {
                continue;
            }
            
            if (pair[1] == null) {
                pair[1] = "";
            }
            props.setProperty(pair[0].trim(), pair[1].trim());
        }
        props.store(out, null);
        out.close();
    }
    
    

//    public static void createProductFolders() {
//
//        File directory = new File(PRODUCT_DATA_PATH);
//
//        if (!directory.exists()) {
//
//            directory.mkdir();
//        }
//
//        File storeDirectory = new File(STORE_PRODUCT_PATH);
//
//        if (!storeDirectory.exists()) {
//
//            storeDirectory.mkdir();
//        }
//
//        File branchDirectory = new File(BRANCH_PRODUCT_PATH);
//
//        if (!branchDirectory.exists()) {
//
//            branchDirectory.mkdir();
//        }
//
//        File fixingDirectory = new File(FIXING_PRODUCT_PATH);
//
//        if (!fixingDirectory.exists()) {
//
//            fixingDirectory.mkdir();
//        }
//
//    }

    public static void copyConfigsFilesByName(String fileName) throws IOException {
        
        File file = new File(CONFIG_FOLDER_PATH + fileName);
        if(file.exists()) {
            return;
        }
        
        InputStream inputStream = Configs.class.getResourceAsStream("/" + fileName);
//        InputStreamReader clientSecretReader = new InputStreamReader(inputStream);
//
//        Properties props = new Properties();
//        props.load(clientSecretReader);
//        clientSecretReader.close();
//        inputStream.close();

//        FileOutputStream outLocal = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
//        props.store(outLocal, null);
//        outLocal.close();
        FileOutputStream outLocal = null;
        try {
            outLocal = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            outLocal.write(buffer);

//            File file = new File(CONFIG_FOLDER_PATH + fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (outLocal != null) {
                try {
                    outLocal.close();
                } catch (IOException ex) {
                    Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void copyExecuteFilesByName(String fileName) {
        
        File file = new File(CONFIG_FOLDER_PATH + fileName);
        if(file.exists()) {
            return;
        }
        
        InputStream inputStream = null;
        FileOutputStream outLocal = null;

        inputStream = Configs.class.getResourceAsStream("/" + fileName);

        if(inputStream == null) {
            return;
        }
        
//        InputStreamReader clientSecretReader = new InputStreamReader(inputStream);
        try {
            outLocal = new FileOutputStream(CONFIG_FOLDER_PATH + fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            outLocal.write(buffer);

            file = new File(CONFIG_FOLDER_PATH + fileName);
            file.setExecutable(true);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (outLocal != null) {
                try {
                    outLocal.close();
                } catch (IOException ex) {
                    Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//    public static void copyConfigsFiles() {
//        try {
//            copyConfigsFilesByName("AmazonProductTemplate.xlsx");
//            copyConfigsFilesByName("BulletPoints.txt");
//            copyConfigsFilesByName("GTIN_brands.txt");
//            copyConfigsFilesByName("ProductTypes.txt");
//            copyConfigsFilesByName("config.conf");
//            copyConfigsFilesByName("pathConfig.conf");
//            copyConfigsFilesByName("login_account.txt");
//            copyConfigsFilesByName("marker_api.conf");
//            copyExecuteFilesByName("AmazonProductTemplate.xlsx");
//            copyExecuteFilesByName("BTG" + pathChar + "automotive_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "baby-products_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "beauty_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "cell-phones_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "electronics_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "fashion_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "garden_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "health_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "home-improvement_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "office-products_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "pet-supplies_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "sporting-goods_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "toys-and-games_browse_tree_guide.xls");
//            copyExecuteFilesByName("BTG" + pathChar + "videogames_browse_tree_guide.xls");
//            if (OSUtil.isWindows()) {
//                copyExecuteFilesByName("chromedriver.exe");
//            } else {
//                copyExecuteFilesByName("chromedriver");
//            }
//            System.out.println("configs copied...");

//        } catch (IOException ex) {
//            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static void updateConfig(ConfigInfo data) {
        isStopByNoShipping = data.noShipingFound;
        downloadAllImage = data.imageDownload ? 1 : 0;
        vpsIp = data.vpsIp;
        maxRow = data.maxRow > 0 ? data.maxRow : 1000;
        template = data.template != null ? data.template.trim() : "";
        ai = data.ai;
        System.out.println("Download " + downloadAllImage);
        System.out.println("NewFile: " + Configs.excelSampleFilePath);
        if (downloadAllImage == 1) {
            initImageDir();
        }
    }

    public static String getConfigInfo(HashMap<String, String> configs, String computerSerial) {
        StringBuilder sb = new StringBuilder();
        sb.append("DSN: ").append(computerSerial).append(";");

        for (String key : configs.keySet()) {
            if (key.equalsIgnoreCase("template")) {
                sb.append("template: ").append(StringUtils.isEmpty(Configs.template) ? "Old" : "New").append(";");
            } else {
                sb.append(key).append(": ").append(configs.get(key)).append(";");
            }
        }
        return sb.toString();
    }
}
