package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


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
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;

	@Column(name="atendance_book_date")
	private String atendanceBookDate;

	private String commont;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="furikae_date")
	private String furikaeDate;

	@Column(name="kinmu_end_time")
	private String kinmuEndTime;

	@Column(name="kinmu_start_time")
	private String kinmuStartTime;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to ProjWorkMaster
	@ManyToOne
	@JoinColumn(name="project_code")
	private ProjWorkMaster projWorkMaster;

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

	public ProjWorkMaster getProjWorkMaster() {
		return this.projWorkMaster;
	}

	public void setProjWorkMaster(ProjWorkMaster projWorkMaster) {
		this.projWorkMaster = projWorkMaster;
	}

	public WorkDayKbnMaster getWorkDayKbnMaster() {
		return this.workDayKbnMaster;
	}

	public void setWorkDayKbnMaster(WorkDayKbnMaster workDayKbnMaster) {
		this.workDayKbnMaster = workDayKbnMaster;
	}

}