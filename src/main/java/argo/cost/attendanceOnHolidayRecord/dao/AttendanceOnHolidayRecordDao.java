package argo.cost.attendanceOnHolidayRecord.dao;

import java.util.List;

import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;

/**
 * <p>
 * 休日出勤管理ＤＡＯのインターフェース
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface AttendanceOnHolidayRecordDao {
	
	/**
	 * 休日振替勤務情報を取得
	 * 
	 * @param yearPeriod
	 *                  年度
	 * @param userId
	 *              氏名ID
	 * @return
	 *        休日振替勤務情報
	 */
	 List<HolidayAtendanceYotei> getHolidayExchangeWorkList(String yearPeriod, String userId);
	
	/**
	 * 休日勤務情報を取得
	 * 
	 * @param yearPeriod
	 *                  年度
	 * @param userId
	 *              氏名ID
	 * @return
	 *        休日勤務情報
	 */
	 List<HolidayAtendance> getHolidayWorkList(String yearPeriod, String userId);
}
