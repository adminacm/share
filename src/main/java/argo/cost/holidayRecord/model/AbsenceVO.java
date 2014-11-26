package argo.cost.holidayRecord.model;

/**
 * <p>
 * 欠勤情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class AbsenceVO {

	/**
	 * 日付
	 */
	private String absentDate;
	
	/**
	 * 日数 
	 */
	private String dayQuantity;
	
	/**
	 * 時間数
	 */
	private String hourQuantity;

	public String getAbsentDate() {
		return absentDate;
	}

	public void setAbsentDate(String absentDate) {
		this.absentDate = absentDate;
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
