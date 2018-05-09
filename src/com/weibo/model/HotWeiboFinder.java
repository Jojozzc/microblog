package com.weibo.model;

import com.weibo.dao.HotWeiboFinderDao;
import com.weibo.myUtil.JDBCUtil;
import com.weibo.myUtil.JedisUtil;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.*;

public class HotWeiboFinder {
    public static final int DEFAULT_TOP = 100;
    public static final String CACHEMARKKEY = "WEIBOMARK";
    public static final String CACHEUPTODATE = "1";
    public static final String CACHEOUTOFDATE = "0";
    public List<Weibo> getHotWeibo(int top){
        if(top <= 0) throw new RuntimeException("Top值必须为正");
        return getHot(top);
    }

    public List<Weibo> getHotWeibo(){
        return getHot(DEFAULT_TOP);
    }

    private List<Weibo> getHot(int top){
        return getHotFromDB(top);
    }

    private synchronized List<Weibo> getHotFromCache(){
        return null;
//        Jedis jedis = JedisUtil.getInstance().getJedis();
//        if(!jedis.exists(CACHEMARKKEY)){
//            jedis.set(CACHEMARKKEY, CACHEOUTOFDATE);
//            HotWeiboFinderDao dao = new HotWeiboFinderDao();
//            List<User> hotUsers = getHotUserFromDB(DEFAULT_TOP);
//
//        }
//        else {
//            if(jedis.get(CACHEMARKKEY).equals(CACHEUPTODATE)){
//
//            }
//            else {
//
//            }
//        }
    }
    private synchronized List<User> getHotUserFromDB(int top){
        HotWeiboFinderDao dao = new HotWeiboFinderDao();
        return dao.queryHotUserByReadNum(top);
    }
    public List<Weibo> getRealHotFromDB(int top){
        return getHotFromDB(top);
    }
    private List<Weibo> getHotFromDB(int top){
        List<Weibo> res = new ArrayList<Weibo>();
        HotWeiboFinderDao dao = new HotWeiboFinderDao();
        List<User> users = dao.queryHotUserByReadNum(top);
        // PriorityQueue默认为最小堆
        PriorityQueue<HotUserNode> userHeap = new PriorityQueue<HotUserNode>(new Comparator<HotUserNode>() {
            @Override
            public int compare(HotUserNode o1, HotUserNode o2) {
                if(o1.getSortedWeibos().size() == 0) return 1;
                if(o2.getSortedWeibos().size() == 0) return -1;
                SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date1 = sDateFormat.parse(o1.getSortedWeibos().get(0).getCreateTime());
                    Date date2 = sDateFormat.parse(o2.getSortedWeibos().get(0).getCreateTime());
                    return date1.after(date2)?-1:1;
                }catch (Exception e){
                    System.out.println(e.toString());
                }
                return -o1.getSortedWeibos().get(0).getCreateTime().compareTo(o2.getSortedWeibos().get(0).getCreateTime());
            }
        });
        for(User user : users){
            List<Weibo> weibos = dao.queryTopWeiboByUserId(user.getId() ,top);
            HotUserNode hotUserNode = new HotUserNode(user, weibos);
            userHeap.offer(hotUserNode);
        }
        int count = 0;
        while (userHeap.size() > 0 && count < top){
            count++;
            HotUserNode node = userHeap.poll();
            res.add(node.getSortedWeibos().remove(0));
            if(node.getSortedWeibos().size() == 0) continue;
            else userHeap.offer(node);
        }
        return res;
    }

    public void refreshCache(Weibo weibo){

    }

    class HotUserNode{
        private User user;
        private List<Weibo> sortedWeibos;

        public HotUserNode(User user, List<Weibo> sortedWeibos){
            this.user = user;
            this.sortedWeibos = sortedWeibos;
        }

        public List<Weibo> getSortedWeibos() {
            return sortedWeibos;
        }

        public User getUser() {
            return user;
        }
    }


}
