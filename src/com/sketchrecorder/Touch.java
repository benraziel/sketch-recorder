package com.sketchrecorder;

import org.json.JSONException;
import org.json.JSONObject;

public class Touch 
{
	float x;
	float y;
	long time;
	
	public Touch(float x, float y, long time)
	{
		this.x = x;
		this.y = y;
		this.time = time;
	}
	
	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}
	
	public long getTime()
	{
		return time;
	}
	
	public JSONObject getJSON()
	{
		JSONObject touchObject = new JSONObject();
		
		try
		{
			touchObject.put("time", time);
			touchObject.put("x", x);
			touchObject.put("y", y);
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
		return touchObject;
	}
}
