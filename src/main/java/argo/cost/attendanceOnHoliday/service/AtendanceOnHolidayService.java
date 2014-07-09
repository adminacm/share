package argo.cost.attendanceOnHoliday.service;

import java.text.ParseException;
import java.util.ArrayList;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.entity.WorkDayKbnMaster;

/**
 * 休日勤務入力画面サービスのインタフェース
 *
 * @author COST argo Corporation.
 */
public interface AtendanceOnHolidayService {

	/**
	 * ユーザーが当日の休日勤務データ設定
	 * 
	 * @param form
	 *            休日勤務画面情報
	 * @param currentDate
	 *            勤怠入力画面から渡した休日の日付
	 * 
	 */
	void setAtendanceOnHolidayInfo(AtendanceOnHolidayForm atendanceOnHolidayForm, String currentDate) throws ParseException;

	/**
	 * 勤務日区分リストを取得
	 * 
	 * @return　　勤務区分リスト
	 *            
	 */
	ArrayList<WorkDayKbnMaster> getAtendanceDayKbnList();
	
	/**
	 * 休日勤務データ保存
	 * 
	 * @param atendanceOnHoliday
	 *            休日勤務画面情報
	 * @param UserID
	 *            ユーザーID
	 * 
	 * @return 休日勤務データ保存結果
	 * 
	 * @ throws Exception
	 */
	void saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHolidayForm) throws Exception;
	
	/**
	 * 休日勤務データ削除
	 * 
	 * @param strAtendanceDate
	 *            休日勤務の日付
	 * @param UserID
	 *            ユーザーID
	 * 
	 * @return 休日勤務データ削除結果
	 */
	void deleteAtendanceOnHoliday(String strAtendanceDate, String UserID) throws Exception;

}
