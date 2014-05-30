package argo.cost.attendanceInput.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceInput.dao.AttendanceInputDao;
import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProjectVO;
import argo.cost.attendanceInput.model.HolidayAttendanceVO;
import argo.cost.attendanceInput.model.ShiftInfo;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.KyukaKekinKbnMaster;
import argo.cost.common.entity.Locations;
import argo.cost.common.entity.MCalendar;
import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.ProjWorkTimeManage;
import argo.cost.common.entity.ProjectMaster;
import argo.cost.common.service.ComService;
import argo.cost.common.utils.CostDateUtils;

/**
 * 勤怠入力サービス実現するクラス
 * 勤怠入力に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Service
public class AttendanceInputServiceImpl implements AttendanceInputService {

	/** 定数 */
	// 昨日
	private final String LAST = "last";

	// 明日
	private final String NEXT = "next";
	/**
	 * 共通サービスです。
	 */
	@Autowired
	protected ComService comService;
	/**
	 * 勤怠入力DAO
	 */
	@Autowired
	AttendanceInputDao attendanceInputDao;
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;

	/**
	 * 
	 * 日付の変換処理
	 * 
	 * @param changeFlg
	 *            変換フラグ
	 * @param date
	 *            日付
	 * @return 年月
	 * @throws ParseException
	 */
	@Override
	public String changeDate(String changeFlg, String yyyymmdd)
			throws ParseException {

		String formatDate = "";
		Calendar calendar = new GregorianCalendar();
		Date date = CostDateUtils.toDate(yyyymmdd);
		calendar.setTime(date);

		// 日付の←ボタンを押すと、前の月に表示が切り替わる
		if (LAST.equals(changeFlg)) {

			calendar.add(Calendar.DATE, -1);

		} else if (NEXT.equals(changeFlg)) {

			// 日付の→ボタンを押すと、次の月に表示が切り替わる
			calendar.add(Calendar.DATE, 1);
		}

		date = calendar.getTime();

		// 日付フォーマット
		SimpleDateFormat sdfYearM = new SimpleDateFormat(
				CommonConstant.YYYYMMDD);

		// 年月設定
		formatDate = sdfYearM.format(date);

		// 年月
		return formatDate;
	}

	/**
	 * 勤怠入力画面情報設定
	 * 
	 * @param form
	 *            画面情報
	 * @param newDate
	 *            日付
	 */
	@Override
	public void setAttForm(AttendanceInputForm form, String newDate) throws ParseException {

		// 社員番号
		String userId = form.getUserId();
		// システム日付を取得
		String date = CostDateUtils.getNowDate();
		// 勤怠操作日は存在する場合
		if (!StringUtils.isEmpty(newDate)) {
			date = newDate;
		}

		// 就業データを取得
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザーID
		condition.addConditionEqual("users.id", userId);
		// 勤怠日付
		condition.addConditionEqual("atendanceBookDate", date);
		// 勤怠情報を取得
		KintaiInfo kintaiEntity = baseDao.findSingleResult(condition, KintaiInfo.class);

		// 勤怠日付を設定
		form.setAttDate(date);
		// 勤怠日付のフォーマット（yyyy年MM月dd日）
		String attDate = CostDateUtils.formatDate(date, CommonConstant.YYYYMMDD_KANJI);
		// 曜日名
		String week = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(date));
		// 勤怠日付（表示）を設定
		form.setAttDateShow(attDate.concat("（").concat(week).concat("）"));
		// 社休日の判定
		// 日付より、カレンダー情報を取得
		MCalendar calender = baseDao.findById(date, MCalendar.class);
		
		// カレンダー情報が存在する場合
		if (calender != null) {
			// 出勤日の場合
			if ("1".equals(calender.getOnDutyFlg())) {
				form.setKinmuKun(1);
			// 社休日
			} else {
				// 休日予定勤務情報を取得
				HolidayAttendanceVO attendanceVO = null;
				// 検索条件
				condition = new BaseCondition();
				condition.addConditionEqual("users.id", userId);  // 社員番号
				condition.addConditionEqual("atendanceBookDate", date);  // 予定勤務日
				HolidayAtendanceYotei attYoteEntity = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
				
				// 休日予定勤務情報が存在する場合
				if (attYoteEntity != null) {
					attendanceVO = new HolidayAttendanceVO();
					// 社員番号
					attendanceVO.setUserId(userId);
					// 休日勤務日
					attendanceVO.setAttendanceDate(date);
					// 振替日
					attendanceVO.setFurikaeDate(attYoteEntity.getFurikaeDate());
					// 勤務区分コード
					attendanceVO.setKinmuKbnCode(attYoteEntity.getWorkDayKbnMaster().getCode());
					// 勤務区分名称
					attendanceVO.setKinmuKbnName(attYoteEntity.getWorkDayKbnMaster().getName());
					// 勤務開始時間
					attendanceVO.setKinmuStartTime(attYoteEntity.getKinmuStartTime());
					// 勤務終了時間
					attendanceVO.setKinmuEndTime(attYoteEntity.getKinmuEndTime());
					// プロジェクトID
					attendanceVO.setProjectId(attYoteEntity.getProjWorkMaster().getCode());
					// プロジェクト名称
					attendanceVO.setProjectName(attYoteEntity.getProjWorkMaster().getName());
					//　作業内容
					attendanceVO.setWorkDetail(attYoteEntity.getCommont());
					form.setKinmuKun(2);
				}
				form.setHolidayAttendance(attendanceVO);
				
			}
			
		}

		// 勤怠情報が存在する場合
		if (kintaiEntity != null) {
			// シフトコード
			form.setShiftCd(kintaiEntity.getShiftJikoku().getShiftCode());
			// 勤務開始時刻
			String kinmuSTime = kintaiEntity.getKinmuStartTime();
			form.setWorkSTime(kinmuSTime);
			form.setWorkSHour(CostDateUtils.getHourOrMinute(kinmuSTime, 0));
			form.setWorkSMinute(CostDateUtils.getHourOrMinute(kinmuSTime, 1));
			// 勤務終了時刻
			String kinmuETime = kintaiEntity.getKinmuEndTime();
			form.setWorkETime(kinmuETime);
			form.setWorkEHour(CostDateUtils.getHourOrMinute(kinmuETime, 0));
			form.setWorkEMinute(CostDateUtils.getHourOrMinute(kinmuETime, 1));
			// 休暇欠勤区分
			if (kintaiEntity.getKyukaKekinKbnMaster() != null) {
				form.setKyukaKb(kintaiEntity.getKyukaKekinKbnMaster().getCode());
			}
			// 休暇時間数
			form.setKyukaHours(conBigDecimalToDouble(kintaiEntity.getKyukaJikansu()));
			// 勤務時間数
			form.setWorkHours(conBigDecimalToDouble(kintaiEntity.getKinmuJikansu()));
			// 超過勤務開始時刻
			form.setChoSTime(kintaiEntity.getChokinStartTime());
			// 超過勤務終了時刻
			form.setChoETime(kintaiEntity.getChokinEndTime());
			// 平日割増
			form.setChoWeekday(conBigDecimalToDouble(kintaiEntity.getChokinHeijituJikansu()));
			// 平日通常
			form.setChoWeekday_nomal(conBigDecimalToDouble(kintaiEntity.getChokinHeijituTujyoJikansu()));
			// 休日
			form.setChoHoliday(conBigDecimalToDouble(kintaiEntity.getChokinKyujituJikansu()));
			// 深夜
			form.setmNHours(conBigDecimalToDouble(kintaiEntity.getSinyaKinmuJikansu()));
			// ロケーション情報設定
			form.setLocationId(kintaiEntity.getLocation().getCode());
		}
		// 休暇欠勤区分リストを取得
		List<KyukaKekinKbnMaster> kyukakbList = baseDao.findAll(KyukaKekinKbnMaster.class);
		form.setKyukakbList(kyukakbList);
		
		// 個人倦怠プロジェクト情報リストを作成
		List<AttendanceProjectVO> attendanceProjectList = new ArrayList<AttendanceProjectVO>();
		AttendanceProjectVO attPorject = null;
		// プロジェクトリストを取得
		List<ProjectMaster> projectItemList = baseDao.findAll(ProjectMaster.class);
		// プロジェクト作業リストを取得
		List<ProjWorkMaster> workItemList = baseDao.findAll(ProjWorkMaster.class);
		if (kintaiEntity != null) {
			// 検索条件
			condition = new BaseCondition();
			condition.addConditionEqual("kintaiInfo.id", kintaiEntity.getId());
			List<ProjWorkTimeManage> prjList = baseDao.findResultList(condition, ProjWorkTimeManage.class);
			
			// 個人プロジェクト勤務情報が存在する場合
			if (prjList != null && !prjList.isEmpty()) {
				for (int i = 0; i < prjList.size(); i++) {
					ProjWorkTimeManage proEntity = prjList.get(i);
					attPorject = new AttendanceProjectVO();
					// プロジェクトID
					attPorject.setProjectId(proEntity.getProjectMaster().getCode());
					attPorject.setProjectItemList(projectItemList);
					//　作業ID
					attPorject.setWorkId(proEntity.getProjWorkMaster().getCode());
					attPorject.setWorkItemList(workItemList);
					// 作業時間数
					attPorject.setHours(conBigDecimalToDouble(proEntity.getWorkTimes()));
					
					attendanceProjectList.add(attPorject);
				}
			} else {
				// 空行を追加する
				attPorject = new AttendanceProjectVO();
				attPorject.setProjectItemList(projectItemList);
				attPorject.setWorkItemList(workItemList);
				attendanceProjectList.add(attPorject);
			}
		} else {
			// 空行を追加する
			attPorject = new AttendanceProjectVO();
			attPorject.setProjectItemList(projectItemList);
			attPorject.setWorkItemList(workItemList);
			attendanceProjectList.add(attPorject);
		}
		form.setProjectList(attendanceProjectList);

		// ロケーション情報リストを取得
		List<Locations> locationList = baseDao.findAll(Locations.class);
		
		form.setLocationItemList(locationList);
	}

	/**
	 * 就業データを更新
	 * 
	 * @param form
	 *            画面情報
	 */
	@Override
	public Integer updateAttdendanceInfo(AttendanceInputForm form) {
		// TODO 自動生成されたメソッド・スタブ

		return attendanceInputDao.updateAttdendanceInfo(form);
	}

	/**
	 * 各種値算出
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @throws ParseException
	 */
	@Override
	public void calcWorkingRec(AttendanceInputForm form) throws ParseException {

		// シフト情報を取得
		ShiftInfo shiftInfo = getShiftInfo(form.getShiftCd());

		form.setShiftInfo(shiftInfo);

		// 勤務時間取得
		getWHours(form);
		// 休暇時間数を算出する
		getKyukaHours(form);
		// 超勤時間帯の勤務時間数を算出する
		getChokin(form);
		// 深夜勤務時間数を算出する
		getMidnight(form);

	}

	/**
	 * 勤務時間数を取得する
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getWHours(AttendanceInputForm form) throws ParseException {

		// 勤務時間取得
		Double hours = 0.0;
		String shiftCode = form.getShiftCd();
		String wSTime = form.getWorkSHour().concat(form.getWorkSMinute());
		String wETime = form.getWorkEHour().concat(form.getWorkEMinute());

		// 勤務開始から勤務終了が24:00をまたいでいる場合
		if ("2400".compareTo(wSTime) > 0
				&& "2400".compareTo(wETime) < 0) {

			// 勤務開始時刻 ~ 2330 + 0000 ~ 勤務終了時刻
			hours = getHours(wSTime, "2330", shiftCode)
					+ getHours("0000", wETime, shiftCode);
		} else {

			// 勤務開始時刻 ~ 勤務終了時刻
			hours = getHours(wSTime, wETime,
					shiftCode);
		}
		// 勤務時間
		form.setWorkHours(hours);
	}

	/**
	 * 休暇時間を取得する
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getKyukaHours(AttendanceInputForm form) throws ParseException {

		// 遅刻時間取得
		Double wVhikoku = 0.0;
		// 早退時間取得
		Double wSotai = 0.0;
		ShiftInfo shift = form.getShiftInfo();
		String wSTime = form.getWorkSHour().concat(form.getWorkSMinute());
		String wETime = form.getWorkEHour().concat(form.getWorkEMinute());

		// 遅刻の時間数をカウントする
		if (shift.getStartTime().compareTo(wSTime) < 0) {

			// 定時出勤時刻から勤務開始時刻が24:00をまたいでいる場合
			if ("2400".compareTo(shift.getStartTime()) > 0
					&& "2400".compareTo(wSTime) < 0) {

				// 定時出勤時刻 ~ 2330 + 0000 ~ 勤務開始時刻
				wVhikoku = getHours(shift.getStartTime(), "2330",
						form.getShiftCd())
						+ getHours("0000", wSTime,
								form.getShiftCd());
			} else {

				// 定時出勤時刻 ~ 勤務開始時刻
				wVhikoku = getHours(shift.getStartTime(), wSTime,
						form.getShiftCd());
			}
		}
		// 早退の時間数をカウントする
		if (shift.getEndTime().compareTo(wETime) > 0) {

			// 24:00をまたいでいる場合
			if ("2400".compareTo(wETime) > 0
					&& "2400".compareTo(shift.getEndTime()) < 0) {

				// TODO form.getStandEtimeStr() -> shiftT.getStandEtime()
				// 勤務終了時刻 ~ 2330 + 0000 ~ 定時退勤時刻
				wSotai = getHours(wETime, "2330",
						form.getShiftCd())
						+ getHours("0000", shift.getEndTime(),
								form.getShiftCd());
			} else {

				// 勤務終了時刻 ~ 定時退勤時刻
				wSotai = getHours(wETime, shift.getEndTime(),
						form.getShiftCd());
			}
		}

		// 休暇時間
		form.setKyukaHours(wVhikoku + wSotai);
	}

	/**
	 * 超勤時間帯の勤務時間を取得する
	 * 
	 * @param form 勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getChokin(AttendanceInputForm form) throws ParseException {

		// 超勤時間
		Double wChokinHours = 0.0;
		ShiftInfo shift = form.getShiftInfo();
		String wETime = form.getWorkEHour().concat(form.getWorkEMinute());
		
		//TODO form.getChokinSTimeStr() -> shiftT.getChokinSTime()
		// シフト超勤開始時刻から勤務終了が24:00をまたいでいる場合
		if ("2400".compareTo(shift.getChokinSTime()) > 0 && "2400".compareTo(wETime) < 0) {
			
			// シフト超勤開始時刻 ~ 2330 + 0000 ~ 勤務終了時刻
			wChokinHours = getHours(shift.getChokinSTime(), "2330", shift.getCode()) + getHours("0000", wETime, shift.getCode());
		} else {
			
			// シフト超勤開始時刻 ~ 勤務終了時刻
			wChokinHours = getHours(shift.getChokinSTime(), wETime, shift.getCode());
		}
		
		// 超勤時間は１時間以上の場合
		if(wChokinHours >= 1) {
			//TODO form.getChokinSTimeStr() -> shiftT.getChokinSTime()
			// 超勤開始時刻＝シフト超勤開始時刻
			form.setChoSTime(shift.getChokinSTime());
			// 超勤終了時刻＝勤務終了時刻
			form.setChoETime(wETime);
				
			// 休日か平日の判断
			if("休日".equals(form.getKinmuKun()) || "振替休日".equals(form.getKinmuKun())){
				
				form.setChoWeekday(0.0);
				form.setChoWeekday_nomal(0.0);
				// 超勤休日を設定する
				form.setChoHoliday(wChokinHours);
				
			} else {
				// 休暇欠勤区分
				if ("".equals(form.getKyukaKb())){
					
					// 超勤平日（割増）を設定する
					form.setChoWeekday(wChokinHours);
					
				} else {
					if ("半休(有給休暇)".equals(form.getKyukaKb())){
						
						if (form.getWorkHours() <= 7.5){
							form.setChoHoliday(0.0);
							form.setChoWeekday(0.0);
							// 勤務時間が7.5時間以下だった場合はすべて通常として扱う
							form.setChoWeekday_nomal(wChokinHours);
						} else {
		
							form.setChoWeekday(form.getWorkHours() - 7.5);
							form.setChoWeekday_nomal(wChokinHours - form.getChoWeekday());
						}
						
					} else if ("時間休(有給休暇)".equals(form.getKyukaKb())){
		
						if (form.getWorkHours() <= 7.5){
							
							// 勤務時間が7.5時間以下だった場合はすべて通常として扱う
							form.setChoWeekday_nomal(wChokinHours);
						} else {
		
							// 勤務時間が7.5時間以上だった場合は休暇時間分を通常として扱う
							form.setChoWeekday_nomal(form.getKyukaHours());
							// 超勤時間 - 通常として扱った時間を割増時間として扱う
							form.setChoWeekday(wChokinHours - form.getKyukaHours());
						}
					}
				}
			}
		} else {
			form.setChoSTime("");
			form.setChoETime("");
			form.setChoHoliday(0.0);
			form.setChoWeekday(0.0);
			form.setChoWeekday_nomal(0.0);
		}
	}

	/**
	 * 深夜勤務時間数を取得する
	 * 
	 * @param form 勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getMidnight(AttendanceInputForm form) throws ParseException {

		ShiftInfo shift = form.getShiftInfo();
		// 深夜勤務時間
		Double wMNHours = 0.0;
		// 深夜勤務時間0点以前
		Double wMNHoursBe = 0.0;
		// 深夜勤務時間0点以后
		Double wMNHoursAf = 0.0;
		String wSTime = form.getWorkSHour().concat(form.getWorkSMinute());
		String wETime = form.getWorkEHour().concat(form.getWorkEMinute());
		
		// 勤務開始から勤務終了が24:00をまたぐとき
		if ("2400".compareTo(wSTime) > 0 && "2400".compareTo(wETime) < 0) {
			// 深夜勤務時間0点以前
			if ("2200".compareTo(wSTime) >= 0){
				
				wMNHoursBe = getHours("2200", "2330", shift.getCode());
			} else {
				
				wMNHoursBe = getHours(wSTime, "2330", shift.getCode());
			}
			// 深夜勤務時間0点以后
			if ("0430".compareTo(wETime) >= 0) {
				
				wMNHoursAf = getHours("0000", wETime, shift.getCode());
			} else {
				
				wMNHoursAf = getHours("0000", "0430", shift.getCode());
			}
			// 深夜勤務時間
			wMNHours = wMNHoursBe + wMNHoursAf;
		} else {
			// 勤務開始時間と勤務終了時間が0点以前
			if ("2200".compareTo(wSTime) >= 0){
				
				if ("2330".compareTo(wETime) <= 0) {

					wMNHours = getHours("2200", "2330", shift.getCode());
				} else {
					
					wMNHours = getHours("2200", wETime, shift.getCode());
				}
				
			} else {

				if ("2330".compareTo(wETime) <= 0) {

					wMNHours = getHours(wSTime, "2330", shift.getCode());
				} else {
					
					wMNHours = getHours(wSTime, wETime, shift.getCode());
				}
			}
			
			// 勤務開始時間と勤務終了時間が0点以后
			if ("2400".compareTo(wSTime) <= 0) {
				
				if ("2830".compareTo(wETime) >= 0) {

					wMNHours = getHours(wSTime, "2830", shift.getCode());
				} else {
					
					wMNHours = getHours(wSTime, wETime, shift.getCode());
				}
			}
		}
		
		// 深夜を設定する
		if (wMNHours > 0) {
			form.setmNHours(wMNHours);
		} else {
			form.setmNHours(0.0);
		}
	
		
	}
	/**
	 * 時間を取得する
	 * 
	 * @param sTime
	 *            開始時間hhmm
	 * @param eTime
	 *            　終了時間hhmm
	 * @param shiftCode
	 *            　シフトコード
	 * @return 時間数
	 */
	private Double getHours(String sTime, String eTime, String shiftCode) {

		// TODO SQL文を利用しDBから取得する。
		// 仮
		// 終了時間hour
		Double eTimeH = 0.0;
		// 開始時間
		Double sTimeH = 0.0;
		// 時間数
		Double Hours = 0.0;

		// TODO シフト表シートを検索
		if (!eTime.isEmpty()) {
			eTimeH = Double.valueOf(eTime.substring(0, 2))
					+ Double.valueOf(eTime.substring(2, 4)) / 60;
		}
		if (!sTime.isEmpty()) {
			sTimeH = Double.valueOf(sTime.substring(0, 2))
					+ Double.valueOf(sTime.substring(2, 4)) / 60;
		}

		Hours = eTimeH - sTimeH;
		if ((Hours > 7.5
				&& "1300".compareTo(sTime) > 0)
				|| (Hours < 7.5 && "1200".compareTo(sTime) > 0 && "1300"
						.compareTo(eTime) < 0)) {
			Hours = Hours - 1;
		}

		if (Hours >= 8 || "2200".compareTo(eTime) < 0) {
			Hours = Hours - 0.5;
		}

		if ("1000".equals(sTime) || "1100".equals(sTime)
				|| "1300".equals(sTime)) {
			Hours = Hours - 0.5;
		}

		if (Hours >= 12) {
			Hours = Hours - 0.5;

		}
		// 時間差戻る
		return Hours;
	}

	private ShiftInfo getShiftInfo(String shiftCode) {

		// TODO 自動生成されたメソッド・スタブ Dao を必要
		ShiftInfo info = null;
		// シフトコードが存在しないの場合
		if (StringUtils.equals("0900", shiftCode)) {

			info = new ShiftInfo();

			// シフトコード
			info.setCode("0900");
			// 定時出勤時刻
			info.setStartTime("0900");
			// 午前終了時刻
			info.setAmETime("1200");
			// 午後開始時刻
			info.setFmSTime("1300");
			// 定時退勤時刻
			info.setEndTime("1730");
			// 超勤開始時刻
			info.setChokinSTime("1800");
		}
		
		if (StringUtils.equals("1000", shiftCode)) {

			info = new ShiftInfo();

			// シフトコード
			info.setCode("1000");
			// 定時出勤時刻
			info.setStartTime("1000");
			// 午前終了時刻
			info.setAmETime("1200");
			// 午後開始時刻
			info.setFmSTime("1300");
			// 定時退勤時刻
			info.setEndTime("1830");
			// 超勤開始時刻
			info.setChokinSTime("1900");
		}

		return info;

	}
	
	private Double conBigDecimalToDouble(BigDecimal b) {
		
		if (b == null) {
			return new Double(0.0);
		} else {
			return b.doubleValue();
		}
		
	}

}
