package argo.cost.attendanceInput.model;

import java.util.List;

import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.ProjectMaster;

/**
 * 個人倦怠プロジェクト情報
 */
public class AttendanceProjectVO {
	
	/**
	 * プロジェクトID
	 */
	private String projectId;
	/**
	 * プロジェクトリスト
	 */
	private List<ProjectMaster> projectItemList;
	/**
	 * 作業ID
	 */
	private String workId;
	/**
	 * 作業リスト
	 */
	private List<ProjWorkMaster> workItemList;
	/**
	 * 作業時間数
	 */
	private Double hours;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<ProjectMaster> getProjectItemList() {
		return projectItemList;
	}

	public void setProjectItemList(List<ProjectMaster> projectItemList) {
		this.projectItemList = projectItemList;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public List<ProjWorkMaster> getWorkItemList() {
		return workItemList;
	}

	public void setWorkItemList(List<ProjWorkMaster> workItemList) {
		this.workItemList = workItemList;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}
	

}
