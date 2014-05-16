package argo.cost.attendanceOnHolidayRecord.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecord.dao.AttendanceOnHolidayRecordDao;
import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;
import argo.cost.attendanceOnHolidayRecord.model.HolidayExchangeWorkVO;
import argo.cost.attendanceOnHolidayRecord.model.HolidayOverWorkVO;

/**
 * <p>
 * 休日出勤管理サービス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Service
public class AttendanceOnHolidayRecordServiceImpl implements AttendanceOnHolidayRecordService {
	
	/**
	 * 休日出勤管理DAO
	 */
	@Autowired
	private AttendanceOnHolidayRecordDao attendanceOnHolidayRecordDao;

	/**
	 * 休日出勤管理情報をセット
	 * 
	 * @param attendanceOnHolidayRecordForm
	 *                                     休日出勤管理画面情報
	 */
	@Override
	public void setAttendanceOnHolidayRecordInfo(AttendanceOnHolidayRecordForm attendanceOnHolidayRecordForm) {

		// 年度を取得
		String strYearPeriod = attendanceOnHolidayRecordForm.getYearPeriod();
		
		// 氏名を取得
		String strUserName = attendanceOnHolidayRecordForm.getUserName();
		
		// 休日振替勤務情報を取得
		List<HolidayExchangeWorkVO> holidayExchangeWorkList = attendanceOnHolidayRecordDao.getHolidayExchangeWorkList(strYearPeriod, strUserName);
		// 休日勤務情報を取得
		List<HolidayOverWorkVO> holidayOverWorkList = attendanceOnHolidayRecordDao.getHolidayOverWorkList(strYearPeriod, strUserName);

		// 休日振替勤務情報をセット
		attendanceOnHolidayRecordForm.setHolidayExchangeWorkList(holidayExchangeWorkList);
		// 休日勤務情報をセット
		attendanceOnHolidayRecordForm.setHolidayOverWorkList(holidayOverWorkList);
	}
}
