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
}
