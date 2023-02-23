package com.mdl.common.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @description:日期工具
 * @author: meidanlong
 * @date: 2021/7/22 11:18 AM
 */
public class DateTimeUtil {

    private static String BAIDU_URL = "http://www.baidu.com";
    private static final DateTimeFormatter DATE_FMT = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter TIME_ONLY_FMT = DateTimeFormat.forPattern("HH:mm:ss");
    private static final DateTimeFormatter HOUR_MINUTE_FMT = DateTimeFormat.forPattern("HH:mm");
    private static final DateTimeFormatter DATE_MONTH_FMT = DateTimeFormat.forPattern("yyyy-MM");

    private DateTimeUtil() {
    }


    /**
     * 获取当前时间，精确到秒
     * 以百度时间为准
     * @return
     */
    public static Date curTime() throws IOException {
        URL url = new URL(BAIDU_URL);
        URLConnection uc = url.openConnection();
        uc.connect();// 发出连接
        long ld = uc.getDate();
        return new Date(ld);
    }

    public static Date stringToDate(String dateStr) {
        if (StringUtil.isBlank(dateStr)) {
            return null;
        }
        DateTime d = DATE_FMT.parseDateTime(dateStr);
        return d.toDate();
    }
    public static Date hourMinuteStringToDate(String dateStr) {
        if (StringUtil.isBlank(dateStr)) {
            return null;
        }
        DateTime d = HOUR_MINUTE_FMT.parseDateTime(dateStr);
        return d.toDate();
    }

    public static Date stringToDateTime(String datetimeStr) {
        if (StringUtil.isBlank(datetimeStr)) {
            return null;
        }
        DateTime time = TIME_FMT.parseDateTime(datetimeStr);
        return time.toDate();
    }

    public static Date stringToMonthTime(String datetimeStr) {
        if (StringUtil.isBlank(datetimeStr)) {
            return null;
        }
        DateTime time = DATE_MONTH_FMT.parseDateTime(datetimeStr);
        return time.toDate();
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return DATE_FMT.print(dateTime);
    }

    public static String dateTimeToString(Date date) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return TIME_FMT.print(dateTime);
    }

    public static String dateToTimeOnly(Date date) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return TIME_ONLY_FMT.print(dateTime);
    }

    public static String dateToTimeWithMinuteString(Date date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        return formatter.print(new DateTime(date));
    }

    public static String minuteToTimeWithMinuteString(Integer minute) {
        DateTime time = new DateTime();
        time = time.withTime(0, 0, 0, 0);
        time = time.plusSeconds(minute * 60);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        return formatter.print(time);
    }

    public static String dateToStringByFormatter(Date date, String formatterStr) {
        if (date == null) {
            return "";
        }
        if (StringUtil.isBlank(formatterStr)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern(formatterStr);
        return formatter.print(new DateTime(date));
    }

    public static Date stringToDateByFormatter(String dateStr, String formatterStr) {
        if (StringUtil.isBlank(dateStr)) {
            return null;
        }
        if (StringUtil.isBlank(formatterStr)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern(formatterStr);
        return formatter.parseDateTime(dateStr).toDate();
    }

    public static Date getBusinessDateOfDatetime(Date datetime) {
        DateTime nowDateTime = new DateTime(datetime.getTime());
        DateTime businessDate = nowDateTime.minusHours(6).withTime(0, 0, 0, 0);
        return businessDate.toDate();

    }

    public static Date getDateOfDatetime(Date datetime) {
        DateTime dateTime = new DateTime(datetime.getTime());
        DateTime date = dateTime.withTime(0, 0, 0, 0);
        return date.toDate();

    }

    public static Date getEndTimeOfDate(Date datetime){
        if(datetime == null){
            return null;
        }
        DateTime dateTime = new DateTime(datetime.getTime());
        DateTime date = dateTime.withTime(23,59,59,0);
        return date.toDate();
    }

    public static Date plusDay(Date date, int days) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.plusDays(days);
        return dateTime.toDate();
    }

    public static Date minusDays(Date date, int days) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.minusDays(days);
        return dateTime.toDate();
    }

    public static Date plusSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.plusSeconds(seconds);
        return dateTime.toDate();
    }

    public static Date minusSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.minusSeconds(seconds);
        return dateTime.toDate();
    }


    public static Date plusMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.plusMinutes(minutes);
        return dateTime.toDate();
    }

    public static Date minusMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.minusMinutes(minutes);
        return dateTime.toDate();
    }

    public static Date plusMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.plusMonths(months);
        return dateTime.toDate();
    }

    public static Date minusMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.minusMonths(months);
        return dateTime.toDate();
    }
    public static Date plusYears(Date date, int years) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.plusYears(years);
        return dateTime.toDate();
    }

    public static Date minusYears(Date date, int years) {
        DateTime dateTime = new DateTime(date.getTime());
        dateTime = dateTime.minusYears(years);
        return dateTime.toDate();
    }

    public static int beforeDays(Date beforeDate, Date afterDate) {
        long mils = afterDate.getTime() - beforeDate.getTime();
        if (mils <= 0) {
            return 0;
        }
        long milsOneDay = 1000 * 60 * 60 * 24L;
        if (mils % milsOneDay == 0) {
            return new Long(mils / milsOneDay).intValue();
        } else {
            return new Long(mils / milsOneDay + 1).intValue();
        }
    }

    /**
     * Long转换为Date
     *
     * @param lo
     * @return
     */
    public static Date getLongToDate(Long lo) {
        if (null == lo) {
            return null;
        }
        Date date = new Date(lo);
        return date;
    }

    /**
     * 获取月份偏移后的时间
     *
     * @param offset 偏移量
     * @return Date
     */
    public static Date getMonthOffsetDate(Integer offset) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, offset);
        return calendar.getTime();
    }

    /**
     * 获取日期偏移量后的时间
     *
     * @param offset 偏移量
     * @return Date
     */
    public static Date getDateOffsetDate(Integer offset) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    /**
     * 当前时间，秒级
     * @return
     */
    public static Integer currentTime() {
        return ((int) (System.currentTimeMillis() / 1000));
    }

    public static Date getZeroDate(Date date) {
        String strDate = DateTimeUtil.getStrDate(date, "yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(strDate + " 00:00:00");
        } catch (Exception e) {
            return null;
        }
    }

    public static String getStrDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 判断before时间在after时间之前
     *
     * @param before
     * @param after
     * @return
     */
    public static boolean dateBefore(Date before, Date after) {
        return (before != null && after != null && before.before(after));
    }
}
