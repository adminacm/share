package argo.cost.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the holiday_atendance_yotei database table.
 * 
 */
@Entity
@Table(name="holiday_atendance_yotei")
@NamedQuery(name="HolidayAtendanceYotei.findAll", query="SELECT h FROM HolidayAtendanceYotei h")
public class HolidayAtendanceYotei implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="atendance_book_date", nullable=false, length=8)
	private String atendanceBookDate;

	@Column(length=255)
	private String commont;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(name="furikae_date", length=8)
	private String furikaeDate;

	@Column(name="kinmu_end_time", nullable=false, length=4)
	private String kinmuEndTime;

	@Column(name="kinmu_start_time", nullable=false, length=4)
	private String kinmuStartTime;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	//bi-directional many-to-one association to ProjectMaster
	@ManyToOne
	@JoinColumn(name="project_code")
	private ProjectMaster projectMaster;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users users;

	//bi-directional many-to-one association to WorkDayKbnMaster
	@ManyToOne
	@JoinColumn(name="atendance_date_kbn_cd")
	private WorkDayKbnMaster workDayKbnMaster;

	public HolidayAtendanceYotei() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAtendanceBookDate() {
		return this.atendanceBookDate;
	}

	public void setAtendanceBookDate(String atendanceBookDate) {
		this.atendanceBookDate = atendanceBookDate;
	}

	public String getCommont() {
		return this.commont;
	}

	public void setCommont(String commont) {
		this.commont = commont;
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

	public String getFurikaeDate() {
		return this.furikaeDate;
	}

	public void setFurikaeDate(String furikaeDate) {
		this.furikaeDate = furikaeDate;
	}

	public String getKinmuEndTime() {
		return this.kinmuEndTime;
	}

	public void setKinmuEndTime(String kinmuEndTime) {
		this.kinmuEndTime = kinmuEndTime;
	}

	public String getKinmuStartTime() {
		return this.kinmuStartTime;
	}

	public void setKinmuStartTime(String kinmuStartTime) {
		this.kinmuStartTime = kinmuStartTime;
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


	public ProjectMaster getProjectMaster() {
		return this.projectMaster;
	}

	public void setProjectMaster(ProjectMaster projectMaster) {
		this.projectMaster = projectMaster;
	}

	public Users getUser() {
		return this.users;
	}

	public void setUser(Users users) {
		this.users = users;
	}

	public WorkDayKbnMaster getWorkDayKbnMaster() {
		return this.workDayKbnMaster;
	}

	public void setWorkDayKbnMaster(WorkDayKbnMaster workDayKbnMaster) {
		this.workDayKbnMaster = workDayKbnMaster;
	}

}