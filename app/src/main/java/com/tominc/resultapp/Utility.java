package com.tominc.resultapp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Pattern;

/**
 * Created by Ramola on 8/1/2016.
 */
public class Utility {

    private final static String REGEX="^(((IITU)1[4-5][1-2](0[1-9]|[1-2][0-9]))|(1((4MI5|(5MI[4-5])))(0[1-9]|[1-5][0-9]))|(1[2-5][1-5](((0[1-9]|[1-8][0-9]))|(90|91|92|93|94)))|(1[1-5]6(0[1-9]|[1-4][0-9]))|(1[3-5]7(0[1-9]|[1-5][0-9])))$";


    public static boolean checkRollno(String rollno){
        Pattern pattern=Pattern.compile(REGEX);
        if(pattern.matcher(rollno).matches()) return true;
        return false;
    }
    public static String getTimestamp(String pubDate){
        DateTimeFormatter formatter= DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss z");
        DateTime dateTime=formatter.parseDateTime(pubDate);
        DateTime start=new DateTime(DateTimeZone.forID("Asia/Kolkata"));
        DateTime end=new DateTime(dateTime.getMillis(),DateTimeZone.forID("Asia/Kolkata"));
        Duration duration=new Duration(start,end);
        return getTime(Math.abs(duration.getStandardSeconds()));
    }
    private static String getTime(long time){
        long minute=time/60l;
        long hour=minute/60l;
        long day=hour/24l;
        if(time<60l)
            return time+" seconds ago";
        else if (minute<60l)
            return minute+" minute ago";
        else if(hour<24l)
            return hour+" hour ago";
        else
            return day+"day ago";
    }

}
