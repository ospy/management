package com.manage.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateUtils
{
  public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm";
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  public static final int MILLISECOND_FORMAT = 86400000;

  public static Date bornDate(String format, String dateStr)
    throws ParseException
  {
    return new SimpleDateFormat(format).parse(dateStr);
  }

  public static Date bornDateSimple(String dateStr)
    throws ParseException
  {
    return bornDate("yyyy-MM-dd HH:mm", dateStr);
  }

  public static String format(String format)
  {
    if (StringUtil.isBlank(format)) {
      format = "yyyy-MM-dd HH:mm:ss";
    }
    return new SimpleDateFormat(format).format(new Date());
  }

  public static String format(Date date, String format)
  {
    return new SimpleDateFormat(format).format(date);
  }

  public static String format(Calendar c, String format)
  {
    return new SimpleDateFormat(format).format(c.getTime());
  }

  public static String formatSimple()
  {
    return format(new Date(), "yyyy-MM-dd HH:mm");
  }

  public static String formatSimple(Date date)
  {
    return format(date, "yyyy-MM-dd HH:mm");
  }

  public static String formatSimple(String dateStr)
    throws ParseException
  {
    return formatSimple(bornDateSimple(dateStr));
  }

  public static String format(String format_old, String dateStr, String format_new)
    throws ParseException
  {
    return format(bornDate(format_old, dateStr), format_new);
  }

  public static Calendar bornCalendar(String format, String dateStr)
    throws ParseException
  {
    Calendar cd = Calendar.getInstance();
    cd.setTime(bornDate(format, dateStr));
    return cd;
  }

  public static String formatSimple_GMT(long milliseconds)
  {
    return formatSimple(new Date(milliseconds));
  }

  public static String formatSimple_GMT(String milliseconds)
  {
    return formatSimple_GMT(Long.parseLong(milliseconds));
  }

  public static String format_GMT(long milliseconds, String format)
  {
    return format(new Date(milliseconds), format);
  }

  public static String formatSimple_UNIX(long milliseconds)
  {
    return formatSimple(new Date(milliseconds * 1000L));
  }

  public static String formatSimple_UNIX(String milliseconds)
  {
    return formatSimple_UNIX(Long.parseLong(milliseconds));
  }

  public static String format_UNIX(long milliseconds, String format)
  {
    return format(new Date(milliseconds * 1000L), format);
  }

  public static long born_UNIX()
  {
    return new Date().getTime() / 1000L;
  }

  public static int getDayOfMonth(int year, int month)
  {
    Calendar a = Calendar.getInstance();
    int maxDate = 0;
    if ((year != 0) && (month != 0))
    {
      a.clear();
      a.set(1, year);
      a.set(2, month - 1);
      maxDate = a.getActualMaximum(5);
    }
    return maxDate;
  }

  public static int getMonthOfMaxDay()
  {
    Calendar a = Calendar.getInstance();
    a.set(5, 1);
    a.roll(5, -1);
    return a.get(5);
  }

  public static int getMonthOfMaxDay(int year, int month)
  {
    Calendar a = Calendar.getInstance();
    a.set(1, year);
    a.set(2, month - 1);
    return a.getActualMaximum(5);
  }

  public static boolean isBefore(String format, String dateStr)
    throws ParseException
  {
    return new Date().before(bornDate(format, dateStr));
  }

  public static boolean isSimpleBefore(String dateStr)
    throws ParseException
  {
    return new Date().before(bornDate("yyyy-MM-dd HH:mm", dateStr));
  }

  public static boolean isBefore(String format_1, String dateStr_1, String format_2, String dateStr_2)
    throws ParseException
  {
    return bornDate(format_1, dateStr_1).before(bornDate(format_2, dateStr_2));
  }

  public static boolean isAfter(String format, String dateStr)
    throws ParseException
  {
    return new Date().after(bornDate(format, dateStr));
  }

  public static boolean isSimpleAfter(String dateStr)
    throws ParseException
  {
    return new Date().after(bornDate("yyyy-MM-dd HH:mm", dateStr));
  }

  public static boolean isAfter(String format_1, String dateStr_1, String format_2, String dateStr_2)
    throws ParseException
  {
    return bornDate(format_1, dateStr_1).after(bornDate(format_2, dateStr_2));
  }

  public static boolean isDuring(String format, String begin, String end, String current)
    throws ParseException
  {
    return (isBefore(format, begin, format, current)) && (isAfter(format, end, format, current));
  }

  public static boolean isDuring(String format, String begin, String end)
    throws ParseException
  {
    Date date = new Date();
    return (bornDate(format, begin).before(date)) && (bornDate(format, end).before(date));
  }

  public static int differenceDate(String format, String begin, String end)
    throws ParseException
  {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

    long millisecond = simpleDateFormat.parse(end).getTime() - simpleDateFormat.parse(begin).getTime();

    return (int)(millisecond / 86400000L);
  }

  public static int differenceDateSimple(String begin, String end)
    throws ParseException
  {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    return (int)((simpleDateFormat.parse(end).getTime() - simpleDateFormat.parse(begin).getTime()) / 86400000L);
  }

  public static int differenceDateSimple(String date)
    throws ParseException
  {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    return (int)((simpleDateFormat.parse(date).getTime() - new Date().getTime()) / 86400000L);
  }

  public static int differenceMonth(Calendar start, Calendar end)
  {
    Calendar temp = (Calendar)end.clone();
    temp.add(5, 1);

    int year = end.get(1) - start.get(1);

    int month = 0;
    if (start.get(5) <= temp.get(5)) {
      month = temp.get(2) - start.get(2);
    } else if (getMonthOfMaxDay(temp.get(1), temp.get(2)) == temp.get(5)) {
      month = temp.get(2) - start.get(2);
    } else {
      month = temp.get(2) - start.get(2);
      month = month > 0 ? month - 1 : 0;
    }

    if ((start.get(5) == 1) && (temp.get(5) == 1))
      return year * 12 + month + 1;
    if ((start.get(5) != 1) && (temp.get(5) == 1))
      return year * 12 + month;
    if ((start.get(5) == 1) && (temp.get(5) != 1)) {
      return year * 12 + month;
    }
    return year * 12 + month - 1 < 0 ? 0 : year * 12 + month;
  }

  public static int differenceMonth(Date start, Date end)
  {
    Calendar startCal = Calendar.getInstance();
    startCal.setTime(start);
    Calendar endCal = Calendar.getInstance();
    startCal.setTime(end);

    return differenceMonth(startCal, endCal);
  }

  public static int differenceMonth(String format, String start, String end)
    throws ParseException
  {
    return differenceMonth(bornCalendar(format, start), bornCalendar(format, end));
  }

  public static int differenceMonthSimple(String start, String end)
    throws ParseException
  {
    return differenceMonth("yyyy-MM-dd", start, end);
  }

  public static String add(String format, String dateStr, int field, int amount)
    throws ParseException
  {
    Calendar c = bornCalendar(format, dateStr);
    c.add(field, amount);
    return format(c, format);
  }

  public static String add(String format, int field, int amount)
    throws ParseException
  {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(field, amount);
    return format(c, format);
  }
}