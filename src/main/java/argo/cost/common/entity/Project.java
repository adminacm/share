package argo.cost.common.entity;

/**
 * プロジェクト情報
 */
public class Project {
	
	/**
	 * プロジェクトコード
	 */
	private String projCode;
	
	/**
	 * プロジェクト名
	 */
	private String projName;
	
	/**
	 * プロジェクト作業時間
	 */
	private Double projHours;
	
	/**
	 * プロジェクト管理
	 */
	private Double projManageHours;
	
	/**
	 * 基本設計
	 */
	private Double basicDesignHours;
	
	/**
	 * 会議
	 */
	private Double meetingHours;
	
	/**
	 * 事務処理・社内会議
	 */
	private Double projUnrelatedWorkHours;

	//#################################
	//#################################
	
	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public Double getProjHours() {
		return projHours;
	}

	public void setProjHours(Double projHours) {
		this.projHours = projHours;
	}

	public Double getProjManageHours() {
		return projManageHours;
	}

	public void setProjManageHours(Double projManageHours) {
		this.projManageHours = projManageHours;
	}

	public Double getBasicDesignHours() {
		return basicDesignHours;
	}

	public void setBasicDesignHours(Double basicDesignHours) {
		this.basicDesignHours = basicDesignHours;
	}

	public Double getMeetingHours() {
		return meetingHours;
	}

	public void setMeetingHours(Double meetingHours) {
		this.meetingHours = meetingHours;
	}

	public Double getProjUnrelatedWorkHours() {
		return projUnrelatedWorkHours;
	}

	public void setProjUnrelatedWorkHours(Double projUnrelatedWorkHours) {
		this.projUnrelatedWorkHours = projUnrelatedWorkHours;
	}
}
