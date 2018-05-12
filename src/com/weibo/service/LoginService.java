package com.weibo.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.weibo.SessionConfig;
import com.weibo.dao.UserDao;
import com.weibo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginService extends BaseService {
    private static Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        System.out.println(userId);
        System.out.println(password);
        UserDao userDao = new UserDao();
        ResponseBody responseBody = new ResponseBody();
        if(userId == null || password == null){
            responseBody.setCode(4002);
        }
        else{
            User loginUser = userDao.queryById(userId);
            System.out.println(loginUser.toString());
            if(loginUser == null || !password.equals(loginUser.getPassword())){
                responseBody.setCode(4001);
            }
            else {
                HttpSession session = req.getSession();//获取session
                session.setAttribute(SessionConfig.PASSWORD, loginUser.getPassword());
                session.setAttribute(SessionConfig.USER_NAME, loginUser.getUserName());// 将用户名和密码保存在session中
                session.setAttribute(SessionConfig.USERID, loginUser.getId());
                System.out.println("session 内容:");
                System.out.println("password:" + loginUser.getPassword());
                System.out.println("user:" + loginUser.getUserName());
                System.out.println("user id:" + loginUser.getId());
                responseBody.setCode(2001);
                responseBody.setUser(loginUser);
            }
        }
        String json = gson.toJson(responseBody);
        System.out.println(json);
        resp.getWriter().write(json);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        this.doGet(req, resp);
    }

    private void doGetOld(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String userName = req.getParameter("userID");
        String psw = req.getParameter("userPassWord");
        UserDao userDao = new UserDao();
        User user = userDao.queryById(userName);
        System.out.println("用户名: " + userName);
        System.out.println("密码: " + psw);
        if(user == null || !psw.equals(user.getPassword())){
            resp.sendRedirect("sina/index.html");
        }
        else {
            HttpSession session = req.getSession();//获取session
            session.setAttribute("password", user.getPassword());
            session.setAttribute("user", user.getUserName());// 将用户名和密码保存在session中
            session.setAttribute("user_id", user.getId());
            System.out.println("session 内容:");
            System.out.println("password:" + user.getPassword());
            System.out.println("user:" + user.getUserName());
            System.out.println("user_id:" + user.getId());
//                resp.sendRedirect("index.jsp");// 跳转到success.jsp页面

//            resp.sendRedirect("sina/main.jsp");
        }
    }
    class ResponseBody{
        @SerializedName("code")
        private int code = 5001;
        @SerializedName("user")
        private User user;

        public void setCode(int code) {
            this.code = code;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
