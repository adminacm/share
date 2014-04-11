package argo.cost.attendanceOnHolidayRecordDetail.dao;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;


public interface AttendanceOnHolidayRecordDetailDao {

	/**
	 * 休日出勤管理詳細画面情報取得
	 * 
	 * @param userId
	 * 	                           ユーザＩＤ
	 * @param date
	 *            日付
	 * @return
	 *        休日出勤管理詳細画面情報
	 */
	AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(
			String userId, String date, String workKbn);

	/**
	 * 超勤振替申請を提出
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 * 
	 */
	Integer updateAttendanceOnHolidayRecordDetail(AttendanceOnHolidayRecordDetailForm form);

}
