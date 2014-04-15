package argo.cost.monthlyReportApproval.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.ComDao;
import argo.cost.monthlyReportApproval.dao.MonthlyReportApprovalDao;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;
import argo.cost.monthlyReportApproval.model.ProjectVo;

@Service
public class MonthlyReportApprovalServiceImpl implements MonthlyReportApprovalService {

	/**
	 * 月報承認DAO
	 */
	@Autowired
	private MonthlyReportApprovalDao monApprovalDao;
	
	/**
	 * 共通DAO
	 */	
	@Autowired
	private ComDao comDao;

	/**
	 * 処理状況名を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況表示名
	 */
	@Override
	public String getStatus(String applyNo) {

		// 申請番号による、処理状況値を取得
		String statusValue = monApprovalDao.getStatus(applyNo);
		
		// 処理状況名
		String statusName = comDao.findStatusName(statusValue);
		
		return statusName;
	}

	/**
	 * 月報承認データを取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        月報承認データ一覧リスト
	 */
	@Override
	public List<MonthlyReportApprovalVo> getMonReApprovalList(String applyNo) {

		// 月報承認データを取得
		List<MonthlyReportApprovalVo> monthlyReportApprovalList = monApprovalDao.searchMonthReportApprovalList(applyNo);
		
		return monthlyReportApprovalList;
	}

	/**
	 * 【PJ別作業時間集計】情報を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        プロジェクト情報
	 */
	@Override
	public List<ProjectVo> getProjectList(String applyNo) {

		// プロジェクト情報を取得
		List<ProjectVo> projectList = monApprovalDao.searchProjectList(applyNo);
		
		return projectList;
		
	}

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
	@Override
	public String updateProStatus(String applyNo, String proStatus) {

		// プロジェクト情報を取得
		String updateFlg = monApprovalDao.updateProStatus(applyNo, proStatus);
		
		return updateFlg;
	}
}
