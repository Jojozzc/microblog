package com.weibo.servlet;

import com.google.gson.Gson;
import com.weibo.beans.LoginBean;
import com.weibo.dao.UserDao;
import com.weibo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().write("ServletPath:" + req.getServletPath());
        String path = req.getServletPath();
        if(path.equals("/Reg.do")){
            String userId = req.getParameter("register-name");
            String userName = req.getParameter("register-mingzi");
            String psw = req.getParameter("register-pas");
            UserDao userDao = new UserDao();
            User user = userDao.queryById(userId);
            if(user == null){
                System.out.println("账号: " + userId);
                System.out.println("名字: " + userName);
                System.out.println("密码: " + psw);
                user = new User(userId, userName, " ", " ", psw, 0, 0, 0);
                userDao.addUser(user);
//                resp.getWriter().write("Successful!");
                resp.sendRedirect("http://127.0.0.1:8848/sina/index.html");

            }
            else {
                resp.getWriter().write("Fail!");
            }
        }

        if(path.equals("/Login.do")){
            String userName = req.getParameter("userID");
            String psw = req.getParameter("userPassWord");
            UserDao userDao = new UserDao();
            User user = userDao.queryById(userName);
            System.out.println("用户名: " + userName);
            System.out.println("密码: " + psw);
            if(user == null || !psw.equals(user.getPassword())){
                resp.sendRedirect("http://127.0.0.1:8848/sina/index.html");
            }
            else {
                HttpSession session = req.getSession();//获取session
                session.setAttribute("user", userName);// 将用户名和密码保存在session中
                resp.sendRedirect("index.jsp");// 跳转到success.jsp页面

//                resp.sendRedirect("http://127.0.0.1:8848/sina/main.html");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
