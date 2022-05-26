package kr.co.steamsale.backend.collect.review;

import java.util.List;

import kr.co.steamsale.backend.data.App;
import kr.co.steamsale.backend.data.Review;
import kr.co.steamsale.backend.service.CollectService;
import kr.co.steamsale.backend.util.ScrapHelper;

import org.jsoup.nodes.Document;

import com.prismweaver.backend.batch.AbstractBatch;

public class ReviewCollector extends AbstractBatch {

	// '게임 소개'도 여기서 scrap.
	private static final String programId = "SS_COL_005";
	private static final String reviewURL = "http://store.steampowered.com/app/%d";
	private static final int Zzz = 2000;
	
	public ReviewCollector() {
		super(programId);
	}

	public static void main(String[] args) {
		new ReviewCollector().execute();
	}

	@Override
	protected void run() {
		logger.info("ReviewCollector on");
		
		int total = 0;
		
		CollectService collectService = new CollectService();
		String[] type = { "demo", "game", "mod", "dlc"}; 
		
		try {
			List<App> list = collectService.getAppList(type);
			
			for(App data : list){
		
				logger.info("id : " + data.getAppId());
				
				Document doc = ScrapHelper.scrap(String.format(reviewURL, data.getAppId()), false);
				
				if(doc.select("div.game_description_snippet") != null){
					String description = doc.select("div.game_description_snippet").text();
					collectService.setAppDescription(data.getAppId(), description);
				}
				
				
				if(doc.select(".user_reviews_filter_bar") != null){
					String positive = doc.select("div#ReviewsTab_positive.user_reviews_tab span.user_reviews_count").text();
					String negative = doc.select("div#ReviewsTab_negative.user_reviews_tab span.user_reviews_count").text();
					
					int positiveCount = positive.equals("") ? 0 : Integer.parseInt(positive.replaceAll("\\D+",""));
					int negativeCount = negative.equals("") ? 0 : Integer.parseInt(negative.replaceAll("\\D+",""));
					
					Review review = new Review(); 
					review.setId(data.getAppId());
					review.setPositive(positiveCount);
					review.setNegative(negativeCount);
					
					total += collectService.setAppReviewCount(review, programId);
					
					Thread.sleep(Zzz);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			if(e.getMessage().contains("429 for URL")){
				try {
					Thread.sleep(Zzz * 60);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			else{
				try {
					Thread.sleep(Zzz);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		logger.info("ReviewCollector TOTAL : " + total);
		logger.info("ReviewCollector off");
	}

}
