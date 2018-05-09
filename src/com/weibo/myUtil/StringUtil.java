package com.weibo.myUtil;

public class StringUtil {
    private static StringUtil instance = new StringUtil();
    private StringUtil(){}

    public static StringUtil getInstance() {
        return instance;
    }

    public String toUTF8(String str){
        String strUTF8 = null;
        try {
            strUTF8 = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return strUTF8;
    }
}
