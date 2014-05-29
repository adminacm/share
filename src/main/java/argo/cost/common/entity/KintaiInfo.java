package argo.cost.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the kintai_info database table.
 * 
 */
@Entity
@Table(name="kintai_info")
@NamedQuery(name="KintaiInfo.findAll", query="SELECT k FROM KintaiInfo k")
public class KintaiInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;

	@Column(name="atendance_book_date")
	private String atendanceBookDate;

	@Column(name="chokin_end_time")
	private String chokinEndTime;

	@Column(name="chokin_heijitu_jikansu")
	private BigDecimal chokinHeijituJikansu;

	@Column(name="chokin_heijitu_tujyo_jikansu")
	private BigDecimal chokinHeijituTujyoJikansu;

	@Column(name="chokin_kyujitu_jikansu")
	private BigDecimal chokinKyujituJikansu;

	@Column(name="chokin_start_time")
	private String chokinStartTime;

	private String code;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user_id")
	private String createdUserId;

	@Column(name="furikae_date")
	private String furikaeDate;

	@Column(name="kinmu_end_time")
	private String kinmuEndTime;

	@Column(name="kinmu_jikansu")
	private BigDecimal kinmuJikansu;

	@Column(name="kinmu_start_time")
	private String kinmuStartTime;

	@Column(name="kyuka_jikansu")
	private BigDecimal kyukaJikansu;

	@Column(name="sinya_kinmu_jikansu")
	private BigDecimal sinyaKinmuJikansu;

	@Column(name="torikomi_syori_date")
	private String torikomiSyoriDate;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="updated_user_id")
	private String updatedUserId;

	//bi-directional many-to-one association to ApprovalManage
	@ManyToOne
	@JoinColumn(name="approval_id")
	private ApprovalManage approvalManage;

	//bi-directional many-to-one association to KyukaKekinKbnMaster
	@ManyToOne
	@JoinColumn(name="kyuka_kekin_code")
	private KyukaKekinKbnMaster kyukaKekinKbnMaster;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="location_code")
	private Locations location;

	//bi-directional many-to-one association to ShiftJikoku
	@ManyToOne
	@JoinColumn(name="shift_cd")
	private ShiftJikoku shiftJikoku;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users users;

	//bi-directional many-to-one association to WorkDayKbnMaster
	@ManyToOne
	@JoinColumn(name="kinmu_kbn_cd")
	private WorkDayKbnMaster workDayKbnMaster;

	//bi-directional many-to-one association to ProjWorkTimeManage
	@OneToMany(mappedBy="kintaiInfo")
	private List<ProjWorkTimeManage> projWorkTimeManages;

	public KintaiInfo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAtendanceBookDate() {
		return this.atendanceBookDate;
	}

	public void setAtendanceBookDate(String atendanceBookDate) {
		this.atendanceBookDate = atendanceBookDate;
	}

	public String getChokinEndTime() {
		return this.chokinEndTime;
	}

	public void setChokinEndTime(String chokinEndTime) {
		this.chokinEndTime = chokinEndTime;
	}

	public BigDecimal getChokinHeijituJikansu() {
		return this.chokinHeijituJikansu;
	}

	public void setChokinHeijituJikansu(BigDecimal chokinHeijituJikansu) {
		this.chokinHeijituJikansu = chokinHeijituJikansu;
	}

	public BigDecimal getChokinHeijituTujyoJikansu() {
		return this.chokinHeijituTujyoJikansu;
	}

	public void setChokinHeijituTujyoJikansu(BigDecimal chokinHeijituTujyoJikansu) {
		this.chokinHeijituTujyoJikansu = chokinHeijituTujyoJikansu;
	}

	public BigDecimal getChokinKyujituJikansu() {
		return this.chokinKyujituJikansu;
	}

	public void setChokinKyujituJikansu(BigDecimal chokinKyujituJikansu) {
		this.chokinKyujituJikansu = chokinKyujituJikansu;
	}

	public String getChokinStartTime() {
		return this.chokinStartTime;
	}

	public void setChokinStartTime(String chokinStartTime) {
		this.chokinStartTime = chokinStartTime;
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

	public String getFurikaeDate() {
		return this.furikaeDate;
	}

	public void setFurikaeDate(String furikaeDate) {
		this.furikaeDate = furikaeDate;
	}

	public String getKinmuEndTime() {
		return this.kinmuEndTime;
	}

	public void setKinmuEndTime(String kinmuEndTime) {
		this.kinmuEndTime = kinmuEndTime;
	}

	public BigDecimal getKinmuJikansu() {
		return this.kinmuJikansu;
	}

	public void setKinmuJikansu(BigDecimal kinmuJikansu) {
		this.kinmuJikansu = kinmuJikansu;
	}

	public String getKinmuStartTime() {
		return this.kinmuStartTime;
	}

	public void setKinmuStartTime(String kinmuStartTime) {
		this.kinmuStartTime = kinmuStartTime;
	}

	public BigDecimal getKyukaJikansu() {
		return this.kyukaJikansu;
	}

	public void setKyukaJikansu(BigDecimal kyukaJikansu) {
		this.kyukaJikansu = kyukaJikansu;
	}

	public BigDecimal getSinyaKinmuJikansu() {
		return this.sinyaKinmuJikansu;
	}

	public void setSinyaKinmuJikansu(BigDecimal sinyaKinmuJikansu) {
		this.sinyaKinmuJikansu = sinyaKinmuJikansu;
	}

	public String getTorikomiSyoriDate() {
		return this.torikomiSyoriDate;
	}

	public void setTorikomiSyoriDate(String torikomiSyoriDate) {
		this.torikomiSyoriDate = torikomiSyoriDate;
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

	public ApprovalManage getApprovalManage() {
		return this.approvalManage;
	}

	public void setApprovalManage(ApprovalManage approvalManage) {
		this.approvalManage = approvalManage;
	}

	public KyukaKekinKbnMaster getKyukaKekinKbnMaster() {
		return this.kyukaKekinKbnMaster;
	}

	public void setKyukaKekinKbnMaster(KyukaKekinKbnMaster kyukaKekinKbnMaster) {
		this.kyukaKekinKbnMaster = kyukaKekinKbnMaster;
	}

	public Locations getLocation() {
		return this.location;
	}

	public void setLocation(Locations location) {
		this.location = location;
	}

	public ShiftJikoku getShiftJikoku() {
		return this.shiftJikoku;
	}

	public void setShiftJikoku(ShiftJikoku shiftJikoku) {
		this.shiftJikoku = shiftJikoku;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public WorkDayKbnMaster getWorkDayKbnMaster() {
		return this.workDayKbnMaster;
	}

	public void setWorkDayKbnMaster(WorkDayKbnMaster workDayKbnMaster) {
		this.workDayKbnMaster = workDayKbnMaster;
	}

	public List<ProjWorkTimeManage> getProjWorkTimeManages() {
		return this.projWorkTimeManages;
	}

	public void setProjWorkTimeManages(List<ProjWorkTimeManage> projWorkTimeManages) {
		this.projWorkTimeManages = projWorkTimeManages;
	}

	public ProjWorkTimeManage addProjWorkTimeManage(ProjWorkTimeManage projWorkTimeManage) {
		getProjWorkTimeManages().add(projWorkTimeManage);
		projWorkTimeManage.setKintaiInfo(this);

		return projWorkTimeManage;
	}

	public ProjWorkTimeManage removeProjWorkTimeManage(ProjWorkTimeManage projWorkTimeManage) {
		getProjWorkTimeManages().remove(projWorkTimeManage);
		projWorkTimeManage.setKintaiInfo(null);

		return projWorkTimeManage;
	}

}