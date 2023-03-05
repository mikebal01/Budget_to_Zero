package ca.michaelbalcerzak.budgettozero.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.PurchaseInfoStruct;

public class PurchaseAdmin extends MainDatabase{

    private final String PURCHASE_TABLE = "purchase";
    private final String CATEGORY_ID = "category_id";
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
        values.put(CATEGORY_ID, purchase.getCategoryPK());
        values.put(DESCRIPTION, purchase.getDescription());
        values.put(PURCHASE_COST, purchase.getSpendAmount());
        values.put(DATE, purchase.getSpendDate());
        return values;
    }

    public ArrayList<PurchaseInfoStruct> get25MostRecentPurchases() {
        final String QUERY = "SELECT * FROM " + PURCHASE_TABLE + " ORDER BY " + ID + " DESC LIMIT 25";
        return getPurchasesList(QUERY);
    }

    public ArrayList<PurchaseInfoStruct> get25MostRecentPurchasesForCategory(String categoryPK) {
        final String QUERY = "SELECT * FROM " + PURCHASE_TABLE + " WHERE " + CATEGORY_ID + " = '" + categoryPK + "' ORDER BY " + ID + " DESC LIMIT 25";
        return getPurchasesList(QUERY);
    }

    public PurchaseInfoStruct getPurchaseByPK(String purchasePK) {
        final String QUERY = "SELECT * FROM " + PURCHASE_TABLE + " WHERE " + ID + " = " + purchasePK;
        return getPurchasesList(QUERY).get(0);
    }

    @SuppressLint("Range")
    private ArrayList<PurchaseInfoStruct> getPurchasesList(final String QUERY) {
        DecimalFormat decimalFormatTwoDecimals = new DecimalFormat("########.00");
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PurchaseInfoStruct> purchaseList = new ArrayList<>();
        Cursor cursor = db.rawQuery(QUERY, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            purchaseList.add(new PurchaseInfoStruct(cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                    decimalFormatTwoDecimals.format(cursor.getDouble(cursor.getColumnIndex(PURCHASE_COST))),
                    cursor.getString(cursor.getColumnIndex(DATE)),
                    cursor.getString(cursor.getColumnIndex(CATEGORY_ID))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return purchaseList;
    }

    public void deleteAllPurchasesForCategory(String toDelete) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PURCHASE_TABLE, CATEGORY_ID + " =?", new String[]{toDelete});
        db.close();
    }

    public void deletePurchase(String purchasePK) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PURCHASE_TABLE, ID + " =?", new String[]{purchasePK});
        db.close();
    }
}
