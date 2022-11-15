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

import java.util.ArrayList;

public class PurchaseHistoryListView extends BaseAdapter {
    private final Activity context;
    private final ArrayList<PurchaseInfoStruct> _purchases;


    public PurchaseHistoryListView(Activity context, ArrayList<PurchaseInfoStruct> purchases) {
       // super(context, R.layout.purchasehistory);
        // TODO Auto-generated constructor stub

        this.context=context;
        _purchases=purchases;
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
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.purchasehistory, null,true);

        TextView purchaseDate = rowView.findViewById(R.id.purchaseDate);
        TextView purchaseDescription = rowView.findViewById(R.id.purchaseDescription);
        TextView purchaseCost = rowView.findViewById(R.id.purchaseCost);

        purchaseDate.setText(_purchases.get(position).getSpendDate());
        purchaseDescription.setText(_purchases.get(position).getDescription());
        purchaseCost.setText(_purchases.get(position).getSpendAmount());

        return rowView;
    }
}
