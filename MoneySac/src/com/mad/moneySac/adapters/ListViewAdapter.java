package com.mad.moneySac.adapters;

/**
 * Created by Kev1n on 05.10.13.
 */
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.moneySac.R;
import com.mad.moneySac.model.Entry;

public class ListViewAdapter extends ArrayAdapter<Entry> {
    private final Context context;
    private final LinkedList<Entry> values;

    public ListViewAdapter(Context context, LinkedList<Entry> values) {
        super(context, R.layout.listrow, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listrow, parent, false);
        TextView textViewDesc = (TextView) rowView.findViewById(R.id.textViewListRowDesc);
        TextView textViewAmount = (TextView) rowView.findViewById(R.id.textViewListRowAmount);
        TextView textViewDate = (TextView) rowView.findViewById(R.id.textViewListRowDate);
        ImageView imageViewArrow = (ImageView) rowView.findViewById(R.id.imageViewListRowArrow);
        textViewDesc.setText(values.get(position).getDesc());
        textViewAmount.setText(values.get(position).getAmount()+"");

        //get a formatted String from Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");
        textViewDate.setText(sdf.format(values.get(position).getDate()).toString());
        // Change the icon for Windows and iPhone
        if (values.get(position).getIsIncome()) {
            imageViewArrow.setImageResource(R.drawable.arrow_green);
        } else {
            imageViewArrow.setImageResource(R.drawable.arrow_red);
        }

        return rowView;
    }
}
