package com.aspire.birp.common.excel;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableFont;

/**
 * @Title: Excel操作类
 * @Description: 简单的Excel操作，完成与数据库的动态导出
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月30日 上午11:34:07
 * @version: V1.0
 */
public class ExcelUtils {

	/**
	 * 从数据库读数据，写入Excel
	 * 
	 * @param os  数据流，如果是写本地文件的话，可以是FileOutputStream；
	 *            如果是写Web下载的话，可以是ServletOupputStream
	 * @param title Excel工作簿的标题,如果不用的话,可以写null或者""
	 * @param rs 数据结果集
	 * @param map 数据结果集对应Excel表列名映射:key对应数据结果集的列名，必须是大写； value,目前只能对应Column对象
	 * @throws Exception 方法内的父类异常有SQLException和IOException
	 */
	public static void export(OutputStream os, String title, List<Map<String,Object>> rs,
			Map<String,Column> mapper) throws Exception {

		jxl.write.WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
		jxl.write.WritableSheet wsheet = wbook.createSheet("第一页", 0); // sheet名称
		jxl.write.WritableFont wfont = null; // 字体
		jxl.write.WritableCellFormat wcfFC = null; // 字体格式
		jxl.write.Label wlabel = null; // Excel表格的Cell

		// 设置excel标题字体
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 16,
				WritableFont.BOLD, false,
				jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
		wcfFC = new jxl.write.WritableCellFormat(wfont);

		// 添加excel标题
		jxl.write.Label wlabel1 = new jxl.write.Label(5, 0, title, wcfFC);
		wsheet.addCell(wlabel1);

		// 设置列名字体
		// 如果有标题的话，要设置一下偏移
		int offset = 2;
		if (title == null || title.trim().equals(""))
			offset = 0;
		else {
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			wcfFC = new jxl.write.WritableCellFormat(wfont);
		}

		// 根据map来创建Excel的列名
		Collection<Column> array = mapper.values();
		Iterator<Column> it = array.iterator();
		while (it.hasNext()) {
			Column col =  it.next();
			wlabel = new jxl.write.Label(col.getIndex(), offset,
					col.getDisplayName());
			wsheet.addCell(wlabel);
		}

		// 设置正文字体
		wfont = new jxl.write.WritableFont(WritableFont.TIMES, 14,
				WritableFont.BOLD, false,
				jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
		wcfFC = new jxl.write.WritableCellFormat(wfont);

		// 往Excel输出数据
		int rowIndex = 1 + offset;
		for (Map<String,Object> row : rs) {
			it = array.iterator();
			while (it.hasNext()) {
				Column col =  it.next();
				String value = String.valueOf(row.get(col.getMetaName()));
				if("null".equalsIgnoreCase(value)) value = "";
				wlabel = new jxl.write.Label(col.getIndex(), rowIndex, value);
				wsheet.addCell(wlabel);
			}
			rowIndex++;
		}
		
		wbook.write(); // 写入文件
		wbook.close();
		os.flush();
		os.close();
	}
	
	
	/**
	 * 从数据库读数据，写入Excel
	 * 
	 * @param os  数据流，如果是写本地文件的话，可以是FileOutputStream；
	 *            如果是写Web下载的话，可以是ServletOupputStream
	 * @param title Excel工作簿的标题,如果不用的话,可以写null或者""
	 * @param rs 数据结果集
	 * @param map 数据结果集对应Excel表列名映射:key对应数据结果集的列名，必须是大写； value,目前只能对应Column对象
	 * @throws Exception 方法内的父类异常有SQLException和IOException
	 */
	public static void export(OutputStream os, String title, List<Object[]> rs,
			List<Column> mapper) throws Exception {

		jxl.write.WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
		jxl.write.WritableSheet wsheet = wbook.createSheet("第一页", 0); // sheet名称
		jxl.write.WritableFont wfont = null; // 字体
		jxl.write.WritableCellFormat wcfFC = null; // 字体格式
		jxl.write.Label wlabel = null; // Excel表格的Cell

		// 设置excel标题字体
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 16,
				WritableFont.BOLD, false,
				jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
		wcfFC = new jxl.write.WritableCellFormat(wfont);

		// 添加excel标题
		jxl.write.Label wlabel1 = new jxl.write.Label(5, 0, title, wcfFC);
		wsheet.addCell(wlabel1);

		// 设置列名字体
		// 如果有标题的话，要设置一下偏移
		int offset = 2;
		if (title == null || title.trim().equals(""))
			offset = 0;
		else {
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			wcfFC = new jxl.write.WritableCellFormat(wfont);
		}

		// 根据map来创建Excel的列名
		for (int i = 0; i < mapper.size(); i++) {
			Column col =  mapper.get(i);
			wlabel = new jxl.write.Label(col.getIndex(), offset,
					col.getDisplayName());
			wsheet.addCell(wlabel);
		}

		// 设置正文字体
		wfont = new jxl.write.WritableFont(WritableFont.TIMES, 14,
				WritableFont.BOLD, false,
				jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
		wcfFC = new jxl.write.WritableCellFormat(wfont);

		// 往Excel输出数据
		int rowIndex = 1 + offset;
		for (Object[] row : rs) {
			for (int i = 0; i < row.length; i++) {
				String value = String.valueOf(row[i]);
				if("null".equalsIgnoreCase(value)) value = "";
				wlabel = new jxl.write.Label(i, rowIndex, value);
				wsheet.addCell(wlabel);
			}
			rowIndex++;
		}
		
		wbook.write(); // 写入文件
		wbook.close();
		os.flush();
		os.close();
	}
}
