package kr.co.steamsale.backend.data;

public class PriceLog {
	String execDate;
	String execTime;
	int id;
	double usd;
	double krw;

	public String getExecDate() {
		return execDate;
	}

	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getUsd() {
		return usd;
	}

	public void setUsd(double usd) {
		this.usd = usd;
	}

	public double getKrw() {
		return krw;
	}

	public void setKrw(double krw) {
		this.krw = krw;
	}

}
