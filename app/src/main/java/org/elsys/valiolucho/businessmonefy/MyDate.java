package org.elsys.valiolucho.businessmonefy;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MyDate {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_LEGIBLE_FORMAT = "dd-MMM-yyyy";

    public String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT);
        DateTime dateTime = new DateTime();
        return formatter.print(dateTime);
    }

    public String getPreviousDateTime(String date, String period) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT);
        DateTime dateTime = formatter.parseDateTime(date);
        if("today".equals(period)) {
            dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        }else if ("day".equals(period)) {
            dateTime.minusDays(1);
        }else if ("week".equals(period)){
            dateTime.minusWeeks(1);
        }else if ("month".equals(period)) {
            dateTime.minusMonths(1);
        }else if ("year".equals(period)) {
            dateTime.minusYears(1);
        }else if("endDay".equals(period)) {
            dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        }
        return dateTime.toString();
    }

    public String getLegibleDate(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_LEGIBLE_FORMAT);
        DateTime dateTime = formatter.parseDateTime(date);
        return dateTime.toString();
    }
}
