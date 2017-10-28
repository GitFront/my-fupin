
package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNews;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.common.utils.ClobToStringUtls;
import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

@Alias("News")
public class News extends BaseData{

    private Integer id;
    private String time;
    private String descs;

	private Object pics;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescs() {
        return descs;
    }

    public void setDesc(String descs) {
        this.descs = descs;
    }

	public Object getPics() {
		return pics;
	}

	public void setPics(Object pics) {
		this.pics = pics;
	}
	
	private List<String> getpicsList(String pics){
		List<String> picsList = new ArrayList<String>();
        String[] picsArr = pics.split(";");
		for(int j=0;j<picsArr.length;j++) {
			picsList.add(Global.getConfig("image.path")+picsArr[j]);
		}
		return picsList;
	}

	  @Override
		public String toString() {
			// TODO Auto-generated method stub
			return "{\"desc\":\"" + descs + "\",\"pics\":\"" + pics==null ? "" : getpicsList(ClobToStringUtls.ClobToString((Clob)getPics())) + "\"} ";
		}
}
