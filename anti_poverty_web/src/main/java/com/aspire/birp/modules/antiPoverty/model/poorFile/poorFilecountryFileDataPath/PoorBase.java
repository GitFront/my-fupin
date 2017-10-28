package com.aspire.birp.modules.antiPoverty.model.poorFile.poorFilecountryFileDataPath;

import org.apache.ibatis.type.Alias;

@Alias("PoorBase")
public class PoorBase {
	private String name;
	private String old;
	private String new1;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOld() {
		return old;
	}
	public void setOld(String old) {
		this.old = old;
	}
	public String getNew1() {
		return new1;
	}
	public void setNew1(String new1) {
		this.new1 = new1;
	}
	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\",\"old\":\"" + old + "\",\"new\":\""
				+ new1 + "\"}  ";
	}
	
}
