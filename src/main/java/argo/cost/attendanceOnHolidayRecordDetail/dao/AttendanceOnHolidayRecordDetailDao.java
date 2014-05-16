package argo.cost.attendanceOnHolidayRecordDetail.dao;

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
	 * 休日出勤管理詳細データを取得
	 * 
	 * @param userId
	 * 	                           ユーザＩＤ
	 * @param date
	 *            日付
	 * @param workKbn
	 *               勤務区分
	 * @return 休日出勤管理詳細データ
	 */
	AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(
			String userId, String date, String workKbn);

	/**
	 * 休日出勤管理詳細の超勤振替申請情報を更新
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 * @return 更新結果フラグ
	 */
	Integer updateAttendanceOnHolidayRecordDetail(AttendanceOnHolidayRecordDetailForm attendanceOnHolidayRecordDetailForm);
}
