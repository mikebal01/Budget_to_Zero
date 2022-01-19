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
    private Context _context;

    public CategoryAdmin(Context context) {
        super(context);
        _context = context;
    }

    public void addCategory(CategoryInfoStruct category) {
        ContentValues values = createContentValuesForCategory(category);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(CATEGORY_TABLE, null, values);
        db.close();
    }

    private ContentValues createContentValuesForCategory(CategoryInfoStruct category){
        ContentValues values = new ContentValues();
        values.put(NAME, category.get_name());
        values.put(BUDGET_AMOUNT, category.get_budgetAmount());
        values.put(RESET_FREQUENCY_NAME, category.get_resetInterval());
        return values;
    }


    @SuppressLint("Range")
    public ArrayList<CategoryInfoStruct> getAllCategories() {
        final String query = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CategoryInfoStruct> recordViewList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
                    recordViewList.add(new CategoryInfoStruct(cursor.getString(cursor.getColumnIndex(CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(BUDGET_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(RESET_FREQUENCY_NAME))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return recordViewList;
    }

}
