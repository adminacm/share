package argo.cost.holidayForOvertimeApproval.model;

import java.io.Serializable;

import argo.cost.common.model.AbstractForm;

/**
 * <p>
 * 超勤振替申請承認画面フォーム情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class HolidayForOvertimeApprovalForm extends AbstractForm implements Serializable {

	/**
	 * パジョンID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 申請番号
	 */
	private String applyNo;
	
	/**
	 * 処理状況
	 */
	private String proStatus;
	
	/**
	 * 日付
	 */
	private String date;
	
	/**
	 * 勤務区分
	 */
	private String workKbnName;
	
	/**
	 * 勤務開始時間
	 */
	private String workStartTime;
	
	/**
	 * 勤務終了時間
	 */
	private String workEndTime;
	
	/**
	 * 代休期限
	 */
	private String turnedHolidayEndDate;

	/**
	 * プロジェクト名
	 */
	private String projectName;
	
	/**
	 * 業務内容
	 */
	private String workDetail;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWorkKbnName() {
		return workKbnName;
	}

	public void setWorkKbnName(String workKbnName) {
		this.workKbnName = workKbnName;
	}

	public String getWorkStartTime() {
		return workStartTime;
	}

	public void setWorkStartTime(String workStartTime) {
		this.workStartTime = workStartTime;
	}

	public String getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}

	public String getTurnedHolidayEndDate() {
		return turnedHolidayEndDate;
	}

	public void setTurnedHolidayEndDate(String turnedHolidayEndDate) {
		this.turnedHolidayEndDate = turnedHolidayEndDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getWorkDetail() {
		return workDetail;
	}

	public void setWorkDetail(String workDetail) {
		this.workDetail = workDetail;
	}
}
