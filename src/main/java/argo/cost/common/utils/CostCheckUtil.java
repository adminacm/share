package argo.cost.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.expression.ParseException;

public class CostCheckUtil {
	
	/**
	 * Null或は空文字判断する
	 * 
	 * @param strInput
	 * @return true:ある　false:ありません
	 */
	public static boolean checkNullOrBlank(String strInput) {
		if ("".equals(strInput) || strInput == null) {
			return true;
		}

		return false;

	}
	
	/**
	 * Null或は空文字判断する
	 * 
	 * @param strInput
	 * @return true:ある　false:ありません
	 */
	public static boolean checkTimeFomathhhh(String strDate) {
		if (strDate != null) {

			SimpleDateFormat sim = new SimpleDateFormat("hhhh");
			sim.format(strDate);
			return true;
		}else {
			
			return false;
		}

	}

	/**
	 * String型の日付とシステムの日付を比較する
	 * 
	 * @param strDate
	 * @return true:日付が同じです　false:日付が同じではない
	 * @throws ParseException
	 */
	public static boolean compareToSystemDate(String strDate)
			throws ParseException {

		Date date = new Date();

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		// システム日付と入力した日付が同じの場合
		try {
			if (date.getTime() == bartDateFormat.parse(strDate).getTime()) {

				return true;

			}
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	/**
	 * String型の日付とシステムの日付を比較する
	 * 
	 * @param strDate
	 * @return true:日付が同じです　false:日付が同じではない
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static boolean checkTimehhhh(String strTime) throws ParseException {

		Date date = new Date(strTime);

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		// システム日付と入力した日付が同じの場合
		try {
			if (date.getTime() == bartDateFormat.parse(strTime).getTime()) {

				return true;

			}
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

}
