package argo.cost.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the apply_kbn_master database table.
 * 
 */
@Entity
@Table(name="apply_kbn_master")
@NamedQuery(name="ApplyKbnMaster.findAll", query="SELECT a FROM ApplyKbnMaster a")
public class ApplyKbnMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=2)
	private String code;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(nullable=false, length=255, unique=true)
	private String name;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	//bi-directional many-to-one association to ApprovalManage
	@OneToMany(mappedBy="applyKbnMaster")
	private List<ApprovalManage> approvalManages;

	public ApplyKbnMaster() {
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

	public List<ApprovalManage> getApprovalManages() {
		return this.approvalManages;
	}

	public void setApprovalManages(List<ApprovalManage> approvalManages) {
		this.approvalManages = approvalManages;
	}

	public ApprovalManage addApprovalManage(ApprovalManage approvalManage) {
		getApprovalManages().add(approvalManage);
		approvalManage.setApplyKbnMaster(this);

		return approvalManage;
	}

	public ApprovalManage removeApprovalManage(ApprovalManage approvalManage) {
		getApprovalManages().remove(approvalManage);
		approvalManage.setApplyKbnMaster(null);

		return approvalManage;
	}

}