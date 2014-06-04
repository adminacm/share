package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;


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
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="user_id")
	private String userId;

	@Column(name="fuyu_all_hours")
	private Integer fuyuAllHours;

	@Column(name="fuyu_year")
	private String fuyuYear;

	@Column(name="huyu_days")
	private Integer huyuDays;

	@Column(name="last_year_remained_hours")
	private float lastYearRemainedHours;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false,updatable=false)
	private Users users;

	public YukyuKyukaFuyu() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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