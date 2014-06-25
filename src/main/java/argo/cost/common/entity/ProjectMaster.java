package argo.cost.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the project_master database table.
 * 
 */
@Entity
@Table(name="project_master")
@NamedQuery(name="ProjectMaster.findAll", query="SELECT p FROM ProjectMaster p")
public class ProjectMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="project_code", unique=true, nullable=false, length=8)
	private String projectCode;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(name="eigyo_tanto_id", nullable=false, length=255)
	private String eigyoTantoId;

	@Column(name="end_date", nullable=false, length=8)
	private String endDate;

	@Column(name="phase_code", nullable=false, length=6)
	private String phaseCode;

	@Column(name="phase_name", nullable=false, length=255)
	private String phaseName;

	@Column(name="start_date", nullable=false, length=8)
	private String startDate;

	@Column(nullable=false, length=2)
	private String syukanka;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	@Column(name="user_id", nullable=false, length=20)
	private String userId;

	//bi-directional one-to-one association to ProjectBasic
	@OneToOne
	@JoinColumn(name="project_code", nullable=false, insertable=false, updatable=false)
	private ProjectBasic projectBasic;

	public ProjectMaster() {
	}

	public String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getEigyoTantoId() {
		return this.eigyoTantoId;
	}

	public void setEigyoTantoId(String eigyoTantoId) {
		this.eigyoTantoId = eigyoTantoId;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPhaseCode() {
		return this.phaseCode;
	}

	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

	public String getPhaseName() {
		return this.phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSyukanka() {
		return this.syukanka;
	}

	public void setSyukanka(String syukanka) {
		this.syukanka = syukanka;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedUserId() {
		return this.updatedUserId;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ProjectBasic getProjectBasic() {
		return this.projectBasic;
	}

	public void setProjectBasic(ProjectBasic projectBasic) {
		this.projectBasic = projectBasic;
	}

}