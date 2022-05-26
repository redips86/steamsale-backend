package kr.co.steamsale.backend.util;

import org.json.JSONArray;

public class ParseHelper {
	public static String jsonArrayToString(JSONArray array){
		
		String result = "";
		
		for(int i=0; i<array.length(); i++){
			result += array.getString(0);
		}
		
		return result;
	}
}
