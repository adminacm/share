package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the shift_jikoku database table.
 * 
 */
@Entity
@Table(name="shift_jikoku")
@NamedQuery(name="ShiftJikoku.findAll", query="SELECT s FROM ShiftJikoku s")
public class ShiftJikoku implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="shift_code")
	private String shiftCode;

	@Column(name="am_end_time")
	private String amEndTime;

	@Column(name="chokin_start_time")
	private String chokinStartTime;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="pm_start_time")
	private String pmStartTime;

	@Column(name="teiji_kinmu_time")
	private String teijiKinmuTime;

	@Column(name="teiji_taikin_time")
	private String teijiTaikinTime;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to KintaiInfo
	@OneToMany(mappedBy="shiftJikoku")
	private List<KintaiInfo> kintaiInfos;

	//bi-directional many-to-one association to ShiftInfo
	@OneToMany(mappedBy="shiftJikoku")
	private List<ShiftInfo> shiftInfos;

	public ShiftJikoku() {
	}

	public String getShiftCode() {
		return this.shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public String getAmEndTime() {
		return this.amEndTime;
	}

	public void setAmEndTime(String amEndTime) {
		this.amEndTime = amEndTime;
	}

	public String getChokinStartTime() {
		return this.chokinStartTime;
	}

	public void setChokinStartTime(String chokinStartTime) {
		this.chokinStartTime = chokinStartTime;
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

	public String getPmStartTime() {
		return this.pmStartTime;
	}

	public void setPmStartTime(String pmStartTime) {
		this.pmStartTime = pmStartTime;
	}

	public String getTeijiKinmuTime() {
		return this.teijiKinmuTime;
	}

	public void setTeijiKinmuTime(String teijiKinmuTime) {
		this.teijiKinmuTime = teijiKinmuTime;
	}

	public String getTeijiTaikinTime() {
		return this.teijiTaikinTime;
	}

	public void setTeijiTaikinTime(String teijiTaikinTime) {
		this.teijiTaikinTime = teijiTaikinTime;
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

	public List<KintaiInfo> getKintaiInfos() {
		return this.kintaiInfos;
	}

	public void setKintaiInfos(List<KintaiInfo> kintaiInfos) {
		this.kintaiInfos = kintaiInfos;
	}

	public KintaiInfo addKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().add(kintaiInfo);
		kintaiInfo.setShiftJikoku(this);

		return kintaiInfo;
	}

	public KintaiInfo removeKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().remove(kintaiInfo);
		kintaiInfo.setShiftJikoku(null);

		return kintaiInfo;
	}

	public List<ShiftInfo> getShiftInfos() {
		return this.shiftInfos;
	}

	public void setShiftInfos(List<ShiftInfo> shiftInfos) {
		this.shiftInfos = shiftInfos;
	}

	public ShiftInfo addShiftInfo(ShiftInfo shiftInfo) {
		getShiftInfos().add(shiftInfo);
		shiftInfo.setShiftJikoku(this);

		return shiftInfo;
	}

	public ShiftInfo removeShiftInfo(ShiftInfo shiftInfo) {
		getShiftInfos().remove(shiftInfo);
		shiftInfo.setShiftJikoku(null);

		return shiftInfo;
	}

}