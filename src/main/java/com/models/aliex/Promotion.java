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
public class Promotion {
    public PriceUnit maxAmount;
    public PriceUnit minAmount;
    public String discount;
    public TimeLeft timeLeft;
    public long stock;

    public PriceUnit getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(PriceUnit maxAmount) {
        this.maxAmount = maxAmount;
    }

    public PriceUnit getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(PriceUnit minAmount) {
        this.minAmount = minAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public TimeLeft getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(TimeLeft timeLeft) {
        this.timeLeft = timeLeft;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
    
    
}
