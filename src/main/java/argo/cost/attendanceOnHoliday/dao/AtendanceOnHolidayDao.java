package argo.cost.attendanceOnHoliday.dao;

import java.util.ArrayList;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHoliday;
import argo.cost.attendanceOnHoliday.model.CodeNameMap;

public interface AtendanceOnHolidayDao {

	/**
	 * 当日休日勤務情報有無チャック
	 * 
	 * @param userId
	 *            ユーザID
	 * @return ユーザ情報
	 */
	boolean atendanceOnHolidayDataChk(String userId, String date);

	/**
	 * 当日休日勤務情報取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @return ユーザ情報
	 */
	AtendanceOnHoliday atendanceOnHolidayDataGet(String userId, String date);

	/**
	 * 勤務日区分リストを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @return ユーザ情報
	 */
	ArrayList<CodeNameMap> getAtendanceDayKbnList();

	/**
	 * プロジェクトリストを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @return ユーザ情報
	 */
	ArrayList<CodeNameMap> getProjectKbnList();
	
	/**
	 * 休日勤務データの削除
	 * 
	 * @param userId
	 *            ユーザID
	 */
	String deleteAtendanceOnHoliday(String strAtendanceDate, String UserID);
	
	/**
	 * 休日勤務データの保存
	 * 
	 * @param userId
	 *            ユーザID
	 */
	String saveAtendanceOnHoliday(AtendanceOnHoliday atendanceOnHoliday,String UserID);

}
