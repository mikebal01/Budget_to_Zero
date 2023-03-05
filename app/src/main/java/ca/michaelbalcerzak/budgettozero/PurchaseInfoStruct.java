package ca.michaelbalcerzak.budgettozero;

public class PurchaseInfoStruct {

    private final String _purchasePK;
    private final String _description;
    private final String _spendAmount;
    private final String _spendDate;
    private final String _categoryPK;

    public PurchaseInfoStruct(String purchasePK, String description, String spentAmount, String date, String categoryPK) {
        _purchasePK = purchasePK;
        _description = description;
        _spendAmount = spentAmount;
        _spendDate = date;
        _categoryPK = categoryPK;
    }

    public String getPurchasePK() {
        return _purchasePK;
    }

    public String getDescription() {
        return _description;
    }

    public String getSpendAmount() {
        return _spendAmount;
    }

    public String getSpendDate() {
        return _spendDate;
    }

    public String getCategoryPK() {
        return _categoryPK;
    }
}
