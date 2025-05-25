package com.phanduy.aliexscrap.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.apache.commons.io.FileUtils.copyDirectory;

public class CrawlerMachine {

    public WebDriver driver;
    public JavascriptExecutor executor;
    public Actions actions;

    public CrawlerMachine() {

    }

    public boolean initDriver() {
        if (driver != null) {
            System.out.println("driver ready");
            return true;
        }
        ChromeOptions options = new ChromeOptions();
        String customProfilePath = System.getProperty("user.home") + "/selenium-profile";
        options.addArguments("--user-data-dir=" + customProfilePath);
        options.addArguments("--start-maximized");
        options.addArguments("disable-infobars");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("disable-javascript");
        options.addArguments("--disable-gpu");
        options.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);
        options.addArguments("--headless");

        try {
            driver = new ChromeDriver(options);
            executor = (JavascriptExecutor) driver;
            actions = new Actions(driver);

            return true;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public boolean isValidCookie(String cookieFilePath) {
        File cookieFile = new File(cookieFilePath);
        if (!cookieFile.exists()) {
            System.out.println("Cookie not found");
            return false;
        }

        String url = "jdbc:sqlite:" + cookieFile.getAbsolutePath();

        // Connect to the database
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT host_key, name, expires_utc FROM cookies");

            long nowEpochMicros = System.currentTimeMillis() * 1000L;
            long chromeEpochDiffMicros = 11644473600000000L; // difference between 1601 and 1970 in microseconds

            while (rs.next()) {
                String domain = rs.getString("host_key");
                String name = rs.getString("name");
                long expiresUtc = rs.getLong("expires_utc");

                long expiresUnix = (expiresUtc - chromeEpochDiffMicros) / 1000000;
                LocalDateTime expiresTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(expiresUnix), ZoneOffset.UTC);

                boolean isExpired = expiresUtc < (nowEpochMicros + chromeEpochDiffMicros);
                System.out.printf("Cookie [%s] on [%s] expires at %s - %s\n",
                        name, domain, expiresTime, (isExpired ? "EXPIRED" : "VALID"));
                return !isExpired;
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Error reading cookies DB: " + e.getMessage());
            return false;
        }
    }

    public Document processWithoutCookie(String URL) {
        Document doc = null;
        try {
            org.jsoup.Connection connection = Jsoup.connect(URL)
                    .validateTLSCertificates(false)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .header("Content-Type", "text/html; charset=utf-8")
                    .header("Accept", "text/html,text/plain,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Language", "*")
                    .header("Accept-Encoding", "gzip, deflate, br");
            doc = connection.get();
            return doc;
        } catch (IOException e1) {
            return null;
        }
    }
}
