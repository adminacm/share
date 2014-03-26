package argo.cost.attendanceOnHoliday.service;

import java.util.ArrayList;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHoliday;
import argo.cost.attendanceOnHoliday.model.CodeNameMap;

public interface AtendanceOnHolidayService {

	// ユーザーがこの日休日勤務データ有無
	public boolean atendanceOnHolidayDataChk(String userId, String date);

	// ユーザーがこの日休日勤務データ有無
	public AtendanceOnHoliday atendanceOnHolidayDataGet(String userId,
			String date);

	// 勤務日区分リストを取得
	public ArrayList<CodeNameMap> atendanceDayKbnList();

	// プロジェクトリストを取得
	public ArrayList<CodeNameMap> projectKbnList();

	// 休日勤務データ保存
	public String saveAtendanceOnHoliday(AtendanceOnHoliday atendanceOnHoliday,String UserID);
	
	// 休日勤務データ削除
	public String deleteAtendanceOnHoliday(String strAtendanceDate, String UserID);

}
