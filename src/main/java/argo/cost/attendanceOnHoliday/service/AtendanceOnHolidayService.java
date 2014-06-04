package argo.cost.attendanceOnHoliday.service;

import java.text.ParseException;
import java.util.ArrayList;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.WorkDayKbnMaster;
import argo.cost.common.model.ListItemVO;

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
	 * プロジェクト名プルダウンリストを取得
	 * 
	 * @return　　プロジェクトリスト
	 *            
	 */
	ArrayList<ProjWorkMaster> getProjectWorkKbnList();

	/**
	 * 休日勤務データ保存
	 * 
	 * @param atendanceOnHoliday
	 *            休日勤務画面情報
	 * @param UserID
	 *            ユーザーID
	 * 
	 * @return 休日勤務データ保存結果
	 */
	String saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHolidayForm,String UserID);
	
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
	Integer deleteAtendanceOnHoliday(String strAtendanceDate, String UserID);

}
