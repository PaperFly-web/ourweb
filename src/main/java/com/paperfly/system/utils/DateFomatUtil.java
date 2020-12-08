package com.paperfly.system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFomatUtil {

    public static Date fomatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm ss");
        String format = simpleDateFormat.format(date);
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
    public static Date stringToDate(String time,String fomat){
        SimpleDateFormat sdf2 = new SimpleDateFormat(fomat);
        Date dateTime=null;
        try {
            dateTime= sdf2. parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }
    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }
    public static String getCurrentMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date date = new Date();
        return sdf.format(date);
    }
}
