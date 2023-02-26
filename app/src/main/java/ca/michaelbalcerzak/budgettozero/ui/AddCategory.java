package ca.michaelbalcerzak.budgettozero.ui;


import android.app.Activity;
import android.app.DatePickerDialog;
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

import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.CommonHelpers.DateHelper;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.R;
import ca.michaelbalcerzak.budgettozero.ResetInterval;

public class AddCategory extends Activity {
    Button _addCategoryButton;
    EditText _categoryName;
    EditText _budgetAmount;
    Spinner _resetFrequency;
    LinearLayout _dayOfWeekLayout;
    ResetInterval _resetInterval;
    TextView _footerDateWeek;
    TextView _footerDateMonth;
    TextView _footerDateBiMonthly1;
    TextView _footerDateBiMonthly2;
    Calendar _calendar;
    Calendar _calendar2;

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
        _footerDateWeek = findViewById(R.id.textView13);
        _footerDateMonth = findViewById(R.id.textView15);
        _footerDateBiMonthly1 = findViewById(R.id.textView17);
        _footerDateBiMonthly2 = findViewById(R.id.textView20);
        MaterialStyledDatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            _calendar.set(Calendar.YEAR, year);
            _calendar.set(Calendar.MONTH, month);
            _calendar.set(Calendar.DAY_OF_MONTH, day);
            if (_resetInterval.equals(ResetInterval.WEEKLY) || _resetInterval.equals(ResetInterval.BI_WEEKLY)) {
                updateResetDateFooter(_calendar, findViewById(R.id.textView12), findViewById(R.id.textView13));
            } else if (_resetInterval.equals(ResetInterval.MONTHLY)) {
                updateResetDateFooter(_calendar, findViewById(R.id.textView14), findViewById(R.id.textView15));
            } else if (_resetInterval.equals(ResetInterval.BI_MONTHLY)) {
                updateResetDateFooter(_calendar, findViewById(R.id.textView16), findViewById(R.id.textView17));
            }
        };
        MaterialStyledDatePickerDialog.OnDateSetListener date2 = (view, year, month, day) -> {
            _calendar2.set(Calendar.YEAR, year);
            _calendar2.set(Calendar.MONTH, month);
            _calendar2.set(Calendar.DAY_OF_MONTH, day);
            updateResetDateFooter(_calendar2, findViewById(R.id.textView19), findViewById(R.id.textView20));
        };

        _footerDateWeek.setOnClickListener(v -> showDatePicker(date, _calendar));
        _footerDateMonth.setOnClickListener(v -> showDatePicker(date, _calendar));
        _footerDateBiMonthly1.setOnClickListener(v -> showDatePicker(date, _calendar));
        _footerDateBiMonthly2.setOnClickListener(v -> showDatePicker(date2, _calendar2));
    }

    private void showDatePicker(MaterialStyledDatePickerDialog.OnDateSetListener date, Calendar calendar) {
        DatePickerDialog dialog = new DatePickerDialog(AddCategory.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        Calendar current = Calendar.getInstance();
        current.add(Calendar.DATE, 1);
        dialog.getDatePicker().setMinDate(current.getTimeInMillis());
        dialog.show();
    }

    private void updateResetDateFooter(Calendar calendar, TextView headView, TextView tailView) {
        String footer_tail_from_resource;
        int numberOfDays = DateHelper.numberOfDaysFromToday(calendar);
        if (numberOfDays == 1) {
            footer_tail_from_resource = getResources().getString(R.string.add_purchase_day_footer_tail_singular);
        } else {
            footer_tail_from_resource = getResources().getString(R.string.add_purchase_day_footer_tail_plural);
        }
        String displayFooter = getResources().getString(R.string.add_purchase_day_footer_head) + " <b>" + numberOfDays + " </b>" + footer_tail_from_resource;
        headView.setText(Html.fromHtml(displayFooter));
        headView.setVisibility(View.VISIBLE);

        String buttonText = " <u> " + SimpleDateFormat.getDateInstance().format(calendar.getTime()) + " </u>";
        tailView.setText(Html.fromHtml(buttonText));
        tailView.setVisibility(View.VISIBLE);
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
                hideResetDates();
                findViewById(R.id.dayOfMonthLayoutFooter).setVisibility(View.GONE);
                switch (i) {
                    case 1: //Monthly
                        _resetInterval = ResetInterval.MONTHLY;
                        findViewById(R.id.dayOfMonthLayoutFooter).setVisibility(View.VISIBLE);
                        _calendar = DateHelper.getNextMonthWithSpecifiedDayOfMonth(1);
                        updateResetDateFooter(_calendar, findViewById(R.id.textView14), findViewById(R.id.textView15));
                        break;
                    case 2: //Bi-Monthly
                        _resetInterval = ResetInterval.BI_MONTHLY;
                        findViewById(R.id.dayOfMonthLayoutFooterBiMonthly).setVisibility(View.VISIBLE);
                        _calendar = DateHelper.getNextMonthWithSpecifiedDayOfMonth(1);
                        updateResetDateFooter(_calendar, findViewById(R.id.textView16), findViewById(R.id.textView17));
                        _calendar2 = DateHelper.getNextMonthWithSpecifiedDayOfMonth(15);
                        updateResetDateFooter(_calendar2, findViewById(R.id.textView19), findViewById(R.id.textView20));
                        break;
                    case 3: //Bi-Weekly
                        _resetInterval = ResetInterval.BI_WEEKLY;
                        _dayOfWeekLayout.setVisibility(View.VISIBLE);
                        findViewById(R.id.textView11).setVisibility(View.VISIBLE);
                        break;
                    case 4: //weekly
                        _resetInterval = ResetInterval.WEEKLY;
                        _dayOfWeekLayout.setVisibility(View.VISIBLE);
                        findViewById(R.id.textView11).setVisibility(View.VISIBLE);
                        break;
                    case 5: //Daily
                        _resetInterval = ResetInterval.DAILY;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void hideResetDates() {
        _dayOfWeekLayout.setVisibility(View.GONE);
        findViewById(R.id.textView11).setVisibility(View.GONE);
        findViewById(R.id.textView12).setVisibility(View.GONE);
        _footerDateWeek.setVisibility(View.GONE);
        ArrayList<Integer> list = getAllButtonIdsAsList();
        setBackgroundOfButtonsToDefault(list);
        findViewById(R.id.dayOfMonthLayoutFooter).setVisibility(View.GONE);
        findViewById(R.id.dayOfMonthLayoutFooterBiMonthly).setVisibility(View.GONE);

    }

    public void dayOfWeekClicked(View view) {
        findViewById(R.id.dayOfWeekLayoutFooter).setVisibility(View.VISIBLE);
        ArrayList<Integer> allButtonsAsList = getAllButtonIdsAsList();
        int dayOfWeek = allButtonsAsList.indexOf(view.getId());
        allButtonsAsList.remove(dayOfWeek);
        setBackgroundOfButtonsToDefault(allButtonsAsList);
        findViewById(view.getId()).setBackgroundColor(R.style.Base_Widget_AppCompat_Button_Small);

        _calendar = Calendar.getInstance();
        _calendar.add(Calendar.DATE, getDaysUntilReset(dayOfWeek));

        updateResetDateFooter(_calendar, findViewById(R.id.textView12), findViewById(R.id.textView13));
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
        if (isValidUserInput("")) {
            CategoryAdmin categoryAdmin = new CategoryAdmin(this);
            categoryAdmin.addCategory(createBudgetCategoryFromUserInput());
            finish();
        }
    }

    private void setBackgroundOfButtonsToDefault(ArrayList<Integer> allButtonsAsList) {
        for (Integer button : allButtonsAsList) {
            findViewById(button).setBackgroundColor(Color.parseColor("#cfcfcf"));
        }
    }

    boolean isValidUserInput(String originalCategoryName) {
        if (_categoryName.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.add_category_title_error,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (_budgetAmount.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.add_category_amount_error,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        if (categoryAdmin.getCategoryByName(_categoryName.getText().toString()) != null && !originalCategoryName.equals(_categoryName.getText().toString())) {
            Toast.makeText(this, R.string.category_already_exists,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}