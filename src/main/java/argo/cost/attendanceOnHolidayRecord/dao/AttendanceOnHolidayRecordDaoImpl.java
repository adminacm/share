package argo.cost.attendanceOnHolidayRecord.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.attendanceOnHolidayRecord.model.HolidayExchangeWorkVO;
import argo.cost.attendanceOnHolidayRecord.model.HolidayOverWorkVO;

@Repository
public class AttendanceOnHolidayRecordDaoImpl implements AttendanceOnHolidayRecordDao {
	
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
	@Override
	public List<HolidayExchangeWorkVO> searchholidayExchangeWorkList(
			String yearPeriod, String userName) {

		// TODO ＤＢから休日振替勤務情報を取得
		List<HolidayExchangeWorkVO> resultList = new ArrayList<HolidayExchangeWorkVO>();
		HolidayExchangeWorkVO info = new HolidayExchangeWorkVO();
		info.setHolidayTurnedWorkingDate("2014/5/5");
		info.setWorkingDayTurnedHolidayDate("2014/5/9");
		resultList.add(info);
		
		return resultList;
	}

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
	@Override
	public List<HolidayOverWorkVO> searchholidayOverWorkList(String yearPeriod,
			String userName) {

		// TODO ＤＢから休日勤務情報を取得
		List<HolidayOverWorkVO> resultList = new ArrayList<HolidayOverWorkVO>();
		HolidayOverWorkVO info = new HolidayOverWorkVO();
		info.setHolidayOverWorkDate("2014/4/13");
		info.setTurnedHolidayEndDate("2014/6/30");
		info.setTurnedHolidayDate("2014/4/18");
		info.setOverWorkTurnedReqDate("");
		resultList.add(info);
		
		info = new HolidayOverWorkVO();
		info.setHolidayOverWorkDate("2014/5/10");
		info.setTurnedHolidayEndDate("2014/7/31");
		info.setTurnedHolidayDate("超勤振替");
		info.setOverWorkTurnedReqDate("2014/5/20");
		resultList.add(info);
		
		info = new HolidayOverWorkVO();
		info.setHolidayOverWorkDate("2014/5/18");
		info.setTurnedHolidayEndDate("2014/7/31");
		info.setTurnedHolidayDate("");
		info.setOverWorkTurnedReqDate("");
		resultList.add(info);
		
		return resultList;
	}

}
