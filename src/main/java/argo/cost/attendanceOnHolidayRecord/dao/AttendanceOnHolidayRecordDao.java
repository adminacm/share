package argo.cost.attendanceOnHolidayRecord.dao;

import java.util.List;

import argo.cost.attendanceOnHolidayRecord.model.HolidayExchangeWorkVO;
import argo.cost.attendanceOnHolidayRecord.model.HolidayOverWorkVO;

public interface AttendanceOnHolidayRecordDao {
	
	/**
	 * 休日振替勤務情報取得
	 * 
	 * @param yearPeriod
	 *            年度
	 * @param userName
	 *            氏名
	 * @return
	 *        休日振替勤務リスト
	 */
	List<HolidayExchangeWorkVO> getHolidayExchangeWorkList(String yearPeriod, String userName);
	
	/**
	 * 休日勤務情報取得
	 * 
	 * @param yearPeriod
	 *            年度
	 * @param userName
	 *            氏名
	 * @return
	 *        休日勤務リスト
	 */
	List<HolidayOverWorkVO> getHolidayOverWorkList(String yearPeriod, String userName);

}
