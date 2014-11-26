package argo.cost.attendanceInput.model;

public class ShiftVO {

	/**
	 * シフトコード
	 */
	private String code;
	
	/**
	 * 定時出勤時刻
	 */
	private String startTime;
	
	/**
	 * 午前終了時刻
	 */
	private String amETime;
	
	/**
	 * 午後開始時刻
	 */
	private String fmSTime;
	
	/**
	 * 定時退勤時刻
	 */
	private String endTime;
	
	/**
	 * シフト超勤開始時刻
	 */
	private String chokinSTime;
	
	/**
	 * 定時出勤時刻(hhnn)
	 */
	private String startTimeStr;

	/**
	 * 午前終了時刻(hhnn)
	 */
	private String amETimeStr;
	
	/**
	 * 午後開始時刻(hhnn)
	 */
	private String fmSTimeStr;
	
	/**
	 * 定時退勤時刻(hhnn)
	 */
	private String endTimeStr;
	
	/**
	 * シフト超勤開始時刻(hhnn)
	 */
	private String chokinSTimeStr;
	/**
	 * 一日の勤務時間数
	 */
	private Double workHours;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getAmETime() {
		return amETime;
	}

	public void setAmETime(String amETime) {
		this.amETime = amETime;
	}

	public String getFmSTime() {
		return fmSTime;
	}

	public void setFmSTime(String fmSTime) {
		this.fmSTime = fmSTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getChokinSTime() {
		return chokinSTime;
	}

	public void setChokinSTime(String chokinSTime) {
		this.chokinSTime = chokinSTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getAmETimeStr() {
		return amETimeStr;
	}

	public void setAmETimeStr(String amETimeStr) {
		this.amETimeStr = amETimeStr;
	}

	public String getFmSTimeStr() {
		return fmSTimeStr;
	}

	public void setFmSTimeStr(String fmSTimeStr) {
		this.fmSTimeStr = fmSTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getChokinSTimeStr() {
		return chokinSTimeStr;
	}

	public void setChokinSTimeStr(String chokinSTimeStr) {
		this.chokinSTimeStr = chokinSTimeStr;
	}

	public Double getWorkHours() {
		return workHours;
	}

	public void setWorkHours(Double workHours) {
		this.workHours = workHours;
	}
	
	
}
