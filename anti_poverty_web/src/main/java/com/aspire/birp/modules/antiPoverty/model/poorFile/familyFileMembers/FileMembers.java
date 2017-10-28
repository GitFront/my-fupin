
package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers;

import com.aspire.birp.modules.antiPoverty.model.base.BaseModel;

public class FileMembers  extends BaseModel{
	public String dataToJson() throws Exception{
		return getData().toString();
	}
}
