package com.project.common.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrgJsonUtil {
	public  static JSONObject toJson(String objStr){
		try{
			JSONObject obj = new JSONObject(objStr);
			return obj;
		}catch(Exception e){
			return null;
		}
	}
	
	public static JSONArray toArray(String objStr){
		try{
			JSONArray array = new JSONArray(objStr);
			return array;
		}catch(Exception e){
			return null;
		}
	}
}
