package com.weibo.myUtil;


import com.weibo.dao.CounterDao;
import com.weibo.event.BaseEvent;
import com.weibo.event.WeiboCreatedEvent;
import com.weibo.event.WeiboReadEvent;

public class Observer {
//    public void update(Observable observable, Class<? extends BaseEvent> event){
//
//    }
    public void update(Observable observable, Object arg){

    }

    public void update(Observable observable, WeiboCreatedEvent event){

    }
    public void update(Observable observable, WeiboReadEvent event){

    }

}
