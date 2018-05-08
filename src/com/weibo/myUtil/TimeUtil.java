package com.weibo.myUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static TimeUtil instance = new TimeUtil();
    private TimeUtil(){}

    public static TimeUtil getInstance() {
        return instance;
    }

    public String getNowDateAndTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        return dateFormat.format(now);
    }

    public long timeToLong(String time){
        if(time == null) return Long.MIN_VALUE;
        StringBuilder stb = new StringBuilder();
        for(int i = 0; i < time.length(); i++){
            if(time.charAt(i) >= '0' && time.charAt(i) <= '9')
                stb.append(time.charAt(i));
        }
        if(stb.length() == 0) return 0;
        return Long.valueOf(stb.toString());
    }

}
