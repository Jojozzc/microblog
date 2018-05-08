package com.weibo.myUtil;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;

public class ActiveMQUtil {
    private final static String BROKERURL = "tcp://localhost:61616";
    private static ActiveMQUtil instance = new ActiveMQUtil();
    private static Connection connection = null;
    private ActiveMQUtil(){}
    private volatile boolean isReleased = true;

    public static ActiveMQUtil getInstance() {
        return instance;
    }

    public synchronized Connection getConnection(){
        if(!isReleased){
            return connection;
        }
        else {
            ActiveMQConnectionFactory activeMQConnectionFactory  =
                    new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                            ActiveMQConnection.DEFAULT_PASSWORD,
                            BROKERURL);
            try{
                connection = activeMQConnectionFactory.createConnection();
                connection.start();
                isReleased = false;
            }catch (Exception e){
                System.out.println("ActiveMQ连接异常");
                e.printStackTrace();
            }
            return connection;
        }
    }

    public synchronized void releaseConnection(){
        if(connection != null){
            try {
                connection.close();
                isReleased = true;
            }catch (Exception e){
                System.out.println("ActiveMQ释放异常");
            }
        }
    }
}
