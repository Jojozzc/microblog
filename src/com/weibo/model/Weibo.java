package com.weibo.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.weibo.dao.WeiboDao;
import com.weibo.event.WeiboCreatedEvent;
import com.weibo.event.WeiboReadEvent;
import com.weibo.myUtil.*;

import javax.jms.*;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class Weibo extends Observable {
    public static final String TYPE_ADD_WEIBO = "crt";
    public static final String TYPE_COMMENT_WEIBO = "comt";
    public static final String TYPE_VOTEUP_WEIBO = "vote";
    public static final String TYPE_DEFAULT = "def";

    @SerializedName("id")
    private long id;

    @SerializedName("userid")
    private String userId;

    @SerializedName("content")
    private String content = "";

    @SerializedName("type")
    private String type;

    @SerializedName("to")
    private long to;// 当微博为评论时to有效

    @SerializedName("readnum")
    private int readnum = 0;

    @SerializedName("commentCount")
    private int commentCount = 0;

    @SerializedName("upvoteCount")
    private int upvoteCount = 0;

    @SerializedName("repostCount")
    private int repostCount = 0;

    @SerializedName("nickName")
    private String nickName = "";

    @SerializedName("createTime")
    private String createTime = "";

    public Weibo(String content, String userId, String type, long to){
        this.content = content;
        this.userId = userId;
        if(type == null) type = TYPE_DEFAULT;
        switch (type){
            case TYPE_ADD_WEIBO:
                this.to = -1;
                this.type = type;
                break;
            case TYPE_COMMENT_WEIBO:
                this.type = type;
                this.to = to;
                break;
            case TYPE_VOTEUP_WEIBO:
                this.type = type;
                this.content = "upvote";
                this.to = to;
                break;
                default:
                    this.type = TYPE_DEFAULT;
                    this.to = -1;
        }
    }

    public boolean read(){
        try {
            WeiboReadEvent weiboReadEvent = new WeiboReadEvent(this);
            notifyObservers(weiboReadEvent);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        finally {
            return false;
        }
    }
    public synchronized boolean send(){
        createTime = TimeUtil.getInstance().getNowDateAndTime();
        try{
            WeiboDao weiboDao = new WeiboDao();
            weiboDao.addWeibo(this);
            WeiboCreatedEvent event = new WeiboCreatedEvent(this);
            notifyObservers(event);
            System.out.println("微博发送成功");
            return true;
        }catch (Exception e){
            System.out.println("微博发送失败");
            return false;
        }
    }
    private void sendLog(){
        javax.jms.Connection conn = ActiveMQUtil.getInstance().getConnection();
        Session session = null;
        Destination destination = null;
        MessageProducer producer = null;
        try {
            session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("LoggerQueue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            WeiboLogger weiboLogger = new WeiboLogger();
            weiboLogger.setType(type);
            weiboLogger.setContent(content);
            weiboLogger.setUserId(userId);
            weiboLogger.setCreateTime(createTime);
            String msg = (new Gson().toJson(weiboLogger)).toString();
            System.out.println(msg);
            TextMessage textMessage = session.createTextMessage(msg);
            producer.send(textMessage);
            session.commit();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        finally {
            if (conn != null)
                ActiveMQUtil.getInstance().releaseConnection();
        }

    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReadnum() {
        return readnum;
    }

    public void setReadnum(int readnum) {
        this.readnum = readnum;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getUpvoteCount() {
        return upvoteCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setUpvoteCount(int upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public int getRepostCount() {
        return repostCount;
    }

    public void setRepostCount(int repostCount) {
        this.repostCount = repostCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("微博内容：" + content);
        res.append("[作者：" + userId + "]");
        res.append("[评论数：" + commentCount + "]");
        res.append("[赞数：" + upvoteCount + "]");
        res.append("[已读：" + readnum + "]");
        res.append("[转发：" + repostCount + "]");
        res.append("(时间：" + createTime + ")");
        return res.toString();
    }
}
