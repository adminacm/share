package argo.cost.setup.checker;



import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
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
	 * 入社日
	 */
	private final static String  NYUSHYA_DATE = "入社日";
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
	 * 入社日チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @throws Exception 
	 */
	public static void chkNyushyaDate(SetupForm setupForm) throws Exception {
		
		// 入社日
		String strNyushaDate = setupForm.getJoinDate();
		// 未入力
		if (StringUtils.isEmpty(strNyushaDate)) {
			// 入社日が未入力です
			setupForm.putConfirmMsg(MessageConstants.COSE_E_001, new String[] {NYUSHYA_DATE});
			throw new Exception();
		}
		// 入社日のyyyy/MM/dd形式値が数値以外
		if (!CostDateUtils.isValidDate(strNyushaDate, CommonConstant.YYYY_MM_DD)) {
			// 入社日を正しく入力してください
			setupForm.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {NYUSHYA_DATE});
			throw new Exception();
		}
		
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
		// 休業開始と休業終了がnull以外の場合
		if (!StringUtils.isEmpty(setupForm.getHolidayStart()) && !StringUtils.isEmpty(setupForm.getHolidayEnd())) {

			// 休業開始日
			String holidayStart = setupForm.getHolidayStart().replace("/", "").substring(0, 6);
			// 休業終了日
			String holidayEnd = setupForm.getHolidayEnd().replace("/", "").substring(0, 6);
			
			// 退職日の承認情報を取得
			BaseCondition condition = new BaseCondition();
			condition.addConditionBetween("syoriYm", holidayStart, holidayEnd);
			// 先日の勤怠情報を取得
			List<ApprovalManage> resultList = baseDao.findResultList(condition, ApprovalManage.class);
			
			if (resultList.size() > 0) {
				
				setupForm.putConfirmMsg(MessageConstants.COSE_E_1107);
				throw new Exception();
			}
		}
		// 休業開始がnull以外の場合
		if (!StringUtils.isEmpty(setupForm.getHolidayStart()) && StringUtils.isEmpty(setupForm.getHolidayEnd())) {

			// 休業開始日
			String holidayStart = setupForm.getHolidayStart().replace("/", "").substring(0, 6);
		
			// 退職日の承認情報を取得
			BaseCondition condition = new BaseCondition();
			condition.addConditionGreaterEqualThan("syoriYm", holidayStart);
			// 先日の勤怠情報を取得
			List<ApprovalManage> resultList = baseDao.findResultList(condition, ApprovalManage.class);
			
			if (resultList.size() > 0) {
				
				setupForm.putConfirmMsg(MessageConstants.COSE_E_1107);
				throw new Exception();
			}
		}
		// 退職日がnull以外の場合
		if (!StringUtils.isEmpty(setupForm.getOutDate())) {

			// 休業開始日
			String outDate = setupForm.getOutDate().replace("/", "").substring(0, 6);
			
			// 退職日の承認情報を取得
			BaseCondition condition = new BaseCondition();
			condition.addConditionGreaterEqualThan("syoriYm", outDate);
			// 先日の勤怠情報を取得
			List<ApprovalManage> resultList = baseDao.findResultList(condition, ApprovalManage.class);
			
			if (resultList.size() > 0) {
				
				setupForm.putConfirmMsg(MessageConstants.COSE_E_1107);
				throw new Exception();
			}
		}
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
}
