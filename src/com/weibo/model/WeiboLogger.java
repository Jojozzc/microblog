package com.weibo.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.weibo.dao.LoggerDao;
import com.weibo.event.WeiboCreatedEvent;
import com.weibo.myUtil.ActiveMQUtil;
import com.weibo.myUtil.JDBCUtil;
import com.weibo.myUtil.Observable;
import com.weibo.myUtil.Observer;

import javax.jms.*;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WeiboLogger extends Observer {
    @SerializedName("id")
    private int id;

    @SerializedName("content")
    private String content;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("type")
    private String type;

    @SerializedName("userId")
    private String userId;

    @Override
    public void update(Observable observable, Object arg){
        if(arg == null)return;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        createTime = dateFormat.format(now);
        LoggerDao loggerDao = new LoggerDao();
        content = ((Weibo)observable).getContent();
        type = ((Weibo)observable).getType();
        userId = ((Weibo)observable).getUserId();
        loggerDao.writeLog(this);

    }

    @Override
    public void update(Observable observable, WeiboCreatedEvent event){
        if(event == null)return;
        Weibo weibo = event.getWeibo();
        type = weibo.getType();
        content = weibo.getContent();
        createTime = weibo.getCreateTime();
        userId = weibo.getUserId();
        sendLog();
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
            String msg = (new Gson().toJson(this)).toString();
            System.out.println(msg);
            TextMessage textMessage = session.createTextMessage(msg);
            producer.send(textMessage);
            session.commit();
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("创建日志发送失败");
        }
        finally {
            if (conn != null)
                ActiveMQUtil.getInstance().releaseConnection();
        }

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
