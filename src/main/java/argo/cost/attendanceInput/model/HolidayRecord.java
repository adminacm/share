package argo.cost.attendanceInput.model;

/**
 * 休日勤務情報
 */
public class HolidayRecord {
	
	/**
	 * 社員番号
	 */
	private String userId;
	/**
	 * 日付
	 */
	private String date;
	/**
	 * 代休取得期限
	 */
	private String limitDate;
	/**
	 * 代休日
	 */
	private String exchangeDay;
	/**
	 * 振替申請日
	 */
	private String transferAppDay;
	/**
	 * 支払年月
	 */
	private String payOutYM;
	/**
	 * 集計処理フラグ
	 */
	private Integer processKbn;
	/**
	 * 集計処理日
	 */
	private String processDate;
	
	private String projectCode;
	
	private String projectName;
	
	private String workNaiyo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}

	public String getExchangeDay() {
		return exchangeDay;
	}

	public void setExchangeDay(String exchangeDay) {
		this.exchangeDay = exchangeDay;
	}

	public String getTransferAppDay() {
		return transferAppDay;
	}

	public void setTransferAppDay(String transferAppDay) {
		this.transferAppDay = transferAppDay;
	}

	public String getPayOutYM() {
		return payOutYM;
	}

	public void setPayOutYM(String payOutYM) {
		this.payOutYM = payOutYM;
	}

	public Integer getProcessKbn() {
		return processKbn;
	}

	public void setProcessKbn(Integer processKbn) {
		this.processKbn = processKbn;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getWorkNaiyo() {
		return workNaiyo;
	}

	public void setWorkNaiyo(String workNaiyo) {
		this.workNaiyo = workNaiyo;
	}
	
	
	

}
