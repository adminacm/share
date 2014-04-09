package argo.cost.attendanceOnHolidayRecord.service;

import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;


public interface AttendanceOnHolidayRecordService {

	/**
	 * 休日出勤管理情報取得
	 * 
	 * @param form
	 *            休日出勤管理画面情報
	 */
	void searchAttendanceOnHolidayRecord(AttendanceOnHolidayRecordForm form);
}
