package ca.michaelbalcerzak.budgettozero.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.CommonHelpers.DateHelper;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.Database.PurchaseAdmin;
import ca.michaelbalcerzak.budgettozero.PurchaseInfoStruct;
import ca.michaelbalcerzak.budgettozero.R;

public class EditPurchase extends AddPurchase {

    private PurchaseInfoStruct _originalPurchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String purchasePK = extras.getString("purchasePK");
        PurchaseAdmin purchaseAdmin = new PurchaseAdmin(this);
        _originalPurchase = purchaseAdmin.getPurchaseByPK(purchasePK);
        setContentView(R.layout.add_purchase);
        setupVariables(_originalPurchase.getCategoryPK());
        populateCurrentPurchaseInfo(_originalPurchase);
    }

    private void populateCurrentPurchaseInfo(PurchaseInfoStruct purchase) {
        _description.setText(purchase.getDescription());
        _totalSpent.setText(purchase.getSpendAmount());
        setSpinnerToCurrentCategory(purchase.getCategoryPK());
        //    _spinner.setSelection();
        String date = purchase.getSpendDate();
        _purchaseDatePicker.init(DateHelper.getYearFromStringDate(date), DateHelper.getMonthFromStringDate(date), DateHelper.getDayFromStringDate(date), null);
    }

    public void addPurchaseClicked(View view) {
        PurchaseAdmin purchaseAdmin = new PurchaseAdmin(this);
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);

        if (_totalSpent.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.purchase_no_spend_amount,
                    Toast.LENGTH_LONG).show();
        } else {
            String description;
            if (_description.getText().toString().isEmpty()) {
                description = getResources().getString(R.string.Purchase);
            } else {
                description = _description.getText().toString();
            }

            CategoryInfoStruct category = categoryAdmin.getCategoryByName(_spinner.getSelectedItem().toString());

            PurchaseInfoStruct purchase = new PurchaseInfoStruct(null, description, _totalSpent.getText().toString(), DateHelper.formatDateFromPicker(_purchaseDatePicker), category.getCategoryPk());
    /*        //purchaseAdmin.addPurchase(purchase);

            double startingBalance = Double.parseDouble(_remainingBudget.getText().toString());
            double debit = Double.parseDouble(purchase.getSpendAmount());
            categoryAdmin.adjustCategoryForPurchase(purchase.getCategoryPK(), String.valueOf(startingBalance - debit));*/
            finish();
        }
    }

    private void setSpinnerToCurrentCategory(String categoryPK) {
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        String categoryName = categoryAdmin.getCategoryByPk(categoryPK).getName();
        int count = _spinner.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            if (_spinner.getItemAtPosition(i).equals(categoryName)) {
                _spinner.setSelection(i);
                break;
            }
        }
    }
}
