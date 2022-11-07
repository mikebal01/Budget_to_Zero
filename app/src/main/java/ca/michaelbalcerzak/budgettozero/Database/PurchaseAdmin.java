package ca.michaelbalcerzak.budgettozero.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ca.michaelbalcerzak.budgettozero.PurchaseInfoStruct;

public class PurchaseAdmin extends MainDatabase{

    private final String PURCHASE_TABLE = "purchase";
    private final String CATEGORY_NAME = "category_name";
    private final String DESCRIPTION = "description";
    private final String PURCHASE_COST = "cost";
    private final String DATE = "date";

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
}
