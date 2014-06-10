package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


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
	@Column(name="apply_no")
	private String applyNo;

	@Column(name="apply_detail")
	private String applyDetail;
	
	@Column(name="app_ym")
	private String appYm;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
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
	@OneToMany(mappedBy="approvalManage")
	private List<KintaiInfo> kintaiInfos;

	public ApprovalManage() {
	}

	public String getApplyDetail() {
		return this.applyDetail;
	}

	public void setApplyDetail(String applyDetail) {
		this.applyDetail = applyDetail;
	}

	public String getApplyNo() {
		return this.applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getAppYm() {
		return this.appYm;
	}

	public void setAppYm(String appYm) {
		this.appYm = appYm;
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

	public List<KintaiInfo> getKintaiInfos() {
		return this.kintaiInfos;
	}

	public void setKintaiInfos(List<KintaiInfo> kintaiInfos) {
		this.kintaiInfos = kintaiInfos;
	}

	public KintaiInfo addKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().add(kintaiInfo);
		kintaiInfo.setApprovalManage(this);

		return kintaiInfo;
	}

	public KintaiInfo removeKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().remove(kintaiInfo);
		kintaiInfo.setApprovalManage(null);

		return kintaiInfo;
	}

}