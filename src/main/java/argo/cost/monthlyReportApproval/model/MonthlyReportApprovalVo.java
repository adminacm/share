package argo.cost.monthlyReportApproval.model;

/**
 * 月報承認一覧情報
 */
public class MonthlyReportApprovalVo {
	
	/**
	 * 日付(表示用)
	 */
	private String day;
	
	/**
	 * 日付
	 */
	private String date;
	

	/**
	 * 曜日
	 */
	private String week;

	/**
	 * 勤務区分
	 */
	private String workKbn;

	/**
	 * 勤務区分名
	 */
	private String workKbnName;
	
	/**
	 * ｼﾌﾄ
	 */
	private String shift;
	
	/**
	 * 出勤
	 */
	private String workSTime;
	
	/**
	 * 退勤
	 */
	private String workETime;

	/**
	 * 休暇時間数
	 */
	private Double restHours;
	
	/**
	 * 勤務時間数
	 */
	private Double workHours;
	
	/**
	 * 超勤開始
	 */
	private String choSTime;
	
	/**
	 * 超勤終了
	 */
	private String choETime;
	
	/**
	 * 超勤平増
	 */
	private Double choWeekday;
	
	/**
	 * 超勤平常
	 */
	private Double choWeekdayNomal;
	
	/**
	 * 超勤休日
	 */
	private Double choHoliday;
	
	/**
	 * 超勤深夜
	 */
	private Double mNHours;
	
	/**
	 * ﾛｹｰｼｮﾝコード
	 */
	private String locationCode;

	/**
	 * ﾛｹｰｼｮﾝ名
	 */
	private String locationName;

	/**
	 * 合計行フラグ
	 */
	private Boolean totleFlg;
	
	
	
	//#################################
	//#################################
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
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

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getWorkSTime() {
		return workSTime;
	}

	public void setWorkSTime(String workSTime) {
		this.workSTime = workSTime;
	}

	public String getWorkETime() {
		return workETime;
	}

	public void setWorkETime(String workETime) {
		this.workETime = workETime;
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

	public String getChoSTime() {
		return choSTime;
	}

	public void setChoSTime(String choSTime) {
		this.choSTime = choSTime;
	}

	public String getChoETime() {
		return choETime;
	}

	public void setChoETime(String choETime) {
		this.choETime = choETime;
	}

	public Double getChoWeekday() {
		return choWeekday;
	}

	public void setChoWeekday(Double choWeekday) {
		this.choWeekday = choWeekday;
	}

	public Double getChoWeekdayNomal() {
		return choWeekdayNomal;
	}

	public void setChoWeekdayNomal(Double choWeekdayNomal) {
		this.choWeekdayNomal = choWeekdayNomal;
	}

	public Double getChoHoliday() {
		return choHoliday;
	}

	public void setChoHoliday(Double choHoliday) {
		this.choHoliday = choHoliday;
	}

	public Double getmNHours() {
		return mNHours;
	}

	public void setmNHours(Double mNHours) {
		this.mNHours = mNHours;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Boolean getTotleFlg() {
		return totleFlg;
	}

	public void setTotleFlg(Boolean totleFlg) {
		this.totleFlg = totleFlg;
	}
	
}
