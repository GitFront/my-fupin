package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileDevlopmentStatus;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
@Alias("CountryFileDevlopmentStatusData")
public class CountryFileDevlopmentStatusData extends BaseData{
	private String average;
	private String collective;
	private String ruralMedical;
	private String pensionInsurance;
	private String medicalHelp;
	private String amount;
	private String distanceAdminToTown;
	private String busAvailable;
	private String amountNatrualToAdmin;
	private String distanceNatrualToAdmin;
	private String notEnabledLivingCountry;
	private String notEnabledProductionCountry;
	private String notEnabledLivingFamily;
	private String enabledCountry;
	private String unsafe;
	private String difficult;
	private String amountFsc;
	private String amountFamilyFsc;
	private String travelFamily;
	private String travelPeople;
	private String ruralInnFamily;
	private String ruralInnAverage;
	private String library;
	private String tv;
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public String getCollective() {
		return collective;
	}
	public void setCollective(String collective) {
		this.collective = collective;
	}
	public String getRuralMedical() {
		return ruralMedical;
	}
	public void setRuralMedical(String ruralMedical) {
		this.ruralMedical = ruralMedical;
	}
	public String getPensionInsurance() {
		return pensionInsurance;
	}
	public void setPensionInsurance(String pensionInsurance) {
		this.pensionInsurance = pensionInsurance;
	}
	public String getMedicalHelp() {
		return medicalHelp;
	}
	public void setMedicalHelp(String medicalHelp) {
		this.medicalHelp = medicalHelp;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDistanceAdminToTown() {
		return distanceAdminToTown;
	}
	public void setDistanceAdminToTown(String distanceAdminToTown) {
		this.distanceAdminToTown = distanceAdminToTown;
	}
	public String getBusAvailable() {
		return busAvailable;
	}
	public void setBusAvailable(String busAvailable) {
		this.busAvailable = busAvailable;
	}
	public String getAmountNatrualToAdmin() {
		return amountNatrualToAdmin;
	}
	public void setAmountNatrualToAdmin(String amountNatrualToAdmin) {
		this.amountNatrualToAdmin = amountNatrualToAdmin;
	}
	public String getDistanceNatrualToAdmin() {
		return distanceNatrualToAdmin;
	}
	public void setDistanceNatrualToAdmin(String distanceNatrualToAdmin) {
		this.distanceNatrualToAdmin = distanceNatrualToAdmin;
	}
	public String getNotEnabledLivingCountry() {
		return notEnabledLivingCountry;
	}
	public void setNotEnabledLivingCountry(String notEnabledLivingCountry) {
		this.notEnabledLivingCountry = notEnabledLivingCountry;
	}
	public String getNotEnabledProductionCountry() {
		return notEnabledProductionCountry;
	}
	public void setNotEnabledProductionCountry(String notEnabledProductionCountry) {
		this.notEnabledProductionCountry = notEnabledProductionCountry;
	}
	public String getNotEnabledLivingFamily() {
		return notEnabledLivingFamily;
	}
	public void setNotEnabledLivingFamily(String notEnabledLivingFamily) {
		this.notEnabledLivingFamily = notEnabledLivingFamily;
	}
	public String getEnabledCountry() {
		return enabledCountry;
	}
	public void setEnabledCountry(String enabledCountry) {
		this.enabledCountry = enabledCountry;
	}
	public String getUnsafe() {
		return unsafe;
	}
	public void setUnsafe(String unsafe) {
		this.unsafe = unsafe;
	}
	public String getDifficult() {
		return difficult;
	}
	public void setDifficult(String difficult) {
		this.difficult = difficult;
	}
	public String getAmountFsc() {
		return amountFsc;
	}
	public void setAmountFsc(String amountFsc) {
		this.amountFsc = amountFsc;
	}
	public String getAmountFamilyFsc() {
		return amountFamilyFsc;
	}
	public void setAmountFamilyFsc(String amountFamilyFsc) {
		this.amountFamilyFsc = amountFamilyFsc;
	}
	public String getTravelFamily() {
		return travelFamily;
	}
	public void setTravelFamily(String travelFamily) {
		this.travelFamily = travelFamily;
	}
	public String getTravelPeople() {
		return travelPeople;
	}
	public void setTravelPeople(String travelPeople) {
		this.travelPeople = travelPeople;
	}
	public String getRuralInnFamily() {
		return ruralInnFamily;
	}
	public void setRuralInnFamily(String ruralInnFamily) {
		this.ruralInnFamily = ruralInnFamily;
	}
	public String getRuralInnAverage() {
		return ruralInnAverage;
	}
	public void setRuralInnAverage(String ruralInnAverage) {
		this.ruralInnAverage = ruralInnAverage;
	}
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	public String getTv() {
		return tv;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	@Override
	public String toString() {
		return "{" 
			+ "\"income\":{"
				+"\"average\":\"" + average +"元"
				+ "\"collective\":\""+ collective+"万元" 
				+ "\",\"ruralMedical\":\"" + ruralMedical
			+ "\"},\"socialSecurity\":{" 
				+ "\"pensionInsurance\":\"" + pensionInsurance
				+ "\",\"medicalHelp\":\"" + medicalHelp 
			+ "\"},\"dangerHouse\":{"
				+ "\"amount\":\""+ amount 
			+ "\"},\"countryRoad\":{"
				+ "\"distanceAdminToTown\":\""+ distanceAdminToTown 
				+ "\",\"busAvailable\":\"" + busAvailable
				+ "\",\"amountNatrualToAdmin\":\"" + amountNatrualToAdmin
				+ "\",\"distanceNatrualToAdmin\":\"" + distanceNatrualToAdmin
			+ "\"},\"electricity\":{"
				+ "\"notEnabledLivingCountry\":\"" + notEnabledLivingCountry
				+ "\",\"notEnabledProductionCountry\":\""+ notEnabledProductionCountry
				+ "\",\"notEnabledLivingFamily\":\"" + notEnabledLivingFamily
				+ "\",\"enabledCountry\":\"" + enabledCountry
			+ "\"},\"waterSafety\":{"
				+ "\"unsafe\":\"" + unsafe 
				+ "\",\"difficult\":\""+ difficult
			+ "\"},\"industry\":{"
				+ "\"amountFsc\":\"" + amountFsc
				+ "\",\"amountFamilyFsc\":\"" + amountFamilyFsc
			+ "\"},\"travel\":{"
				+ "\"travelFamily\":\"" + travelFamily
				+ "\",\"travelPeople\":\"" + travelPeople
				+ "\",\"ruralInnFamily\":\"" + ruralInnFamily
				+ "\",\"ruralInnAverage\":\"" + ruralInnAverage
			+ "\"},\"culture\":{"
				+ "\"library\":\"" + library 
				+ "\",\"tv\":\"" + tv 
				+ "\"}  "
				+ "}  ";
	}
	
}
