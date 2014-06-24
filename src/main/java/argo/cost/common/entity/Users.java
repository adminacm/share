package argo.cost.common.entity;


import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM Users u")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=20)
	private String id;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id", length=20)
	private String createdUserId;

	@Column(name="dairisha_id", length=20)
	private String dairishaId;

	@Column(name="kinmu_end_time", nullable=false, length=4)
	private String kinmuEndTime;

	@Column(name="kinmu_start_time", nullable=false, length=4)
	private String kinmuStartTime;

	@Column(name="kyugyo_end_date", length=8)
	private String kyugyoEndDate;

	@Column(name="kyugyo_start_date", length=8)
	private String kyugyoStartDate;

	@Column(name="login_mail_address", nullable=false, length=40)
	private String loginMailAddress;

	@Column(name="nyusha_date", nullable=false, length=8)
	private String nyushaDate;

	@Column(nullable=false, length=16)
	private String password;

	@Column(name="standard_shift_cd", nullable=false, length=6)
	private String standardShiftCd;

	@Column(nullable=false, precision=1)
	private BigDecimal status;

	@Column(name="taisyoku_date", length=8)
	private String taisyokuDate;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id", length=20)
	private String updatedUserId;

	@Column(name="user_name", nullable=false, length=30)
	private String userName;

	//bi-directional many-to-one association to ApprovalManage
	@OneToMany(mappedBy="users")
	private List<ApprovalManage> approvalManages;

	//bi-directional many-to-one association to HolidayAtendanceYotei
	@OneToMany(mappedBy="users")
	private List<HolidayAtendanceYotei> holidayAtendanceYoteis;

	//bi-directional many-to-one association to KintaiInfo
	@OneToMany(mappedBy="users")
	private List<KintaiInfo> kintaiInfos;

	//bi-directional many-to-one association to MadeSyskyuyofileOutput
	@OneToMany(mappedBy="users")
	private List<MadeSyskyuyofileOutput> madeSyskyuyofileOutputs;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="users")
	private List<UserRole> userRoles;

	//bi-directional many-to-one association to AffiliationMaster
	@ManyToOne
	@JoinColumn(name="shozoku_id", nullable=false)
	private AffiliationMaster affiliationMaster;

	//bi-directional many-to-one association to YukyuKyukaFuyu
	@OneToMany(mappedBy="users")
	private List<YukyuKyukaFuyu> yukyuKyukaFuyus;

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

	public List<HolidayAtendanceYotei> getHolidayAtendanceYoteis() {
		return this.holidayAtendanceYoteis;
	}

	public void setHolidayAtendanceYoteis(List<HolidayAtendanceYotei> holidayAtendanceYoteis) {
		this.holidayAtendanceYoteis = holidayAtendanceYoteis;
	}

	public HolidayAtendanceYotei addHolidayAtendanceYotei(HolidayAtendanceYotei holidayAtendanceYotei) {
		getHolidayAtendanceYoteis().add(holidayAtendanceYotei);
		holidayAtendanceYotei.setUser(this);

		return holidayAtendanceYotei;
	}

	public HolidayAtendanceYotei removeHolidayAtendanceYotei(HolidayAtendanceYotei holidayAtendanceYotei) {
		getHolidayAtendanceYoteis().remove(holidayAtendanceYotei);
		holidayAtendanceYotei.setUser(null);

		return holidayAtendanceYotei;
	}

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

	public List<MadeSyskyuyofileOutput> getMadeSyskyuyofileOutputs() {
		return this.madeSyskyuyofileOutputs;
	}

	public void setMadeSyskyuyofileOutputs(List<MadeSyskyuyofileOutput> madeSyskyuyofileOutputs) {
		this.madeSyskyuyofileOutputs = madeSyskyuyofileOutputs;
	}

	public MadeSyskyuyofileOutput addMadeSyskyuyofileOutput(MadeSyskyuyofileOutput madeSyskyuyofileOutput) {
		getMadeSyskyuyofileOutputs().add(madeSyskyuyofileOutput);
		madeSyskyuyofileOutput.setUsers(this);

		return madeSyskyuyofileOutput;
	}

	public MadeSyskyuyofileOutput removeMadeSyskyuyofileOutput(MadeSyskyuyofileOutput madeSyskyuyofileOutput) {
		getMadeSyskyuyofileOutputs().remove(madeSyskyuyofileOutput);
		madeSyskyuyofileOutput.setUsers(null);

		return madeSyskyuyofileOutput;
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

	public List<YukyuKyukaFuyu> getYukyuKyukaFuyus() {
		return this.yukyuKyukaFuyus;
	}

	public void setYukyuKyukaFuyus(List<YukyuKyukaFuyu> yukyuKyukaFuyus) {
		this.yukyuKyukaFuyus = yukyuKyukaFuyus;
	}

	public YukyuKyukaFuyu addYukyuKyukaFuyus(YukyuKyukaFuyu yukyuKyukaFuyus) {
		getYukyuKyukaFuyus().add(yukyuKyukaFuyus);
		yukyuKyukaFuyus.setUsers(this);

		return yukyuKyukaFuyus;
	}

	public YukyuKyukaFuyu removeYukyuKyukaFuyus(YukyuKyukaFuyu yukyuKyukaFuyus) {
		getYukyuKyukaFuyus().remove(yukyuKyukaFuyus);
		yukyuKyukaFuyus.setUsers(null);

		return yukyuKyukaFuyus;
	}

}