package ca.michaelbalcerzak.budgettozero;

import java.text.DecimalFormat;

public class CategoryInfoStruct {

    private final String _categoryPk;
    private final String _name;
    private final String _budgetAmount;
    private String _remainingBudget;
    private final String _resetInterval;

    public CategoryInfoStruct(String categoryPk, String name, String budgetAmount, String resetInterval, String remainingBudget){
        _categoryPk = categoryPk;
        _name = name;
        _budgetAmount = budgetAmount;
        _remainingBudget = remainingBudget;
        _resetInterval = resetInterval;
    }

    public String getCategoryPk() {
        return _categoryPk;
    }

    public String getName() {
        return _name;
    }

    public String getBudgetAmount() {
        DecimalFormat decimalFormatTwoDecimals = new DecimalFormat("########.00");
        String value = decimalFormatTwoDecimals.format(Double.valueOf(_budgetAmount));
        if (value.equals(".00")) {
            return "0";
        }
        return value;
    }

    public String getRemainingBudgetAmount() {
        DecimalFormat decimalFormatTwoDecimals = new DecimalFormat("########.00");
        String value = decimalFormatTwoDecimals.format(Double.valueOf(_remainingBudget));
        if (value.equals(".00")) {
            return "0";
        }
        return value;
    }

    public String getResetInterval() {
        return _resetInterval;
    }

    public void resetRemainingBudget(){
        _remainingBudget = _budgetAmount;
    }
}
