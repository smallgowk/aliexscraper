package com.phanduy.aliexscrap.utils;

import com.phanduy.aliexscrap.model.StoreInfo;
import com.phanduy.aliexscrap.model.response.ItemListResponse;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliexScraper extends CrawlerMachine {

    private static AliexScraper aliexScraper;
    public static AliexScraper getInstance() {
        if (aliexScraper == null) {
            aliexScraper = new AliexScraper();
        }
        return aliexScraper;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String crawlSellerId(String storeId) {
        Document document = processWithoutCookie("https://www.aliexpress.com/store/" + storeId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(CrawlerMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
        String pageSource = document.html();
        int index = pageSource.indexOf("sellerId");
        String r1 = pageSource.substring(index, index + "sellerId".length() + 1 + 15);
        return r1.replaceAll("[^0-9]", "");
    }

    public ItemListResponse readCache(String owner,
                                      String storeId,
                                      String categoryId) {
        Path filePath = Paths.get(owner, storeId, categoryId + ".txt");
        if (!Files.exists(filePath)) return null;

        try {
            List<String> lines = Files.readAllLines(filePath);
            return lines.isEmpty() ? null : new ItemListResponse(lines);
        } catch (IOException e) {
            throw new RuntimeException("Error reading cache", e);
        }
    }

    public String buildCategoryUrl(String storeId, String productGroupId, String spm) {
        if (productGroupId != null && !productGroupId.isEmpty()) {
            return String.format(
                    "https://www.aliexpress.com/store/%s/pages/all-items.html?" +
                            "productGroupId=%s&spm=%s&storeId=%s&sortType=bestmatch_sort&shop_sortType=bestmatch_sort",
                    storeId, productGroupId, spm, storeId
            );
        } else {
            return String.format(
                    "https://www.aliexpress.com/store/%s?" +
                            "spm=%s&sortType=bestmatch_sort",
                    storeId, spm
            );
        }
    }

    public void crawlProduct(String productId) {
        driver.get("https://www.aliexpress.com/item/" + productId + ".html");
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'pdp-body-top-right')]")));
//            WebElement shipInfo = driver.findElement(By.xpath("//div[contains(@class, 'pdp-body-top-right')]"));
            List<WebElement> choiceElements = driver.findElements(By.cssSelector("div[class^='choice-mind--wrap']"));
            if (!choiceElements.isEmpty()) {
                System.out.println("✅ Có tồn tại lựa chọn (choice)");
            } else {
                System.out.println("❌ Không có lựa chọn (choice)");
            }

        } catch (Exception e) {
            System.err.println("❌ Error during crawling: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }


    public ItemListResponse crawlData(String storeId,
                                      String categoryId,
                                      String url) {
        Set<String> itemIds = new HashSet<>();

        driver.get(url);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        Pattern pattern = Pattern.compile(".*/item/(\\d+)\\.html");

        String regex = "/item/(\\d+)\\.html";
        Pattern pattern = Pattern.compile(regex);

        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@currentpage]")));
            int page = 1;

            while (true) {
                List<WebElement> links = driver.findElements(By.tagName("a"));
                System.out.println("Links: " + links.size());
                for (WebElement link : links) {
                    String href = link.getAttribute("href");
                    if (href != null && !href.isEmpty() && href.contains("/item/") && href.contains("aliexpress.com")) {
                        Matcher matcher = pattern.matcher(href);

                        if (matcher.find()) {
                            String itemId = matcher.group(1); // Lấy itemId
                            itemIds.add(itemId);
                        }
                    }

                }

                System.out.println("Crawling page " + page + " " + itemIds);

                WebElement pageInfo = driver.findElement(By.xpath("//div[@currentpage]"));
                int currentPage = Integer.parseInt(pageInfo.getAttribute("currentpage"));
                int totalPage = Integer.parseInt(pageInfo.getAttribute("totalpage"));
                page = currentPage;

                if (currentPage >= totalPage) break;

                List<WebElement> nextButtons = new WebDriverWait(driver, 10)
                        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                                By.xpath("//div[contains(@style, 'background-image')]")));

                if (!nextButtons.isEmpty()) {
                    System.out.println("Next button found!");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButtons.get(nextButtons.size() - 1));
                    Thread.sleep(2000);
                    System.out.println("Current url: " + driver.getCurrentUrl());
                } else {
                    System.out.println("Next button not found");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error during crawling: " + e.getMessage());
        } finally {
            driver.quit();
        }

        List<String> itemList = new ArrayList<>(itemIds);
        saveToFile(storeId, categoryId, itemList);
        return new ItemListResponse(itemList);
    }

    public StoreInfo crawlStoreInfo(String storeId) {
        driver.get("https://www.aliexpress.com/store/" + storeId);

        StoreInfo storeInfo = new StoreInfo();
        try {
            Thread.sleep(1000); // Chờ trang tải

            WebElement widgetDiv = driver.findElement(By.xpath("//div[@data-widgetid and @data-modulename]"));
            storeInfo.setWidgetId(widgetDiv.getAttribute("data-widgetid"));
            storeInfo.setModuleName(widgetDiv.getAttribute("data-modulename"));

            List<WebElement> scripts = driver.findElements(By.tagName("script"));
            for (WebElement script : scripts) {
                String content = script.getAttribute("innerHTML");
                if (content != null && content.contains("window._seo_description_content_")) {
                    Matcher matcher = Pattern.compile("window\\._seo_description_content_\\s*=\\s*(\\{.*?\\});", Pattern.DOTALL)
                            .matcher(content);
                    if (matcher.find()) {
                        String json = matcher.group(1);
                        // Cần dùng thư viện JSON parser như Jackson để parse phần này
                        // (giả định bạn dùng Jackson hoặc Gson sau)
                    }
                    break;
                }
            }

            for (WebElement a : driver.findElements(By.xpath("//a[contains(@data-href, '/store/')]"))) {
                String href = a.getAttribute("data-href");
                if (href != null && href.contains("/store/" + storeId)) {
                    String storeName = a.getText().trim();
                    if (!storeName.isEmpty()) {
                        storeInfo.storeName = storeName;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error while parsing store info: " + e.getMessage());
        } finally {
            driver.quit();
        }

        return storeInfo;
    }


    private void saveToFile(String storeId, String categoryId, List<String> items) {
        Path categoryFolder = Paths.get(storeId);
        try {
            Files.createDirectories(categoryFolder);
            Path filePath = categoryFolder.resolve(categoryId + ".txt");
            Files.write(filePath, items);
            System.out.println("✅ Saved to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }
    }


}
