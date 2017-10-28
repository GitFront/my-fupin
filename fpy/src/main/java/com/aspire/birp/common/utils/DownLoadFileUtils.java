package com.aspire.birp.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.report.ReportEnginer;


public class DownLoadFileUtils {
	
	private static Logger log = LoggerFactory.getLogger(DownLoadFileUtils.class);
	/**
	 * 下载文件
	 * @param response
	 * @param filePath
	 * @param fileName
	 * @param fileType
	 * @throws IOException
	 */
	public static void downLoadFile(HttpServletResponse response,
			String filePath, String fileName, String fileType) throws IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/"+fileType+";charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;Filename="
				+ new String(fileName.getBytes("UTF-8"),"ISO8859-1"));

		InputStream in = null;
		try {
			in = new FileInputStream(filePath);
			String length = String.valueOf(in.available());
			response.setHeader("Content-Length", length);
			int len = 0;
			byte[] buffer = new byte[1024];
			OutputStream out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
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
     * 将多个Excel打包成zip文件 
     * 打包完删除源文件
     * @param srcfile 
     * @param zipfile 
     */  
    public static void zipFiles(List<File> srcfile, File zipfile) {    
        byte[] buf = new byte[1024];    
        ZipOutputStream out = null;
        FileInputStream in = null;
        try {    
            // Create the ZIP file    
           out = new ZipOutputStream(new FileOutputStream(zipfile));    
            // Compress the files    
            for (int i = 0; i < srcfile.size(); i++) {    
                File file = srcfile.get(i);    
                in = new FileInputStream(file);    
                // Add ZIP entry to output stream.    
                out.putNextEntry(new ZipEntry(file.getName()));    
                // Transfer bytes from the file to the ZIP file    
                int len;    
                while ((len = in.read(buf)) > 0) {    
                    out.write(buf, 0, len);    
                }    
                // Complete the entry    
                 
            }    
            // Complete the ZIP file    
               
        } catch (IOException e) {    
           e.printStackTrace();  
        }finally {
        	try {
				out.closeEntry();
				in.close();  
		        out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
           
		}    
    }    
  
  
    public static void downFile(HttpServletResponse response,String serverPath, String str) {    
    	 InputStream ins = null;
         BufferedInputStream bins = null;
         OutputStream outs = null;
         BufferedOutputStream bouts = null;
    	try {    
            String path = serverPath + str;    
            File file = new File(path); 
            if (file.exists()) {    
                ins = new FileInputStream(path);    
                bins = new BufferedInputStream(ins);// 放到缓冲流里面    
                outs = response.getOutputStream();// 获取文件输出IO流    
                bouts = new BufferedOutputStream(outs);    
                response.setContentType("application/x-download");// 设置response内容的类型    
                response.setHeader(    
                        "Content-disposition",    
                        "attachment;filename="    
                                + URLEncoder.encode(str, "UTF-8"));// 设置头部信息    
                int bytesRead = 0;    
                byte[] buffer = new byte[8192];    
                 //开始向网络传输文件流    
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {    
                   bouts.write(buffer, 0, bytesRead);    
               }    
               bouts.flush();// 这里一定要调用flush()方法       
            } else {    
            	throw new Exception("downLoad file is not exist ！ filePath: "+path);    
            }    
        } catch (Exception e) {    
        	log.error("下载文件异常: ",e); 
        }finally {
        	 try {
				ins.close();
				bins.close();    
	            outs.close();    
	            bouts.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
             
		}    
    }  
    
    public static void createExcel(String templateFile, Map<String, Object> context, String destFile) {    
        try {
        	ReportEnginer enginer = new ReportEnginer();
        	enginer.excute(templateFile, context, destFile);
        } catch (Exception e) {    
            e.printStackTrace();  
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


    public static void main(String[] args) {
    	List<File> srcfile = new ArrayList<File>();
    	srcfile.add(new File("D:/output.xls"));
    	srcfile.add(new File("D:/renzhi2.xls"));
    	zipFiles(srcfile,new File("D:/excel.zip"));
	}
}
