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

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        _addCategoryButton = findViewById(R.id.addNewCatagoryButton);

    }



    public void createBudgetClicked(View view) {
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        CategoryInfoStruct fakeCategory1 = new CategoryInfoStruct(null, "Cat 1", "250.00", ResetInterval.NEVER.name());
        categoryAdmin.addCategory(fakeCategory1);
        CategoryInfoStruct fakeCategory2 = new CategoryInfoStruct(null, "Dog 2", "666.66", ResetInterval.DAILY.name());
        categoryAdmin.addCategory(fakeCategory2);
        ArrayList<CategoryInfoStruct> allCategories = categoryAdmin.getAllCategories();
    }
}
