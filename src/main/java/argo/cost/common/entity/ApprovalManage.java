package argo.cost.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the approval_manage database table.
 * 
 */
@Entity
@Table(name="approval_manage")
@NamedQuery(name="ApprovalManage.findAll", query="SELECT a FROM ApprovalManage a")
public class ApprovalManage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="apply_no", unique=true, nullable=false, length=30)
	private String applyNo;

	@Column(name="app_ymd", nullable=false, length=8)
	private String appYmd;

	@Column(name="apply_detail", length=255)
	private String applyDetail;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(name="syori_ym", length=6)
	private String syoriYm;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	//bi-directional many-to-one association to ApplyKbnMaster
	@ManyToOne
	@JoinColumn(name="apply_kbn_code")
	private ApplyKbnMaster applyKbnMaster;

	//bi-directional many-to-one association to StatusMaster
	@ManyToOne
	@JoinColumn(name="apply_status_code")
	private StatusMaster statusMaster;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users users;

	//bi-directional many-to-one association to KintaiInfo
	@OneToMany(mappedBy="approvalManage1")
	private List<KintaiInfo> kintaiInfos1;

	//bi-directional many-to-one association to KintaiInfo
	@OneToMany(mappedBy="approvalManage2")
	private List<KintaiInfo> kintaiInfos2;

	public ApprovalManage() {
	}

	public String getApplyNo() {
		return this.applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getAppYmd() {
		return this.appYmd;
	}

	public void setAppYmd(String appYmd) {
		this.appYmd = appYmd;
	}
	
	public String getSyoriYm() {
		return syoriYm;
	}

	public void setSyoriYm(String syoriYm) {
		this.syoriYm = syoriYm;
	}

	public String getApplyDetail() {
		return this.applyDetail;
	}

	public void setApplyDetail(String applyDetail) {
		this.applyDetail = applyDetail;
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

	public ApplyKbnMaster getApplyKbnMaster() {
		return this.applyKbnMaster;
	}

	public void setApplyKbnMaster(ApplyKbnMaster applyKbnMaster) {
		this.applyKbnMaster = applyKbnMaster;
	}

	public StatusMaster getStatusMaster() {
		return this.statusMaster;
	}

	public void setStatusMaster(StatusMaster statusMaster) {
		this.statusMaster = statusMaster;
	}

	public Users getUser() {
		return this.users;
	}

	public void setUser(Users users) {
		this.users = users;
	}

	public List<KintaiInfo> getKintaiInfos1() {
		return this.kintaiInfos1;
	}

	public void setKintaiInfos1(List<KintaiInfo> kintaiInfos1) {
		this.kintaiInfos1 = kintaiInfos1;
	}

	public KintaiInfo addKintaiInfos1(KintaiInfo kintaiInfos1) {
		getKintaiInfos1().add(kintaiInfos1);
		kintaiInfos1.setApprovalManage1(this);

		return kintaiInfos1;
	}

	public KintaiInfo removeKintaiInfos1(KintaiInfo kintaiInfos1) {
		getKintaiInfos1().remove(kintaiInfos1);
		kintaiInfos1.setApprovalManage1(null);

		return kintaiInfos1;
	}

	public List<KintaiInfo> getKintaiInfos2() {
		return this.kintaiInfos2;
	}

	public void setKintaiInfos2(List<KintaiInfo> kintaiInfos2) {
		this.kintaiInfos2 = kintaiInfos2;
	}

	public KintaiInfo addKintaiInfos2(KintaiInfo kintaiInfos2) {
		getKintaiInfos2().add(kintaiInfos2);
		kintaiInfos2.setApprovalManage2(this);

		return kintaiInfos2;
	}

	public KintaiInfo removeKintaiInfos2(KintaiInfo kintaiInfos2) {
		getKintaiInfos2().remove(kintaiInfos2);
		kintaiInfos2.setApprovalManage2(null);

		return kintaiInfos2;
	}
}