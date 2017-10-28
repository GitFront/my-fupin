package com.aspire.birp.modules.antiPoverty.model.poorFile.fileBusinessRest;

import com.aspire.birp.modules.antiPoverty.model.base.BaseModel;

public class FileBusinessRest  extends BaseModel{
	public String dataToJson() throws Exception{
		return getData().toString();
	}
}
