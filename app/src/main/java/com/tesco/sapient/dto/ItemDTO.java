package com.tesco.sapient.dto;

/**
 * Created by Aki on 3/17/2018.
 */

public class ItemDTO {
    private int id;
    private int itemBarCode;
    private int itemTypeCode;
    private String itemTypeName;
    private int itemQuantity;
    private double itemPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemBarCode() {
        return itemBarCode;
    }

    public void setItemBarCode(int itemBarCode) {
        this.itemBarCode = itemBarCode;
    }

    public int getItemTypeCode() {
        return itemTypeCode;
    }

    public void setItemTypeCode(int itemTypeCode) {
        this.itemTypeCode = itemTypeCode;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
