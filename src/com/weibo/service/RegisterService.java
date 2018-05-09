package com.weibo.service;

import com.weibo.dao.UserDao;
import com.weibo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterService extends BaseService {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        super.doGet(req, resp);
//        new String(req.getParameter("content").getBytes("ISO-8859-1"), "UTF-8")
        String userId
                = new String(req.getParameter("register-name").getBytes("ISO-8859-1"), "UTF-8");
        String userName
                = new String(req.getParameter("register-mingzi").getBytes("ISO-8859-1"), "UTF-8");
        String psw = new String(req.getParameter("register-pas").getBytes("ISO-8859-1"), "UTF-8");
        UserDao userDao = new UserDao();
        User user = userDao.queryById(userId);
        if(user == null){
            System.out.println("账号: " + userId);
            System.out.println("名字: " + userName);
            System.out.println("密码: " + psw);
            user = new User(userId, userName, " ", " ", psw, 0, 0, 0);
            userDao.addUser(user);
//                resp.getWriter().write("Successful!");
            resp.sendRedirect("sina/index.html");

        }
        else {
            resp.getWriter().write("Fail!");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        super.doPost(req, resp);
        this.doGet(req, resp);
    }
}
