package argo.cost.attendanceOnHoliday.service;

import java.text.ParseException;
import java.util.ArrayList;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.model.ListItemVO;

public interface AtendanceOnHolidayService {

	/**
	 * ユーザーがこの日休日勤務データ設定
	 * 
	 * @param form
	 *            休日勤務画面情報
	 * @param date
	 *            勤怠入力画面から渡した休日の日付
	 * 
	 */
	void setAtendanceOnHolidayInfo(AtendanceOnHolidayForm form, String date) throws ParseException;

	// 勤務日区分リストを取得
	ArrayList<ListItemVO> getAtendanceDayKbnList();

	// 休日勤務データ保存
	String saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHoliday,String UserID);
	
	// 休日勤務データ削除
	Integer deleteAtendanceOnHoliday(String strAtendanceDate, String UserID);

}
