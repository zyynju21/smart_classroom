/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yiyue.common.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 *
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public final static String DATE_MONTH_PATTERN = "yyyy-MM";

    public static String formatMonth(Date date) {
        return dateToString(date, DATE_MONTH_PATTERN);
    }
    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
	public static String format(Date date) {
        return dateToString(date, DATE_PATTERN);
    }

    public static String formatTime(Date date) {
        return dateToString(date, DATE_TIME_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String dateToString(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date= null;
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    public static Date date2DateLastSecond(Date date){
        String str=dateToString(date,"yyyy-MM-dd");
        str=str+" 23:59:59";
        Date d=stringToDate(str,"yyyy-MM-dd hh:mm:ss");
        System.out.println(d);
        return d;
    }

    /**
     * 验证当前日期是否超出活动时间
     * @param endDate
     * @return
     */
    public static boolean isActivityDateRange(Date endDate) {
        Date currentDate=new Date();
        Date compDate=date2DateLastSecond(endDate);
        System.out.println("currentDate:"+currentDate.getTime());
        System.out.println("compDate:"+compDate.getTime());
        if(currentDate.getTime()>compDate.getTime()){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String date=formatActivityDate(new Date());
        System.out.println(date);
    }

    public static String formatActivityDate(Date weddingDate) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(weddingDate);
        int index=calendar.get(Calendar.DAY_OF_WEEK)-1;
        String str=dateToString(weddingDate,"yyyy年 MM月 dd日 HH:mm")+" ("+weekDays[index]+")";
        return str;
    }
}
