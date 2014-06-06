package argo.cost.attendanceOnHolidayRecordDetail.dao;

import java.text.ParseException;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;

/**
 * <p>
 * 休日出勤管理詳細画面ＤＡＯのインターフェース
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface AttendanceOnHolidayRecordDetailDao {

	/**
	 * 勤務区分が「休日振替勤務」の詳細情報を取得
	 * 
	 * @param userId
	 * 	                           社員番号
	 * @param date
	 *            日付
	 * @return 休日出勤管理詳細情報
	 */
	AttendanceOnHolidayRecordDetailForm getFurikaeDetail(String userId, String date) throws ParseException;
	
	/**
	 * 勤務区分が「休日勤務」の詳細情報を取得
	 * 
	 * @param userId
	 * 	                           社員番号
	 * @param date
	 *            日付
	 * @return 休日出勤管理詳細情報
	 */
	AttendanceOnHolidayRecordDetailForm getWorkDetail(String userId, String date) throws ParseException;

	/**
	 * 休日出勤管理詳細の超勤振替申請情報を更新
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 *            
	 */
	void updateAttendanceOnHolidayRecordDetail(AttendanceOnHolidayRecordDetailForm form);
}
