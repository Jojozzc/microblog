package com.weibo.dao;

import com.weibo.model.User;
import com.weibo.myUtil.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FollowDao {
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
    public List<User> queryfollowByUserId(String userId){
//        String sql = "select follow_id from follow where user_id = ?";
        String sql = "select uid, uname, uemail, uphone, "
                                       + "upassword, umessagecount, ufanscount, ufollowscount, unick_name, ureadedcount " +
                "from user inner join follow on(user.uid = follow.follow_id) where user_id = ?";
//        Set<String> userIdSet = new HashSet<String>();
        List<User> res = new ArrayList<User>();
        PreparedStatement preStm = null;
        Connection conn = null;
        ResultSet resultSet = null;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userId);
            resultSet = preStm.executeQuery();
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
            e.printStackTrace();
        }finally {
            if(conn != null) JDBCUtil.getInstance().releaseConn();
        }
        return  res;
    }

    public void addFollowByUid(String userId, String follow_id){
        if(userId == null ||follow_id == null) throw new RuntimeException("userid不能为空");

    }
}
