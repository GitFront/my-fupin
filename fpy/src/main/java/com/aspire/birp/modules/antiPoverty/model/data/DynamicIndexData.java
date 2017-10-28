package com.aspire.birp.modules.antiPoverty.model.data;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;



@Alias("IndexData")
public class DynamicIndexData extends BaseData{
    private String areaLevel;
 
    private Long successFamilyAmount;

    private double successFamilyRate;

    private Long poorPeopleAmount;
    
    private Long poorFamilyAmount; 

    private Long successPeopleAmount;

    private double successPeopleRate;

    private int score;

    private int amountOrg;

    private int amountResponsible;

    private int amountLeader;

    private int amountInterview;

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	public double getSuccessFamilyRate() {
		return successFamilyRate;
	}

	public void setSuccessFamilyRate(double successFamilyRate) {
		this.successFamilyRate = successFamilyRate;
	}


	public double getSuccessPeopleRate() {
		return successPeopleRate;
	}

	public void setSuccessPeopleRate(double successPeopleRate) {
		this.successPeopleRate = successPeopleRate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Long getSuccessFamilyAmount() {
		return successFamilyAmount;
	}

	public void setSuccessFamilyAmount(Long successFamilyAmount) {
		this.successFamilyAmount = successFamilyAmount;
	}

	public Long getPoorPeopleAmount() {
		return poorPeopleAmount;
	}

	public void setPoorPeopleAmount(Long poorPeopleAmount) {
		this.poorPeopleAmount = poorPeopleAmount;
	}

	public Long getPoorFamilyAmount() {
		return poorFamilyAmount;
	}

	public void setPoorFamilyAmount(Long poorFamilyAmount) {
		this.poorFamilyAmount = poorFamilyAmount;
	}

	public Long getSuccessPeopleAmount() {
		return successPeopleAmount;
	}

	public void setSuccessPeopleAmount(Long successPeopleAmount) {
		this.successPeopleAmount = successPeopleAmount;
	}

	public int getAmountOrg() {
		return amountOrg;
	}

	public void setAmountOrg(int amountOrg) {
		this.amountOrg = amountOrg;
	}

	public int getAmountResponsible() {
		return amountResponsible;
	}

	public void setAmountResponsible(int amountResponsible) {
		this.amountResponsible = amountResponsible;
	}

	public int getAmountLeader() {
		return amountLeader;
	}

	public void setAmountLeader(int amountLeader) {
		this.amountLeader = amountLeader;
	}

	public int getAmountInterview() {
		return amountInterview;
	}

	public void setAmountInterview(int amountInterview) {
		this.amountInterview = amountInterview;
	}

	@Override
	public String toString() {
		return "{\"areaLevel\":\"" + areaLevel + "\",\"topIndexes\":{"  + "\"successFamilyAmount\":" + successFamilyAmount
				+ ",\"successFamilyRate\":" + successFamilyRate + ",\"poorPeopleAmount\":" + poorPeopleAmount
				+ ",\"poorFamilyAmount\":" + poorFamilyAmount + ",\"successPeopleAmount\":"
				+ successPeopleAmount + ",\"successPeopleRate\":" + successPeopleRate + (score==0 ? "" : ",\"score\":"+score)
				+ "},\"stat\":{"+"\"amountOrg\":" + amountOrg + ",\"amountResponsible\":" + amountResponsible
				+ ",\"amountLeader\":" + amountLeader + ",\"amountInterview\":" + amountInterview + "}}";
	}

	
    
    
}
