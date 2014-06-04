package argo.cost.holidayRecord.dao;

import java.util.List;

import argo.cost.holidayRecord.model.AbsenceVO;
import argo.cost.holidayRecord.model.PayHolidayVO;
import argo.cost.holidayRecord.model.SpecialHolidayVO;

/**
 * <p>
 * 休暇管理画面ＤＡＯのインターフェイス
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface HolidayRecordDao {
	
	/**
	 * 有給休暇情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 */
	List<PayHolidayVO> getPayHolidayList(String userId, String yearPeriod);
	
	/**
	 * 欠勤情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 */
	List<AbsenceVO> getAbsenceList(String userId, String yearPeriod);
	
	/**
	 * 特別休暇情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 */
	List<SpecialHolidayVO> getSpecialHolidayList(String userId, String yearPeriod);
}
