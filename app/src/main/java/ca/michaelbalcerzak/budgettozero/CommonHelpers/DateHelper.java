package ca.michaelbalcerzak.budgettozero.CommonHelpers;

import android.widget.DatePicker;

public class DateHelper {

    //FORMAT: DD-MM-YYYY
    public static String formatDateFromPicker(DatePicker datePicker){
        String day = String.valueOf(datePicker.getDayOfMonth());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String year = String.valueOf(datePicker.getYear());
        return  day + "-" + month + "-" + year;
    }
}
