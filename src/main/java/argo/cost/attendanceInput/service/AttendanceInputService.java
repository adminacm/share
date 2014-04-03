package argo.cost.attendanceInput.service;

import java.text.ParseException;
import java.util.List;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProject;
import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.attendanceInput.model.WorkTimeDetail;
import argo.cost.common.model.ListItemVO;

public interface AttendanceInputService {
	
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
	 * @return ロケーション情報
	 */
	List<ListItemVO> getLocationItemList();
	
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
	
	/**
	 * ユーザ作業情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param yyyymmdd 日付
	 * @return ユーザ作業情報
	 */
	List<AttendanceProject> getProjectList(String userId, String yyyymmdd) throws ParseException;
	
	/**
	 * 就業データを取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param yyyymmdd 日付
	 * @return 就業データ
	 */
	WorkTimeDetail getWorkTimeDetail(String userId, String yyyymmdd);
	
	/**
	 * 勤怠入力画面情報設定
	 * 
	 * @param form 
	 * 				画面情報
	 * @param newDate 
	 * 				日付
	 * @param userId
	 * 			ユーザID
	 */
	void setAttForm(AttendanceInputForm form, String newDate, String userId) throws ParseException;
	
	/**
	 * 就業データを取得
	 * 
	 * @param form 
	 * 				画面情報
	 * 
	 * @return 更新結果　０：更新失敗　１：更新成功
	 */
	Integer updateAttdendanceInfo(AttendanceInputForm form);
	/**
	 * 各種値算出
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @throws ParseException
	 */
	void calcWorkingRec(AttendanceInputForm form) throws ParseException;
}
