package com.weibo.dao;

import com.weibo.model.User;
import com.weibo.model.Weibo;
import com.weibo.model.WeiboLogger;
import com.weibo.myUtil.JDBCUtil;
import com.weibo.myUtil.TimeUtil;

import javax.sql.rowset.JdbcRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WeiboDao {
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

    public synchronized void addWeibo(Weibo weibo){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql = "INSERT INTO message(mcontent, user_id, mreadnum, mtype, mto, mcreate_time) VALUES(?,?,?,?,?,?)";
        PreparedStatement preStm = null;

        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, weibo.getContent());
            preStm.setString(2, weibo.getUserId());
            preStm.setString(3, String.valueOf(weibo.getReadnum()));
            preStm.setString(4, weibo.getType());
            preStm.setString(5, String.valueOf(weibo.getTo()));
            preStm.setString(6, weibo.getCreateTime());
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

    public void update(Weibo weibo) throws Exception{
        // 微博内容不可变
        String sql = "update message set mcomment_count = ?, mreadnum = ? , mupvote_count," +
              "mrepost_count = ?" + "where id = ?";
        PreparedStatement preparedStatement=null;
        Connection conn = null;
        try{
            conn = JDBCUtil.getInstance().getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(weibo.getCommentCount()));
            preparedStatement.setString(2, String.valueOf(weibo.getReadnum()));
            preparedStatement.setString(3, String.valueOf(weibo.getUpvoteCount()));
            preparedStatement.setString(4, String.valueOf(weibo.getRepostCount()));
            preparedStatement.setString(5, String.valueOf(weibo.getId()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(Exception e) {
            throw new RuntimeException("更新微博失败", e);
        }
    }

    public void delete(long wid){

    }

    public Weibo queryById(long id)throws Exception{
        Weibo weibo = null;
        String sql="select * from message where id = ?";
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        try{
            conn = JDBCUtil.getInstance().getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet rSet=preparedStatement.executeQuery();
            if(rSet.next()){
                weibo = new Weibo(rSet.getString(INDEX_MCONTENT), rSet.getString(INDEX_USER_ID),
                        rSet.getString(INDEX_MTYPE), rSet.getInt(INDEX_MTO));
                weibo.setCommentCount(rSet.getInt(INDEX_MCOMMENT_COUNT));
                weibo.setReadnum(rSet.getInt(INDEX_MREADNUM));
                weibo.setId(rSet.getInt(INDEX_ID));
                weibo.setRepostCount(rSet.getInt(INDEX_MRESPOST_COUNT));
                weibo.setUpvoteCount(rSet.getInt(INDEX_MUPVOTE_COUNT));
                weibo.setCreateTime(rSet.getString(INDEX_MCREATE_TIME));
            }
            rSet.close();
            preparedStatement.close();
        }
        catch(Exception e){
            throw new Exception("操作出现异常");
        }
        finally {
            JDBCUtil.getInstance().releaseConn();
        }

        return weibo;
    }

    public List<Weibo> queryByUserId(String uid){
        List<Weibo> resList = new ArrayList<Weibo>();
        String sql="select * from message where user_id = ?";
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        try {
            conn = JDBCUtil.getInstance().getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(uid));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Weibo weibo = new Weibo(resultSet.getString(INDEX_MCONTENT), resultSet.getString(INDEX_USER_ID),
                        resultSet.getString(INDEX_MTYPE), resultSet.getInt(INDEX_MTO));
                weibo.setCommentCount(resultSet.getInt(INDEX_MCOMMENT_COUNT));
                weibo.setReadnum(resultSet.getInt(INDEX_MREADNUM));
                weibo.setId(resultSet.getInt(INDEX_ID));
                weibo.setRepostCount(resultSet.getInt(INDEX_MRESPOST_COUNT));
                weibo.setUpvoteCount(resultSet.getInt(INDEX_MUPVOTE_COUNT));
                weibo.setCreateTime(resultSet.getString(INDEX_MCREATE_TIME));
                resList.add(weibo);
            }
        }catch (Exception e){

        }
        finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }

        return resList;
    }

    public List<Weibo> queryByUserIdOrderByDateRange(String userId, int start, int end, boolean desc){
        if(start < 0 || start > end) return null;
        String sql = desc?"select * from message where user_id = ? order by mcreate_time desc limit ?,?"
                : "select * from message where user_id = ? order by mcreate_time limit ?,?";
        List<Weibo> res = new ArrayList<Weibo>(end - start + 1);
        PreparedStatement prestm = null;
        Connection conn = JDBCUtil.getInstance().getConnection();
        try {
            prestm = conn.prepareStatement(sql);
            prestm.setString(1, userId);
            prestm.setInt(2, start);
            prestm.setInt(3, end - start + 1);
            ResultSet resultSet =  prestm.executeQuery();
            while (resultSet.next()){
                Weibo weibo = new Weibo(resultSet.getString(INDEX_MCONTENT), resultSet.getString(INDEX_USER_ID),
                        resultSet.getString(INDEX_MTYPE), resultSet.getInt(INDEX_MTO));
                weibo.setCommentCount(resultSet.getInt(INDEX_MCOMMENT_COUNT));
                weibo.setReadnum(resultSet.getInt(INDEX_MREADNUM));
                weibo.setId(resultSet.getInt(INDEX_ID));
                weibo.setRepostCount(resultSet.getInt(INDEX_MRESPOST_COUNT));
                weibo.setUpvoteCount(resultSet.getInt(INDEX_MUPVOTE_COUNT));
                weibo.setCreateTime(resultSet.getString(INDEX_MCREATE_TIME));
                res.add(weibo);
            }
        }catch (Exception e){
            System.out.println("queryByUserIdOrderByDateRange");
            e.printStackTrace();

        }finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return res;

    }

    public List<Weibo> queryByUserIdOrderByDate(String userId, int top, boolean desc){
        String sql = desc?"select * from message where user_id = ? order by mcreate_time desc limit ?"
                : "select * from message where user_id = ? order by mcreate_time limit ?";
        PreparedStatement prestm = null;
        Connection conn = JDBCUtil.getInstance().getConnection();
        List<Weibo> res = new ArrayList<Weibo>();
        try {
            prestm = conn.prepareStatement(sql);
            prestm.setString(1, userId);
            prestm.setInt(2, top);
            ResultSet resultSet =  prestm.executeQuery();
            while (resultSet.next()){
                Weibo weibo = new Weibo(resultSet.getString(INDEX_MCONTENT), resultSet.getString(INDEX_USER_ID),
                        resultSet.getString(INDEX_MTYPE), resultSet.getInt(INDEX_MTO));
                weibo.setCommentCount(resultSet.getInt(INDEX_MCOMMENT_COUNT));
                weibo.setReadnum(resultSet.getInt(INDEX_MREADNUM));
                weibo.setId(resultSet.getInt(INDEX_ID));
                weibo.setRepostCount(resultSet.getInt(INDEX_MRESPOST_COUNT));
                weibo.setUpvoteCount(resultSet.getInt(INDEX_MUPVOTE_COUNT));
                weibo.setCreateTime(resultSet.getString(INDEX_MCREATE_TIME));
                res.add(weibo);
            }
        }catch (Exception e){
            System.out.println("queryByUserIdOrderByDate异常");
            e.printStackTrace();

        }finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return res;
    }

    public void autoAddWeiboBash(String uid, int num, String baseContent){
        UserDao dao = new UserDao();

        User user = dao.queryById(uid);
        if(user != null){
            String sql = "INSERT INTO message(mcontent, user_id, mreadnum, mtype, mto, mcreate_time) VALUES(?,?,?,?,?,?)";
            PreparedStatement preStm = null;
            Connection conn = null;
            try {
                conn = JDBCUtil.getInstance().getConnection();
                preStm = conn.prepareStatement(sql);
                conn.setAutoCommit(false);
                for(int i = 1; i <= num; i++){
                    preStm.setString(1, baseContent + i);
                    preStm.setString(2, uid);
                    preStm.setInt(3, 0);
                    preStm.setString(4, "crt");
                    preStm.setInt(5, -1);
                    preStm.setString(6, TimeUtil.getInstance().getNowDateAndTime());
                    preStm.addBatch();
                    if(i % 100 == 0){
                        preStm.executeBatch();
                    }
                    preStm.executeBatch();
                    conn.commit();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(conn != null) JDBCUtil.getInstance().releaseConn();
            }
        }
    }
}
