package ca.michaelbalcerzak.budgettozero;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class PurchaseHistoryListView extends BaseAdapter {
    private final Activity context;
    private final PurchaseInfoStruct[] _purchases;


    public PurchaseHistoryListView(Activity context, PurchaseInfoStruct[] purchases) {
       // super(context, R.layout.purchasehistory);
        // TODO Auto-generated constructor stub

        this.context=context;
        _purchases=purchases;
    }


    @Override
    public int getCount() {
        return _purchases.length;
    }

    @Override
    public Object getItem(int position) {
        return _purchases[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.purchasehistory, null,true);

        TextView purchaseDate = (TextView) rowView.findViewById(R.id.purchaseDate);
        TextView purchaseDescription = (TextView) rowView.findViewById(R.id.purchaseDescription);
        TextView purchaseCost = (TextView) rowView.findViewById(R.id.purchaseCost);

        purchaseDate.setText(_purchases[position].getSpendDate());
        purchaseDescription.setText(_purchases[position].getDescription());
        purchaseCost.setText(_purchases[position].getSpendAmount());

        return rowView;

    };
}
