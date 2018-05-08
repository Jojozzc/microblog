package com.weibo.event;

import com.weibo.model.Weibo;

public class WeiboCreatedEvent extends BaseEvent {
    // 微博发送事件
    private Weibo weibo = null;
    public WeiboCreatedEvent(Weibo weibo){
        super();
        this.weibo = weibo;
    }
    public Weibo getWeibo() {
        return weibo;
    }
}
