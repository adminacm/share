package argo.cost.holidayForOvertimeApproval.dao;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.ComDao;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.holidayForOvertimeApproval.model.HolidayForOvertimeApprovalForm;

@Repository
public class HolidayForOvertimeApprovalDaoImpl implements HolidayForOvertimeApprovalDao {

	/**
	 * 共通DAO
	 */
	@Autowired
	private ComDao comDao;

	/**
	 * 処理状況値を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況値
	 */
	@Override
	public String getStatus(String applyNo) {
		// TODO ＤＢから、申請番号による、処理状况値を取得
		return "02";
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
		// TODO 自動生成されたメソッド・スタブ
		// 日付
		String date = "20140506";
		// 勤務区分コード
		String workKbn = "01";
		// 代休期限
		String turnedHolidayEndDate = "20140731";
		
		HolidayForOvertimeApprovalForm holidayForOvertimeApprovalInfo = new HolidayForOvertimeApprovalForm();
		//TODO 日付表示名
		holidayForOvertimeApprovalInfo.setDate(getShowDate(date));
		// 勤務区分名
		holidayForOvertimeApprovalInfo.setWorkKbnName(comDao.findWorkKbnName(workKbn));
		// 勤務開始時間
		holidayForOvertimeApprovalInfo.setWorkStartTime("10:00");
		// 勤務終了時間
		holidayForOvertimeApprovalInfo.setWorkEndTime("19:00");
		// 代休期限
		holidayForOvertimeApprovalInfo.setTurnedHolidayEndDate(getShowDate(turnedHolidayEndDate));
		// プロジェクト名
		holidayForOvertimeApprovalInfo.setProjectName("プロジェクト名");
		// 業務内容
		holidayForOvertimeApprovalInfo.setWorkDetail("システム復旧作業のため");
		
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
		// TODO 自動生成されたメソッド・スタブ
		return "1";
	}

	/**
	 * 日付表示名取得（yyyy年mm月dd日(火)）
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	private String getShowDate(String date) throws ParseException {
		
		String showDate = CostDateUtils.formatDate(date,
				CommonConstant.YYYYMMDD_KANJI);
		// 曜日名
		String week = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(date));
		
		return showDate.concat("（").concat(week).concat("）");
		
	}
}
