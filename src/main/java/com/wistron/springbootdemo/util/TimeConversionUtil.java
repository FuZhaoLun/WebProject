package com.wistron.springbootdemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConversionUtil {
    public static String dateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
