package argo.cost.common.model.entity;

import java.io.Serializable;

/**
 * <p>
 * ｼﾌﾄ時刻表情報を表現します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public class ShiftTime implements Serializable {

	private static final long serialVersionUID = 1;

	// ********************
	// *****フィールド*****
	// ********************
	/**
	 * シフトコード
	 */
	private String shiftCd;

	/**
	 * 定時出勤時刻
	 */
	private String standSTime;
	
	/**
	 * 午前終了時刻
	 */
	private String amETime;
	
	/**
	 * 午後開始時刻
	 */
	private String pmSTime;
	
	/**
	 * 定時退勤時刻
	 */
	private String standEtime;
	
	/**
	 * 超勤開始時刻
	 */
	private String chokinSTime;

	// ********************
	// **アクセスメッソド**
	// ********************
	public String getShiftCd() {
		return shiftCd;
	}

	public void setShiftCd(String shiftCd) {
		this.shiftCd = shiftCd;
	}

	public String getStandSTime() {
		return standSTime;
	}

	public void setStandSTime(String standSTime) {
		this.standSTime = standSTime;
	}

	public String getAmETime() {
		return amETime;
	}

	public void setAmETime(String amETime) {
		this.amETime = amETime;
	}

	public String getPmSTime() {
		return pmSTime;
	}

	public void setPmSTime(String pmSTime) {
		this.pmSTime = pmSTime;
	}

	public String getStandEtime() {
		return standEtime;
	}

	public void setStandEtime(String standEtime) {
		this.standEtime = standEtime;
	}

	public String getChokinSTime() {
		return chokinSTime;
	}

	public void setChokinSTime(String chokinSTime) {
		this.chokinSTime = chokinSTime;
	}
}