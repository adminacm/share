package argo.cost.monthlyReportApproval.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.common.model.ProjWorkTimeCountVO;

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
	 * 月報年月
	 */
	private String reportMoth;
	
	/**
	 * 処理状況
	 */
	private String proStatus;
	
	/**
	 * 処理状況名
	 */
	private String proStatusName;
	
	/**
	 *  戻り用画面のURL
	 */
	private String backUrl;
	
	/**
	 * 月報承認明細一覧
	 */
	private List<MonthlyReportDispVO> monthlyReportApprovalList;

	/**
	 * プロジェクト情報
	 */
	private List<ProjWorkTimeCountVO> projectList = new ArrayList<ProjWorkTimeCountVO>();

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

	public List<MonthlyReportDispVO> getMonthlyReportApprovalList() {
		return monthlyReportApprovalList;
	}

	public void setMonthlyReportApprovalList(
			List<MonthlyReportDispVO> monthlyReportApprovalList) {
		this.monthlyReportApprovalList = monthlyReportApprovalList;
	}

	public List<ProjWorkTimeCountVO> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ProjWorkTimeCountVO> projectList) {
		this.projectList = projectList;
	}

	public String getProStatusName() {
		return proStatusName;
	}

	public void setProStatusName(String proStatusName) {
		this.proStatusName = proStatusName;
	}

	public String getReportMoth() {
		return reportMoth;
	}

	public void setReportMoth(String reportMoth) {
		this.reportMoth = reportMoth;
	}
	
}
