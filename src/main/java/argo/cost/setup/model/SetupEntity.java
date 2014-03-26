package argo.cost.setup.model;

/**
 * 個人設定表情報
 * @author xxy
 *
 */
public class SetupEntity {
	
	/**
	 * 代理入力者
	 */
	private String agent;
	
	/**
	 * 標準ｼﾌﾄ
	 */
	private String standardShift;
	
	/**
	 * 勤務開始時刻
	 */
	private Integer workStart;
	
	/**
	 * 勤務終了時刻
	 */
	private Integer workEnd;
	
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

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getStandardShift() {
		return standardShift;
	}

	public void setStandardShift(String standardShift) {
		this.standardShift = standardShift;
	}

	public Integer getWorkStart() {
		return workStart;
	}

	public void setWorkStart(Integer workStart) {
		this.workStart = workStart;
	}

	public Integer getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(Integer workEnd) {
		this.workEnd = workEnd;
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
