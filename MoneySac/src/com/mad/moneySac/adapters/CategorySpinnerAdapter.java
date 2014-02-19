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

public class CategorySpinnerAdapter extends ArrayAdapter<SacEntryType> {
    private final Context context;
    private final List<SacEntryType> values;

    public CategorySpinnerAdapter(Context context, int txtViewResourceId, List<SacEntryType> values) {
        super(context, txtViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	
	/*
	 * @return View gibt Listenelement zurueck
	 */
	public View getCustomView(int position, View convertView, ViewGroup parent) {
	
    
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.category_spinneritem, parent, false);
        TextView type = (TextView) rowView.findViewById(R.id.moneysac_category_type_spinner);
        ImageView icon = (ImageView) rowView.findViewById(R.id.moneysac_category_img_spinner);
        
        type.setText(values.get(position).getName());
        int iconID = values.get(position).getCategoryIcon();
        icon.setImageResource(iconID);

        return rowView;
    }
}
