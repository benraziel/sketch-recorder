package com.sketchrecorder;

import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Stroke 
{
	List<Touch> touches;
	
	public Stroke()
	{
		touches = new Vector<Touch>();
	}
	
	public void add(Touch touch)
	{
		touches.add(touch);
	}
	
	public void clear()
	{
		touches.clear();
	}
	
	public List<Touch> getTouches()
	{
		return touches;
	}
	
	public boolean isEmpty()
	{
		return touches.isEmpty();
	}
	
	public JSONObject getJSON()
	{
		JSONObject strokeObject = new JSONObject();
		JSONArray strokeJSONArray = new JSONArray();
		
		for (Touch touch : touches)
		{
			strokeJSONArray.put(touch.getJSON());
		}
		
		try
		{
			strokeObject.put("pointCount", touches.size());
			strokeObject.put("points", strokeJSONArray);
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
		return strokeObject;
	}
}
