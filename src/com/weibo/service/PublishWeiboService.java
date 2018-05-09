package com.weibo.service;

import com.weibo.dao.UserDao;
import com.weibo.dao.WeiboDao;
import com.weibo.model.User;
import com.weibo.model.Weibo;
import com.weibo.model.WeiboCounter;
import com.weibo.myUtil.WeiboCounterUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PublishWeiboService extends BaseService {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
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
            weibo.register(WeiboCounterUtil.getInstance().getCounter());
            weibo.send();
            System.out.println("发送成功");
            System.out.println(content);
        }
        else {
            resp.getWriter().write("{\"status\":\"fail\"}");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        this.doGet(req, resp);
    }
}
