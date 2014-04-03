package argo.cost.attendanceOnHoliday.dao;

import java.util.ArrayList;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.model.ListItemVO;

public interface AtendanceOnHolidayDao {

	/**
	 * 当日休日勤務情報有無チャック
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            当前の日付
	 * @return ユーザ情報
	 */
	boolean atendanceOnHolidayDataChk(String userId, String date);

	/**
	 * 当日休日勤務情報取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            当前の日付
	 * @return 当日休日勤務情報
	 */
	AtendanceOnHolidayForm atendanceOnHolidayDataGet(String userId, String date);

	/**
	 * 勤務日区分リストを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @return 勤務日区分リスト
	 */
	ArrayList<ListItemVO> getAtendanceDayKbnList();
	
	/**
	 * 休日勤務データの削除
	 * 
	 * @param strAtendanceDate
	 *            削除した勤務データの日付
	 * @param userId
	 *            ユーザID
	 */
	String deleteAtendanceOnHoliday(String strAtendanceDate, String UserID);
	
	/**
	 * 休日勤務データの保存
	 * 
	 * @param atendanceOnHoliday
	 *            入力した勤務情報
	 * @param userId
	 *            ユーザID
	 */
	String saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHoliday, String UserID);

}
