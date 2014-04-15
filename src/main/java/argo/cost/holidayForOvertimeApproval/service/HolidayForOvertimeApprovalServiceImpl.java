package argo.cost.holidayForOvertimeApproval.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.ComDao;
import argo.cost.holidayForOvertimeApproval.dao.HolidayForOvertimeApprovalDao;
import argo.cost.holidayForOvertimeApproval.model.HolidayForOvertimeApprovalForm;

@Service
public class HolidayForOvertimeApprovalServiceImpl implements HolidayForOvertimeApprovalService {
	
	/**
	 * 超勤振替申請承認DAO
	 */
	@Autowired
	private HolidayForOvertimeApprovalDao holidayForOvertimeApprovalDao;
	
	/**
	 * 共通DAO
	 */	
	@Autowired
	private ComDao comDao;

	/**
	 * 処理状況名を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況表示名
	 */
	@Override
	public String getStatus(String applyNo) {

		// 申請番号による、処理状況値を取得
		String statusValue = holidayForOvertimeApprovalDao.getStatus(applyNo);
		
		// 処理状況名
		String statusName = comDao.findStatusName(statusValue);
		
		return statusName;
	}

	/**
	 * 超勤振替申請承認画面情報取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        超勤振替申請承認画面情報
	 * @throws ParseException 
	 */
	@Override
	public HolidayForOvertimeApprovalForm getHolidayForOvertimeApproval(
			String applyNo) throws ParseException {
		
		// 超勤振替申請承認画面情報取得
		HolidayForOvertimeApprovalForm holidayForOvertimeApprovalInfo = holidayForOvertimeApprovalDao.getHolidayForOvertimeApproval(applyNo);
		
		// 超勤振替申請承認画面情報戻る
		return holidayForOvertimeApprovalInfo;
	}

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
	@Override
	public String updateProStatus(String applyNo, String proStatus) {

		// 更新実行
		String updateFlg = holidayForOvertimeApprovalDao.updateProStatus(applyNo, proStatus);
		
		// 更新フラグ
		return updateFlg;
	}

}
