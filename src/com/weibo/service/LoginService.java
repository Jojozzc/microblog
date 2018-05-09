package com.weibo.service;

import com.weibo.dao.UserDao;
import com.weibo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginService extends BaseService {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
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

            resp.sendRedirect("sina/main.jsp");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        this.doGet(req, resp);
    }
}
