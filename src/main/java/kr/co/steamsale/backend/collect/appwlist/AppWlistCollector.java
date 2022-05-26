package kr.co.steamsale.backend.collect.appwlist;

import java.util.List;

import kr.co.steamsale.backend.data.App;
import kr.co.steamsale.backend.service.CollectService;

import com.prismweaver.backend.batch.AbstractBatch;

public class AppWlistCollector extends AbstractBatch {

	// reload tab을 따로 둘까
	private static final String programId = "SS_COL_002";
	
	public AppWlistCollector() {
		super(programId);
	}

	@Override
	protected void run() {
		logger.info("AppWlistCollector on");
		
		int total = 0;
		
		CollectService collectService = new CollectService();
		
		List<App> list = collectService.getAppList();
		
		for(App data : list){
			total += collectService.putAppWlist(data, programId);	
		}
		
		logger.info("AppWlistCollector TOTAL : " + total);
		logger.info("AppWlistCollector off");
	}

	public static void main(String[] args) {
		new AppWlistCollector().execute();
	}

}
