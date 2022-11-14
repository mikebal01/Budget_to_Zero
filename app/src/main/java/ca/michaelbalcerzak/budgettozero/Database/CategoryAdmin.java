package ca.michaelbalcerzak.budgettozero.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;

public class CategoryAdmin extends MainDatabase{

    private final String CATEGORY_TABLE = "category";
    private final String CATEGORY_ID = "category_id";
    private final String NAME = "name";
    private final String BUDGET_AMOUNT = "budget_amount";
    private final String RESET_FREQUENCY_NAME = "reset_frequency_name";
    private final String REMAINING_BUDGET_AMOUNT = "budget_remaining";

    public CategoryAdmin(Context context) {
        super(context);
    }

    public void addCategory(CategoryInfoStruct category) {
        ContentValues values = createContentValuesForCategory(category);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(CATEGORY_TABLE, null, values);
        db.close();
    }

    private ContentValues createContentValuesForCategory(CategoryInfoStruct category){
        ContentValues values = new ContentValues();
        values.put(NAME, category.getName());
        values.put(BUDGET_AMOUNT, category.getBudgetAmount());
        values.put(REMAINING_BUDGET_AMOUNT, category.getRemainingBudgetAmount());
        values.put(RESET_FREQUENCY_NAME, category.getResetInterval());
        return values;
    }


    @SuppressLint("Range")
    public ArrayList<CategoryInfoStruct> getAllCategories() {
        final String query = "SELECT * FROM " + CATEGORY_TABLE + " ORDER BY " + NAME + " DESC";
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CategoryInfoStruct> CategoryViewList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            CategoryViewList.add(new CategoryInfoStruct(cursor.getString(cursor.getColumnIndex(CATEGORY_ID)),
                                                              cursor.getString(cursor.getColumnIndex(NAME)),
                                                              cursor.getString(cursor.getColumnIndex(BUDGET_AMOUNT)),
                                                              cursor.getString(cursor.getColumnIndex(RESET_FREQUENCY_NAME)),
                                                              cursor.getString(cursor.getColumnIndex(REMAINING_BUDGET_AMOUNT))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return CategoryViewList;
    }

    @SuppressLint("Range")
    public CategoryInfoStruct getCategoryByPk(String categoryPk) {
        final String query = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + CATEGORY_ID + " = " + categoryPk;
        SQLiteDatabase db = getReadableDatabase();
        CategoryInfoStruct category = null;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            category = (new CategoryInfoStruct(cursor.getString(cursor.getColumnIndex(CATEGORY_ID)),
                                               cursor.getString(cursor.getColumnIndex(NAME)),
                                               cursor.getString(cursor.getColumnIndex(BUDGET_AMOUNT)),
                                               cursor.getString(cursor.getColumnIndex(RESET_FREQUENCY_NAME)),
                                               cursor.getString(cursor.getColumnIndex(REMAINING_BUDGET_AMOUNT))));
        }
        cursor.close();
        db.close();
        return category;
    }
    @SuppressLint("Range")
    public CategoryInfoStruct getCategoryByName(String categoryName) {
        final String query = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + NAME + " = \'" + categoryName + "'";
        SQLiteDatabase db = getReadableDatabase();
        CategoryInfoStruct category = null;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            category = (new CategoryInfoStruct(cursor.getString(cursor.getColumnIndex(CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(BUDGET_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(RESET_FREQUENCY_NAME)),
                    cursor.getString(cursor.getColumnIndex(REMAINING_BUDGET_AMOUNT))));
        }
        cursor.close();
        db.close();
        return category;
    }

    public void debitCategoryForPurchase(String categoryName, String newRemainingBalance){
        ContentValues values = new ContentValues();
        values.put(NAME, categoryName);
        values.put(REMAINING_BUDGET_AMOUNT, newRemainingBalance);

        SQLiteDatabase db = getWritableDatabase();
        db.update(CATEGORY_TABLE, values, NAME + " = '" + categoryName + "\'", null);
        db.close();
    }

    public String getSumOfRemainingBudgetAmounts(){
        return getCategorySumAsString(REMAINING_BUDGET_AMOUNT);
    }

    public String getSumOfTotalBudgets(){
        return getCategorySumAsString(BUDGET_AMOUNT);
    }

    private String getCategorySumAsString(String columnToSum){
        final String query = "SELECT SUM(" + columnToSum + ") FROM " + CATEGORY_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String remainingBudget = cursor.getString(0);
        cursor.close();
        db.close();
        return remainingBudget;
    }
}
