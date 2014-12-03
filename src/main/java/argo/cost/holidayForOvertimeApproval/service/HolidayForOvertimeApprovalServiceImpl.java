package argo.cost.holidayForOvertimeApproval.service;

import java.sql.Timestamp;
import java.text.ParseException;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.MCalendar;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.service.ComServiceImpl;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.holidayForOvertimeApproval.model.HolidayForOvertimeApprovalForm;

@Service
public class HolidayForOvertimeApprovalServiceImpl implements HolidayForOvertimeApprovalService {
	/**
	 * 勤怠情報更新履歴番号
	 */
	private int version1 = 0;
	/**
	 * 超勤振替申請情報更新履歴番号
	 */
	private int version2 = 0;
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private ComServiceImpl comServiceImpl;

	/**
	 * 処理状況名を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況表示名
	 */
	@Override
	public String getStatusName(String applyNo) {

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
	public HolidayForOvertimeApprovalForm getHolidayForOvertimeApproval(HolidayForOvertimeApprovalForm holidayForOvertimeApprovalForm,
			String applyNo) throws ParseException {

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// 申請番号
		condition.addConditionEqual("approvalManage2.applyNo", applyNo);
		// 勤怠情報取得
		KintaiInfo kintaiInfo = baseDao.findSingleResult(condition, KintaiInfo.class);

		if (kintaiInfo != null) {
			// 履歴番号
			version1 = kintaiInfo.getVersion();
			// 検索条件
			condition = new BaseCondition();
			// ユーザＩＤ
			condition.addConditionEqual("users.id", kintaiInfo.getUsers().getId());
			// 日付
			condition.addConditionEqual("atendanceDate", kintaiInfo.getAtendanceDate());
			// 休日勤務予定情報を取得
			HolidayAtendanceYotei holidayAtendanceYoteiInfo = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
			
			// 休日名を取得
			MCalendar kyujituInfo = baseDao.findById(kintaiInfo.getAtendanceDate(), MCalendar.class);
			String kyujisuName = "";
			if (kyujituInfo != null) {
				
				if (!kyujituInfo.getKyujisuName().isEmpty()) {
	
					kyujisuName = "祝日（" + kyujituInfo.getKyujisuName() + "）";
				}
			}
		
			// 検索条件
			BaseCondition approvalManagecondition = new BaseCondition();
			// 超勤振替申請番号
			approvalManagecondition.addConditionEqual("applyNo", applyNo);
			// 申請区分＝超勤振替「2」
			approvalManagecondition.addConditionEqual("applyKbnMaster.code", CommonConstant.APPLY_KBN_CHOKIN_FURIKAE);
			
			// 超勤振替申請情報取得
			ApprovalManage approvalManageInfo = baseDao.findSingleResult(approvalManagecondition, ApprovalManage.class);
			
			Users taishoUser = new Users();
			if (approvalManageInfo != null) {
				// 履歴番号
				version2 = approvalManageInfo.getVersion();
				taishoUser = approvalManageInfo.getUser();
			}
			
			// 対象社員番号
			holidayForOvertimeApprovalForm.setTaishoUserId(taishoUser.getId());
			// 対象氏名
			holidayForOvertimeApprovalForm.setTaishoUserName(taishoUser.getUserName());
			// 日付
			holidayForOvertimeApprovalForm.setDate(getShowDate(kintaiInfo.getAtendanceDate()) + kyujisuName);
			// 勤務区分名
			holidayForOvertimeApprovalForm.setWorkKbnName(kintaiInfo.getWorkDayKbnMaster().getName());
			// 勤務開始時間
			holidayForOvertimeApprovalForm.setWorkStartTime(CostDateUtils.formatTime(kintaiInfo.getKinmuStartTime()));
			// 勤務終了時間
			holidayForOvertimeApprovalForm.setWorkEndTime(CostDateUtils.formatTime(kintaiInfo.getKinmuEndTime()));
			// 代休期限
			holidayForOvertimeApprovalForm.setTurnedHolidayEndDate(getShowDate(kintaiInfo.getDaikyuGetShimekiriDate()));
			if (holidayAtendanceYoteiInfo != null) {
				// プロジェクト名
				holidayForOvertimeApprovalForm.setProjectName(holidayAtendanceYoteiInfo.getProjectBasic().getProjectName());
				// 業務内容
				holidayForOvertimeApprovalForm.setWorkDetail(holidayAtendanceYoteiInfo.getCommont());
			}
		}
		
		// 超勤振替申請承認画面情報戻る
		return holidayForOvertimeApprovalForm;
	}

	/**
	 * 承認処理を実行
	 * 
	 * @param applyNo
	 *               申請番号
	 */
	@Override
	public void approvalOverWork(String applyNo) throws OptimisticLockException {

		// 申請番号による、承認情報を取得
		ApprovalManage approvalInfo = baseDao.findById(applyNo, ApprovalManage.class);
		
		// 申請状況に「承認」を設定
		StatusMaster statusMaster = baseDao.findById(CommonConstant.STATUS_SYOUNIN, StatusMaster.class);
		approvalInfo.setStatusMaster(statusMaster);
        // 更新者
		approvalInfo.setUpdatedUserId(approvalInfo.getUser().getId());
		// 更新時刻
		approvalInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		approvalInfo.setVersion(version2);

		// 更新実行
		baseDao.update(approvalInfo);
	}

	/**
	 * 差戻処理実行
	 * 
	 * @param applyNo
	 *               申請番号
	 */
	@Override
	public void remandOverWork(String applyNo) throws OptimisticLockException {
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// 申請番号
		condition.addConditionEqual("approvalManage2.applyNo", applyNo);
		// 勤怠情報取得
		KintaiInfo kintaiInfo = baseDao.findSingleResult(condition, KintaiInfo.class);
		
		// 代休日に空白を設定
		kintaiInfo.setDaikyuDate(null);
		// 超勤振替申請日に空白を設定
		kintaiInfo.setFurikaeShinseiDate(null);
		// 申請番号
		kintaiInfo.setApprovalManage2(null);
        // 更新者
		kintaiInfo.setUpdatedUserId(kintaiInfo.getUsers().getId());
		// 更新時刻
		kintaiInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		kintaiInfo.setVersion(version1);

		// 勤怠情報テーブルを更新
		baseDao.update(kintaiInfo);
		
		// 申請番号による、承認情報を削除
		condition = new BaseCondition();
		condition.addConditionEqual("applyNo", applyNo);
		condition.addConditionEqual("version", version2);
		
		baseDao.deleteByCondition(condition, ApprovalManage.class);
	}

	/**
	 * 日付表示名取得（yyyy年mm月dd日(火)）
	 * 
	 * @param date
	 * @return 日付表示名
	 * @throws ParseException 
	 */
	private String getShowDate(String date) throws ParseException {
		
		String showDate = "";
		String week = "";
		if (date != null) {
			String formatDate = CostDateUtils.formatDate(date, CommonConstant.YYYYMMDD_KANJI);
			// 曜日名
			week = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(date));
			
			showDate = formatDate.concat("（").concat(week).concat("）");
		}

		return showDate;
	}
}
