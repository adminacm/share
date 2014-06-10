package argo.cost.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the chokin_kanri database table.
 * 
 */
@Entity
@Table(name="chokin_kanri")
@NamedQuery(name="ChokinKanri.findAll", query="SELECT c FROM ChokinKanri c")
public class ChokinKanri implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;

	@Column(name="chokin_date")
	private String chokinDate;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="csv_out_date")
	private String csvOutDate;

	@Column(name="csv_output_flg")
	private BigDecimal csvOutputFlg;

	@Column(name="himoku_kbn_code")
	private String himokuKbnCode;

	private BigDecimal hours;

	@Column(name="siharai_year_month")
	private String siharaiYearMonth;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to User
	@ManyToOne
	private Users users;

	public ChokinKanri() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChokinDate() {
		return this.chokinDate;
	}

	public void setChokinDate(String chokinDate) {
		this.chokinDate = chokinDate;
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

	public String getCsvOutDate() {
		return this.csvOutDate;
	}

	public void setCsvOutDate(String csvOutDate) {
		this.csvOutDate = csvOutDate;
	}

	public BigDecimal getCsvOutputFlg() {
		return this.csvOutputFlg;
	}

	public void setCsvOutputFlg(BigDecimal csvOutputFlg) {
		this.csvOutputFlg = csvOutputFlg;
	}

	public String getHimokuKbnCode() {
		return this.himokuKbnCode;
	}

	public void setHimokuKbnCode(String himokuKbnCode) {
		this.himokuKbnCode = himokuKbnCode;
	}

	public BigDecimal getHours() {
		return this.hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	public String getSiharaiYearMonth() {
		return this.siharaiYearMonth;
	}

	public void setSiharaiYearMonth(String siharaiYearMonth) {
		this.siharaiYearMonth = siharaiYearMonth;
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