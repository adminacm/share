package argo.cost.setup.checker;



import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.setup.model.SetupForm;

/**
 * 個人設定変更画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class SetupChecker {

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
	@SuppressWarnings("deprecation")
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
		Date syuGyoStartDate = new Date(setupForm.getHolidayStart());
		Date syuGyoEndDate = new Date(setupForm.getHolidayEnd());
		Date nyuShaDate = new Date(setupForm.getJoinDate());
		Date taishokuDate = new Date(setupForm.getOutDate());
		// 休業開始日は入社日より後の日付であること
		if (syuGyoStartDate.before(nyuShaDate)) {
			
			setupForm.putConfirmMsg(MessageConstants.COSE_E_1105, new String[] {KYUGYO_START_DATE});
			throw new Exception();
	    
		// 退職日は入社日より後の日付であること
		} else if (taishokuDate.before(nyuShaDate)) {
			setupForm.putConfirmMsg(MessageConstants.COSE_E_1105,new String[] {TAISHOKU_DATE});
			throw new Exception();
		}
		
		
		// 休業終了日は休業開始日より後の日付であること
		if (syuGyoEndDate.before(syuGyoStartDate)) {
			setupForm.putConfirmMsg(MessageConstants.COSE_E_1106);
			throw new Exception();
			
		}
		
		// 入力された休業期間や退職日の勤怠が提出以降の状況の場合
		
		
		
		
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
