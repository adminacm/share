package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the holiday_atendance database table.
 * 
 */
@Entity
@Table(name="holiday_atendance")
@NamedQuery(name="HolidayAtendance.findAll", query="SELECT h FROM HolidayAtendance h")
public class HolidayAtendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="daikyu_date")
	private String daikyuDate;

	@Column(name="daikyu_get_shimekiri_date")
	private String daikyuGetShimekiriDate;

	@Column(name="firikae_shisei_date")
	private String firikaeShiseiDate;

	@Column(name="holiday_syukin_date")
	private String holidaySyukinDate;

	@Column(name="shukei_syori_date")
	private String shukeiSyoriDate;

	@Column(name="siharai_year_month")
	private String siharaiYearMonth;

	@Column(name="syukei_flg")
	private String syukeiFlg;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users users;

	public HolidayAtendance() {
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

	public String getDaikyuDate() {
		return this.daikyuDate;
	}

	public void setDaikyuDate(String daikyuDate) {
		this.daikyuDate = daikyuDate;
	}

	public String getDaikyuGetShimekiriDate() {
		return this.daikyuGetShimekiriDate;
	}

	public void setDaikyuGetShimekiriDate(String daikyuGetShimekiriDate) {
		this.daikyuGetShimekiriDate = daikyuGetShimekiriDate;
	}

	public String getFirikaeShiseiDate() {
		return this.firikaeShiseiDate;
	}

	public void setFirikaeShiseiDate(String firikaeShiseiDate) {
		this.firikaeShiseiDate = firikaeShiseiDate;
	}

	public String getHolidaySyukinDate() {
		return this.holidaySyukinDate;
	}

	public void setHolidaySyukinDate(String holidaySyukinDate) {
		this.holidaySyukinDate = holidaySyukinDate;
	}

	public String getShukeiSyoriDate() {
		return this.shukeiSyoriDate;
	}

	public void setShukeiSyoriDate(String shukeiSyoriDate) {
		this.shukeiSyoriDate = shukeiSyoriDate;
	}

	public String getSiharaiYearMonth() {
		return this.siharaiYearMonth;
	}

	public void setSiharaiYearMonth(String siharaiYearMonth) {
		this.siharaiYearMonth = siharaiYearMonth;
	}

	public String getSyukeiFlg() {
		return this.syukeiFlg;
	}

	public void setSyukeiFlg(String syukeiFlg) {
		this.syukeiFlg = syukeiFlg;
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