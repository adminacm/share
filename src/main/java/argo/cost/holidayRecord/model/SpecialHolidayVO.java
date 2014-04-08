package argo.cost.holidayRecord.model;

/**
 * <p>
 * 特別休暇情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class SpecialHolidayVO {

	/**
	 * 日付
	 */
	private String specialHolidayDate;
	
	/**
	 * 日数 
	 */
	private String dayQuantity;

	public String getSpecialHolidayDate() {
		return specialHolidayDate;
	}

	public void setSpecialHolidayDate(String specialHolidayDate) {
		this.specialHolidayDate = specialHolidayDate;
	}

	public String getDayQuantity() {
		return dayQuantity;
	}

	public void setDayQuantity(String dayQuantity) {
		this.dayQuantity = dayQuantity;
	}
	
}
