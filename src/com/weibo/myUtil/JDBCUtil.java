package com.weibo.myUtil;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
    private static JDBCUtil instance = new JDBCUtil();
    private JDBCUtil(){}

    // DB user name
    private static String USERNAME;

    // user password
    private static String PASSWORD;

    private static String DRIVER;

    private static String URL;

    private Connection connection;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;

    static {
        loadConfig();
    }

    public static JDBCUtil getInstance() {
        return instance;
    }

    private static void loadConfig(){
        try{
            InputStream inStream = JDBCUtil.class.getResourceAsStream("/jdbc.properties");
            Properties prop = new Properties();
            prop.load(inStream);
            USERNAME = prop.getProperty("jdbc.username");
            PASSWORD = prop.getProperty("jdbc.password");
            DRIVER = prop.getProperty("jdbc.driver");
            URL = prop.getProperty("jdbc.url");
        }catch (Exception e){
            throw new RuntimeException("数据库配置文件读取异常", e);
        }
    }

    public Connection getConnection() {
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (Exception e){
            throw new RuntimeException("数据库连接错误", e);
        }
        return connection;
    }

    public void releaseConn() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
