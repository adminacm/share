package argo.cost.attendanceOnHolidayRecordDetail.service;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;



public interface AttendanceOnHolidayRecordDetailService {

	/**
	 * 休日出勤管理詳細画面情報取得
	 * 
	 * @param userId
	 * 	                           ユーザＩＤ
	 * @param date
	 *            日付
	 * @param workKbn
	 *               勤務区分
	 */
	AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(String userId, String date, String workKbn);

	/**
	 * 超勤振替申請を提出
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 * 
	 * @return
	 *        結果フラグ
	 */
	Integer overWorkPayRequest(AttendanceOnHolidayRecordDetailForm form);
}
