package argo.cost.holidayRecord.dao;

import java.util.List;

import argo.cost.common.entity.KyukaKekin;
import argo.cost.holidayRecord.model.PayHolidayVO;

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
	 * @return 有給休暇情報
	 */
	List<PayHolidayVO> getPayHolidayList(String userId, String yearPeriod);
	
	/**
	 * 欠勤情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 * @return 欠勤情報
	 */
	List<KyukaKekin> getAbsenceList(String userId, String yearPeriod);
	
	/**
	 * 特別休暇情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 * @return 特別休暇情報
	 */
	List<KyukaKekin> getSpecialHolidayList(String userId, String yearPeriod);
}
