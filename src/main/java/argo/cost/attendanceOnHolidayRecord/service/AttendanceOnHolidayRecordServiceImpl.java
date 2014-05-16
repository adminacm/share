package argo.cost.attendanceOnHolidayRecord.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecord.dao.AttendanceOnHolidayRecordDao;
import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;
import argo.cost.attendanceOnHolidayRecord.model.HolidayExchangeWorkVO;
import argo.cost.attendanceOnHolidayRecord.model.HolidayOverWorkVO;

@Service
public class AttendanceOnHolidayRecordServiceImpl implements AttendanceOnHolidayRecordService {
	
	@Autowired
	private AttendanceOnHolidayRecordDao attendanceOnHolidayRecordDao;

	/**
	 * 休暇休日出勤管理情報セット
	 * 
	 * @param form
	 *            休日出勤管理画面情報
	 */
	@Override
	public void setAttendanceOnHolidayRecordInfo(AttendanceOnHolidayRecordForm form) {

		// 年度を取得
		String strYearPeriod = form.getYearPeriod();
		
		// 氏名を取得
		String strUserName = form.getUserName();
		
		// 休日振替勤務情報を取得
		List<HolidayExchangeWorkVO> holidayExchangeWorkList = attendanceOnHolidayRecordDao.getHolidayExchangeWorkList(strYearPeriod, strUserName);
		// 休日勤務情報を取得
		List<HolidayOverWorkVO> holidayOverWorkList = attendanceOnHolidayRecordDao.getHolidayOverWorkList(strYearPeriod, strUserName);

		// 休日振替勤務情報
		form.setHolidayExchangeWorkList(holidayExchangeWorkList);
		// 休日勤務情報をセット
		form.setHolidayOverWorkList(holidayOverWorkList);
	}
}
