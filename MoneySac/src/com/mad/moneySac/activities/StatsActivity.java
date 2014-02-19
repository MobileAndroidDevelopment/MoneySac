package com.mad.moneySac.activities;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.PieLabelLinkStyle;
import org.afree.chart.plot.PiePlot;
import org.afree.data.general.DefaultPieDataset;
import org.afree.data.general.PieDataset;
import org.afree.graphics.geom.Font;
import org.afree.ui.RectangleInsets;
import org.afree.util.UnitType;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;

import com.mad.moneySac.R;
import com.mad.moneySac.model.StatsView;

public class StatsActivity extends Activity {
	
	private static final RectangleInsets ZERO_INSETS = new RectangleInsets(UnitType.ABSOLUTE, 0.0, 0.0, 0.0, 0.0);

	AFreeChart	statsChart;
	PiePlot		statsPlot;
	StatsView	statsView;
	double		income	= 0.0;
    double		expense	= 0.0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
		
		statsView = new StatsView(this, size.x);
		statsChart = createPieChart(createDataset());
		statsView.drawChart(statsChart);
		
        setContentView(statsView);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

	private AFreeChart createPieChart(PieDataset dataset) {
		AFreeChart chart = ChartFactory.createPieChart(	null,		// title
														dataset,	// dataset
														false,		// legend
														false,		// tooltips
														false );	// urls

		chart.setNotify(false);
		chart.setBorderStroke(0.0F);
		chart.setPadding(ZERO_INSETS);
		
		statsPlot = (PiePlot) chart.getPlot();
		statsPlot.setLabelFont(new Font("SansSerif", Typeface.NORMAL, 11));
		statsPlot.setNoDataMessage("No data available");
		statsPlot.setCircular(true);
		statsPlot.setAutoPopulateSectionOutlinePaint(true);
		statsPlot.setAutoPopulateSectionOutlineStroke(true);
		statsPlot.setAutoPopulateSectionPaint(true);
		statsPlot.setInteriorGap(0.0);
		statsPlot.setLabelGap(0.0);
		statsPlot.setLabelLinkMargin(0.0);
		statsPlot.setLabelLinkStroke(0.2F);
		statsPlot.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);
		statsPlot.setLabelLinksVisible(true);
		statsPlot.setLabelOutlineStroke(0.0F);
		
		return chart;
	}
	
    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        getExtrasFromBundle();
        
        dataset.setValue("Einnahmen", income);
        dataset.setValue("Ausgaben", expense);
        
        return dataset;
    }

	private void getExtrasFromBundle() {
		Bundle extras = getIntent().getExtras();
		if (extras.containsKey(MoneySac.INCOME)) income = (Double) extras.get(MoneySac.INCOME);
		if (extras.containsKey(MoneySac.EXPENSE)) expense = (Double) extras.get(MoneySac.EXPENSE);
	}

}
