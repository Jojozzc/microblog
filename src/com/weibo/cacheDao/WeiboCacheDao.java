package com.weibo.cacheDao;


import com.google.gson.Gson;
import com.weibo.model.Weibo;
import com.weibo.myUtil.JedisUtil;
import com.weibo.myUtil.TimeUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WeiboCacheDao {
    private static final String USER_JEDISSORTEDSET = "users";
    public synchronized String jedisZaddWeiboToUserId(String uid, List<Weibo> weibos){
        if(uid == null) {
            throw new  RuntimeException("uid不能为null");
        }
        Jedis jedis = JedisUtil.getInstance().getJedis();
        Gson gson = new Gson();
        String json;
        for(int i = 0; i < weibos.size(); i++){
            json = gson.toJson(weibos.get(i));
            jedis.zadd(uid, -TimeUtil.getInstance().timeToLong(weibos.get(i).getCreateTime()), json);
        }
        return uid;
    }

    public synchronized List<Weibo> queryWeibosFromCacheByUserId(String uid){
        Jedis jedis = JedisUtil.getInstance().getJedis();
        Set<String> jsonSet = jedis.zrange(uid, 0, 100);
        List<Weibo> weibos = new ArrayList<Weibo>();
        Gson gson = new Gson();
        for(String json : jsonSet){
            weibos.add((Weibo)gson.fromJson(json, Weibo.class));
        }
        return weibos;
    }

    public synchronized boolean existsUser(String uid){
        if(uid == null) throw new RuntimeException("uid不能为null");
        Jedis jedis = JedisUtil.getInstance().getJedis();
        return jedis.exists(uid);
    }
    public synchronized long getUserCacheNums(){
        Jedis jedis = JedisUtil.getInstance().getJedis();
        return jedis.zcard(USER_JEDISSORTEDSET);
    }
    public synchronized long getMinReadNum(){
        // 还未实现
        Jedis jedis = JedisUtil.getInstance().getJedis();
        return 0;
    }

    public synchronized void jedisZaddUserWithReadedCount(String uid, long readedNum){
        Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.zadd(USER_JEDISSORTEDSET, -readedNum, uid);

    }


}
