package ca.michaelbalcerzak.budgettozero.ui;

import android.os.Bundle;
import android.view.View;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.R;

public class EditCategory extends AddCategory {

    private String CATEGORY_PK;

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

        _categoryName.setText(category.getName());
        _budgetAmount.setText(category.getBudgetAmount());
        if(getSpinnerSelection(category.getResetInterval()) != -1) {
            _resetFrequency.setSelection(getSpinnerSelection(category.getResetInterval()));
        }
        _addCategoryButton.setText(R.string.update_category);
    }

    public void createBudgetClicked(View view) {
        if (isValidUserInput()) {
            CategoryAdmin categoryAdmin = new CategoryAdmin(this);
            categoryAdmin.updateCategory(createBudgetCategoryFromUserInput(), CATEGORY_PK);
            finish();
        }
    }

    private int getSpinnerSelection (String selectedResetFrequency){
        String[] stringArray = getResources().getStringArray(R.array.budget_frequency_array);
        int index = 0;
       while (!selectedResetFrequency.equals(stringArray[index].toUpperCase())) {
           index++;
           if(index == stringArray.length){
               return -1;
           }
       }
           return index;
    }
}
