package com.weibo.model;

import com.weibo.dao.CounterDao;
import com.weibo.dao.UserDao;
import com.weibo.dao.WeiboDao;
import com.weibo.event.BaseEvent;
import com.weibo.event.FollowEvent;
import com.weibo.event.WeiboCreatedEvent;
import com.weibo.event.WeiboReadEvent;
import com.weibo.myUtil.Observable;
import com.weibo.myUtil.Observer;

public class WeiboCounter extends Observer{


    public void update(Observable observable, WeiboCreatedEvent event){
        UserDao userDao = new UserDao();
        userDao.updateUserMsgcountIncreaseOne(event.getWeibo().getUserId());

    }

    public void update(Observable observable, WeiboReadEvent event){
        CounterDao counterDao = new CounterDao();
        counterDao.weiboReadNumAddOne(event.getWeibo().getId());
        UserDao userDao = new UserDao();
        userDao.updateUserReadedCountIncreaseOne(event.getWeibo().getUserId());
        WeiboDao weiboDao = new WeiboDao();
        weiboDao.increaseReadNumByWeiboId(event.getWeibo().getId());

    }

    @Override
    public void update(Observable observable, FollowEvent event) {
        super.update(observable, event);
        User user = event.getUser();
        User followUser = event.getFollow();
        UserDao userDao = new UserDao();
    }
}
