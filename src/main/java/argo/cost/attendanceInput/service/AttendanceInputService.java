package argo.cost.attendanceInput.service;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.common.exception.BusinessException;

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
	 * @throws Exception 
	 */
	String changeDate(String changeFlg, String yyyymmdd) throws Exception;

	/**
	 * 勤怠入力画面情報設定
	 * 
	 * @param form 
	 * 				画面情報
	 * @param newDate 
	 * 				日付
	 *  @throws Exception 
	 */
	void setAttForm(AttendanceInputForm form, String newDate) throws Exception;
	
	/**
	 * 就業データを取得
	 * 
	 * @param form 
	 * 				画面情報
	 *  @throws Exception 
	 */
	void updateAttdendanceInfo(AttendanceInputForm form) throws BusinessException;
	
	/**
	 * 各種値算出
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 *  @throws Exception 
	 */
	void calcWorkingRec(AttendanceInputForm form) throws BusinessException;
	
}
