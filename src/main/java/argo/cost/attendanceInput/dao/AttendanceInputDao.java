package argo.cost.attendanceInput.dao;

import java.util.List;

import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.common.model.ListItem;

public interface AttendanceInputDao {

	/**
	 * 休暇欠勤区分プルダウンリスト取得
	 * 
	 * @return 休暇欠勤区分プルダウンリスト
	 */
	List<ListItem> getHolidayLackingItem();
	
	/**
	 * 個人勤怠プロジェクト情報取得
	 * 
	 * 
	 * @return 個人勤怠プロジェクト情報
	 */
	List<ListItem> getWorkItemList();
	
	/**
	 * ロケーション情報取得
	 * 
	 * 
	 * @return ロケーション情報
	 */
	List<ListItem> getLocationItemList();
	
	/**
	 * 休日勤務情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param yyyymmdd 日付
	 * @return 休日勤務情報
	 */
	HolidayRecord getHolidayRecord(String userId, String yyyymmdd);
}
