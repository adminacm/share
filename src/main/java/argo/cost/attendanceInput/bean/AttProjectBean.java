package argo.cost.attendanceInput.bean;

import java.util.List;

/**
 * 勤怠入力プロジェクト項目
 */
public class AttProjectBean {

	/**
	 * プロジェクト名
	 */
	private String projectId;

	private List<ProjectItem> projectList;
	/**
	 * 作業
	 */
	private String workId;
	
	private List<WorkItem> workList;
    
	/**
	 * 時間数
	 */
	private Double workingTime;
	
	//#################################
	/**
	 * プロジェクト名を取得する
	 */
	public String getProjectId() {
		return projectId;
	}
	
	/**
	 * プロジェクト名を設定する
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public Double getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(Double workingTime) {
		this.workingTime = workingTime;
	}

	public List<ProjectItem> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ProjectItem> projectList) {
		this.projectList = projectList;
	}

	public List<WorkItem> getWorkList() {
		return workList;
	}

	public void setWorkList(List<WorkItem> workList) {
		this.workList = workList;
	}

}
