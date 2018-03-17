package com.tesco.sapient.dto;

/**
 * Created by Aki on 3/16/2018.
 */

public class ItemTypeDTO {
    private int id;
    private String name;
    public ItemTypeDTO() {
    }

    public ItemTypeDTO(String name) {
        this.name = name;
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
}
