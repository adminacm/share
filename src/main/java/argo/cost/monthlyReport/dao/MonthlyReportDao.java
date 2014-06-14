package argo.cost.monthlyReport.dao;


/**
 * 月報画面Dao
 * 報画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface MonthlyReportDao {
	
	
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
}
