package ca.michaelbalcerzak.budgettozero;

public class CategoryInfoStruct {

    private final String _categoryPk;
    private final String _name;
    private final String _budgetAmount;
    private final String _resetInterval;

    public CategoryInfoStruct(String categoryPk, String name, String budgetAmount, String resetInterval){
        _categoryPk = categoryPk;
        _name = name;
        _budgetAmount = budgetAmount;
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

    public String getResetInterval() {
        return _resetInterval;
    }
}
