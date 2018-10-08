package cn.hg.common;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {


    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     */
    public static String getStandardDate(String timeStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newdate;
        try {
            newdate = formatter.parse(timeStr);
        } catch (ParseException e) {
            newdate = new Date();
            e.printStackTrace();
        }
        String Time;
        long t = newdate.getTime();
        long time = System.currentTimeMillis() - t;
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
        long year = (long) Math.ceil(time / 365 / 24 / 60 / 60 / 1000.0f);// 年
        if (year - 1 > 0) {
            Time = (year - 1) + "年前";
        } else if (day - 1 > 0) {
            if (day == 365) {
                Time = "1年前";
            } else {
                Time = day + "天前";
            }
        } else if (hour - 1 > 0) {
            if (hour == 24) {
                Time = "1天前";
            } else {
                Time = (hour - 1) + "小时前";
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                Time = "1分钟前";
            } else if (minute >= 1 && minute < 60) {
                Time = minute - 1 + "分钟前";
            } else {
                Time = "刚刚";
            }
        } else {
            Time = "刚刚";
        }
        return Time;
    }
}
