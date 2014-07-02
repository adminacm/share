package argo.cost.attendanceOnHoliday.checker;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.attendanceOnHoliday.dao.AtendanceOnHolidayDao;
import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.utils.CostDateUtils;

/**
 * 休日勤務入力画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class AtendanceOnHolidayChecker {

	/**
	 * 振替休日
	 */
	private final static String  FURIKAEBI = "振替休日";
	/**
	 * 勤務開始時刻。
	 */
	private final static String  KINMU_START_TIME = "勤務開始時刻";
	/**
	 * 勤務終了時刻
	 */
	private final static String KINMU_END_TIME = "勤務終了時刻";
	/**
	 * プロジェクト名
	 */
	private final static String PROJECT_NAME = "プロジェクト名";
	/**
	 * 業務内容
	 */
	private final static String WORK_DETAIL = "業務内容";
	/**
	 * 勤務日区分
	 */
	private final static String WORK_DAY_KBN = "勤務日区分";
	
	/**
	 * 共通DAO
	 */
	@Autowired
	static BaseDao baseDao;
	/**
	 * 勤怠入力DAO
	 */
	@Autowired
	static AtendanceOnHolidayDao atendanceOnHolidayDao;
	
	public static BaseDao getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(BaseDao baseDao) {
		AtendanceOnHolidayChecker.baseDao = baseDao;
	}

	public static AtendanceOnHolidayDao getAtendanceOnHolidayDao() {
		return atendanceOnHolidayDao;
	}

	public static void setAtendanceOnHolidayDao(
			AtendanceOnHolidayDao atendanceOnHolidayDao) {
		AtendanceOnHolidayChecker.atendanceOnHolidayDao = atendanceOnHolidayDao;
	}

	/**
	 * 勤務開始時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkStartTimeInput(AtendanceOnHolidayForm form) {
		
		// 勤務開始時刻
		String wSTime = form.getStrAtendanceTimeStat();
		// 未入力
		if (StringUtils.isEmpty(wSTime)) {
			// 勤務開始時刻が未入力です
			form.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {KINMU_START_TIME});
			return;
		}
		// 勤務開始時刻のhhnn形式値が数値以外
		if (!CostDateUtils.isTimeHHnn(wSTime.replace(":", StringUtils.EMPTY))) {
			// 勤務終了時刻を正しく入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KINMU_START_TIME});
		}
		// 勤務終了時刻の分の値が0、30以外
		if ((!StringUtils.endsWith(wSTime, "00"))
				&& (!StringUtils.endsWith(wSTime, "30"))) {
			// 勤務終了時刻は30分単位で入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_007, new String[] {KINMU_START_TIME});
		}
		
		
	}

	/**
	 * 勤務終了時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkEndTimeInput(AtendanceOnHolidayForm form) {
		
		// 勤務終了時刻
		String wETime = form.getStrAtendanceTimeEnd();
		// 未入力
		if (StringUtils.isEmpty(wETime)) {
			// 勤務終了時刻が未入力です
			form.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {KINMU_END_TIME});
			return;
		}
		// 勤務終了時刻のhhnn形式値が数値以外
		if (!CostDateUtils.isTimeHHnn(wETime.replace(":", StringUtils.EMPTY))) {
			// 勤務終了時刻を正しく入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KINMU_END_TIME});
		}
		// 勤務終了時刻の分の値が0、30以外
		if ((!StringUtils.endsWith(wETime, "00"))
				&& (!StringUtils.endsWith(wETime, "30"))) {
			// 勤務終了時刻は30分単位で入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_007, new String[] {KINMU_END_TIME});
		}
	}

	/**
	 * 振替日チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkFurikaeBiInput(AtendanceOnHolidayForm form) {
		
		// 勤務終了時刻
		String furikaebi = form.getStrHurikaeDate();
		// 勤務区分
		String kinmuKbn = form.getSelectedAtendanceDayKbn();
		// 勤務区分が「休日振替勤務」で
		if (StringUtils.equals(kinmuKbn, CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE)) {
			// 振替休日が未入力
			if (StringUtils.isEmpty(furikaebi)) {
				// 振替休日を入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_004, new String[] {FURIKAEBI});
				return;
			}
			// 振替休日に日付以外が入力されている
			if (!CostDateUtils.isValidDate(furikaebi, CommonConstant.YYYY_MM_DD)) {
				// 振替休日には日付を入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_017, new String[] {FURIKAEBI});
			}
			// 振替休日が当日の日付
			if (StringUtils.equals(form.getStrAtendanceDate(), furikaebi.replaceAll("/", ""))) {
				// 振替休日が当日の日付です
				form.putConfirmMsg(MessageConstants.COSE_E_018, new String[] {FURIKAEBI});
			}
			// 振替休日の日付の就業データが処理済の場合
			// 処理状況を取得
			String status = atendanceOnHolidayDao.getAttendanceInfoStatus(form.getUserId(), furikaebi.replaceAll("/", ""));
			// 振替休日の日付の就業データが処理済の場合 TODO
			if (StringUtils.equals(status, "03")) {
				// 振替休日が当日の日付です
				form.putConfirmMsg(MessageConstants.COSE_E_020, new String[] {FURIKAEBI});
			}
		} else {
			// 振替休日が入力されている
			if (StringUtils.isNotEmpty(furikaebi)) {
				// 休日振替勤務以外のときは振替休日は入力できません
				form.putConfirmMsg(MessageConstants.COSE_E_019);
			}
		}
	}

	/**
	 * プロジェクト名チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkProjectID(AtendanceOnHolidayForm form) {
		
		// プロジェクトID
		String projectId = form.getSelectedProjCd();
		// 未選択
		if (StringUtils.isEmpty(projectId)) {
			// プロジェクト名を選択してください
			form.putConfirmMsg(MessageConstants.COSE_E_021, new String[] {PROJECT_NAME});
		}
	}

	/**
	 * 業務内容チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkWorkDetail(AtendanceOnHolidayForm form) {
		
		// 業務内容
		String workDetail = form.getStrCommont();
		// 未入力
		if (StringUtils.isEmpty(workDetail)) {
			// プロジェクト名を選択してください
			form.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {WORK_DETAIL});
		}
	}

	/**
	 * 勤務日区分チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkWorkDayKbn(AtendanceOnHolidayForm form) {
		
		// 勤務日区分が「休日」「休日振替勤務」以外
		if ((!StringUtils.equals(form.getSelectedAtendanceDayKbn(), CommonConstant.WORKDAY_KBN_KYUJITU))
				&& (!StringUtils.equals(form.getSelectedAtendanceDayKbn(), CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE))) {
			// 勤務区分の値が不正です
			form.putConfirmMsg(MessageConstants.COSE_E_022, new String[] {WORK_DAY_KBN});
		}
	}
}
