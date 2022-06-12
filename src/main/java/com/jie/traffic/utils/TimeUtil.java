package com.jie.traffic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Classname TimeUtils
 * @Description TODO
 * @Date 2022/5/12 20:14
 * @Created by Jieqiyue
 */
public class TimeUtil {
    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     * @param timestampString 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (formats.isEmpty()) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }
    public static String TimeStamp2Date(String timestampString) {
        return TimeStamp2Date(timestampString,"");
    }

    public static String TimeStamp2Date(long timestampString) {
        return TimeStamp2Date(timestampString,"");
    }
    public static String TimeStamp2Date(long timestampString,String formats) {
        if (formats.isEmpty()) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = timestampString * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }


//    public static void main(String[] args) {
//        String s = TimeUtil.TimeStamp2Date("1652147067");
//        System.out.println(
//                s
//        );
//    }
}
