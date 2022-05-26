package kr.co.steamsale.backend.collect.sub;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import kr.co.steamsale.backend.data.Platform;
import kr.co.steamsale.backend.data.Price;
import kr.co.steamsale.backend.data.ReleaseDate;
import kr.co.steamsale.backend.data.Sub;
import kr.co.steamsale.backend.service.CollectService;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.prismweaver.backend.batch.AbstractBatch;

public class SubCollector extends AbstractBatch {

	private static final String programId = "SS_COL_006";
	private static final String sourceURL = "http://store.steampowered.com/api/packagedetails/?packageids=%d&l=koreana&cc=KR";
	private static final int Zzz = 2000;

	public SubCollector() {
		super(programId);
	}

	public static void main(String[] args) {
		new SubCollector().execute();
	}

	@Override
	protected void run() {
		logger.info("SubCollector on");

		int total = 0;

		CollectService collectService = new CollectService();
		// for test
		// List<Sub> subs = collectService.getPackageList(230230);
		List<Sub> subs = collectService.getPackageList();

		for (Sub sub : subs) {
			String url = String.format(sourceURL, sub.getPackageId());

			try {
				JSONObject origObj = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));

				if (origObj.getJSONObject(String.valueOf(sub.getPackageId())).getBoolean("success")) {
					JSONObject list = origObj.getJSONObject(String.valueOf(sub.getPackageId())).getJSONObject("data");
					// price
					Sub data = getPackageInfo(list);
					data.setPackageId(sub.getPackageId());
					total += collectService.setPackageInfo(data, programId);
				}

				Thread.sleep(Zzz);

				logger.info(sub.getPackageId() + ", " + "Complete.");
			} catch (Exception e) {
				logger.error(sub.getPackageId() + ", " + e.getMessage());
				if (e.getMessage().contains("429 for URL")) {
					try {
						Thread.sleep(Zzz * 60);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(Zzz);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

			}

		}
		
		logger.info("SubCollector TOTAL : " + total);
		logger.info("SubCollector off");
	}

	private Sub getPackageInfo(JSONObject list) {
		
		Sub data = new Sub();
		
		data.setName(list.getString("name"));
		data.setPageImage(list.getString("page_image"));
		if(list.has("header_image"))
			data.setHeaderImage(list.getString("header_image"));
		data.setSmallLogo(list.getString("small_logo"));
		
		// 우선 제외
		/*List<App> apps = new ArrayList<App>();
		JSONArray appArray = list.getJSONArray("apps");
		
		for(int i=0; i<appArray.length(); i++){
			App app = new App();
			app.setAppId(appArray.getJSONObject(i).getInt("id"));
			app.setAppId(appArray.getJSONObject(i).getInt("id"));
			
			apps.add(app);
		}
		
		data.setApps(apps);
		*/
		
		if(list.has("price")){
			Price price = new Price();
			price.setCurrency(list.getJSONObject("price").getString("currency"));
			price.setDiscountPercent(list.getJSONObject("price").getLong("discount_percent"));
			price.setFinalPrice(list.getJSONObject("price").getDouble("final") / 100);
			price.setInitialPrice(list.getJSONObject("price").getDouble("initial") / 100);
			price.setIndividualPrice(list.getJSONObject("price").getDouble("individual") / 100);
			data.setPrice(price);
		}
		
		Platform platform = new Platform();	
		platform.setLinux(list.getJSONObject("platforms").getBoolean("linux"));
		platform.setMac(list.getJSONObject("platforms").getBoolean("mac"));
		platform.setWindows(list.getJSONObject("platforms").getBoolean("windows"));
		data.setPlatform(platform);
		
		ReleaseDate releaseDate = new ReleaseDate();
		releaseDate.setComingSoon(list.getJSONObject("release_date").getBoolean("coming_soon"));
		releaseDate.setReleaseDate(list.getJSONObject("release_date").getString("date"));
		data.setReleaseDate(releaseDate);
		
		return data;
	}
}
