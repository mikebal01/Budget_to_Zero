package ca.michaelbalcerzak.budgettozero;

public class PurchaseInfoStruct {

    private final String _purchasePK;
    private final String _description;
    private final String _spendAmount;
    private final String _spendDate;

    public PurchaseInfoStruct(String purchasePK, String description, String spentAmount, String date){
        _purchasePK = purchasePK;
        _description = description;
        _spendAmount = spentAmount;
        _spendDate = date;
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
}
