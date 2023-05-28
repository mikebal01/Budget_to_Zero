package ca.michaelbalcerzak.budgettozero;

public class ResetFrequencyInfoStruct {
    private final int ID;
    private final String CATEGORY_ID;
    private final String RESET_Frequency;
    private String RESET_DATE;
    private String BI_MONTHLY_RESET_DATE;
    private final int IS_LAST_DAY_OF_MONTH;

    public ResetFrequencyInfoStruct(int id, String categoryID, String restFrequency, String resetDate, String biMonthlyResetDate, boolean isLastDayOfTheMonth) {
        ID = id;
        CATEGORY_ID = categoryID;
        RESET_Frequency = restFrequency;
        RESET_DATE = resetDate;
        BI_MONTHLY_RESET_DATE = biMonthlyResetDate;
        if (isLastDayOfTheMonth) {
            IS_LAST_DAY_OF_MONTH = 1;
        } else {
            IS_LAST_DAY_OF_MONTH = 0;
        }
    }

    public int getID() {
        return ID;
    }

    public String getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public String getRESET_Frequency() {
        return RESET_Frequency;
    }

    public String getRESET_DATE() {
        return RESET_DATE;
    }

    public String getBI_MONTHLY_RESET_DATE() {
        return BI_MONTHLY_RESET_DATE;
    }

    public int getIS_LAST_DAY_OF_MONTH() {
        return IS_LAST_DAY_OF_MONTH;
    }

    public void setResetDate(String newResetDate) {
        RESET_DATE = newResetDate;
    }

    public void setBiMonthlyResetDate(String newResetDate) {
        BI_MONTHLY_RESET_DATE = newResetDate;
    }
}
