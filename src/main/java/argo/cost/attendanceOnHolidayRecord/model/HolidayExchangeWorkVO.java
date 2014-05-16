package argo.cost.attendanceOnHolidayRecord.model;

/**
 * <p>
 * 休日振替勤務情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class HolidayExchangeWorkVO {

	/**
	 * 休日振替勤務日付
	 */
	private String holidayTurnedWorkingDate;

	/**
	 * 振替休日
	 */
	private String workingDayTurnedHolidayDate;

	public String getHolidayTurnedWorkingDate() {
		return holidayTurnedWorkingDate;
	}

	public void setHolidayTurnedWorkingDate(String holidayTurnedWorkingDate) {
		this.holidayTurnedWorkingDate = holidayTurnedWorkingDate;
	}

	public String getWorkingDayTurnedHolidayDate() {
		return workingDayTurnedHolidayDate;
	}

	public void setWorkingDayTurnedHolidayDate(String workingDayTurnedHolidayDate) {
		this.workingDayTurnedHolidayDate = workingDayTurnedHolidayDate;
	}
}
