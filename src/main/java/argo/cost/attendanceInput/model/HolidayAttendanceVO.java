package argo.cost.attendanceInput.model;

/**
 * 休日予定勤務情報
 */
public class HolidayAttendanceVO {
	
	/**
	 * 社員番号
	 */
	private String userId;
	/**
	 * 日付
	 */
	private String attendanceDate;
	/**
	 * 振替日
	 */
	private String furikaeDate;
	/**
	 * 勤務区分コード
	 */
	private String kinmuKbnCode;
	/**
	 * 勤務区分名称
	 */
	private String kinmuKbnName;
	/**
	 * プロジェクトコード
	 */
	private String projectId;
	/**
	 * プロジェクト名称
	 */
	private String projectName;
	/**
	 * 勤務開始時刻
	 */
	private String kinmuStartTime;
	/**
	 * 勤務終了時刻
	 */
	private String kinmuEndTime;
	/**
	 * 業務内容
	 */
	private String workDetail;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public String getFurikaeDate() {
		return furikaeDate;
	}
	public void setFurikaeDate(String furikaeDate) {
		this.furikaeDate = furikaeDate;
	}
	public String getKinmuKbnCode() {
		return kinmuKbnCode;
	}
	public void setKinmuKbnCode(String kinmuKbnCode) {
		this.kinmuKbnCode = kinmuKbnCode;
	}
	public String getKinmuKbnName() {
		return kinmuKbnName;
	}
	public void setKinmuKbnName(String kinmuKbnName) {
		this.kinmuKbnName = kinmuKbnName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getKinmuStartTime() {
		return kinmuStartTime;
	}
	public void setKinmuStartTime(String kinmuStartTime) {
		this.kinmuStartTime = kinmuStartTime;
	}
	public String getKinmuEndTime() {
		return kinmuEndTime;
	}
	public void setKinmuEndTime(String kinmuEndTime) {
		this.kinmuEndTime = kinmuEndTime;
	}
	public String getWorkDetail() {
		return workDetail;
	}
	public void setWorkDetail(String workDetail) {
		this.workDetail = workDetail;
	}
	
	
}
