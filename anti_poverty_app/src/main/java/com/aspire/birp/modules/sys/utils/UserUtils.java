package com.aspire.birp.modules.sys.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.aspire.bi.common.config.Global;
import com.aspire.bi.common.service.SpringContextHolder;
import com.aspire.auth.modules.security.service.SecurityService;
import com.aspire.auth.modules.sys.entity.Area;
import com.aspire.auth.modules.sys.entity.Menu;
import com.aspire.auth.modules.sys.entity.Office;
import com.aspire.auth.modules.sys.entity.Role;
import com.aspire.auth.modules.sys.entity.User;
import com.google.common.collect.Maps;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class UserUtils  {
	 //.getBean("securityServiceHandler")
	
 	private static SecurityService securityServiceHandler;
 	
	public static final String CACHE_USER = "user";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_USER_LIST = "userList";
	
	public static final String CACHE_SERVERCATEGORYINFO_LIST = "serverCategoryInfoList";
	public static final String CACHE_SERVERDIRECTORY_LIST = "serverDirectoryList";
	
	private static final Map<String, Map<String, Object>> CACHE_USER_MAP = new HashMap<String, Map<String,Object>>();
	
    private static SecurityService getSecurityService(){
    	if(securityServiceHandler==null){ 
     		securityServiceHandler = (SecurityService)SpringContextHolder.getBean("securityServiceHandler");
     		//AppContextFactory.getBean("securityServiceHandler");
    	}
    	return securityServiceHandler;
    }
	
	private static String getUserLoginName(){
 		try {
 			Subject subject = SecurityUtils.getSubject();
 	 		PrincipalCollection principals = subject.getPrincipals();
 			List list =  principals.asList();
 		    String userId = null;
 		    if(list!=null && list.size()>0){
 		    	userId = list.get(0).toString();
 			}
 		    return userId;
		} catch (Exception e) {
 		}
 		return null;
	}
	
	public static User getUser(){
		User user = (User)getCache(CACHE_USER);
		if (user == null){
			try{
 			    String loginName = getUserLoginName();
 				if (loginName!=null){
					user =   getSecurityService().findUserInfo(loginName);
 					putCache(CACHE_USER, user);
				}
			}catch (UnavailableSecurityManagerException e) {
				
			}catch (InvalidSessionException e){
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		if (user == null){
			user = new User();
			try{
				SecurityUtils.getSubject().logout();
			}catch (UnavailableSecurityManagerException e) {
				
			}catch (InvalidSessionException e){
				
			}
		}
		return user;
	}

	public static  void logout() {
		removeCache(CACHE_USER);
		SecurityUtils.getSubject().logout();
 	}
	
	public static User getUser(boolean isRefresh){
		if (isRefresh){
			removeCache(CACHE_USER);
		}
		return getUser();
	}

	public static List<Role> getRoleList(){
		 
		return null;
	}

	public static List<Menu> getMenuList() throws RemoteException {
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			User user = getUser();
			String modulesName =  Global.getConfig("modulesName");
		    System.out.println("modulesName="+modulesName);
			menuList = getSecurityService().findMenu(user.getLoginName(),modulesName);
 			putCache(CACHE_MENU_LIST, menuList);
		}
		return  menuList;
	}
	
	public static List<Menu> getMenuList(String parentId,Integer level) throws RemoteException {
		List<Menu> menuList = getMenuList();
 		if(parentId==null || parentId.trim().equals("") || level == 0){
			return menuList;
		}else {
			List<Menu> tempList =  eachMenuList(menuList,level-1);
			for(int i = 0 ; i < tempList.size() ;i++){
				if(tempList.get(i).getId().equals(parentId)){
					menuList = tempList.get(i).getChildList();
				}
			}
		}
 		return menuList;
 	}
	
	/**
	 * 
	 *  --1  12
	 *    ---21  22
	 *     ----31  32 33
	 *       ----41 42 43 44 45
	 * @param menu
	 * @param level
	 * @return
	 */
	private static List<Menu> eachMenuList(List<Menu> menuList,Integer level){
		List<Menu> tempList = new ArrayList<Menu>();
 		level--;
 		if(level>0){
 			for(int i = 0 ; i < menuList.size();i++){
 				 if(menuList.get(i).getChildList()!=null){
 					List<Menu> tempList2 = eachMenuList(menuList.get(i).getChildList(),level);
 					if(tempList2!=null && tempList2.size() > 0){
 						tempList.addAll(tempList2);
 					}
  				 }
 			}
 		}else {
 			tempList.addAll(menuList);
		}
   		return tempList;
	}


	public static List<Menu> getMenuListWithoutCache() {
 		return null;
	}

	/**
	 * 菜单管理使用到
	 * @param system
	 * @return
	 */
	public static List<Menu> getMenuListWithoutCache(String system){
		 return null;
	}
	
	public static List<Area> getAreaList(){
		 return null;
	}
	
	public static List<Office> getOfficeList(){
		 return null;
	}
	
	
	public static User getUserByLoginName(String loginName){
		try {
			return  getSecurityService().findUserInfo(loginName);
		} catch (RemoteException e) {
 			e.printStackTrace();
		}
		return null;
	}

	public static User getUserById(String id){
	  return null;
	}
	 
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
		Object obj = getCacheMap().get(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
		getCacheMap().put(key, value);
	}

	public static void removeCache(String key) {
		getCacheMap().remove(key);
	}
	
	public static Map<String, Object> getCacheMap(){
		Map<String, Object> map = Maps.newHashMap();
		try{
			String loginName = getUserLoginName();
			Map<String, Object> userMap = CACHE_USER_MAP.get(loginName);
			if(userMap==null){
				CACHE_USER_MAP.put(loginName, map);
			}
			userMap = CACHE_USER_MAP.get(loginName);
 			return userMap;
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return map;
	}
	
	
	public static  void reomveCacheMap(String loginName){
		CACHE_USER_MAP.remove(loginName);
	}
	
	public static User getUser(String loginName){
		User user = null;
			if (loginName!=null){
			try {
				user = getSecurityService().findUserInfo(loginName);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	return user;
}
	
}
