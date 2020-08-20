package com.vikas.mvvmarchexample.utils;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeConverter {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeZone timeZone = TimeZone.getTimeZone("IST");

    @TypeConverter
    public static Date fromTimestamp(String value){
        try {
            df.setTimeZone(timeZone);
            return df.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(Date value){
        df.setTimeZone(timeZone);
        return value == null ? null : df.format(value);
    }
}
