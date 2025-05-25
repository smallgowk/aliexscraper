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
public class Price {
    public PriceUnit maxAmount;
    public PriceUnit maxAmountPerPiece;
    public PriceUnit minAmount;
    public PriceUnit minAmountPerPiece;
    public int minimumPurchase;
    public String processingTime;

    public PriceUnit getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(PriceUnit maxAmount) {
        this.maxAmount = maxAmount;
    }

    public PriceUnit getMaxAmountPerPiece() {
        return maxAmountPerPiece;
    }

    public void setMaxAmountPerPiece(PriceUnit maxAmountPerPiece) {
        this.maxAmountPerPiece = maxAmountPerPiece;
    }

    public PriceUnit getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(PriceUnit minAmount) {
        this.minAmount = minAmount;
    }

    public PriceUnit getMinAmountPerPiece() {
        return minAmountPerPiece;
    }

    public void setMinAmountPerPiece(PriceUnit minAmountPerPiece) {
        this.minAmountPerPiece = minAmountPerPiece;
    }

    public int getMinimumPurchase() {
        return minimumPurchase;
    }

    public void setMinimumPurchase(int minimumPurchase) {
        this.minimumPurchase = minimumPurchase;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }
    
    
}
