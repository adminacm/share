package argo.cost.monthlyReport.model;

/**
 * 月報一覧情報
 */
public class MonthlyReportEntity {
	
	/**
	 * 社員番号
	 */
	private String userId;
	
	/**
	 * 日付
	 */
	private String workDate;
	
	/**
	 * 勤務区分
	 */
	private String workKbn;

	/**
	 * 勤務区分名
	 */
	private String workKbnName;
	
	/**
	 * シフトコード
	 */
	private String shiftCode;
	
	/**
	 * 勤務開始時刻
	 */
	private Integer workSTime;
	
	/**
	 * 勤務終了時刻
	 */
	private Integer workETime;
	
	/**
	 * 休暇欠勤区分
	 */
	private String restKbn;

	/**
	 * 休暇欠勤区分名
	 */
	private String restKbnName;
	
	/**
	 * 備考
	 */
	private String commant;
	
	/**
	 * 振替日
	 */
	private String repDate;
	
	/**
	 * 休暇欠勤時間数
	 */
	private Double restHours;

	/**
	 * 勤務時間数
	 */
	private Double workHours;

	/**
	 * 超勤開始時刻
	 */
	private Integer overSTime;

	/**
	 * 超勤終了時刻
	 */
	private Integer overETime;

	/**
	 * 超勤平日時間数
	 */
	private Double overHours;

	/**
	 * 超勤平日時間数_通常
	 */
	private Double overHoursOrdinary;

	/**
	 * 超勤休日時間数
	 */
	private Double overHoursHoliday;

	/**
	 * 深夜勤務時間数
	 */
	private Double overHoursNight;
	/**
	 * 取込処理日
	 */
	private String disposeDate;
	
	/**
	 * ﾛｹｰｼｮﾝ
	 */
	private String locationCode;

	/**
	 * ﾛｹｰｼｮﾝ名
	 */
	private String locationName;
	//#################################
	//#################################

	public String getUserId() {
		return userId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getWorkKbn() {
		return workKbn;
	}

	public void setWorkKbn(String workKbn) {
		this.workKbn = workKbn;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public Integer getWorkSTime() {
		return workSTime;
	}

	public void setWorkSTime(Integer workSTime) {
		this.workSTime = workSTime;
	}

	public Integer getWorkETime() {
		return workETime;
	}

	public void setWorkETime(Integer workETime) {
		this.workETime = workETime;
	}

	public String getRestKbn() {
		return restKbn;
	}

	public void setRestKbn(String restKbn) {
		this.restKbn = restKbn;
	}

	public String getCommant() {
		return commant;
	}

	public void setCommant(String commant) {
		this.commant = commant;
	}

	public String getRepDate() {
		return repDate;
	}

	public void setRepDate(String repDate) {
		this.repDate = repDate;
	}

	public Double getRestHours() {
		return restHours;
	}

	public void setRestHours(Double restHours) {
		this.restHours = restHours;
	}

	public Double getWorkHours() {
		return workHours;
	}

	public void setWorkHours(Double workHours) {
		this.workHours = workHours;
	}

	public Integer getOverSTime() {
		return overSTime;
	}

	public void setOverSTime(Integer overSTime) {
		this.overSTime = overSTime;
	}

	public Integer getOverETime() {
		return overETime;
	}

	public void setOverETime(Integer overETime) {
		this.overETime = overETime;
	}

	public Double getOverHours() {
		return overHours;
	}

	public void setOverHours(Double overHours) {
		this.overHours = overHours;
	}

	public Double getOverHoursOrdinary() {
		return overHoursOrdinary;
	}

	public void setOverHoursOrdinary(Double overHoursOrdinary) {
		this.overHoursOrdinary = overHoursOrdinary;
	}

	public Double getOverHoursHoliday() {
		return overHoursHoliday;
	}

	public void setOverHoursHoliday(Double overHoursHoliday) {
		this.overHoursHoliday = overHoursHoliday;
	}

	public Double getOverHoursNight() {
		return overHoursNight;
	}

	public void setOverHoursNight(Double overHoursNight) {
		this.overHoursNight = overHoursNight;
	}

	public String getDisposeDate() {
		return disposeDate;
	}

	public void setDisposeDate(String disposeDate) {
		this.disposeDate = disposeDate;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getWorkKbnName() {
		return workKbnName;
	}

	public void setWorkKbnName(String workKbnName) {
		this.workKbnName = workKbnName;
	}

	public String getRestKbnName() {
		return restKbnName;
	}

	public void setRestKbnName(String restKbnName) {
		this.restKbnName = restKbnName;
	}

}
