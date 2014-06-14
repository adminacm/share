package argo.cost.monthlyReport.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.ListItemVO;
import argo.cost.common.model.MonthlyReportDispVO;

/**
 * <p>
 * 月報画面情報を記載します。
 * </p>
 *
 */
public class MonthlyReportForm extends AbstractForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ********************
	// ** フィールド **
	// ********************
	/**
	 * 氏名
	 */
	private String userCode;
	
	/**
	 * 氏名リスト
	 */
	private List<ListItemVO> userList;

	/**
	 * 処理状況
	 */
	private String proStatus;
	
	/**
	 * 年月
	 */
	private String yearMonth;

	/**
	 * 年月（表示）
	 */
	private String yearMonthHyoji;
	
	/**
	 * 月報一覧
	 */
	private List<MonthlyReportDispVO> mRList;
	
	/**
	 * プロジェクト名
	 */
	private String projName;
	
	/**
	 * プロジェクト管理
	 */
	private String projManagement;
	
	/**
	 * 基本設計
	 */
	private String basicDesign;
	
	/**
	 * 会議
	 */
	private String meeting;
	
	/**
	 * 事務処理・社内会議
	 */
	private String paperwork;
//	/**
//	 * プロジェクト情報
//	 */
//	private List<Project> projectList;

	//#################################
	//#################################
	/**
	 * 氏名を取得する
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * 氏名を設定する
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public List<MonthlyReportDispVO> getmRList() {
		return mRList;
	}

	public void setmRList(List<MonthlyReportDispVO> mRList) {
		this.mRList = mRList;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProjManagement() {
		return projManagement;
	}

	public void setProjManagement(String projManagement) {
		this.projManagement = projManagement;
	}

	public String getBasicDesign() {
		return basicDesign;
	}

	public void setBasicDesign(String basicDesign) {
		this.basicDesign = basicDesign;
	}

	public String getMeeting() {
		return meeting;
	}

	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}

	public String getPaperwork() {
		return paperwork;
	}

	public void setPaperwork(String paperwork) {
		this.paperwork = paperwork;
	}
	
	public List<ListItemVO> getUserList() {
		return userList;
	}

	public void setUserList(List<ListItemVO> userList) {
		this.userList = userList;
	}
	
	public String getYearMonthHyoji() {
		return yearMonthHyoji;
	}

	public void setYearMonthHyoji(String yearMonthHyoji) {
		this.yearMonthHyoji = yearMonthHyoji;
	}

//	public List<Project> getProjectList() {
//		return projectList;
//	}
//
//	public void setProjectList(List<Project> projectList) {
//		this.projectList = projectList;
//	}
}
