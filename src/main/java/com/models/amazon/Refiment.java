/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.amazon;

import java.util.List;

/**
 *
 * @author duyuno
 */
public class Refiment {
    public String name;
    public String attribute;
    public String nodeId;
//    public List<String> values;

    public Refiment(String name, String attribute, String nodeId) {
        this.name = name;
        this.attribute = attribute;
        this.nodeId = nodeId;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

//    public List<String> getValues() {
//        return values;
//    }
//
//    public void setValues(List<String> values) {
//        this.values = values;
//    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    
    
    
}
