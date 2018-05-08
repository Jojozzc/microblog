package com.weibo.dao;

import com.weibo.model.Weibo;
import com.weibo.model.WeiboLogger;
import com.weibo.myUtil.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoggerDao {
    public void writeLog(WeiboLogger logger){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql = "INSERT INTO logger(lcontent, lcreate_time, ltype, user_id) VALUES(?,?,?,?)";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, logger.getContent());
            preStm.setString(2, logger.getCreateTime());
            preStm.setString(3, logger.getType());
            preStm.setString(4, logger.getUserId());
            preStm.executeUpdate();
            preStm.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(conn != null)
                JDBCUtil.getInstance().releaseConn();
        }
    }

}
