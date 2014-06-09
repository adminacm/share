package argo.cost.attendanceOnHolidayRecordDetail.service;

import java.text.ParseException;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;

/**
 * <p>
 * 休日出勤管理詳細画面サービスのインターフェース
 * </p>
 *
 * @author COST argo Corporation.
 */
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
	 * @return 休日出勤管理詳細画面情報取得
	 */
	AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(String userId, String date, String workKbn) throws ParseException;

	/**
	 * 超勤振替申請を提出
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 *            
	 */
	void overWorkPayRequest(AttendanceOnHolidayRecordDetailForm form);
}
