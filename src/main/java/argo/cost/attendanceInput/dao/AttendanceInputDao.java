package argo.cost.attendanceInput.dao;

import java.text.ParseException;
import java.util.List;

import argo.cost.attendanceInput.model.AttendanceProject;
import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.attendanceInput.model.WorkTimeDetail;
import argo.cost.common.model.ListItemVO;

public interface AttendanceInputDao {

	/**
	 * 休暇欠勤区分プルダウンリスト取得
	 * 
	 * @return 休暇欠勤区分プルダウンリスト
	 */
	List<ListItemVO> getHolidayLackingItem();
	
	/**
	 * 個人勤怠プロジェクト情報取得
	 * 
	 * 
	 * @return 個人勤怠プロジェクト情報
	 */
	List<ListItemVO> getWorkItemList();
	
	/**
	 * ロケーション情報取得
	 * 
	 * 
	 * @return ロケーション情報
	 */
	List<ListItemVO> getLocationItemList();
	
	/**
	 * 休日勤務情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param yyyymmdd 日付
	 * @return 休日勤務情報
	 */
	HolidayRecord getHolidayRecord(String userId, String yyyymmdd);
	
	/**
	 * 就業データを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param yyyymmdd
	 *            日付
	 * @return 就業データ
	 */
	WorkTimeDetail getWorkTimeDetail(String userId, String yyyymmdd);
	
	/**
	 * ユーザ作業情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param yyyymmdd 日付
	 * @return ユーザ作業情報
	 */
	List<AttendanceProject> getProjectList(String userId, String yyyymmdd) throws ParseException;
	
}
