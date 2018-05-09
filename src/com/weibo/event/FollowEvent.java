package com.weibo.event;

import com.weibo.model.User;

public class FollowEvent extends BaseEvent{
    private User user = null;
    private User follow = null;

    public FollowEvent(User user, User follow){
        this.user = user;
        this.follow = follow;
    }

    public User getFollow() {
        return follow;
    }

    public User getUser() {
        return user;
    }
}
