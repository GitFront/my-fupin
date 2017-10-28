package com.aspire.birp.common.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/** 

 * 导出EXCEL工具类，适用于单行表头的表格 

 * @author wjq 

 * @since 2012-08-30 

 */  

public class ExportExcelUtils {  

      

private static final long serialVersionUID = 2165773254718823136L;  

    

    /** 

     * @Title: exportExcel 

     * @Description: 导出Excel的方法

     * @author: EX-WANGJIANQIANG001 @ 2012-8-31 下午03:49:08

     * @param workbook 

     * @param sheetNum 

     * @param sheetTitle

     * @param headers

     * @param result

     * @param out

     * @throws Exception    

     */    

    public void exportExcel(Workbook workbook,int sheetNum,String sheetTitle, String[] headers, List<List<String>> result, OutputStream out) throws Exception{  

        // 生成一个表格  

    Sheet sheet = workbook.createSheet();

    workbook.setSheetName(sheetNum,sheetTitle);

        // 生成一个样式  

    CellStyle style = workbook.createCellStyle();  

        // 设置样式  

        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);  

        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  

        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  

        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  

        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  

        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成一个字体  

        Font font = workbook.createFont();  
        //org.apache.poi.hssf.util.HSSFColor$GREY_25_PERCENT@452267
        //font.setColor(HSSFColor.BLACK.index);  
       
        font.setColor(HSSFColor.GREEN.index);  
        font.setFontHeightInPoints((short) 12);  

        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  

        // 把字体应用到当前的样式  

        style.setFont(font);  

          

        // 指定当单元格内容显示不下时自动换行  

        style.setWrapText(true);  

          

        // 产生表格标题行  

        Row row = sheet.createRow(0);  

        for (int i = 0; i < headers.length; i++) {  

            Cell cell = row.createCell(i);  

            cell.setCellStyle(style);  

            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  

            cell.setCellValue(text.toString());

        }  

        // 遍历集合数据，产生数据行  

        if(result != null){  

            int index = 1;  

            for(List<String> m:result){  

                row = sheet.createRow(index);  

                int cellIndex = 0;  

               for(String str:m){

                Cell cell = row.createCell(cellIndex);  

                cell.setCellValue(str.toString());  

                cellIndex++;  

              }

              index++;  

            }     

        }  

    }  
    
    public void exportExcel(Workbook workbook,int sheetNum,String sheetTitle, String[] headers,List<Map<String, Object>> result, String[] listKey ) throws Exception{  

        // 生成一个表格  

    Sheet sheet = workbook.createSheet();

    workbook.setSheetName(sheetNum,sheetTitle);
        // 生成一个样式  
    CellStyle style = workbook.createCellStyle();  

        // 设置样式  
        //style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);  
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); 
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
        style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色． 
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
        style.setLeftBorderColor(HSSFColor.BLACK.index); 
        style.setBorderRight(HSSFCellStyle.BORDER_THIN); 
        style.setRightBorderColor(HSSFColor.BLACK.index); 
        style.setBorderTop(HSSFCellStyle.BORDER_THIN); 
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐 
        style.setWrapText(true);// 指定单元格自动换行 
        
        // 生成一个字体  

        Font font = workbook.createFont();  
       // font.setColor(HSSFColor.BLACK.index);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体"); 
        font.setFontHeight((short) 230); 
        //font.setColor(HSSFColor.GREEN.index); 
       // font.setFontHeightInPoints((short) 12);  

       // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  

        // 把字体应用到当前的样式  

        style.setFont(font);  

          

        // 指定当单元格内容显示不下时自动换行  

       // style.setWrapText(true);  
        
        CellStyle style1 = workbook.createCellStyle();  
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
        style1.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色． 
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
        style1.setLeftBorderColor(HSSFColor.BLACK.index); 
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN); 
        style1.setRightBorderColor(HSSFColor.BLACK.index); 
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN); 
        style1.setTopBorderColor(HSSFColor.BLACK.index);
        //style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐 
        style1.setWrapText(false);// 指定单元格自动换行 

          

        // 产生表格标题行  

        Row row = sheet.createRow(0);  
        String [] testva= new String[headers.length];
        for (int i = 0; i < headers.length; i++) {  
        	 testva[i]="8888888";
            Cell cell = row.createCell(i);  

            cell.setCellStyle(style);  

            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  

            cell.setCellValue(text.toString());
           // sheet.autoSizeColumn(( short ) i );
           
        }  
       
        // 遍历集合数据，产生数据行  
        
        
		if (result.size() != 0) {
			
			 int index = 1;  
			for (Map map : result) {
				
				row = sheet.createRow(index); 
				 int cellIndex = 0;
				for (String key : listKey) {
					
					if(null ==testva[cellIndex])
					{
						testva[cellIndex]="0";
					}
					
					if(null !=map.get(key))
					{
						Cell cell = row.createCell(cellIndex);
						String va=map.get(key).toString();
						if(va.getBytes().length >= testva[cellIndex].getBytes().length)
						{
							if(cellIndex!=0 && va.getBytes().length > testva[cellIndex-1].getBytes().length)
							{
								testva[cellIndex]=va;
							}
							
						}
		                   if(!org.apache.commons.lang3.StringUtils.isEmpty(va) && isNum(va) ){
		                      //是数字当作double处理
		                	   if(va.length()>11)
		                	   {
		                		   cell.setCellValue(va); 
									cell.setCellStyle(style1); 
		                	   }else
		                	   {
		                      cell.setCellValue(Double.parseDouble(va));
		                      cell.setCellStyle(style1); 
		                     
		                	   }
		                   }else
		                   {
		                	cell.setCellValue(map.get(key).toString()); 
							cell.setCellStyle(style1); 

		                   }

		                  // sheet.setColumnWidth(cellIndex,map.get(key).toString().getBytes().length*2*256);
						/*
						
						if (map.get(key) instanceof Double) {
							cell.setCellValue(((Double) map.get(key)).doubleValue()); 
							cell.setCellStyle(style1); 
						} else if (map.get(key) instanceof Integer) {
							cell.setCellValue(((Integer) map.get(key)).doubleValue()); 
							cell.setCellStyle(style1); 
						} else if (map.get(key) instanceof Number) {
							cell.setCellValue(((Number) map.get(key)).doubleValue()); 
							cell.setCellStyle(style1); 
						} else {
						cell.setCellValue(map.get(key).toString()); 
						cell.setCellStyle(style1); 
					}*/
					}else
					{
						Cell cell = row.createCell(cellIndex);
						cell.setCellValue(""); 
						cell.setCellStyle(style1); 
					}
					cellIndex++;
				}
				index++;
			}
			
		} else {
			// 如果内容为空，则提示无符合条件记录
			row = sheet.createRow(1); 
			Cell cell = row.createCell(2);
			cell.setCellValue("无符合条件记录....");
		}
			
		for(int i=0;i<testva.length;i++)
		{
			String t=testva[i]+"88888888888";
			sheet.setColumnWidth(i,t.getBytes().length*256);
			//sheet.autoSizeColumn(( short ) i,true ); // 调整第一列宽度
			/*if(testva[i].length()== testva[i].getBytes().length)
			{
				String t=testva[i]+"88";
				sheet.setColumnWidth(i,t.length()*256);
			}else
			{
			//sheet.setColumnWidth(i,testva[i].length()*2*256);
			sheet.setColumnWidth(i,testva[i].getBytes().length*256);
			}*/
			
		}
			
    }  

    
    /**
	 * 关闭流
	 * 
	 * @param is
	 * @param aas
	 * @param os
	 */
	public void closeIO(ByteArrayInputStream aas,
			ByteArrayOutputStream os) {
		
		if (aas != null) {
			try {
				aas.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
    
    public  boolean isNum(String str){return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");}
}  
