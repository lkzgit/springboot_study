package com.demo.model.untils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期格式转换工具类
 */
public class DateUtil {

    /**
     * 传入日期
     * 返回字符串
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_date_format = new SimpleDateFormat("yyyy-MM-dd");

    public DateUtil() {
    }

    /**
     * 返回指定格式的日期字符串:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * 判断两个日期字符串是否相等
     * @param s
     * @param e
     * @return
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) != null && fomatDate(e) != null) {
            return s.compareTo(e) > 0;
        } else {
            return false;
        }
    }

    /**
     * 指定字符串转日期:yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date fomatDate(String date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return fmt.parse(date);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param i
     * @return
     */
    public static String getAddDay(int i) {
        String currentTime = getTime();
        GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(currentTime.substring(0, 4)), Integer.parseInt(currentTime.substring(5, 7)) - 1, Integer.parseInt(currentTime.substring(8, 10)));
        gCal.add(5, i);
        return sdf_date_format.format(gCal.getTime());
    }

    /**
     * 日期转字符串,将时间戳转为指定字符串,转的是天数
     * @param i
     * @return
     */
    public static String getAddDayTime(int i) {
        Date date = new Date(System.currentTimeMillis() + (long)(i * 24 * 60 * 60 * 1000));
        return sdfTime.format(date);
    }

    /**
     * 日期转字符串,将时间戳转为指定字符串,转的是毫秒数
     * @param i
     * @return
     */
    public static String getAddDaySecond(int i) {
        Date date = new Date(System.currentTimeMillis() + (long)(i * 1000));
        return sdfTime.format(date);
    }

    public static void main(String[] args) {
        System.out.println(getTime());
        System.out.println(getAddDay(100));
        System.out.println(getAddDayTime(100));
        System.out.println(getAddDaySecond(100));
        System.out.println(System.currentTimeMillis());
    }

}
