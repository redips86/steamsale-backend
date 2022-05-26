package kr.co.steamsale.backend.service;

import java.util.List;

import kr.co.steamsale.backend.dao.CollectDao;
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

public class CollectService {
	public int setApp(App data, String oprt) {
		return new CollectDao().upsertApp(data, oprt);
	}
	
	public List<App> getAppList(){
		return new CollectDao().selectAppList();
	}
	
	public List<App> getAppList(int appId){
		return new CollectDao().selectAppList(appId);
	}
	
	public List<App> getAppList(String[] type) {
		return new CollectDao().selectAppList(type);
	}
	
	public App getApp(int appId){
		return new CollectDao().selectApp(appId);
	}
	
	public int setAppInfo(App data, String oprt){
		return new CollectDao().updateAppInfo(data, oprt);
	}
	
	public int setPriceInfo(Price data, String oprt){
		return new CollectDao().upsertPriceInfo(data, oprt);
	}
	
	public List<Price> getPriceList(){
		return new CollectDao().selectPriceList();
	}

	public int setCategoryInfo(Category category, String oprt) {
		CollectDao dao = new CollectDao();
		
		int isExist =  dao.selectCategory(category.getCategoryId());
		if(isExist == 0){
			return dao.insertCategory(category, oprt);
		}
		
		return 0;
	}

	public void removeAppCategoryInfo(int appId){
		new CollectDao().deleteAppCategory(appId);
	}
	
	public int putAppCategoryInfo(Category category, String oprt){
		return new CollectDao().insertAppCategory(category, oprt);
	}
	
	public int setGenreInfo(Genre genre, String oprt) {
		CollectDao dao = new CollectDao();
		
		int isExist =  dao.selectGenre(genre.getGenreId());
		if(isExist == 0){
			return dao.insertGenre(genre, oprt);
		}
	
		return 0;
	}
	
	public void removeAppGenreInfo(int appId){
		new CollectDao().deleteAppGenre(appId);
	}
	
	public int putAppGenreInfo(Genre genre, String oprt){
		return new CollectDao().insertAppGenre(genre, oprt);
	}

	public List<AppWlist> getAppWlist(int selectCount) {
		return new CollectDao().selectAppWlist(selectCount);
	}

	public int putAppWlist(App data, String oprt) {
		return new CollectDao().insertAppWlist(data, oprt); 
	}

	public int setAppWlistStatus(AppWlist data, String status, String preStatus, String oprt) {
		return new CollectDao().updateAppWlistStatus(data, status, preStatus, oprt);
	}

/*	public int putAppSaleList(AppSale data, String oprt) {
		return new CollectDao().insertAppSaleList(data, oprt); 
	}*/

	public int setAppReviewCount(Review data, String oprt) {
		return new CollectDao().updateAppReviewCount(data, oprt); 
	}

	public List<AppDetailWlist> getAppDetailWlist(int selectCount) {
		return new CollectDao().selectAppDetailWlist(selectCount);
	}

	public void removeAppPackageInfo(int appId) {
		new CollectDao().deleteAppPackageInfo(appId);
	}

	public int putAppPackageInfo(Sub sub, String oprt) {
		return new CollectDao().insertAppPackageInfo(sub, oprt);
	}

	public List<Sub> getPackageList() {
		return new CollectDao().selectPackageList();
	}
	
	public List<Sub> getPackageList(int packageId) {
		return new CollectDao().selectPackageList(packageId);
	}

	public int setPackageInfo(Sub data, String oprt) {
		return new CollectDao().upsertPackageInfo(data, oprt);
	}

	public List<SaleWlist> getSaleWlist() {
		return new CollectDao().selectSaleWlist();
	}
	
	public List<SaleWlist> getSaleWlist(int id) {
		return new CollectDao().selectSaleWlist(id);
	}

	public int setSaleInfo(Sale data, String oprt) {
		return new CollectDao().upsertSaleInfo(data, oprt);
	}
	
	public int removeSaleInfo(String type, int id) {
		return new CollectDao().deleteSaleInfo(type, id);
	}

	public int setFranchiseInfo(Franchise data, String oprt) {
		return new CollectDao().upsertFranchiseInfo(data, oprt);
	}

	public List<Franchise> getFranchiseWlist() {
		return new CollectDao().selectFranchiseWlist();
	}

	public void removeFranchiseDetailInfo(Franchise data) {
		new CollectDao().deleteFranchiseDetailInfo(data);
	}
	

	public int putFranchiseInfo(Franchise data, String oprt) {
		return new CollectDao().insertFranchiseInfo(data, oprt);
	}

	public int putFranchiseDetailInfo(Franchise data, String oprt) {
		return new CollectDao().insertFranchiseDetailInfo(data, oprt);
	}

	public List<Price> getSubPriceList() {
		return new CollectDao().selectSubPriceList();
	}

	public int setAppDescription(int id, String description) {
		return new CollectDao().updateAppDescription(id, description);
	}

	

	
}
