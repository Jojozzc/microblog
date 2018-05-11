package com.weibo.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.weibo.SessionConfig;
import com.weibo.dao.UserDao;
import com.weibo.dao.WeiboDao;
import com.weibo.model.User;
import com.weibo.model.Weibo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteWeiboService extends BaseService {
    private static Gson gson = new Gson();
    // 删除微博服务
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 删除微博
        System.out.println("删除微博服务开启");
        super.doPost(req, resp);
        ResponseBody responseBody = new ResponseBody();
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        String wid = req.getParameter("weiboId");
        long weiboId = Long.valueOf(wid);

        if(userId == null || resp == null){
            userId = req.getSession().getAttribute(SessionConfig.USERID).toString();
            password = req.getSession().getAttribute(SessionConfig.PASSWORD).toString();
        }
        System.out.println("【删除微博服务】userId" + userId);
        System.out.println("【删除微博服务】password" + password);
        System.out.println("【删除微博服务】weiboid" + weiboId);

        if(userId == null || resp == null){
            responseBody.setCode(4001);
            responseBody.setMessage("缺少账号或密码");
        }
        else {
            UserDao userDao = new UserDao();
            User user = userDao.queryById(userId);
            if(user == null || !user.getPassword().equals(password)){
                responseBody.setCode(4001);
                responseBody.setMessage("账号或密码错误");
            }
            else {
                if(wid == null){
                    responseBody.setCode(4002);
                }
                else {
                    WeiboDao weiboDao = new WeiboDao();
                    Weibo weibo = null;
                    try {
                        weibo = weiboDao.queryById(weiboId);
                        responseBody.setCode(6002);
                    }catch (Exception e){
                        responseBody.setCode(5001);
                        e.printStackTrace();
                    }
                    if(weibo != null){
                        responseBody.setCode(2001);
                        weiboDao.delete(weiboId);
                    }

                }
            }
        }

        resp.getWriter().write(gson.toJson(responseBody));

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        this.doPost(req, resp);
    }

    class ResponseBody{
        @SerializedName("code")
        private int code = 5001;
        @SerializedName("message")
        private String message = "";

        public void setMessage(String message) {
            this.message = message;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
