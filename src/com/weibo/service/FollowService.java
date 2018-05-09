package com.weibo.service;

import com.weibo.SessionConfig;
import com.weibo.dao.FollowDao;
import com.weibo.model.Follow;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FollowService extends BaseService {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        String userId = req.getSession().getAttribute(SessionConfig.USERID).toString();
        String followId = req.getParameter("flwid");
        if(userId != null && followId != null){
            FollowDao followDao = new FollowDao();
            followDao.addFollowByUid(userId, followId);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        this.doGet(req,resp);
    }
}
