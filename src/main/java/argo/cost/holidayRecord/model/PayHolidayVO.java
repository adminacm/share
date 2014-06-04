package argo.cost.holidayRecord.model;

/**
 * <p>
 * 有給休暇情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class PayHolidayVO {

	/**
	 * 日付
	 */
	private String payHolidayDate;
	
	/**
	 * 休暇区分コード
	 */
	private String holidayKbnCode;
	
	/**
	 * 休暇区分
	 */
	private String holidayKbnName;
	
	/**
	 * 日数 
	 */
	private String dayQuantity;
	
	/**
	 * 時間数
	 */
	private String hourQuantity;

	public String getPayHolidayDate() {
		return payHolidayDate;
	}

	public void setPayHolidayDate(String payHolidayDate) {
		this.payHolidayDate = payHolidayDate;
	}

	public String getHolidayKbnCode() {
		return holidayKbnCode;
	}

	public void setHolidayKbnCode(String holidayKbnCode) {
		this.holidayKbnCode = holidayKbnCode;
	}

	public String getHolidayKbnName() {
		return holidayKbnName;
	}

	public void setHolidayKbnName(String holidayKbnName) {
		this.holidayKbnName = holidayKbnName;
	}

	public String getDayQuantity() {
		return dayQuantity;
	}

	public void setDayQuantity(String dayQuantity) {
		this.dayQuantity = dayQuantity;
	}

	public String getHourQuantity() {
		return hourQuantity;
	}

	public void setHourQuantity(String hourQuantity) {
		this.hourQuantity = hourQuantity;
	}
}
