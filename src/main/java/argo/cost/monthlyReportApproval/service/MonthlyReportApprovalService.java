package argo.cost.monthlyReportApproval.service;

import java.text.ParseException;

import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalForm;


public interface MonthlyReportApprovalService {
	
	/**
	 * 処理状況を取得
	 * 
	 * @param form
	 *               画面情報
	 * @return
	 *        処理状況表示名
	 */
	void getStatusCode(MonthlyReportApprovalForm form);
	
	/**
	 * 月報承認データを取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        月報承認データ一覧リスト
	 * @throws ParseException 
	 */
	MonthlyReportApprovalForm getMonReApprovalList(MonthlyReportApprovalForm monthlyReportApprovalForm, String applyNo) throws ParseException;
	
//	/**
//	 * 【PJ別作業時間集計】情報を取得
//	 * 
//	 * @param applyNo
//	 *               申請番号
//	 * @return
//	 *        プロジェクト情報
//	 */
//	List<ProjWorkTimeManage> getProjectList(String applyNo);

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
