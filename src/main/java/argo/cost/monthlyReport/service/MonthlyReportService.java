package argo.cost.monthlyReport.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import argo.cost.monthlyReport.model.MonthlyReportInfo;
import argo.cost.monthlyReport.model.ProjectVo;

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
	 * ユーザの月報情報を取得し、月報リストに設定する
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 */
	void setUserMonthReport(String userId, String date, List<MonthlyReportInfo> monthList);
	
	/**
	 * 【PJ別作業時間集計】情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 * @return
	 *        プロジェクト情報
	 */
	List<ProjectVo> getProjectList(String userId, String date);

}
