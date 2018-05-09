package com.weibo.service;

import com.google.gson.Gson;
import com.weibo.dao.FollowDao;
import com.weibo.dao.UserDao;
import com.weibo.dao.WeiboDao;
import com.weibo.model.Follow;
import com.weibo.model.HotWeiboFinder;
import com.weibo.model.User;
import com.weibo.model.Weibo;
import com.weibo.myUtil.WeiboCounterUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewService extends BaseService {
    public static List<Weibo> hots = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
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
            System.out.println("看热门微博");
            if(hots == null){
                HotWeiboFinder finder = new HotWeiboFinder();
                //            List<Weibo> hotWeibos = finder.getHotWeibo(10);
                WeiboDao dao = new WeiboDao();

                hots = finder.getHotWeibo(100);
            }
            List<Weibo> hotWeibos = new ArrayList<Weibo>();
            for(int i = start; i <= end && i < hots.size(); i++){
                hotWeibos.add(hots.get(i));
            }
            UserDao userDao = new UserDao();
            User user;
            FollowDao followDao = new FollowDao();
            if(hotWeibos != null && hotWeibos.size() != 0){
                for(Weibo weibo : hotWeibos){
                    user = userDao.queryById(weibo.getUserId());
                    weibo.setFrd(followDao.queryIsFollowing(user.getId(), weibo.getUserId()));
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
                    weibo.register(WeiboCounterUtil.getInstance().getCounter());
                    weibo.read();
                    System.out.println(weibo.toString());
                }
                Gson gson = new Gson();
                String selfWeibosJson = gson.toJson(selfWeibos);
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(selfWeibosJson);
                System.out.println(selfWeibosJson);
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
                        weibo.register(WeiboCounterUtil.getInstance().getCounter());
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
