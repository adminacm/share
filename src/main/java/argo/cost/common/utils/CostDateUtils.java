package argo.cost.common.utils;

import java.text.SimpleDateFormat;

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
	 
	 /**
	  * 日付チェック
	  * 
	  * @param date
	  * 		日付
	  * @return
	  * 		チェック結果
	  */
	 public static boolean isValidDate(String date) {
		 
		// 日付フォーマット
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dateFormat.setLenient(false);
        try
        {
        	// 日付変換
			dateFormat.parse(date);
			return true;
         }
        catch (Exception e)
        {
            return false;
        }
	 }
}
