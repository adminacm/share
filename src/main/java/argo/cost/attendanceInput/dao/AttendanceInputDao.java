package argo.cost.attendanceInput.dao;

import java.text.ParseException;
import java.util.List;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProjectVO;
import argo.cost.attendanceInput.model.HolidayRecord;

/**
 * 勤怠入力DAO
 * 勤怠入力に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface AttendanceInputDao {

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
	List<AttendanceProjectVO> getProjectList(String userId, String yyyymmdd) throws ParseException;
	
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
	 * シフトテーブルから作業時間数を取得する
	 * 
	 * @param shiftCode 
	 * 				シフトコード
	 * @param sTime 
	 * 				作業開始時刻
	 * @param eTime 
	 * 				作業終了時刻
	 * @param flag 
	 * 				計算フラグ(0:Between；1:以上;2:以下)
	 * 
	 * @return 作業時間数
	 */
	Double countWorkTime(String shiftCode, String sTime, String eTime, int flag);
}
