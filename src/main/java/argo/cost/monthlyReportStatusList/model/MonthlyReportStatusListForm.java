package argo.cost.monthlyReportStatusList.model;

import java.util.List;

import argo.cost.common.model.ListItem;

/**
 * <p>
 * 月報状況一覧画面情報を記載します。
 * </p>
 *
 */
public class MonthlyReportStatusListForm {
	
	// ********************
	// ** フィールド **
	// ********************
	/**
	 * 年月
	 */
	private String yearMonth;
	
	/**
	 * 年月リスト
	 */
	private List<ListItem> yearMonthList;
	
	/**
	 * 所属
	 */
	private String affiliation;
	
	/**
	 * 所属リスト
	 */
	private List<ListItem> affiliationList;
	/**
	 * 状況
	 */
	private String status;
	
	/**
	 * 状況リスト
	 */
	private List<ListItem> statusList;
	
	/**
	 * 月報状況一覧リスト
	 */
	private List<MonthlyReportStatusListInfo> mRSList;

	// ************************
	// ** アクセサメソッド　** 
	// ************************
	
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public List<ListItem> getYearMonthList() {
		return yearMonthList;
	}

	public void setYearMonthList(List<ListItem> yearMonthList) {
		this.yearMonthList = yearMonthList;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public List<ListItem> getAffiliationList() {
		return affiliationList;
	}

	public void setAffiliationList(List<ListItem> affiliationList) {
		this.affiliationList = affiliationList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ListItem> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<ListItem> statusList) {
		this.statusList = statusList;
	}

	public List<MonthlyReportStatusListInfo> getmRSList() {
		return mRSList;
	}

	public void setmRSList(List<MonthlyReportStatusListInfo> mRSList) {
		this.mRSList = mRSList;
	}
}
