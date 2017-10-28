package com.aspire.birp.modules.antiPoverty.model.dataMonitor.dataMonitorOrderBasicInfo;

public class ValuesNew {
	private Integer label;
	private Integer value;
	private Boolean disabled;
	public Integer getLabel() {
		return label;
	}
	public void setLabel(Integer label) {
		this.label = label;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Override
	public String toString() {
		return "{\"label\":\"" + label + "\",\"value\":\"" + value
				+ "\",\"disabled\":\"" + disabled + "\"}  ";
	}
	
}
