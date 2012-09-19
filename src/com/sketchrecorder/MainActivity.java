package com.sketchrecorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.sketchrecorder.R;

public class MainActivity extends Activity implements View.OnTouchListener
{
	CanvasView canvas;
	Sketch sketch;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        sketch = new Sketch();
        
        canvas = (CanvasView)findViewById(R.id.canvasView);
        canvas.setOnTouchListener(this);
        canvas.setSketch(sketch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle item selection
        switch (item.getItemId()) 
        {
	        case R.id.menu_clear:
	        	clearPressed();
	            return true;
	        
	        case R.id.menu_save:
	            savePressed();
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    public void clearPressed()
    {
		sketch.clear();
		canvas.invalidate();
    }
    
    public void savePressed()
    {
    	writeToSDCard(sketch);    	
    	Toast.makeText(getApplicationContext(), "Sketch saved", Toast.LENGTH_SHORT).show();
    	
    	sketch.clear();
    	canvas.invalidate();
    }
    
	public boolean onTouch(View v, MotionEvent event)
	{
		int action = event.getAction();
	    
		final int historySize = event.getHistorySize();
	    final int pointerCount = event.getPointerCount();
	     
		//Log.v("ON_TOUCH", "Action = " + action + " View:" + v.toString());
	     
	    for (int h = 0; h < historySize; h++) 
	    {
	        //System.out.printf("At time %d:", event.getHistoricalEventTime(h));
	         
	        for (int p = 0; p < pointerCount; p++) 
	        {
	            //System.out.printf("  pointer %d: (%f,%f)",event.getPointerId(p), event.getHistoricalX(p, h), event.getHistoricalY(p, h));
	             
	        	sketch.addTouch(new Touch(event.getHistoricalX(p, h),
	            		 		event.getHistoricalY(p, h),
	            		 		event.getHistoricalEventTime(h)));
	         }
	     }
	    
	    switch (action & MotionEvent.ACTION_MASK) 
	    {
	    	case MotionEvent.ACTION_UP:
	    		sketch.endStroke();
	    		break;
	    		
	    	default:
	    		break;
	    }
	    
	    canvas.invalidate();
	    
	    return true;
	}
	
	public void writeToSDCard(Sketch sketch)
	{
    	JSONObject sketchJSON = sketch.getJSON();
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_HH-mm-ss");
    	Date currDate = new Date();
    	String filename = dateFormat.format(currDate) + ".txt";
    	
    	File sdcard = Environment.getExternalStorageDirectory();
    	File folder = new File (sdcard.getAbsolutePath() + "/SketchRecorder");
    	
    	boolean createFolderSuccess;
    	
    	if (!folder.exists())
    	{
    		createFolderSuccess = folder.mkdir();
    		
    		if (!createFolderSuccess)
    		{
    			Log.e("SketchRecorder", "Error writing file - failed to create folder");
    			return;
    		}
    	}
    	
    	File file = new File(folder, filename);
    	
    	try 
    	{
    		PrintWriter out = new PrintWriter(file);
			out.println(sketchJSON);
			
			out.close();
		}
    	catch (FileNotFoundException e) 
    	{
			Log.e("SketchRecorder", "Error writing file - file not found");
		}
	}
}
