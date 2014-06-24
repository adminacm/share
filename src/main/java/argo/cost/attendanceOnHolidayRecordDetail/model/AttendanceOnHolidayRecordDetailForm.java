package argo.cost.attendanceOnHolidayRecordDetail.model;

import java.io.Serializable;

import argo.cost.common.model.AbstractForm;

/**
 * <p>
 * 休日出勤管理詳細画面フォーム情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class AttendanceOnHolidayRecordDetailForm extends AbstractForm implements Serializable {

	/**
	 * パジョンID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日付
	 */
	private String holidayWorkDate;
	/**
	 * 勤務区分
	 */
	private String workKbn;
	/**
	 * 勤務区分名
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
	 * 振替日
	 */
	private String exchangeDate;
	
	/**
	 * プロジェクト名
	 */
	private String projectName;
	
	/**
	 * 業務内容
	 */
	private String workDetail;
	
	/**
	 * 代休期限
	 */
	private String turnedHolidayEndDate;
	
	/**
	 * 代休日
	 */
	private String turnedHolidayDate;
	
	/**
	 * 超勤振替申請日
	 */
	private String overWorkTurnedReqDate;
	
	/**
	 * 超勤振替フラグ
	 */
	private Boolean overWorkFlg;

	public String getHolidayWorkDate() {
		return holidayWorkDate;
	}

	public void setHolidayWorkDate(String holidayWorkDate) {
		this.holidayWorkDate = holidayWorkDate;
	}

	public String getWorkKbn() {
		return workKbn;
	}

	public void setWorkKbn(String workKbn) {
		this.workKbn = workKbn;
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

	public String getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(String exchangeDate) {
		this.exchangeDate = exchangeDate;
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

	public String getTurnedHolidayEndDate() {
		return turnedHolidayEndDate;
	}

	public void setTurnedHolidayEndDate(String turnedHolidayEndDate) {
		this.turnedHolidayEndDate = turnedHolidayEndDate;
	}

	public String getTurnedHolidayDate() {
		return turnedHolidayDate;
	}

	public void setTurnedHolidayDate(String turnedHolidayDate) {
		this.turnedHolidayDate = turnedHolidayDate;
	}

	public String getOverWorkTurnedReqDate() {
		return overWorkTurnedReqDate;
	}

	public void setOverWorkTurnedReqDate(String overWorkTurnedReqDate) {
		this.overWorkTurnedReqDate = overWorkTurnedReqDate;
	}

	public Boolean getOverWorkFlg() {
		return overWorkFlg;
	}

	public void setOverWorkFlg(Boolean overWorkFlg) {
		this.overWorkFlg = overWorkFlg;
	}
}
