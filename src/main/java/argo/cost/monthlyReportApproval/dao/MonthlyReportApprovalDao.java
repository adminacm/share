package argo.cost.monthlyReportApproval.dao;

public interface MonthlyReportApprovalDao {

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
	String getLatestShinseiDate(String userId);
}
