package argo.cost.monthlyReportStatusList.model;

import java.util.List;

import argo.cost.common.model.ListItemVO;

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
	private List<ListItemVO> yearMonthList;
	
	/**
	 * 所属
	 */
	private String affiliation;
	
	/**
	 * 所属リスト
	 */
	private List<ListItemVO> affiliationList;
	/**
	 * 状況
	 */
	private String status;
	
	/**
	 * 状況リスト
	 */
	private List<ListItemVO> statusList;
	
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

	public List<ListItemVO> getYearMonthList() {
		return yearMonthList;
	}

	public void setYearMonthList(List<ListItemVO> yearMonthList) {
		this.yearMonthList = yearMonthList;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public List<ListItemVO> getAffiliationList() {
		return affiliationList;
	}

	public void setAffiliationList(List<ListItemVO> affiliationList) {
		this.affiliationList = affiliationList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ListItemVO> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<ListItemVO> statusList) {
		this.statusList = statusList;
	}

	public List<MonthlyReportStatusListInfo> getmRSList() {
		return mRSList;
	}

	public void setmRSList(List<MonthlyReportStatusListInfo> mRSList) {
		this.mRSList = mRSList;
	}
}
