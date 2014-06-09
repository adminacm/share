package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the status_master database table.
 * 
 */
@Entity
@Table(name="status_master")
@NamedQuery(name="StatusMaster.findAll", query="SELECT s FROM StatusMaster s")
public class StatusMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
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

	//bi-directional many-to-one association to ApprovalManage
	@OneToMany(mappedBy="statusMaster")
	private List<ApprovalManage> approvalManages;

	public StatusMaster() {
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
		approvalManage.setStatusMaster(this);

		return approvalManage;
	}

	public ApprovalManage removeApprovalManage(ApprovalManage approvalManage) {
		getApprovalManages().remove(approvalManage);
		approvalManage.setStatusMaster(null);

		return approvalManage;
	}

}