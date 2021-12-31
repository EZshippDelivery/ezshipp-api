package com.ezshipp.api.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ezshipp.api.model.LapseTime;

public class DateUtil {
	public static final String DB_FORMAT_DATETIME = "yyyy-M-d HH:mm:ss";
    public static final String TODAY_FORMAT = "yyyy-MM-dd";
    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEFORMAT_IST = "yyyy-MM-dd 'T' HH:mm:ss Z";
    
	  public static Date getTodayDate()  {
	        DateFormat formatter = new SimpleDateFormat(TODAY_FORMAT);
	        Date today = new Date();
	        Date todayWithZeroTime = null;
	        try {
	            todayWithZeroTime = formatter.parse(formatter.format(today));
	            //System.out.println(todayWithZeroTime.);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return todayWithZeroTime;
	    }
	  
	  public static Timestamp getFiveCutOffTime() {
	        Calendar today = Calendar.getInstance();
	        today.set(Calendar.HOUR_OF_DAY, 17);
	        today.set(Calendar.MINUTE, 01);
	        today.set(Calendar.SECOND, 01);
	        return new Timestamp(today.getTimeInMillis());
	    }
	  
	  public static Timestamp getFourCutOffTime() {
	        Calendar today = Calendar.getInstance();
	        today.set(Calendar.HOUR_OF_DAY, 16);
	        today.set(Calendar.MINUTE, 01);
	        today.set(Calendar.SECOND, 01);
	        return new Timestamp(today.getTimeInMillis());
	    }
	  
	  public static Timestamp getSixCutOffTime() {
	        Calendar today = Calendar.getInstance();
	        today.set(Calendar.HOUR_OF_DAY, 18);
	        today.set(Calendar.MINUTE, 01);
	        today.set(Calendar.SECOND, 01);
	        return new Timestamp(today.getTimeInMillis());
	    }
	  
	  public static Timestamp getOneAM(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.set(Calendar.HOUR_OF_DAY, 01);
	        calendar.set(Calendar.MINUTE, 01);
	        calendar.set(Calendar.SECOND, 01);
	        return new Timestamp(calendar.getTimeInMillis());
	    }

	    public static Timestamp getMidnight(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.set(Calendar.HOUR_OF_DAY, 23);
	        calendar.set(Calendar.MINUTE, 59);
	        calendar.set(Calendar.SECOND, 59);
	        return new Timestamp(calendar.getTimeInMillis());
	    }

	   

	    public static LapseTime getLapseTime(Date start, Date end) {
	        long diff = end.getTime() - start.getTime();
	        long diffSeconds = diff / 1000 % 60;
	        long diffMinutes = diff / (60 * 1000) % 60;
	        long diffHours = diff / (60 * 60 * 1000) % 24;
	        long diffDays = diff / (24 * 60 * 60 * 1000);
	        LapseTime lapseTime = new LapseTime();
	        lapseTime.setDiffDays(diffDays);
	        lapseTime.setDiffHours(diffHours);
	        lapseTime.setDiffMinutes(diffMinutes);
	        lapseTime.setDiffSeconds(diffSeconds);

	        return lapseTime;
	    }

	    public static Date getTodayStartDateTime(int daysToSubtract)  {
	        DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
	        Calendar today = Calendar.getInstance();
	        today.set(Calendar.DAY_OF_YEAR, (today.get(Calendar.DAY_OF_YEAR) - daysToSubtract));
	        today.set(Calendar.HOUR_OF_DAY, 01);
	        today.set(Calendar.MINUTE, 01);
	        today.set(Calendar.SECOND, 01);
	        Date todayWithZeroTime = null;
	        try {
	            todayWithZeroTime = formatter.parse(formatter.format(today.getTime()));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return todayWithZeroTime;
	    }

	    public static Date getTodayEndDateTime(int daysToSubtract)  {
	        DateFormat formatter = new SimpleDateFormat(DATEFORMAT);
	        Calendar today = Calendar.getInstance();
	        today.set(Calendar.DAY_OF_YEAR, (today.get(Calendar.DAY_OF_YEAR) - daysToSubtract));
	        today.set(Calendar.HOUR_OF_DAY, 23);
	        today.set(Calendar.MINUTE, 59);
	        today.set(Calendar.SECOND, 59);
	        Date todayWithZeroTime = null;
	        try {
	            todayWithZeroTime = formatter.parse(formatter.format(today.getTime()));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return todayWithZeroTime;
	    }
	    
	    public static Date  getDate(String dateStr, String format) {
	        final DateFormat formatter = new SimpleDateFormat(format);
	        try {
	            return formatter.parse(dateStr);
	        } catch (ParseException e) {
	            return null;
	        }
	    }
}
