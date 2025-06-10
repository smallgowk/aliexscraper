/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanduy.aliexscrap.model.aliex.store;

import com.phanduy.aliexscrap.config.Configs;
import com.phanduy.aliexscrap.utils.StringUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author PhanDuy
 */
public class AliexStoreInfo extends BaseStoreInfo{
    public String main_key;
    public String tip;
    public String reasons;
    public String description;
    public String storeSign;
    public String accNo;
    public String vpsIp;
    public String category;
    public boolean isOnlyUS;
    public String productId = null;
    public String storeId = null;
    public String query = null;
    public String info = null;

    public String getStoreSign() {
        return storeSign;
    }

    public void setStoreSign(String storeSign) {
        this.storeSign = storeSign;
    }

    public void setVpsIp(String vpsIp) {
        this.vpsIp = vpsIp;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getMain_key() {
        return main_key;
    }

    public void setMain_key(String main_key) {
        this.main_key = main_key;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsOnlyUS() {
        return isOnlyUS;
    }

    public void setIsOnlyUS(boolean isOnlyUS) {
        this.isOnlyUS = isOnlyUS;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setStoreId(String storeId) {
        if (!StringUtils.isEmpty(storeId)) {
            this.storeId = storeId;
            this.storeSign = storeId;
        }
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    public void setQuery(String query) {
        if (query == null) return;
        this.query = query;
        this.storeSign = query.replaceAll(Pattern.quote(" "), "_");
        info = "Keyword: " + query;
    }
    
    public String prefix;
    public String getPrefix() {

        if (prefix != null) {
            return prefix;
        }

        String brandBrief = StringUtils.getStringBrief(brandName);
        if (brandBrief.length() > 2) {
            brandBrief = brandBrief.substring(0, 2);
        }
        prefix = brandBrief.toUpperCase();
        return prefix;
    }
    
    public String genExcelFileNameWithPage(int pageIndex, boolean isError) {
        if (pageIndex == 0) {
            pageIndex = 1;
        }

        File file = new File(isError ? Configs.ERROR_PRODUCT_PATH : Configs.PRODUCT_DATA_PATH + accNo);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + "Aliex");
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + storeSign);

        if (!file.exists()) {
            file.mkdir();
        }

        return file.getPath() + Configs.pathChar + accNo + "_" + storeSign + (isError ? "_error" : "") + "_page" + pageIndex + ".xlsx";
    }
    
    public String getLocalImageFolder() {
        File file = new File(Configs.IMAGE_DATA_PATH + accNo + Configs.pathChar + storeSign);
        if (file.exists()) {
            return file.getPath() + Configs.pathChar;
        }
        file = new File(Configs.IMAGE_DATA_PATH + accNo);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(file.getPath() + Configs.pathChar + storeSign);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getPath() + Configs.pathChar;
    }
    
    public String getLocalImageFolderForProductId(String root, String productId) {
        File file = new File(root);
        file = new File(file.getPath() + Configs.pathChar + productId);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getPath() + Configs.pathChar;
    }
    
    public String getImageVpsFolder() {
        return "http://" + Configs.vpsIp + "/" + accNo + "/" + storeSign + "/";
    }
    
    public String genExcelFileNameForStore(boolean isError) {
        File file = new File(isError ? Configs.ERROR_PRODUCT_PATH : Configs.PRODUCT_DATA_PATH + accNo);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + (isError ? "Errors" : "Aliex"));
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + storeSign);

        if (!file.exists()) {
            file.mkdir();
        }
        return file.getPath() + Configs.pathChar + accNo + "_" + storeSign + ".xlsx";
    }
    
    public String getFolderPath() {
        File file = new File(Configs.PRODUCT_DATA_PATH + accNo);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + "Aliex");
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + storeSign);

        if (!file.exists()) {
            file.mkdir();
        }
        
        return file.getPath();
    }
    
    public String getStoreFolderPath() {
        File file = new File(Configs.PRODUCT_DATA_PATH + accNo);

        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file.getPath() + Configs.pathChar + "Aliex");
        if (!file.exists()) {
            file.mkdir();
        }

        return file.getPath();
    }
    
    public String getKeyCache(HashMap<String, String> toolParams) {
        StringBuilder sb = new StringBuilder(storeSign);
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        sb.append("_").append(sdf.format(new Date())).append("_").append(hashCode()).append("_").append(toolParams.hashCode());
        return sb.toString();
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();

        appendIfValid(sb, main_key);
        appendIfValid(sb, tip);
        appendIfValid(sb, reasons);
        appendIfValid(sb, description);
        appendIfValid(sb, storeSign);
        appendIfValid(sb, accNo);
        appendIfValid(sb, vpsIp);
        appendIfValid(sb, category);
        appendIfValid(sb, storeId);
        if (!StringUtils.isEmpty(query)) {
            appendIfValid(sb, query);
        } else {
            appendIfValid(sb, productId);
        }
        appendIfValid(sb, prefix);
        appendIfValid(sb, brandName); // từ BaseStoreInfo nếu có

        // boolean thì vẫn có giá trị true/false nên nên thêm thẳng
        sb.append(isOnlyUS);

        return sb.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AliexStoreInfo other = (AliexStoreInfo) obj;
        if (this.isOnlyUS != other.isOnlyUS) {
            return false;
        }
        if (!Objects.equals(this.main_key, other.main_key)) {
            return false;
        }
        if (!Objects.equals(this.tip, other.tip)) {
            return false;
        }
        if (!Objects.equals(this.reasons, other.reasons)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.storeSign, other.storeSign)) {
            return false;
        }
        if (!Objects.equals(this.accNo, other.accNo)) {
            return false;
        }
        if (!Objects.equals(this.vpsIp, other.vpsIp)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.storeId, other.storeId)) {
            return false;
        }
        if (!Objects.equals(this.query, other.query)) {
            return false;
        }
        return Objects.equals(this.prefix, other.prefix);
    }

// Hàm phụ để kiểm tra null và rỗng
    private void appendIfValid(StringBuilder sb, String value) {
        if (value != null && !value.trim().isEmpty()) {
            sb.append(value.trim());
        }
    }
}
