package argo.cost.attendanceOnHolidayRecord.service;

import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;

/**
 * <p>
 * 休日出勤管理サービスのインターフェース
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface AttendanceOnHolidayRecordService {

	/**
	 * 休日出勤管理情報をセット
	 * 
	 * @param attendanceOnHolidayRecordForm
	 *                                     休日出勤管理画面情報
	 */
	void setAttendanceOnHolidayRecordInfo(AttendanceOnHolidayRecordForm attendanceOnHolidayRecordForm);
}
