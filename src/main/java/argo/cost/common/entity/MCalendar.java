package argo.cost.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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
	@Column(name="on_duty_date", unique=true, nullable=false, length=8)
	private String onDutyDate;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(name="kyujisu_name", length=20)
	private String kyujisuName;

	@Column(name="on_duty_flg", nullable=false, length=1)
	private String onDutyFlg;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
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