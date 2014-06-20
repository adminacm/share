package argo.cost.attendanceOnHolidayRecordDetail.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApplyKbnMaster;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.utils.CostDateUtils;

/**
 * <p>
 * 休日出勤管理詳細画面サービス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Service
public class AttendanceOnHolidayRecordDetailServiceImpl implements AttendanceOnHolidayRecordDetailService {
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 休日出勤管理詳細画面情報を取得
	 * 
	 * @param userId
	 * 	                           ユーザＩＤ
	 * @param date
	 *            日付
	 * @param workKbnO
	 *               勤務区分
	 * @return 休日出勤管理詳細画面情報
	 * @throws ParseException 
	 */
	@Override
	public AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(String userId, String date, String workKbn) throws ParseException {

		// 休日出勤管理詳細情報
		AttendanceOnHolidayRecordDetailForm form = new AttendanceOnHolidayRecordDetailForm();
		
		// 休日出勤管理詳細情報を取得
		if (CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE.equals(workKbn)) {
			
			// 勤務区分が「休日振替勤務」の詳細情報を取得
			form = getFormDetail(userId, date, CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE);
		} else {
			
			// 勤務区分が「休日勤務」の詳細情報を取得
			form = getFormDetail(userId, date, CommonConstant.WORKDAY_KBN_KYUJITU);
		}
		
		// 休日出勤管理詳細画面情報を戻り
		return form;
	}

	/**
	 * 超勤振替申請を提出
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 * @throws ParseException 
	 *            
	 */
	@Override
	public void overWorkPayRequest(AttendanceOnHolidayRecordDetailForm form) throws ParseException {

		// 勤怠情報
		KintaiInfo detailInfo = new KintaiInfo();
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", form.getUserId());
		// 日付
		condition.addConditionEqual("atendanceDate", form.getHolidayWorkDate().replaceAll("/", ""));
		// 勤怠情報を取得
		detailInfo = baseDao.findSingleResult(condition, KintaiInfo.class);
		
		// 代休日
		detailInfo.setDaikyuDate("超勤振替");
		// 振替申請日がシステム日付になる
		detailInfo.setFurikaeShinseiDate(CostDateUtils.getNowDate());
		
		// 休日出勤テーブルを更新
		baseDao.update(detailInfo);
		
		// 承認管理データを作成
		ApprovalManage applyInfo = new ApprovalManage();
		// 申請番号
		applyInfo.setApplyNo(form.getUserId() + CommonConstant.APPLY_KBN_CHOKIN_FURIKAE + form.getHolidayWorkDate());
		// 申請区分
		ApplyKbnMaster applyKbnMaster = new ApplyKbnMaster();
		applyKbnMaster.setCode(CommonConstant.APPLY_KBN_CHOKIN_FURIKAE);
		applyInfo.setApplyKbnMaster(applyKbnMaster );
		// 申請内容
		applyInfo.setApplyDetail("休日勤務日：" + form.getHolidayWorkDate());
		// 状況
		StatusMaster statusMaster = new StatusMaster();
		statusMaster.setCode(CommonConstant.STATUS_TEISYUTU);
		applyInfo.setStatusMaster(statusMaster);
		// 氏名
		Users users = new Users();
		users.setId(form.getUserId());
		applyInfo.setUser(users);
		// 申請時間
		applyInfo.setAppYmd(CostDateUtils.getNowDate());
		// 休日勤務日
		applyInfo.setItemDate(form.getHolidayWorkDate().replaceAll("/", ""));
		
		// 承認管理データを作成
		baseDao.insert(applyInfo);
	}

	/**
	 * 休日出勤管理の詳細情報を取得
	 * 
	 * @param userId
	 * 	                           社員番号
	 * @param date
	 *            日付
	 * @param workDayKbn
	 *                  勤務日区分
	 * @return 休日出勤管理詳細情報
	 * @throws ParseException 
	 */
	private AttendanceOnHolidayRecordDetailForm getFormDetail(
			String userId, String date, String workDayKbn) throws ParseException {

		// 休日出勤管理詳細画面の情報
		AttendanceOnHolidayRecordDetailForm detailForm = new AttendanceOnHolidayRecordDetailForm();
		//　ユーザID
		detailForm.setUserId(userId);
				
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionEqual("atendanceDate", date.replaceAll("/", ""));
		// 勤務日区分「休日」をセット
		condition.addConditionEqual("workDayKbnMaster.code", workDayKbn);
		// 勤務開始時間
		condition.addConditionIsNotNull("kinmuStartTime");
		// 勤怠情報を取得
		KintaiInfo kintaiInfo = baseDao.findSingleResult(condition, KintaiInfo.class);
		
		// 検索条件
		condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionEqual("atendanceDate", date.replaceAll("/", ""));
		// 休日勤務予定情報を取得
		HolidayAtendanceYotei holidayAtendanceYoteiInfo = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);

		// 勤怠情報がありの場合
		if (kintaiInfo != null) {
			//　日付
			detailForm.setHolidayWorkDate(CostDateUtils.formatDate(kintaiInfo.getAtendanceDate(), CommonConstant.YYYY_MM_DD));
			// 勤務区分名
			detailForm.setWorkKbnName(kintaiInfo.getWorkDayKbnMaster().getName());
			// 勤務開始時間
			detailForm.setWorkStartTime(CostDateUtils.formatTime(kintaiInfo.getKinmuStartTime()));
			// 勤務終了時間
			detailForm.setWorkEndTime(CostDateUtils.formatTime(kintaiInfo.getKinmuEndTime()));
			// 振替日
			detailForm.setExchangeDate(CostDateUtils.formatDate(kintaiInfo.getFurikaeDate(), CommonConstant.YYYY_MM_DD));
			// 代休期限
			detailForm.setTurnedHolidayEndDate(CostDateUtils.formatDate(kintaiInfo.getDaikyuGetShimekiriDate(), CommonConstant.YYYY_MM_DD));
			// 代休日
			if (CostDateUtils.isValidDate(kintaiInfo.getDaikyuDate(), CommonConstant.YYYYMMDD)) {
				detailForm.setTurnedHolidayDate(CostDateUtils.formatDate(kintaiInfo.getDaikyuDate(), CommonConstant.YYYY_MM_DD));
			}
			// 超勤振替申請日
			detailForm.setOverWorkTurnedReqDate(CostDateUtils.formatDate(kintaiInfo.getFurikaeShinseiDate(), CommonConstant.YYYY_MM_DD));
		}
		// 休日勤務予定情報がありの場合
		if (holidayAtendanceYoteiInfo != null) {
			// プロジェクト名
 			detailForm.setProjectName(holidayAtendanceYoteiInfo.getProjectMaster().getName());
			// 業務内容
			detailForm.setWorkDetail(holidayAtendanceYoteiInfo.getCommont());
		}
		// 代休未取得
		if (!CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE.equals(workDayKbn) && detailForm.getTurnedHolidayDate() == null && detailForm.getOverWorkTurnedReqDate().isEmpty()) {
			
			// 超勤振替フラグ
			detailForm.setOverWorkFlg(true);
		}

		// 画面情報を戻り
		return detailForm;
	}
}
