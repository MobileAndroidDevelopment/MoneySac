package com.mad.moneySac.adapters;

/**
 * Created by Kev1n on 05.10.13.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.moneySac.R;
import com.mad.moneySac.activities.EditEntryActivity;
import com.mad.moneySac.activities.MoneySac;
import com.mad.moneySac.helpers.MoneyUtils;
import com.mad.moneySac.model.SacEntry;
import com.mad.moneySac.model.SacEntryType;

public class ListViewAdapter extends ArrayAdapter<SacEntry> {
	private final Context context;
	private final List<SacEntry> values;

	public ListViewAdapter(Context context, List<SacEntry> values) {
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
		SacEntry currentSacEntry = values.get(position);
		textViewDesc.setText(currentSacEntry.getDescription());
		textViewAmount.setText(MoneyUtils.getFormattedNumber(currentSacEntry.getAmount()));

		//get a formatted String from Date
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.", Locale.GERMAN);
		textViewDate.setText(sdf.format(new Date(currentSacEntry.getDateTime())).toString());
		// Change the icon for Windows and iPhone
		int listIcon = SacEntryType.getType(currentSacEntry.getType()).getListIcon();
		imageViewArrow.setImageResource(listIcon);
		return rowView;
	}


	public OnItemClickListener getCategoryItemClickListener(){
		return new CategoryItemClickListener();
	}
	
	private class CategoryItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
			Log.d("ON_ITEM_CLICK", "Position: " + position);
			Intent intent = new Intent(context, EditEntryActivity.class);
			intent.putExtra(MoneySac.ENTRY_EXTRA, values.get(position));
			context.startActivity(intent);
		}
	}
}
