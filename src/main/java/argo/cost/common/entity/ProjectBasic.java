package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the project_basic database table.
 * 
 */
@Entity
@Table(name="project_basic")
@NamedQuery(name="ProjectBasic.findAll", query="SELECT p FROM ProjectBasic p")
public class ProjectBasic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="project_code", unique=true, nullable=false, length=8)
	private String projectCode;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(name="naiji_seishiki_code", nullable=false, length=1)
	private String naijiSeishikiCode;

	@Column(name="pj_ranban", nullable=false, length=3)
	private String pjRanban;

	@Column(name="project_name", nullable=false, length=255)
	private String projectName;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	//bi-directional many-to-one association to HolidayAtendanceYotei
	@OneToMany(mappedBy="projectBasic")
	private List<HolidayAtendanceYotei> holidayAtendanceYoteis;

	//bi-directional many-to-one association to ProjWorkTimeManage
	@OneToMany(mappedBy="projectBasic")
	private List<ProjWorkTimeManage> projWorkTimeManages;

	//bi-directional many-to-one association to TorihikisakiRyakuCode
	@ManyToOne
	@JoinColumn(name="torihikisaki_ryaku_code", nullable=false)
	private TorihikisakiRyakuCode torihikisakiRyakuCodeBean;

	//bi-directional one-to-one association to ProjectMaster
	@OneToOne(mappedBy="projectBasic")
	private ProjectMaster projectMaster;

	public ProjectBasic() {
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

	public String getNaijiSeishikiCode() {
		return this.naijiSeishikiCode;
	}

	public void setNaijiSeishikiCode(String naijiSeishikiCode) {
		this.naijiSeishikiCode = naijiSeishikiCode;
	}

	public String getPjRanban() {
		return this.pjRanban;
	}

	public void setPjRanban(String pjRanban) {
		this.pjRanban = pjRanban;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public List<HolidayAtendanceYotei> getHolidayAtendanceYoteis() {
		return this.holidayAtendanceYoteis;
	}

	public void setHolidayAtendanceYoteis(List<HolidayAtendanceYotei> holidayAtendanceYoteis) {
		this.holidayAtendanceYoteis = holidayAtendanceYoteis;
	}

	public HolidayAtendanceYotei addHolidayAtendanceYotei(HolidayAtendanceYotei holidayAtendanceYotei) {
		getHolidayAtendanceYoteis().add(holidayAtendanceYotei);
		holidayAtendanceYotei.setProjectBasic(this);

		return holidayAtendanceYotei;
	}

	public HolidayAtendanceYotei removeHolidayAtendanceYotei(HolidayAtendanceYotei holidayAtendanceYotei) {
		getHolidayAtendanceYoteis().remove(holidayAtendanceYotei);
		holidayAtendanceYotei.setProjectBasic(null);

		return holidayAtendanceYotei;
	}

	public List<ProjWorkTimeManage> getProjWorkTimeManages() {
		return this.projWorkTimeManages;
	}

	public void setProjWorkTimeManages(List<ProjWorkTimeManage> projWorkTimeManages) {
		this.projWorkTimeManages = projWorkTimeManages;
	}

	public ProjWorkTimeManage addProjWorkTimeManage(ProjWorkTimeManage projWorkTimeManage) {
		getProjWorkTimeManages().add(projWorkTimeManage);
		projWorkTimeManage.setProjectBasic(this);

		return projWorkTimeManage;
	}

	public ProjWorkTimeManage removeProjWorkTimeManage(ProjWorkTimeManage projWorkTimeManage) {
		getProjWorkTimeManages().remove(projWorkTimeManage);
		projWorkTimeManage.setProjectBasic(null);

		return projWorkTimeManage;
	}

	public TorihikisakiRyakuCode getTorihikisakiRyakuCodeBean() {
		return this.torihikisakiRyakuCodeBean;
	}

	public void setTorihikisakiRyakuCodeBean(TorihikisakiRyakuCode torihikisakiRyakuCodeBean) {
		this.torihikisakiRyakuCodeBean = torihikisakiRyakuCodeBean;
	}

	public ProjectMaster getProjectMaster() {
		return this.projectMaster;
	}

	public void setProjectMaster(ProjectMaster projectMaster) {
		this.projectMaster = projectMaster;
	}

}