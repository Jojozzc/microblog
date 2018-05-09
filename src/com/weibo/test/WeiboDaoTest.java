package com.weibo.test;

import com.weibo.dao.UserDao;
import com.weibo.dao.WeiboDao;
import com.weibo.model.User;
import com.weibo.model.Weibo;
import com.weibo.myUtil.StringUtil;
import com.weibo.myUtil.WeiboCounterUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WeiboDaoTest {
//    @Test
    public void autoAddWeiboBash(){
        WeiboDao dao = new WeiboDao();
        dao.autoAddWeiboBash("13342228768", 10, "今天打麻将输了");
    }

//    @Test
    public void queryByUserIdOrderByDateRange(){
        WeiboDao dao = new WeiboDao();
        List<Weibo> list = dao.queryByUserIdOrderByDateRange("zzcjojo", 0, 10, true);
        if(list != null)
            for(int i = 0; i < list.size(); i++){
                System.out.println(i + 1 + list.get(i).toString());
        }
    }
//    @Test
    public void writeUser(){
        String starts = "韩雪、陈赫、陈坤、杜淳、冯绍峰、" +
                "韩庚、胡歌、何炅、黄渤、黄晓明、贾乃亮、李晨、李易峰、鹿晗、井柏然、刘烨、陆毅、孙红雷、王宝强、汪峰、" +
                "王俊凯、王凯、王源、魏晨、文章、吴亦凡、小沈阳、徐峥、杨洋、张翰、张杰、张艺兴、郑恺、Angelababy、" +
                "白百何、董洁、霍思燕、范冰冰、高圆圆、李冰冰、李湘、李小璐、李宇春、蒋勤勤、蒋欣、刘诗诗、刘涛、柳岩、刘亦菲、" +
                "那英、戚薇、宋佳、孙俪、汤唯、唐嫣、佟丽娅、王珞丹、谢娜、杨幂、杨紫、姚晨、章子怡、赵丽颖、赵薇、郑爽、周迅、" +
                "白敬亭、白举纲、白客、包贝尔、陈楚生、陈思诚、陈翔、陈晓、陈羽凡、陈学冬、迟帅、大张伟、董成鹏、窦唯、段奕宏、" +
                "傅程鹏、付辛博、高虎、高云翔、郭京飞、郭晓东、韩栋、何晟铭、胡兵、胡海泉、胡彦斌、胡夏、华晨宇、华少、黄海冰、" +
                "黄海波、黄景瑜、黄磊、黄轩、黄子韬、姜潮、蒋劲夫、靳东、金世佳、冷漠、李代沫、李东学、李光洁、李行亮、李健、" +
                "李佳航、李荣浩、李炜、李响、李玉刚、廖凡、林峰、林更新、林宥嘉、凌潇肃、刘昊然、罗晋、马可、马天宇、欧豪、" +
                "潘粤明、蒲巴甲、乔振宇、任重、沙溢、沈腾、释小龙、宋小宝、白冰、鲍蕾、曹颖、陈好、陈数、陈思斯、陈紫函、" +
                "邓家佳、迪丽热巴、丁丁、董璇、付梦妮、甘露、甘婷婷、高露、龚玥菲、古力娜扎、关晓彤、郭珍霓、海陆、海清、" +
                "韩雪、郝蕾、何洁、胡冰卿、胡蝶、胡静、胡可、黄圣依、黄奕、黄英、贾玲、贾青、江疏影、蒋梦婕、蒋依依、" +
                "江映蓉、江一燕、金莎、景甜、鞠婧祎、阚清子、柯蓝、李菲儿、李嘉格、李金铭、李沁、李晟、李小萌、李小冉、" +
                "林妙可、林允、刘萌萌、刘庭羽、刘雯、刘惜君、刘忻、颖儿、刘雨欣、刘芸、娄艺潇、路晨、马思纯、马苏、马雅舒、" +
                "马伊俐、毛晓彤、梅婷、苗圃、莫小棋、慕容晓晓、穆婷婷、倪妮、秦海璐、秦岚、尚雯婕、沈梦辰、舒畅、" +
                "斯琴高丽、宋茜、苏岩、孙菲菲、孙骁骁、孙耀琦、谭晶、谭松韵、谭维维、唐一菲、唐艺昕、陶昕然、童瑶、王丽坤、" +
                "王靓雅、王琳、王麟、王蓉、王晓晨、王子文、吴莫愁、吴倩、吴昕、弦子、谢楠、熊黛林、熊乃瑾、徐帆、徐娇、徐璐、" +
                "颜丹晨、闫妮、杨蓉、杨魏玲花、杨子姗、姚笛、姚星彤、叶璇、叶一茜、俞飞鸿、郁可唯、于莎莎、袁立、袁泉、袁姗姗、" +
                "曾轶可、曾泳醍、瞿颖、张碧晨、张静初、张靓颖、张檬、张萌、张含韵、张嘉倪、张天爱、张雪迎、张语格、张玉娇、张雨绮、" +
                "张歆艺、张馨予、张梓琳、张子萱、赵文琪、赵奕欢、周笔畅、周冬雨、周韦彤、朱珠、左小青";
        UserDao userDao = new UserDao();
        String[] startNames = starts.split("、");
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        WeiboDao weiboDao = new WeiboDao();
//        for(String s : startNames)System.out.println(s);
        for(int i = 0; i < startNames.length; i++){
//            User user =  new User();
            // User(String id, String userName, String email, String phone, String password,
            //                int messageCount, int fansCount, int followsCount)
            try {
                String uid = PinyinHelper.toHanyuPinyinString(startNames[i], format, new String()) + i;
                List<Weibo> weibos = weiboDao.queryByUserIdOrderByDateRange(uid, 0, 12, true);
                for(Weibo weibo : weibos){
                    for(int j = 0; j < i % 5; j++){
                        weibo.register(WeiboCounterUtil.getInstance().getCounter());
                        weibo.read();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }

    }
//    @Test
    public void writeWeibo(){

    }
}
