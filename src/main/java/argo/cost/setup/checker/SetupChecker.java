package argo.cost.setup.checker;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.setup.model.SetupForm;

/**
 * 個人設定変更画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class SetupChecker {
	
	/**
	 * 共通DAO
	 */
	@Autowired
	static BaseDao baseDao;
	
	public static BaseDao getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(BaseDao baseDao) {
		SetupChecker.baseDao = baseDao;
	}
	/**
	 * 休業開始日
	 */
	private final static String  KYUGYO_START_DATE = "休業開始日";
	/**
	 * 休業終了日
	 */
	private final static String KYUGYO_END_DATE = "休業終了日";
	/**
	 * 退職日
	 */
	private final static String TAISHOKU_DATE = "退職日";
	
	/**
	 * 保存時の入力チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws Exception 
	 */
	public static void chkSaveDate(SetupForm setupForm) throws Exception {
		
		// 休業開始日、退職日は入社日より後の日付であること
		// 休業開始日は入社日より後の日付であること
		if (!StringUtils.isEmpty(setupForm.getHolidayStart()) && setupForm.getHolidayStart().compareTo(setupForm.getJoinDate()) < 0) {
			setupForm.putConfirmMsg(MessageConstants.COSE_E_1105, new String[] {KYUGYO_START_DATE});
			throw new Exception();
	    
		// 退職日は入社日より後の日付であること
		} else if (!StringUtils.isEmpty(setupForm.getOutDate()) && setupForm.getOutDate().compareTo(setupForm.getJoinDate()) < 0) {
			setupForm.putConfirmMsg(MessageConstants.COSE_E_1105,new String[] {TAISHOKU_DATE});
			throw new Exception();
		}
		
		// 休業終了日は休業開始日より後の日付であること
		if (!StringUtils.isEmpty(setupForm.getHolidayEnd()) && !StringUtils.isEmpty(setupForm.getHolidayStart()) 
				&& setupForm.getHolidayEnd().compareTo(setupForm.getHolidayStart()) < 0) {
			setupForm.putConfirmMsg(MessageConstants.COSE_E_1106);
			throw new Exception();
		}
		
		// 入力された休業期間や退職日の勤怠が提出以降の状況の場合
		// 休業期間内の勤怠をチェックする
		chkApplyStatus(setupForm, setupForm.getHolidayStart(), setupForm.getHolidayEnd(), 0);
		// 退職日より後の勤怠をチェックする
		chkApplyStatus(setupForm, null, setupForm.getOutDate(), 0);
	}
	
	/**
	 * 休業開始日チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws Exception 
	 */
	public static void chkKyukaKaishiDate(SetupForm form) throws Exception {
		
		// 休業開始日
		String date = form.getHolidayStart();
		// 休業開始日のyyyy/MM/dd形式値が数値以外
		if (StringUtils.isNotEmpty(date) && !CostDateUtils.isValidDate(date, CommonConstant.YYYY_MM_DD)) {
			// 休業開始日を正しく入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KYUGYO_START_DATE});
			throw new Exception();
		}
	}

	/**
	 * 休業終了日チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws Exception 
	 */
	public static void chkKyukaShyuryoDate(SetupForm form) throws Exception {
		
		// 休業終了日
		String date = form.getHolidayEnd();
		// 休業終了日のyyyy/MM/dd形式値が数値以外
		if (StringUtils.isNotEmpty(date) && !CostDateUtils.isValidDate(date, CommonConstant.YYYY_MM_DD)) {
			// 休業終了日を正しく入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {KYUGYO_END_DATE});
			throw new Exception();
		}
	}

	/**
	 * 退職日チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws Exception 
	 */
	public static void chkTaishokuDate(SetupForm form) throws Exception {
		
		// 退職日
		String date = form.getOutDate();
		// 退職日のyyyy/MM/dd形式値が数値以外
		if (StringUtils.isNotEmpty(date) && !CostDateUtils.isValidDate(date, CommonConstant.YYYY_MM_DD)) {
			// 退職日を正しく入力してください
			form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {TAISHOKU_DATE});
			throw new Exception();
		}
	}
	
	private static void chkApplyStatus(SetupForm form, String sDate, String eDate, int flag) throws Exception {
		
		// 対象者
		String userId = form.getUserId();
		BaseCondition condition = new BaseCondition();
		List<KintaiInfo> kintaiList = new ArrayList<KintaiInfo>();
		// 期間内の場合
		if (0 == flag) {
			// 期間開始日が存在する場合
			if (StringUtils.isNotEmpty(sDate)) {
				
				// 期間終了日が存在する場合
				if (StringUtils.isNotEmpty(eDate)) {
					condition = new BaseCondition();
					condition.addConditionEqual("users.id", userId);
					// 勤怠日は期間内
					condition.addConditionBetween("atendanceDate",
							sDate.replaceAll("/", StringUtils.EMPTY),
							eDate.replaceAll("/", StringUtils.EMPTY));
					// 期間内の勤怠情報を取得
					kintaiList = baseDao.findResultList(condition, KintaiInfo.class); 
				} else {
					condition = new BaseCondition();
					condition.addConditionEqual("users.id", userId);
					// 勤怠日は期間開始日より後
					condition.addConditionGreaterEqualThan("atendanceDate",
							sDate.replaceAll("/", StringUtils.EMPTY));
					// 期間開始日より後の勤怠情報を取得
					kintaiList = baseDao.findResultList(condition, KintaiInfo.class); 
				}
			}
		} else {
			
			// 期間終了日が存在する場合
			if (StringUtils.isNotEmpty(eDate)) {
				condition = new BaseCondition();
				condition.addConditionEqual("users.id", userId);
				// 勤怠日は期間終了日より後
				condition.addConditionGreaterEqualThan("atendanceDate",
						eDate.replaceAll("/", StringUtils.EMPTY));
				// 期間開始日より後の勤怠情報を取得
				kintaiList = baseDao.findResultList(condition, KintaiInfo.class);
			}
		}
		// 休業期間内の勤怠情報
		for (KintaiInfo kintaiInfo : kintaiList) {
			// 申請情報が存在する場合
			if (kintaiInfo.getApprovalManage1() != null) {
				// 申請状態
				String status = kintaiInfo.getApprovalManage1().getStatusMaster().getCode();
				// 「作成中」以外の場合
				if (!StringUtils.equals(CommonConstant.STATUS_SAKUSEIJYOU, status)) {
					// 休業終了日は休業開始日よりあとの日付を入力してください
					form.putConfirmMsg(MessageConstants.COSE_E_1107);
					throw new Exception();
				}
			}
		}
	}
}
