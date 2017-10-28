package com.aspire.birp.modules.sys.web.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bi.common.config.Global;
import com.aspire.auth.modules.sys.entity.Menu;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.modules.sys.utils.UserUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Controller
public class MainController  {
	
	private Logger logger = Logger.getLogger(MainController.class);
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Subject subject = SecurityUtils.getSubject();  
		Object principal = subject.getPrincipal();  
	    PrincipalCollection principals = subject.getPrincipals();
	    if(principals==null){
			return "/";
	    }
	    List list =  principals.asList();
	    String userId = null;
	    if(list!=null && list.size()>0){
	    	userId = list.get(0).toString();
	    }
 		// 如果已经登录，则跳转到管理首页
		if(userId != null){
			return "redirect:/a/index.do";
		}
		model.addAttribute("isValidateCodeLogin", true);
		return "/";
	}
	
	@RequestMapping("/a/logout")
	public String logout(){
 		try {
			UserUtils.logout();
 		} catch (Exception e) {
			logger.error("注销失败", e);
 		}
		return "redirect:"+Global.getConfig("sso.cas.url")+"/logout";	
	}
	
	
	@RequestMapping("/a")
	public String index(HttpServletResponse response,HttpServletRequest request,HttpSession session,Model model) throws RemoteException{
 		User user = UserUtils.getUser();
	/*	User user = UserUtils.getUserByLoginName("admin");
		UserUtils.putCache(UserUtils.CACHE_USER, user);*/
 		// 未登录，则跳转到登录页
		if(user.getId() == null){
			return "redirect:"+Global.getConfig("shiro.login.url");
		}
 		
 		
 		
		//每次第一次登录都重新清理 缓存
		if(session.getAttribute("userName") == null){
 			UserUtils.reomveCacheMap(user.getLoginName());
			session.setAttribute("userName", user.getLoginName());
		}
		
 		model.addAttribute("user", user);
		String parentId = request.getParameter("parentId");
		String url = "redirect:/a/index";
 		if(parentId==null || parentId.trim().equals("")){
			  List<Menu> menus = UserUtils.getMenuList();
		   	    Menu firstMenu = null;
		 	    if(menus != null){
			    	for(Menu menu:menus){
			    		if(menu.getIsShow().equals("1")){
			    			firstMenu = menu;
			    			break;
			    		}
			    	}
			    }
		 	    if(firstMenu!=null){
		 	    	parentId = firstMenu.getId();
			   	    if(firstMenu.getPageType().equals("1")){
			   	    	url = "redirect:"+firstMenu.getHref()+"?parentId="+parentId; 
			 	    }else {
						url = "redirect:/a/index?parentId="+parentId;
					}
		 	    }
		}else {
			url = "redirect:/a/index?parentId="+parentId;
 		}
   		return url;
	}
	
	/*@RequestMapping("/a")
	public String index(HttpServletResponse response,HttpServletRequest request,HttpSession session,Model model) throws RemoteException{
 		User user = UserUtils.getUser();
 		// 未登录，则跳转到登录页
		if(user.getId() == null){
			return "redirect:"+Global.getConfig("shiro.login.url");
		}
		
		//每次第一次登录都重新清理 缓存
		if(session.getAttribute("userName") == null){
 			UserUtils.reomveCacheMap(user.getLoginName());
			session.setAttribute("userName", user.getLoginName());
		}
		
 		model.addAttribute("user", user);
		String parentId = request.getParameter("parentId");
		String url = "/pages/index";
 		if(parentId==null || parentId.trim().equals("")){
			  List<Menu> menus = UserUtils.getMenuList();
		   	    Menu firstMenu = null;
		 	    if(menus != null){
			    	for(Menu menu:menus){
			    		if(menu.getIsShow().equals("1")){
			    			firstMenu = menu;
			    			break;
			    		}
			    	}
			    }
		 	    if(firstMenu!=null){
		 	    	parentId = firstMenu.getId();
			   	    if(firstMenu.getPageType().equals("1")){
			   	    	url = "redirect:"+firstMenu.getHref()+"?parentId="+parentId; 
			 	    }else {
						url = "/pages/index?parentId="+parentId;
					}
		 	    }
		}
   		return url;
	}
	*/
	
	/**
	 * 默认进入数据监控页面
	 * @return
	 */
	@RequestMapping("/a/index")
	public String index(){
 		return "/modules/fupin/entrance";
	} 
	
	/**
	 * 进入数据监控
	 * @return
	 */
	@RequestMapping("/a/entrance")
	public String entrancePage(){
 		return "/modules/fupin/entrance";
	}
	
	/**
	 * 进入战略地图
	 * @return
	 */
	@RequestMapping("/a/strategy")
	public String strategyPage(){
 		return "/modules/fupin/strategy";
	}
	
	/**
	 * 进入数据监控
	 * @return
	 */
	@RequestMapping("/a/data")
	public String dataPage(){
 		return "/modules/fupin/data";
	}
	
	/**
	 * 进入责任监控
	 * @return
	 */
	@RequestMapping("/a/duty")
	public String dutyPage(){
 		return "/modules/fupin/duty";
	}
	
	/**
	 * 进入项目监控
	 * @return
	 */
	@RequestMapping("/a/project")
	public String projectPage(){
 		return "/modules/fupin/project";
	}
	
	/**
	 * 进入资金监控
	 * @return
	 */
	@RequestMapping("/a/capital")
	public String capitalPage(){
 		return "/modules/fupin/capital";
	}
	
	/**
	 * 示范村
	 * @return
	 */
	@RequestMapping("/a/model")
	public String modelPage(){
 		return "/modules/fupin/example_country";
	}
	
	/**
	 * 进入绩效考核
	 * @return
	 */
	@RequestMapping("/a/check")
	public String checkPage(){
 		return "/modules/fupin/check";
	}
	
	/**
	 * 进入东西部扶贫
	 * @return
	 */
	@RequestMapping("/a/dxfp")
	public String dxfpPage(){
 		return "/modules/fupin/dxfp";
	}
	
	/**
	 * 进入扶贫服务
	 * @return
	 */
	@RequestMapping("/a/service")
	public String servicePage(){
 		return "/modules/fupin/poor_service";
	}
	
	/**
	 * 进入搜索
	 * @return
	 */
	@RequestMapping("/a/search")
	public String serachPage(){
 		return "/modules/fupin/search";
	}
	
	@RequestMapping("/a/search_result")
	public String serachResultPage(){
 		return "/modules/fupin/search_result";
	}
	
	/**
	 * 进入地图demo
	 * @return
	 */
	@RequestMapping("/a/demo")
	public String mapDemo(){
 		return "/modules/test/demo";
	}
//	@RequestMapping("/a/index")
//	public String index(Model model){
// 		return "/pages/index";
//	} 
//	
//	@RequestMapping("/a/firstPage")
//	public String firstPage(Model model){
// 		return "/pages/firstPage";
//	}
//	
//	@RequestMapping("/a/sysMgr")
//	public String sysMgr(HttpServletResponse response,Model model){
//		response.setHeader("P3P","CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
// 		return "/pages/sysMgr";
//	}
	
	//通过parentId 返回子 menu List
	@RequestMapping("/a/menus")
	@JsonIgnoreProperties(value={"hibernateLazyInitializer"})  
	@ResponseBody
	public List<Menu> menus(@RequestParam Map<String, String> param){
		String parentId = param.get("parentId");
		Integer level = Integer.parseInt(param.get("level"));
		List<Menu> menus = new ArrayList<Menu>();
 		try {
 			menus = UserUtils.getMenuList(parentId, level);
 		} catch (Exception e) {
 			e.printStackTrace();
		}                        
 		return menus;
 	}
	                                     
 	
	
	public static void main(String[] args) {
		System.out.println(Global.getConfig("modulesName"));
	}
}
