package argo.cost.monthlyReportApproval.service;

import java.util.List;

import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;
import argo.cost.monthlyReportApproval.model.ProjectVo;


public interface MonthlyReportApprovalService {
	
	/**
	 * 処理状況を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況表示名
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
	List<MonthlyReportApprovalVo> getMonthReportList(String applyNo);
	
	/**
	 * 【PJ別作業時間集計】情報を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        プロジェクト情報
	 */
	List<ProjectVo> getProjectList(String applyNo);
}
