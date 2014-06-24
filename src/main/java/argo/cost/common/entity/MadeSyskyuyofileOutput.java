package argo.cost.common.entity;


import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the made_syskyuyofile_output database table.
 * 
 */
@Entity
@Table(name="made_syskyuyofile_output")
@NamedQuery(name="MadeSyskyuyofileOutput.findAll", query="SELECT m FROM MadeSyskyuyofileOutput m")
public class MadeSyskyuyofileOutput implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="made_kyuyofile_name", unique=true, nullable=false, length=40)
	private String madeKyuyofileName;

	@Column(name="apply_deal_year_month", nullable=false, length=6)
	private String applyDealYearMonth;

	@Column(name="file_created_timestamp", nullable=false)
	private Timestamp fileCreatedTimestamp;

	@Column(name="kyuyofile_naiyo")
	private byte[] kyuyofileNaiyo;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="file_created_user_id", nullable=false)
	private Users users;

	public MadeSyskyuyofileOutput() {
	}

	public String getMadeKyuyofileName() {
		return this.madeKyuyofileName;
	}

	public void setMadeKyuyofileName(String madeKyuyofileName) {
		this.madeKyuyofileName = madeKyuyofileName;
	}

	public String getApplyDealYearMonth() {
		return this.applyDealYearMonth;
	}

	public void setApplyDealYearMonth(String applyDealYearMonth) {
		this.applyDealYearMonth = applyDealYearMonth;
	}

	public Timestamp getFileCreatedTimestamp() {
		return this.fileCreatedTimestamp;
	}

	public void setFileCreatedTimestamp(Timestamp fileCreatedTimestamp) {
		this.fileCreatedTimestamp = fileCreatedTimestamp;
	}

	public byte[] getKyuyofileNaiyo() {
		return this.kyuyofileNaiyo;
	}

	public void setKyuyofileNaiyo(byte[] kyuyofileNaiyo) {
		this.kyuyofileNaiyo = kyuyofileNaiyo;
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

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}