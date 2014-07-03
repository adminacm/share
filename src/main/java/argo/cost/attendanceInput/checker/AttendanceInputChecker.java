package argo.cost.attendanceInput.checker;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.attendanceInput.dao.AttendanceInputDao;
import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProjectVO;
import argo.cost.attendanceInput.model.ShiftVO;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.KyukaKekinKbnMaster;
import argo.cost.common.entity.MCalendar;
import argo.cost.common.entity.YukyuKyukaFuyu;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.common.utils.CostStringUtils;

/**
 * 勤怠入力画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class AttendanceInputChecker {

	/**
	 * 勤怠
	 */
	private final static String  KINTAI = "勤怠";
	/**
	 * 勤務開始時刻。
	 */
	private final static String  KINMU_START_TIME = "勤務開始時刻";
	/**
	 * 勤務終了時刻
	 */
	private final static String KINMU_END_TIME = "勤務終了時刻";
	/**
	 * 勤務開始時刻・勤務終了時刻
	 */
	private final static String KINMU_START_END_TIME = "勤務開始時刻・終了時刻";
	/**
	 * 休暇欠勤区分
	 */
	private final static String KYUKA_KEKIN_KBN = "休暇欠勤区分";
	/**
	 * 休暇区分
	 */
	private final static String KYUKA_KBN = "休暇区分";
	
	/**
	 * 共通DAO
	 */
	@Autowired
	static BaseDao baseDao;
	/**
	 * 勤怠入力DAO
	 */
	@Autowired
	static AttendanceInputDao attendanceInputDao;
	
	public static BaseDao getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(BaseDao baseDao) {
		AttendanceInputChecker.baseDao = baseDao;
	}

	public AttendanceInputDao getAttendanceInputDao() {
		return attendanceInputDao;
	}

	public void setAttendanceInputDao(AttendanceInputDao attendanceInputDao) {
		AttendanceInputChecker.attendanceInputDao = attendanceInputDao;
	}

	/**
	 * 勤務開始時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @param standSTimeStr
	 *            定時出勤時刻(hhnn)
	 */
	public static void chkWorkSTimeFormat(AttendanceInputForm form) {
		
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		String hour = StringUtils.isEmpty(form.getWorkSHour()) ? StringUtils.EMPTY : form.getWorkSHour();
		String minute = StringUtils.isEmpty(form.getWorkSMinute()) ? StringUtils.EMPTY : form.getWorkSMinute();
		// 勤務開始時刻
		String kinmuSTime = hour.concat(minute);
		form.setWorkSTime(kinmuSTime);
		form.setWorkSTimeStr(kinmuSTime);
		
		// 勤務開始時刻が入力される場合
		if (!StringUtils.isEmpty(kinmuSTime)) {
			// 勤務開始時刻のhhnn形式値が数値以外
			if (!CostDateUtils.isTimeHHnn(kinmuSTime)) {
				// 勤務開始時刻を正しく入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KINMU_START_TIME});
			} else {
				// 勤務開始時刻＜定時出勤時刻(hhnn)
				if (kinmuSTime.compareTo(shift.getStartTimeStr()) < 0) {
					form.setWorkSTimeStr(CostDateUtils.AddForOneDay(kinmuSTime));
				} else {
					form.setWorkSTimeStr(kinmuSTime);
				}
			}
		}
	}

	/**
	 * 勤務開始時刻重複チェック
	 * 		前日の勤務終了時刻と対象日の勤務開始時刻の値が同じ
	 * 			☆勤務開始時刻が前日の勤務終了時刻と同時刻です
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkWorkSTimeSame(AttendanceInputForm form) {
		
		// 勤怠日付
		String date = form.getAttDate();
		//　先日日付
		String lastDate = CostDateUtils.addDays(date, -1);
		// 先日の勤怠情報を取得
		BaseCondition condition = new BaseCondition();
		condition.addConditionEqual("users.id", form.getUserId());
		condition.addConditionEqual("atendanceBookDate", lastDate);
		// 先日の勤怠情報を取得
		KintaiInfo info = baseDao.findSingleResult(condition, KintaiInfo.class);
		// 勤怠情報を取得が存在する
		if (info != null) {
			// 前日の勤務終了時刻と対象日の勤務開始時刻の値が同じ
			if (StringUtils.equals(form.getWorkSTime(), info.getKinmuEndTime())) {
				// 勤務開始時刻が前日の勤務終了時刻と同時刻です
				form.putConfirmMsg(MessageConstants.COSE_W_1102);
			}
		}
	}
	
	/**
	 * 勤務終了時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @param standSTimeStr
	 *            定時出勤時刻(hhnn)
	 */
	public static void chkWorkETimeFormat(AttendanceInputForm form) {
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		String hour = StringUtils.isEmpty(form.getWorkEHour()) ? StringUtils.EMPTY : form.getWorkEHour();
		String minute = StringUtils.isEmpty(form.getWorkEMinute()) ? StringUtils.EMPTY : form.getWorkEMinute();
		// 勤務終了時刻
		String kinmuETime = hour.concat(minute);
		form.setWorkETime(kinmuETime);
		form.setWorkETimeStr(kinmuETime);
		
		// 勤務終了時刻が入力される場合
		if (!StringUtils.isEmpty(kinmuETime)) {
			// 勤務終了時刻のhhnn形式値が数値以外
			if (!CostDateUtils.isTimeHHnn(kinmuETime)) {
				// 勤務終了時刻を正しく入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KINMU_END_TIME});
			} else {
				// 勤務終了時刻＜＝定時出勤時刻(hhnn)
				if (kinmuETime.compareTo(shift.getStartTimeStr()) <= 0) {
					form.setWorkETimeStr(CostDateUtils.AddForOneDay(kinmuETime));
				} else {
					form.setWorkETimeStr(kinmuETime);
				}
			}
		}
	}

	/**
	 * 勤務時刻の整合性チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws ParseException 
	 */
	public static void chkWorkTimeFormat(AttendanceInputForm form) throws ParseException {
		
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		// 定時出勤時刻
		String standSTimeStr = shift.getStartTimeStr();
		// 定時退勤時刻
		String standETimeStr = shift.getEndTimeStr();
		// 勤務開始時刻
		String sTime = form.getWorkSTime();
		// 勤務開始時刻の分
		String sMinute = form.getWorkSMinute();
		// 勤務終了時刻
		String eTime = form.getWorkETime();
		// 勤務終了時刻の分
		String eMinute = form.getWorkEMinute();
		// 勤務開始時刻（hhnn）
		String sTimeStr = form.getWorkSTimeStr();
		// 勤務終了時刻（hhnn）
		String eTimeStr = form.getWorkETimeStr();

		if (StringUtils.isNotEmpty(sTime)
				|| StringUtils.isNotEmpty(eTime)) {
			// 勤務開始時刻と勤務終了時刻が片方だけ入力されている
			if (StringUtils.isEmpty(sTime)
					|| StringUtils.isEmpty(eTime)) {
				// 勤務開始時刻・終了時刻は両方入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_006);
				return;
			} else {

				// 勤務開始時刻は定時勤務時間帯以外はエラー
				if (sTimeStr.compareTo(standSTimeStr) < 0
						|| standETimeStr.compareTo(sTimeStr) <= 0) {
					// 勤務開始時刻は定時勤務時間内の時刻を入力してください
					form.putConfirmMsg(MessageConstants.COSE_E_008);
					return;
				}
				// 勤務終了時刻が定時出勤時刻～勤務開始時刻の間の時刻はエラー
				if (eTimeStr.compareTo(sTimeStr) <= 0) {
					// 勤務終了時刻は勤務開始時刻より後の時刻を入力してください
					form.putConfirmMsg(MessageConstants.COSE_E_009);
					return;
				}
				// 24時間を越える勤務はエラー
				if (24 < CostDateUtils.MinusTime(sTimeStr, eTimeStr)) {
					// 1日に24時間を超える勤務は入力できません
					form.putConfirmMsg(MessageConstants.COSE_E_010);
					return;
				}
				// 勤務開始時刻は30分単位で入力
				if (!CostDateUtils.isHalfHour(sMinute)) {
					// 勤務開始時刻は30分単位で入力してください
					form.putConfirmMsg(MessageConstants.COSE_E_007, new String[] {KINMU_START_TIME});
					return;
				}
				// 勤務終了時刻は30分単位で入力
				if (!CostDateUtils.isHalfHour(eMinute)) {
					// 勤務終了時刻は30分単位で入力してください
					form.putConfirmMsg(MessageConstants.COSE_E_007, new String[] {KINMU_END_TIME});
					return;
				}
			}
			
		}
	}
	
	/**
	 * 休暇欠勤区分の取得チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKyuKaKbn(AttendanceInputForm form) {
		
		// 休暇欠勤区分
		String kykaKbn = form.getKyukaKb();
		// 休暇欠勤区分が存在する場合
		if (!StringUtils.isEmpty(kykaKbn)) {
			// 休暇欠勤区分情報を取得
			KyukaKekinKbnMaster entity = baseDao.findById(kykaKbn, KyukaKekinKbnMaster.class);
			
			// 休暇欠勤区分情報を抽出されない
			if (entity == null) {
				// 休暇欠勤区分を正しく入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KYUKA_KEKIN_KBN});
			}
		}
	}

	/**
	 * 出勤日フラグの取得チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkWorkDayFlag(AttendanceInputForm form) {
		
		// 対象日付
		String date = form.getAttDate();
		// 対象日付が存在する場合
		if (!StringUtils.isEmpty(date)) {
			// 日付より、カレンダー情報を取得
			MCalendar calender = baseDao.findById(date, MCalendar.class);
			
			// 休暇欠勤区分情報を抽出されない
			if (calender == null) {
				// カレンダーデータの出勤日フラグが設定されていません
				form.putConfirmMsg("カレンダーデータの出勤日フラグが設定されていません");
			}
		}
	}

	/**
	 * 休暇欠勤区分・勤務時刻の整合性チェック
	 * 
	 * 		①休暇欠勤区分""or"02"or"03"or"07"の場合
	 * 			勤務開始時刻または勤務終了時刻が未入力
	 * 				☆勤務開始時刻・終了時刻を入力してください
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbnAndWorkTime01(AttendanceInputForm form) {
		
		// 休暇欠勤区分
		String kyukaKbn = form.getKyukaKb();
		// 勤務開始時刻
		String sTime = form.getWorkSTime();
		// 勤務終了時刻
		String eTime = form.getWorkETime();
		// 休暇欠勤区分""or"02"or"03"or"07"の場合
		if (StringUtils.isEmpty(kyukaKbn)
				|| StringUtils.equals(CommonConstant.KK_KBN_HANKYU, kyukaKbn)
				|| StringUtils.equals(CommonConstant.KK_KBN_JIKANKYU, kyukaKbn)
				|| StringUtils.equals(CommonConstant.KK_KBN_CHIKOKU, kyukaKbn)) {
			
			// 勤務開始時刻または勤務終了時刻が未入力
			if (StringUtils.isEmpty(sTime) || StringUtils.isEmpty(eTime)) {
				// 勤務開始時刻・終了時刻を入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_004, new String[] { KINMU_START_END_TIME });
			}
		}
	}

	/**
	 * 休暇欠勤区分・勤務時刻の整合性チェック
	 * 
	 * 		①勤務区分"01"(出勤)or"03"(休日振替勤務)で
	 * 			勤務開始時刻が定時出勤時刻より後または勤務終了時刻が定時退勤時刻より前で
	 * 				休暇欠勤区分が未入力
	 * 					☆定時時間帯の勤務時間数が7.5h未満です。休暇区分も入力してください
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbnAndWorkTime02(AttendanceInputForm form) {
		
		// 勤務区分
		String kinmuKbn = form.getWorkDayKbn();
		// 休暇欠勤区分
		String kyukaKbn = form.getKyukaKb();
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		// 勤務区分"01"(出勤)or"03"(休日振替勤務)で
		if (StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, kinmuKbn)
				|| StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, kinmuKbn)) {
			if (StringUtils.isNotEmpty(form.getWorkSTimeStr())
					&& StringUtils.isNotEmpty(form.getWorkETimeStr())) {
				// 定時時間帯の勤務が足りない場合
				if (shift.getStartTimeStr().compareTo(form.getWorkSTimeStr()) < 0
						|| form.getWorkETimeStr().compareTo(shift.getEndTimeStr()) < 0) {
					// は休暇が入力されているか
					if (StringUtils.isEmpty(kyukaKbn)) {
						// 定時時間帯の勤務時間数が7.5h未満です。休暇区分も入力してください
						form.putConfirmMsg(MessageConstants.COSE_E_011, new String[] {String.valueOf(shift.getWorkHours())});
					}
				}
			}
		}
	}

	/**
	 * 休暇欠勤区分・勤務時刻の整合性チェック
	 * 
	 * 		①勤務区分"01"(出勤)or"03"(休日振替勤務)で
	 * 			勤務開始時刻が定時出勤時刻より後または勤務終了時刻が定時退勤時刻より前で
	 * 				休暇欠勤区分"02"(半休(有給休暇))で、
	 * 					勤務開始時刻が定時出勤時刻より後かつ勤務終了時刻が定時退勤時刻より前、
	 * 					または勤務終了時刻が午前終了時刻より前、または勤務開始時刻が午後開始時刻より後
	 * 						☆正しい休暇区分を入力してください
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbnAndWorkTime03(AttendanceInputForm form) {
		
		// 勤務区分
		String kinmuKbn = form.getWorkDayKbn();
		// 休暇欠勤区分
		String kyukaKbn = form.getKyukaKb();
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		// 勤務区分"01"(出勤)or"03"(休日振替勤務)で
		if (StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, kinmuKbn)
				|| StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, kinmuKbn)) {
			
			if (StringUtils.isNotEmpty(form.getWorkSTimeStr())
					&& StringUtils.isNotEmpty(form.getWorkETimeStr())) {
				// 定時時間帯の勤務が足りない場合
				if (shift.getStartTimeStr().compareTo(form.getWorkSTimeStr()) < 0
						|| form.getWorkETimeStr().compareTo(shift.getEndTimeStr()) < 0) {
					// 休暇欠勤区分"02"(半休(有給休暇))で
					if (StringUtils.equals(CommonConstant.KK_KBN_HANKYU, kyukaKbn)) {
						// 午前も午後も勤務が欠けていたらエラー
						// 勤務開始時刻が定時出勤時刻より後かつ勤務終了時刻が定時退勤時刻より前、
						// または勤務終了時刻が午前終了時刻より前、または勤務開始時刻が午後開始時刻より後
						if ((shift.getStartTimeStr().compareTo(form.getWorkSTimeStr()) < 0
								&& form.getWorkETimeStr().compareTo(shift.getEndTimeStr()) < 0)
								|| form.getWorkETimeStr().compareTo(shift.getAmETimeStr()) < 0
								|| shift.getFmSTimeStr().compareTo(form.getWorkSTimeStr()) < 0) {
							// 正しい休暇区分を入力してください
							form.putConfirmMsg(MessageConstants.COSE_E_012, new String[] {KYUKA_KBN});
						}
					}
				}
			}
		}
	}

	/**
	 * 休暇欠勤区分・勤務時刻の整合性チェック
	 * 
	 * 		①勤務区分"01"(出勤)or"03"(休日振替勤務)で勤務開始時刻が定時出勤時刻、
	 * 			勤務終了時刻が定時退勤時刻以降で休暇欠勤区分が入力されている
	 * 				☆休暇欠勤区分が入力されています
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbnAndWorkTime04(AttendanceInputForm form) {
		
		// 勤務区分
		String kinmuKbn = form.getWorkDayKbn();
		// 休暇欠勤区分
		String kyukaKbn = form.getKyukaKb();
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		// 勤務区分"01"(出勤)or"03"(休日振替勤務)で
		if (StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, kinmuKbn)
				|| StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, kinmuKbn)) {
			
			if (StringUtils.isNotEmpty(form.getWorkSTimeStr())
					&& StringUtils.isNotEmpty(form.getWorkETimeStr())) {
				// 勤務開始時刻が定時出勤時刻、勤務終了時刻が定時退勤時刻以降
				if (form.getWorkSTimeStr().compareTo(shift.getStartTimeStr()) == 0
						&& shift.getEndTimeStr().compareTo(form.getWorkETimeStr()) <= 0) {
					// 休暇欠勤区分が入力されている
					if (StringUtils.isNotEmpty(kyukaKbn)) {
						// 休暇欠勤区分が入力されています
						form.putConfirmMsg(MessageConstants.COSE_E_013);
					}
				}
			}
		}
	}

	/**
	 * 休暇欠勤区分・勤務時刻の整合性チェック
	 * 
	 * 		①勤務開始時刻と勤務終了時刻が未入力で勤務区分"01"(出勤)or"03"(休日振替勤務)で
	 * 			休暇欠勤区分が未入力
	 * 				☆勤怠が未入力です
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbnAndWorkTime05(AttendanceInputForm form) {
		
		// 勤務区分"01"(出勤)or"03"(休日振替勤務)で
		if (StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, form.getWorkDayKbn())
				|| StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, form.getWorkDayKbn())) {
			// 勤務開始時刻と勤務終了時刻が未入力で
			if (StringUtils.isEmpty(form.getWorkSTime())
					&& StringUtils.isEmpty(form.getWorkETime())) {
				// 休暇欠勤区分が入力されている
				if (StringUtils.isEmpty(form.getKyukaKb())) {
					// 休暇欠勤区分が入力されています
					form.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {KINTAI});
				}
			}
		}
	}

	/**
	 * 休暇欠勤区分・勤務時刻の整合性チェック
	 * 
	 * 		①休暇欠勤区分"01"(全休(有給休暇))or"04"(特別休暇)or"05"(代休)or"06"(欠勤)"08"(無給公休)で
	 * 			勤務開始時刻または勤務終了時刻に入力あり
	 * 				☆終日休暇の日は勤務できません
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbnAndWorkTime06(AttendanceInputForm form) {
		
		// 休暇欠勤区分
		String kyukaKbn = form.getKyukaKb();
		// 勤務開始時刻
		String sTime = form.getWorkSTime();
		// 勤務終了時刻
		String eTime = form.getWorkETime();
		// 休暇欠勤区分"01"(全休(有給休暇))or"04"(特別休暇)or"05"(代休)or"06"(欠勤)"08"(無給公休)で
		if (StringUtils.equals(CommonConstant.KK_KBN_ZENKYU, kyukaKbn)
				|| StringUtils.equals(CommonConstant.KK_KBN_TOKUBETUKYU, kyukaKbn)
				|| StringUtils.equals(CommonConstant.KK_KBN_TAIKYU, kyukaKbn)
				|| StringUtils.equals(CommonConstant.KK_KBN_KEKIN, kyukaKbn)
				|| StringUtils.equals(CommonConstant.KK_KBN_MUKYU, kyukaKbn)) {
			
			// 勤務開始時刻または勤務終了時刻に入力あり
			if ((!StringUtils.isEmpty(sTime))
					|| (!StringUtils.isEmpty(eTime))) {
				// 終日休暇の日は勤務できません
				form.putConfirmMsg(MessageConstants.COSE_E_003);
			}
		}
	}

	/**
	 * 休暇欠勤区分・シフトコードの整合性チェック
	 * 
	 * 		①勤務区分"01"(出勤)or"03"(休日振替勤務)以外（＝勤務区分"02"(休日)or"04"(振替休日)で
	 * 			勤務開始時刻が定時出勤時刻と不一致
	 * 				☆休日の勤務開始は定時出勤時刻を入力してください
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbnAndShiftCode(AttendanceInputForm form) {
		
		// 勤務区分
		String kinmuKbn = form.getWorkDayKbn();
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		// 勤務区分"02"(休日)or"04"(振替休日)で
		if (StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU, kinmuKbn)
				|| StringUtils.equals(CommonConstant.WORKDAY_KBN_FURIKAE_KYUJITU, kinmuKbn)) {
			// 勤務開始が定時出勤時刻以外はエラー
			if (!StringUtils.equals(form.getWorkSTimeStr(), shift.getStartTimeStr())) {
				// 休日の勤務開始は定時出勤時刻を入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_014);
			}
		}
	}

	/**
	 * 休暇欠勤区分（代休）チェック
	 * 
	 * 		①休暇欠勤区分"05"(代休)で、
	 * 			休日勤務管理から代休取得期限が対象日以前で代休日がNULLのレコードが取得できない
	 * 				☆取得できる代休はありません
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbn001(AttendanceInputForm form) {
		
		// 休暇欠勤区分"05"(代休)で
		if (StringUtils.equals(CommonConstant.KK_KBN_TAIKYU, form.getKyukaKb())) {
			// 休日勤務管理から代休取得期限が対象日以前で代休日がNULLのレコードを取得
			BaseCondition condition = new BaseCondition();
			condition.addConditionEqual("users.id", form.getUserId());          // 社員番号
	        // 勤務日区分
			condition.addConditionEqual("workDayKbnMaster.code", CommonConstant.WORKDAY_KBN_KYUJITU);
			// 代休取得期限
			condition.addConditionGreaterEqualThan("daikyuGetShimekiriDate", form.getAttDate());
			// 休日勤務日
			condition.addConditionLessThan("atendanceDate", form.getAttDate());
			// 勤務時間数＞＝7.5
			condition.addConditionGreaterEqualThan("kinmuJikansu", new BigDecimal(7.5));
			// 代休日がNULL
			condition.addConditionIsNull("daikyuDate");
			// 代休可能の勤怠情報を取得する
			List<KintaiInfo> entity = baseDao.findResultList(condition, KintaiInfo.class);

			// 代休情報を取得されない
			if (entity == null || entity.isEmpty()) {
				// 取得できる代休はありません
				form.putConfirmMsg(MessageConstants.COSE_E_015);
			}
		}
	}

	/**
	 * 休暇欠勤区分（有給休暇）チェック
	 * 
	 * 		①休暇欠勤区分"01"(全休)は8h、"02"(半休)は4h、"03"(時間休)は休暇欠勤時間数と
	 * 			t_休暇欠勤管理から取得した当年度4月1日から対象日前日までの有給休暇時間数(全休、半休、時間休)の合計時間数
	 * 			を足した値が当年度分の有給休暇付与時間数をこえている
	 * 			または、休暇欠勤区分"03"(時間休)の休暇欠勤時間数とt_休暇欠勤管理から取得した
	 * 			当年度4月1日から対象日前日までの時間休(有給休暇)の時間数の合計時間数を足した値が40時間をこえている
	 * 				☆有給休暇の取得限度を超えています
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbn002(AttendanceInputForm form) {
		
		Double kyukaHours = 0.0;
		// 休暇欠勤区分
		String kyukaKbn = form.getKyukaKb();
		// 全休の場合
		if (StringUtils.equals(CommonConstant.KK_KBN_ZENKYU, kyukaKbn)) {
			kyukaHours = 8.0;
		// 半休の場合
		} else if (StringUtils.equals(CommonConstant.KK_KBN_HANKYU, kyukaKbn)) {
			kyukaHours = 4.0;
		// 時間休の場合
		} else if (StringUtils.equals(CommonConstant.KK_KBN_JIKANKYU, kyukaKbn)) {
			// 休暇欠勤時間数
			kyukaHours = form.getKyukaHours();
		}
		// 本年度の休暇時間数を取得
		Double sumKyukaHours = attendanceInputDao.getSumKyukaTime(form.getUserId(), form.getAttDate(), 0);
		
		// 本年度の有給休暇総数を取得
		Double yukyuKyukaHours = 0.0;
		BaseCondition condition = new BaseCondition();
		condition.addConditionEqual("users.id", form.getUserId());
		condition.addConditionEqual("fuyuYear", form.getAttDate().substring(0, 4));
		// 本年度の有給休暇総数を取得
		YukyuKyukaFuyu entity = baseDao.findSingleResult(condition, YukyuKyukaFuyu.class);
		if (entity != null) {
			yukyuKyukaHours = entity.getFuyuAllHours().doubleValue();
		}
		// 有給休暇付与時間数をこえている
		if (yukyuKyukaHours < kyukaHours + sumKyukaHours) {
			// 有給休暇の取得限度を超えています
			form.putConfirmMsg(MessageConstants.COSE_E_016);
		}
		
		// 本年度の休暇時間数(時間休)を取得
		sumKyukaHours = attendanceInputDao.getSumKyukaTime(form.getUserId(), form.getAttDate(), 1);
		// 40時間をこえている
		if (40 < sumKyukaHours + kyukaHours) {
			// 有給休暇の取得限度を超えています
			form.putConfirmMsg(MessageConstants.COSE_E_016);
		}
	}

	/**
	 * 休暇欠勤区分（有給休暇）チェック
	 * 
	 * 		①勤務区分"01"(出勤)or"03"(休日振替勤務)で勤務開始時刻が定時出勤時刻より
	 * 			後または勤務終了時刻が定時退勤時刻より前で休暇欠勤区分"02"(半休(有給休暇))で、
	 * 			勤務開始時刻が定時出勤時刻で勤務終了時刻が定時退勤時刻より前、
	 * 			または勤務開始時刻が午前終了時刻より前かつ勤務終了時刻が定時退勤時刻以降
	 * 				☆有給休暇が余分に取得されています
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKykaKbn003(AttendanceInputForm form) {

		// 勤務区分
		String kinmuKbn = form.getWorkDayKbn();
		// シフト情報
		ShiftVO shift = form.getShiftInfo();
		// 勤務区分"01"(出勤)or"03"(休日振替勤務)で
		if (StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, kinmuKbn)
				|| StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, kinmuKbn)) {
			if (StringUtils.isNotEmpty(form.getWorkSTimeStr())
					&& StringUtils.isNotEmpty(form.getWorkETimeStr())) {
				// 勤務開始時刻が定時出勤時刻より後または勤務終了時刻が定時退勤時刻より前
				if (shift.getStartTimeStr().compareTo(form.getWorkSTimeStr()) < 0
						|| form.getWorkETimeStr().compareTo(shift.getEndTimeStr()) < 0) {
					// 休暇欠勤区分"02"(半休(有給休暇))で、
					if (StringUtils.equals(form.getKyukaKb(), CommonConstant.KK_KBN_HANKYU)) {
						// 勤務開始時刻が定時出勤時刻で勤務終了時刻が定時退勤時刻より前
						// または勤務開始時刻が午前終了時刻より前かつ勤務終了時刻が定時退勤時刻以降
						if ((StringUtils.equals(form.getWorkSTimeStr(), shift.getStartTimeStr())
								&& shift.getAmETimeStr().compareTo(form.getWorkETimeStr()) < 0)
								|| (form.getWorkSTimeStr().compareTo(shift.getAmETimeStr()) < 0
										&& shift.getEndTimeStr().compareTo(form.getWorkETimeStr()) <= 0)) {
							// 有給休暇が余分に取得されています
							form.putConfirmMsg(MessageConstants.COSE_W_1101);
						}
					}
				}
			}
		}
	}

	/**
	 * 休暇欠勤区分と勤務区分のチェック
	 * 	勤務区分が「休日」か「振替休日」の場合
	 * 		休暇欠勤区分が空欄以外はエラー
	 * 			☆休日には休暇取得できません
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKyuKaKbnAndKinmuKbn(AttendanceInputForm form) {
		
		// 勤務区分が「休日」か「振替休日」の場合
		if (StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU, form.getWorkDayKbn())
				|| StringUtils.equals(CommonConstant.WORKDAY_KBN_FURIKAE_KYUJITU, form.getWorkDayKbn())) {
			// 休暇欠勤区分が空欄以外はエラー
			if (StringUtils.isNotEmpty(form.getKyukaKb())) {
				// 休暇欠勤区分を正しく入力してください
				form.putConfirmMsg("休日には休暇取得できません");
			}
		}
	}

	/**
	 * 休日勤務データのチェック
	 * 	休日勤務が存在する
	 * 		勤怠情報が存在しない
	 * 			☆休日勤務が存在する、勤怠実績を入力してください
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkHolidayYoteiInfo(AttendanceInputForm form) {
		
		// 休日勤務情報が存在する場合
		if (form.getHolidayAttendance() != null) {
			BaseCondition condition = new BaseCondition();
			condition.addConditionEqual("users.id", form.getTaishoUserId());
			condition.addConditionEqual("atendanceDate", form.getAttDate());
			KintaiInfo kintaiInfo = baseDao.findSingleResult(condition, KintaiInfo.class);
			// 勤怠情報が存在しない
			if (kintaiInfo == null) {
				
				// 休日勤務が存在する、勤怠実績を入力してください
				form.putConfirmMsg("休日勤務が存在する、勤怠実績を入力してください");
			} else {
				// 勤怠情報が存在しない
				if (StringUtils.isEmpty(kintaiInfo.getKinmuStartTime())) {
					// 休日勤務が存在する、勤怠実績を入力してください
					form.putConfirmMsg("休日勤務が存在する、勤怠実績を入力してください");
				}
			}
		}
	}

	/**
	 * プロジェクト情報のチェック
	 * 	休日勤務が存在する
	 * 		勤怠情報が存在しない
	 * 			☆休日勤務が存在する、勤怠実績を入力してください
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkProjectList(AttendanceInputForm form) {
		
		// プロジェクト情報
		List<AttendanceProjectVO> projectList = form.getProjectList();
		Double sumHour = 0.0;
		for (AttendanceProjectVO project : projectList) {
			
			// 作業時間数
			String workHour = project.getHours();
			// 入力の場合
			if (StringUtils.isNotEmpty(workHour)) {
				if (!CostStringUtils.isToDouble(workHour)) {
					// 作業時間数を正しく入力してください
					form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {"作業時間数"});
					return;
				}
				sumHour += Double.parseDouble(workHour);
			}
		}
		// 全ての時間数の合計値は勤務時間数と合わないの場合
		if (sumHour != 0.0 && sumHour.compareTo(form.getWorkHours()) != 0) {
			// 作業時間数の合計が勤務時間数と一致していません
			form.putConfirmMsg(MessageConstants.COSE_E_025);
			return;
		}
	}
}
