package com.mad.moneySac.model;

import org.afree.chart.AFreeChart;
import org.afree.graphics.geom.RectShape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

public class StatsView extends ImageView {
	
	Context context;
	private Bitmap		bitmap;
    private RectShape	rectArea;
    private Canvas		canvas;
    private AFreeChart	chart;

    public StatsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
    }

    public StatsView(Context context) {
        super(context);
        this.context = context;
        initChart();
    }

    private void initChart() {
        int width = getResources().getConfiguration().smallestScreenWidthDp;
        Toast.makeText(context, "size:"+width, Toast.LENGTH_SHORT).show();
    	bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
    	rectArea = new RectShape(0.0, 0.0, width, width);
    }

    public void drawChart(AFreeChart chart) {
        canvas = new Canvas(bitmap);
        this.chart = chart;             
        this.chart.draw(canvas, rectArea);
        setImageBitmap(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);               
    }
    
}
