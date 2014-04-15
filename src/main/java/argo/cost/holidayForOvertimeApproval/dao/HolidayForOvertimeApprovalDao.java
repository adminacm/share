package argo.cost.holidayForOvertimeApproval.dao;

import java.text.ParseException;

import argo.cost.holidayForOvertimeApproval.model.HolidayForOvertimeApprovalForm;

public interface HolidayForOvertimeApprovalDao {

	/**
	 * 処理状況値を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況値
	 */
	String getStatus(String applyNo);

	/**
	 * 超勤振替申請承認画面情報取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        超勤振替申請承認画面情報
	 */
	HolidayForOvertimeApprovalForm getHolidayForOvertimeApproval(String applyNo) throws ParseException ;

	/**
	 * 申請状況更新
	 * 
	 * @param applyNo
	 *               申請番号
	 * @param proStatus
	 *                 申請状況
	 * @return
	 *        更新フラグ
	 */
	String updateProStatus(String applyNo, String proStatus);
}
