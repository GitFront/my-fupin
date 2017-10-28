package com.aspire.birp.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	private static final String PASSWORD_CRYPT_KEY = "$_zj001_";
	  private static final String DES = "DES";

	  public static boolean isEmpty(String s)
	  {
	    if (s == null) return true;

	    return s.trim().equals("");
	  }

	  public static byte[] encrypt(byte[] src, byte[] key)
	    throws Exception
	  {
	    SecureRandom sr = new SecureRandom();

	    DESKeySpec dks = new DESKeySpec(key);

	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    SecretKey securekey = keyFactory.generateSecret(dks);

	    Cipher cipher = Cipher.getInstance("DES");

	    cipher.init(1, securekey, sr);

	    return cipher.doFinal(src);
	  }

	  public static byte[] decrypt(byte[] src, byte[] key)
	    throws Exception
	  {
	    SecureRandom sr = new SecureRandom();

	    DESKeySpec dks = new DESKeySpec(key);

	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    SecretKey securekey = keyFactory.generateSecret(dks);

	    Cipher cipher = Cipher.getInstance("DES");

	    cipher.init(2, securekey, sr);

	    return cipher.doFinal(src);
	  }

	  public static final String decrypt(String data)
	  {
	    try
	    {
	      return new String(decrypt(hex2byte(data.getBytes()), "$_zj001_".getBytes()));
	    }
	    catch (Exception localException) {
	    }
	    return null;
	  }

	  public static final String encrypt(String password)
	  {
	    try
	    {
	      return byte2hex(encrypt(password.getBytes(), "$_zj001_".getBytes()));
	    }
	    catch (Exception localException) {
	    }
	    return null;
	  }

	  public static String byte2hex(byte[] b)
	  {
	    String hs = "";
	    String stmp = "";

	    for (int n = 0; n < b.length; n++) {
	      stmp = Integer.toHexString(b[n] & 0xFF);
	      if (stmp.length() == 1)
	        hs = hs + "0" + stmp;
	      else {
	        hs = hs + stmp;
	      }
	    }
	    return hs.toUpperCase();
	  }

	  public static byte[] hex2byte(byte[] b) {
	    if (b.length % 2 != 0) {
	      throw new IllegalArgumentException("长度不是偶数");
	    }
	    byte[] b2 = new byte[b.length / 2];
	    for (int n = 0; n < b.length; n += 2) {
	      String item = new String(b, n, 2);
	      b2[(n / 2)] = (byte)Integer.parseInt(item, 16);
	    }

	    return b2;
	  }

	  public static boolean isNum(String str)
	  {
	    return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?)$");
	  }

	  public static void main(String[] arg) {
	    System.out.println(encrypt("gd@123"));
	    System.out.println(decrypt("a60ddd4393db83420dcf7e12c03a043ce2b4c2a1f389bc67e104e1fb"));
	  }

	  public static String replaceHtml(String html)
	  {
	    if (isBlank(html)) {
	      return "";
	    }
	    String regEx = "<.+?>";
	    Pattern p = Pattern.compile(regEx);
	    Matcher m = p.matcher(html);
	    String s = m.replaceAll("");
	    return s;
	  }

	  public static String abbr(String str, int length)
	  {
	    if (str == null)
	      return "";
	    try
	    {
	      StringBuilder sb = new StringBuilder();
	      int currentLength = 0;
	      for (char c : str.toCharArray()) {
	        currentLength += String.valueOf(c).getBytes("GBK").length;
	        if (currentLength <= length - 3) {
	          sb.append(c);
	        } else {
	          sb.append("...");
	          break;
	        }
	      }
	      return sb.toString();
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return "";
	  }

	  public static Double toDouble(Object val)
	  {
	    if (val == null)
	      return Double.valueOf(0.0D);
	    try
	    {
	      return Double.valueOf(trim(val.toString())); } catch (Exception e) {
	    }
	    return Double.valueOf(0.0D);
	  }

	  public static Float toFloat(Object val)
	  {
	    return Float.valueOf(toDouble(val).floatValue());
	  }

	  public static Long toLong(Object val)
	  {
	    return Long.valueOf(toDouble(val).longValue());
	  }

	  public static Integer toInteger(Object val)
	  {
	    return Integer.valueOf(toLong(val).intValue());
	  }

	  public static String getRemoteAddr(HttpServletRequest request)
	  {
	    String remoteAddr = request.getHeader("X-Real-IP");
	    if (isNotBlank(remoteAddr))
	      remoteAddr = request.getHeader("X-Forwarded-For");
	    else if (isNotBlank(remoteAddr))
	      remoteAddr = request.getHeader("Proxy-Client-IP");
	    else if (isNotBlank(remoteAddr)) {
	      remoteAddr = request.getHeader("WL-Proxy-Client-IP");
	    }
	    return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	  }
}
