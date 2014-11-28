package argo.cost.monthlyReport.checker;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.Locations;
import argo.cost.common.entity.MCalendar;
import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
import argo.cost.common.entity.WorkDayKbnMaster;
import argo.cost.common.exception.BusinessException;
import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.monthlyReport.model.MonthlyReportForm;

/**
 * 休日勤務入力画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class MonthlyReportChecker {

	/**
	 * 共通DAO
	 */
	BaseDao baseDao;
	
	MonthlyReportForm form;
	
	public MonthlyReportChecker(MonthlyReportForm form, BaseDao baseDao) {
		this.form = form;
		this.baseDao = baseDao;
	}

	/**
	 * 勤怠情報の存在チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 * @throws ParseException 
	 */
	public void chkKintaiInfoNull(MonthlyReportDispVO kintaiInfo) throws BusinessException, ParseException {
		
		// 社員番号
		String userId = form.getTaishoUserId();

		// 対象日
		String attDate = kintaiInfo.getDate();
		MCalendar calendar = baseDao.findById(attDate, MCalendar.class);
		
		BaseCondition condition = new BaseCondition();
		condition.addConditionEqual("users.id", userId);                 // 社員番号
		condition.addConditionEqual("atendanceDate", attDate);           // 対象日
		// 勤怠情報を取得
		KintaiInfo kintai = baseDao.findSingleResult(condition, KintaiInfo.class);
		
		// 出勤日の場合
		if (StringUtils.equals(CommonConstant.WORKDAY_FLAG_SHUKIN, calendar.getOnDutyFlg())) {
			// 当日の勤怠情報が存在しない場合
			if (kintai == null) {
				condition = new BaseCondition();
				condition.addConditionEqual("users.id", userId);              // 社員番号
				condition.addConditionEqual("furikaeDate", attDate);          // 振替日
				// 振替日は当日の勤怠情報を取得
				KintaiInfo furikaeInfo = baseDao.findSingleResult(condition, KintaiInfo.class);
				// 休暇期間以外の場合
				if (isWorkDate(attDate, userId)) {
					// 振替元の勤怠情報は存在しない場合
					if (furikaeInfo == null) {
						String kintaiDate = CostDateUtils.formatDate(kintaiInfo.getDate(), CommonConstant.YYYY_MM_DD);
						// 勤怠情報を入力ください
						form.putConfirmMsg(MessageConstants.COSE_E_1103, new String[] {kintaiDate});
						throw new BusinessException();
					} else {
						// 勤怠情報を追加する(振替休日)
						insertKintaiInfo(userId, form.getUserId(), attDate, CommonConstant.WORKDAY_KBN_FURIKAE_KYUJITU);
					}
				}
			}
		} else {
			condition = new BaseCondition();
			condition.addConditionEqual("users.id", userId);                 // 社員番号
			condition.addConditionEqual("atendanceDate", attDate);           // 対象日
			// 休日勤務情報を取得
			HolidayAtendanceYotei holidayInfo = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
			// 休暇期間以外の場合
			if (isWorkDate(attDate, userId)) {
				// 当日の勤怠情報が存在しない場合
				if (kintai == null) {
					// 休日勤務情報が存在する
					if (holidayInfo != null) {
						// 勤怠情報を入力ください
						form.putConfirmMsg(MessageConstants.COSE_E_1103, new String[] {kintaiInfo.getDate()});
						throw new BusinessException();
					} else {
						// 勤怠情報が存在しない場合
						if (kintai == null) {
							// 勤怠情報を追加する(休日)
							insertKintaiInfo(userId, form.getUserId(), attDate, CommonConstant.WORKDAY_KBN_KYUJITU);
						}
					}
				}
			}
		}
	}

	/**
	 * 勤務開始時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public void chkKintaiInfoInput(MonthlyReportDispVO kintaiInfo) throws BusinessException {
		
		// 社員番号
		String userId = form.getTaishoUserId();
		// 社員情報を取得
		Users userInfo = baseDao.findById(userId, Users.class);

		// 対象日
		String attDate = kintaiInfo.getDate();
		// 対象日が入社日以降、かつ対象日が休業期間に含まれない、かつ対象日が退職日以前
		if (userInfo.getNyushaDate().compareTo(attDate) <= 0 
				&& attDate.compareTo(userInfo.getTaisyokuDate()) < 0
				&& ((attDate.compareTo(userInfo.getKyugyoStartDate()) < 0
						&& userInfo.getKyugyoEndDate().compareTo(attDate) < 0))
						|| (StringUtils.isEmpty(userInfo.getKyugyoStartDate())
								&& StringUtils.isEmpty(userInfo.getKyugyoEndDate()))) {
			// 勤務開始時刻と勤務終了時刻が未入力で
			if (StringUtils.isEmpty(kintaiInfo.getWorkSTime())
					&& StringUtils.isEmpty(kintaiInfo.getWorkETime())) {
				// 勤務区分"01"(出勤)or"03"(休日振替勤務)で休暇欠勤区分が未入力
				if ((StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, kintaiInfo.getWorkKbn())
						|| StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, kintaiInfo.getWorkKbn()))
						&& StringUtils.isEmpty(kintaiInfo.getKyukaKb())) {
					// 勤怠情報を入力ください
					form.putConfirmMsg(MessageConstants.COSE_E_1103, new String[] {kintaiInfo.getDate()});
					
					throw new BusinessException();
				}
			}
		}
	}

	/**
	 * 勤務開始時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public void chkKintaiInfoDaikyu(MonthlyReportDispVO kintaiInfo) throws BusinessException {

		// 社員番号
		String userId = form.getTaishoUserId();
		// 対象日
		String attDate = kintaiInfo.getDate();
		// 休暇欠勤区分"05"(代休)で
		if (StringUtils.equals(CommonConstant.KK_KBN_TAIKYU, kintaiInfo.getKyukaKb())) {

			BaseCondition condition = new BaseCondition();
			condition.addConditionEqual("users.id", userId);          // 社員番号
	        // 勤務日区分
			condition.addConditionIn("workDayKbnMaster.code", new String[] {CommonConstant.WORKDAY_KBN_SHUKIN, CommonConstant.WORKDAY_KBN_FURIKAE_KYUJITU});
			// 代休取得期限
			condition.addConditionGreaterEqualThan("daikyuGetShimekiriDate", attDate);
			// 勤務時間数＞＝7.5
			condition.addConditionGreaterEqualThan("kinmuJikansu", new BigDecimal(7.5));
			// 代休日がNULL
			condition.addConditionIsNull("daikyuDate");
			// 代休可能の勤怠情報を取得する
			List<KintaiInfo> entity = baseDao.findResultList(condition, KintaiInfo.class);
			// 代休情報を取得されない
			if (entity == null || entity.isEmpty()) {
				// 取得できる代休はありません
				form.putConfirmMsg(MessageConstants.COSE_E_023, new String[] {attDate});
				throw new BusinessException();
			}
		}
	}

	/**
	 * 休暇欠勤区分・勤務時刻(休業期間)の整合性チェック
	 * 
	 * 		①入社日より前、または休業期間中、または退職日より後の日付で
	 * 			勤務区分"01"(出勤)or"03"(休日振替勤務)の場合に勤務開始時刻または休暇欠勤区分が入力されている場合はエラー
	 * 				☆休業期間中と退職後の日付には勤怠を入力できません
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public void chkKyugyoKikan(MonthlyReportDispVO kintaiInfo) throws BusinessException {
		
		// 勤怠データ存在するのみ、チェックを実行する。
		if (StringUtils.isEmpty(kintaiInfo.getWorkKbn())) {
			return;
		}
		// 対象者
		String taishoUserId = form.getTaishoUserId();
		// 対象日
		String taishoDate = kintaiInfo.getDate();
		// 対象ユーザー情報を取得
		Users user = baseDao.findById(taishoUserId, Users.class);
		// 入社日
		String nyushaDate = user.getNyushaDate();
		// 休業開始日
		String kyugyoSDate = user.getKyugyoStartDate();
		// 休業終了日
		String kyugyoEdate = user.getKyugyoEndDate();
		// 退職日
		String taishokuDate = user.getTaisyokuDate();
		// 入社日より前
		if (taishoDate.compareTo(nyushaDate) < 0) {
			// 休業期間中と退職後の日付には勤怠を入力できません
			form.putConfirmMsg(MessageConstants.COSE_E_026);
			throw new BusinessException();
		}
		// 休業開始日が存在する
		if (StringUtils.isNotEmpty(kyugyoSDate)) {
			// 休業終了日が存在する
			if (StringUtils.isNotEmpty(kyugyoEdate)) {
				// 休業期間中
				if (kyugyoSDate.compareTo(taishoDate) <= 0
						&& taishoDate.compareTo(kyugyoEdate) <= 0) {
					// 休業期間中と退職後の日付には勤怠を入力できません
					form.putConfirmMsg(MessageConstants.COSE_E_026);
					throw new BusinessException();
				} 
			} else {
				if (kyugyoSDate.compareTo(taishoDate) <= 0) {
					// 休業期間中と退職後の日付には勤怠を入力できません
					form.putConfirmMsg(MessageConstants.COSE_E_026);
					throw new BusinessException();
				}
			
			}
		}
		
		// 退職日より後の日付
		if (StringUtils.isNotEmpty(taishokuDate)
				&& taishokuDate.compareTo(taishoDate) <= 0) {
			// 休業期間中と退職後の日付には勤怠を入力できません
			form.putConfirmMsg(MessageConstants.COSE_E_026);
			throw new BusinessException();
		}
	}

	/**
	 * 代休期限日のチェック
	 * 
	 * 		①処理月が代休取得期限となる休日勤務があり
	 * 			代休未取得かつ超勤振替未申請の場合
	 * 				☆代休取得期限切れとなる休日勤務があります
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws BusinessException 
	 */
	public void chkDaikyuKigen() throws BusinessException {
		
		// 検索条件を設定
		BaseCondition condition = new BaseCondition();
		// 社員番号
		condition.addConditionEqual("users.id", form.getTaishoUserId());
		// 代休取得期限
		condition.addConditionLike("daikyuGetShimekiriDate", form.getYearMonth().substring(0, 6).concat("%"));
		// 勤務区分が「02」休日
		condition.addConditionEqual("workDayKbnMaster.code", CommonConstant.WORKDAY_KBN_KYUJITU);
		// 代休日は存在しない
		condition.addConditionIsNull("daikyuDate");
		// 超勤振替未申請
		condition.addConditionIsNull("approvalManage2");
		// 代休情報を取得
		List<KintaiInfo> result = baseDao.findResultList(condition, KintaiInfo.class);
		
		// 代休情報が存在する場合
		if (result != null && result.size() > 0) {
			// 休暇欠勤区分が入力されています
			form.putConfirmMsg(MessageConstants.COSE_E_1108);
			throw new BusinessException();
		}
	}

	/**
	 * 勤怠情報を追加する
	 * 
	 * @param userId
	 *            対象者ID
	 * @param loginId
	 *            ログインID
	 * @param attDate
	 *            対象日ID
	 *            
	 * @throws BusinessException 
	 */
	private void insertKintaiInfo(String userId, String loginId, String attDate, String kbn) throws BusinessException {

		// 勤怠情報
		KintaiInfo entity = new KintaiInfo();
		// ユーザー情報
		Users user = baseDao.findById(userId, Users.class);
		entity.setUsers(user);                                   // 社員番号
		entity.setAtendanceDate(attDate);                        // 対象日
		WorkDayKbnMaster workDayKbnMaster = baseDao.findById(kbn, WorkDayKbnMaster.class);
		entity.setWorkDayKbnMaster(workDayKbnMaster);            // 勤務日区分
		// シフト時刻情報
		ShiftJikoku shiftInfo = baseDao.findById(user.getStandardShiftCd(), ShiftJikoku.class);
		entity.setShiftJikoku(shiftInfo);                        // シフトコード
		// ロケーション情報
		Locations location = baseDao.findById("01", Locations.class);
		entity.setLocation(location);                            // ロケーションコード
		entity.setCreatedUserId(loginId);               // 登録者
		entity.setCreatedDate(new Timestamp(System.currentTimeMillis())); // 登録時刻
		entity.setUpdatedUserId(loginId);               // 更新者
		entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));  // 更新時刻
		
		// 勤怠情報を追加する
		baseDao.insert(entity);
	}
	
	private boolean isWorkDate(String taishoDate, String taishoUserId) {
		
		// 対象ユーザー情報を取得
		Users user = baseDao.findById(taishoUserId, Users.class);
		// 入社日
		String nyushaDate = user.getNyushaDate();
		// 休業開始日
		String kyugyoSDate = user.getKyugyoStartDate();
		// 休業終了日
		String kyugyoEdate = user.getKyugyoEndDate();
		// 退職日
		String taishokuDate = user.getTaisyokuDate();
		// 入社日より前
		if (taishoDate.compareTo(nyushaDate) < 0) {
			return false;
		}
		// 休業開始日が存在する
		if (StringUtils.isNotEmpty(kyugyoSDate)) {
			// 休業終了日が存在する
			if (StringUtils.isNotEmpty(kyugyoEdate)) {
				// 休業期間中
				if (kyugyoSDate.compareTo(taishoDate) <= 0
						&& taishoDate.compareTo(kyugyoEdate) <= 0) {
					return false;
				} 
			} else if (kyugyoSDate.compareTo(taishoDate) <= 0) {
					return false;
			}
		}
		// 退職日より後の日付
		if (StringUtils.isNotEmpty(taishokuDate)
				&& taishokuDate.compareTo(taishoDate) <= 0) {
			return false;
		}
		return true;
	}
}
