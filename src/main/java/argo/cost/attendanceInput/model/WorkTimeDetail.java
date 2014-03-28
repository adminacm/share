package argo.cost.attendanceInput.model;

/**
 * <p>
 * 就業データを表現します。
 * </p>
 *
 */
public class WorkTimeDetail {
	
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
	private String kinmuKbn;
	/**
	 * シフトコード
	 */
	private String shiftCode;
	
	/**
	 * 勤務開始時刻
	 */
	private String kinmuSTime;
	
	/**
	 * 勤務終了時刻
	 */
	private String kinmuEtime;
	/**
	 * 休暇欠勤区分
	 */
	private String sykaKetukinKbn;
	/**
	 * 備考
	 */
	private String bikou;

	/**
	 * 振替日
	 */
	private String furikaeDate;

	/**
	 * 休暇欠勤時間数
	 */
	private Double sykaKetukinhours;

	/**
	 * 勤務時間数
	 */
	private Double kinmuHours;

	/**
	 * 超勤開始時刻
	 */
	private String tyokinStime;
	/**
	 * 超勤終了時刻
	 */
	private String tyokinEtime;
	/**
	 * 超勤平日時間数
	 */
	private Double tyokinHeijiHours;
	/**
	 * 超勤平日時間数_通常
	 */
	private Double tyokinHeijiTujyoHours;
	/**
	 * 超勤休日時間数
	 */
	private Double tyokinKyujiHours;
	/**
	 * 深夜勤務時間数
	 */
	private Double synyaKinmuHours;
	/**
	 * 取込処理日
	 */
	private String syoriDate;
	/**
	 * 処理状態
	 */
	private String status;
	
	/**
	 * ロケーションコード
	 */
	private String locationCode;
	
	public String getUserId() {
		return userId;
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
	public String getKinmuKbn() {
		return kinmuKbn;
	}
	public void setKinmuKbn(String kinmuKbn) {
		this.kinmuKbn = kinmuKbn;
	}
	public String getShiftCode() {
		return shiftCode;
	}
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}
	public String getKinmuSTime() {
		return kinmuSTime;
	}
	public void setKinmuSTime(String kinmuSTime) {
		this.kinmuSTime = kinmuSTime;
	}
	public String getKinmuEtime() {
		return kinmuEtime;
	}
	public void setKinmuEtime(String kinmuEtime) {
		this.kinmuEtime = kinmuEtime;
	}
	public String getSykaKetukinKbn() {
		return sykaKetukinKbn;
	}
	public void setSykaKetukinKbn(String sykaKetukinKbn) {
		this.sykaKetukinKbn = sykaKetukinKbn;
	}
	public String getBikou() {
		return bikou;
	}
	public void setBikou(String bikou) {
		this.bikou = bikou;
	}
	public String getFurikaeDate() {
		return furikaeDate;
	}
	public void setFurikaeDate(String furikaeDate) {
		this.furikaeDate = furikaeDate;
	}
	public Double getSykaKetukinhours() {
		return sykaKetukinhours;
	}
	public void setSykaKetukinhours(Double sykaKetukinhours) {
		this.sykaKetukinhours = sykaKetukinhours;
	}
	public Double getKinmuHours() {
		return kinmuHours;
	}
	public void setKinmuHours(Double kinmuHours) {
		this.kinmuHours = kinmuHours;
	}
	public String getTyokinStime() {
		return tyokinStime;
	}
	public void setTyokinStime(String tyokinStime) {
		this.tyokinStime = tyokinStime;
	}
	public String getTyokinEtime() {
		return tyokinEtime;
	}
	public void setTyokinEtime(String tyokinEtime) {
		this.tyokinEtime = tyokinEtime;
	}
	public Double getTyokinHeijiHours() {
		return tyokinHeijiHours;
	}
	public void setTyokinHeijiHours(Double tyokinHeijiHours) {
		this.tyokinHeijiHours = tyokinHeijiHours;
	}
	public Double getTyokinHeijiTujyoHours() {
		return tyokinHeijiTujyoHours;
	}
	public void setTyokinHeijiTujyoHours(Double tyokinHeijiTujyoHours) {
		this.tyokinHeijiTujyoHours = tyokinHeijiTujyoHours;
	}
	public Double getTyokinKyujiHours() {
		return tyokinKyujiHours;
	}
	public void setTyokinKyujiHours(Double tyokinKyujiHours) {
		this.tyokinKyujiHours = tyokinKyujiHours;
	}
	public Double getSynyaKinmuHours() {
		return synyaKinmuHours;
	}
	public void setSynyaKinmuHours(Double synyaKinmuHours) {
		this.synyaKinmuHours = synyaKinmuHours;
	}
	public String getSyoriDate() {
		return syoriDate;
	}
	public void setSyoriDate(String syoriDate) {
		this.syoriDate = syoriDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	

}
