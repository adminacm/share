package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the torihikisaki_ryaku_code database table.
 * 
 */
@Entity
@Table(name="torihikisaki_ryaku_code")
@NamedQuery(name="TorihikisakiRyakuCode.findAll", query="SELECT t FROM TorihikisakiRyakuCode t")
public class TorihikisakiRyakuCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="torihikisaki_ryaku_code", unique=true, nullable=false, length=4)
	private String torihikisakiRyakuCode;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(name="kokyaku_code", nullable=false, length=3)
	private String kokyakuCode;

	@Column(name="kokyaku_ryakusyo", nullable=false, length=255)
	private String kokyakuRyakusyo;

	@Column(name="torihikisaki_name", nullable=false, length=255)
	private String torihikisakiName;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	//bi-directional many-to-one association to ProjectBasic
	@OneToMany(mappedBy="torihikisakiRyakuCodeBean")
	private List<ProjectBasic> projectBasics;

	public TorihikisakiRyakuCode() {
	}

	public String getTorihikisakiRyakuCode() {
		return this.torihikisakiRyakuCode;
	}

	public void setTorihikisakiRyakuCode(String torihikisakiRyakuCode) {
		this.torihikisakiRyakuCode = torihikisakiRyakuCode;
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

	public String getKokyakuCode() {
		return this.kokyakuCode;
	}

	public void setKokyakuCode(String kokyakuCode) {
		this.kokyakuCode = kokyakuCode;
	}

	public String getKokyakuRyakusyo() {
		return this.kokyakuRyakusyo;
	}

	public void setKokyakuRyakusyo(String kokyakuRyakusyo) {
		this.kokyakuRyakusyo = kokyakuRyakusyo;
	}

	public String getTorihikisakiName() {
		return this.torihikisakiName;
	}

	public void setTorihikisakiName(String torihikisakiName) {
		this.torihikisakiName = torihikisakiName;
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

	public List<ProjectBasic> getProjectBasics() {
		return this.projectBasics;
	}

	public void setProjectBasics(List<ProjectBasic> projectBasics) {
		this.projectBasics = projectBasics;
	}

	public ProjectBasic addProjectBasic(ProjectBasic projectBasic) {
		getProjectBasics().add(projectBasic);
		projectBasic.setTorihikisakiRyakuCodeBean(this);

		return projectBasic;
	}

	public ProjectBasic removeProjectBasic(ProjectBasic projectBasic) {
		getProjectBasics().remove(projectBasic);
		projectBasic.setTorihikisakiRyakuCodeBean(null);

		return projectBasic;
	}

}