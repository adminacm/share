package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;


/**
 * 休暇欠勤区分マスタテーブル.
 * 
 */
@Entity
@Table(name="kyuka_kekin_kbn_master")
@NamedQuery(name="KyukaKekinKbnMaster.findAll", query="SELECT k FROM KyukaKekinKbnMaster k")
public class KyukaKekinKbnMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private String code;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="deduction_flg")
	private BigDecimal deductionFlg;

	private String name;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to KintaiInfo
	@OneToMany(mappedBy="kyukaKekinKbnMaster")
	private List<KintaiInfo> kintaiInfos;

	//bi-directional many-to-one association to KyukaKekin
	@OneToMany(mappedBy="kyukaKekinKbnMaster")
	private List<KyukaKekin> kyukaKekins;

	public KyukaKekinKbnMaster() {
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

	public BigDecimal getDeductionFlg() {
		return this.deductionFlg;
	}

	public void setDeductionFlg(BigDecimal deductionFlg) {
		this.deductionFlg = deductionFlg;
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

	public List<KintaiInfo> getKintaiInfos() {
		return this.kintaiInfos;
	}

	public void setKintaiInfos(List<KintaiInfo> kintaiInfos) {
		this.kintaiInfos = kintaiInfos;
	}

	public KintaiInfo addKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().add(kintaiInfo);
		kintaiInfo.setKyukaKekinKbnMaster(this);

		return kintaiInfo;
	}

	public KintaiInfo removeKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().remove(kintaiInfo);
		kintaiInfo.setKyukaKekinKbnMaster(null);

		return kintaiInfo;
	}

	public List<KyukaKekin> getKyukaKekins() {
		return this.kyukaKekins;
	}

	public void setKyukaKekins(List<KyukaKekin> kyukaKekins) {
		this.kyukaKekins = kyukaKekins;
	}

	public KyukaKekin addKyukaKekin(KyukaKekin kyukaKekin) {
		getKyukaKekins().add(kyukaKekin);
		kyukaKekin.setKyukaKekinKbnMaster(this);

		return kyukaKekin;
	}

	public KyukaKekin removeKyukaKekin(KyukaKekin kyukaKekin) {
		getKyukaKekins().remove(kyukaKekin);
		kyukaKekin.setKyukaKekinKbnMaster(null);

		return kyukaKekin;
	}

}