package argo.cost.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	 /**
		 * 日付のフォーマットを行う。 <BR>
		 * 
		 * @param yyyymmdd
		 *            西暦年月日
		 * @return boolean true:祝休日(金融機関休日含む) false :平日
		 */
		public static String formatDate(String yyyymmdd, String format) throws ParseException {

			String formatDate = "";
			
			Date date = toDate(yyyymmdd);
			// 日付が空白以外の場合
			if (date != null) {
				
				// 日付フォーマット
				SimpleDateFormat sdfDate = new SimpleDateFormat(format);
				
				// 日付設定
				formatDate = sdfDate.format(date);
			}

			// フォーマット日付
			return formatDate;
			
		}

		/**
		 * 受け取った文字列を日付型に変換し返却するメソッド<BR>
		 * 　1. 引数の西暦年月日がYYYYMMDD形式であるかチェックを行う<BR>
		 * 　1-1. チェックの結果、YYYYMMDD形式でない場合、例外を投げる。<BR>
		 * 　1-2. チェックの結果、YYYYMMDD形式の場合、日付型に変換を行い返却する。<BR>
		 * 
		 * @param yyyymmdd
		 *            チェック対象となる西暦年月日
		 * @return 日付型に変換した引数
		 */
		public static Date toDate(String yyyymmdd) throws ParseException {

			// 動作パラメータがYYYYMMDD形式の場合、日付型に変換する。
			DateFormat format = new SimpleDateFormat(CommonConstant.YYYYMMDD);

			// あいまいチェック：オフ
			format.setLenient(false);
			return format.parse(yyyymmdd);
		}

		/**
		 * システム日付を取得し返却するメソッド<BR>
		 * 
		 * @return システム日付
		 */
		public static String getNowDate() {
			
			String temp_str=""; 
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.YYYYMMDD); 
			temp_str = sdf.format(dt);
			
			return temp_str;
			
		}
		
		/**
		 * システム日付を取得し返却するメソッド<BR>
		 * 
		 * @param dt 
		 * 			判定用日付
		 * 
		 * @return 日付より取得の曜日名
		 */
		public static String getWeekOfDate(Date dt) {
			
			String[] weekDays = {"日","月","火","水","木","金","土"};
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			
			if (w < 0) {
				w = 0;
			}
			
			return weekDays[w];
			
		}
}
