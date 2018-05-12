package com.weibo.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.weibo.SessionConfig;
import com.weibo.dao.FollowDao;
import com.weibo.model.Follow;
import com.weibo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FollowListService extends BaseService {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        System.out.println("好友列表请求");
        HttpSession session = req.getSession();
        String uid = session.getAttribute(SessionConfig.USERID).toString();
        String password = session.getAttribute(SessionConfig.PASSWORD).toString();
        System.out.println("session内容:uname" + session.getAttribute(SessionConfig.USER_NAME));
        System.out.println("session内容:uid" + uid);
        System.out.println("session内容password:" + password);
        FollowDao dao = new FollowDao();
        List<User> users = dao.queryfollowByUserId(uid);
        if(users != null && users.size() != 0){
            List<Follow> follows = new ArrayList<Follow>(users.size());
            for(User user : users){
                follows.add(new Follow(user.getUserName()));
            }
            Gson gson = new Gson();
            String json = gson.toJson(follows);
            System.out.println(json);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        this.doGet(req, resp);
    }

    class ResponseBody{
        @SerializedName("code")
        private int code;
        @SerializedName("message")
        private String message = "";



    }
}
