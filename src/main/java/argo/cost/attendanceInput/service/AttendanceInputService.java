package argo.cost.attendanceInput.service;

import java.text.ParseException;
import java.util.List;

import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.common.model.ListItem;

public interface AttendanceInputService {
	
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
	 * @return ロケーション情報
	 */
	List<ListItem> getLocationItemList();
	
	/**
	 * 
	 * 日付の変換処理
	 * 
	 * @param changeFlg 変換フラグ
	 * @param date 日付
	 * @return 年月
	 * @throws ParseException 
	 */
	String changeDate(String changeFlg, String yyyymmdd) throws ParseException;
	
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
