package com.zzf.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author cc
 */
public class DateUtil {
    public static Date getDeleteDate(Date now,int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }
    public static Date getStartTime(Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,num);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndTime(Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,num);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
