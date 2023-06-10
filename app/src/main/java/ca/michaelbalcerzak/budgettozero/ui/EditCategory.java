package ca.michaelbalcerzak.budgettozero.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.Database.CategoryResetAdmin;
import ca.michaelbalcerzak.budgettozero.Database.PurchaseAdmin;
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
        _deleteCategory.setVisibility(View.VISIBLE);
        _deleteCategory.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Delete Category");
            String text = "Are you sure you want to delete the category <b>" + _originalCategoryName + "</b>? <br> <br> Warning: This action can not be undone";
            // builder.setMessage(getString(R.string.delete_confirm) + " " + purchaseInfo.getDescription() + " @ " + purchaseInfo.getSpendAmount());
            builder.setMessage(Html.fromHtml(text));
            builder.setPositiveButton(R.string.reset_confirm_confirm,
                    (dialog, which) -> {
                        deleteCategory();
                        finish();
                    });
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
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

    private void deleteCategory() {
        CategoryResetAdmin categoryResetAdmin = new CategoryResetAdmin(this);
        PurchaseAdmin purchaseAdmin = new PurchaseAdmin(this);
        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        categoryResetAdmin.deleteResetForCategory(CATEGORY_PK);
        purchaseAdmin.deleteAllPurchasesForCategory(CATEGORY_PK);
        categoryAdmin.deleteCategory(CATEGORY_PK);
    }
}
