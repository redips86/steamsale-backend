package kr.co.steamsale.backend.dao;

import java.util.HashMap;

import kr.co.steamsale.backend.data.PlayerLog;
import kr.co.steamsale.backend.data.PriceLog;

import com.prismweaver.backend.dao.AbstractDao;

public class LogDao extends AbstractDao {

	public int insertPriceLog(PriceLog data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("execDate", data.getExecDate());
		params.put("execTime", data.getExecTime());
		params.put("id", data.getId());
		params.put("usd", data.getUsd());
		params.put("krw", data.getKrw());
		params.put("oprt", oprt);

		return this.getDBUtils().insert("LogMapper.insertPriceLog", params);
	}

	public int insertPlayerLog(PlayerLog data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("execDate", data.getExecDate());
		params.put("execTime", data.getExecTime());
		params.put("id", data.getId());
		params.put("playerCount", data.getPlayerCount());
		params.put("oprt", oprt);

		return this.getDBUtils().insert("LogMapper.insertPlayerLog", params);
	}

	public int insertSubPriceLog(PriceLog data, String oprt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("execDate", data.getExecDate());
		params.put("execTime", data.getExecTime());
		params.put("id", data.getId());
		params.put("usd", data.getUsd());
		params.put("krw", data.getKrw());
		params.put("oprt", oprt);

		return this.getDBUtils().insert("LogMapper.insertSubPriceLog", params);
	}

}
