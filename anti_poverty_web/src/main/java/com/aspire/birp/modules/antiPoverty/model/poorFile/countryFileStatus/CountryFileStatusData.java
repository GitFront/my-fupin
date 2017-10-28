package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileStatus;

import java.util.List;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

public class CountryFileStatusData<CountryFileStatusBase>  extends BaseData<CountryFileStatusBase>  {
	private String countryName;
	private String countryAttribute;
	private String topography;
	private String devTarget;
	private String groups;
	private String total;
	private List<CountryFileStatusBase> bars;
	private String total1;
	private List<CountryFileStatusBase> bars2;
	private String total2;
	private List<CountryFileStatusBase> bars43;
	private String total4;
	private String available;
	private String total3;
	private String available2;
	private String grassTotal;
	private String waterTotal;
	@Override
	public String toString() {
		return "{" 
			+ "\"summary\":{"
				+"\"countryName\":\"" + countryName
				+ "\",\"countryAttribute\":\"" + countryAttribute
				+ "\",\"topography\":\"" + topography 
				+ "\",\"devTarget\":\""+ devTarget 
				+ "\",\"groups\":\"" + groups 
			+ "\"},\"population\":{" 
			+ "\"family\":{" 
				+ "\",\"total\":\""+ total 
				+ "\",\"bars\":\"" + bars 
			+ "\"},\"people\":{" 
				+ "\",\"total1\":\"" + total1
				+ "\",\"bars2\":\"" + bars2 
			+ "\"},\"labor\":{" 
				+ "\",\"total2\":\"" + total2
				+ "\",\"bars43\":\"" + bars43 
			+ "\"},\"production\":{"
			+ "\"cultivated_land\":{"
				+ "\",\"total4\":\"" + total4
				+ "\",\"available\":\"" + available
			+ "\"},\"forest_land\":{"
				+ "\",\"total3\":\""+ total3 
				+ "\",\"available2\":\"" + available2
				+ "\"},\"grassTotal\":\"" + grassTotal 
				+ "\",\"waterTotal\":\""+ waterTotal + "\"}  ";
	}
	
	
}
