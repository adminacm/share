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
	 *　時刻フォーマットhhnn形式を表す文字列
	 */
	private static final String HHNN = "HHmm";
	
	/**
	 * 　時間フォーマット
	 * 
	 * @param time
	 *            時間（HHmm）
	 * @return 表示用時間（HH:mm）
	 */
	public static String formatTime(String time) {

		String result = StringUtils.EMPTY;
		
		if (!time.isEmpty()) {
			// タイムのフォーマット
			String hour = time.substring(0, 2);
			String minute = time.substring(2);
			result = hour.concat(CommonConstant.COLON_HANKAKU).concat(minute);
		}

		return result;
	}

	/**
	 * 日付チェック
	 * 
	 * @param date
	 *            日付
	 * @return チェック結果
	 */
	public static boolean isValidDate(String date, String format) {

		// 日付フォーマット
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		try {
			// 日付変換
			dateFormat.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 時刻のフォマーとチェック：hhnn
	 * 
	 * @param time
	 *            時刻
	 * @return チェック結果
	 * 			true:時刻は「hhnn」の時刻です
	 * 			false:時刻は「hhnn」以外、または、時刻以外
	 */
	public static boolean isTimeHHnn(String date) {

		// 日付フォーマット
		SimpleDateFormat dateFormat = new SimpleDateFormat(HHNN);
		dateFormat.setLenient(false);
		try {
			// 日付変換
			dateFormat.parse(date);
			return true;
		} catch (Exception e) {
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
	public static String formatDate(String yyyymmdd, String format)
			throws ParseException {

		String formatDate = "";

		// 日付が空白以外の場合
		if (yyyymmdd != null && !yyyymmdd.isEmpty()) {
			
			Date date = toDate(yyyymmdd);

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

		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.YYYYMMDD);
		temp_str = sdf.format(dt);

		return temp_str;

	}

	/**
	 * システム日付を取得し返却するメソッド<BR>
	 * 
	 * @param dt
	 *            判定用日付
	 * 
	 * @return 日付より取得の曜日名
	 */
	public static String getWeekOfDate(Date dt) {

		String[] weekDays = { "日", "月", "火", "水", "木", "金", "土" };

		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

		if (w < 0) {
			w = 0;
		}

		return weekDays[w];

	}
	
//	/**
//	 * 祝休日の判定を行う。 <BR>
//	 * 
//	 * @param yyyymmdd
//	 *            西暦年月日
//	 * @return boolean true:祝休日(金融機関休日含む) false :平日
//	 */
//	public static boolean isHoliday(String yyyymmdd) {
//		
//		// TODO:現存のDBより、詳細の処理はない
//		String weekday = null;
//		try {
//			weekday = getWeekOfDate(toDate(yyyymmdd));
//		} catch (ParseException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		if (StringUtils.equals("土", weekday) || StringUtils.equals("日", weekday)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	/**
	 * 時と分を取得。 <BR>
	 * 
	 * @param hhmm
	 *            ｈｈ：ｍｍの時間
	 * @return flag 0:hh 
	 * 		   flag 1:mm
	 * @throws ParseException 
	 */
	public static String getHourOrMinute(String hhmm, int flag) throws ParseException {
		
		String str = StringUtils.EMPTY;
		SimpleDateFormat format = new SimpleDateFormat("HHmm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(format.parse(hhmm));
		if (flag == 0) {
			str = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		} else {
			str = String.valueOf(cal.get(Calendar.MINUTE));
		}
		return CostStringUtils.addZeroForNum(str, 2);
	}

	/**
	 * 時と分を取得。 <BR>
	 * 
	 * @param hhmm
	 *            ｈｈ：ｍｍの時間
	 * @return flag 0:hh 
	 * 		   flag 1:mm
	 * @throws ParseException 
	 */
	public static String formatHHmm(String hhmm) throws ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("hh:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(format.parse(hhmm));
		String str1 = String.valueOf(cal.get(Calendar.HOUR));
		String str2 = String.valueOf(cal.get(Calendar.MINUTE));
		return CostStringUtils.addZeroForNum(str1, 2).concat( CostStringUtils.addZeroForNum(str2, 2));
	}
	

	/**
	 * 分のチェック。 <BR>
	 * 
	 * @param minute
	 *            分
	 * @return true:00,30
	 * 		   false:以外
	 */
	public static boolean isHalfHour(String minute) {
		
		// 分を入力されない場合
		if (StringUtils.isEmpty(minute)) {
			return false;
		}
		
		int minu = Integer.parseInt(minute);
		// 分が30単位でない
		if (minu % 30 != 0) {
			return false;
		}
		
		return true;
	}
	
}
