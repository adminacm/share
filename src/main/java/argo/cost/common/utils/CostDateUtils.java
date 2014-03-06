package argo.cost.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import argo.cost.common.constant.CommonConstant;

/**
 * 日付操作ライブラリ
 * 
 * @author cost
 * 
 */
public class CostDateUtils extends DateUtils {
	
	 /**
	  *　数字を時間に変換する。
	  * @param time
	  * 			半角整数
	  * @return 表示用時間（HH：ｍｍ）
	  */
	 public static String formatIntegerToTime(Integer time) {
		 
		 String result = StringUtils.EMPTY;
		 String str;
		 String hour;
		 String minute;
		 if (time != null) {
			 // タイムのフォーマット
			 str = CostStringUtils.addZeroForNum(String.valueOf(time), 4);
			 hour = str.substring(0, 2);
			 minute = str.substring(2);
			 result = hour.concat(CommonConstant.COLON_HANKAKU).concat(minute);
		 }
		 
		 return result;
	 }
}
