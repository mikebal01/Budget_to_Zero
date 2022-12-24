package ca.michaelbalcerzak.budgettozero.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MainDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "budget.db";

    public MainDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");

        final String createResetFrequencyTable = "CREATE TABLE reset_frequency (reset_frequency_name TEXT UNIQUE)";
        db.execSQL(createResetFrequencyTable);
        db.execSQL("INSERT INTO reset_frequency VALUES ('NEVER');");
        db.execSQL("INSERT INTO reset_frequency VALUES ('MONTHLY');");
        db.execSQL("INSERT INTO reset_frequency VALUES ('BIMONTHLY');");
        db.execSQL("INSERT INTO reset_frequency VALUES ('BIWEEKLY');");
        db.execSQL("INSERT INTO reset_frequency VALUES ('WEEKLY');");
        db.execSQL("INSERT INTO reset_frequency VALUES ('DAILY');");
        db.execSQL("INSERT INTO reset_frequency VALUES ('CUSTOM');");

        final String createCategoryTable = "CREATE TABLE category (category_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, budget_amount REAL," +
                " budget_remaining REAL, reset_frequency_name text, FOREIGN KEY (reset_frequency_name) REFERENCES reset_frequency (reset_frequency_name));";
        db.execSQL(createCategoryTable);

        final String createPurchaseTable = "CREATE TABLE purchase (purchase_id INTEGER PRIMARY KEY AUTOINCREMENT, category_name TEXT, description TEXT, cost REAL," +
                " date text, FOREIGN KEY (category_name) REFERENCES category (name));";
        db.execSQL(createPurchaseTable);

        final String createSettingsTable = "CREATE TABLE settings (name TEXT UNIQUE, is_enabled INTEGER DEFAULT 0)";
        db.execSQL(createSettingsTable);
        db.execSQL("INSERT INTO settings VALUES ('CLEAR_HISTORY_ON_RESET', 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
//            db.execSQL("ALTER TABLE records ADD COLUMN notes TEXT");
        }

    }
}