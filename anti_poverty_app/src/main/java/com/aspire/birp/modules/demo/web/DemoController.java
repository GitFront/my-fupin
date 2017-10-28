package com.aspire.birp.modules.demo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aspire.birp.modules.demo.vo.GroupDataVo;
import com.aspire.birp.modules.demo.vo.InvertedLineVo;
import com.aspire.birp.modules.demo.vo.PieDataVo;

@Controller
@RequestMapping(value = "/d")
public class DemoController  {
	
	@RequestMapping("/demo.htm")
	public String forward(@RequestParam(value = "id") String path){			
		return "/modules/rp_demo/"+path;
	}
	
	@RequestMapping(value = "/highchart/invertedLine/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<InvertedLineVo> loadInvertedLine(){	
		/*获取参数信息*/
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		
		System.out.println("----------- userid : " +userid +" | user name : " + username +"------------------");
		/*返回列表数据*/
		List<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("name", 0);
		map1.put("group", "分组1");
		map1.put("value", 15);
		rows.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("name", 10);
		map2.put("group", "分组1");
		map2.put("value", 2.5);
		rows.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("name", 20);
		map3.put("group", "分组1");
		map3.put("value", -15.3);
		rows.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("name", 30);
		map4.put("group", "分组1");
		map4.put("value", -43.6);
		rows.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("name", 40);
		map5.put("group", "分组1");
		map5.put("value", -87.2);
		rows.add(map5);
		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("name", 50);
		map6.put("group", "分组1");
		map6.put("value", -30.2);
		rows.add(map6);
		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("name", 60);
		map7.put("group", "分组1");
		map7.put("value", 21.2);
		rows.add(map7);
		HashMap<String, Object> map8 = new HashMap<String, Object>();
		map8.put("name", 70);
		map8.put("group", "分组1");
		map8.put("value", -2.3);
		rows.add(map8);
		HashMap<String, Object> map9 = new HashMap<String, Object>();
		map9.put("name", 80);
		map9.put("group", "分组1");
		map9.put("value", 21.2);
		rows.add(map9);
		HashMap<String, Object> map10 = new HashMap<String, Object>();
		map10.put("name", 0);
		map10.put("group", "分组2|hide");
		map10.put("value", 23);
		rows.add(map10);
		HashMap<String, Object> map11 = new HashMap<String, Object>();
		map11.put("name", 10);
		map11.put("group", "分组2|hide");
		map11.put("value", -10.2);
		rows.add(map11);
		 
		List<InvertedLineVo> vos = new ArrayList<InvertedLineVo>();
		
		for (HashMap<String, Object> hashMap : rows) {
			InvertedLineVo  vo = new InvertedLineVo(
					Double.parseDouble(String.valueOf(hashMap.get("name"))),
					(String)hashMap.get("group"),
					Double.parseDouble(String.valueOf(hashMap.get("value"))));
			vos.add(vo);
		}
		
		return vos;
	}
	
	@RequestMapping(value = "/highchart/groupData/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<GroupDataVo> loadGroupData(){	
		/**
		 * { name: '2013-01', group: 'olive', value: i1 },  
    	                   { name: '2013-01', group: 'momo', value: i2 },  
    	                   { name: '2013-01', group: 'only', value: i3 },  
    	                   { name: '2013-02', group: 'olive', value: i5 },  
    	                   { name: '2013-02', group: 'momo', value: i6 },  
    	                   { name: '2013-02', group: 'only', value: i7 },  
    	                   { name: '2013-03', group: 'olive', value: i9 },  
    	                   { name: '2013-03', group: 'momo', value: i10 },  
    	                   { name: '2013-03', group: 'only', value: i11 },  
    	                   { name: '2013-04', group: 'olive', value: i4 },  
    	                   { name: '2013-04', group: 'momo', value: i8 },  
    	                   { name: '2013-04', group: 'only', value: i12 }
		 */
		/*返回列表数据*/
		List<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("name", "2013-01");
		map1.put("group", "momo");
		map1.put("value", (int)(Math.random() * 100));
		rows.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("name", "2013-01");
		map2.put("group", "only");
		map2.put("value", (int)(Math.random() * 100));
		rows.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("name", "2013-01");
		map3.put("group", "olive");
		map3.put("value", (int)(Math.random() * 100));
		rows.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("name", "2013-01");
		map4.put("group", "other");
		map4.put("value", (int)(Math.random() * 100));
		rows.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("name", "2013-02");
		map5.put("group", "momo");
		map5.put("value", (int)(Math.random() * 100));
		rows.add(map5);
		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("name", "2013-02");
		map6.put("group", "only");
		map6.put("value", (int)(Math.random() * 100));
		rows.add(map6);
		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("name", "2013-02");
		map7.put("group", "olive");
		map7.put("value", (int)(Math.random() * 100));
		rows.add(map7);
		HashMap<String, Object> map8 = new HashMap<String, Object>();
		map8.put("name", "2013-02");
		map8.put("group", "other");
		map8.put("value", (int)(Math.random() * 100));
		rows.add(map8);
		HashMap<String, Object> map9 = new HashMap<String, Object>();
		map9.put("name", "2013-03");
		map9.put("group", "momo");
		map9.put("value", (int)(Math.random() * 100));
		rows.add(map9);
		HashMap<String, Object> map10 = new HashMap<String, Object>();
		map10.put("name", "2013-03");
		map10.put("group", "only");
		map10.put("value", (int)(Math.random() * 100));
		rows.add(map10);
		HashMap<String, Object> map11 = new HashMap<String, Object>();
		map11.put("name", "2013-03");
		map11.put("group", "olive");
		map11.put("value", (int)(Math.random() * 100));
		rows.add(map11);
		HashMap<String, Object> map12 = new HashMap<String, Object>();
		map12.put("name", "2013-03");
		map12.put("group", "other");
		map12.put("value", (int)(Math.random() * 100));
		rows.add(map12);
		 
		List<GroupDataVo> vos = new ArrayList<GroupDataVo>();
		
		for (HashMap<String, Object> hashMap : rows) {
			GroupDataVo  vo = new GroupDataVo(
					String.valueOf(hashMap.get("name")),
					(String)hashMap.get("group"),
					Double.parseDouble(String.valueOf(hashMap.get("value"))));
			vos.add(vo);
		}
		
		return vos;
	}
	
	
	@RequestMapping(value = "/highchart/pieData/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<PieDataVo> loadPieData(){	
		/**
		   * [
  		            { name: 'olive', value: 116 }, 
  		            { name: 'momo', value: 115 }, 
  		            { name: 'only', value: 222 }, 
  		            { name: 'for', value: 324}]
		   */
		 List<PieDataVo> vos = new ArrayList<PieDataVo>();
		 vos.add(new PieDataVo("olive",Double.valueOf(116)));
		 vos.add(new PieDataVo("momo",Double.valueOf(115)));
		 vos.add(new PieDataVo("only",Double.valueOf(222)));
		 vos.add(new PieDataVo("for",Double.valueOf(324)));	
		
		return vos;
	}
	
	
}
