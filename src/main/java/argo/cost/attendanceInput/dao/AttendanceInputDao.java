package argo.cost.attendanceInput.dao;

import argo.cost.attendanceInput.model.AttendanceInputForm;

/**
 * 勤怠入力DAO
 * 勤怠入力に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface AttendanceInputDao {
	
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
	/**
	 * 本年度の休暇時間数を取得する
	 * 
	 * @param userId 
	 * 				社員番号
	 * @param yyyymmdd 
	 * 				対象日
	 * @param flag 
	 * 				計算フラグ（０：全ての有給休暇数、1：時間休のみ）
	 * 
	 * @return 対象日までの本年度の休暇時間数
	 */
	Double getSumKyukaTime(String userId, String yyyymmdd, int flag);
}
