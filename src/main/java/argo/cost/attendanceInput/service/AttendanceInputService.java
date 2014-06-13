package argo.cost.attendanceInput.service;

import java.text.ParseException;

import argo.cost.attendanceInput.model.AttendanceInputForm;

/**
 * 勤怠入力サービスクラス
 * 勤怠入力に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface AttendanceInputService {
	
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
	 * 勤怠入力画面情報設定
	 * 
	 * @param form 
	 * 				画面情報
	 * @param newDate 
	 * 				日付
	 */
	void setAttForm(AttendanceInputForm form, String newDate) throws ParseException;
	
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
