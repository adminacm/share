package argo.cost.attendanceOnHoliday.dao;

import java.util.ArrayList;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.model.ListItemVO;

/**
 * 休日勤務入力画面のDAO
 *
 * @author COST argo Corporation.
 */
public interface AtendanceOnHolidayDao {

	/**
	 * 当日休日勤務情報取得
	 * 
	 * @param userId
	 *            当前のユーザID
	 * @param date
	 *            当前の日付
	 * @return 当日休日勤務情報
	 */
	AtendanceOnHolidayForm atendanceOnHolidayDataGet(String userID, String currentDate);

	/**
	 * 勤務日区分リストを取得
	 * 
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
	 * @return 休日勤務データを削除結果           
	 */
	String deleteAtendanceOnHoliday(String strAtendanceDate, String userID);
	
	/**
	 * 休日勤務データの保存
	 * 
	 * @param atendanceOnHoliday
	 *            入力した勤務情報
	 * @param userId
	 *            ユーザID
	 * @return 休日勤務データを保存結果           
	 */
	String saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHoliday, String userID);

}
