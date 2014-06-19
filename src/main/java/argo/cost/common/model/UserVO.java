package argo.cost.common.model;

import java.io.Serializable;

/**
 * <p>
 * ユーザ情報を表現します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1;

	// ********************
	// *****フィールド*****
	// ********************
	/**
	 * 社員番号です。
	 */
	private String userId;
	/**
	 * 対象社員番号です。
	 */
	private String taishoUserId;
	/**
	 * 表示用ユーザー名です。
	 */
	private String userName;
	/**
	 * 登録名です。
	 */
	private String loginMailAdress;
	
	/**
	 * パスワードです。
	 */
	private String password;
	/**
	 * 代理入力者IDです。
	 */
	private String dairishaId;
	/**
	 * 標準ｼﾌﾄです。
	 */
	private String standardShiftCd;
	/**
	 * 勤務開始時刻です。
	 */
	private String kinmuStartTime;
	/**
	 * 勤務終了時刻です。
	 */
	private String kinmuEndTime;
	/**
	 * 休業開始日です。
	 */
	private String kyugyoStartDate;
	/**
	 * 休業終了日です。
	 */
	private String kyugyoEndDate;
	/**
	 * 入社日です。
	 */
	private String nyushaDate;
	/**
	 * 退職日です。
	 */
	private String taisyokuDate;

	// ********************
	// **アクセスメッソド**
	// ********************
	/**
	 * ユーザIDを取得します。
	 *
	 * @return ユーザID
	 */
	public String getUserId() {

		return userId;
	}

	/**
	 * ユーザIDを設定します。
	 *
	 * @param ユーザID
	 */
	public void setUserId(String userId) {

		this.userId = userId;
	}
	/**
	 * 対象ユーザIDを取得します。
	 *
	 * @return 対象ユーザID
	 */
	public String getTaishoUserId() {
		return taishoUserId;
	}
	/**
	 * 対象ユーザIDを設定します。
	 *
	 * @param 対象ユーザID
	 */
	public void setTaishoUserId(String taishoUserId) {
		this.taishoUserId = taishoUserId;
	}

	/**
	 * ユーザ名称を取得します。
	 *
	 * @return ユーザ名称
	 */
	public String getUserName() {

		return userName;
	}

	/**
	 * ユーザ名称を設定します。
	 *
	 * @param ユーザ名称
	 */
	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginMailAdress() {
		return loginMailAdress;
	}

	public void setLoginMailAdress(String loginMailAdress) {
		this.loginMailAdress = loginMailAdress;
	}

	public String getDairishaId() {
		return dairishaId;
	}

	public void setDairishaId(String dairishaId) {
		this.dairishaId = dairishaId;
	}

	public String getStandardShiftCd() {
		return standardShiftCd;
	}

	public void setStandardShiftCd(String standardShiftCd) {
		this.standardShiftCd = standardShiftCd;
	}

	public String getKinmuStartTime() {
		return kinmuStartTime;
	}

	public void setKinmuStartTime(String kinmuStartTime) {
		this.kinmuStartTime = kinmuStartTime;
	}

	public String getKinmuEndTime() {
		return kinmuEndTime;
	}

	public void setKinmuEndTime(String kinmuEndTime) {
		this.kinmuEndTime = kinmuEndTime;
	}

	public String getKyugyoStartDate() {
		return kyugyoStartDate;
	}

	public void setKyugyoStartDate(String kyugyoStartDate) {
		this.kyugyoStartDate = kyugyoStartDate;
	}

	public String getKyugyoEndDate() {
		return kyugyoEndDate;
	}

	public void setKyugyoEndDate(String kyugyoEndDate) {
		this.kyugyoEndDate = kyugyoEndDate;
	}

	public String getNyushaDate() {
		return nyushaDate;
	}

	public void setNyushaDate(String nyushaDate) {
		this.nyushaDate = nyushaDate;
	}

	public String getTaisyokuDate() {
		return taisyokuDate;
	}

	public void setTaisyokuDate(String taisyokuDate) {
		this.taisyokuDate = taisyokuDate;
	}
	
}