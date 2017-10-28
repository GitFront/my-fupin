package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileBasic;

import org.apache.ibatis.type.Alias;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

@Alias("CountryFileBasicData")
public class CountryFileBasicData extends BaseData{
	private String countryName;
    private String responsibleName;
    private String responsibleAvatar;
    private String responsiblePhone;
    private String responsibleDuty;
    private String countryLeaderName;
    private String countryLeaderAvatar;
    private String countryLeaderPhone;
    private String countryLeaderOrg;
    private String countryLeaderStartTime;
    private String amountTotalFamily;
    private String amountTotalPeople;
    private String amountPoorFamily;
    private String amountPoorPeople;
    private String ratioPoor;
    private String amountSuccessFamily;
    private String amountSuccessPeople;
    private String score;
    private String hasAchieved;
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getResponsibleName() {
		return responsibleName;
	}
	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}
	public String getResponsibleAvatar() {
		return responsibleAvatar;
	}
	public void setResponsibleAvatar(String responsibleAvatar) {
		this.responsibleAvatar = responsibleAvatar;
	}
	public String getResponsiblePhone() {
		return responsiblePhone;
	}
	public void setResponsiblePhone(String responsiblePhone) {
		this.responsiblePhone = responsiblePhone;
	}
	public String getResponsibleDuty() {
		return responsibleDuty;
	}
	public void setResponsibleDuty(String responsibleDuty) {
		this.responsibleDuty = responsibleDuty;
	}
	public String getCountryLeaderName() {
		return countryLeaderName;
	}
	public void setCountryLeaderName(String countryLeaderName) {
		this.countryLeaderName = countryLeaderName;
	}
	public String getCountryLeaderAvatar() {
		return countryLeaderAvatar;
	}
	public void setCountryLeaderAvatar(String countryLeaderAvatar) {
		this.countryLeaderAvatar = countryLeaderAvatar;
	}
	public String getCountryLeaderPhone() {
		return countryLeaderPhone;
	}
	public void setCountryLeaderPhone(String countryLeaderPhone) {
		this.countryLeaderPhone = countryLeaderPhone;
	}
	public String getCountryLeaderOrg() {
		return countryLeaderOrg;
	}
	public void setCountryLeaderOrg(String countryLeaderOrg) {
		this.countryLeaderOrg = countryLeaderOrg;
	}
	public String getCountryLeaderStartTime() {
		return countryLeaderStartTime;
	}
	public void setCountryLeaderStartTime(String countryLeaderStartTime) {
		this.countryLeaderStartTime = countryLeaderStartTime;
	}
	public String getAmountTotalFamily() {
		return amountTotalFamily;
	}
	public void setAmountTotalFamily(String amountTotalFamily) {
		this.amountTotalFamily = amountTotalFamily;
	}
	public String getAmountTotalPeople() {
		return amountTotalPeople;
	}
	public void setAmountTotalPeople(String amountTotalPeople) {
		this.amountTotalPeople = amountTotalPeople;
	}
	public String getAmountPoorFamily() {
		return amountPoorFamily;
	}
	public void setAmountPoorFamily(String amountPoorFamily) {
		this.amountPoorFamily = amountPoorFamily;
	}
	public String getAmountPoorPeople() {
		return amountPoorPeople;
	}
	public void setAmountPoorPeople(String amountPoorPeople) {
		this.amountPoorPeople = amountPoorPeople;
	}
	public String getRatioPoor() {
		return ratioPoor;
	}
	public void setRatioPoor(String ratioPoor) {
		this.ratioPoor = ratioPoor;
	}
	public String getAmountSuccessFamily() {
		return amountSuccessFamily;
	}
	public void setAmountSuccessFamily(String amountSuccessFamily) {
		this.amountSuccessFamily = amountSuccessFamily;
	}
	public String getamountSuccessPeople() {
		return amountSuccessPeople;
	}
	public void setamountSuccessPeople(String amountSuccessPeople) {
		this.amountSuccessPeople = amountSuccessPeople;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getHasAchieved() {
		return hasAchieved;
	}
	public void setHasAchieved(String hasAchieved) {
		this.hasAchieved = hasAchieved;
	}
	boolean[] achieved_arr = {false,true};
	@Override
	public String toString() {
		return "{\"countryName\":\"" + countryName
			+ "\",\"summary\":{"  
				+ "\"responsibleName\":\"" + responsibleName
				+ "\",\"responsibleAvatar\":\"" + Global.getConfig("image.path")+responsibleAvatar
				+ "\",\"responsiblePhone\":\"" + responsiblePhone
				+ "\",\"responsibleDuty\":\"" + responsibleDuty
				+ "\",\"countryLeaderName\":\"" + countryLeaderName
				+ "\",\"countryLeaderAvatar\":\"" +  Global.getConfig("image.path")+countryLeaderAvatar
				+ "\",\"countryLeaderPhone\":\"" + countryLeaderPhone
				+ "\",\"countryLeaderOrg\":\"" + countryLeaderOrg
				+ "\",\"countryLeaderStartTime\":\"" + countryLeaderStartTime
			+ "\"},\"stat\":{"  
				+ "\"amountTotalFamily\":\"" + amountTotalFamily
				+ "\",\"amountTotalPeople\":\"" + amountTotalPeople
				+ "\",\"amountPoorFamily\":\"" + amountPoorFamily
				+ "\",\"amountPoorPeople\":\"" + amountPoorPeople
				+ "\",\"ratioPoor\":\"" + ratioPoor
				+ "\",\"amountSuccessFamily\":\"" + amountSuccessFamily 
				+ "\",\"amountSuccessPeople\":\"" + amountSuccessPeople 
				+ "\",\"score\":\"" + score 
				+ "\",\"hasAchieved\":\""+ achieved_arr[Integer.parseInt(hasAchieved)] + "\"}  "
				+ "}  ";
	}
  
}
