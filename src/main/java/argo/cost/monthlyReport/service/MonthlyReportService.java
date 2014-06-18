package argo.cost.monthlyReport.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.monthlyReport.model.MonthlyReportForm;

/**
 * 月報画面サービス
 * 報画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface MonthlyReportService {

	/**
	 * 年月取得処理
	 * 
	 * @param date 日付
	 * 
	 * @return フォーマット日付
	 */
	String getDateFormat(Date date);
	
	/**
	 * 月報一覧を取得
	 * 
	 * @param date 日付
	 * 
	 * @return 月報一覧
	 */
	List<MonthlyReportDispVO> getMonthyReportList(Date date);

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
	 * ユーザの月報情報を取得し、月報リストに設定する
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 * @throws ParseException 
	 */
	void setUserMonthReport(String userId, String date, MonthlyReportForm monthlyReportForm) throws ParseException;
	
	
	/**
	 * 申請状況更新
	 * 
	 * @param applyNo
	 *               申請番号
	 * @param proStatus
	 *                 申請状況
	 * @return
	 *        更新フラグ
	 */
	String getUserLatestShinseiMonth(String userId);
	
	/**
	 * 月報提出　　　　　　　　　　　　　　
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 * @throws ParseException 
	 */
	String monthyReportCommit(MonthlyReportForm monthlyReportForm) throws ParseException;
}
