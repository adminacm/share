package argo.cost.monthlyReport.checker;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.Users;
import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.monthlyReport.model.MonthlyReportForm;

/**
 * 休日勤務入力画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class MonthlyReportChecker {

	/**
	 * 勤務開始時刻。
	 */
	private final static String  KINMU_START_TIME = "勤務開始時刻";
	/**
	 * 勤務終了時刻
	 */
	private final static String KINMU_END_TIME = "勤務終了時刻";
	
	/**
	 * 共通DAO
	 */
	@Autowired
	static BaseDao baseDao;
	
	public static BaseDao getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(BaseDao baseDao) {
		MonthlyReportChecker.baseDao = baseDao;
	}

	/**
	 * 勤務開始時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKintaiInfoInput(MonthlyReportForm form) {
		
		// 社員番号
		String userId = form.getTaishoUserId();
		// 社員情報を取得
		Users userInfo = baseDao.findById(userId, Users.class);

		// 対象日が入社日以降、かつ対象日が休業期間に含まれない、かつ対象日が退職日以前
		List<MonthlyReportDispVO> monthlyReportDispVOList = form.getmRList();
		
		for (int i = 0; i < monthlyReportDispVOList.size()-1; i++) {
			MonthlyReportDispVO kintaiInfo = monthlyReportDispVOList.get(i);
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
					}
				}
			}
		}
	}
//
//	/**
//	 * 勤務開始時刻チェック
//	 * 
//	 * @param form
//	 *            画面情報オブジェクト
//	 */
//	public static void chkKintaiInfo(MonthlyReportDispVO kintaiInfo) {
//		
//		// 社員番号
//		String userId = form.getTaishoUserId();
//		// 社員情報を取得
//		Users userInfo = baseDao.findById(userId, Users.class);
//
//		// 対象日が入社日以降、かつ対象日が休業期間に含まれない、かつ対象日が退職日以前
//		List<MonthlyReportDispVO> monthlyReportDispVOList = form.getmRList();
//		
//		for (int i = 0; i < monthlyReportDispVOList.size()-1; i++) {
//			MonthlyReportDispVO kintaiInfo = monthlyReportDispVOList.get(i);
//			// 対象日
//			String attDate = kintaiInfo.getDate();
//			// 対象日が入社日以降、かつ対象日が休業期間に含まれない、かつ対象日が退職日以前
//			if (userInfo.getNyushaDate().compareTo(attDate) <= 0 
//					&& attDate.compareTo(userInfo.getTaisyokuDate()) < 0
//					&& ((attDate.compareTo(userInfo.getKyugyoStartDate()) < 0
//							&& userInfo.getKyugyoEndDate().compareTo(attDate) < 0))
//							|| (StringUtils.isEmpty(userInfo.getKyugyoStartDate())
//									&& StringUtils.isEmpty(userInfo.getKyugyoEndDate()))) {
//				// 勤務開始時刻と勤務終了時刻が未入力で
//				if (StringUtils.isEmpty(kintaiInfo.getWorkSTime())
//						&& StringUtils.isEmpty(kintaiInfo.getWorkETime())) {
//					// 勤務区分"01"(出勤)or"03"(休日振替勤務)で休暇欠勤区分が未入力
//					if ((StringUtils.equals(CommonConstant.WORKDAY_KBN_SHUKIN, kintaiInfo.getWorkKbn())
//							|| StringUtils.equals(CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE, kintaiInfo.getWorkKbn()))
//							&& StringUtils.isEmpty(kintaiInfo.getKyukaKb())) {
//						// 勤怠情報を入力ください
//						form.putConfirmMsg(MessageConstants.COSE_E_1103, new String[] {kintaiInfo.getDate()});
//					}
//				}
//			}
//		}
//	}
}
