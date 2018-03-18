package com.tesco.sapient.dto;

/**
 * UserDTO POJO class to manage User object
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class UserDTO {
    private int userId;
    private String userName;
    private String password;
    private int storeId;
    private String storeName;

    public UserDTO() {
    }

    public UserDTO(String userName, String password, int storeId, String storeName) {
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                '}';
    }
}
