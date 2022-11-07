package ca.michaelbalcerzak.budgettozero.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.R;

public class AddPurchase extends Activity {

    private TextView _description;
    private TextView _totalSpent;
    private DatePicker _purchaseDate;

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
        setupCategorySpinner(startCategoryPk);
        TextView remainingBudgetStart = findViewById(R.id.textViewRemainingBudgetStart);
        remainingBudgetStart.setText("221");

        /*_resetFrequency = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _resetFrequency.setAdapter(adapter);

        _addCategoryButton = findViewById(R.id.addNewCatagoryButton);
        _categoryName = findViewById(R.id.editTextTextCategoryName);
        _budgetAmount = findViewById(R.id.editTextBudgetAmount);*/
    }

    private void setupCategorySpinner(String startCategoryPk){
        Spinner spinner = findViewById(R.id.addPurchaseCategorySpinner);
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        int spinnerStartPosition = -1;
        ArrayList<CategoryInfoStruct> allCategories = categoryAdmin.getAllCategories();
        ArrayList<String> categoryNames = new ArrayList<>();
        for(CategoryInfoStruct categoryInfoStruct : allCategories){
            categoryNames.add(categoryInfoStruct.getName());
            if(startCategoryPk != null && categoryInfoStruct.getCategoryPk().equals(startCategoryPk)){
                spinnerStartPosition += categoryNames.size();
            }
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,categoryNames);
        spinner.setAdapter(adp);

        if (startCategoryPk != null){
            spinner.setSelection(spinnerStartPosition);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void addPurchaseClicked(View view) {
        finish();
    }
}
