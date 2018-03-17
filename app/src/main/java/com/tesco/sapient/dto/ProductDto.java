package com.tesco.sapient.dto;

/**
 * Created by Aki on 3/17/2018.
 */

public class ProductDto {
    private int id;
    private String name;
    private int barCode;
    private double price;

    public ProductDto() {
    }

    public ProductDto(String name, int barCode, double price) {
        this.name = name;
        this.barCode = barCode;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }
}
