package com.mad.moneySac.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.moneySac.R;
import com.mad.moneySac.model.Category;
import com.mad.moneySac.model.SacEntryType;

public class CategoryListViewAdapter extends ArrayAdapter<Category> {
    private final Context context;
    private final List<Category> values;

    public CategoryListViewAdapter(Context context, List<Category> values) {
        super(context, R.layout.category_listrow, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.category_listrow, parent, false);
        TextView titleCat = (TextView) rowView.findViewById(R.id.moneysac_category__title_listrow);
        TextView type = (TextView) rowView.findViewById(R.id.moneysac_category__type_listrow);
        ImageView icon = (ImageView) rowView.findViewById(R.id.moneysac_category_img_listrow);
        
        titleCat.setText(values.get(position).getName());
        type.setText(values.get(position).getType());
        int iconID = SacEntryType.getType(values.get(position).getType()).getCategoryIcon();
        icon.setImageResource(iconID);

        return rowView;
    }
}
