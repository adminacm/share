package argo.cost.holidayForOvertimeApproval.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.holidayForOvertimeApproval.model.HolidayForOvertimeApprovalForm;

@Service
public class HolidayForOvertimeApprovalServiceImpl implements HolidayForOvertimeApprovalService {
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

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

		// 申請番号による、承認情報を取得
		ApprovalManage approvalInfo = baseDao.findById(applyNo, ApprovalManage.class);
		
		// 処理状況名
		String statusName = approvalInfo.getStatusMaster().getName();
		
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

		// 超勤振替申請承認画面情報
		HolidayForOvertimeApprovalForm form = new HolidayForOvertimeApprovalForm();
		
		// 申請番号による、承認情報を取得
		ApprovalManage approvalInfo = baseDao.findById(applyNo, ApprovalManage.class);
		
		// ユーザID
		String userId = approvalInfo.getUser().getId();
		// 超勤振替申請時間
		String chokinDate = approvalInfo.getAppYmd();
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("atendanceBookDate", chokinDate);
		
		// 休日振替勤務情報取得
		HolidayAtendanceYotei holidayAtendanceYoteiInfo = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
		HolidayAtendance holidayAtendanceInfo = baseDao.findSingleResult(condition, HolidayAtendance.class);
		
		// 日付
		form.setDate(getShowDate(holidayAtendanceInfo.getHolidaySyukinDate()));
		// 勤務区分名
		form.setWorkKbnName(holidayAtendanceYoteiInfo.getWorkDayKbnMaster().getName());
		// 勤務開始時間
		form.setWorkStartTime(CostDateUtils.formatTime(holidayAtendanceYoteiInfo.getKinmuStartTime()));
		// 勤務終了時間
		form.setWorkEndTime(CostDateUtils.formatTime(holidayAtendanceYoteiInfo.getKinmuEndTime()));
		// 代休期限
		form.setTurnedHolidayEndDate(getShowDate(holidayAtendanceInfo.getDaikyuGetShimekiriDate()));
		// プロジェクト名
		form.setProjectName(holidayAtendanceYoteiInfo.getProjectMaster().getName());
		// 業務内容
		form.setWorkDetail(holidayAtendanceYoteiInfo.getCommont());
		
		// 超勤振替申請承認画面情報戻る
		return form;
	}

	/**
	 * 申請状況更新
	 * 
	 * @param applyNo
	 *               申請番号
	 * @param proStatus
	 *                 申請状況
	 */
	@Override
	public void updateProStatus(String applyNo, String proStatus) {

		// 申請番号による、承認情報を取得
		ApprovalManage approvalInfo = baseDao.findById(applyNo, ApprovalManage.class);
		
		// 申請状況を設定
		StatusMaster statusMaster = new StatusMaster();
		statusMaster.setCode(proStatus);
		approvalInfo.setStatusMaster(statusMaster);

		// 更新実行
		baseDao.update(approvalInfo);
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
