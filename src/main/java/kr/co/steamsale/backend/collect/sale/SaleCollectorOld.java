/*package kr.co.steamsale.backend.collect.sale;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kr.co.steamsale.backend.data.AppSale;
import kr.co.steamsale.backend.service.CollectService;
import kr.co.steamsale.backend.util.ScrapHelper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.prismweaver.backend.batch.AbstractBatch;

public class SaleCollectorOld extends AbstractBatch{

	// featured를 파싱해야 하지 않을까..
	// featured는 BIG PICTURE모드라서 쓸수가 없음.
	// 전체 데이터를 스크래핑 후 가격차이가 나는것을 appsale로 묶어야 하지 않나 싶음.
	private static final String programId = "SS_COL_004";
	private static final String saleURL = "http://store.steampowered.com/search/results?sort_by=_ASC&specials=1&page=%d";
	
	public SaleCollectorOld() {
		super(programId);
	}

	public static void main(String[] args) {
		new SaleCollectorOld().execute();
	}

	@Override
	protected void run() {
		logger.info("SaleCollector on");
		
		int total = 0;
		
		Date date = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("hhmm");
		String execDate = sdfDate.format(date);
		String execTime = sdfTime.format(date);
		
		CollectService collectService = new CollectService();
		
		try {
			Document doc = ScrapHelper.scrap(String.format(saleURL, 1), false);
			Elements elems = doc.select(".search_pagination_left");
						
			String count = elems.text();
			int totalCount = Integer.parseInt(count.split("/")[1].trim()); // 무조건 있음.
			int stand = ((totalCount % 25) == 0) ? (totalCount / 25) : (totalCount / 25) + 1;
			
			Map<Integer, AppSale> sourceMap = new HashMap<Integer, AppSale>();
			
			for(int i=1; i<(stand + 1); i++){
				doc = ScrapHelper.scrap(String.format(saleURL, i), false);
				elems = doc.select("#search_result_container div a[data-ds-appid]");
				
				for(Element elem : elems){
					String href = elem.attr("href");
					int appId = 0;
					int packageId = 0;
					
					if(href.contains("app")){
						appId = Integer.parseInt(href.substring(href.indexOf("/app"), href.indexOf("?")).replaceAll("\\D+",""));
					}else if(href.contains("sub")){
						packageId = Integer.parseInt(href.substring(href.indexOf("/sub"), href.indexOf("?")).replaceAll("\\D+",""));
					}
					
					if(appId > 0 || packageId > 0){
						AppSale appSale = new AppSale();
						appSale.setPackageId(packageId);
						appSale.setAppId(appId);
						appSale.setExecDate(execDate);
						appSale.setExecTime(execTime);
						
						int id = (packageId == 0) ? appId : packageId;
						
						sourceMap.put(id, appSale);
					}
				}
			}
			
			Iterator<Integer> keys = sourceMap.keySet().iterator();
	        while(keys.hasNext() ){
	            Integer key = keys.next();
	            total += collectService.putAppSaleList(sourceMap.get(key), programId);
	        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("SaleCollector TOTAL : " + total);
		logger.info("SaleCollector off");
	}

}
*/