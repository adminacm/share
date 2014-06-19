package argo.cost.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the shift_jikoku database table.
 * 
 */
@Entity
@Table(name="shift_jikoku")
public class ShiftJikoku implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="shift_code", unique=true, nullable=false, length=6)
	private String shiftCode;

	@Column(name="am_end_time", nullable=false, length=4)
	private String amEndTime;

	@Column(name="chokin_start_time", nullable=false, length=4)
	private String chokinStartTime;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(name="pm_start_time", nullable=false, length=4)
	private String pmStartTime;

	@Column(name="teiji_kinmu_time", nullable=false, length=4)
	private String teijiKinmuTime;

	@Column(name="teiji_taikin_time", nullable=false, length=4)
	private String teijiTaikinTime;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	//bi-directional many-to-one association to KintaiInfo
	@OneToMany(mappedBy="shiftJikoku")
	private List<KintaiInfo> kintaiInfos;

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

}