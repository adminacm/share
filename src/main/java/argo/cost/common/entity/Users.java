package argo.cost.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.context.annotation.Lazy;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="Users.findAll", query="SELECT u FROM Users u")
@Lazy(value=false)
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private String id;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="dairisha_id")
	private String dairishaId;

	@Column(name="kinmu_end_time")
	private String kinmuEndTime;

	@Column(name="kinmu_start_time")
	private String kinmuStartTime;

	@Column(name="kyugyo_end_date")
	private String kyugyoEndDate;

	@Column(name="kyugyo_start_date")
	private String kyugyoStartDate;

	@Column(name="login_mail_address")
	private String loginMailAddress;

	@Column(name="nyusha_date")
	private String nyushaDate;

	private String password;

	@Column(name="standard_shift_cd")
	private String standardShiftCd;

	private BigDecimal status;

	@Column(name="taisyoku_date")
	private String taisyokuDate;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	@Column(name="user_name")
	private String userName;
	
	//bi-directional many-to-one association to ApprovalManage
	@OneToMany(mappedBy="users")
	private List<ApprovalManage> approvalManages;
	
//	//bi-directional many-to-one association to ChokinKanri
//	@OneToMany(mappedBy="users")
//	private List<ChokinKanri> chokinKanris;
//
//	//bi-directional many-to-one association to HolidayAtendance
//	@OneToMany(mappedBy="users")
//	private List<HolidayAtendance> holidayAtendances;
//
	//bi-directional many-to-one association to KintaiInfo
	@OneToMany(mappedBy="users")
	private List<KintaiInfo> kintaiInfos;

	//bi-directional many-to-one association to KyukaKekin
	@OneToMany(mappedBy="users")
	private List<KyukaKekin> kyukaKekins;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="users")
	private List<UserRole> userRoles;

	//bi-directional many-to-one association to AffiliationMaster
	@ManyToOne
	@JoinColumn(name="shozoku_id")
	private AffiliationMaster affiliationMaster;
//
//	//bi-directional many-to-one association to YukyuKyukaFuyu
//	@OneToMany(mappedBy="users")
//	private List<YukyuKyukaFuyu> yukyuKyukaFuyus;

	public Users() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

	public String getDairishaId() {
		return this.dairishaId;
	}

	public void setDairishaId(String dairishaId) {
		this.dairishaId = dairishaId;
	}

	public String getKinmuEndTime() {
		return this.kinmuEndTime;
	}

	public void setKinmuEndTime(String kinmuEndTime) {
		this.kinmuEndTime = kinmuEndTime;
	}

	public String getKinmuStartTime() {
		return this.kinmuStartTime;
	}

	public void setKinmuStartTime(String kinmuStartTime) {
		this.kinmuStartTime = kinmuStartTime;
	}

	public String getKyugyoEndDate() {
		return this.kyugyoEndDate;
	}

	public void setKyugyoEndDate(String kyugyoEndDate) {
		this.kyugyoEndDate = kyugyoEndDate;
	}

	public String getKyugyoStartDate() {
		return this.kyugyoStartDate;
	}

	public void setKyugyoStartDate(String kyugyoStartDate) {
		this.kyugyoStartDate = kyugyoStartDate;
	}

	public String getLoginMailAddress() {
		return this.loginMailAddress;
	}

	public void setLoginMailAddress(String loginMailAddress) {
		this.loginMailAddress = loginMailAddress;
	}

	public String getNyushaDate() {
		return this.nyushaDate;
	}

	public void setNyushaDate(String nyushaDate) {
		this.nyushaDate = nyushaDate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStandardShiftCd() {
		return this.standardShiftCd;
	}

	public void setStandardShiftCd(String standardShiftCd) {
		this.standardShiftCd = standardShiftCd;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getTaisyokuDate() {
		return this.taisyokuDate;
	}

	public void setTaisyokuDate(String taisyokuDate) {
		this.taisyokuDate = taisyokuDate;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<ApprovalManage> getApprovalManages() {
		return this.approvalManages;
	}

	public void setApprovalManages(List<ApprovalManage> approvalManages) {
		this.approvalManages = approvalManages;
	}

	public ApprovalManage addApprovalManage(ApprovalManage approvalManage) {
		getApprovalManages().add(approvalManage);
		approvalManage.setUser(this);

		return approvalManage;
	}

	public ApprovalManage removeApprovalManage(ApprovalManage approvalManage) {
		getApprovalManages().remove(approvalManage);
		approvalManage.setUser(null);

		return approvalManage;
	}
	
//	public List<ChokinKanri> getChokinKanris() {
//		return this.chokinKanris;
//	}
//
//	public void setChokinKanris(List<ChokinKanri> chokinKanris) {
//		this.chokinKanris = chokinKanris;
//	}
//
//	public ChokinKanri addChokinKanri(ChokinKanri chokinKanri) {
//		getChokinKanris().add(chokinKanri);
//		chokinKanri.setUsers(this);
//
//		return chokinKanri;
//	}
//
//	public ChokinKanri removeChokinKanri(ChokinKanri chokinKanri) {
//		getChokinKanris().remove(chokinKanri);
//		chokinKanri.setUsers(null);
//
//		return chokinKanri;
//	}
//
//	public List<HolidayAtendance> getHolidayAtendances() {
//		return this.holidayAtendances;
//	}
//
//	public void setHolidayAtendances(List<HolidayAtendance> holidayAtendances) {
//		this.holidayAtendances = holidayAtendances;
//	}
//
//	public HolidayAtendance addHolidayAtendance(HolidayAtendance holidayAtendance) {
//		getHolidayAtendances().add(holidayAtendance);
//		holidayAtendance.setUsers(this);
//
//		return holidayAtendance;
//	}
//
//	public HolidayAtendance removeHolidayAtendance(HolidayAtendance holidayAtendance) {
//		getHolidayAtendances().remove(holidayAtendance);
//		holidayAtendance.setUsers(null);
//
//		return holidayAtendance;
//	}

	public List<KintaiInfo> getKintaiInfos() {
		return this.kintaiInfos;
	}

	public void setKintaiInfos(List<KintaiInfo> kintaiInfos) {
		this.kintaiInfos = kintaiInfos;
	}

	public KintaiInfo addKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().add(kintaiInfo);
		kintaiInfo.setUsers(this);

		return kintaiInfo;
	}

	public KintaiInfo removeKintaiInfo(KintaiInfo kintaiInfo) {
		getKintaiInfos().remove(kintaiInfo);
		kintaiInfo.setUsers(null);

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
		kyukaKekin.setUsers(this);

		return kyukaKekin;
	}

	public KyukaKekin removeKyukaKekin(KyukaKekin kyukaKekin) {
		getKyukaKekins().remove(kyukaKekin);
		kyukaKekin.setUsers(null);

		return kyukaKekin;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setUsers(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setUsers(null);

		return userRole;
	}

	public AffiliationMaster getAffiliationMaster() {
		return this.affiliationMaster;
	}

	public void setAffiliationMaster(AffiliationMaster affiliationMaster) {
		this.affiliationMaster = affiliationMaster;
	}
//
//	public List<YukyuKyukaFuyu> getYukyuKyukaFuyus() {
//		return this.yukyuKyukaFuyus;
//	}
//
//	public void setYukyuKyukaFuyus(List<YukyuKyukaFuyu> yukyuKyukaFuyus) {
//		this.yukyuKyukaFuyus = yukyuKyukaFuyus;
//	}
//
//	public YukyuKyukaFuyu addYukyuKyukaFuyus(YukyuKyukaFuyu yukyuKyukaFuyus) {
//		getYukyuKyukaFuyus().add(yukyuKyukaFuyus);
//		yukyuKyukaFuyus.setUsers(this);
//
//		return yukyuKyukaFuyus;
//	}
//
//	public YukyuKyukaFuyu removeYukyuKyukaFuyus(YukyuKyukaFuyu yukyuKyukaFuyus) {
//		getYukyuKyukaFuyus().remove(yukyuKyukaFuyus);
//		yukyuKyukaFuyus.setUsers(null);
//
//		return yukyuKyukaFuyus;
//	}

}