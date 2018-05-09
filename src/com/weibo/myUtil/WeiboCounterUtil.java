package com.weibo.myUtil;

import com.weibo.model.WeiboCounter;

public class WeiboCounterUtil {
    private static WeiboCounter counter = new WeiboCounter();
    private static WeiboCounterUtil instance = new WeiboCounterUtil();
    private WeiboCounterUtil(){

    }

    public WeiboCounter getCounter() {
        return counter;
    }

    public static WeiboCounterUtil getInstance() {
        return instance;
    }
}
