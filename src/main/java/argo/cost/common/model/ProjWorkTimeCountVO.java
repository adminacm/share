package argo.cost.common.model;


/**
 * 月報承認一覧詳細情報
 */
public class ProjWorkTimeCountVO {
	
	/**
	 * プロジェクト名前(漢字)
	 */
	private String projName;
	
	/**
	 * プロジェクト名前(コード)
	 */
	private String projCode;
	
	/**
	 * プロジェクト作業時間総計
	 */
	private Double prpjectWorkTotalHours;

	/**
	 * 作業時間内容名前
	 */
	private String workContentName;

	/**
	 * 作業時間数
	 */
	private Double workHoursNum;

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public Double getPrpjectWorkTotalHours() {
		return prpjectWorkTotalHours;
	}

	public void setPrpjectWorkTotalHours(Double prpjectWorkTotalHours) {
		this.prpjectWorkTotalHours = prpjectWorkTotalHours;
	}

	public String getWorkContentName() {
		return workContentName;
	}

	public void setWorkContentName(String workContentName) {
		this.workContentName = workContentName;
	}

	public Double getWorkHoursNum() {
		return workHoursNum;
	}

	public void setWorkHoursNum(Double workHoursNum) {
		this.workHoursNum = workHoursNum;
	}
	
	
	

}
