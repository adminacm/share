package argo.cost.holidayForOvertimeApproval.service;

import java.text.ParseException;

import javax.persistence.OptimisticLockException;

import argo.cost.holidayForOvertimeApproval.model.HolidayForOvertimeApprovalForm;



public interface HolidayForOvertimeApprovalService {
	
	/**
	 * 処理状況を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況表示名
	 */
	String getStatusName(String applyNo);

	/**
	 * 超勤振替申請承認画面情報取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        超勤振替申請承認画面情報
	 */
	HolidayForOvertimeApprovalForm getHolidayForOvertimeApproval(HolidayForOvertimeApprovalForm holidayForOvertimeApprovalForm, String applyNo) throws ParseException ;

	/**
	 * 承認処理を実行
	 * 
	 * @param applyNo
	 *               申請番号
	 */
	void approvalOverWork(String applyNo) throws OptimisticLockException;

	/**
	 * 差戻処理実行
	 * 
	 * @param applyNo
	 *               申請番号
	 */
	void remandOverWork(String applyNo);
}
