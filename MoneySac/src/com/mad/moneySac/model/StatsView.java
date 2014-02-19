package com.mad.moneySac.model;

import org.afree.chart.AFreeChart;
import org.afree.graphics.geom.RectShape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class StatsView extends ImageView {
	
	private Context		context;
	private Bitmap		bitmap;
    private RectShape	rectArea;
    private Canvas		canvas;
    private AFreeChart	chart;
    private int			width;

    public StatsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        this.width = 400;
    }

    public StatsView(Context context, int width) {
        super(context);
        this.context = context;
        this.width = width;
        initChart();
    }

    private void initChart() {
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
