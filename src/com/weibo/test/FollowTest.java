package com.weibo.test;

import com.weibo.dao.FollowDao;
import org.junit.Test;

public class FollowTest {
    @Test
    public void follow(){
        FollowDao followDao = new FollowDao();
        followDao.addFollowByUid("zzcjojo","jinsha172");
    }
}
