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

	@Column(name="apply_ymd")
	private String applyYmd;

	@Column(name="approved_ymd")
	private String approvedYmd;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="syori_ym")
	private String syoriYm;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	@Version
	@Column(name="version")
	private Integer version;
	
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

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="apply_user_id")
	private Users applicantUser;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="approve_user_id")
	private Users approveUser;

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

	public String getApplyDetail() {
		return this.applyDetail;
	}

	public void setApplyDetail(String applyDetail) {
		this.applyDetail = applyDetail;
	}

	public String getApplyYmd() {
		return this.applyYmd;
	}

	public void setApplyYmd(String applyYmd) {
		this.applyYmd = applyYmd;
	}

	public String getApprovedYmd() {
		return this.approvedYmd;
	}

	public void setApprovedYmd(String approvedYmd) {
		this.approvedYmd = approvedYmd;
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

	public String getSyoriYm() {
		return this.syoriYm;
	}

	public void setSyoriYm(String syoriYm) {
		this.syoriYm = syoriYm;
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

	public void setUser(Users user) {
		this.users = user;
	}

	public Users getApplicantUser() {
		return this.applicantUser;
	}

	public void setApplicantUser(Users applicantUser) {
		this.applicantUser = applicantUser;
	}

	public Users getApproveUser() {
		return this.approveUser;
	}

	public void setApproveUser(Users approveUser) {
		this.approveUser = approveUser;
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

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}