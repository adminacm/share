package argo.cost.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * カレンダーテーブル.
 * 
 */
@Entity
@Table(name="Calendar")
public class MCalendar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="on_duty_date")
	private String onDutyDate;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="kyujisu_name")
	private String kyujisuName;

	@Column(name="on_duty_flg")
	private String onDutyFlg;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	public MCalendar() {
	}

	public String getOnDutyDate() {
		return this.onDutyDate;
	}

	public void setOnDutyDate(String onDutyDate) {
		this.onDutyDate = onDutyDate;
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

	public String getKyujisuName() {
		return this.kyujisuName;
	}

	public void setKyujisuName(String kyujisuName) {
		this.kyujisuName = kyujisuName;
	}

	public String getOnDutyFlg() {
		return this.onDutyFlg;
	}

	public void setOnDutyFlg(String onDutyFlg) {
		this.onDutyFlg = onDutyFlg;
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

}