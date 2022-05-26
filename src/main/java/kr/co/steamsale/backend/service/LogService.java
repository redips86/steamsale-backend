package kr.co.steamsale.backend.service;

import kr.co.steamsale.backend.dao.LogDao;
import kr.co.steamsale.backend.data.PlayerLog;
import kr.co.steamsale.backend.data.PriceLog;

public class LogService {
	public int putPriceLog(PriceLog data, String oprt) {
		return new LogDao().insertPriceLog(data, oprt);
	}

	public int putPlayerLog(PlayerLog data, String oprt) {
		return new LogDao().insertPlayerLog(data, oprt);
	}

	public int putSubPriceLog(PriceLog data, String oprt) {
		return new LogDao().insertSubPriceLog(data, oprt);
	}
}
