package kr.co.steamsale.backend.data;

public class Price {
	int id;
	String currency;
	double initialPrice;
	double finalPrice;
	double discountPercent;
	double individualPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public double getIndividualPrice() {
		return individualPrice;
	}

	public void setIndividualPrice(double individualPrice) {
		this.individualPrice = individualPrice;
	}

}
