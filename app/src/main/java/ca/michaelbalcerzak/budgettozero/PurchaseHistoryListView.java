package ca.michaelbalcerzak.budgettozero;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PurchaseHistoryListView extends BaseAdapter {
    private final Activity context;
    private final ArrayList<PurchaseInfoStruct> _purchases;
    private final String _displayCurrency;


    public PurchaseHistoryListView(Activity context, ArrayList<PurchaseInfoStruct> purchases, String displayCurrency) {
        // super(context, R.layout.purchasehistory);
        // TODO Auto-generated constructor stub

        this.context = context;
        _purchases = purchases;
        _displayCurrency = displayCurrency;
    }


    @Override
    public int getCount() {
        return _purchases.size();
    }

    @Override
    public Object getItem(int position) {
        return _purchases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.purchasehistory, null, true);

        TextView purchaseDate = rowView.findViewById(R.id.purchaseDate);
        TextView purchaseDescription = rowView.findViewById(R.id.purchaseDescription);
        TextView purchaseCost = rowView.findViewById(R.id.purchaseCost);

        purchaseDate.setText(_purchases.get(position).getSpendDate());
        purchaseDescription.setText(_purchases.get(position).getDescription());
        String cost = _displayCurrency + _purchases.get(position).getSpendAmount();
        purchaseCost.setText(cost);

        return rowView;
    }
}
