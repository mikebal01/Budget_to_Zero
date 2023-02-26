package ca.michaelbalcerzak.budgettozero.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SettingsAdmin extends MainDatabase{

    public final String CLEAR_HISTORY_ON_RESET = "CLEAR_HISTORY_ON_RESET";
    public final String DISPLAY_CURRENCY = "DISPLAY_CURRENCY";
    private final String SETTINGS_TABLE = "settings";
    private final String IS_ENABLED = "is_enabled";
    private final String VALUE = "value";
    private final String SETTING_NAME = "name";

    public SettingsAdmin(Context context) {
        super(context);
    }

    public boolean clearHistoryOnReset(){
        return isSettingEnabled(CLEAR_HISTORY_ON_RESET);
    }
    @SuppressLint("Range")
    private boolean isSettingEnabled(String setting_name){
        final String QUERY = "SELECT "+ IS_ENABLED +" FROM " + SETTINGS_TABLE + " WHERE " + SETTING_NAME + " = '" + setting_name + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        cursor.moveToFirst();
        boolean is_enabled = (cursor.getInt(cursor.getColumnIndex(IS_ENABLED)) == 1);
        cursor.close();
        db.close();
        return is_enabled;
    }

    public void enableSettings(String[] setting_name) {
        updateSettings(setting_name, null, 1);
    }

    public void disableSettings(String[] setting_name) {
        updateSettings(setting_name, null, 0);
    }

    public void setValueForSetting(String[] settingName, String value) {
        updateSettings(settingName, value, 0);
    }

    private void updateSettings(String[] names, String value, int enabled) {
        ContentValues values = new ContentValues();
        values.put(IS_ENABLED, enabled);
        values.put(VALUE, value);
        SQLiteDatabase db = getWritableDatabase();
        db.update(SETTINGS_TABLE, values, SETTING_NAME + " = ?", names);
        db.close();
    }

    @SuppressLint("Range")
    public String getSettingValue(String setting_name) {
        String value = "";
        final String QUERY = "SELECT " + VALUE + " FROM " + SETTINGS_TABLE + " WHERE " + SETTING_NAME + " = '" + setting_name + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            value = (cursor.getString(cursor.getColumnIndex(VALUE)));
        }
        cursor.close();
        db.close();

        return value;
    }
}


