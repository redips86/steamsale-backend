package kr.co.steamsale.backend.collect.franchise;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import kr.co.steamsale.backend.data.Franchise;
import kr.co.steamsale.backend.data.FranchiseChild;
import kr.co.steamsale.backend.service.CollectService;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.prismweaver.backend.batch.AbstractBatch;

public class FranchiseCollector extends AbstractBatch {

	private static final String programId = "SS_COL_008";
	private static final String sourceURL = "http://store.steampowered.com/api/salepage/?id=%s&l=koreana&cc=KR";

	private static final int Zzz = 2000;

	public FranchiseCollector() {
		super(programId);
	}

	@Override
	protected void run() {
		logger.info("FranchiseCollector on");

		int total = 0;

		CollectService collectService = new CollectService();
		List<Franchise> list = collectService.getFranchiseWlist();

		for (Franchise data : list) {
			String url = String.format(sourceURL, data.getSaleId());

			try {
				JSONObject origObj = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));

				if (origObj.getInt("status") == 1) {
					Franchise franchise = new Franchise();
					franchise.setSaleId(data.getSaleId());
					franchise.setSaleName(origObj.getString("name"));
					franchise.setAvailable(origObj.getString("available"));
					
					JSONArray childObj = origObj.getJSONArray("items");
					
					collectService.removeFranchiseDetailInfo(franchise);
					collectService.putFranchiseInfo(franchise, programId);
					for(int i=0; i<childObj.length(); i++){
						FranchiseChild child = new FranchiseChild();	
						child.setId(childObj.getJSONObject(i).getInt("id"));
						child.setName(childObj.getJSONObject(i).getString("name"));
						child.setType(childObj.getJSONObject(i).getInt("type"));
						franchise.setFranchiseChild(child);	
						
						total += collectService.putFranchiseDetailInfo(franchise, programId);
					}
					
					
					logger.info("url : " + url + " complete");
				}
				Thread.sleep(Zzz);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		logger.info("FranchiseCollector TOTAL : " + total);
		logger.info("FranchiseCollector off");

	}

	public static void main(String[] args) {
		new FranchiseCollector().execute();
	}

}
