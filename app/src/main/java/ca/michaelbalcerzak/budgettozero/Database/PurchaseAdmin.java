package ca.michaelbalcerzak.budgettozero.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.PurchaseInfoStruct;

public class PurchaseAdmin extends MainDatabase{

    private final String PURCHASE_TABLE = "purchase";
    private final String CATEGORY_NAME = "category_name";
    private final String DESCRIPTION = "description";
    private final String PURCHASE_COST = "cost";
    private final String DATE = "date";
    private final String ID = "purchase_id";

    public PurchaseAdmin(Context context) {
        super(context);
    }

    public void addPurchase(PurchaseInfoStruct purchase) {
        ContentValues values = createContentValuesForPurchase(purchase);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(PURCHASE_TABLE, null, values);
        db.close();
    }

    private ContentValues createContentValuesForPurchase(PurchaseInfoStruct purchase){
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, purchase.getCategoryName());
        values.put(DESCRIPTION, purchase.getDescription());
        values.put(PURCHASE_COST, purchase.getSpendAmount());
        values.put(DATE, purchase.getSpendDate());
        return values;
    }

    public ArrayList<PurchaseInfoStruct> get25MostRecentPurchases(){
        final String QUERY = "SELECT * FROM " + PURCHASE_TABLE + " ORDER BY " + ID + " DESC LIMIT 25";
        return getPurchasesList(QUERY);
    }

    public ArrayList<PurchaseInfoStruct> get25MostRecentPurchasesForCategory(String category){
        final String QUERY = "SELECT * FROM " + PURCHASE_TABLE + " WHERE " + CATEGORY_NAME + " = '" + category + "' ORDER BY " + ID + " DESC LIMIT 25";
        return getPurchasesList(QUERY);
    }

    @SuppressLint("Range")
    private ArrayList<PurchaseInfoStruct> getPurchasesList(final String QUERY) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PurchaseInfoStruct> purchaseList = new ArrayList<>();
        Cursor cursor = db.rawQuery(QUERY, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            purchaseList.add(new PurchaseInfoStruct(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(PURCHASE_COST)),
                    cursor.getString(cursor.getColumnIndex(DATE)),
                    cursor.getString(cursor.getColumnIndex(CATEGORY_NAME))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return purchaseList;
    }
}
