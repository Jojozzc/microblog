package com.weibo.test;

import com.weibo.dao.FollowDao;
import com.weibo.model.User;
import org.junit.Test;

import java.util.List;

public class FollowDaoTest {
    @Test
    public void queryfollowByUserId(){
        FollowDao dao = new FollowDao();
        List<User> users = dao.queryfollowByUserId("zzcjojo");
        if(users != null && users.size() != 0){
            System.out.println("ok");
            for(int i = 0; i < users.size(); i++){
                System.out.println(users.get(i).toString());
            }
        }
    }
}
