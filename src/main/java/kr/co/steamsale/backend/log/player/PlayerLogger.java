package kr.co.steamsale.backend.log.player;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.co.steamsale.backend.data.App;
import kr.co.steamsale.backend.data.PlayerLog;
import kr.co.steamsale.backend.service.CollectService;
import kr.co.steamsale.backend.service.LogService;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.prismweaver.backend.batch.AbstractBatch;

public class PlayerLogger extends AbstractBatch{
	
	// [2015-06-10 09:52:44] INFO  - AbstractBatch.endBatch(65)     | Elaspe time : 3624.644 seconds
	private static final String programId = "SS_LOG_002";
	private static final String apiUrl = "http://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1?appId=%d";

	public PlayerLogger() {
		super(programId);
	}

	@Override
	protected void run() {
		logger.info("PlayerLogger on");
		
		int total = 0;
		
		CollectService collectService = new CollectService();
		LogService logService = new LogService();
		
		Date date = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HHmm");
		String execDate = sdfDate.format(date);
		String execTime = sdfTime.format(date);
		
		List<App> list = collectService.getAppList();
		
		for(App data : list){
			try {
				JSONObject origObj = new JSONObject(IOUtils.toString(new URL(String.format(apiUrl, data.getAppId())), Charset.forName("UTF-8")));
				JSONObject responsObj = origObj.getJSONObject("response");
				
				if(responsObj.has("player_count")){
					logger.info("id : " + data.getAppId());
					
					PlayerLog playerLog = new PlayerLog();
					playerLog.setExecDate(execDate);
					playerLog.setExecTime(execTime);
					playerLog.setId(data.getAppId());
					playerLog.setPlayerCount(responsObj.getInt("player_count"));
					
					total += logService.putPlayerLog(playerLog, programId);
				}	
			} catch (Exception e) {
				logger.error(e.getMessage());
			}	
		}
		
		logger.info("PlayerLogger TOTAL : " + total);
		logger.info("PlayerLogger off");
	}

	public static void main(String[] args) {
		new PlayerLogger().execute();
	}

}
