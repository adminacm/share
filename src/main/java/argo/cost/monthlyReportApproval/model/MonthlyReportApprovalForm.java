package argo.cost.monthlyReportApproval.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.model.AbstractForm;

/**
 * <p>
 * 月報承認画面フォーム情報クラス
 * </p>
 *
 */
public class MonthlyReportApprovalForm extends AbstractForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// ********************
	// ** フィールド **
	// ********************
	/**
	 * 処理状況
	 */
	private String proStatus;
	
	/**
	 * 月報承認一覧
	 */
	private List<MonthlyReportApprovalVo> monthlyReportApprovalList;
	
	/**
	 * プロジェクト情報
	 */
	private List<ProjectVo> projectList;
	
	//#################################
	//#################################

	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	public List<MonthlyReportApprovalVo> getMonthlyReportApprovalList() {
		return monthlyReportApprovalList;
	}

	public void setMonthlyReportApprovalList(List<MonthlyReportApprovalVo> monthlyReportApprovalList) {
		this.monthlyReportApprovalList = monthlyReportApprovalList;
	}

	public List<ProjectVo> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ProjectVo> projectList) {
		this.projectList = projectList;
	}
}
