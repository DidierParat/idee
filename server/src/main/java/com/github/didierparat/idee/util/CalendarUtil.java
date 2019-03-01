package com.github.didierparat.idee.util;

import java.util.Calendar;

public class CalendarUtil {

  private CalendarUtil() {}

  public static Calendar getNextSaturday() {
    final Calendar nextSaturday = Calendar.getInstance();
    int currentDay = nextSaturday.get(Calendar.DAY_OF_WEEK);
    int daysBeforeSaturday = Calendar.SATURDAY - currentDay;
    nextSaturday.add(Calendar.DAY_OF_WEEK, daysBeforeSaturday);
    return nextSaturday;
  }
}
