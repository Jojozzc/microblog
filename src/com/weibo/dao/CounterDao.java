package com.weibo.dao;

import com.weibo.myUtil.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CounterDao {
    public void messageAddOne(String userId){
        // 用户的微博数目更改
        if(userId == null) return;
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql = "UPDATE user SET umessagecount = umessagecount + 1 WHERE uid = ?";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userId);
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

    public void weiboReadNumAddOne(long weiboId){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql = "UPDATE message SET mreadnum = mreadnum + 1 WHERE id = ?";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, String.valueOf(weiboId));
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

    public void weiboUpvoteCountAddOne(int weiboId){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql = "UPDATE message SET mupvote_count = mupvote_count + 1 WHERE id = ?";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, String.valueOf(weiboId));
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

    public void weiboRepostCountAddOne(int weiboId){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql = "UPDATE message SET mrepost_count = mrepost_count + 1 WHERE id = ?";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, String.valueOf(weiboId));
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
