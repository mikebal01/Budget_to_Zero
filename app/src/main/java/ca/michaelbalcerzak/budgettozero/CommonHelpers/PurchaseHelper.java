package ca.michaelbalcerzak.budgettozero.CommonHelpers;

import android.content.Context;

import ca.michaelbalcerzak.budgettozero.CategoryInfoStruct;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.Database.PurchaseAdmin;
import ca.michaelbalcerzak.budgettozero.PurchaseInfoStruct;

public class PurchaseHelper {
    public static void deletePurchaseAndRestoreCostToBudget(Context context, PurchaseInfoStruct purchase) {
        CategoryAdmin categoryAdmin = new CategoryAdmin(context);
        PurchaseAdmin purchaseAdmin = new PurchaseAdmin(context);

        CategoryInfoStruct originalCategory = categoryAdmin.getCategoryByPk(purchase.getCategoryPK());
        double restoredBudget = Double.parseDouble(purchase.getSpendAmount());
        restoredBudget += Double.parseDouble(originalCategory.getRemainingBudgetAmount());
        categoryAdmin.adjustCategoryForPurchase(originalCategory.getCategoryPk(), String.valueOf(restoredBudget));
        purchaseAdmin.deletePurchase(purchase.getPurchasePK());
    }
}
