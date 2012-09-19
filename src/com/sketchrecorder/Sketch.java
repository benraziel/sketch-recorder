package com.sketchrecorder;

import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sketch 
{
	List<Stroke> strokes;
	
	public Sketch()
	{
		strokes = new Vector<Stroke>();
	}
	
	public List<Stroke> getStrokes()
	{
		return strokes;
	}
	
	public void endStroke()
	{
		strokes.add(new Stroke());
	}
	
	public void addTouch(Touch touch)
	{
		if (strokes.isEmpty())
		{
			strokes.add(new Stroke());
		}
		
		Stroke currStroke = strokes.get(strokes.size() - 1);
		currStroke.add(touch);
	}
	
	public void clear()
	{
		strokes.clear();
	}
	
	public JSONObject getJSON()
	{
		JSONObject sketchObject = new JSONObject();
		JSONArray strokesJSONArray = new JSONArray();
		
		int strokeCount = 0;
		
		try
		{
			for (Stroke stroke : strokes)
			{
				if (!stroke.isEmpty())
				{
					strokesJSONArray.put(stroke.getJSON());
					strokeCount++;
				}
			}
			
			sketchObject.put("strokeCount", strokeCount);
			sketchObject.put("strokes", strokesJSONArray);
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
		return sketchObject;
	}
}
