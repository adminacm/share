package argo.cost.attendanceOnHolidayRecord.service;

import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;


public interface AttendanceOnHolidayRecordService {

	/**
	 * 休日出勤管理情報セット
	 * 
	 * @param form
	 *            休日出勤管理画面情報
	 */
	void setAttendanceOnHolidayRecordInfo(AttendanceOnHolidayRecordForm form);
}
