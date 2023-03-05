package ca.michaelbalcerzak.budgettozero.CommonHelpers;

import android.widget.DatePicker;

import java.util.Calendar;

public class DateHelper {

    //FORMAT: dd-MM-yyyy
    public static String formatDateFromPicker(DatePicker datePicker) {
        String day = String.valueOf(datePicker.getDayOfMonth());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String year = String.valueOf(datePicker.getYear());
        return day + "-" + month + "-" + year;
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
        return Integer.valueOf(split[0]);
    }

    public static int getMonthFromStringDate(String date) {
        String[] split = date.split("-");
        int month = Integer.valueOf(split[1]) - 1;
        if (month < 0) {
            month = 11;
        }
        return month;
    }

    public static int getYearFromStringDate(String date) {
        String[] split = date.split("-");
        return Integer.valueOf(split[2]);
    }
}
