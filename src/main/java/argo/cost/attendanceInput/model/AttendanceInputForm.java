package argo.cost.attendanceInput.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.entity.KyukaKekinKbnMaster;
import argo.cost.common.entity.Locations;
import argo.cost.common.model.AbstractForm;

/**
 * 勤怠入力
 */
public class AttendanceInputForm extends AbstractForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 日付
	 */
	private String attDate;
	
	/**
	 * 日付(表示)
	 */
	private String attDateShow;
	
	/**
	 * 勤務日区分
	 */
	private String workDayKbn;
	
	/**
	 * 勤務区分名
	 */
	private String workDayKbnName;
	/**
	 * シフトコード
	 */
	private String shiftCd;
	/**
	 * 標準シフトコード
	 */
	private String shiftCdShow;
	/**
	 * 勤務開始時刻(hhnn)
	 */
	private String workSTime;
	/**
	 * 勤務開始時刻計算用
	 */
	private String workSTimeStr;
	/**
	 * 勤務開始時刻(hh)
	 */
	private String workSHour;
	/**
	 * 勤務開始時刻(nn)
	 */
	private String workSMinute;
	
	/**
	 * 勤務終了時刻(hhnn)
	 */
	private String workETime;
	/**
	 * 勤務終了時刻計算用
	 */
	private String workETimeStr;
	/**
	 * 勤務終了時刻(hh)
	 */
	private String workEHour;
	/**
	 * 勤務終了時刻(nn)
	 */
	private String workEMinute;

	/**
	 * 休暇欠勤区分
	 */
	private String kyukaKb;
	/**
	 * 休暇欠勤区分リスト
	 */
	private List<KyukaKekinKbnMaster> kyukakbList;

	/**
	 * 休暇時間
	 */
	private Double kyukaHours;

	/**
	 * 勤務時間
	 */
	private Double workHours;

	/**
	 * 超勤開始時刻
	 */
	private String choSTime;

	/**
	 * 超勤終了時刻
	 */
	private String choETime;
	
	/**
	 * 超勤開始時刻(表示)
	 */
	private String choSTimeShow;

	/**
	 * 超勤終了時刻(表示)
	 */
	private String choETimeShow;
	
	/**
	 * 超勤平日（割増）
	 */
	private Double choWeekday;
	
	/**
	 * 超勤平日（通常）
	 */
	private Double choWeekdayNomal;
	
	/**
	 * 超勤休日
	 */
	private Double choHoliday;
	
	/**
	 * 深夜
	 */
	private Double mNHours;
	
	/**
	 * プロジェクトリスト
	 */
	private List<AttendanceProjectVO> projectList;
	
	/**
	 * ロケーション
	 */
	private String locationId;
	
	/**
	 * ロケーションリスト
	 */
	private List<Locations> locationItemList;
	
	/**
	 * 休日勤務情報
	 */
	private HolidayAttendanceVO holidayAttendance;
	
	/**
	 * 休日勤務情報フラグ
	 */
	private Integer kinmuKun = 0;
	
	/**
	 * 月報承認状況コード
	 */
	private String appStatusCode;

	/**
	 * 月報承認状況名
	 */
	private String appStatusName;
	
	/**
	 * シフト情報
	 */
	private ShiftVO shiftVO;
	
	/**
	 * 月報承認状況名前表示非表示フラグ（"1"：表示する；"0":表示しない）
	 */
	private String monthReportStatusHyojiFlg;
	
	/**
	 * 休日勤務入力ボタン可用不可用フラグ（"1"：可用する；"0":不可用する）
	 */
	private String holidayAttendanceBtnInUseFlg;
	/**
	 * 休日勤務の勤務情報入力部分表示非表示フラグ（"1"：表示する；"0":表示しない）
	 */
	private String holidayAttendanceInputPartHyojiFlg;
	
	/**
	 * 計算ボタン可用不可用フラグ（"1"：可用する；"0":不可用する）
	 */
	private String keisanBtnInUseFlg;
	
	/**
	 * 休暇時間数表示非表示フラグ（"1"：表示する；"0":表示しない）
	 */
	private String kyukaJikansuHyojiFlg;
	
	/**
	 * 勤務時間数表示非表示フラグ（"1"：表示する；"0":表示しない）
	 */
	private String kinmuJikansuHyojiFlg;
	
	/**
	 * 平日割増表示非表示フラグ（"1"：表示する；"0":表示しない）
	 */
	private String hejituWarimashiHyojiFlg;
	
	/**
	 * 平日通常表示非表示フラグ（"1"：表示する；"0":表示しない）
	 */
	private String hejituTujyouHyojiFlg;
	
	/**
	 * 休日超勤時間数表示非表示フラグ（"1"：表示する；"0":表示しない）
	 */
	private String kyujituChokinJikansuHyojiFlg;
	
	/**
	 * 深夜超勤時間数表示非表示フラグ（"1"：表示する；"0":表示しない）
	 */
	private String shiyaChokinJikansuHyojiFlg;
	
	/**
	 * 保存ボタン可用不可用フラグ（"1"：可用する；"0":不可用する）
	 */
	private String saveBtnInUseFlg;
	
	
	//#################################
	//#################################
	/**
	 * 日付を取得する
	 */
	public String getAttDate() {
		return attDate;
	}
	
	/**
	 * 日付を設定する
	 */
	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}

	/**
	 * シフトコードを取得する
	 */
	public String getShiftCd() {
		return shiftCd;
	}

	/**
	 * シフトコードを設定する
	 */
	public void setShiftCd(String shiftCd) {
		this.shiftCd = shiftCd;
	}
    
	public String getShiftCdShow() {
		return shiftCdShow;
	}

	public void setShiftCdShow(String shiftCdShow) {
		this.shiftCdShow = shiftCdShow;
	}

	/**
	 * 勤務開始時刻(hhnn)を取得する
	 */
	public String getWorkSTime() {
		return workSTime;
	}
    
	/**
	 * 勤務開始時刻(hhnn)を設定する
	 */
	public void setWorkSTime(String workSTime) {
		this.workSTime = workSTime;
	}

	/**
	 * 勤務終了時刻(hhnn)を取得する
	 */
	public String getWorkETime() {
		return workETime;
	}

	/**
	 * 勤務終了時刻(hhnn)を設定する
	 */
	public void setWorkETime(String workETime) {
		this.workETime = workETime;
	}

	/**
	 * 休暇欠勤区分を取得する
	 */
	public String getKyukaKb() {
		return kyukaKb;
	}

	/**
	 * 休暇欠勤区分を設定する
	 */
	public void setKyukaKb(String kyukaKb) {
		this.kyukaKb = kyukaKb;
	}

	/**
	 * 休暇時間を取得する
	 */
	public Double getKyukaHours() {
		return kyukaHours;
	}

	/**
	 * 休暇時間を設定する
	 */
	public void setKyukaHours(Double kyukaHours) {
		this.kyukaHours = kyukaHours;
	}

	/**
	 * 勤務時間を取得する
	 */
	public Double getWorkHours() {
		return workHours;
	}

	/**
	 * 勤務時間を設定する
	 */
	public void setWorkHours(Double workHours) {
		this.workHours = workHours;
	}

	/**
	 * 超勤開始時刻を取得する
	 */
	public String getChoSTime() {
		return choSTime;
	}

	/**
	 * 超勤開始時刻を設定する
	 */
	public void setChoSTime(String choSTime) {
		this.choSTime = choSTime;
	}

	/**
	 * 超勤終了時刻を取得する
	 */
	public String getChoETime() {
		return choETime;
	}

	/**
	 * 超勤終了時刻を設定する
	 */
	public void setChoETime(String choETime) {
		this.choETime = choETime;
	}

	/**
	 * 超勤平日（割増）を取得する
	 */
	public Double getChoWeekday() {
		return choWeekday;
	}

	/**
	 * 超勤平日（割増）を設定する
	 */
	public void setChoWeekday(Double choWeekday) {
		this.choWeekday = choWeekday;
	}

	/**
	 * 超勤平日（通常）を取得する
	 */
	public Double getChoWeekdayNomal() {
		return choWeekdayNomal;
	}

	/**
	 * 超勤平日（通常）を設定する
	 */
	public void setChoWeekdayNomal(Double choWeekdayNomal) {
		this.choWeekdayNomal = choWeekdayNomal;
	}

	/**
	 * 超勤休日を取得する
	 */
	public Double getChoHoliday() {
		return choHoliday;
	}

	/**
	 * 超勤休日を設定する
	 */
	public void setChoHoliday(Double choHoliday) {
		this.choHoliday = choHoliday;
	}

	public String getWorkDayKbn() {
		return workDayKbn;
	}

	public void setWorkDayKbn(String workDayKbn) {
		this.workDayKbn = workDayKbn;
	}

	public String getWorkDayKbnName() {
		return workDayKbnName;
	}

	public void setWorkDayKbnName(String workDayKbnName) {
		this.workDayKbnName = workDayKbnName;
	}

	public Double getmNHours() {
		return mNHours;
	}

	public void setmNHours(Double mNHours) {
		this.mNHours = mNHours;
	}

	public List<KyukaKekinKbnMaster> getKyukakbList() {
		return kyukakbList;
	}

	public void setKyukakbList(List<KyukaKekinKbnMaster> kyukakbList) {
		this.kyukakbList = kyukakbList;
	}

	public String getAttDateShow() {
		return attDateShow;
	}

	public void setAttDateShow(String attDateShow) {
		this.attDateShow = attDateShow;
	}

	public List<AttendanceProjectVO> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<AttendanceProjectVO> projectList) {
		this.projectList = projectList;
	}

	
	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public List<Locations> getLocationItemList() {
		return locationItemList;
	}

	public void setLocationItemList(List<Locations> locationItemList) {
		this.locationItemList = locationItemList;
	}

	public String getWorkSHour() {
		return workSHour;
	}

	public void setWorkSHour(String workSHour) {
		this.workSHour = workSHour;
	}

	public String getWorkSMinute() {
		return workSMinute;
	}

	public void setWorkSMinute(String workSMinute) {
		this.workSMinute = workSMinute;
	}

	public String getWorkEHour() {
		return workEHour;
	}

	public void setWorkEHour(String workEHour) {
		this.workEHour = workEHour;
	}

	public String getWorkEMinute() {
		return workEMinute;
	}

	public void setWorkEMinute(String workEMinute) {
		this.workEMinute = workEMinute;
	}

	public HolidayAttendanceVO getHolidayAttendance() {
		return holidayAttendance;
	}

	public void setHolidayAttendance(HolidayAttendanceVO holidayAttendance) {
		this.holidayAttendance = holidayAttendance;
	}

	public Integer getKinmuKun() {
		return kinmuKun;
	}

	public void setKinmuKun(Integer kinmuKun) {
		this.kinmuKun = kinmuKun;
	}

	public ShiftVO getShiftInfo() {
		return shiftVO;
	}

	public void setShiftInfo(ShiftVO shiftVO) {
		this.shiftVO = shiftVO;
	}

	public String getWorkSTimeStr() {
		return workSTimeStr;
	}

	public void setWorkSTimeStr(String workSTimeStr) {
		this.workSTimeStr = workSTimeStr;
	}

	public String getWorkETimeStr() {
		return workETimeStr;
	}

	public void setWorkETimeStr(String workETimeStr) {
		this.workETimeStr = workETimeStr;
	}

	public String getAppStatusCode() {
		return appStatusCode;
	}

	public void setAppStatusCode(String appStatusCode) {
		this.appStatusCode = appStatusCode;
	}

	public String getAppStatusName() {
		return appStatusName;
	}

	public void setAppStatusName(String appStatusName) {
		this.appStatusName = appStatusName;
	}

	public String getChoSTimeShow() {
		return choSTimeShow;
	}

	public void setChoSTimeShow(String choSTimeShow) {
		this.choSTimeShow = choSTimeShow;
	}

	public String getChoETimeShow() {
		return choETimeShow;
	}

	public void setChoETimeShow(String choETimeShow) {
		this.choETimeShow = choETimeShow;
	}

	public String getMonthReportStatusHyojiFlg() {
		return monthReportStatusHyojiFlg;
	}

	public void setMonthReportStatusHyojiFlg(String monthReportStatusHyojiFlg) {
		this.monthReportStatusHyojiFlg = monthReportStatusHyojiFlg;
	}

	public String getHolidayAttendanceBtnInUseFlg() {
		return holidayAttendanceBtnInUseFlg;
	}

	public void setHolidayAttendanceBtnInUseFlg(String holidayAttendanceBtnInUseFlg) {
		this.holidayAttendanceBtnInUseFlg = holidayAttendanceBtnInUseFlg;
	}

	public String getHolidayAttendanceInputPartHyojiFlg() {
		return holidayAttendanceInputPartHyojiFlg;
	}

	public void setHolidayAttendanceInputPartHyojiFlg(
			String holidayAttendanceInputPartHyojiFlg) {
		this.holidayAttendanceInputPartHyojiFlg = holidayAttendanceInputPartHyojiFlg;
	}

	public String getKeisanBtnInUseFlg() {
		return keisanBtnInUseFlg;
	}

	public void setKeisanBtnInUseFlg(String keisanBtnInUseFlg) {
		this.keisanBtnInUseFlg = keisanBtnInUseFlg;
	}

	public String getKyukaJikansuHyojiFlg() {
		return kyukaJikansuHyojiFlg;
	}

	public void setKyukaJikansuHyojiFlg(String kyukaJikansuHyojiFlg) {
		this.kyukaJikansuHyojiFlg = kyukaJikansuHyojiFlg;
	}

	public String getKinmuJikansuHyojiFlg() {
		return kinmuJikansuHyojiFlg;
	}

	public void setKinmuJikansuHyojiFlg(String kinmuJikansuHyojiFlg) {
		this.kinmuJikansuHyojiFlg = kinmuJikansuHyojiFlg;
	}

	public String getHejituWarimashiHyojiFlg() {
		return hejituWarimashiHyojiFlg;
	}

	public void setHejituWarimashiHyojiFlg(String hejituWarimashiHyojiFlg) {
		this.hejituWarimashiHyojiFlg = hejituWarimashiHyojiFlg;
	}

	public String getHejituTujyouHyojiFlg() {
		return hejituTujyouHyojiFlg;
	}

	public void setHejituTujyouHyojiFlg(String hejituTujyouHyojiFlg) {
		this.hejituTujyouHyojiFlg = hejituTujyouHyojiFlg;
	}

	public String getKyujituChokinJikansuHyojiFlg() {
		return kyujituChokinJikansuHyojiFlg;
	}

	public void setKyujituChokinJikansuHyojiFlg(String kyujituChokinJikansuHyojiFlg) {
		this.kyujituChokinJikansuHyojiFlg = kyujituChokinJikansuHyojiFlg;
	}

	public String getShiyaChokinJikansuHyojiFlg() {
		return shiyaChokinJikansuHyojiFlg;
	}

	public void setShiyaChokinJikansuHyojiFlg(String shiyaChokinJikansuHyojiFlg) {
		this.shiyaChokinJikansuHyojiFlg = shiyaChokinJikansuHyojiFlg;
	}

	public String getSaveBtnInUseFlg() {
		return saveBtnInUseFlg;
	}

	public void setSaveBtnInUseFlg(String saveBtnInUseFlg) {
		this.saveBtnInUseFlg = saveBtnInUseFlg;
	}
	
	
}
