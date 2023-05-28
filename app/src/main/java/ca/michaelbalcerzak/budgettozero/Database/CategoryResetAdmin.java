package ca.michaelbalcerzak.budgettozero.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ca.michaelbalcerzak.budgettozero.ResetFrequencyInfoStruct;

public class CategoryResetAdmin extends MainDatabase {

    private final String CATEGORY_RESET_TABLE = "categoryReset";
    private final String ID = "reset_id";
    private final String CATEGORY_ID = "category_id";
    private final String RESET_FREQUENCY = "reset_frequency_name";
    private final String RESET_DATE = "reset_date";
    private final String BI_MONTHLY_RESET_DATE = "bi_monthly_alt_reset_date";
    private final String IS_LAST_DAY_OF_MONTH = "is_last_day_of_month";

    public CategoryResetAdmin(Context context) {
        super(context);
    }

    public void addResetForCategory(ResetFrequencyInfoStruct resetFrequency) {
        ContentValues values = createContentValuesForReset(resetFrequency);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(CATEGORY_RESET_TABLE, null, values);
        db.close();
    }

    private ContentValues createContentValuesForReset(ResetFrequencyInfoStruct resetFrequency) {
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, resetFrequency.getCATEGORY_ID());
        values.put(RESET_FREQUENCY, resetFrequency.getRESET_Frequency());
        values.put(RESET_DATE, resetFrequency.getRESET_DATE());
        values.put(BI_MONTHLY_RESET_DATE, resetFrequency.getBI_MONTHLY_RESET_DATE());
        values.put(IS_LAST_DAY_OF_MONTH, resetFrequency.getIS_LAST_DAY_OF_MONTH());
        if (resetFrequency.getID() != -1) {
            values.put(ID, resetFrequency.getID());
        }
        return values;
    }

    @SuppressLint("Range")
    public ResetFrequencyInfoStruct getResetByCategoryPK(String categoryPk) {
        final String query = "SELECT * FROM " + CATEGORY_RESET_TABLE + " WHERE " + CATEGORY_ID + " = " + categoryPk;
        SQLiteDatabase db = getReadableDatabase();
        ResetFrequencyInfoStruct resetFrequencyInfoStruct = null;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            resetFrequencyInfoStruct = (new ResetFrequencyInfoStruct(cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(RESET_FREQUENCY)),
                    cursor.getString(cursor.getColumnIndex(RESET_DATE)),
                    cursor.getString(cursor.getColumnIndex(BI_MONTHLY_RESET_DATE)),
                    cursor.getInt(cursor.getColumnIndex(IS_LAST_DAY_OF_MONTH)) == 1));
        }

        cursor.close();
        db.close();
        return resetFrequencyInfoStruct;
    }

    public void updateCategoryReset(ResetFrequencyInfoStruct resetFrequency) {
        ContentValues values = createContentValuesForReset(resetFrequency);
        SQLiteDatabase db = getWritableDatabase();
        db.update(CATEGORY_RESET_TABLE, values, CATEGORY_ID + " = ?", new String[]{resetFrequency.getCATEGORY_ID()});
        db.close();
    }
}
