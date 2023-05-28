package ca.michaelbalcerzak.budgettozero;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

import ca.michaelbalcerzak.budgettozero.CommonHelpers.DateHelper;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.Database.CategoryResetAdmin;
import ca.michaelbalcerzak.budgettozero.Database.PurchaseAdmin;
import ca.michaelbalcerzak.budgettozero.Database.SettingsAdmin;

public class ResetProcessor {

    public static void resetAllExpiredBudgets(Context context) {
        ArrayList<CategoryInfoStruct> categories = new CategoryAdmin(context).getAllCategories();
        CategoryResetAdmin categoryResetAdmin = new CategoryResetAdmin(context);

        for (CategoryInfoStruct category : categories) {
            ResetFrequencyInfoStruct resetFrequency = categoryResetAdmin.getResetByCategoryPK(category.getCategoryPk());
            if (resetFrequency != null && (DateHelper.isTodayPastReset(resetFrequency.getRESET_DATE()) || DateHelper.isTodayPastReset(resetFrequency.getBI_MONTHLY_RESET_DATE()))) {
                resetBudgetAmountsAndClearHistory(context, category);
                setNewBudgetResetDate(context, resetFrequency);
            }
        }
    }

    private static void resetBudgetAmountsAndClearHistory(Context context, CategoryInfoStruct category) {
        boolean clearHistoryOnReset = new SettingsAdmin(context).clearHistoryOnReset();
        if (clearHistoryOnReset) {
            PurchaseAdmin purchaseAdmin = new PurchaseAdmin(context);
            purchaseAdmin.deleteAllPurchasesForCategory(category.getCategoryPk());
        }
        new CategoryAdmin(context).resetRemainingBudgetForCategory(category.getCategoryPk());
    }

    private static void setNewBudgetResetDate(Context context, ResetFrequencyInfoStruct resetFrequency) {
        String frequency = resetFrequency.getRESET_Frequency();
        Calendar calendar = DateHelper.getCalendarFromString(resetFrequency.getRESET_DATE());
        Calendar bi_monthly = DateHelper.getCalendarFromString(resetFrequency.getBI_MONTHLY_RESET_DATE());

        if (frequency.equals(ResetInterval.DAILY.name())) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        } else if (frequency.equals(ResetInterval.WEEKLY.name())) {
            calendar = DateHelper.getNextMatchingDayOfWeek(resetFrequency.getRESET_DATE(), 7);
        } else if (frequency.equals(ResetInterval.BI_WEEKLY.name())) {
            calendar = DateHelper.getNextMatchingDayOfWeek(resetFrequency.getRESET_DATE(), 14);
        } else if (frequency.equals(ResetInterval.BI_MONTHLY.name())) {
            if (DateHelper.isTodayPastReset(resetFrequency.getRESET_DATE())) {
                int dayOfMonth = DateHelper.getDayFromStringDate(resetFrequency.getRESET_DATE());
                calendar = DateHelper.getNextMonthWithSpecifiedDayOfMonth(dayOfMonth);
            }
            if (DateHelper.isTodayPastReset(resetFrequency.getBI_MONTHLY_RESET_DATE())) {
                int dayOfMonth = DateHelper.getDayFromStringDate(resetFrequency.getBI_MONTHLY_RESET_DATE());
                bi_monthly = DateHelper.getNextMonthWithSpecifiedDayOfMonth(dayOfMonth);
            }
        } else if (frequency.equals(ResetInterval.MONTHLY.name())) {
            int dayOfMonth = DateHelper.getDayFromStringDate(resetFrequency.getRESET_DATE());
            calendar = DateHelper.getNextMonthWithSpecifiedDayOfMonth(dayOfMonth);
        }

        resetFrequency.setResetDate(DateHelper.formatDateFromCalender(calendar));
        resetFrequency.setBiMonthlyResetDate(DateHelper.formatDateFromCalender(bi_monthly));

        new CategoryResetAdmin(context).updateCategoryReset(resetFrequency);
    }
}
