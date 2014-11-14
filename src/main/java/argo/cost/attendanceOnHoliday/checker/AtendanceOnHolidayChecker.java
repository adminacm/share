package argo.cost.attendanceOnHoliday.checker;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.attendanceOnHoliday.dao.AtendanceOnHolidayDao;
import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.exception.BusinessException;
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
	 * @throws BusinessException 
	 */
	public static void chkStartTimeInput(AtendanceOnHolidayForm form) throws BusinessException {
		
		// 勤務開始時刻
		String wSTime = form.getStrAtendanceTimeStat();
		// 未入力
		if (StringUtils.isEmpty(wSTime)) {
			// 勤務開始時刻が未入力です
			form.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {KINMU_START_TIME});
			throw new BusinessException();
		}
		// 勤務開始時刻のhhnn形式値が数値以外
		if (!CostDateUtils.isTimeHHnn(wSTime.replace(":", StringUtils.EMPTY))) {
			// 勤務終了時刻を正しく入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KINMU_START_TIME});
			throw new BusinessException();
		}
		// 勤務終了時刻の分の値が0、30以外
		if ((!StringUtils.endsWith(wSTime, "00"))
				&& (!StringUtils.endsWith(wSTime, "30"))) {
			// 勤務終了時刻は30分単位で入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_007, new String[] {KINMU_START_TIME});
			throw new BusinessException();
		}
		
		
	}

	/**
	 * 勤務終了時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public static void chkEndTimeInput(AtendanceOnHolidayForm form) throws BusinessException {
		
		// 勤務終了時刻
		String wETime = form.getStrAtendanceTimeEnd();
		// 未入力
		if (StringUtils.isEmpty(wETime)) {
			// 勤務終了時刻が未入力です
			form.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {KINMU_END_TIME});
			throw new BusinessException();
		}
		// 勤務終了時刻のhhnn形式値が数値以外
		if (!CostDateUtils.isTimeHHnn(wETime.replace(":", StringUtils.EMPTY))) {
			// 勤務終了時刻を正しく入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KINMU_END_TIME});
			throw new BusinessException();
		}
		// 勤務終了時刻の分の値が0、30以外
		if ((!StringUtils.endsWith(wETime, "00"))
				&& (!StringUtils.endsWith(wETime, "30"))) {
			// 勤務終了時刻は30分単位で入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_007, new String[] {KINMU_END_TIME});
			throw new BusinessException();
		}
	}

	/**
	 * 振替日チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public static void chkFurikaeBiInput(AtendanceOnHolidayForm form) throws BusinessException {
		
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
				throw new BusinessException();
			}
			// 振替休日に日付以外が入力されている
			if (!CostDateUtils.isValidDate(furikaebi, CommonConstant.YYYY_MM_DD)) {
				// 振替休日には日付を入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_017, new String[] {FURIKAEBI});
				throw new BusinessException();
			}
			// 振替休日が当日の日付
			if (StringUtils.equals(form.getStrAtendanceDate(), furikaebi.replaceAll("/", ""))) {
				// 振替休日が当日の日付です
				form.putConfirmMsg(MessageConstants.COSE_E_018, new String[] {FURIKAEBI});
				throw new BusinessException();
			}
			// 振替休日の日付の就業データが処理済の場合
			BaseCondition condition = new BaseCondition();
			condition.addConditionEqual("users.id", form.getTaishoUserId());                  // 対象者
			condition.addConditionEqual("atendanceDate", furikaebi.replaceAll("/", ""));      // 振替日
			// 振替日の勤怠情報実績を取得
			KintaiInfo kintai = baseDao.findSingleResult(condition, KintaiInfo.class);
			// 振替日の勤怠情報実績が存在する
			if (kintai != null
					&& (StringUtils.isNotEmpty(kintai.getKinmuStartTime())
							|| kintai.getKyukaKekinKbnMaster() != null)) {
				// 勤怠実績が存在する、振替日を指定できません
				form.putConfirmMsg(MessageConstants.COSE_E_028);
				throw new BusinessException();
			}
		} else {
			// 振替休日が入力されている
			if (StringUtils.isNotEmpty(furikaebi)) {
				// 休日振替勤務以外のときは振替休日は入力できません
				form.putConfirmMsg(MessageConstants.COSE_E_019);
				throw new BusinessException();
			}
		}
	}

	/**
	 * プロジェクト名チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public static void chkProjectID(AtendanceOnHolidayForm form) throws BusinessException {
		
		// プロジェクトID
		String projectId = form.getSelectedProjCd();
		// 未選択
		if (StringUtils.isEmpty(projectId)) {
			// プロジェクト名を選択してください
			form.putConfirmMsg(MessageConstants.COSE_E_021, new String[] {PROJECT_NAME});
			throw new BusinessException();
		}
	}

	/**
	 * 業務内容チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public static void chkWorkDetail(AtendanceOnHolidayForm form) throws BusinessException {
		
		// 業務内容
		String workDetail = form.getStrCommont();
		// 未入力
		if (StringUtils.isEmpty(workDetail)) {
			// プロジェクト名を選択してください
			form.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {WORK_DETAIL});
			throw new BusinessException();
		}
	}

	/**
	 * 勤務日区分チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public static void chkWorkDayKbn(AtendanceOnHolidayForm form) throws BusinessException {
		
		// 勤務日区分が「休日」「休日振替勤務」以外
		if ((!StringUtils.equals(form.getSelectedAtendanceDayKbn(), CommonConstant.WORKDAY_KBN_KYUJITU))
				&& (!StringUtils.equals(form.getSelectedAtendanceDayKbn(), CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE))) {
			// 勤務区分の値が不正です
			form.putConfirmMsg(MessageConstants.COSE_E_022, new String[] {WORK_DAY_KBN});
			throw new BusinessException();
		}
	}
	

	/**
	 * 勤務日実績チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public static void chkDelKintaiInfo(AtendanceOnHolidayForm form) throws BusinessException {
		
		// 対象日
		String date = form.getStrAtendanceDate().replace("/", "");
		// 対象者
		String userId = form.getTaishoUserId();
		
		BaseCondition condition = new BaseCondition();
		
		condition.addConditionEqual("users.id", userId);         // 対象者
		condition.addConditionEqual("atendanceDate", date);      // 勤務日
		// 対象日の勤怠情報実績を取得
		KintaiInfo kintai = baseDao.findSingleResult(condition, KintaiInfo.class);
		// 勤怠実績が存在する場合
		if (kintai != null
				&& (StringUtils.isNotEmpty(kintai.getKinmuStartTime())
						|| kintai.getKyukaKekinKbnMaster() != null)) {
			// 勤怠実績が存在する、休日勤務情報を削除しないてください
			form.putConfirmMsg(MessageConstants.COSE_E_027);
			throw new BusinessException();
		}
	}
}
