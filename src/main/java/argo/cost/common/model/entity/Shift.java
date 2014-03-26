package argo.cost.common.model.entity;

import java.io.Serializable;

/**
 * <p>
 * ｼﾌﾄ表情報を表現します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public class Shift implements Serializable {

	private static final long serialVersionUID = 1;

	// ********************
	// *****フィールド*****
	// ********************
	/**
	 * シフトコード
	 */
	private String shiftCd;

	/**
	 * 時間帯コード
	 */
	private String timeZoneCode;
	
	/**
	 * 勤務フラグ
	 */
	private Integer workFlg;

	// ********************
	// **アクセスメッソド**
	// ********************
	
	public String getShiftCd() {
		return shiftCd;
	}

	public void setShiftCd(String shiftCd) {
		this.shiftCd = shiftCd;
	}

	public String getTimeZoneCode() {
		return timeZoneCode;
	}

	public void setTimeZoneCode(String timeZoneCode) {
		this.timeZoneCode = timeZoneCode;
	}

	public Integer getWorkFlg() {
		return workFlg;
	}

	public void setWorkFlg(Integer workFlg) {
		this.workFlg = workFlg;
	}
}