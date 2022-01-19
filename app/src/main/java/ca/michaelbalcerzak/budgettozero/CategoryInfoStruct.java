package ca.michaelbalcerzak.budgettozero;

public class CategoryInfoStruct {

    private String _categoryPk;
    private String _name;
    private String _budgetAmount;
    private String _resetInterval;

    public CategoryInfoStruct(String categoryPk, String name, String budgetAmount, String resetInterval){
        _categoryPk = categoryPk;
        _name = name;
        _budgetAmount = budgetAmount;
        _resetInterval = resetInterval;
    }

    public String get_categoryPk() {
        return _categoryPk;
    }

    public String get_name() {
        return _name;
    }

    public String get_budgetAmount() {
        return _budgetAmount;
    }

    public String get_resetInterval() {
        return _resetInterval;
    }
}
