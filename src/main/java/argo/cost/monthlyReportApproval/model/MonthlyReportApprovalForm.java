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
	 * 申請番号
	 */
	private String applyNo;
	
	/**
	 * 処理状況
	 */
	private String proStatus;
	
	/**
	 *  戻り用画面のURL
	 */
	private String backUrl;
	
	/**
	 * 月報承認明細一覧
	 */
	private List<MonthlyReportApprovalVo> monthlyReportApprovalList;

	/**
	 * プロジェクト情報
	 */
//	private List<Project> projectList;

	//#################################
	//#################################
	
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public List<MonthlyReportApprovalVo> getMonthlyReportApprovalList() {
		return monthlyReportApprovalList;
	}

	public void setMonthlyReportApprovalList(
			List<MonthlyReportApprovalVo> monthlyReportApprovalList) {
		this.monthlyReportApprovalList = monthlyReportApprovalList;
	}

//	public List<Project> getProjectList() {
//		return projectList;
//	}
//
//	public void setProjectList(List<Project> projectList) {
//		this.projectList = projectList;
//	}
}
