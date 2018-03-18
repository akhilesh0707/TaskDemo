package com.tesco.sapient.dto;

/**
 * ProjectDTO POJO class to manage product barcode
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class ProductDTO {
    private int id;
    private String name;
    private int barCode;
    private double price;

    public ProductDTO() {
    }

    public ProductDTO(String name, int barCode, double price) {
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
