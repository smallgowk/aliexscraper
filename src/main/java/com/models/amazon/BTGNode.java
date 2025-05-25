/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.amazon;

import java.util.ArrayList;

/**
 *
 * @author duyuno
 */
public class BTGNode {

    public static final String ITEM_TYPE_KEY_FIELD_NAME = "item_type_keyword";
    public static final String AUDIENCE_KEY_FIELD_NAME = "target_audience_keywords";
    public static final String DEPART_FIELD_NAME = "department_name";

    public String nodeName;
    public String nodeId;
    public String departMentName;
    public String keyword;
    public String audienceKeyword;
    public String nodePath;
    public String parentBranch;
    public String btgFile;
    public String templateFile;
    
    public ArrayList<Refiment> listRefiment;

    public BTGNode(String nodePath, String nodeName, String nodeId, String keyword, String audienceKeys, String departMentName, String parentBranch, String btgFile, String templateFile) {
        this.nodePath = nodePath;
        this.nodeName = nodeName;
        this.nodeId = nodeId;
        this.parentBranch = parentBranch;
        this.btgFile = btgFile;
        this.templateFile = templateFile;
        this.keyword = processValues(keyword, ITEM_TYPE_KEY_FIELD_NAME);
        this.audienceKeyword = processValues(audienceKeys, AUDIENCE_KEY_FIELD_NAME);
        this.departMentName = processValues(departMentName, DEPART_FIELD_NAME);
    }

    public String getTemplateFile() {
        return templateFile;
//        return "AmazonProductTemplate.xlsx";
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

    public String getAudienceKeyword() {
        return audienceKeyword;
    }

    public void setAudienceKeyword(String audienceKeyword) {
        this.audienceKeyword = audienceKeyword;
    }
    
    

    public ArrayList<Refiment> getListRefiment() {
        return listRefiment;
    }

    public String getParentBranch() {
        return parentBranch;
    }

    public void setParentBranch(String parentBranch) {
        this.parentBranch = parentBranch;
    }

    public String getBtgFile() {
        return btgFile;
    }

    public void setBtgFile(String btgFile) {
        this.btgFile = btgFile;
    }
    
    public void setListRefiment(ArrayList<Refiment> listRefiment) {
        this.listRefiment = listRefiment;
    }

    public static String processValues(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        if (!value.contains(fieldName)) {
            value = null;
        } else {
            value = value.substring(value.indexOf(fieldName) + fieldName.length() + 1, value.length());
            
            if(value.contains("OR")) {
                String[] valueParts = value.split("OR");
                value = valueParts[0].substring(1, valueParts[0].length()).trim();
            }
        }
        
        return value;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDepartMentName() {
        return departMentName;
    }

    public void setDepartMentName(String departMentName) {
        this.departMentName = departMentName;
    }

    public String toInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeId).append(" - ").append(nodePath).append(" - ").append(nodeName);
        if(keyword != null) {
            sb.append(" - ").append(keyword);
        }
        
        if(departMentName != null) {
            sb.append(" - ").append(departMentName);
        }
        return sb.toString();
    }
}
