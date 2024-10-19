package ca.michaelbalcerzak.budgettozero.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DecimalFormat;
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

    public void updateCategory(CategoryInfoStruct category, String categoryPK){
        ContentValues values = createContentValuesForCategory(category);
        SQLiteDatabase db = getWritableDatabase();
        db.update(CATEGORY_TABLE, values, CATEGORY_ID + " = ?", new String[] {categoryPK});
        db.close();
    }

    public void resetRemainingBudgetForCategory(String categoryPK){
        CategoryInfoStruct categoryByPk = getCategoryByPk(categoryPK);
        categoryByPk.resetRemainingBudget();
        updateCategory(categoryByPk, categoryPK);
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
        DecimalFormat decimalFormatTwoDecimals = new DecimalFormat("########.00");
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
                    decimalFormatTwoDecimals.format(cursor.getDouble(cursor.getColumnIndex(REMAINING_BUDGET_AMOUNT)))));
        }
        cursor.close();
        db.close();
        return category;
    }
    @SuppressLint("Range")
    public CategoryInfoStruct getCategoryByName(String categoryName) {
        categoryName = categoryName.replaceAll("'", "''");
        final String query = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + NAME + " = '" + categoryName + "'";
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

    public void adjustCategoryForPurchase(String categoryPK, String newRemainingBalance) {
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, categoryPK);
        values.put(REMAINING_BUDGET_AMOUNT, newRemainingBalance);

        SQLiteDatabase db = getWritableDatabase();
        db.update(CATEGORY_TABLE, values, CATEGORY_ID + " = '" + categoryPK + "'", null);
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
        double remainingBudget = cursor.getDouble(0);
        cursor.close();
        db.close();
        DecimalFormat decimalFormatTwoDecimals = new DecimalFormat("########.00");
        String remaining = decimalFormatTwoDecimals.format(remainingBudget);
        if (remaining.equals(".00")) {
            return "0";
        }
        return decimalFormatTwoDecimals.format(remainingBudget);
    }

    public void deleteCategory(String categoryPk) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CATEGORY_TABLE, CATEGORY_ID + " =?", new String[]{categoryPk});
        db.close();
    }
}
