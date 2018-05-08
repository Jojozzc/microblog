package com.weibo.test;

import com.google.gson.Gson;
import com.weibo.dao.UserDao;
import com.weibo.model.HotWeiboFinder;
import com.weibo.model.User;
import com.weibo.model.Weibo;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;
public class UserDaoTest {
//    @Test
    public void queryFollows(){
        List<String> follows = null;
        UserDao dao = new UserDao();
        follows = dao.queryFollowIdByUserId("zzcjojo");
        for(String id : follows){
            System.out.println(id);
        }
    }

    @Test
    public void hotWeiboTest(){
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
        String weiboJsons = gson.toJson(hotWeibos);
        System.out.println(weiboJsons);
    }
}
