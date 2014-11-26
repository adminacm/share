package argo.cost.makeKyuyoFile.model;

public class PayMagistrateCsvInfo {

	/**
	 * 社員番号
	 */
	private String userId;

	/**
	 * 超過勤務時間数（平日_割増）
	 */
	private String overWeekdayHours;

	/**
	 * 超過勤務時間数（休日）
	 */
	private String overHolidayHours;

	/**
	 * 超過勤務時間数（深夜）
	 */
	private String overNightHours;

	/**
	 * 超過勤務時間数（休日出勤振替分）
	 */
	private String overHolidayChangeWorkHours;

	/**
	 * 欠勤時間数
	 */
	private String absenceHours;

	/**
	 * 超過勤務時間数（平日_通常）
	 */
	private String overWeekdayNomalHours;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOverWeekdayHours() {
		return overWeekdayHours;
	}

	public void setOverWeekdayHours(String overWeekdayHours) {
		this.overWeekdayHours = overWeekdayHours;
	}

	public String getOverHolidayHours() {
		return overHolidayHours;
	}

	public void setOverHolidayHours(String overHolidayHours) {
		this.overHolidayHours = overHolidayHours;
	}

	public String getOverNightHours() {
		return overNightHours;
	}

	public void setOverNightHours(String overNightHours) {
		this.overNightHours = overNightHours;
	}

	public String getOverHolidayChangeWorkHours() {
		return overHolidayChangeWorkHours;
	}

	public void setOverHolidayChangeWorkHours(String overHolidayChangeWorkHours) {
		this.overHolidayChangeWorkHours = overHolidayChangeWorkHours;
	}

	public String getAbsenceHours() {
		return absenceHours;
	}

	public void setAbsenceHours(String absenceHours) {
		this.absenceHours = absenceHours;
	}

	public String getOverWeekdayNomalHours() {
		return overWeekdayNomalHours;
	}

	public void setOverWeekdayNomalHours(String overWeekdayNomalHours) {
		this.overWeekdayNomalHours = overWeekdayNomalHours;
	}
}
