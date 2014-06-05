package argo.cost.attendanceOnHolidayRecord.model;

/**
 * <p>
 * 休日勤務情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class HolidayWorkVO {

	/**
	 * 休日勤務日付
	 */
	private String holidayOverWorkDate;

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

	public String getHolidayOverWorkDate() {
		return holidayOverWorkDate;
	}

	public void setHolidayOverWorkDate(String holidayOverWorkDate) {
		this.holidayOverWorkDate = holidayOverWorkDate;
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
	
	

}
