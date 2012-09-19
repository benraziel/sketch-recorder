package com.sketchrecorder;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View
{
	Sketch sketch;
	Paint lineStyle;
	
	public CanvasView(Context context) 
	{
		super(context);
		
		init();
	}
	
	public CanvasView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		
		init();
	}

	
	public CanvasView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		
		init();
	}
	
	public void init()
	{
		lineStyle = new Paint();
		lineStyle.setColor(Color.BLUE);
		
		lineStyle.setStyle(Style.STROKE);
		lineStyle.setStrokeWidth(3.0f);
		lineStyle.setStrokeCap(Paint.Cap.ROUND);
	}
	
	public void setSketch(Sketch sketch)
	{
		this.sketch = sketch;
	}
		
	public void onDraw(Canvas canvas)
	{
		List<Stroke> strokes = sketch.getStrokes();
		
		if ((strokes == null) || (strokes.isEmpty()))
		{
			return;
		}
		
		for (Stroke stroke : strokes)
		{
			if (stroke.isEmpty())
			{
				continue;
			}
			
			List<Touch> touches = stroke.getTouches();
			
			float prevX = touches.get(0).getX();
			float prevY = touches.get(0).getY();
			
			for (int i=1; i < touches.size(); i++)
			{
				float currX = touches.get(i).getX();
				float currY = touches.get(i).getY();
				
				canvas.drawLine(prevX, prevY, currX, currY, lineStyle);
				
				prevX = currX;
				prevY = currY;
			}
		}
	}
}
