package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the proj_work_master database table.
 * 
 */
@Entity
@Table(name="proj_work_master")
@NamedQuery(name="ProjWorkMaster.findAll", query="SELECT p FROM ProjWorkMaster p")
public class ProjWorkMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private String code;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	private String name;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to HolidayAtendanceYotei
	@OneToMany(mappedBy="projWorkMaster")
	private List<HolidayAtendanceYotei> holidayAtendanceYoteis;

	//bi-directional many-to-one association to ProjWorkTimeManage
	@OneToMany(mappedBy="projWorkMaster")
	private List<ProjWorkTimeManage> projWorkTimeManages;

	public ProjWorkMaster() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
		holidayAtendanceYotei.setProjWorkMaster(this);

		return holidayAtendanceYotei;
	}

	public HolidayAtendanceYotei removeHolidayAtendanceYotei(HolidayAtendanceYotei holidayAtendanceYotei) {
		getHolidayAtendanceYoteis().remove(holidayAtendanceYotei);
		holidayAtendanceYotei.setProjWorkMaster(null);

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
		projWorkTimeManage.setProjWorkMaster(this);

		return projWorkTimeManage;
	}

	public ProjWorkTimeManage removeProjWorkTimeManage(ProjWorkTimeManage projWorkTimeManage) {
		getProjWorkTimeManages().remove(projWorkTimeManage);
		projWorkTimeManage.setProjWorkMaster(null);

		return projWorkTimeManage;
	}

}