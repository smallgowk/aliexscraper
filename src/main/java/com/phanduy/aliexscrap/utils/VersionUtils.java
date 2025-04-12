package com.phanduy.aliexscrap.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionUtils {
    public static String getAppVersion() {
        Package aPackage = VersionUtils.class.getPackage();
        if (aPackage != null) {
            String version = aPackage.getSpecificationVersion();
            return version != null ? version : "Unknown";
        }
        return "Unknown";
    }

    public static String getAppVersionFromResource() {
        try (InputStream input = VersionUtils.class.getResourceAsStream("/app.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("version", "Unknown");
        } catch (IOException e) {
            return "Unknown";
        }
    }

}
