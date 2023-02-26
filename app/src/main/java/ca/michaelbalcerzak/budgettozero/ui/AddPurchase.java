package ca.michaelbalcerzak.budgettozero.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.CommonHelpers.DateHelper;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.Database.PurchaseAdmin;
import ca.michaelbalcerzak.budgettozero.PurchaseInfoStruct;
import ca.michaelbalcerzak.budgettozero.R;

public class AddPurchase extends Activity {

    private TextView _description;
    private TextView _totalSpent;
    private DatePicker _purchaseDatePicker;
    private TextView _remainingBudget;
    private Spinner _spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String startCategoryPk = extras.getString("categoryPk");
        setContentView(ca.michaelbalcerzak.budgettozero.R.layout.add_purchase);
        setupVariables(startCategoryPk);
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    private void setupVariables(String startCategoryPk){
        _remainingBudget = findViewById(R.id.textViewRemainingBudgetStart);
        _description = findViewById(R.id.editTextDescription);
        _totalSpent = findViewById(R.id.editTextTotalSpent);
        _purchaseDatePicker = findViewById(R.id.datePicker1);
        setupCategorySpinner(startCategoryPk);
    }

    private void setupCategorySpinner(String startCategoryPk){
        _spinner = findViewById(R.id.addPurchaseCategorySpinner);
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        int spinnerStartPosition = -1;
        ArrayList<CategoryInfoStruct> allCategories = categoryAdmin.getAllCategories();
        ArrayList<String> categoryNames = new ArrayList<>();
        for(CategoryInfoStruct categoryInfoStruct : allCategories){
            categoryNames.add(categoryInfoStruct.getName());
            if(categoryInfoStruct.getCategoryPk().equals(startCategoryPk)){
                spinnerStartPosition += categoryNames.size();
                _remainingBudget.setText(categoryAdmin.getCategoryByPk(startCategoryPk).getRemainingBudgetAmount());
            }
        }
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
        _spinner.setAdapter(adp);

        if (startCategoryPk != null){
            _spinner.setSelection(spinnerStartPosition);
        }
        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                _remainingBudget.setText(categoryAdmin.getCategoryByName(_spinner.getSelectedItem().toString()).getRemainingBudgetAmount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    public void addPurchaseClicked(View view) {
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
            PurchaseInfoStruct purchase = new PurchaseInfoStruct(null, description, _totalSpent.getText().toString(), DateHelper.formatDateFromPicker(_purchaseDatePicker), _spinner.getSelectedItem().toString());
            PurchaseAdmin purchaseAdmin = new PurchaseAdmin(this);
            purchaseAdmin.addPurchase(purchase);

            CategoryAdmin categoryAdmin = new CategoryAdmin(this);
            double startingBalance = Double.parseDouble(_remainingBudget.getText().toString());
            double debit = Double.parseDouble(purchase.getSpendAmount());
            categoryAdmin.debitCategoryForPurchase(purchase.getCategoryName(), String.valueOf(startingBalance - debit));
            finish();
        }
    }
}
