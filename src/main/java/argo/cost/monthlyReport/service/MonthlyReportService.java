package argo.cost.monthlyReport.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import argo.cost.monthlyReport.model.MonthlyReportInfo;

public interface MonthlyReportService {

	/**
	 * 年月取得処理
	 * 
	 * @param date 日付
	 * 
	 * @return フォーマット日付
	 */
	String ｇetDateFormat(Date date);
	
	/**
	 * 月報一覧を取得
	 * 
	 * @param date 日付
	 * 
	 * @return 月報一覧
	 */
	List<MonthlyReportInfo> getMonReList(Date date);

	/**
	 * 
	 * 年月の変換処理
	 * 
	 * @param changeFlg 変換フラグ
	 * @param date 日付
	 * @return 年月
	 */
	String changeYearMonth(String changeFlg, String date) throws ParseException;
	
	/**
	 * 
	 * ユーザの最後の月報提出年月を取得処理
	 * 
	 * @param userId ユーザID
	 * 
	 * @return 最後の月報提出年月
	 */
	String getUserMonth(String userId);
	
	/**
	 * 受け取った文字列を日付型に変換し返却するメソッド<BR>
	 * 　1. 引数の西暦年月日がYYYYMMDD形式であるかチェックを行う<BR>
	 * 　1-1. チェックの結果、YYYYMMDD形式でない場合、例外を投げる。<BR>
	 * 　1-2. チェックの結果、YYYYMMDD形式の場合、日付型に変換を行い返却する。<BR>
	 * 
	 * @param yyyymmdd
	 *            チェック対象となる西暦年月日
	 * @return 日付型に変換した引数
	 * @throws ParseException 
	 */
	Date toDate(String yyyymmdd) throws ParseException;
	
	/**
	 * ユーザの月報情報を取得し、月報リストに設定する
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 */
	void setUserMonthReport(String userId, String date, List<MonthlyReportInfo> monthList);
}
