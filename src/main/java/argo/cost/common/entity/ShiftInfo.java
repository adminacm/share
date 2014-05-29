package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the shift_info database table.
 * 
 */
@Entity
@Table(name="shift_info")
@NamedQuery(name="ShiftInfo.findAll", query="SELECT s FROM ShiftInfo s")
public class ShiftInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="kinmu_flg")
	private String kinmuFlg;

	@Column(name="time_zone_code")
	private String timeZoneCode;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to ShiftJikoku
	@ManyToOne
	@JoinColumn(name="shift_code")
	private ShiftJikoku shiftJikoku;

	public ShiftInfo() {
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

	public String getKinmuFlg() {
		return this.kinmuFlg;
	}

	public void setKinmuFlg(String kinmuFlg) {
		this.kinmuFlg = kinmuFlg;
	}

	public String getTimeZoneCode() {
		return this.timeZoneCode;
	}

	public void setTimeZoneCode(String timeZoneCode) {
		this.timeZoneCode = timeZoneCode;
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

	public ShiftJikoku getShiftJikoku() {
		return this.shiftJikoku;
	}

	public void setShiftJikoku(ShiftJikoku shiftJikoku) {
		this.shiftJikoku = shiftJikoku;
	}

}