package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileCondition;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

@Alias("FamilyFileConditionData")
public class FamilyFileConditionData extends BaseData{
	private String total;
	private String available1;
	private String total1;
	private String return1;
	private String grassTotal;
	private String waterTotal;
	private String productionElectricity;
	private String livingElectricity;
	private String waterSafety;
	private String houseArea;
	private String distanceMainRoad;
	private String roadType;
	private String fuelType;
	private String toilet;
	private String joinedFsc;
	private String fruit;
	public String getFruit() {
		return fruit;
	}
	public void setFruit(String fruit) {
		this.fruit = fruit;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getavailable1() {
		return available1;
	}
	public void setavailable1(String available1) {
		this.available1 = available1;
	}
	public String getTotal1() {
		return total1;
	}
	public void setTotal1(String total1) {
		this.total1 = total1;
	}
	public String getReturn1() {
		return return1;
	}
	public void setReturn1(String return1) {
		this.return1 = return1;
	}
	public String getGrassTotal() {
		return grassTotal;
	}
	public void setGrassTotal(String grassTotal) {
		this.grassTotal = grassTotal;
	}
	public String getWaterTotal() {
		return waterTotal;
	}
	public void setWaterTotal(String waterTotal) {
		this.waterTotal = waterTotal;
	}
	public String getProductionElectricity() {
		return productionElectricity;
	}
	public void setProductionElectricity(String productionElectricity) {
		this.productionElectricity = productionElectricity;
	}
	public String getLivingElectricity() {
		return livingElectricity;
	}
	public void setLivingElectricity(String livingElectricity) {
		this.livingElectricity = livingElectricity;
	}
	public String getWaterSafety() {
		return waterSafety;
	}
	public void setWaterSafety(String waterSafety) {
		this.waterSafety = waterSafety;
	}
	public String getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}
	public String getDistanceMainRoad() {
		return distanceMainRoad;
	}
	public void setDistanceMainRoad(String distanceMainRoad) {
		this.distanceMainRoad = distanceMainRoad;
	}
	public String getRoadType() {
		return roadType;
	}
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getToilet() {
		return toilet;
	}
	public void setToilet(String toilet) {
		this.toilet = toilet;
	}
	public String getJoinedFsc() {
		return joinedFsc;
	}
	public void setJoinedFsc(String joinedFsc) {
		this.joinedFsc = joinedFsc;
	}
	@Override
	public String toString() {
		return "{" 
			+ "\"production\":{"
			+ "\"cultivated_land\":{"
				+"\"available\":\"" + available1 
				+ "\",\"total\":\""+ total 
			+ "\"},\"forest_land\":{"
				+ "\"total\":\"" + total1 
				+ "\",\"return\":\"" + return1
				+ "\",\"fruit\":\"" + fruit
			+ "\"},"
				+ "\"grassTotal\":\"" + grassTotal 
				+ "\",\"waterTotal\":\""+ waterTotal 
			+ "\"},\"living\":{"  
				+ "\"productionElectricity\":\""+ productionElectricity 
				+ "\",\"livingElectricity\":\""+ livingElectricity 
				+ "\",\"waterSafety\":\"" + waterSafety
				+ "\",\"houseArea\":\"" + houseArea
				+ "\",\"distanceMainRoad\":\"" + distanceMainRoad
				+ "\",\"roadType\":\"" + roadType 
				+ "\",\"fuelType\":\""+ fuelType 
				+ "\",\"toilet\":\"" + toilet
				+ "\",\"joinedFsc\":\"" + joinedFsc + "\"}  "
				+ "}  ";
	}
	

}
