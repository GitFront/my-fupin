package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileBusiness;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.BaseListMode;

@Alias("familyFileBusinessData")
public class FamilyFileBusinessData<BaseListMode> extends BaseData<BaseListMode> {
	private String amountTotal;
	private List<BaseListMode> listDoing;
	private List<BaseListMode> listDone;
	private List<BaseListMode> listToDo;
	
	
	public String getAmountTotal() {
		return amountTotal;
	}


	public void setAmountTotal(String amountTotal) {
		this.amountTotal = amountTotal;
	}


	public List<BaseListMode> getListDoing() {
		return listDoing;
	}


	public void setListDoing(List<BaseListMode> listDoing) {
		this.listDoing = listDoing;
	}


	public List<BaseListMode> getListDone() {
		return listDone;
	}


	public void setListDone(List<BaseListMode> listDone) {
		this.listDone = listDone;
	}


	public List<BaseListMode> getListToDo() {
		return listToDo;
	}


	public void setListToDo(List<BaseListMode> listToDo) {
		this.listToDo = listToDo;
	}


	@Override
	public String toString() {
		
		return "{\"listDoing\":"+"{\"amountTotal\":\"" + listDoing.size() +"\",\"list\":"+listToStr(listDoing)+"},"+
		"\"listDone\":"+"{\"amountTotal\":\"" + listDone.size() +"\",\"list\":"+listToStr(listDone)+"},"+
		"\"listToDo\":"+"{\"amountTotal\":\"" + listToDo.size() +"\",\"list\":"+listToStr(listToDo)+"}}" ;
	}
	
	
}
