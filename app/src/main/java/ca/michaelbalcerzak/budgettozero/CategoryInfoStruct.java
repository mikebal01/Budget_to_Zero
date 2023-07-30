package ca.michaelbalcerzak.budgettozero;

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
        return _budgetAmount;
    }

    public String getRemainingBudgetAmount() { return String.valueOf(Math.round(100 * Double.valueOf(_remainingBudget))/100);

       // return _remainingBudget;
    }

    public String getResetInterval() {
        return _resetInterval;
    }

    public void resetRemainingBudget(){
        _remainingBudget = _budgetAmount;
    }
}
