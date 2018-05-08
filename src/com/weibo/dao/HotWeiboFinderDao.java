package com.weibo.dao;

import com.weibo.model.User;
import com.weibo.model.Weibo;
import com.weibo.myUtil.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HotWeiboFinderDao {
    private static final int INDEX_ID = 1;
    private static final int INDEX_MCONTENT = 2;
    private static final int INDEX_USER_ID = 3;
    private static final int INDEX_MREADNUM = 4;
    private static final int INDEX_MTYPE = 5;
    private static final int INDEX_MTO = 6;
    private static final int INDEX_MCOMMENT_COUNT = 7;
    private static final int INDEX_MUPVOTE_COUNT = 8;
    private static final int INDEX_MRESPOST_COUNT = 9;
    private static final int INDEX_MCREATE_TIME = 10;
    public List<User> queryHotUserByReadNum(int top){
        if(top < 0) throw new RuntimeException("top值必须非负");
        String sql = " select user_id, sum(mreadnum) as s from message group by user_id order by s desc limit ?";
        PreparedStatement prestm = null;
        List<User> users = new ArrayList<User>();
        List<String> userids = new ArrayList<String>(top);
        List<Integer> readNums = new ArrayList<Integer>(top);
        Connection conn = null;
        UserDao userDao = new UserDao();
        ResultSet rstSet = null;
        String uid;
        try {
            conn = JDBCUtil.getInstance().getConnection();
            prestm = conn.prepareCall(sql);
            prestm.setInt(1, top);
            rstSet = prestm.executeQuery();
            while (rstSet.next()){
                uid = rstSet.getString(1);
                userids.add(uid);
                readNums.add(rstSet.getInt(2));
                users.add(userDao.queryById(uid));
            }

        }catch (Exception e){
            System.out.println(e.toString() + "查询热门用户异常");
        }
        finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return users;
    }

    public List<Weibo> queryTopWeiboByUserId(String uid, int top){
        // 返回已经排按时间降序排序的微博（由最近到最远）
        if(top < 0) throw new RuntimeException("top值必须非负");
        String sql = "select * from message where user_id = ? order by mcreate_time desc limit ?";
        PreparedStatement prestm = null;
        List<Weibo> weibos = new ArrayList<Weibo>();
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtil.getInstance().getConnection();
            prestm = conn.prepareCall(sql);
            prestm.setString(1, uid);
            prestm.setInt(2, top);
            resultSet = prestm.executeQuery();
            while (resultSet.next()){
                Weibo weibo = new Weibo(resultSet.getString(INDEX_MCONTENT), resultSet.getString(INDEX_USER_ID),
                        resultSet.getString(INDEX_MTYPE), resultSet.getInt(INDEX_MTO));
                weibo.setCommentCount(resultSet.getInt(INDEX_MCOMMENT_COUNT));
                weibo.setReadnum(resultSet.getInt(INDEX_MREADNUM));
                weibo.setId(resultSet.getInt(INDEX_ID));
                weibo.setRepostCount(resultSet.getInt(INDEX_MRESPOST_COUNT));
                weibo.setUpvoteCount(resultSet.getInt(INDEX_MUPVOTE_COUNT));
                weibo.setCreateTime(resultSet.getString(INDEX_MCREATE_TIME));
                weibos.add(weibo);
            }
        }catch (Exception e){
            System.out.println("热门微博查询异常");
        }
        finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return weibos;
    }


    public void queryHotWeiboByRange(){

    }


}
