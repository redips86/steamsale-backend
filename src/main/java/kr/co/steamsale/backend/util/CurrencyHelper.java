package kr.co.steamsale.backend.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyHelper {
	private static final String apiKey = "d4696b83738efc75f2cc720617707969";
	private static final String apiUrl = "http://apilayer.net/api/live?access_key=%s&currencies=%s&source=%s&format=1";
	
	public static double getCurrency(String from, String to) throws JSONException, MalformedURLException, IOException{
		JSONObject origObj = new JSONObject(IOUtils.toString(new URL(String.format(apiUrl, apiKey, to, from)), Charset.forName("UTF-8")));
		
		return origObj.getJSONObject("quotes").getDouble(from + to); 
	}
}
