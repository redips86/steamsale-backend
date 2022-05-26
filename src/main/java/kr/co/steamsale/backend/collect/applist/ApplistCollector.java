package kr.co.steamsale.backend.collect.applist;

import kr.co.steamsale.backend.data.App;
import kr.co.steamsale.backend.service.CollectService;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.steam.community.WebApi;
import com.prismweaver.backend.batch.AbstractBatch;

public class ApplistCollector extends AbstractBatch {

	private static final String programId = "SS_COL_001";

	public ApplistCollector() {
		super(programId);
	}

	public static void main(String[] args) {
		new ApplistCollector().execute(); 
	}

	@Override
	protected void run() {
		logger.info("ApplistCollector on");
		
		int total = 0;
		
		CollectService collectService = new CollectService();
		
        try {
            JSONObject data = new JSONObject(WebApi.getJSON("ISteamApps", "GetAppList", 2));

            JSONArray applistData = data.getJSONObject("applist").getJSONArray("apps");
            for(int i = 0; i < applistData.length(); i ++) {
                JSONObject appData = applistData.getJSONObject(i);
                App app = new App();
                app.setAppId(appData.getInt("appid"));
                app.setName(appData.getString("name"));
                // pizza issue 해결하려면 mysql 버전이 5.5+
                if(app.getAppId() != 387870)
                	total += collectService.setApp(app, programId);    
                logger.info(appData);
            }
            
        } catch(Exception e) {
        	logger.error(e.getMessage());
        }
        
        logger.info("ApplistCollector TOTAL : " + total);
        logger.info("ApplistCollector off");
	}

}
