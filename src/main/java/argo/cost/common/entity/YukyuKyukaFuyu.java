package argo.cost.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the yukyu_kyuka_fuyu database table.
 * 
 */
@Entity
@Table(name="yukyu_kyuka_fuyu")
@NamedQuery(name="YukyuKyukaFuyu.findAll", query="SELECT y FROM YukyuKyukaFuyu y")
public class YukyuKyukaFuyu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="fuyu_all_hours")
	private Integer fuyuAllHours;

	@Column(name="fuyu_year", length=4)
	private String fuyuYear;

	@Column(name="huyu_days")
	private Integer huyuDays;

	@Column(name="last_year_remained_hours")
	private float lastYearRemainedHours;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users users;

	public YukyuKyukaFuyu() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFuyuAllHours() {
		return this.fuyuAllHours;
	}

	public void setFuyuAllHours(Integer fuyuAllHours) {
		this.fuyuAllHours = fuyuAllHours;
	}

	public String getFuyuYear() {
		return this.fuyuYear;
	}

	public void setFuyuYear(String fuyuYear) {
		this.fuyuYear = fuyuYear;
	}

	public Integer getHuyuDays() {
		return this.huyuDays;
	}

	public void setHuyuDays(Integer huyuDays) {
		this.huyuDays = huyuDays;
	}

	public float getLastYearRemainedHours() {
		return this.lastYearRemainedHours;
	}

	public void setLastYearRemainedHours(float lastYearRemainedHours) {
		this.lastYearRemainedHours = lastYearRemainedHours;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}