package argo.cost.attendanceInput.checker;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.utils.CostDateUtils;

/**
 * 勤怠入力画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class AttendanceInputChecker {

	/**
	 * 共通DAO
	 */
	@Autowired
	static BaseDao baseDao;
	
	public static BaseDao getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(BaseDao baseDao) {
		AttendanceInputChecker.baseDao = baseDao;
	}

	/**
	 * 勤務開始時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 * @param standSTimeStr
	 *            定時出勤時刻(hhnn)
	 */
	public static void chkWorkSTimeFormat(AttendanceInputForm form, String standSTimeStr) {
		
		// 勤務開始時刻
		String kinmuSTime = form.getWorkSHour().concat(form.getWorkSMinute());
		form.setWorkSTime(kinmuSTime);
		
		// 勤務開始時刻が入力される場合
		if (!StringUtils.isEmpty(kinmuSTime)) {
			// 勤務開始時刻のhhnn形式値が数値以外
			if (!CostDateUtils.isTimeHHnn(kinmuSTime)) {
				// 勤務開始時刻を正しく入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {"勤務開始時刻"});
			} else {
				// 勤務開始時刻＜定時出勤時刻(hhnn)
				if (kinmuSTime.compareTo(standSTimeStr) < 0) {
					form.setWorkSTimeStr(CostDateUtils.AddForOneDay(kinmuSTime));
				} else {
					form.setWorkSTimeStr(kinmuSTime);
				}
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
	public static void chkWorkETimeFormat(AttendanceInputForm form, String standSTimeStr) {
		
		// 勤務終了時刻
		String kinmuETime = form.getWorkEHour().concat(form.getWorkEMinute());
		form.setWorkETime(kinmuETime);
		
		// 勤務終了時刻が入力される場合
		if (!StringUtils.isEmpty(kinmuETime)) {
			// 勤務終了時刻のhhnn形式値が数値以外
			if (!CostDateUtils.isTimeHHnn(kinmuETime)) {
				// 勤務終了時刻を正しく入力してください
				form.putConfirmMsg(MessageConstants.COSE_E_002, new String[] {"勤務終了時刻"});
			} else {
				// 勤務終了時刻＜＝定時出勤時刻(hhnn)
				if (kinmuETime.compareTo(standSTimeStr) <= 0) {
					form.setWorkETimeStr(CostDateUtils.AddForOneDay(kinmuETime));
				} else {
					form.setWorkETimeStr(kinmuETime);
				}
			}
		}
	}
}
