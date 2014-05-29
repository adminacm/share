package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the work_day_kbn_master database table.
 * 
 */
@Entity
@Table(name="work_day_kbn_master")
@NamedQuery(name="WorkDayKbnMaster.findAll", query="SELECT w FROM WorkDayKbnMaster w")
public class WorkDayKbnMaster implements Serializable {
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

	@Column(name="work_day_flg")
	private BigDecimal workDayFlg;

	//bi-directional many-to-one association to HolidayAtendanceYotei
	@OneToMany(mappedBy="workDayKbnMaster")
	private List<HolidayAtendanceYotei> holidayAtendanceYoteis;

	//bi-directional many-to-one association to KintaiInfo
	@OneToMany(mappedBy="workDayKbnMaster")
	private List<KintaiInfo> kintaiInfos;

	public WorkDayKbnMaster() {
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

	public BigDecimal getWorkDayFlg() {
		return this.workDayFlg;
	}

	public void setWorkDayFlg(BigDecimal workDayFlg) {
		this.workDayFlg = workDayFlg;
	}

	public List<HolidayAtendanceYotei> getHolidayAtendanceYoteis() {
		return this.holidayAtendanceYoteis;
	}

	public void setHolidayAtendanceYoteis(List<HolidayAtendanceYotei> holidayAtendanceYoteis) {
		this.holidayAtendanceYoteis = holidayAtendanceYoteis;
	}

	public HolidayAtendanceYotei addHolidayAtendanceYotei(HolidayAtendanceYotei holidayAtendanceYotei) {
		getHolidayAtendanceYoteis().add(holidayAtendanceYotei);
		holidayAtendanceYotei.setWorkDayKbnMaster(this);

		return holidayAtendanceYotei;
	}

	public HolidayAtendanceYotei removeHolidayAtendanceYotei(HolidayAtendanceYotei holidayAtendanceYotei) {
		getHolidayAtendanceYoteis().remove(holidayAtendanceYotei);
		holidayAtendanceYotei.setWorkDayKbnMaster(null);

		return holidayAtendanceYotei;
	}

	public List<KintaiInfo> getKintaiInfos() {
		return this.kintaiInfos;
	}

	public void setKintaiInfos(List<KintaiInfo> kintaiInfos) {
		this.kintaiInfos = kintaiInfos;
	}

	public KintaiInfo addKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().add(kintaiInfo);
		kintaiInfo.setWorkDayKbnMaster(this);

		return kintaiInfo;
	}

	public KintaiInfo removeKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().remove(kintaiInfo);
		kintaiInfo.setWorkDayKbnMaster(null);

		return kintaiInfo;
	}

}