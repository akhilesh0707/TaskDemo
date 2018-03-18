package com.tesco.sapient.dto;

/**
 * ItemTypeDTO POJO class to manage Item type
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
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
