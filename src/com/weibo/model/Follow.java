package com.weibo.model;

import com.google.gson.annotations.SerializedName;

public class Follow {
    @SerializedName("name")
    private String name = "";


    public Follow(String name){
        this.name = name;
    }
    public void addFollow(String userId, String follow){
        if(userId == null || follow == null){
            throw new RuntimeException("用户id不能为空");

        }

    }
}
