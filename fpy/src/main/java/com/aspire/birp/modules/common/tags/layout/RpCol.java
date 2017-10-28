package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.aspire.bi.common.util.StringUtils;

@SuppressWarnings("serial")
public class RpCol extends TagSupport {
	
	/**
	 * 单元格的标题
	 */
	private String title = "";
	
	/**
	 * 单元格的图标
	 * 表格 Table
	 * 饼状图 Pie
	 * 柱状图 Bar
	 * 折线图 Line
	 * 仪表盘 Gauge
	 * 面积图 Area
	 * 雷达图 Polar
	 * 气泡图 Scatter
	 */
	private String icon = "";
	
	/**
	 * 按钮组，格式为：add:fu('123');del:fu2();参数组为(add,edit,delete,search,upload,download)
	 */
	private String btns = "";

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {
			   out.print(this.startTag(false));			   
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_BODY_INCLUDE;
	}
	
	/**
	 * 动态生成开始标签
	 * @return
	 */
	protected String startTag(boolean isGrid){
		StringBuffer tag_ = new StringBuffer();
		String width = "100%";
		/*获取父标签属性值*/
		RpRow pt = (RpRow) this.getParent();
		String class_ = "kit-col";
		String style_ = "";
		if(isGrid && !pt.isColumns()) {
			class_ = "kit-col-grid";
			width = "99.8%";
		}else if(isGrid && pt.isColumns()){
			style_ = "border:0px !important;";
		}
		if(pt.isColumns()) width = "50%";
		
		tag_.append(" <div class=\""+class_+"\" style=\"width:"+width+";"+style_+"\">");
		tag_.append("  <div class=\"panel\">");
		tag_.append("   <div class=\"panel-head\">");
		tag_.append("     <h2><i class=\"kit-icon-medium kit-icon-"+iconConv(this.icon)+"\"></i>"+this.title+"</h2>");
		/*处理按钮组*/
		tag_.append(this.putBtns(this.btns));
		tag_.append("   </div>");	
		return tag_.toString();	
	}
	
	
	public int doEndTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {			   
			   out.print("  </div>");
			   out.print(" </div>");
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
	}
	
	/**
	 * 转换图标格式
	 * @param icon
	 * @return
	 */
	protected String iconConv(String icon){
		String tag_ = "table";
		if(Icons.Pie.toString().equalsIgnoreCase(icon)){
			tag_ = "chart2";
		}else if(Icons.Area.toString().equalsIgnoreCase(icon)){
			tag_ = "chart4";
		}else if(Icons.Bar.toString().equalsIgnoreCase(icon)){
			tag_ = "chart1";
		}else if(Icons.Gauge.toString().equalsIgnoreCase(icon)){
			tag_ = "chart7";
		}else if(Icons.Line.toString().equalsIgnoreCase(icon)){
			tag_ = "chart3";
		}else if(Icons.Polar.toString().equalsIgnoreCase(icon)){
			tag_ = "chart6";
		}else if(Icons.Scatter.toString().equalsIgnoreCase(icon)){
			tag_ = "chart5";
		}else{
			tag_ = "table";
		}	
		return tag_;
	}
	
	/**
	 * 处理按钮组
	 * @param btns
	 * @return
	 */
	protected String putBtns(String btns){
		StringBuffer tag_ = new StringBuffer();
		
		if(StringUtils.isBlank(btns)) return tag_.toString();
		
		String[] btnInfo = StringUtils.split(btns,";");
		for (int i = 0; i < btnInfo.length; i++) {
			String[] btn_ = StringUtils.split(btnInfo[i],":");
			String iconName = btn_[0];
			String fn = "";
			if(btn_.length > 1) fn = btn_[1];
			if(Buttons.add.toString().equals(iconName)){
				tag_.append("     <a href=\"javascript:"+fn+";\" class=\"kit-icon-medium kit-icon-add1\"></a>");
			}else if(Buttons.delete.toString().equals(iconName)){
				tag_.append("     <a href=\"javascript:"+fn+";\" class=\"kit-icon-medium kit-icon-del1\"></a>");
			}else if(Buttons.download.toString().equals(iconName)){
				tag_.append("     <a href=\"javascript:"+fn+";\" class=\"kit-icon-medium kit-icon-export\"></a>");
			}else if(Buttons.edit.toString().equals(iconName)){
				tag_.append("     <a href=\"javascript:"+fn+";\" class=\"kit-icon-medium kit-icon-edit1\"></a>");
			}else if(Buttons.search.toString().equals(iconName)){
				tag_.append("     <a href=\"javascript:"+fn+";\" class=\"kit-icon-medium kit-icon-search1\"></a>");
			}else if(Buttons.upload.toString().equals(iconName)){
				tag_.append("     <a href=\"javascript:"+fn+";\" class=\"kit-icon-medium kit-icon-import\"></a>");
			}else if(Buttons.more.toString().equals(iconName)){
				tag_.append("     <a href=\"javascript:"+fn+";\" class=\"button-more\"></a>");
			}else if(Buttons.toggle.toString().equals(iconName)){
				String temp_ = StringUtils.replaceEach(btnInfo[i], new String[]{"toggle:{","}"}, new String[]{"",""});
				String[] toggle_ = StringUtils.split(temp_,",");
				String id_ = "";
				String icon_ = "";
				String name_ = "";
				for (int j = 0; j < toggle_.length; j++) {
					if(toggle_[j].startsWith("id")){
						id_ = toggle_[j].replace("id:", "");
					}else if(toggle_[j].startsWith("icon")){
						icon_ = toggle_[j].replace("icon:", "");
					}else if(toggle_[j].startsWith("name")){
						name_ = toggle_[j].replace("name:", "");
					}else if(toggle_[j].startsWith("click")){
						fn = toggle_[j].replace("click:", "");
					} 
				}
				if(StringUtils.isBlank(id_)) id_ = UUID.randomUUID().toString();		
				tag_.append("     <a id=\""+id_+"\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'"+icon_+"',toggle:true,plain:true\"" +
        						" onclick=\"javascript:"+fn+"(!$(this).linkbutton('options').selected);\" style=\"margin-top:1.4px;margin-right:6px\">"+name_+"</a>");
			}else if(Buttons.button.toString().equals(iconName)){
				String temp_ = StringUtils.replaceEach(btnInfo[i], new String[]{"button:{","}"}, new String[]{"",""});
				String[] toggle_ = StringUtils.split(temp_,",");
				String id_ = "";
				String icon_ = "";
				String name_ = "";
				for (int j = 0; j < toggle_.length; j++) {
					if(toggle_[j].startsWith("id")){
						id_ = toggle_[j].replace("id:", "");
					}else if(toggle_[j].startsWith("icon")){
						icon_ = toggle_[j].replace("icon:", "");
					}else if(toggle_[j].startsWith("name")){
						name_ = toggle_[j].replace("name:", "");
					}else if(toggle_[j].startsWith("click")){
						fn = toggle_[j].replace("click:", "");
					} 
				}
				if(StringUtils.isBlank(id_)) id_ = UUID.randomUUID().toString();		
				tag_.append("     <a id=\""+id_+"\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'"+icon_+"',plain:true\"" +
        						" onclick=\"javascript:"+fn+"();\" style=\"margin-top:1.4px;margin-right:6px\">"+name_+"</a>");
			}
		}
		/*如果没有符合条件的字符，则返回空对象*/
		if(StringUtils.isBlank(btns)) return tag_.toString();
		tag_.insert(0, "   <div class=\"btn-group floatright\">");
		tag_.append("   </div>");
			
		return tag_.toString();
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

	

	public void setBtns(String btns) {
		this.btns = btns;
	}


	public String getTitle() {
		return title;
	}


	public String getIcon() {
		return icon;
	}


	public String getBtns() {
		return btns;
	}

}
