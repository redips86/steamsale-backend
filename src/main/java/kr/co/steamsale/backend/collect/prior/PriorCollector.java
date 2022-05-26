package kr.co.steamsale.backend.collect.prior;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.steamsale.backend.data.App;
import kr.co.steamsale.backend.data.Franchise;
import kr.co.steamsale.backend.service.CollectService;
import kr.co.steamsale.backend.util.ScrapHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import com.prismweaver.backend.batch.AbstractBatch;

public class PriorCollector extends AbstractBatch {
	/*
	applist api가 전체 게임을 반환하지 않음을 확인했음.
	franchise page 정보는 news feed, Main Page에서만 찾을 수 있었음. (아무리 찾아도 안보임)
	따라서, news feed를 script하고, 신규 등록 게임은 wlist에 넣고
	franchise 정보는 따로 저장해서 sale과 연계한다.
	*/
	private static final String programId = "SS_COL_007";
	private static final String sourceURL = "http://store.steampowered.com/feeds/special_deals.xml";
	private static final String mainURL = "http://store.steampowered.com/";
	
	private static final String appPatt = "http://store.steampowered.com/app/[0-9]+";
	//private static final String subPatt = "http://store.steampowered.com/sub/[0-9]+";
	private static final String franchisePatt = "http://store.steampowered.com/sale/[0-9a-zA-Z-_]+";
	
	public PriorCollector() {
		super(programId);
	}

	@Override
	protected void run() {
		logger.info("PriorCollector on");
		
		CollectService collectService = new CollectService();
		
		int appTotal = 0;
		int franchiseTotal = 0;
		
		try {
			Document doc = ScrapHelper.scrap(sourceURL, false);
			String htmlText = Jsoup.clean(doc.body().html(), Whitelist.simpleText());
			
			// 1. APP_WLIST
			Pattern appP = Pattern.compile(appPatt);
			Matcher appM = appP.matcher(htmlText);
			
			while (appM.find()) {
			   App data = new App();
			   data.setAppId(Integer.parseInt(appM.group().replaceAll("\\D+","")));
			   appTotal += collectService.putAppWlist(data, programId);
			}
			
			
			// 2. SUB_WLIST 건수가 5건 미만이라 우선 제외
			
			// 3. FRANCHISE from news feed
			Pattern franchiseP = Pattern.compile(franchisePatt);
			Matcher franchiseM = franchiseP.matcher(htmlText);
			
			while (franchiseM.find()) {
			   Franchise data = new Franchise();
			   String result = franchiseM.group();
			   data.setSaleId(result.substring(result.lastIndexOf("/") + 1));
			   franchiseTotal += collectService.setFranchiseInfo(data, programId);
			}
			
			// 4. FRANCHISE from Main Page
			doc = ScrapHelper.scrap(mainURL, false);
			htmlText = doc.html();
			franchiseM = franchiseP.matcher(htmlText);
			
			while (franchiseM.find()) {
			   Franchise data = new Franchise();
			   String result = franchiseM.group();
			   data.setSaleId(result.substring(result.lastIndexOf("/") + 1));
			   franchiseTotal += collectService.setFranchiseInfo(data, programId);
			}
			
			logger.info("PriorCollector APP_TOTAL : " + appTotal);
			logger.info("PriorCollector FRANCHISE_Total : " + franchiseTotal);
			logger.info("PriorCollector off");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
			new PriorCollector().execute(); 
	}

}
