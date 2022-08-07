package ca.michaelbalcerzak.budgettozero.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import ca.michaelbalcerzak.budgettozero.R;

public class AddPurchase extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ca.michaelbalcerzak.budgettozero.R.layout.add_purchase);
        setupVariables();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    private void setupVariables(){

        /*_resetFrequency = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _resetFrequency.setAdapter(adapter);

        _addCategoryButton = findViewById(R.id.addNewCatagoryButton);
        _categoryName = findViewById(R.id.editTextTextCategoryName);
        _budgetAmount = findViewById(R.id.editTextBudgetAmount);*/
    }
}
