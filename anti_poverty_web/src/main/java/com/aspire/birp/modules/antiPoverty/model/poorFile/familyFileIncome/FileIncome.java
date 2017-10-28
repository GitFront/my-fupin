
package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome;

import com.aspire.birp.modules.antiPoverty.model.base.BaseModel;

public class FileIncome  extends BaseModel{
	public String dataToJson() throws Exception{
		return getData().toString();
	}
}
