package com.mad.moneySac.activities;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mad.moneySac.R;
import com.mad.moneySac.model.SacEntryType;

public class Widget extends AppWidgetProvider {

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			// Create an Intent to launch ExampleActivity
			Intent intentExpense = new Intent(context, EditEntryActivity.class);
			intentExpense.putExtra(MoneySac.TYPE_EXTRA, SacEntryType.EXPENSE);
			PendingIntent pendingIntentExpense = PendingIntent.getActivity(context, 0, intentExpense, PendingIntent.FLAG_UPDATE_CURRENT);

			Intent intentIncome = new Intent(context, EditEntryActivity.class);
			intentIncome.putExtra(MoneySac.TYPE_EXTRA, SacEntryType.INCOME);
			PendingIntent pendingIntentIncome = PendingIntent.getActivity(context, 1, intentIncome, PendingIntent.FLAG_UPDATE_CURRENT);
			
			// Get the layout for the App Widget and attach an on-click listener to the button
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			views.setOnClickPendingIntent(R.id.widgetButtonPlus, pendingIntentIncome);
			views.setOnClickPendingIntent(R.id.widgetButtonMinus, pendingIntentExpense);
			
			// Tell the AppWidgetManager to perform an update on the current app widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}