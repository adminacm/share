package argo.cost.attendanceOnHolidayRecord.dao;

import java.util.List;

import argo.cost.attendanceOnHolidayRecord.model.HolidayExchangeWorkVO;
import argo.cost.attendanceOnHolidayRecord.model.HolidayOverWorkVO;

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
	 * @param userName
	 *                氏名
	 * @return
	 *        休日振替勤務情報
	 */
	List<HolidayExchangeWorkVO> getHolidayExchangeWorkList(String yearPeriod, String userName);
	
	/**
	 * 休日勤務情報を取得
	 * 
	 * @param yearPeriod
	 *                  年度
	 * @param userName
	 *                氏名
	 * @return
	 *        休日勤務情報
	 */
	List<HolidayOverWorkVO> getHolidayOverWorkList(String yearPeriod, String userName);
}
