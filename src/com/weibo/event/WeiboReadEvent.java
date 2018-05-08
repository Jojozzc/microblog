package com.weibo.event;

import com.weibo.model.Weibo;


public class WeiboReadEvent extends BaseEvent{
    private Weibo weibo = null;
    public WeiboReadEvent(Weibo weibo){
        super();
        this.weibo = weibo;
    }

    public Weibo getWeibo() {
        return weibo;
    }
}
