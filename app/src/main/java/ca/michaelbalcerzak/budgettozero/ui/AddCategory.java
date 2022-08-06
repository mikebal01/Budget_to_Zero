package ca.michaelbalcerzak.budgettozero.ui;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.R;
import ca.michaelbalcerzak.budgettozero.ResetInterval;

public class AddCategory extends Activity {
    Button _addCategoryButton;
    EditText _categoryName;
    EditText _budgetAmount;
    Spinner _resetFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ca.michaelbalcerzak.budgettozero.R.layout.addnewcategory);
        setupVariables();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    private void setupVariables(){

        _resetFrequency = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _resetFrequency.setAdapter(adapter);

        _addCategoryButton = findViewById(R.id.addNewCatagoryButton);
        _categoryName = findViewById(R.id.editTextTextCategoryName);
        _budgetAmount = findViewById(R.id.editTextBudgetAmount);
    }

    private CategoryInfoStruct createBudgetCategoryFromUserInput(){
        return new CategoryInfoStruct(null,
                _categoryName.getText().toString(),
                _budgetAmount.getText().toString(),
                _resetFrequency.getSelectedItem().toString().toUpperCase());
    }

    public void createBudgetClicked(View view) {
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        categoryAdmin.addCategory(createBudgetCategoryFromUserInput());
        finish();
    }
}
