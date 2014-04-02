package argo.cost.attendanceInput.model;

public class ShiftInfo {

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
	
	
}
