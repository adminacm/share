package argo.cost.monthlyReportApproval.dao;

import java.util.List;

import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;
import argo.cost.monthlyReportApproval.model.ProjectVo;


public interface MonthlyReportApprovalDao {

	/**
	 * 処理状況値を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況値
	 */
	String getStatus(String applyNo);
	
	/**
	 * 月報承認データを取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        月報承認データ一覧リスト
	*/
	List<MonthlyReportApprovalVo> searchMonthReportList(String applyNo);

	/**
	 * プロジェクト情報を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        プロジェクト情報リスト
	 */
	List<ProjectVo> searchProjectList(String applyNo);
}
