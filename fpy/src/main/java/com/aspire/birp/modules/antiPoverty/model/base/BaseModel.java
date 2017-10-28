package com.aspire.birp.modules.antiPoverty.model.base;

import java.io.Serializable;
import com.aspire.birp.modules.antiPoverty.utils.JsonFormatUtils;

/**
 * <b>Description</b>: 基础Model
 * 
 * @author chudy
 * @history:2017-05-10 Added
 * 
 */
public class BaseModel  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String msg;
	private BaseData data;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public BaseData getData() {
		return data;
	}
	public void setData(BaseData data) {
		this.data = data;
	}
	public String dataToJson() throws Exception{
		String json = JsonFormatUtils.toUnderlineJSONStringByStr(data.toString());
		return json.substring(0, json.length()-1);
	}
	@Override
	public String toString() {
		try {
			return "{\"code\":" + code + ",\"msg\":\"" + msg + "\",\"data\":" +dataToJson() + "}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
}
