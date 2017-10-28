package com.aspire.birp.modules.antiPoverty.model.dataMonitor.dataMonitorOrderBasicInfo;

public class DataMonitorOrderBasicInfoBese {
	private Object key;
	private Object label;
	private Object type;
	private Object selected_value;
	private ValuesNew valuesNew;
	
	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getLabel() {
		return label;
	}

	public void setLabel(Object label) {
		this.label = label;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public Object getSelected_value() {
		return selected_value;
	}

	public void setSelected_value(Object selected_value) {
		this.selected_value = selected_value;
	}

	public ValuesNew getValuesNew() {
		return valuesNew;
	}

	public void setValuesNew(ValuesNew valuesNew) {
		this.valuesNew = valuesNew;
	}

	@Override
	public String toString() {
		return "{\"key\":\"" + key + "\",\"label\":\"" + label
				+ "\",\"type\":\"" + type + "\",\"selected_value\":\""
				+ selected_value + "\",\"values\":\"" + valuesNew + "\"}  ";
	}
	
}
