package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the kyuka_kekin database table.
 * 
 */
@Entity
@Table(name="kyuka_kekin")
@NamedQuery(name="KyukaKekin.findAll", query="SELECT k FROM KyukaKekin k")
public class KyukaKekin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="koujyo_ym")
	private String koujyoYm;

	@Column(name="kyuka_date")
	private String kyukaDate;

	@Column(name="kyuka_jikansu")
	private BigDecimal kyukaJikansu;

	@Column(name="syori_date")
	private String syoriDate;

	@Column(name="syori_flag")
	private BigDecimal syoriFlag;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to KyukaKekinKbnMaster
	@ManyToOne
	@JoinColumn(name="kyuka_kekin_code")
	private KyukaKekinKbnMaster kyukaKekinKbnMaster;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users users;

	public KyukaKekin() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getKoujyoYm() {
		return this.koujyoYm;
	}

	public void setKoujyoYm(String koujyoYm) {
		this.koujyoYm = koujyoYm;
	}

	public String getKyukaDate() {
		return this.kyukaDate;
	}

	public void setKyukaDate(String kyukaDate) {
		this.kyukaDate = kyukaDate;
	}

	public BigDecimal getKyukaJikansu() {
		return this.kyukaJikansu;
	}

	public void setKyukaJikansu(BigDecimal kyukaJikansu) {
		this.kyukaJikansu = kyukaJikansu;
	}

	public String getSyoriDate() {
		return this.syoriDate;
	}

	public void setSyoriDate(String syoriDate) {
		this.syoriDate = syoriDate;
	}

	public BigDecimal getSyoriFlag() {
		return this.syoriFlag;
	}

	public void setSyoriFlag(BigDecimal syoriFlag) {
		this.syoriFlag = syoriFlag;
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

	public KyukaKekinKbnMaster getKyukaKekinKbnMaster() {
		return this.kyukaKekinKbnMaster;
	}

	public void setKyukaKekinKbnMaster(KyukaKekinKbnMaster kyukaKekinKbnMaster) {
		this.kyukaKekinKbnMaster = kyukaKekinKbnMaster;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}