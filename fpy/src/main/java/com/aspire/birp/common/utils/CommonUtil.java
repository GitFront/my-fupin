package com.aspire.birp.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.chainsaw.Main;

/**
 * <b>Description</b>: 共通处理类
 * 
 */
public class CommonUtil {
	 /**
     * 路径符号
     */
    public static String strSeparator = System.getProperty("file.separator");
    
	/**
	 * 去除字符串空值
	 * @param str
	 * @return
	 */
	public static String nullToString(String str){
		if(str==null || str.length()==0){
			return "";
		}else{
			return str.trim();
		}
	}
	
	/**
	 * 获取日期String值,默认格式：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateStringValue(Date date,String format){
		if(format==null ||("").equals(format)){
			format="yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 将日期字符串转换为日期,默认格式：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date getDateByString(String strDate,String format){
		Date date = null;
		try {
			if(format==null ||("").equals(format)){
				format="yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			if(!isEmpty(strDate)){
				date = sdf.parse(strDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 校验是否为日期及日期格式是否正确
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static boolean isDate(String strDate,String format){
		try {
			if(format==null ||("").equals(format)){
				format="yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 通过参数获取前几月的时间
	 * @param date 日期
	 * @param iMonth 月数
	 * @param iDays 天数
	 * @return
	 */
    public static Date getDateByMonthAndDay(Date date, int iMonth, int iDays) {
		// 开始日期
		GregorianCalendar greDate = new GregorianCalendar();

		try {
			greDate.setTime(date);
			greDate.add(GregorianCalendar.MONTH, iMonth);
			greDate.add(GregorianCalendar.DATE, iDays);
			return greDate.getTime();
		} catch (Exception e) {
			return null;
		}
	}
    
    /**
     * 通过参数时分秒获取变更后的时间
     * @param date
     * @param iHour
     * @param iMinute
     * @param iSecond
     * @return
     */
    public static Date getDateTimeByIncrement(Date date, final int iHour,
			final int iMinute, final int iSecond) {
		// 开始日期
		GregorianCalendar greDate = new GregorianCalendar();

		try {
			greDate.setTime(date);
			greDate.add(GregorianCalendar.HOUR, iHour);
			greDate.add(GregorianCalendar.MINUTE, iMinute);
			greDate.add(GregorianCalendar.SECOND, iSecond);
			return greDate.getTime();
		} catch (Exception e) {
			return null;
		}
	}
    /**
     * 根据日期计算当天开始的最早时间（即0点0分0秒0毫秒）
     * @param date
     * @return
     */
    public static Date getCurrtDateStartTime(Date date){
    	String str = getDateStringValue(date, "yyyy-MM-dd");
		str += " 00:00:00";
		return getDateByString(str, null);
    }
    
    /**
     * 根据日期计算当天开始的最早时间（即0点0分0秒0毫秒）
     * @param date
     * @return
     */
    public static Date getCurrtDateEndTime(Date date){
    	String str = getDateStringValue(date, "yyyy-MM-dd");
		str += " 23:59:59";
		return getDateByString(str, null);
    }
    
    /**   
     * 计算两个日期之间相差的天数(超过24小时算一天)   
     * @param date1   
     * @param date2   
     * @return   
     */    
    public static int getDaysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
    
    
    /**   
     * 计算两个日期跨度天数
     * @param date1   
     * @param date2   
     * @return   
     */    
    public static int getDaysSpan(Date startDate, Date endDate ){  
        Calendar beginCalendar = Calendar.getInstance();  
        Calendar endCalendar = Calendar.getInstance() ;   
          
        try {  
            beginCalendar.setTime( startDate ) ;  
            endCalendar.setTime( endDate ) ;  
              
            if (beginCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)) {  
                return endCalendar.get(Calendar.DAY_OF_YEAR)  
                        - beginCalendar.get(Calendar.DAY_OF_YEAR);  
            } else {  
                if (beginCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {  
                    int days = beginCalendar.getActualMaximum(Calendar.DAY_OF_YEAR)  
                            - beginCalendar.get(Calendar.DAY_OF_YEAR)  
                            + endCalendar.get(Calendar.DAY_OF_YEAR);  
                    for (int i = beginCalendar.get(Calendar.YEAR) + 1; i < endCalendar  
                            .get(Calendar.YEAR); i++) {  
                        Calendar c = Calendar.getInstance();  
                        c.set(Calendar.YEAR, i);  
                        days += c.getActualMaximum(Calendar.DAY_OF_YEAR);  
                    }  
                    return days;  
                } else {  
                    int days =endCalendar.getActualMaximum(Calendar.DAY_OF_YEAR)  
                            - endCalendar.get(Calendar.DAY_OF_YEAR)  
                            + beginCalendar.get(Calendar.DAY_OF_YEAR);  
                    for (int i = endCalendar.get(Calendar.YEAR) + 1; i < beginCalendar  
                            .get(Calendar.YEAR); i++) {  
                        Calendar c = Calendar.getInstance();  
                        c.set(Calendar.YEAR, i);  
                        days += c.getActualMaximum(Calendar.DAY_OF_YEAR);  
                    }  
                    return days;  
                }  
            }  
        } catch (Exception e) {  
            //throw e;
        	System.out.println(e.getMessage());
        	return 0;
        }  
    }  
    
    /**   
     * 计算两个日期之间相差的小时数   
     * @param date1   
     * @param date2   
     * @return   
     */    
    public static int getHoursBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between = (time2 - time1) / (1000 * 3600);

		return Integer.parseInt(String.valueOf(between));
	} 
    
    /**   
     * 计算两个日期之间相差的分数   
     * @param date1   
     * @param date2   
     * @return   
     */    
    public static int getSecondsBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between = (time2 - time1) / (1000 * 60);

		return Integer.parseInt(String.valueOf(between));
	} 
	
	/**
	 * 判断字符串是否为数字
	 * 
	 * @param strin
	 * @return
	 */
	public static boolean IsNum(String str) {
		char c;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if ((c <= 0x0039 && c >= 0x0030) == false)
				return false;
		}
		return true;

	}
	
	public static boolean isNumeric(String str){  
		Pattern pattern = Pattern.compile("[0-9]*");   
		return pattern.matcher(str).matches();  
	} 

    /**
     * 判断字符串是否为空,用于校验前台数据
     * @param str
     * @return
     */
    public static boolean isEmptyOrNull(String str){
    	return str == null || "".equals(str.trim()) || "null".equals(str.trim());
    }
	/**
	 * 判断字符串是否为空（null/"")
	 */
	public static boolean isEmpty(String str){
		return str == null || "".equals(str.trim());
	}
	/**
	 * 判断Collection是否为空
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Collection coll){
		return coll == null || coll.size() < 1;
	}
	/**
	 * 判断Map是否为空
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Map map){
		return map == null || map.size() < 1;
	}
	/**
	 * 判断String数组是否为空
	 */
	public static boolean isEmpty(String[] array){
		return array == null || array.length < 1;
	}
	/**
	 * 判断String数组是否为空
	 */
	public static boolean isEmpty(byte[] array){
		return array == null || array.length < 1;
	}
	/**
	 * 判断long数组是否为空
	 */
	public static boolean isEmpty(long[] array){
		return array == null || array.length < 1;
	}
	/**
	 * 判断int数组是否为空
	 */
	public static boolean isEmpty(int[] array){
		return array == null || array.length < 1;
	}
	
	/**
	 * 获取文件扩展名 
	 * @param filename
	 * @return
	 */
    public static String getExtensionName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return filename.substring(dot);   
            }   
        }   
        return filename;   
    }
    
    /**
     * 通过文件路径截取文件名
     * @param filePath
     * @return
     */
    public static String getFileNameByFilePath(String filePath){
    	String filename = null;
    	if ((filePath != null) && (filePath.length() > 0)) {   
            int dot = filePath.lastIndexOf(strSeparator);   
            if ((dot >-1) && (dot < (filePath.length() - 1))) {   
                return filePath.substring(dot+1);   
            }   
        }   
        return filename;
    }
    
    /**
     * 通过文件路径截取文件名
     * @param filePath
     * @return
     */
    public static String getFileNameByWebPath(String filePath){
    	String filename = null;
    	if ((filePath != null) && (filePath.length() > 0)) {   
            int dot = filePath.lastIndexOf("\\");
            if ((dot >-1) && (dot < (filePath.length() - 1))) {   
                return filePath.substring(dot+1);   
            }   
        }   
        return filename;
    }
    /**
     * 通过文件路径截取文件名
     * @param filePath
     * @return
     */
    public static String getFileNameByHttpUrl(String filePath){
    	String filename = null;
    	if ((filePath != null) && (filePath.length() > 0)) {   
            int dot = filePath.lastIndexOf("/");   
            if ((dot >-1) && (dot < (filePath.length() - 1))) {   
                return filePath.substring(dot+1);   
            }   
        }   
        return filename;
    }
    
    /**
	 * 获取文件名(截取掉后辍名) 
	 * @param filename
	 * @return
	 */
    public static String getFileNameOfTruncateExtension(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return filename.substring(0,dot);   
            }   
        }   
        return filename;   
    }
	
    /**
	 * 随机生成数字ID
	 * @return
	 */
	public static Long getRandomId(int length) {
		StringBuilder random = new StringBuilder();
		if(length >= 8){
			random.append(String.valueOf(System.currentTimeMillis()).substring(0, length - 8));
			random.append(new Random().nextInt(9999999));
		}else{
			random.append(new Random().nextInt(9999999));
		}
		return Long.valueOf(random.toString());
	}
	
	 public static Object getMapValue(Map map,String key){
		 if(isEmpty(map)){
			 return "";
		 }
		 Object object = map.get(key);
		 if(null == object) return "";
		 return object;
	  }
	 
	 /**
	  * 获取UUID字符串
	  * @return
	  */
	 public static String getUUID(){
		 return getCode(12);
	 }
	
	/**
	 * 获取随机字符串
	 */
	public static String getCode(int length){
		//除去0 1 i o l I O L 
		String[] codes = {"2","3","4","5","6","7","8","9","a","b","c","d","e",
				"f","g","h","j","k","m","n","p","q","r","s","t",
				"u","v","w","x","y","z","A","B","C","D","E",
				"F","G","H","J","K","M","N","P","Q","R",
				"S","T","U","V","W","X","Y","Z"};
		StringBuilder builder = new StringBuilder();
		for(int i = 0;i < length;i++){
			builder.append(codes[(int)(Math.random() * codes.length)]);
		}
		return builder.toString();
	}
	
	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null) {
			String[] ipArray = ip.split(",");
			if (ipArray != null && ipArray.length > 1) {
				ip = ipArray[0];
			}
		}

		return ip;
	}
	
	
	final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();
	static {
		zoneNum.put(11, "北京");
		zoneNum.put(12, "天津");
		zoneNum.put(13, "河北");
		zoneNum.put(14, "山西");
		zoneNum.put(15, "内蒙古");
		zoneNum.put(21, "辽宁");
		zoneNum.put(22, "吉林");
		zoneNum.put(23, "黑龙江");
		zoneNum.put(31, "上海");
		zoneNum.put(32, "江苏");
		zoneNum.put(33, "浙江");
		zoneNum.put(34, "安徽");
		zoneNum.put(35, "福建");
		zoneNum.put(36, "江西");
		zoneNum.put(37, "山东");
		zoneNum.put(41, "河南");
		zoneNum.put(42, "湖北");
		zoneNum.put(43, "湖南");
		zoneNum.put(44, "广东");
		zoneNum.put(45, "广西");
		zoneNum.put(46, "海南");
		zoneNum.put(50, "重庆");
		zoneNum.put(51, "四川");
		zoneNum.put(52, "贵州");
		zoneNum.put(53, "云南");
		zoneNum.put(54, "西藏");
		zoneNum.put(61, "陕西");
		zoneNum.put(62, "甘肃");
		zoneNum.put(63, "青海");
		zoneNum.put(64, "新疆");
		zoneNum.put(71, "台湾");
		zoneNum.put(81, "香港");
		zoneNum.put(82, "澳门");
		zoneNum.put(91, "外国");
	}
	final static int[] PARITYBIT = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
	final static int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	
	/**
	 * 身份证验证 *
	 * @param s 号码内容 *
	 * @return 是否有效 null和"" 都是false
	 * 
	 **/
	public static boolean isIdcard(String s) {
		if (s == null || (s.length() != 15 && s.length() != 18))
			return false;
		final char[] cs = s.toUpperCase().toCharArray(); // 校验位数
		int power = 0;
		for (int i = 0; i < cs.length; i++) {
			if (i == cs.length - 1 && cs[i] == 'X')
				break;// 最后一位可以 是X或x
			if (cs[i] < '0' || cs[i] > '9')
				return false;
			if (i < cs.length - 1) {
				power += (cs[i] - '0') * POWER_LIST[i];
			}
		} // 校验区位码
		if (!zoneNum.containsKey(Integer.valueOf(s.substring(0, 2)))) {
			return false;
		} // 校验年份
		String year = s.length() == 15 ? "19" + s.substring(6, 8) : s.substring(6, 10);
		final int iyear = Integer.parseInt(year);
		if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
			return false;// 1900年的PASS，超过今年的PASS
		// 校验月份
		String month = s.length() == 15 ? s.substring(8, 10) : s.substring(10, 12);
		final int imonth = Integer.parseInt(month);
		if (imonth < 1 || imonth > 12) {
			return false;
		}
		// 校验天数
		String day = s.length() == 15 ? s.substring(10, 12) : s.substring(12, 14);
		final int iday = Integer.parseInt(day);
		if (iday < 1 || iday > 31)
			return false;
		// 校验一个合法的年月日
		if (!validate(iyear, imonth, iday))
			return false;
		// 校验"校验码"
		if (s.length() == 15)
			return true;
		return cs[cs.length - 1] == PARITYBIT[power % 11];
	}

	private static boolean validate(int year, int imonth, int iday) {
		// 比如考虑闰月，大小月等
		return true;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isValidIdcard(String s) {
		if(null == s || "".equals(s))
			return false;
		if(s.length() < 18 && isNumeric(s)){
			return true;
		}
		if(s.length() == 18){
			return isIdcard(s);
		}
		return false;
	}
	
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		List<String> ml = new ArrayList<String>();
		map1.put("ddd", ml);
		Integer it = null;
		//map1.get("ddd").toStrin+g();
		System.out.println("===="+isNumeric("121r"));
		//System.out.println("===="+getCode(12));
		//map.get("ddd").toString();
		
	}

}
