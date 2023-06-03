package ca.michaelbalcerzak.budgettozero;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.CommonHelpers.DateHelper;
import ca.michaelbalcerzak.budgettozero.CommonHelpers.PurchaseHelper;
import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.Database.CategoryResetAdmin;

public class SummaryCategoryListView extends BaseAdapter {
    private final Activity context;
    private final ArrayList<CategoryInfoStruct> _category;
    private final String _displayCurrency;


    public SummaryCategoryListView(Activity context, String displayCurrency) {
        this.context = context;
        CategoryAdmin categoryAdmin = new CategoryAdmin(context);
        _category = categoryAdmin.getAllCategories();
        _displayCurrency = displayCurrency;
    }


    @Override
    public int getCount() {
        return _category.size();
    }

    @Override
    public Object getItem(int position) {
        return _category.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CategoryInfoStruct getPurchaseInfoAtPosition(int position) {
        return _category.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.category_summary, null, true);


        TextView categortyName = rowView.findViewById(R.id.categoryName);
        TextView budgetAmount = rowView.findViewById(R.id.budgetAmount);
        ProgressBar budgetSpent = rowView.findViewById(R.id.PROGRESS_BAR);
        ProgressBar daysIntoBudget = rowView.findViewById(R.id.PROGRESS_BAR2);

        categortyName.setText(_category.get(position).getName());
        String headerBudgetAmount = _displayCurrency + _category.get(position).getRemainingBudgetAmount() + "/" + _displayCurrency + _category.get(position).getBudgetAmount();
        budgetAmount.setText(headerBudgetAmount);
        int completionPercentage = PurchaseHelper.calculatePercentage(_category.get(position).getRemainingBudgetAmount(), _category.get(position).getBudgetAmount());
        budgetSpent.setProgress(completionPercentage);

        CategoryResetAdmin categoryResetAdmin = new CategoryResetAdmin(context);
        ResetFrequencyInfoStruct resetFrequency = categoryResetAdmin.getResetByCategoryPK(_category.get(position).getCategoryPk());
        String date = resetFrequency.getRESET_DATE();
        int daysUntilReset = DateHelper.getNumberOfDaysUntilReset(date);
        int intervalLength = ResetInterval.valueOf(_category.get(position).getResetInterval()).getDaysPerInterval();
        daysIntoBudget.setProgress((intervalLength - daysUntilReset) * 100 / intervalLength);
        return rowView;
    }
}
