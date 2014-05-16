package argo.cost.monthlyReportStatusList.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 月報状況一覧画面フォーム情報クラス
 * </p>
 *
 */
public class MonthlyReportStatusListForm  extends AbstractForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// ********************
	// ** フィールド **
	// ********************
	/**
	 * 年
	 */
	private String year;
	
	/**
	 * 年リスト
	 */
	private List<ListItemVO> yearList;
	/**
	 * 月
	 */
	private String month;
	
	/**
	 * 月リスト
	 */
	private List<ListItemVO> monthList;
	
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
	private List<MonthlyReportStatusListVo> monthlyReportStatusList;

	// ************************
	// ** アクセサメソッド　** 
	// ************************
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<ListItemVO> getYearList() {
		return yearList;
	}

	public void setYearList(List<ListItemVO> yearList) {
		this.yearList = yearList;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<ListItemVO> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<ListItemVO> monthList) {
		this.monthList = monthList;
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

	public List<MonthlyReportStatusListVo> getMonthlyReportStatusList() {
		return monthlyReportStatusList;
	}

	public void setMonthlyReportStatusList(
			List<MonthlyReportStatusListVo> monthlyReportStatusList) {
		this.monthlyReportStatusList = monthlyReportStatusList;
	}
}
