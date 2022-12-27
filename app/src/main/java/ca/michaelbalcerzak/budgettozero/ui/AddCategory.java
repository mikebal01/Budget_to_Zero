package ca.michaelbalcerzak.budgettozero.ui;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.R;

public class AddCategory extends Activity {
    Button _addCategoryButton;
    EditText _categoryName;
    EditText _budgetAmount;
    Spinner _resetFrequency;
    LinearLayout _dayOfWeekLayout;
    int _dayOfWeek = -1;

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

    void setupVariables(){
        setupSpinner();
        _addCategoryButton = findViewById(R.id.addNewCatagoryButton);
        _categoryName = findViewById(R.id.editTextDescription);
        _budgetAmount = findViewById(R.id.editTextTotalSpent);
        _dayOfWeekLayout = findViewById(R.id.dayOfWeekLayout);
    }

     private void setupSpinner(){
        _resetFrequency = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _resetFrequency.setAdapter(adapter);
        _resetFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                _dayOfWeekLayout.setVisibility(View.GONE);
                switch (i) {
                    case 0: //NEVER Don't Need to do anything
                       break;
                    case 1: //Monthly
                        Toast.makeText(getApplicationContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: //Bi-Monthly
                        Toast.makeText(getApplicationContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: //Bi-Weekly
                    case 4: //weekly
                        _dayOfWeekLayout.setVisibility(View.VISIBLE);
                        break;
                    case 5: //Daily
                        break;
                    case 6: // Custom
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void dayOfWeekClicked(View view) {
      int clickedButton = view.getId();
      ArrayList<Integer> allButtonsAsList = getAllButtonIdsAsList();
      _dayOfWeek = allButtonsAsList.indexOf(clickedButton);
      allButtonsAsList.remove(_dayOfWeek);
        for (Integer button : allButtonsAsList) {
            findViewById(button).setBackgroundColor(Color.parseColor("#cfcfcf"));
        }
      Button selected = findViewById(clickedButton);
      selected.setBackgroundColor(R.style.Base_Widget_AppCompat_Button_Small);

    }

    private ArrayList <Integer> getAllButtonIdsAsList(){
        ArrayList<Integer> buttons = new ArrayList<>();
        buttons.add(R.id.buttonSunday);
        buttons.add(R.id.buttonMonday);
        buttons.add(R.id.buttonTuseday);
        buttons.add(R.id.buttonWednesday);
        buttons.add(R.id.buttonThursday);
        buttons.add(R.id.buttonFriday);
        buttons.add(R.id.buttonSaturday);
        return buttons;
    }

    CategoryInfoStruct createBudgetCategoryFromUserInput(){
        return new CategoryInfoStruct(null,
                _categoryName.getText().toString(),
                _budgetAmount.getText().toString(),
                _resetFrequency.getSelectedItem().toString().toUpperCase(),
                _budgetAmount.getText().toString());
    }

    public void createBudgetClicked(View view) {
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        categoryAdmin.addCategory(createBudgetCategoryFromUserInput());
        finish();
    }
}
