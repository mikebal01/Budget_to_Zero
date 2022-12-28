package ca.michaelbalcerzak.budgettozero.ui;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.R;
import ca.michaelbalcerzak.budgettozero.ResetInterval;

public class AddCategory extends Activity {
    Button _addCategoryButton;
    EditText _categoryName;
    EditText _budgetAmount;
    Spinner _resetFrequency;
    LinearLayout _dayOfWeekLayout;
    int _dayOfWeek = -1;
    ResetInterval _resetInterval;
    TextView _footerDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ca.michaelbalcerzak.budgettozero.R.layout.addnewcategory);
        setupVariables();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void setupVariables() {
        setupSpinner();
        _addCategoryButton = findViewById(R.id.addNewCatagoryButton);
        _categoryName = findViewById(R.id.editTextDescription);
        _budgetAmount = findViewById(R.id.editTextTotalSpent);
        _dayOfWeekLayout = findViewById(R.id.dayOfWeekLayout);
        _footerDate = findViewById(R.id.textView13);
        _footerDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //perform your action here
            }
        });
    }

    private void setupSpinner() {
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
                        _resetInterval = ResetInterval.BI_WEEKLY;
                        _dayOfWeekLayout.setVisibility(View.VISIBLE);
                        break;
                    case 4: //weekly
                        _resetInterval = ResetInterval.WEEKLY;
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
        ArrayList<Integer> allButtonsAsList = getAllButtonIdsAsList();
        _dayOfWeek = allButtonsAsList.indexOf(view.getId());
        allButtonsAsList.remove(_dayOfWeek);
        for (Integer button : allButtonsAsList) {
            findViewById(button).setBackgroundColor(Color.parseColor("#cfcfcf"));
        }
        findViewById(view.getId()).setBackgroundColor(R.style.Base_Widget_AppCompat_Button_Small);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, getDaysUntilReset(_dayOfWeek));

        TextView footer = findViewById(R.id.textView12);
        String displayFooter = getResources().getString(R.string.add_purchase_day_footer_head) + " <b>" + getDaysUntilReset(_dayOfWeek) + " </b>" +
                getResources().getString(R.string.add_purchase_day_footer_tail);
        String buttonText = " <u> " + SimpleDateFormat.getDateInstance().format(calendar.getTime());
        _footerDate.setText(Html.fromHtml(buttonText));
        footer.setText(Html.fromHtml(displayFooter));
    }

    private ArrayList<Integer> getAllButtonIdsAsList() {
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

    private int getDaysUntilReset(int selectedDayOfWeek) {
        int currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int selectedDateWithArrayIndexOffset = ++selectedDayOfWeek;
        int differenceInDays;
        if (selectedDateWithArrayIndexOffset > currentDayOfWeek) {
            differenceInDays = selectedDateWithArrayIndexOffset - currentDayOfWeek;
        } else if (selectedDateWithArrayIndexOffset < currentDayOfWeek) {
            differenceInDays = 7 - currentDayOfWeek + selectedDateWithArrayIndexOffset;
        } else {
            differenceInDays = 7;
        }
        if (_resetInterval.equals(ResetInterval.BI_WEEKLY)) {
            differenceInDays += 7;
        }
        return differenceInDays;
    }

    CategoryInfoStruct createBudgetCategoryFromUserInput() {
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
