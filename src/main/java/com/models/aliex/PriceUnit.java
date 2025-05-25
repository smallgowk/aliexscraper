/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.aliex;

/**
 *
 * @author Admin
 */
public class PriceUnit {
    public String currency;
    public String value;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PriceUnit(String currency, String value) {
        this.currency = currency;
        this.value = value;
    }

    public PriceUnit() {
    }
}
