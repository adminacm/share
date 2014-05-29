package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the proj_work_time_manage database table.
 * 
 */
@Entity
@Table(name="proj_work_time_manage")
@NamedQuery(name="ProjWorkTimeManage.findAll", query="SELECT p FROM ProjWorkTimeManage p")
public class ProjWorkTimeManage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	@Column(name="work_times")
	private BigDecimal workTimes;

	//bi-directional many-to-one association to KintaiInfo
	@ManyToOne
	@JoinColumn(name="kinmu_id")
	private KintaiInfo kintaiInfo;

	//bi-directional many-to-one association to ProjWorkMaster
	@ManyToOne
	@JoinColumn(name="work_code")
	private ProjWorkMaster projWorkMaster;

	//bi-directional many-to-one association to ProjectMaster
	@ManyToOne
	@JoinColumn(name="project_code_id")
	private ProjectMaster projectMaster;

	public ProjWorkTimeManage() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public BigDecimal getWorkTimes() {
		return this.workTimes;
	}

	public void setWorkTimes(BigDecimal workTimes) {
		this.workTimes = workTimes;
	}

	public KintaiInfo getKintaiInfo() {
		return this.kintaiInfo;
	}

	public void setKintaiInfo(KintaiInfo kintaiInfo) {
		this.kintaiInfo = kintaiInfo;
	}

	public ProjWorkMaster getProjWorkMaster() {
		return this.projWorkMaster;
	}

	public void setProjWorkMaster(ProjWorkMaster projWorkMaster) {
		this.projWorkMaster = projWorkMaster;
	}

	public ProjectMaster getProjectMaster() {
		return this.projectMaster;
	}

	public void setProjectMaster(ProjectMaster projectMaster) {
		this.projectMaster = projectMaster;
	}

}