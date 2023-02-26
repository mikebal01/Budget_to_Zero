package ca.michaelbalcerzak.budgettozero.ui;

import android.os.Bundle;
import android.view.View;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.R;

public class EditCategory extends AddCategory {

    private String CATEGORY_PK;
    private String _originalCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        CATEGORY_PK = extras.getString("categoryPk");
        setContentView(R.layout.addnewcategory);
        setupVariables();
        populateCurrentCategoryInfo();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    private void populateCurrentCategoryInfo(){
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        CategoryInfoStruct category = categoryAdmin.getCategoryByPk(CATEGORY_PK);
        _originalCategoryName = category.getName();
        _categoryName.setText(category.getName());
        _budgetAmount.setText(category.getBudgetAmount());
        if(getSpinnerSelection(category.getResetInterval()) != -1) {
            _resetFrequency.setSelection(getSpinnerSelection(category.getResetInterval()));
        }
        _addCategoryButton.setText(R.string.update_category);
    }

    public void createBudgetClicked(View view) {
        if (isValidUserInput(_originalCategoryName)) {
            CategoryAdmin categoryAdmin = new CategoryAdmin(this);
            categoryAdmin.updateCategory(createBudgetCategoryFromUserInput(), CATEGORY_PK);
            finish();
        }
    }

    CategoryInfoStruct createBudgetCategoryFromUserInput() {
        return new CategoryInfoStruct(null,
                _categoryName.getText().toString(),
                _budgetAmount.getText().toString(),
                _resetFrequency.getSelectedItem().toString().toUpperCase(),
                getNewRemainingBudget());
    }

    private String getNewRemainingBudget() {
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        CategoryInfoStruct category = categoryAdmin.getCategoryByPk(CATEGORY_PK);
        Double originalBudgetAmount = Double.valueOf(category.getBudgetAmount());
        Double newBudgetAmount = Double.valueOf(_budgetAmount.getText().toString());
        Double remainingBudget = Double.valueOf(category.getRemainingBudgetAmount());

        if (newBudgetAmount > originalBudgetAmount) {
            remainingBudget += (newBudgetAmount - originalBudgetAmount);
        } else if (newBudgetAmount < originalBudgetAmount) {
            remainingBudget -= (originalBudgetAmount - newBudgetAmount);
        }
        return String.valueOf(remainingBudget);
    }

    private int getSpinnerSelection(String selectedResetFrequency) {
        String[] stringArray = getResources().getStringArray(R.array.budget_frequency_array);
        int index = 0;
        while (!selectedResetFrequency.equals(stringArray[index].toUpperCase())) {
            index++;
            if (index == stringArray.length) {
                return -1;
            }
        }
           return index;
    }
}
