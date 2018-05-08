package com.weibo.myUtil;

import redis.clients.jedis.Jedis;

public class JedisUtil {
    private static JedisUtil instance = new JedisUtil();
    private Jedis jedis = null;
    private JedisUtil(){
        jedis = new Jedis("localhost");
    }

    public static JedisUtil getInstance() {
        return instance;
    }
    public Jedis getJedis(){
        return jedis;
    }
}
