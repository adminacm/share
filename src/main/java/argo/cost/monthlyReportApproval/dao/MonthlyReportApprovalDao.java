package argo.cost.monthlyReportApproval.dao;

import java.util.List;

import argo.cost.common.model.entity.Project;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;


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
	List<MonthlyReportApprovalVo> searchMonthReportApprovalList(String applyNo);

	/**
	 * プロジェクト情報を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        プロジェクト情報リスト
	 */
	List<Project> searchProjectList(String applyNo);

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
	String updateProStatus(String applyNo, String proStatus);
}
