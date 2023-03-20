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

    public static int calculatePercentage(String remainingBudgetAmounts, String budgetAmount) {
        try {
            int remaining = Integer.parseInt(remainingBudgetAmounts);
            int total = Integer.parseInt(budgetAmount);
            int spent = total - remaining;
            if (spent == 0) {
                return 0;
            }
            return spent * 100 / total;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
