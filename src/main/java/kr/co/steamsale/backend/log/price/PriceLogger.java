package kr.co.steamsale.backend.log.price;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.co.steamsale.backend.data.Price;
import kr.co.steamsale.backend.data.PriceLog;
import kr.co.steamsale.backend.service.CollectService;
import kr.co.steamsale.backend.service.LogService;
import kr.co.steamsale.backend.util.CurrencyHelper;

import org.json.JSONException;

import com.prismweaver.backend.batch.AbstractBatch;

public class PriceLogger extends AbstractBatch {

	// [2015-06-05 17:18:36] INFO  - AbstractBatch.endBatch(65)     | Elaspe time : 315.837 seconds
	private static final String programId = "SS_LOG_001";
	
	public PriceLogger() {
		super(programId);
	}

	@Override
	protected void run() {
		logger.info("PriceLogger on");
		
		int total = 0;
		
		CollectService collectService = new CollectService();
		LogService logService = new LogService();
		
		Date date = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HHmm");
		String execDate = sdfDate.format(date);
		String execTime = sdfTime.format(date);
		
		double currencyKRW;
		
		try {
			currencyKRW = CurrencyHelper.getCurrency("USD", "KRW");
			
			if(currencyKRW != 0){
				List<Price> list = collectService.getPriceList();
				
				for(Price data : list){
					logger.info("id : " + data.getId());
					
					PriceLog log = new PriceLog();
					log.setId(data.getId());
					log.setUsd(data.getFinalPrice());
					log.setKrw((float)(data.getFinalPrice() * currencyKRW));
					log.setExecDate(execDate);
					log.setExecTime(execTime);
					
					total += logService.putPriceLog(log, programId);
				}
			}else{
				throw new RuntimeException("can't fetch the currency information");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}	
		
		logger.info("PriceLogger TOTAL : " + total);
		logger.info("PriceLogger off");
	}

	public static void main(String[] args) {
		new PriceLogger().execute();
	}

}
