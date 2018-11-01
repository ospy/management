package com.manage.utils;

import java.util.Random;


public class RandomUtils
{
  private static Random r = new Random();
  private static char[] stringAll = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private static char[] stringLowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  private static char[] stringLowercaseAndNumber = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
  private static char[] stringUppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private static char[] stringUppercaseAndNumber = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private static char[] stringNumber = "0123456789".toCharArray();

  public static int getInt(Integer min, Integer max)
  {
    return r.nextInt(max.intValue() - min.intValue()) + min.intValue();
  }

  public static char getUppercaseChar()
  {
    return (char)getInt(Integer.valueOf(65), Integer.valueOf(90));
  }

  public static char getLowercaseChar()
  {
    return (char)getInt(Integer.valueOf(97), Integer.valueOf(123));
  }

//  public static long getLong19(Integer len)
//  {
//	  return null;
//   // return Long.parseLong(Math.abs(r.nextLong()).substring(0, len.intValue()));
//  }

  public static boolean getBoolean()
  {
    return r.nextBoolean();
  }

  public static String getString(Integer len, char[] stringOrder)
  {
    if (len.intValue() < 1) return null;

    char[] randBuffer = new char[len.intValue()];
    for (int i = 0; i < randBuffer.length; i++) {
      randBuffer[i] = stringOrder[r.nextInt(stringOrder.length)];
    }
    return new String(randBuffer);
  }

  public static String getDefaultString(Integer len)
  {
    return getString(len, stringAll);
  }

  public static String getUppercaseAndNumberString(Integer len)
  {
    return getString(len, stringUppercaseAndNumber);
  }

  public static String getUppercaseString(Integer len)
  {
    return getString(len, stringUppercase);
  }

  public static String getLowercaseString(Integer len)
  {
    return getString(len, stringLowercase);
  }

  public static String getLowercaseAndNumberString(Integer len)
  {
    return getString(len, stringLowercaseAndNumber);
  }

  public static String getNumberString(Integer len)
  {
    return getString(len, stringNumber);
  }

  public static Integer[] getArray(int length, int min, int max)
  {
    Integer[] num = new Integer[length];
    for (int i = 0; i < length; i++) num[i] = Integer.valueOf(getInt(Integer.valueOf(min), Integer.valueOf(max)));
    return num;
  }
  public static Random getR() {
    return r;
  }
}
