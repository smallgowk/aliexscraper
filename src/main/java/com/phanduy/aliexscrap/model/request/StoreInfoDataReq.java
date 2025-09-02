package com.phanduy.aliexscrap.model.request;

public class StoreInfoDataReq {
    public String link;
    public String storeName;
    public String keyword;
    public String age;
    public String score;
    public String followers;
    public String sold;

    public StoreInfoDataReq(String link, String storeName, String keyword, String age, String score, String followers, String sold) {
        this.link = link;
        this.storeName = storeName;
        this.keyword = keyword;
        this.age = age;
        this.score = score;
        this.followers = followers;
        this.sold = sold;
    }
}
