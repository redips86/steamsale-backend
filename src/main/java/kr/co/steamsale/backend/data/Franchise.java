package kr.co.steamsale.backend.data;

public class Franchise {
	String saleId;
	String saleName;
	String available;
	FranchiseChild franchiseChild;

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public FranchiseChild getFranchiseChild() {
		return franchiseChild;
	}

	public void setFranchiseChild(FranchiseChild franchiseChild) {
		this.franchiseChild = franchiseChild;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}
}
