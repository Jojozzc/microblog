package com.weibo.test;

import com.weibo.dao.WeiboDao;
import com.weibo.model.Weibo;
import org.junit.Test;

import java.util.List;

public class WeiboDaoTest {
//    @Test
    public void autoAddWeiboBash(){
        WeiboDao dao = new WeiboDao();
        dao.autoAddWeiboBash("13342228768", 10, "今天打麻将输了");
    }

    @Test
    public void queryByUserIdOrderByDateRange(){
        WeiboDao dao = new WeiboDao();
        List<Weibo> list = dao.queryByUserIdOrderByDateRange("zzcjojo", 0, 10, true);
        if(list != null)
            for(int i = 0; i < list.size(); i++){
                System.out.println(i + 1 + list.get(i).toString());
        }
    }
}
