package argo.cost.common.utils;


import org.apache.commons.lang3.StringUtils;



/**
 * 文字列操作ライブラリ
 * 
 * @author cost
 * 
 */
public class CostStringUtils extends StringUtils {

	/**
	 * 
	 *　数字の前ゼロを追加する
	 * 
	 * @param  str
	 * 				数字
	 * @param strLength 
	 * 				数字長度
	 * 
	 * @return ユーザの月報情報
	 */
	 public static String addZeroForNum(String str, int strLength) {
	     int strLen = str.length();
	     StringBuffer sb = null;
	     while (strLen < strLength) {
	           sb = new StringBuffer();
	           sb.append("0").append(str);
	           str = sb.toString();
	           strLen = str.length();
	     }
	     return str;
	 }
	 
}