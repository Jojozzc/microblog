package com.weibo.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.weibo.SessionConfig;
import com.weibo.dao.FollowDao;
import com.weibo.dao.UserDao;
import com.weibo.model.Follow;
import com.weibo.model.User;
import com.weibo.myUtil.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FollowService extends BaseService {
    protected static Gson gson = new Gson();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        ResponseBody responseBody = new ResponseBody();
        resp.setCharacterEncoding("UTF-8");
        String followId = req.getParameter("followid");
        String password = req.getParameter("password");
        String userId = req.getParameter("userid");
        if(userId == null || password == null){
            userId = req.getSession().getAttribute(SessionConfig.USERID).toString();
            password = req.getSession().getAttribute(SessionConfig.USERID).toString();
        }
        if(userId != null && followId != null){
            FollowDao followDao = new FollowDao();
            //followDao.addFollowByUid(userId, followId);
            UserDao userDao = new UserDao();
            User confirm = userDao.queryById(userId);
            if(confirm == null || !confirm.getPassword().equals(password)){
                responseBody.setCode(4001);
            }
            else{
                if(followDao.queryIsFollowing(userId, followId)){
                    responseBody.setCode(6001);
                }
                else {
                    responseBody.setCode(2001);
                }
            }
        }
        else {
            responseBody.setCode(4002);
        }

        resp.getWriter().write(responseBody.toJSON());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        this.doGet(req,resp);
    }

    class ResponseBody{

        @SerializedName("code")
        private int code = 5001;
        @SerializedName("message")
        private String message = "";

        public void setCode(int code) {
            this.code = code;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public String toJSON(){
            return gson.toJson(this);
        }
    }
}
