package com.mad.moneySac.activities;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.PieLabelLinkStyle;
import org.afree.chart.plot.PiePlot;
import org.afree.data.general.DefaultPieDataset;
import org.afree.data.general.PieDataset;
import org.afree.graphics.PaintType;
import org.afree.graphics.SolidColor;
import org.afree.graphics.geom.Font;
import org.afree.ui.RectangleInsets;
import org.afree.util.UnitType;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;

import com.mad.moneySac.R;
import com.mad.moneySac.model.StatsView;

public class StatsActivity extends Activity {
	
	private static final RectangleInsets ZERO_INSETS = new RectangleInsets(UnitType.ABSOLUTE, 0.0, 0.0, 0.0, 0.0);
	private static final PaintType transparent	= new SolidColor(color.transparent);
	private static final PaintType white		= new SolidColor(color.white);
	private static final PaintType green		= new SolidColor(color.holo_green_light);
	private static final PaintType red			= new SolidColor(color.holo_red_light);

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
		
		configureChart();
		
		statsView.drawChart(statsChart);	
        setContentView(statsView);
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
		
		return chart;
	}
	
	private void configureChart() {
		statsChart.setBackgroundPaintType(transparent);
		// statsChart.setBorderEffect(effect);
		statsChart.setBorderPaintType(transparent);
		statsChart.setBorderStroke(0.0F);
		statsChart.setBorderVisible(false);
		statsChart.setNotify(false);
		
		statsPlot = (PiePlot) statsChart.getPlot();
		
		statsPlot.setLabelFont(new Font("SansSerif", Typeface.NORMAL, 24));
		statsPlot.setNoDataMessage("No data available");
		statsPlot.setCircular(true);
		statsPlot.setAutoPopulateSectionOutlinePaint(true);
		statsPlot.setAutoPopulateSectionOutlineStroke(true);
		statsPlot.setAutoPopulateSectionPaint(true);
		statsPlot.setInteriorGap(0.002);
		statsPlot.setLabelGap(0.0);
		statsPlot.setLabelLinkMargin(0.0);
		statsPlot.setLabelLinkStroke(0.3F);
		statsPlot.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);
		statsPlot.setLabelLinksVisible(true);
		statsPlot.setLabelOutlineStroke(0.0F);
		statsPlot.setIgnoreNullValues(true);
		statsPlot.setIgnoreZeroValues(true);
		statsPlot.setShadowXOffset(0.0);
		statsPlot.setShadowYOffset(0.0);
		statsPlot.setSimpleLabels(true);
		statsPlot.setStartAngle(270.0);
		statsPlot.setOutlineVisible(false);
		
		statsPlot.setBackgroundPaintType(transparent);
		statsPlot.setLabelBackgroundPaintType(transparent);
		statsPlot.setLabelLinkPaintType(transparent);
		statsPlot.setLabelOutlinePaint(transparent);
		statsPlot.setLabelShadowPaint(transparent);
		statsPlot.setShadowPaint(new Paint(Color.TRANSPARENT));

		// statsPlot.setSectionPaintType("Ausgaben", red);
		// statsPlot.setSectionPaintType("Einnahmen", green);
	}
	
    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        getExtrasFromBundle();
        
        dataset.setValue("Ausgaben", expense);
        dataset.setValue("Einnahmen", income);
        
        return dataset;
    }

	private void getExtrasFromBundle() {
		Bundle extras = getIntent().getExtras();
		if (extras.containsKey(MoneySac.INCOME)) income = (Double) extras.get(MoneySac.INCOME);
		if (extras.containsKey(MoneySac.EXPENSE)) expense = (Double) extras.get(MoneySac.EXPENSE);
	}

}
