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
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.KyukaKekinKbnMaster;
import argo.cost.common.entity.Locations;
import argo.cost.common.entity.MCalendar;
import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.ProjWorkTimeManage;
import argo.cost.common.entity.ProjectMaster;
import argo.cost.common.entity.ShiftInfo;
import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
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
		
		// 休日予定勤務情報を取得
		HolidayAttendanceVO attendanceVO = null;
		// 検索条件
		condition = new BaseCondition();
		condition.addConditionEqual("users.id", userId);  // 社員番号
		condition.addConditionEqual("atendanceBookDate", date);  // 勤務休日
		HolidayAtendanceYotei attYoteEntity = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
		
		// 勤怠情報を取得
		KintaiInfo kintaiEntity = baseDao.findSingleResult(condition, KintaiInfo.class);
		
		// 勤怠情報が存在する場合
		if (kintaiEntity != null) {
			// 勤務日区分
			String workDayKbn = kintaiEntity.getWorkDayKbnMaster().getCode();
			form.setWorkDayKbn(workDayKbn);
			// 勤務日区分名
			form.setWorkDayKbnName(kintaiEntity.getWorkDayKbnMaster().getName());
			// 「出勤」の場合
			if ("01".equals(workDayKbn)) {
				form.setKinmuKun(1);
			// 「休日」の場合
			} else if ("02".equals(workDayKbn)) {
				form.setKinmuKun(2);
			} else if ("03".equals(workDayKbn)) {
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
				attendanceVO.setProjectId(attYoteEntity.getProjectMaster().getCode());
				// プロジェクト名称
				attendanceVO.setProjectName(attYoteEntity.getProjectMaster().getName());
				//　作業内容
				attendanceVO.setWorkDetail(attYoteEntity.getCommont());
				// 休日振替勤務
				form.setKinmuKun(3);
				form.setHolidayAttendance(attendanceVO);
			} else if ("04".equals(workDayKbn)) {
				form.setKinmuKun(4);
			}
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
			form.setChoWeekdayNomal(conBigDecimalToDouble(kintaiEntity.getChokinHeijituTujyoJikansu()));
			// 休日
			form.setChoHoliday(conBigDecimalToDouble(kintaiEntity.getChokinKyujituJikansu()));
			// 深夜
			form.setmNHours(conBigDecimalToDouble(kintaiEntity.getSinyaKinmuJikansu()));
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
				if ("1".equals(calender.getOnDutyFlg())) {
					// 検索条件
					condition = new BaseCondition();
					condition.addConditionEqual("users.id", userId);  // 社員番号
					condition.addConditionEqual("furikaeDate", date);  // 振替日
					attYoteEntity = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
					
					// 休日予定勤務情報が存在する場合
					if (attYoteEntity != null) {
						// 振替休日
						form.setKinmuKun(4);
						// 勤務日区分
						form.setWorkDayKbn("04");
						// 勤務日区分名
						form.setWorkDayKbnName("振替休日");
					} else {
						// 出勤
						form.setKinmuKun(1);
						// 勤務日区分
						form.setWorkDayKbn("01");
						// 勤務日区分名
						form.setWorkDayKbnName("出勤");
					}
				// 出勤フラグが「0」の場合
				} else if ("0".equals(calender.getOnDutyFlg())) {
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
						attendanceVO.setProjectId(attYoteEntity.getProjectMaster().getCode());
						// プロジェクト名称
						attendanceVO.setProjectName(attYoteEntity.getProjectMaster().getName());
						//　作業内容
						attendanceVO.setWorkDetail(attYoteEntity.getCommont());
						// 休日振替勤務
						form.setKinmuKun(3);
						// 勤務日区分
						form.setWorkDayKbn("03");
						// 勤務日区分名
						form.setWorkDayKbnName("休日振替勤務");
						form.setHolidayAttendance(attendanceVO);
					} else {
						// 休日
						form.setKinmuKun(2);
						// 勤務日区分
						form.setWorkDayKbn("02");
						// 勤務日区分名
						form.setWorkDayKbnName("休日");
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
	 * 入力チェック（計算）
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 */
	@Override
	public Boolean checkCountInput(AttendanceInputForm form) {
		
//		// シフトコード
//		String shiftCode = form.getShiftCd();
//		
//		// 勤務終了時刻が未入力の場合
//		if (StringUtils.isEmpty(kinmuETime)) {
//			// 勤務終了時刻が未入力です！
//			form.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {KINMU_END_TIME});
//		}
//
//		// シフトコードより、シフト情報を取得
//		ShiftVO shiftinfo = this.getShiftInfo(form);
//		// 勤務開始時刻の型チェックとフォーマット
//		AttendanceInputChecker.chkWorkSTimeFormat(form, shiftinfo.getStartTimeStr());
//		// 勤務終了時刻の型チェックとフォーマット
//		AttendanceInputChecker.chkWorkSTimeFormat(form, shiftinfo.getStartTimeStr());
//
//		// 休暇欠勤区分
//		String kyukaKbn = form.getKyukaKb();
//		// 休暇欠勤区分を選択された場合
//		if (!StringUtils.isEmpty(kyukaKbn)) {
//			// 休暇欠勤区分"01"(全休(有給休暇))or"04"(特別休暇)or"05"(代休)or"06"(欠勤)or"08"(無給公休)で
//			if (StringUtils.equals(CommonConstant.KK_KBN_ZENKYU, kyukaKbn) ||
//					StringUtils.equals(CommonConstant.KK_KBN_TOKUBETUKYU, kyukaKbn) ||
//					StringUtils.equals(CommonConstant.KK_KBN_TAIKYU, kyukaKbn) ||
//					StringUtils.equals(CommonConstant.KK_KBN_KEKIN, kyukaKbn) ||
//					StringUtils.equals(CommonConstant.KK_KBN_MUKYU, kyukaKbn)) {
//				// 勤務開始時刻または勤務終了時刻に入力あり
//				if ((!StringUtils.isEmpty(kinmuSTime)) ||
//						(!StringUtils.isEmpty(kinmuETime))) {
//					
//					// 終日休暇の日は勤務できません
//					form.putConfirmMsg(MessageConstants.COSE_E_003);
//				}
//			// 上計以外の場合(休暇欠勤区分""or"02"or"03"or"07")
//			} else {
//				// 勤務区分"01"(出勤)or"03"(休日振替勤務)で勤務開始時刻または勤務終了時刻が未入力
//				String kinmuKbn = form.getWorkDayKbn();
//				if ((StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, kinmuKbn) ||
//						StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, kinmuKbn)) &&
//						(StringUtils.isEmpty(kinmuSTime) || StringUtils.isEmpty(kinmuETime))) {
//					// 勤務開始時刻・終了時刻を入力してください
//					form.putConfirmMsg(MessageConstants.COSE_E_004,new String[] {KINMU_START_END_TIME});
//				}
//			}
//		}
//		// シフト時刻情報を取得
//		ShiftJikoku shiftJikokuEntity = baseDao.findById(shiftCode, ShiftJikoku.class);
//		// シフト時刻からレコードが取得できない
//		if (shiftJikokuEntity == null) {
//			// ｼﾌﾄｺｰﾄﾞの時刻データが設定されていません
//			form.putConfirmMsg(MessageConstants.COSE_E_005, new String[] {SHIFT_TIME_DATA});
//		}
//		// 定時出勤時刻
//		String teijiStartTime = shiftJikokuEntity.getTeijiKinmuTime();
//		// 定時退勤時刻
//		String teijiEndTime = shiftJikokuEntity.getTeijiTaikinTime();
//		// 勤務開始時刻と勤務終了時刻が片方だけ入力されている
//		if ((StringUtils.isEmpty(kinmuSTime) && !StringUtils.isEmpty(kinmuETime)) ||
//				StringUtils.isEmpty(kinmuETime) && !StringUtils.isEmpty(kinmuSTime)) {
//			// 勤務開始時刻・終了時刻は両方入力してください
//			form.putConfirmMsg(MessageConstants.COSE_E_006);
//		}
//		// 勤務開始時刻の分
//		String kinmuSMinute = form.getWorkSMinute();
//		// 時刻の分を入力された場合
//		if (!StringUtils.isEmpty(kinmuSMinute) ) {
//			// 時刻の分が30単位でない
//			if (!CostDateUtils.isHalfHour(kinmuSMinute)) {
//				// 勤務開始は30分単位で入力してください
//				form.putConfirmMsg(MessageConstants.COSE_E_007, new String[] {KINMU_START_TIME});
//			}
//			
//		}
//
//		// 勤務終了時刻の分
//		String kinmuEMinute = form.getWorkEMinute();
//		// 時刻の分を入力された場合
//		if (!StringUtils.isEmpty(kinmuEMinute)) {
//			// 時刻の分が30単位でない
//			if (!CostDateUtils.isHalfHour(kinmuEMinute)) {
//				// 勤務終了は30分単位で入力してください
//				form.putConfirmMsg(MessageConstants.COSE_E_007, new String[] {KINMU_END_TIME});
//			}
//		}
//		
//		// 勤務開始時刻が定時出勤時刻より前、または勤務開始時刻が定時退勤時刻時刻以降
//		if ((CostDateUtils.compareDate(kinmuSTime, teijiStartTime) < 0) ||
//				(CostDateUtils.compareDate(kinmuSTime, teijiEndTime) > 0)) {
//			// 勤務開始時刻は定時勤務時間内の時刻を入力してください
//			form.putConfirmMsg(MessageConstants.COSE_E_008);
//		}
//		
//		// 勤務終了時刻が勤務開始時刻以前
//		if (CostDateUtils.compareDate(kinmuETime, kinmuSTime) < 0) {
//			// 勤務終了時刻は勤務開始時刻より後の時刻を入力してください
//			form.putConfirmMsg(MessageConstants.COSE_E_009);
//		}
//		// エラーが発生する場合
//		if (!StringUtils.isEmpty(form.getConfirmMsg())) {
//			return false;
//		}
		// TODO 自動生成されたメソッド・スタブ
		return true;
		
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
		
		// シフトコードより、シフト情報を取得
		ShiftVO shiftinfo = getShiftInfo(form, form.getShiftCd());
		if (StringUtils.isEmpty(form.getConfirmMsg())) {
			form.setShiftInfo(shiftinfo);
		}
		// 休暇欠勤区分
		String kyukaKbn = form.getKyukaKb();
		// 勤務開始時刻の型チェックとフォーマット
		AttendanceInputChecker.chkWorkSTimeFormat(form, shiftinfo.getStartTimeStr());
		// 勤務終了時刻の型チェックとフォーマット
		AttendanceInputChecker.chkWorkETimeFormat(form, shiftinfo.getStartTimeStr());

		form.setShiftInfo(shiftinfo);

		// 勤務時間取得
		getWorkHours(form);
		// 「時間休(有給休暇)」または「遅刻・早退」の場合
		if (StringUtils.equals(CommonConstant.KK_KBN_JIKANKYU, kyukaKbn) 
				|| StringUtils.equals(CommonConstant.KK_KBN_CHIKOKU, kyukaKbn)) {
			// 休暇時間数を算出する
			getRestHours(form, shiftinfo);
		}
		
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
			hours = countWorkTime(shiftCode, wSTimeStr, DAY_OVER_TIME) 
					+ countWorkTime(shiftCode, DAY_START_TIME, wEtime);
		} else {

			// 勤務開始時刻 ~ 勤務終了時刻
			hours = countWorkTime(shiftCode, wStime,
					wEtime);
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
				wChikoku = countRestTime(shiftCode, standSTimeStr, wStime, 2);
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
				wSotai = countRestTime(shiftCode, wEtime, shiftinfo.getEndTime(), 2);
			}
		}

		// 休暇時間
		form.setKyukaHours(wChikoku + wSotai);
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
				form.setChoSTime("");
				form.setChoETime("");
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
		ShiftVO info = null;
		// シフト時刻情報を取得
		ShiftJikoku entity = baseDao.findById(shiftCode, ShiftJikoku.class);
		// シフト時刻からレコードが取得できない
		if (entity == null) {
			// ｼﾌﾄｺｰﾄﾞの時刻データが設定されていません
			form.putConfirmMsg(MessageConstants.COSE_E_005, new String[] {SHIFT_TIME_DATA});
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
	
	private Double conBigDecimalToDouble(BigDecimal b) {
		
		if (b == null) {
			return new Double(0.0);
		} else {
			return b.doubleValue();
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
	 * 
	 * @return 作業時間数
	 */
	private Double countWorkTime(String shiftCode, String sTime, String eTime) {
		
		return attendanceInputDao.countWorkTime(shiftCode, sTime, eTime, 0);
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
		// 終了十区
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
						if (StringUtils.equals(info1.getKinmuFlg(), "1")
								&& StringUtils.equals(info2.getKinmuFlg(), "1")) {
							hours = hours + 0.5;
						}
					}
				}
			}
		}
		
		return hours;
	}
	
}
