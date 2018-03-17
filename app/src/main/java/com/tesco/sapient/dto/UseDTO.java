package com.tesco.sapient.dto;

/**
 * Created by akhpatil on 3/16/2018.
 */

public class UseDTO {
    private int userId;
    private String userName;
    private String password;
    private int storeId;
    private String storeName;

    public UseDTO() {
    }

    public UseDTO(String userName, String password, int storeId, String storeName) {
        this.userName = userName;
        this.password = password;
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
