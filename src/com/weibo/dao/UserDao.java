package com.weibo.dao;

import com.weibo.model.User;
import com.weibo.model.Weibo;
import com.weibo.myUtil.JDBCUtil;
import sun.dc.pr.PRError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final int INDEX_UID = 1;
    private static final int INDEX_UNAME = 2;
    private static final int INDEX_UEMAIL = 3;
    private static final int INDEX_UPHONE = 4;
    private static final int INDEX_UPASSWORD = 5;
    private static final int INDEX_UMESSAGECOUNT = 6;
    private static final int INDEX_UFANSCOUNT = 7;
    private static final int INDEX_UFOLLOWSCOUNT = 8;
    private static final int INDEX_UNICK_NAME = 9;
    private static final int INDEX_UREADEDCOUNT = 10;

    public void addUser(User user){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql
                =
                "INSERT INTO user(uid, uname, uemail, uphone, upassword," +
                        "umessagecount, ufanscount, ufollowscount, unick_name, ureadedcount) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, user.getId());
            preStm.setString(2, user.getUserName());
            preStm.setString(3, user.getEmail());
            preStm.setString(4, user.getPhone());
            preStm.setString(5, user.getPassword());
            preStm.setInt(6, user.getMessageCount());
            preStm.setInt(7, user.getFansCount());
            preStm.setInt(8, user.getFollowsCount());
            preStm.setString(9, user.getNickName());
            preStm.setLong(10, user.getReadedCount());
            preStm.executeUpdate();
            preStm.close();
        }catch (Exception e){
            System.out.println("用户增添失败");
            e.printStackTrace();
        }
        finally {
            if(conn != null)
                JDBCUtil.getInstance().releaseConn();
        }
    }

    public User queryById(String userId){
        if(userId == null) return null;
        User user = null;

        String sql = "select * from user where uid = ?";
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        try {
            conn = JDBCUtil.getInstance().getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new User(resultSet.getString(INDEX_UID), resultSet.getString(INDEX_UNAME),
                        resultSet.getString(INDEX_UEMAIL), resultSet.getString(INDEX_UPHONE),
                        resultSet.getString(INDEX_UPASSWORD), resultSet.getInt(INDEX_UMESSAGECOUNT),
                        resultSet.getInt(INDEX_UFANSCOUNT), resultSet.getInt(INDEX_UFOLLOWSCOUNT));
                user.setNickName(resultSet.getString(INDEX_UNICK_NAME));
                user.setReadedCount(resultSet.getLong(INDEX_UREADEDCOUNT));
            }
            else {
                return null;
            }
        }catch (Exception e){
            System.out.println("用户查询失败");
            e.printStackTrace();
        }
        finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return user;
    }

    public List<User> queryAllUser(){
        List<User> users = new ArrayList<User>();
        String sql="select * from user";
        PreparedStatement preparedStatement = null;
        Connection conn = null;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getString(INDEX_UID), resultSet.getString(INDEX_UNAME),
                        resultSet.getString(INDEX_UEMAIL), resultSet.getString(INDEX_UPHONE),
                        resultSet.getString(INDEX_UPASSWORD), resultSet.getInt(INDEX_UMESSAGECOUNT),
                        resultSet.getInt(INDEX_UFANSCOUNT), resultSet.getInt(INDEX_UFOLLOWSCOUNT));
                user.setNickName(resultSet.getString(INDEX_UNICK_NAME));
                user.setReadedCount(resultSet.getLong(INDEX_UREADEDCOUNT));
                users.add(user);
            }
            return users;
        }catch (Exception e){
            System.out.println("用户查询失败");
            e.printStackTrace();
        }
        finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return users;
    }
    public void deleteUser(User user){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql
                =
                "DELETE FROM user WHERE uid = ?";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, user.getId());
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

    public void updateUserMessagecount(User user) throws Exception{
        if(user == null) throw new RuntimeException("用户更新异常");
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql
                =
                "UPDATE user SET " +
                        "VALUE(uid, uname, uemail, uphone, " +
                        "upassword, umessagecount, ufanscount, ufollowscount, unick_name, ureadedcount) VALUES(?,?,?,?,?,?,?,?,?,?)" +
                        " ? WHERE uid = ?";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, user.getId());
            preStm.setString(2, user.getUserName());
            preStm.setString(3, user.getEmail());
            preStm.setString(4, user.getPhone());
            preStm.setString(5, user.getPassword());
            preStm.setString(6, String.valueOf(user.getMessageCount()));
            preStm.setString(7, String.valueOf(user.getFansCount()));
            preStm.setString(8, String.valueOf(user.getFollowsCount()));
            preStm.setString(9, user.getNickName());
            preStm.setLong(10, user.getReadedCount());
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

    public void updateUserMsgcountIncreaseOne(String userid){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql = "UPDATE user SET umessagecount = umessagecount + 1 WHERE uid = ?";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userid);
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

    public void updateUserReadedCountIncreaseOne(String userid){
        Connection conn = JDBCUtil.getInstance().getConnection();
        String sql = "UPDATE user SET ureadedcount = ureadedcount + 1 WHERE uid = ?";
        PreparedStatement preStm = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userid);
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

    public List<User> queryUserOrderByReadedCount(int top, boolean desc){
        String sql = desc? "select * from user where umessagecount > 0 order by ureadedcount desc limit ?"
                : "select * from user order by ureadedcount limit ?";
        PreparedStatement prestm = null;
        List<User> res = new ArrayList<User>(top);
        Connection conn = JDBCUtil.getInstance().getConnection();
        try {
            prestm = conn.prepareStatement(sql);
            prestm.setInt(1, top);
            ResultSet resultSet = prestm.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getString(INDEX_UID), resultSet.getString(INDEX_UNAME),
                        resultSet.getString(INDEX_UEMAIL), resultSet.getString(INDEX_UPHONE),
                        resultSet.getString(INDEX_UPASSWORD), resultSet.getInt(INDEX_UMESSAGECOUNT),
                        resultSet.getInt(INDEX_UFANSCOUNT), resultSet.getInt(INDEX_UFOLLOWSCOUNT));
                user.setNickName(resultSet.getString(INDEX_UNICK_NAME));
                user.setReadedCount(resultSet.getLong(INDEX_UREADEDCOUNT));
                res.add(user);
            }
        }catch (Exception e){
            System.out.println("阅读数最高用户查询失败");
            e.printStackTrace();
        }
        return res;
    }

    public List<User> queryFollowListByUserId(String userId){
        List<User> followUsers = new ArrayList<User>();
        PreparedStatement preStm = null;
        Connection conn = null;
        String sql = "select * from user where uid = ?";
        List<String> followsId = queryFollowIdByUserId(userId);
        ResultSet resultSet = null;
        if(followsId == null || followsId.size() == 0) return followUsers;
        try {
            conn = JDBCUtil.getInstance().getConnection();
            preStm = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for(String uid : followsId){
                preStm.setString(1, uid);
                resultSet = preStm.executeQuery();
                while (resultSet.next()){
                    User user = new User(resultSet.getString(INDEX_UID), resultSet.getString(INDEX_UNAME),
                            resultSet.getString(INDEX_UEMAIL), resultSet.getString(INDEX_UPHONE),
                            resultSet.getString(INDEX_UPASSWORD), resultSet.getInt(INDEX_UMESSAGECOUNT),
                            resultSet.getInt(INDEX_UFANSCOUNT), resultSet.getInt(INDEX_UFOLLOWSCOUNT));
                    user.setNickName(resultSet.getString(INDEX_UNICK_NAME));
                    user.setReadedCount(resultSet.getLong(INDEX_UREADEDCOUNT));
                    followUsers.add(user);
                }

            }

            conn.commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return followUsers;
    }

    public List<String> queryFollowIdByUserId(String userId){
        List<String> follows = new ArrayList<String>();
        String sql = "select follow_id from follow where user_id = ?";
        PreparedStatement preStm = null;
        Connection conn = JDBCUtil.getInstance().getConnection();
        ResultSet resultSet = null;
        try {
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userId);
            resultSet = preStm.executeQuery();
            while (resultSet.next()){
                follows.add(resultSet.getString(1));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return follows;
    }

    public void increaseFollowOne(String userId){
        String sql = "UPDATE user SET ufollowscount = ufollowscount + 1 WHERE uid = ?";
        PreparedStatement preStm = null;
        Connection conn = null;
        try {
            conn = JDBCUtil.getInstance().getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userId);
            preStm.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
    }

    public void increaseFansOne(String followId){
        String sql = "UPDATE user SET ufanscount = ufanscount + 1 WHERE uid = ?";
        PreparedStatement preStm = null;
        Connection conn = null;
        try {
            conn = JDBCUtil.getInstance().getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, followId);
            preStm.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
    }
}
