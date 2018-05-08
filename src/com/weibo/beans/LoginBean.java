package com.weibo.beans;

import com.google.gson.annotations.SerializedName;

public class LoginBean {
    @SerializedName("username")
    private String userName = null;

    @SerializedName("password")
    private String password = null;

    public LoginBean(){

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
