package argo.cost.attendanceInput.model;

import java.util.List;

import argo.cost.common.model.ListItemVO;

/**
 * 個人倦怠プロジェクト情報
 */
public class AttendanceProject {
	
	/**
	 * プロジェクトID
	 */
	private String projectId;
	/**
	 * プロジェクトリスト
	 */
	private List<ListItemVO> projectItemList;
	/**
	 * 作業ID
	 */
	private String workId;
	/**
	 * 作業リスト
	 */
	private List<ListItemVO> workItemList;
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

	public List<ListItemVO> getProjectItemList() {
		return projectItemList;
	}

	public void setProjectItemList(List<ListItemVO> projectItemList) {
		this.projectItemList = projectItemList;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public List<ListItemVO> getWorkItemList() {
		return workItemList;
	}

	public void setWorkItemList(List<ListItemVO> workItemList) {
		this.workItemList = workItemList;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}
	

}
