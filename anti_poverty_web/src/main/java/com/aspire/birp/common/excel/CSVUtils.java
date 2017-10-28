package com.aspire.birp.common.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Title: csv文件处理类
 * @Description: 用于生成及读取CSV文件格式
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年11月2日 下午2:55:53
 * @version: V1.0
 */
public class CSVUtils {

	// csv's default delemiter is ','
	private final static String DEFAULT_DELIMITER = ",";
	private final static String DEFAULT_NEW_LINE = "\r\n";

	// Mark a new line
	/* private final static String DEFAULT_END = "\r\n"; */
	// If you do not want a UTF-8 ,just replace the byte array.
	/*
	 * private final static byte commonCsvHead[] =
	 * {(byte)0xEF,(byte)0xBB,(byte)0xBF};
	 */

	/**
	 * 生成带标题的CVS文件
	 * @param map csv文件的列表头map
	 * @param outPutPath 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static File createEmptyCSV(LinkedHashMap map,
			String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv",
					new File(outPutPath));
			System.out.println("csvFile：" + csvFile);
			// UTF-8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile),
					FileProperties.getCsvFileCode()), 1024);
			System.out.println("csvFileOutputStream：" + csvFileOutputStream);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
					.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
						.next();
				csvFileOutputStream
						.write("" + (String) propertyEntry.getValue() != null ? (String) propertyEntry
								.getValue() : "" + "");
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(DEFAULT_DELIMITER);
				}
			}
			csvFileOutputStream.write(DEFAULT_NEW_LINE);
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 追加写入CVS文件内容
	 * 
	 * @param exportData 源数据列表
	 * @param 原始文件
	 * @author 张健雄
	 */
	@SuppressWarnings("rawtypes")
	public static File  appendCSV(List<Map<String, Object>> exportData,
			LinkedHashMap map, File csv) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csv, true),FileProperties.getCsvFileCode()), 1024);
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
						.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
							.next();
					String temp_ = BeanUtils.getProperty(row,
							(String) propertyEntry.getKey());
					String value = StringUtils.isBlank(temp_) ? "" : temp_;
					writer.write(value);
					if (propertyIterator.hasNext()) {
						writer.write(DEFAULT_DELIMITER);
					}
				}
				if (iterator.hasNext()) {
					writer.write(DEFAULT_NEW_LINE);
				}
			}
			writer.write(DEFAULT_NEW_LINE);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csv;
	}

	/**
	 * 生成为CVS文件
	 * 
	 * @param exportData 源数据List
	 * @param map csv文件的列表头map
	 * @param outPutPath 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static File createCSVFile(List<Map<String, Object>> exportData,
			LinkedHashMap map, String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv",
					new File(outPutPath));
			System.out.println("csvFile：" + csvFile);
			// UTF-8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile),
					FileProperties.getCsvFileCode()), 1024);
			System.out.println("csvFileOutputStream：" + csvFileOutputStream);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
					.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
						.next();
				csvFileOutputStream
						.write("" + (String) propertyEntry.getValue() != null ? (String) propertyEntry
								.getValue() : "" + "");
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(DEFAULT_DELIMITER);
				}
			}
			csvFileOutputStream.write(DEFAULT_NEW_LINE);
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
						.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
							.next();
					String temp_ = BeanUtils.getProperty(row,
							(String) propertyEntry.getKey());
					String value = StringUtils.isBlank(temp_) ? "" : temp_;
					csvFileOutputStream.write(value);
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(DEFAULT_DELIMITER);
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.write(DEFAULT_NEW_LINE);
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 下载CVS文件
	 * 
	 * @param response
	 * @param csvFilePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @throws IOException
	 */
	public static void exportFile(HttpServletResponse response,
			String csvFilePath, String fileName) throws IOException {

		// 客户使用保存文件的对话框
		response.setCharacterEncoding(FileProperties.getCsvFileCode());
		response.setContentType("application/csv;charset="
				+ FileProperties.getCsvFileCode());
		response.setHeader("Content-Disposition", "attachment;Filename="
				+ new String(
						fileName.getBytes(FileProperties.getCsvFileCode()),
						"ISO8859-1"));

		InputStream in = null;
		try {
			in = new FileInputStream(csvFilePath);
			String length = String.valueOf(in.available());
			response.setHeader("Content-Length", length);
			int len = 0;
			byte[] buffer = new byte[1024];
			OutputStream out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				/* out.write(commonCsvHead); */
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 删除该目录filePath下的所有文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 */
	public static void deleteFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 * @param fileName
	 *            文件名称
	 */
	public static void deleteFile(String filePath, String fileName) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (files[i].getName().equals(fileName)) {
						files[i].delete();
						return;
					}
				}
			}
		}
	}

	/**
	 * 测试数据
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
//		List exportData = new ArrayList<Map>();
//		Map row1 = new LinkedHashMap<String, String>();
//		row1.put("1", "11");
//		row1.put("2", "12");
//		row1.put("3", "13");
//		row1.put("4", "14");
//		exportData.add(row1);
//		row1 = new LinkedHashMap<String, String>();
//		row1.put("1", "21");
//		row1.put("2", "22");
//		row1.put("3", "23");
//		row1.put("4", "24");
//		exportData.add(row1);
//		LinkedHashMap map = new LinkedHashMap();
//		map.put("1", "第一列");
//		map.put("2", "第二列");
//		map.put("3", "第三列");
//		map.put("4", "第四列");
//
//		String path = "D:/export/";
//		String fileName = "文件导出";
//		File file = CSVUtils.createCSVFile(exportData, map, path, fileName);
//		String fileName2 = file.getName();
//		System.out.println("文件名称：" + fileName2);
		 
		/*
		String path = "c:" + File.separator + "export" + File.separator
				+ "date.cvs";
		System.out.println(path.substring(0, path.lastIndexOf(File.separator)));
		System.out
				.println(path.substring(path.lastIndexOf(File.separator) + 1));*/
		
		String test = "select * from abc t where t.abc=? and t.id in (select id from bcd)";
		System.out.println("index from :" + test.indexOf("from"));
		System.out.println("count sql : " + "select count(1) " + test.substring(test.indexOf("from")));
		System.out.println(193000/10000);
		
		

	}
}
