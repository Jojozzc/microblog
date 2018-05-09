package com.weibo.servlet;

import com.google.gson.Gson;
import com.weibo.dao.FollowDao;
import com.weibo.dao.UserDao;
import com.weibo.dao.WeiboDao;
import com.weibo.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainServlet extends HttpServlet {
    private static WeiboCounter counter = new WeiboCounter();
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
                session.setAttribute("password", user.getPassword());
                session.setAttribute("user", user.getUserName());// 将用户名和密码保存在session中
                session.setAttribute("user_id", user.getId());
//                resp.sendRedirect("index.jsp");// 跳转到success.jsp页面

                resp.sendRedirect("sina/main.jsp");
            }
        }
        else if(path.equals("/View.do")){
            System.out.println("查看微博请求");
            System.out.println(req.getParameter("page"));
            HttpSession session = req.getSession();
            System.out.println("session内容:" + session.getAttribute("user"));
            System.out.println("session内容:" + session.getAttribute("user_id"));
            System.out.println("session内容:" + session.getAttribute("password"));
            int pageNum = Integer.valueOf(req.getParameter("page"));
            boolean isHot = Boolean.valueOf(req.getParameter("ishot"));
            String viewId = req.getParameter("name");
            System.out.println("name:"+ viewId);
            int start = (pageNum - 1) * 10;
            int end = start + 10;
            if(isHot){
                HotWeiboFinder finder = new HotWeiboFinder();
    //            List<Weibo> hotWeibos = finder.getHotWeibo(10);
                WeiboDao dao = new WeiboDao();

                List<Weibo> hotWeibos = dao.queryByUserIdOrderByDateRange("dengchao", start, end,true);
                UserDao userDao = new UserDao();
                User user;
                if(hotWeibos != null && hotWeibos.size() != 0){
                    for(Weibo weibo : hotWeibos){
                        user = userDao.queryById(weibo.getUserId());
                        weibo.setNickName(user.getUserName());
                    }
                }
                Gson gson = new Gson();
                String weiboJsons = new String(gson.toJson(hotWeibos));
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(weiboJsons);

                System.out.println(weiboJsons);
            }
            else {
                if(viewId == null){
                    // 看自己
                    System.out.println("看自己的微博");
                    WeiboDao dao = new WeiboDao();
                    List<Weibo> selfWeibos
                            = dao.queryByUserIdOrderByDateRange(session.getAttribute("user_id").toString(),
                            start, end, true);
                    String selfName = session.getAttribute("user").toString();
                    for (Weibo weibo : selfWeibos){
                        weibo.setNickName(selfName);
                        weibo.register(counter);
                        weibo.read();
                    }
                    Gson gson = new Gson();
                    String selfWeibosJson = gson.toJson(selfWeibos);
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(selfWeibosJson);
                }
                else {
                    // 看别人
                    if(viewId != null){
                        System.out.println("别人微博" + viewId);
                        WeiboDao dao = new WeiboDao();
                        List<Weibo> seeWeibos
                                = dao.queryByUserIdOrderByDateRange(viewId,
                                start, end, true);
                        UserDao userDao = new UserDao();
                        User viewUser = userDao.queryById(viewId);
                        String viewName = viewUser.getUserName();
                        for (Weibo weibo : seeWeibos){
                            weibo.setNickName(viewName);
                            weibo.register(counter);
                            weibo.read();
                        }
                        Gson gson = new Gson();
                        String seeWeibosJson = gson.toJson(seeWeibos);
                        System.out.println(seeWeibosJson);
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().write(seeWeibosJson);
                    }

                }
            }

        }
        else if(path.equals("/Pub.do")){
            System.out.println("发微博请求");
            HttpSession session = req.getSession();
            String uid = session.getAttribute("user_id").toString();
            String password = session.getAttribute("password").toString();
            System.out.println("session内容:" + session.getAttribute("user"));
            System.out.println("session内容:" + uid);
            System.out.println("session内容:" + password);
            String content = new String(req.getParameter("content").getBytes("ISO-8859-1"), "UTF-8");
            UserDao dao = new UserDao();
            User user = dao.queryById(session.getAttribute("user").toString());
            if(user == null || !password.equals(user.getPassword())){
                resp.getWriter().write("{\"status\":\"ok\"}");
                WeiboDao weiboDao = new WeiboDao();
                Weibo weibo = new Weibo(content, uid, "crt", -1);
                weibo.register(counter);
                weibo.send();
                System.out.println("发送成功");
                System.out.println(content);
            }
            else {
                resp.getWriter().write("{\"status\":\"fail\"}");
            }

        }
        else if(path.equals("/Fwl.do")){
            System.out.println("好友列表请求");
            HttpSession session = req.getSession();
            String uid = session.getAttribute("user_id").toString();
            String password = session.getAttribute("password").toString();
            System.out.println("session内容:" + session.getAttribute("user"));
            System.out.println("session内容:" + uid);
            System.out.println("session内容:" + password);
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
        else if("/AddFwl.do".equals(path)){
            System.out.println("加关注请求");


        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
