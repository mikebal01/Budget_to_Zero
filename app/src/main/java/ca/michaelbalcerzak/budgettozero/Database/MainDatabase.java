package ca.michaelbalcerzak.budgettozero.Database;

import android.content.Context;
import android.database.Cursor;
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
        final String createCategoryTable = "CREATE TABLE category (category_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, budget_amount REAL)";
        db.execSQL(createCategoryTable);

        /*        final String queryTableVehicle = "CREATE TABLE vehicle (vehicle_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "display_name TEXT, " +
                "cost_per_unit TEXT, " +
                "production_year INTEGER," +
                "model TEXT, " +
                "make TEXT, " +
                "odometer INTEGER," +
                "distance_unit_type TEXT," +
                "default_fuel_unit_type TEXT," +
                "FOREIGN KEY (distance_unit_type) REFERENCES distance_type (distance_type_code)," +
                "FOREIGN KEY (default_fuel_unit_type) REFERENCES fuel_type (fuel_type_code));";
        db.execSQL(queryTableVehicle);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
//            db.execSQL("ALTER TABLE records ADD COLUMN notes TEXT");
        }

    }

    double executeStatisticalQueryDouble(final String query){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        double count = cursor.getDouble(0);
        cursor.close();
        return count;
    }

    int executeStatisticalQueryInt(final String query){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

}