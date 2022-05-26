package kr.co.steamsale.backend.dao;

import java.util.HashMap;
import java.util.List;

import kr.co.steamsale.backend.data.App;
import kr.co.steamsale.backend.data.AppDetailWlist;
import kr.co.steamsale.backend.data.AppWlist;
import kr.co.steamsale.backend.data.Category;
import kr.co.steamsale.backend.data.Franchise;
import kr.co.steamsale.backend.data.Genre;
import kr.co.steamsale.backend.data.Price;
import kr.co.steamsale.backend.data.Review;
import kr.co.steamsale.backend.data.Sale;
import kr.co.steamsale.backend.data.SaleWlist;
import kr.co.steamsale.backend.data.Sub;

import com.prismweaver.backend.dao.AbstractDao;

public class CollectDao extends AbstractDao {

	public int upsertApp(App data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", data.getAppId());
		params.put("name", data.getName());
		params.put("oprt", oprt);

		return this.getDBUtils().insert("CollectMapper.upsertApp", params);
	}

	public List<App> selectAppList() {
		return this.getDBUtils().selectList("CollectMapper.selectAppList");
	}
	
	public List<App> selectAppList(int appId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		
		return this.getDBUtils().selectList("CollectMapper.selectAppList", params);
	}
	

	public List<App> selectAppList(String[] type) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		
		return this.getDBUtils().selectList("CollectMapper.selectAppList", params);
	}
	
	public App selectApp(int appId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		
		return this.getDBUtils().selectOne("CollectMapper.selectApp", params);
	}

	public int updateAppInfo(App data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("oprt", oprt);
		
		return this.getDBUtils().update("CollectMapper.updateAppInfo", params);
	}

	public int upsertPriceInfo(Price data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", data.getId());
		params.put("currency", data.getCurrency());
		params.put("initialPrice", data.getInitialPrice());
		params.put("finalPrice", data.getFinalPrice());
		params.put("discountPercent", data.getDiscountPercent());
		params.put("oprt", oprt);
		
		return this.getDBUtils().update("CollectMapper.upsertPriceInfo", params);
	}

	public List<Price> selectPriceList() {
		return this.getDBUtils().selectList("CollectMapper.selectPriceList");
	}

	public int selectCategory(int categoryId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", categoryId);
		
		return this.getDBUtils().selectOne("CollectMapper.selectCategory", params);
	}

	public int insertCategory(Category category, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", category.getCategoryId());
		params.put("description", category.getDescription());
		params.put("oprt", oprt);
		
		return this.getDBUtils().insert("CollectMapper.insertCategory", params);
	}

	public void deleteAppCategory(int appId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		
		this.getDBUtils().delete("CollectMapper.deleteAppCategory", params);
	}

	public int insertAppCategory(Category category, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", category.getAppId());
		params.put("categoryId", category.getCategoryId());
		params.put("oprt", oprt);
		
		return getDBUtils().insert("CollectMapper.insertAppCategory", params);
	}

	public int selectGenre(int genreId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("genreId", genreId);
		
		return this.getDBUtils().selectOne("CollectMapper.selectGenre", params);

	}

	public int insertGenre(Genre genre, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("genreId",  genre.getGenreId());
		params.put("description",  genre.getDescription());
		params.put("oprt", oprt);
		
		return this.getDBUtils().insert("CollectMapper.insertGenre", params);
	}

		HashMap<String, Object> params = new HashMap<String, Object>();
		public void deleteAppGenre(int appId) {
		params.put("appId", appId);
		
		this.getDBUtils().delete("CollectMapper.deleteAppGenre", params);
	}

	public int insertAppGenre(Genre genre, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", genre.getAppId());
		params.put("genreId", genre.getGenreId());
		params.put("oprt", oprt);
		
		return getDBUtils().insert("CollectMapper.insertAppGenre", params);
	}

	public List<AppWlist> selectAppWlist(int selectCount) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("selectCount", selectCount);
		
		List<AppWlist> data = this.getDBUtils().selectList("CollectMapper.selectAppWlist", params); 
		return  data;
	}

	public int insertAppWlist(App data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", data.getAppId());
		params.put("status", "W");
		params.put("oprt", oprt);
		
		return getDBUtils().insert("CollectMapper.insertAppWlist", params);
	}

	public int updateAppWlistStatus(AppWlist data, String status, String preStatus, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();				
		
		params.put("syncId", data.getSyncId());
		params.put("status", status);
		params.put("preStatus", preStatus);
		params.put("oprt", oprt);
		
		return this.getDBUtils().update("CollectMapper.updateAppWlistStatus", params);
	}

/*	public int insertAppSaleList(AppSale data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("execDate", data.getExecDate());
		params.put("execTime", data.getExecTime());
		params.put("packageId", data.getPackageId());
		params.put("appId", data.getAppId());
		params.put("oprt", oprt);
		
		return getDBUtils().insert("CollectMapper.insertAppSaleList", params);
	}*/

	public int updateAppReviewCount(Review data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", data.getId());
		params.put("positive", data.getPositive());
		params.put("negative", data.getNegative());
		params.put("oprt", oprt);
		
		return getDBUtils().insert("CollectMapper.updateAppReviewCount", params);
	}

	public List<AppDetailWlist> selectAppDetailWlist(int selectCount) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("selectCount", selectCount);
		
		List<AppDetailWlist> data = this.getDBUtils().selectList("CollectMapper.selectAppDetailWlist", params); 
		return  data;
	}

	public void deleteAppPackageInfo(int appId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		
		this.getDBUtils().delete("CollectMapper.deleteAppPackageInfo", params);
	}

	public int insertAppPackageInfo(Sub sub, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appId", sub.getAppId());
		params.put("packageId", sub.getPackageId());
		params.put("oprt", oprt);
		
		return getDBUtils().insert("CollectMapper.insertAppPackageInfo", params);
	}

	public List<Sub> selectPackageList() {
		return this.getDBUtils().selectList("CollectMapper.selectPackageList");
	}

	public List<Sub> selectPackageList(int packageId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("packageId", packageId);
		
		return this.getDBUtils().selectList("CollectMapper.selectPackageList", params);
	}

	public int upsertPackageInfo(Sub data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		params.put("oprt", oprt);

		return this.getDBUtils().insert("CollectMapper.upsertPackageInfo", params);
	}

	public List<SaleWlist> selectSaleWlist() {
		return this.getDBUtils().selectList("CollectMapper.selectSaleWlist"); 
	}
	
	public List<SaleWlist> selectSaleWlist(int id) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		return this.getDBUtils().selectList("CollectMapper.selectSaleWlist", params); 
	}


	public int upsertSaleInfo(Sale data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("sale", data);
		params.put("oprt", oprt);

		return this.getDBUtils().insert("CollectMapper.upsertSaleInfo", params);
	}
	
	public int deleteSaleInfo(String type, int id) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("id", id);
		
		return this.getDBUtils().delete("CollectMapper.deleteSaleInfo", params);
	}

	public int upsertFranchiseInfo(Franchise data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("franchise", data);
		params.put("oprt", oprt);

		return this.getDBUtils().insert("CollectMapper.upsertFranchiseInfo", params);
	}

	public List<Franchise> selectFranchiseWlist() {
		return this.getDBUtils().selectList("CollectMapper.selectFranchiseWlist");
	}

	public void deleteFranchiseDetailInfo(Franchise data) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("saleId", data.getSaleId());
		
		this.getDBUtils().delete("CollectMapper.deleteFranchiseDetailInfo", params);
	}
	
	public int insertFranchiseInfo(Franchise data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("franchise", data);
		params.put("oprt", oprt);

		return this.getDBUtils().insert("CollectMapper.insertFranchiseInfo", params);
	}

	public int insertFranchiseDetailInfo(Franchise data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("franchise", data);
		params.put("oprt", oprt);

		return this.getDBUtils().insert("CollectMapper.insertFranchiseDetailInfo", params);
	}

	public List<Price> selectSubPriceList() {
		return this.getDBUtils().selectList("CollectMapper.selectSubPriceList");
	}

	public int updateAppDescription(int id, String description) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("description", description);

		return this.getDBUtils().insert("CollectMapper.updateAppDescription", params);
	}





}
