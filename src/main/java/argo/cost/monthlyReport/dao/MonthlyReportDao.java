package argo.cost.monthlyReport.dao;


/**
 * 月報画面Dao
 * 報画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface MonthlyReportDao {
	
	
	/**
	 * ユーザ最後の月報提出日付を取得
	 * 
	 * @param userId
	 *               社員番号
	 * @return
	 *        ユーザ最後の月報提出日付
	 */
	String getUserLatestShinseiMonth(String userId);
}
