package kr.co.steamsale.backend.collect.appinfo;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import kr.co.steamsale.backend.data.App;
import kr.co.steamsale.backend.data.AppWlist;
import kr.co.steamsale.backend.data.Category;
import kr.co.steamsale.backend.data.Genre;
import kr.co.steamsale.backend.data.Metacritic;
import kr.co.steamsale.backend.data.Platform;
import kr.co.steamsale.backend.data.Price;
import kr.co.steamsale.backend.data.ReleaseDate;
import kr.co.steamsale.backend.data.Sub;
import kr.co.steamsale.backend.service.CollectService;
import kr.co.steamsale.backend.util.ParseHelper;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.prismweaver.backend.sync.AbstractMultiProcessRunner;

public class AppinfoCollectorHandler extends AbstractMultiProcessRunner<AppWlist> {

	/* Available with 200 processing within 5min */
	private static final String sourceURL = "http://store.steampowered.com/api/appdetails/?appids=%d&l=koreana&cc=%s&v=%d";
	private static final int Zzz = 2000;
	
	public AppinfoCollectorHandler(List<AppWlist> dataList, String oprt) {
		super(dataList, oprt);
	}

	@Override
	public void execute(AppWlist appWlist) {
		CollectService collectService = new CollectService();
		// for test
		// List<App> apps = collectService.getAppList(230230);
		
		// batch -> daemon 재활용
		List<App> apps = collectService.getAppList(appWlist.getAppId());
		
		for (App app : apps) {
			String url = String.format(sourceURL, app.getAppId(), Currency.getInstance(Locale.KOREA), 1);

			try {
				JSONObject origObj = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));

				if (origObj.getJSONObject(String.valueOf(app.getAppId())).getBoolean("success")) {
					JSONObject list = origObj.getJSONObject(String.valueOf(app.getAppId())).getJSONObject("data");
					
					App data = getAppInfo(list);
					
					// price
					for(Price price : getPriceInfo(list)){
						collectService.setPriceInfo(price, oprt);
					}
					
					// category
					collectService.removeAppCategoryInfo(data.getAppId());
					for(Category category : getCategoryInfo(list)){
						collectService.setCategoryInfo(category, oprt);
						collectService.putAppCategoryInfo(category, oprt);
					}
					
					// genre
					collectService.removeAppGenreInfo(data.getAppId());
					for(Genre genre : getGenreInfo(list)){
						collectService.setGenreInfo(genre, oprt);
						collectService.putAppGenreInfo(genre, oprt);
					}
							
					
						
					// Movie
						
					// Package
					collectService.removeAppPackageInfo(data.getAppId());
					for(Sub sub : getPackageInfo(list)){
						collectService.putAppPackageInfo(sub, oprt);
					}
						
					// Screenshot
					
					// DLC
					
					
					collectService.setAppInfo(data, oprt);
					
						
				}
				
				Thread.sleep(Zzz);	
				
				//logger.info(app.getAppId() + ", " + "Complete.");
			} catch (Exception e) {
				logger.error(app.getAppId() + ", " + e.getMessage());
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
		}
	}

	@Override
	public int updateStatus(AppWlist data, String status, String preStatus, String oprt) {
		return new CollectService().setAppWlistStatus(data, status, preStatus, oprt);
	}
	
	/* ----------------- 추후 common으로 분리 ----------------------------------------*/
	private App getAppInfo(JSONObject list){
		
		App data = new App();
		data.setAppId(list.getInt("steam_appid"));
		data.setName(list.getString("name"));

		data.setAboutTheGame(list.getString("about_the_game"));
		if(list.has("developers"))
			data.setDevelopers(ParseHelper.jsonArrayToString(list.getJSONArray("developers")));
		data.setHeaderImage(list.getString("header_image"));
		
		ReleaseDate releaseDate = new ReleaseDate();
		releaseDate.setComingSoon(list.getJSONObject("release_date").getBoolean("coming_soon"));
		releaseDate.setReleaseDate(list.getJSONObject("release_date").getString("date"));
		data.setReleaseDate(releaseDate);

		if (list.optJSONObject("linux_requirements") != null && list.optJSONObject("linux_requirements").has("minimum"))
			data.setLinuxReqMin(list.getJSONObject("linux_requirements").getString("minimum"));
		if (list.optJSONObject("linux_requirements") != null && list.optJSONObject("linux_requirements").has("recommended"))
			data.setLinuxReqRec(list.getJSONObject("linux_requirements").getString("recommended"));
		if (list.optJSONObject("mac_requirements") != null && list.optJSONObject("mac_requirements").has("minimum"))
			data.setMacReqMin(list.getJSONObject("mac_requirements").getString("minimum"));
		if (list.optJSONObject("mac_requirements") != null && list.optJSONObject("mac_requirements").has("recommended"))
			data.setMacReqRec(list.getJSONObject("mac_requirements").getString("recommended"));
		if (list.optJSONObject("pc_requirements") != null && list.optJSONObject("pc_requirements").has("minimum"))
			data.setPcReqMin(list.getJSONObject("pc_requirements").getString("minimum"));
		if (list.optJSONObject("pc_requirements") != null && list.optJSONObject("pc_requirements").has("recommended"))
			data.setPcReqRec(list.getJSONObject("pc_requirements").getString("recommended"));

		if (list.has("metacritic")) {
			Metacritic metacritic = new Metacritic();
			metacritic.setMetaScore(list.getJSONObject("metacritic").getInt("score"));
			if(list.getJSONObject("metacritic").has("url"))
				metacritic.setMetaUrl(list.getJSONObject("metacritic").getString("url"));
			
			data.setMetacritic(metacritic);
		}

		Platform platform = new Platform();	
		platform.setLinux(list.getJSONObject("platforms").getBoolean("linux"));
		platform.setMac(list.getJSONObject("platforms").getBoolean("mac"));
		platform.setWindows(list.getJSONObject("platforms").getBoolean("windows"));
		
		data.setPlatform(platform);

		data.setPublishers(ParseHelper.jsonArrayToString(list.getJSONArray("publishers")));
		if(list.has("recommendations"))
			data.setRecommendations(list.getJSONObject("recommendations").getInt("total"));
		data.setRequiredAge(list.getInt("required_age"));
		if(list.has("supported_languages"))
			data.setSupportedLanguages(list.getString("supported_languages"));
		data.setType(list.getString("type"));
		data.setWebsite(list.optString("website"));
		data.setFree(list.getBoolean("is_free"));
		
		return data;
	}
	
	
	private List<Price> getPriceInfo(JSONObject list){
		List<Price> returnList = new ArrayList<Price>();
		
		if (list.has("price_overview")) {
			Price price = new Price();
			price.setId(list.getInt("steam_appid"));
			price.setCurrency(list.getJSONObject("price_overview").getString("currency"));
			price.setDiscountPercent(list.getJSONObject("price_overview").getLong("discount_percent"));
			price.setFinalPrice(list.getJSONObject("price_overview").getDouble("final") / 100);
			price.setInitialPrice(list.getJSONObject("price_overview").getDouble("initial") / 100);
			
			returnList.add(price);
		}
		
		return returnList;
	}
	
	private List<Category> getCategoryInfo(JSONObject list){
		List<Category> returnList = new ArrayList<Category>();
		
		if(list.has("categories")){
			JSONArray categoryArray = list.getJSONArray("categories");
			
			for(int i=0; i< categoryArray.length(); i++){
				Category category = new Category();
				category.setAppId(list.getInt("steam_appid"));
				category.setCategoryId(categoryArray.getJSONObject(i).getInt("id"));
				category.setDescription(categoryArray.getJSONObject(i).getString("description"));
				
				returnList.add(category);
			}			
		}
		
		return returnList;
	}
	
	private List<Genre> getGenreInfo(JSONObject list){
		List<Genre> returnList = new ArrayList<Genre>();
		
		if(list.has("genres")){
			JSONArray genreArray = list.getJSONArray("genres");
			
			for(int i=0; i< genreArray.length(); i++){
				Genre genre = new Genre();
				genre.setAppId(list.getInt("steam_appid"));
				genre.setGenreId(genreArray.getJSONObject(i).getInt("id"));
				genre.setDescription(genreArray.getJSONObject(i).getString("description"));
				
				returnList.add(genre);
			}		
		}
		
		return returnList;
	}
	
	private List<Sub> getPackageInfo(JSONObject list){
		List<Sub> returnList = new ArrayList<Sub>();
		
		if(list.has("packages")){
			JSONArray packagesArray = list.getJSONArray("packages");

			for(int i=0; i<packagesArray.length(); i++){
				Sub sub = new Sub();
				sub.setAppId(list.getInt("steam_appid"));
				sub.setPackageId(packagesArray.getInt(i));
				
				returnList.add(sub);
			}
		}
		
		return returnList;
	}

}
