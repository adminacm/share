package argo.cost.attendanceInput.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

import argo.cost.attendanceInput.checker.AttendanceInputChecker;
import argo.cost.attendanceInput.dao.AttendanceInputDao;
import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProjectVO;
import argo.cost.attendanceInput.model.HolidayAttendanceVO;
import argo.cost.attendanceInput.model.ShiftVO;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.KyukaKekin;
import argo.cost.common.entity.KyukaKekinKbnMaster;
import argo.cost.common.entity.Locations;
import argo.cost.common.entity.MCalendar;
import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.ProjWorkTimeManage;
import argo.cost.common.entity.ProjectMaster;
import argo.cost.common.entity.ShiftInfo;
import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
import argo.cost.common.entity.WorkDayKbnMaster;
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
	 * ｼﾌﾄｺｰﾄﾞの時刻データ
	 */
	private final String SHIFT_TIME_DATA = "ｼﾌﾄｺｰﾄﾞの時刻データ";
	
	/**
	 * 零時タイム
	 */
	private final String STR_REIJI = "2400";
	/**
	 * 一日開始時刻
	 */
	private final String DAY_START_TIME = "0000";
	/**
	 * 一日終了時刻
	 */
	private final String DAY_OVER_TIME = "2330";
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

		String formatDate = StringUtils.EMPTY;
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

		// 勤怠日付を設定
		form.setAttDate(date);
		// 勤怠日付のフォーマット（yyyy年MM月dd日）
		String attDate = CostDateUtils.formatDate(date, CommonConstant.YYYYMMDD_KANJI);
		// 曜日名
		String week = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(date));
		// 勤怠日付（表示）を設定
		form.setAttDateShow(attDate.concat("（").concat(week).concat("）"));
		
		// 個人倦怠プロジェクト情報リストを作成
		List<AttendanceProjectVO> attendanceProjectList = new ArrayList<AttendanceProjectVO>();
		AttendanceProjectVO attPorject = null;
		// プロジェクトリストを取得
		List<ProjectMaster> projectItemList = baseDao.findAll(ProjectMaster.class);
		// プロジェクト作業リストを取得
		List<ProjWorkMaster> workItemList = baseDao.findAll(ProjWorkMaster.class);
		
		// 検索条件
		condition = new BaseCondition();
		condition.addConditionEqual("users.id", userId);  // 社員番号
		condition.addConditionEqual("atendanceBookDate", date);  // 勤務休日
		
		// 勤怠情報を取得
		KintaiInfo kintaiEntity = baseDao.findSingleResult(condition, KintaiInfo.class);
		HolidayAtendanceYotei attYoteEntity = null;
		// 勤怠情報が存在する場合
		if (kintaiEntity != null) {
			// 勤務日区分
			String workDayKbn = kintaiEntity.getWorkDayKbnMaster().getCode();
			form.setWorkDayKbn(workDayKbn);
			// 勤務日区分名
			form.setWorkDayKbnName(kintaiEntity.getWorkDayKbnMaster().getName());
			// 休日予定勤務情報設定
			this.setHolidayAttendanceInfo(form);
			// 「出勤」または「休日振替休日」の場合
			if (StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, workDayKbn)
					|| StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, workDayKbn)) {
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
				form.setKyukaHours(toDouble(kintaiEntity.getKyukaJikansu()));
				// 勤務時間数
				form.setWorkHours(toDouble(kintaiEntity.getKinmuJikansu()));
				// 超過勤務開始時刻
				form.setChoSTime(kintaiEntity.getChokinStartTime());
				// 超過勤務終了時刻
				form.setChoETime(kintaiEntity.getChokinEndTime());
				// 平日割増
				form.setChoWeekday(toDouble(kintaiEntity.getChokinHeijituJikansu()));
				// 平日通常
				form.setChoWeekdayNomal(toDouble(kintaiEntity.getChokinHeijituTujyoJikansu()));
				// 休日
				form.setChoHoliday(toDouble(kintaiEntity.getChokinKyujituJikansu()));
				// 深夜
				form.setmNHours(toDouble(kintaiEntity.getSinyaKinmuJikansu()));
				// ロケーション情報設定
				form.setLocationId(kintaiEntity.getLocation().getCode());
				
				// 個人プロジェクト勤務情報設定
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
						attPorject.setHours(toDouble(proEntity.getWorkTimes()));
						
						attendanceProjectList.add(attPorject);
					}
				} else {
					// 空行を追加する
					attPorject = new AttendanceProjectVO();
					attPorject.setProjectItemList(projectItemList);
					attPorject.setWorkItemList(workItemList);
					attendanceProjectList.add(attPorject);
				}
			}
			
		// 当日勤怠情報が存在しない場合
		} else {
			// シフトコード
			Users userEntity = baseDao.findById(userId, Users.class);
			form.setShiftCd(userEntity.getStandardShiftCd());
			
			// 社休日の判定
			// 日付より、カレンダー情報を取得
			MCalendar calender = baseDao.findById(date, MCalendar.class);

			// カレンダー情報が存在する場合
			if (calender != null) {
				// 出勤フラグが「1」の場合
				if (StringUtils.equals(CommonConstant.WORKDAY_FLAG_SHUKIN, calender.getOnDutyFlg())) {
					// 検索条件
					condition = new BaseCondition();
					condition.addConditionEqual("users.id", userId);  // 社員番号
					condition.addConditionEqual("furikaeDate", date);  // 振替日
					attYoteEntity = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
					
					// 休日予定勤務情報が存在する場合
					if (attYoteEntity != null) {
						// 勤務日区分
						form.setWorkDayKbn(CommonConstant.WORKDAY_KBN_FURIKAE_KYUJITU);
						// 勤務日区分名
						form.setWorkDayKbnName(findNameByCode(CommonConstant.WORKDAY_KBN_FURIKAE_KYUJITU));
					} else {
						// 勤務日区分
						form.setWorkDayKbn(CommonConstant.WORKDAY_KBN_SHUKIN);
						// 勤務日区分名
						form.setWorkDayKbnName(findNameByCode(CommonConstant.WORKDAY_KBN_SHUKIN));
					}
				// 出勤フラグが「0」の場合
				} else if (StringUtils.equals(CommonConstant.WORKDAY_FLAG_KYUJITU, calender.getOnDutyFlg())) {
					
					// 休日予定勤務情報が存在しない場合
					if (!setHolidayAttendanceInfo(form)) {
						// 勤務日区分
						form.setWorkDayKbn(CommonConstant.WORKDAY_KBN_KYUJITU);
						// 勤務日区分名
						form.setWorkDayKbnName(findNameByCode(CommonConstant.WORKDAY_KBN_KYUJITU));
					} else {
						// 勤務日区分
						form.setWorkDayKbn(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE);
						// 勤務日区分名
						form.setWorkDayKbnName(findNameByCode(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE));
					}
				}
			}
			// 個人プロジェクト作業情報
			// 空行を追加する
			attPorject = new AttendanceProjectVO();
			attPorject.setProjectItemList(projectItemList);
			attPorject.setWorkItemList(workItemList);
			attendanceProjectList.add(attPorject);
		}

		// 休暇欠勤区分リストを取得
		List<KyukaKekinKbnMaster> kyukakbList = baseDao.findAll(KyukaKekinKbnMaster.class);
		form.setKyukakbList(kyukakbList);
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
	 * @throws Exception 
	 */
	@Override
	public Integer updateAttdendanceInfo(AttendanceInputForm form) throws Exception {

		// 計算を実行する
		try {
			this.calcWorkingRec(form);
		} catch (ParseException e) {
			// 更新失敗
			return 0;
		}
		// エラーが発生する場合
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			// 更新失敗
			return 0;
		}

		String userId = form.getUserId();
		String date = form.getAttDate();
		// 検索条件
		BaseCondition condition = new BaseCondition();
		condition.addConditionEqual("users.id", userId);  // 社員番号
		condition.addConditionEqual("atendanceBookDate", date);  // 勤務休日
		
		// 勤怠情報を取得 TODO
		KintaiInfo kintaiEntity = baseDao.findSingleResult(condition, KintaiInfo.class);
		// 勤怠データを更新
		saveKintaiInfo(form, kintaiEntity);

		
		// 休暇欠勤区分：「01,02,03,04,07」の場合
		if (StringUtils.equals(form.getKyukaKb(), CommonConstant.KK_KBN_ZENKYU)
				|| StringUtils.equals(form.getKyukaKb(), CommonConstant.KK_KBN_HANKYU)
				|| StringUtils.equals(form.getKyukaKb(), CommonConstant.KK_KBN_JIKANKYU)
				|| StringUtils.equals(form.getKyukaKb(), CommonConstant.KK_KBN_TOKUBETUKYU)
				|| StringUtils.equals(form.getKyukaKb(), CommonConstant.KK_KBN_CHIKOKU)) {
			// 休暇欠勤情報を取得
			condition = new BaseCondition();
			condition.addConditionEqual("users.id", userId);  // 社員番号
			condition.addConditionEqual("kyukaDate", date);   // 休暇日付
			KyukaKekin kyukaKekin = baseDao.findSingleResult(condition, KyukaKekin.class);
			// 休暇欠勤管理情報管理
			saveKyukaKekinInfo(form, kyukaKekin);
		}
		
		// 勤務日区分：「03（休日振替勤務）」
		if (StringUtils.equals(form.getWorkDayKbn(), CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE)) {

			// 休日勤務情報を取得
			condition = new BaseCondition();
			condition.addConditionEqual("users.id", userId);          // 社員番号
			condition.addConditionEqual("holidaySyukinDate", date);   // 日付
			HolidayAtendance holidayAtendanceInfo = baseDao.findSingleResult(condition, HolidayAtendance.class);
			// 休日勤務情報を管理する
			saveHolidayAttInfo(userId, date, holidayAtendanceInfo);
			
		}
		
//		// 超勤情報を取得
//		condition = new BaseCondition();
//		condition.addConditionEqual("users.id", userId);          // 社員番号
//		condition.addConditionEqual("holidaySyukinDate", date);   // 日付
//		// 超勤情報をクリアする
//		baseDao.deleteByCondition(condition, ChokinKanri.class);
//		List<ChokinKanri> chokinList = baseDao.findResultList(condition, ChokinKanri.class);
		
		// 勤怠情報テーブルの更新
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
		
		// 入力チェック
		doInputCheck(form);
		// エラーが発生されているの場合
		if (!StringUtils.isEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分
		String kyukaKbn = form.getKyukaKb();
		// 勤務時間取得
		getWorkHours(form);
		// 「時間休(有給休暇)」または「遅刻・早退」の場合
		if (StringUtils.equals(CommonConstant.KK_KBN_JIKANKYU, kyukaKbn) 
				|| StringUtils.equals(CommonConstant.KK_KBN_CHIKOKU, kyukaKbn)) {
			// 休暇時間数を算出する
			getRestHours(form, form.getShiftInfo());
		}
		// 休暇欠勤区分・勤務時刻:有給休暇の取得限度を超えています
		AttendanceInputChecker.chkKykaKbn002(form);
		// エラーが発生されているの場合
		if (!StringUtils.isEmpty(form.getConfirmMsg())) {
			return;
		}
		
		// 超勤時間帯の勤務時間数を算出する
		getChokin(form);
		// 深夜勤務時間数を算出する
		getMidnight(form);

	}
	
	public String findNameByCode(String code) {
		
		// 勤務日マスタ情報を取得
		WorkDayKbnMaster entity = baseDao.findById(code, WorkDayKbnMaster.class);
		
		// 勤務日マスタ情報が存在する場合
		if (entity != null) {
			return entity.getName();
		}
		return StringUtils.EMPTY;
	}
// ======================================================================
// 私有メソッド
// ======================================================================
	/**
	 * 勤務時間数を取得する
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getWorkHours(AttendanceInputForm form) throws ParseException {

		// 勤務時間取得
		Double hours = 0.0;
		// シフトコード
		String shiftCode = form.getShiftCd();
		// 勤務開始時刻
		String wStime = form.getWorkSTime();
		// 勤務終了時刻
		String wEtime = form.getWorkETime();
		// 勤務開始時刻(hhnn)
		String wSTimeStr = form.getWorkSTimeStr();
		// 勤務終了時刻(hhnn)
		String wETimeStr = form.getWorkETimeStr();

		// 勤務開始から勤務終了が24:00をまたいでいる場合
		if (wSTimeStr.compareTo(STR_REIJI) < 0
				&& STR_REIJI.compareTo(wETimeStr) < 0) {
			hours = countWorkTime(shiftCode, wSTimeStr, DAY_OVER_TIME, 0) 
					+ countWorkTime(shiftCode, DAY_START_TIME, wEtime, 1);
		} else {

			// 勤務開始時刻 ~ 勤務終了時刻
			hours = countWorkTime(shiftCode, wStime,
					wEtime, 1);
		}
		// 勤務時間
		form.setWorkHours(hours);
	}

	/**
	 * 休暇時間を取得する
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @param shiftinfo
	 *            シフト情報
	 * @throws ParseException 
	 */
	private void getRestHours(AttendanceInputForm form, ShiftVO shiftinfo) throws ParseException {

		// 遅刻時刻
		Double wChikoku = 0.0;
		// 早退時刻
		Double wSotai = 0.0;
		// シフトコード
		String shiftCode = form.getShiftCd();
		// 定時出勤時刻(hhnn)
		String standSTimeStr = shiftinfo.getStartTimeStr();
		// 定時退勤時刻(hhnn)
		String standETimeStr = shiftinfo.getEndTimeStr();
		// 勤務開始時刻
		String wStime = form.getWorkSTime();
		// 勤務終了時刻
		String wEtime = form.getWorkETime();
		// 勤務開始時刻(hhnn)
		String wSTimeStr = form.getWorkSTimeStr();
		// 勤務終了時刻(hhnn)
		String wETimeStr = form.getWorkETimeStr();

		// 遅刻の時間数をカウントする
		if (standSTimeStr.compareTo(wSTimeStr) < 0) {

			// 定時出勤時刻から勤務開始時刻が24:00をまたいでいる場合
			if (standSTimeStr.compareTo(STR_REIJI) < 0
					&& STR_REIJI.compareTo(wSTimeStr) < 0) {
				
				// 定時出勤時刻～23:30＋"0000"～勤務開始時刻
				wChikoku = countRestTime(shiftCode, standSTimeStr, DAY_OVER_TIME, 0)
						+ countRestTime(shiftCode, DAY_START_TIME, wStime, 1);

			} else {

				// 定時出勤時刻 ~ 勤務開始時刻
				wChikoku = countRestTime(shiftCode, standSTimeStr, wStime, 1);
			}
		}
		
		// 早退の時間数をカウントする
		if (wETimeStr.compareTo(standETimeStr) < 0) {

			// 24:00をまたいでいる場合
			if (wETimeStr.compareTo(STR_REIJI) < 0
					&& STR_REIJI.compareTo(standETimeStr) < 0) {
				
				// 勤務終了時刻~23:30 + "0000"~定時退勤時刻
				wSotai = countRestTime(shiftCode, wEtime, DAY_OVER_TIME, 0)
						+ countRestTime(shiftCode, DAY_START_TIME, shiftinfo.getEndTime(), 1);

			} else {

				// 勤務終了時刻 ~ 定時退勤時刻
				wSotai = countRestTime(shiftCode, wEtime, shiftinfo.getEndTime(), 1);
			}
		}

		Double hours = new BigDecimal(wChikoku + wSotai).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		// 休暇時間
		form.setKyukaHours(hours);
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
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		// 勤務終了時刻
		String wETime = form.getWorkETime();
		// 勤務終了時刻(hhnn)
		String wETimeStr = form.getWorkETimeStr();
		
		// 勤務終了時刻が定時退勤時刻を越えている場合超勤時間数を取得
		if (shift.getChokinSTimeStr().compareTo(wETimeStr) < 0) {
			
			// シフト超勤開始時刻から勤務終了が24:00をまたいでいる場合
			if (shift.getChokinSTimeStr().compareTo(STR_REIJI) < 0
					&& STR_REIJI.compareTo(wETimeStr) < 0) {
				// シフト超勤開始時刻→23:30＋"0000"→勤務終了時刻
				wChokinHours = countRestTime(shift.getCode(), shift.getChokinSTime(), DAY_OVER_TIME, 0)
						+ countRestTime(shift.getCode(), DAY_START_TIME, wETime, 1);
				
			} else {
				// シフト超勤開始時刻→勤務終了時刻
				wChokinHours = countRestTime(shift.getCode(), shift.getChokinSTime(), wETime, 1);
			}
			
			// １時間未満の超勤は切り捨て
			if (wChokinHours < 1) {
				form.setChoSTime(StringUtils.EMPTY);
				form.setChoETime(StringUtils.EMPTY);
				form.setChoHoliday(0.0);
				form.setChoWeekday(0.0);
				form.setChoWeekdayNomal(0.0);
			} else {
				// 超勤開始時刻＝シフト超勤開始時刻
				form.setChoSTime(shift.getChokinSTime());
				// 超勤終了時刻＝勤務終了時刻
				form.setChoETime(wETime);
				// 休日か平日の判断
				// 休日振替勤務の場合
				if (StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, form.getWorkDayKbn())) {
					// 超勤平日（割増）
					form.setChoWeekday(0.0);
					// 超勤平日（通常）
					form.setChoWeekdayNomal(0.0);
					// 超勤休日を設定する
					form.setChoHoliday(wChokinHours);
				// 超勤時間から割増部分と通常部分の切り分けを実施
				} else if (StringUtils.isEmpty(form.getKyukaKb())) {
					// 超勤平日（割増）
					form.setChoWeekday(wChokinHours);
					// 超勤平日（通常）
					form.setChoWeekdayNomal(0.0);
					// 超勤休日を設定
					form.setChoHoliday(0.0);
				// 休暇欠勤区分＝半休(有給休暇)
				} else if (StringUtils.equals(CommonConstant.KK_KBN_HANKYU, form.getKyukaKb())) {
					// 勤務時間が7.5時間以下だった場合はすべて通常として扱う
					if (form.getWorkHours() <= 7.5) {
						// 超勤平日（割増）
						form.setChoWeekday(0.0);
						// 超勤平日（通常）
						form.setChoWeekdayNomal(wChokinHours);
					} else {
						// 超勤平日（割増）
						form.setChoWeekday(form.getWorkHours() - 7.5);
						// 超勤平日（通常）
						form.setChoWeekdayNomal(wChokinHours - form.getChoWeekday());
					}
				// 休暇欠勤区分＝時間休(有給休暇)、時間休の場合、使用した時間数があるのでそこから算出
				} else if (StringUtils.equals(CommonConstant.KK_KBN_JIKANKYU, form.getKyukaKb())) {
					// 勤務時間が7.5時間以下だった場合はすべて通常として扱う
					if (form.getWorkHours() <= 7.5) {
						// 超勤平日（割増）
						form.setChoWeekday(0.0);
						// 超勤平日（通常）
						form.setChoWeekdayNomal(wChokinHours);
					} else {
						// 超勤平日（割増）
						form.setChoWeekday(form.getWorkHours() - form.getKyukaHours());
						// 超勤平日（通常）：超勤時間 - 通常として扱った時間を割増時間として扱う
						form.setChoWeekdayNomal(wChokinHours - form.getKyukaHours());
					}
				}
			}
		} else {
			// 休日出勤は7.5h未満勤務は超勤とする
			if ((StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU, form.getWorkDayKbn())
					|| StringUtils.equals(CommonConstant.WORKDAY_KBN_FURIKAE_KYUJITU, form.getWorkDayKbn()))
					&& form.getWorkHours() < 7.5) {
				
				form.setChoHoliday(form.getWorkHours());
				
			}
		}
	}

	/**
	 * 深夜勤務時間数を取得する
	 * 
	 * @param form 勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getMidnight(AttendanceInputForm form) throws ParseException {

		ShiftVO shift = form.getShiftInfo();
		// 深夜勤務時間
		Double wMNHours = 0.0;
		
		// 勤務開始から勤務終了が24:00をまたぐとき
		if (form.getWorkSTimeStr().compareTo(STR_REIJI) < 0 
				&& STR_REIJI.compareTo(form.getWorkETimeStr()) < 0) {
			// 勤務開始時刻→23:30＋"0000"→勤務終了時刻
			wMNHours = getMNHours(shift.getCode(), form.getWorkSTime(), DAY_OVER_TIME, 0)
					+ getMNHours(shift.getCode(), DAY_START_TIME, form.getWorkETime(), 1);
		} else {
			// 勤務開始時刻→勤務終了時刻
			wMNHours = getMNHours(shift.getCode(), form.getWorkSTime(), form.getWorkETime(), 1);
		}
		
		// 深夜を設定する
		if (wMNHours > 0) {
			form.setmNHours(wMNHours);
		} else {
			form.setmNHours(0.0);
		}
		
	}

	/**
	 * シフト情報を取得する
	 * 
	 * @param form
	 *            画面情報
	 * @param shiftCode
	 *            シフトコード
	 * @return シフト情報
	 */
	private ShiftVO getShiftInfo(AttendanceInputForm form, String shiftCode) {

		// ｼﾌﾄｺｰﾄﾞが未入力なら"0900"を使用する
		if (StringUtils.isEmpty(shiftCode)) {
			shiftCode = "0900";
		}
		BaseCondition condition = new BaseCondition();
		condition.addConditionEqual("shiftCode", shiftCode);
		List<ShiftInfo> shiftList = baseDao.findResultList(condition, ShiftInfo.class);
		if (shiftList == null || shiftList.isEmpty()) {
			// ｼﾌﾄｺｰﾄﾞを正しく入力してください
			 form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {"シフトコード"});
			 return null;
		}
		ShiftVO info = null;
		// シフト時刻情報を取得
		ShiftJikoku entity = baseDao.findById(shiftCode, ShiftJikoku.class);
		// シフト時刻からレコードが取得できない
		if (entity == null) {
			// ｼﾌﾄｺｰﾄﾞの時刻データが設定されていません
			form.putConfirmMsg(MessageConstants.COSE_E_005, new String[] {SHIFT_TIME_DATA});
			return null;
		} else {
			String standSTime = entity.getTeijiKinmuTime();               // 定時出勤時刻
			String amETime = entity.getAmEndTime();                       // 午前終了時刻
			String pmSTime = entity.getPmStartTime();                     // 午後開始時刻
			String standEtime = entity.getTeijiTaikinTime();              // 定時退勤時刻
			String chokinSTime = entity.getChokinStartTime();             // 超勤開始時刻
			String standSTimeStr = entity.getTeijiKinmuTime();            // 定時出勤時刻(hhnn)
			
			// 午前終了時刻(hhnn)
			String amETimeStr = amETime;
			// 午前終了時刻＜＝定時出勤時刻(hhnn)
			if (amETime.compareTo(standSTimeStr) <= 0) {
				amETimeStr = CostDateUtils.AddForOneDay(amETime);
			}
			// 午後開始時刻(hhnn)
			String pmSTimeStr = pmSTime;
			// 午後開始時刻＜＝定時出勤時刻(hhnn)
			if (pmSTime.compareTo(standSTimeStr) <= 0) {
				pmSTimeStr = CostDateUtils.AddForOneDay(pmSTime);
			}
			// 定時退勤時刻(hhnn)
			String standEtimeStr = standEtime;
			// 定時退勤時刻＜＝定時出勤時刻(hhnn)
			if (standEtime.compareTo(standSTimeStr) <= 0) {
				standEtimeStr = CostDateUtils.AddForOneDay(standEtime);
			}
			// シフト超勤開始時刻(hhnn)
			String chokinSTimeStr = chokinSTime;
			// シフト超勤開始時刻＜＝定時出勤時刻(hhnn)
			if (chokinSTime.compareTo(standSTimeStr) <= 0) {
				chokinSTimeStr = CostDateUtils.AddForOneDay(chokinSTime);
			}
			
			info = new ShiftVO();
			
			// シフトコード
			info.setCode(shiftCode);
			// 定時出勤時刻
			info.setStartTime(standSTime);
			// 定時出勤時刻
			info.setStartTimeStr(standSTimeStr);
			// 午前終了時刻
			info.setAmETime(amETime);
			// 午前終了時刻
			info.setAmETimeStr(amETimeStr);
			// 午後開始時刻
			info.setFmSTime(pmSTime);
			// 午後開始時刻
			info.setFmSTimeStr(pmSTimeStr);
			// 定時退勤時刻
			info.setEndTime(standEtime);
			// 定時退勤時刻
			info.setEndTimeStr(standEtimeStr);
			// 超勤開始時刻
			info.setChokinSTime(chokinSTime);
			// 超勤開始時刻
			info.setChokinSTimeStr(chokinSTimeStr);
		}
		
		return info;

	}
	
	/**
	 * BigDecimalからDoubleに変換
	 * 
	 * @param b 
	 * 				BigDecimal型のデータ
	 * 
	 * @return Double型のデータ
	 */
	private Double toDouble(BigDecimal b) {
		
		// Nullの場合
		if (b == null) {
			return new Double(0.0);
		} else {
			return b.doubleValue();
		}
	}

	/**
	 * DoubleからBigDecimalに変換
	 * 
	 * @param d 
	 * 				Double型のデータ
	 * 
	 * @return BigDecimal型のデータ
	 */
	private BigDecimal toBigDecimal(Double d) {
		
		// Nullの場合
		if (d == null) {
			return new BigDecimal(0.0);
		} else {
			return new BigDecimal(d);
		}
	}
	/**
	 * シフトテーブルから作業時間数を取得する
	 * 
	 * @param shiftCode 
	 * 				シフトコード
	 * @param sTime 
	 * 				作業開始時刻
	 * @param eTime 
	 * 				作業終了時刻
	 * @param flag 
	 * 				計算フラグ（0：「<=A」;1:「< A」）
	 * 
	 * @return 作業時間数
	 */
	private Double countWorkTime(String shiftCode, String sTime, String eTime, int flag) {
		
		return attendanceInputDao.countWorkTime(shiftCode, sTime, eTime, flag);
	}

	/**
	 * シフトテーブルから休暇時間数を取得する
	 * 
	 * @param shiftCode 
	 * 				シフトコード
	 * @param sTime 
	 * 				作業開始時刻
	 * @param eTime 
	 * 				作業終了時刻
	 * @param flag 
	 * 				遅刻・早退フラグ(0:遅刻；1:早退)
	 * 
	 * @return 休暇時間数
	 */
	private Double countRestTime(String shiftCode, String sTime, String eTime, int flag) {
		
		return attendanceInputDao.countWorkTime(shiftCode, sTime, eTime, flag);
	}
	
	/**
	 * シフトテーブルから深夜時間数を取得する
	 * 
	 * @param shiftCode 
	 * 				シフトコード
	 * @param sTime 
	 * 				作業開始時刻
	 * @param eTime 
	 * 				作業終了時刻
	 * @param flag 
	 * 				計算フラグ（0：「<=A」;1:「< A」）
	 * 
	 * @return 深夜勤務時間数
	 */
	private Double getMNHours(String shiftCode, String sTime, String eTime, int flag) {
		
		BaseCondition condition = new BaseCondition();
		
		condition.addConditionEqual("shiftCode", shiftCode);

		condition.addConditionGreaterEqualThan("timeZoneCode", sTime);
		// 終了時刻
		if (flag == 0) {
			condition.addConditionLessEqualThan("timeZoneCode", eTime);
		} else {
			condition.addConditionLessThan("timeZoneCode", eTime);
		}
		
		List<ShiftInfo> list1 = baseDao.findResultList(condition, ShiftInfo.class);
		
		condition.addConditionEqual("shiftCode", "MN");
		List<ShiftInfo> list2 = baseDao.findResultList(condition, ShiftInfo.class);
		Double hours = 0.0;
		if (list1 != null && list2 != null) {
			
			for (ShiftInfo info1 : list1) {
				for (ShiftInfo info2 : list2) {
					// 同一時刻の場合
					if (StringUtils.equals(info1.getTimeZoneCode(), info2.getTimeZoneCode())) {
						// 勤務フラグが”１”の場合
						if (StringUtils.equals(info1.getKinmuFlg(), CommonConstant.WORKDAY_FLAG_SHUKIN)
								&& StringUtils.equals(info2.getKinmuFlg(), CommonConstant.WORKDAY_FLAG_SHUKIN)) {
							hours = hours + 0.5;
						}
					}
				}
			}
		}
		
		return hours;
	}

	/**
	 * 休日予定勤務情報を設定する
	 * 
	 * @param form 
	 * 				勤怠入力画面情報
	 * 
	 */
	private Boolean setHolidayAttendanceInfo(AttendanceInputForm form) {
		
		Boolean flg = false;
		// 検索条件
		BaseCondition condition = new BaseCondition();
		condition.addConditionEqual("users.id", form.getUserId());  // 社員番号
		condition.addConditionEqual("atendanceBookDate", form.getAttDate());  // 勤務休日
		HolidayAtendanceYotei attYoteEntity = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
		
		// 休日予定勤務情報
		HolidayAttendanceVO attendanceVO = new HolidayAttendanceVO();
		if (attYoteEntity != null) {
			// 社員番号
			attendanceVO.setUserId(form.getUserId());
			// 休日勤務日
			attendanceVO.setAttendanceDate(form.getAttDate());
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
			attendanceVO.setProjectId(attYoteEntity.getProjectMaster().getCode());
			// プロジェクト名称
			attendanceVO.setProjectName(attYoteEntity.getProjectMaster().getName());
			//　作業内容
			attendanceVO.setWorkDetail(attYoteEntity.getCommont());
			flg = true;
		}
		
		// 休日振替勤務
		form.setHolidayAttendance(attendanceVO);
		
		return flg;
	}
	
	/**
	 * 勤怠入力画面入力チェック
	 * 
	 * @param form 
	 * 				勤怠入力画面情報
	 * 
	 */
	private void doInputCheck(AttendanceInputForm form) {
		// シフトコードより、シフト情報を取得
		ShiftVO shiftinfo = getShiftInfo(form, form.getShiftCd());
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		form.setShiftInfo(shiftinfo);

		// 勤務開始時刻の型チェックとフォーマット
		AttendanceInputChecker.chkWorkSTimeFormat(form);
		// 勤務終了時刻の型チェックとフォーマット
		AttendanceInputChecker.chkWorkETimeFormat(form);
		// 休暇欠勤区分・勤務時刻:勤怠が未入力です
		AttendanceInputChecker.chkKykaKbnAndWorkTime05(form);
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分・勤務時刻:勤務開始時刻・終了時刻を入力してください
		AttendanceInputChecker.chkKykaKbnAndWorkTime01(form);
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分・勤務時刻:取得できる代休はありません
		AttendanceInputChecker.chkKykaKbn001(form);
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 勤務時刻の整合性チェック
		try {
			AttendanceInputChecker.chkWorkTimeFormat(form);
		} catch (ParseException e) {
			// 自動生成された catch ブロック
			e.printStackTrace();
		}
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分・勤務時刻:定時時間帯の勤務時間数が7.5h未満です。休暇区分も入力してください
		AttendanceInputChecker.chkKykaKbnAndWorkTime02(form);
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分・勤務時刻:正しい休暇区分を入力してください
		AttendanceInputChecker.chkKykaKbnAndWorkTime03(form);
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分・勤務時刻:有給休暇が余分に取得されています
		AttendanceInputChecker.chkKykaKbn003(form);
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分・勤務時刻:終日休暇の日は勤務できません
		AttendanceInputChecker.chkKykaKbnAndWorkTime06(form);
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分・勤務時刻:休暇欠勤区分が入力されています
		AttendanceInputChecker.chkKykaKbnAndWorkTime04(form);
		// エラーを発生した時
		if (StringUtils.isNotEmpty(form.getConfirmMsg())) {
			return;
		}
		// 休暇欠勤区分・勤務時刻:休日の勤務開始は定時出勤時刻を入力してください
		AttendanceInputChecker.chkKykaKbnAndShiftCode(form);

	}
	
	/**
	 * 勤怠入力画面入力チェック
	 * 
	 * @param form 
	 * 				勤怠入力画面情報
	 * @throws ParseException 
	 * 
	 */
	private String getDaikyuKiGen(String yyyymmdd) throws ParseException {
		
		Calendar c = Calendar.getInstance();
		c.setTime(CostDateUtils.toDate(yyyymmdd));
		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 3);
		c.add(Calendar.DATE, -1);
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH)+1);
		String date = String.valueOf(c.get(Calendar.DATE));
	
		return year + month + date;
	}
	
	private void saveKyukaKekinInfo(AttendanceInputForm form, KyukaKekin kyukaKekin) throws Exception {
		
		String userId = form.getUserId();
		String date = form.getAttDate();
		// 更新フラグ
		boolean flag = true;  
		// 休暇欠勤情報が存在しない場合
		if (kyukaKekin == null) {
			flag = false;
			// 休暇欠勤情報を作成
			kyukaKekin = new KyukaKekin();
			kyukaKekin.setCreatedUserId(userId);               // 登録者
			kyukaKekin.setCreatedDate(new Timestamp(System.currentTimeMillis())); // 登録時刻
		}
		// 社員番号より、社員情報を取得
		Users userEntity = baseDao.findById(userId, Users.class);
		kyukaKekin.setUsers(userEntity);                    // 社員番号
		kyukaKekin.setKyukaDate(date);                      // 日付
		// 休暇欠勤マスタ情報を取得
		KyukaKekinKbnMaster kyukaEntity = baseDao.findById(form.getKyukaKb(), KyukaKekinKbnMaster.class);

		kyukaKekin.setKyukaKekinKbnMaster(kyukaEntity);     // 休暇欠勤区分
		// 全休(有給休暇)
		if (StringUtils.equals(form.getKyukaKb(), CommonConstant.KK_KBN_ZENKYU)) {
			kyukaKekin.setKyukaJikansu(new BigDecimal(8.0)); // 休暇欠勤時間数
		// 半休(有給休暇)
		} else if (StringUtils.equals(form.getKyukaKb(), CommonConstant.KK_KBN_HANKYU)) {
			kyukaKekin.setKyukaJikansu(new BigDecimal(4.0)); // 休暇欠勤時間数
		// 上記以外の場合
		} else {
			kyukaKekin.setKyukaJikansu(toBigDecimal(form.getKyukaHours())); // 休暇欠勤時間数
		}
		kyukaKekin.setSyoriFlag(new BigDecimal(0));        // 集計処理フラグ

		kyukaKekin.setUpdatedUserId(userId);               // 更新者
		kyukaKekin.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新時刻

		// 更新の場合
		if (flag) {
			// 休暇欠勤情報を登録する
			baseDao.update(kyukaKekin);
		} else {
			// 休暇欠勤情報を登録する
			baseDao.insert(kyukaKekin);
		}
	}

	private void saveKintaiInfo(AttendanceInputForm form, KintaiInfo kintaiEntity) throws Exception {
		
		String userId = form.getUserId();
		String date = form.getAttDate();
		// 更新フラグ
		boolean flag = true;  
		// 勤怠情報が存在しない場合
		if (kintaiEntity == null) {
			flag = false;
			// 勤怠情報を登録する
			kintaiEntity = new KintaiInfo();
			kintaiEntity.setCreatedUserId(userId);               // 登録者
			kintaiEntity.setCreatedDate(new Timestamp(System.currentTimeMillis())); // 登録時刻
		}
		// 社員番号より、社員情報を取得
		Users userEntity = baseDao.findById(userId, Users.class);
		kintaiEntity.setUsers(userEntity);                   // ユーザー情報
		kintaiEntity.setAtendanceBookDate(date);             // 勤務日
		
		// 勤務日区分情報を取得
		WorkDayKbnMaster workDayEntity = baseDao.findById(form.getWorkDayKbn(), WorkDayKbnMaster.class);
		kintaiEntity.setWorkDayKbnMaster(workDayEntity);     // 勤務日区分情報
		
		// シフト時刻情報を取得
		ShiftJikoku shiftEntity = baseDao.findById(form.getShiftCd(), ShiftJikoku.class);
		kintaiEntity.setShiftJikoku(shiftEntity);            // シフト時刻情報
		kintaiEntity.setKinmuStartTime(form.getWorkSTime()); // 勤務開始時刻
		kintaiEntity.setKinmuEndTime(form.getWorkETime());   // 勤務終了時刻
		
		// 休暇欠勤マスタ情報を取得
		KyukaKekinKbnMaster kyukaEntity = baseDao.findById(form.getKyukaKb(), KyukaKekinKbnMaster.class);
		kintaiEntity.setKyukaKekinKbnMaster(kyukaEntity);    // 休暇欠勤マスタ情報
		kintaiEntity.setKyukaJikansu(toBigDecimal(form.getKyukaHours()));    // 休暇時間数
		kintaiEntity.setKinmuJikansu(toBigDecimal(form.getWorkHours()));     // 勤務時間数
		kintaiEntity.setChokinStartTime(form.getChoSTime()); // 超勤開始時刻
		kintaiEntity.setChokinEndTime(form.getChoETime());   // 超勤終了時刻
		kintaiEntity.setChokinHeijituJikansu(toBigDecimal(form.getChoWeekday()));  // 超勤平日時間数
		kintaiEntity.setChokinHeijituTujyoJikansu(toBigDecimal(form.getChoWeekdayNomal())); // 超勤平日_通常時間数
		kintaiEntity.setChokinKyujituJikansu(toBigDecimal(form.getChoHoliday()));  // 超勤休日時間数
		kintaiEntity.setSinyaKinmuJikansu(toBigDecimal(form.getmNHours()));        // 深夜時間数
		
		// ロケーション情報を取得
		Locations location = baseDao.findById(form.getLocationId(), Locations.class);
		kintaiEntity.setLocation(location);                  // ロケーション情報
		kintaiEntity.setUpdatedUserId(userId);               // 更新者
		kintaiEntity.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新時刻
		
		// 更新の場合
		if (flag) {
			// 勤怠情報を登録する
			baseDao.update(kintaiEntity);
		} else {
			// 勤怠情報を登録する
			baseDao.insert(kintaiEntity);
		}
	}
	
	private void saveHolidayAttInfo(String userId, String date, HolidayAtendance holidayAtendanceInfo) throws Exception {
		
		// 更新フラグ
		boolean flag = true;
		// 休日勤務が存在しない場合
		if (holidayAtendanceInfo == null) {
			flag = false;
			// 休日勤務情報を追加する
			holidayAtendanceInfo = new HolidayAtendance();
			holidayAtendanceInfo.setCreatedUserId(userId);               // 登録者
			holidayAtendanceInfo.setCreatedDate(new Timestamp(System.currentTimeMillis())); // 登録時刻
			
		}
		// 社員番号より、社員情報を取得
		Users userEntity = baseDao.findById(userId, Users.class);
		holidayAtendanceInfo.setUsers(userEntity);                    // 社員番号
		holidayAtendanceInfo.setHolidaySyukinDate(date);              // 日付
		holidayAtendanceInfo.setDaikyuGetShimekiriDate(getDaikyuKiGen(date)); // 代休期限日
		holidayAtendanceInfo.setSyukeiFlg("0");
		holidayAtendanceInfo.setUpdatedUserId(userId);               // 更新者
		holidayAtendanceInfo.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新時刻
		// 更新の場合
		if (flag) {
			baseDao.update(holidayAtendanceInfo);
		} else {
			baseDao.insert(holidayAtendanceInfo);
		}
	}

}
