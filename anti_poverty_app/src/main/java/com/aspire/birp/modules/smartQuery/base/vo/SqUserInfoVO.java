
package com.aspire.birp.modules.smartQuery.base.vo;
/**  
 * @Title: 用户信息数据对象 
 * @Description: 用于封装后台查询的用户数据
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月20日 下午6:23:56
 * @version: V1.0
 */
public class SqUserInfoVO {

	private String id;
	/*用户名称*/
	private String name;
	/*用户账号信息*/
	private String userAccount;
	/*用户机构*/
	private String companyId;
	private String company;
	/*用户部门*/
	private String departmentId;
	private String departmentName;
	private String departmentPath;
	/*用户基本信息*/
	private String email;
	private String sex;
	
	
	public SqUserInfoVO() {
		super();
	}


	public SqUserInfoVO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUserAccount() {
		return userAccount;
	}


	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}


	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public String getDepartmentPath() {
		return departmentPath;
	}


	public void setDepartmentPath(String departmentPath) {
		this.departmentPath = departmentPath;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}
}


