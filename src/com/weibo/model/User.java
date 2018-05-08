package com.weibo.model;

public class User {
    private String id;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private int messageCount;
    private int fansCount;
    private int followsCount;
    private String nickName = "";
    private long readedCount = 0;
    public User(String id, String userName, String email, String phone, String password,
                int messageCount, int fansCount, int followsCount){
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.messageCount = messageCount;
        this.fansCount = fansCount;
        this.followsCount = followsCount;
        this.nickName = userName;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
    public int getMessageCount() {
        return messageCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }
    public int getFansCount() {
        return fansCount;
    }

    public void setFollowsCount(int followsCount) {
        this.followsCount = followsCount;
    }
    public int getFollowsCount() {
        return followsCount;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }

    public long getReadedCount() {
        return readedCount;
    }

    public void setReadedCount(long readedCount) {
        this.readedCount = readedCount;
    }

    @Override
    public String toString() {
        return "[账号：" + id + "] " + "[用户名：" + userName + "]";

    }
}
