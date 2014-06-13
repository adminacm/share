package argo.cost.setup.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
import argo.cost.common.model.AbstractForm;

/**
 * 個人設定画面情報FORM
 *
 * @author COST argo Corporation.
 */
public class SetupForm extends AbstractForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7059601126898226721L;

	/**
	 * 代理入力者コード
	 */
	private String agentCd;
	
	/**
	 * 代理入力者
	 */
	private String agentName;
	
	/**
	 * 代理入力者リスト
	 */
	private List<Users> agentList;
	
	/**
	 * 標準ｼﾌﾄリスト
	 */
	private String standardShift;
	
	/**
	 * 標準ｼﾌﾄ
	 */
	private List<ShiftJikoku> standardShiftList;
	
	/**
	 * 勤務開始時刻
	 */
	private String workStart;
	
	/**
	 * 勤務開始時刻（時）
	 */
	private String workStartH;
	
	/**
	 * 勤務開始時刻（分）
	 */
	private String workStartM;
	
	/**
	 * 勤務終了時刻
	 */
	private String workEnd;
	
	/**
	 * 勤務終了時刻（時）
	 */
	private String workEndH;
	
	/**
	 * 勤務終了時刻（分）
	 */
	private String workEndM;
	
	/**
	 * 入社日
	 */
	private String joinDate;
	
	/**
	 * 休業開始日
	 */
	private String holidayStart;
	
	/**
	 * 休業終了日
	 */
	private String holidayEnd;
	
	/**
	 * 退職日
	 */
	private String outDate;

	public String getAgentCd() {
		return agentCd;
	}

	public void setAgentCd(String agentCd) {
		this.agentCd = agentCd;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public List<Users> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<Users> agentList) {
		this.agentList = agentList;
	}

	public String getStandardShift() {
		return standardShift;
	}

	public void setStandardShift(String standardShift) {
		this.standardShift = standardShift;
	}

	public List<ShiftJikoku> getStandardShiftList() {
		return standardShiftList;
	}

	public void setStandardShiftList(List<ShiftJikoku> standardShiftList) {
		this.standardShiftList = standardShiftList;
	}

	public String getWorkStart() {
		return workStart;
	}

	public void setWorkStart(String workStart) {
		this.workStart = workStart;
	}

	public String getWorkStartH() {
		return workStartH;
	}

	public void setWorkStartH(String workStartH) {
		this.workStartH = workStartH;
	}

	public String getWorkStartM() {
		return workStartM;
	}

	public void setWorkStartM(String workStartM) {
		this.workStartM = workStartM;
	}

	public String getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(String workEnd) {
		this.workEnd = workEnd;
	}

	public String getWorkEndH() {
		return workEndH;
	}

	public void setWorkEndH(String workEndH) {
		this.workEndH = workEndH;
	}

	public String getWorkEndM() {
		return workEndM;
	}

	public void setWorkEndM(String workEndM) {
		this.workEndM = workEndM;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getHolidayStart() {
		return holidayStart;
	}

	public void setHolidayStart(String holidayStart) {
		this.holidayStart = holidayStart;
	}

	public String getHolidayEnd() {
		return holidayEnd;
	}

	public void setHolidayEnd(String holidayEnd) {
		this.holidayEnd = holidayEnd;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	
	
}