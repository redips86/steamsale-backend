package kr.co.steamsale.backend.collect.sale;

import java.util.Calendar;
import java.util.List;

import kr.co.steamsale.backend.data.Sale;
import kr.co.steamsale.backend.data.SaleWlist;
import kr.co.steamsale.backend.service.CollectService;
import kr.co.steamsale.backend.util.ScrapHelper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.prismweaver.backend.batch.AbstractBatch;

public class SaleCollector extends AbstractBatch {

	// discount_percent가 0이상인 APP, PACKAGE들의 SALE 속성을 수집한다.
	private static final String programId = "SS_COL_004";
	private static final String sourceURL = "http://store.steampowered.com/%s/%d/";
	private static final int Zzz = 1000;

	public SaleCollector() {
		super(programId);
	}

	public static void main(String[] args) {
		new SaleCollector().execute();
	}

	@Override
	protected void run() {
		logger.info("SaleCollector on");

		int total = 0;

		CollectService collectService = new CollectService();
		// for test
		//List<SaleWlist> list = collectService.getSaleWlist(6810);
		
		// for real
		List<SaleWlist> list = collectService.getSaleWlist();

		for (SaleWlist data : list) {
			try {
				Document doc = ScrapHelper.scrap(String.format(sourceURL, data.getType(), data.getId()), false);
				Elements elems = doc.select(".game_purchase_discount_countdown");
				
				boolean isDeleted = false;
				if (elems.hasText()) {
					Element elem = elems.get(0);
					
					String saleText = elem.text();

					String title = saleText.substring(0, saleText.indexOf("!"));
					
					Elements times = elem.select("span");
					
					Sale sale = new Sale();
					sale.setType(data.getType());
					sale.setId(data.getId());
					sale.setTitle(title);
					
					// 시간
					if(times.size() > 0){
						elem = doc.select(".game_area_purchase_game script").get(0);
						String time = elem.html().substring(elem.html().indexOf(",")).replaceAll("\\D+","");
						
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(Long.parseLong(time) * 1000);
						
						sale.setEndDate(calendar.getTime());
					}
					// 날짜
					else{
						String time = saleText.substring(saleText.indexOf(":") + 1);
						if(saleText.indexOf(":") == -1)
							time = saleText.substring(saleText.indexOf("!") + 1);
						
						int year =  Integer.parseInt(time.substring(0, time.indexOf("년")).trim().replaceAll("\\D+",""));
						int month =  Integer.parseInt(time.substring(time.indexOf("년") + 1, time.indexOf("월")).trim().replaceAll("\\D+",""));
						int day =  Integer.parseInt(time.substring(time.indexOf("월") + 1, time.indexOf("일")).trim().replaceAll("\\D+",""));
						
						Calendar calendar = Calendar.getInstance();
						calendar.set(year, month - 1, day, 0, 0, 0);
						sale.setEndDate(calendar.getTime());
					}
					
					total += collectService.setSaleInfo(sale, programId);
				}
				// 페이지가 사라진 케이스이므로 삭제처리.
				else{
					collectService.removeSaleInfo(data.getType(), data.getId());
					isDeleted = true;
				}
				
				logger.info("TYPE : " + data.getType() + ", ID : " + data.getId() + ", DELETE? : " + isDeleted);
				
				Thread.sleep(Zzz);
				
			} catch (Exception e) {
				logger.error("TYPE : " + data.getType() + ", ID : " + data.getId());
				logger.error(e.getMessage());
			}
		}

		logger.info("SaleCollector TOTAL : " + total);
		logger.info("SaleCollector off");
	}

}
