package com.weibo.servlet;

import com.google.gson.Gson;
import com.weibo.beans.LoginBean;
import com.weibo.dao.UserDao;
import com.weibo.model.HotWeiboFinder;
import com.weibo.model.User;
import com.weibo.model.Weibo;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().write("ServletPath:" + req.getServletPath());
        String path = req.getServletPath();
        System.out.println(path);
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
                resp.sendRedirect("sina/index.html");

            }
            else {
                resp.getWriter().write("Fail!");
            }
        }

        else if(path.equals("/Login.do")){
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
                session.setAttribute("user", user.getUserName());// 将用户名和密码保存在session中
//                resp.sendRedirect("index.jsp");// 跳转到success.jsp页面

                resp.sendRedirect("sina/main.jsp");
            }
        }
        else if(path.equals("/View.do")){
            System.out.println("查看微博请求");
            System.out.println(req.getParameter("page"));
            int pageNum = Integer.valueOf(req.getParameter("page"));
            HotWeiboFinder finder = new HotWeiboFinder();
            List<Weibo> hotWeibos = finder.getHotWeibo(10);
            UserDao userDao = new UserDao();
            User user;
            if(hotWeibos != null && hotWeibos.size() != 0){
                for(Weibo weibo : hotWeibos){
                    user = userDao.queryById(weibo.getUserId());
                    weibo.setNickName(user.getUserName());
                }
            }
            Gson gson = new Gson();
//            String weiboJsons = new String(gson.toJson(hotWeibos).getBytes(), "UTF-8");
            String weiboJsons = new String(gson.toJson(hotWeibos));
//            resp.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
//            ServletOutputStream os = resp.getOutputStream();
//            os.write(weiboJsons.getBytes(Charset.forName("UTF-8")));
            resp.getWriter().write(weiboJsons);
//            os.flush();
//            os.close();
            System.out.println(weiboJsons);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
