package argo.cost.attendanceOnHoliday.dao;


/**
 * 休日勤務入力画面DAO
 * 休日勤務入力画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface AtendanceOnHolidayDao {
	
	/**
	 * 処理状況を取得
	 * 
	 * @param form 
	 * 				画面情報
	 * 
	 * @return 更新結果　０：更新失敗　１：更新成功
	 */
	String getAttendanceInfoStatus(String userId, String date);
}
