package ca.michaelbalcerzak.budgettozero.CommonHelpers;

import android.annotation.SuppressLint;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    //FORMAT: dd-MM-yyyy
    public static String formatDateFromPicker(DatePicker datePicker) {
        String day = String.valueOf(datePicker.getDayOfMonth());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String year = String.valueOf(datePicker.getYear());
        return day + "-" + month + "-" + year;
    }

    public static String formatDateFromCalender(Calendar calendar) {
        if (calendar == null) {
            return "";
        } else {
            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            return day + "-" + month + "-" + year;
        }
    }

    public static int numberOfDaysFromToday(Calendar targetDate) {
        return (int) ((targetDate.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / (24 * 60 * 60 * 1000)) + 1;
    }

    public static Calendar getNextMonthWithSpecifiedDayOfMonth(int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static int getDayFromStringDate(String date) {
        String[] split = date.split("-");
        return Integer.parseInt(split[0]);
    }

    public static int getMonthFromStringDate(String date) {
        String[] split = date.split("-");
        int month = Integer.parseInt(split[1]) - 1;
        if (month < 0) {
            month = 11;
        }
        return month;
    }

    public static int getYearFromStringDate(String date) {
        String[] split = date.split("-");
        return Integer.parseInt(split[2]);
    }

    @SuppressLint("SimpleDateFormat")
    public static int getNumberOfDaysUntilReset(String resetDate) {
        try {
            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar = Calendar.getInstance();
            Date today = dates.parse(formatDateFromCalender(calendar));
            Date reset = dates.parse(resetDate);
            assert reset != null;
            assert today != null;
            long difference = Math.abs(reset.getTime() - today.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            return (int) differenceDates;
        } catch (Exception e) {
            return 0;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean isTodayPastReset(String resetDate) {
        try {
            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar = Calendar.getInstance();
            Date today = dates.parse(formatDateFromCalender(calendar));
            Date reset = dates.parse(resetDate);
            assert reset != null;
            assert today != null;
            return !today.before(reset);
        } catch (Exception e) {
            return false;
        }
    }

    public static Calendar getNextMatchingDayOfWeek(String date, final int weekDayCount) {
        Calendar calendar = getCalendarFromString(date);
        Calendar today = Calendar.getInstance();
        while (calendar.before(today)) {
            calendar.add(Calendar.DATE, weekDayCount);
        }
        return calendar;
    }

    public static Calendar getCalendarFromString(String date) {
        if (date.isEmpty()) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, getMonthFromStringDate(date));
        calendar.set(Calendar.DAY_OF_MONTH, getDayFromStringDate(date));
        calendar.set(Calendar.YEAR, getYearFromStringDate(date));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
