package kr.co.steamsale.backend.data;

public class App {
	int appId;
	String name;
	String type;
	int requiredAge;
	String aboutTheGame;
	String supportedLanguages;
	String headerImage;
	String website;
	String pcReqMin;
	String pcReqRec;
	String macReqMin;
	String macReqRec;
	String linuxReqMin;
	String linuxReqRec;
	String developers;
	String publishers;
	Platform platform;
	Metacritic metacritic;
	int recommendations;
	ReleaseDate releaseDate;
	boolean isFree;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRequiredAge() {
		return requiredAge;
	}

	public void setRequiredAge(int requiredAge) {
		this.requiredAge = requiredAge;
	}

	public String getAboutTheGame() {
		return aboutTheGame;
	}

	public void setAboutTheGame(String aboutTheGame) {
		this.aboutTheGame = aboutTheGame;
	}

	public String getSupportedLanguages() {
		return supportedLanguages;
	}

	public void setSupportedLanguages(String supportedLanguages) {
		this.supportedLanguages = supportedLanguages;
	}

	public String getHeaderImage() {
		return headerImage;
	}

	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPcReqMin() {
		return pcReqMin;
	}

	public void setPcReqMin(String pcReqMin) {
		this.pcReqMin = pcReqMin;
	}

	public String getPcReqRec() {
		return pcReqRec;
	}

	public void setPcReqRec(String pcReqRec) {
		this.pcReqRec = pcReqRec;
	}

	public String getMacReqMin() {
		return macReqMin;
	}

	public void setMacReqMin(String macReqMin) {
		this.macReqMin = macReqMin;
	}

	public String getMacReqRec() {
		return macReqRec;
	}

	public void setMacReqRec(String macReqRec) {
		this.macReqRec = macReqRec;
	}

	public String getLinuxReqMin() {
		return linuxReqMin;
	}

	public void setLinuxReqMin(String linuxReqMin) {
		this.linuxReqMin = linuxReqMin;
	}

	public String getLinuxReqRec() {
		return linuxReqRec;
	}

	public void setLinuxReqRec(String linuxReqRec) {
		this.linuxReqRec = linuxReqRec;
	}

	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}

	public String getPublishers() {
		return publishers;
	}

	public void setPublishers(String publishers) {
		this.publishers = publishers;
	}

	public int getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(int recommendations) {
		this.recommendations = recommendations;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Metacritic getMetacritic() {
		return metacritic;
	}

	public void setMetacritic(Metacritic metacritic) {
		this.metacritic = metacritic;
	}

	public void setReleaseDate(ReleaseDate releaseDate) {
		this.releaseDate = releaseDate;
	}

}
