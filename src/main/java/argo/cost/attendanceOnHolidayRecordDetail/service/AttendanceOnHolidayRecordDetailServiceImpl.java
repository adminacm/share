package argo.cost.attendanceOnHolidayRecordDetail.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecordDetail.dao.AttendanceOnHolidayRecordDetailDao;
import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApplyKbnMaster;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;
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
	 * 休日出勤管理Dao
	 */
	@Autowired
	private AttendanceOnHolidayRecordDetailDao detailDao;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 *　状況：提出「01」
	 */
	public static final String STATUS_TEISYUTU = "01";

	/**
	 * 休日出勤管理詳細画面情報を取得
	 * 
	 * @param userId
	 * 	                           ユーザＩＤ
	 * @param date
	 *            日付
	 * @param workKbn
	 *               勤務区分
	 * @return 休日出勤管理詳細画面情報
	 * @throws ParseException 
	 */
	@Override
	public AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(String userId, String date, String workKbn) throws ParseException {

		// 休日出勤管理詳細情報
		AttendanceOnHolidayRecordDetailForm detailInfo = new AttendanceOnHolidayRecordDetailForm();
		
		// 休日出勤管理詳細情報を取得
		if (CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE.equals(workKbn)) {
			
			// 勤務区分が「休日振替勤務」の詳細情報を取得
			detailInfo = getFurikaeDetail(userId, date);
		} else {
			
			// 勤務区分が「休日勤務」の詳細情報を取得
			detailInfo = detailDao.getWorkDetail(userId, date);
		}
		
		return detailInfo;
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

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", form.getUserId());
		// 日付
		condition.addConditionEqual("holidaySyukinDate", form.getDate().replaceAll("/", ""));
		// 休日出勤管理詳細情報を取得
		HolidayAtendance detailInfo = baseDao.findSingleResult(condition, HolidayAtendance.class);
		
		// システム日付
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String systemDate = format.format(new Date());
		
		// 代休日
		detailInfo.setDaikyuDate("超勤振替");
		// 振替申請日がシステム日付になる
		detailInfo.setFirikaeShiseiDate(systemDate);
		
		// 休日出勤テーブルを更新
		baseDao.update(detailInfo);
		
		// 承認管理データを作成
		ApprovalManage applyInfo = new ApprovalManage();
		// 申請番号
		applyInfo.setApplyNo(form.getUserId() + CommonConstant.APPLY_KBN_CHOKIN_FURIKAE + systemDate);
		// 申請区分
		ApplyKbnMaster applyKbnMaster = new ApplyKbnMaster();
		applyKbnMaster.setCode(CommonConstant.APPLY_KBN_CHOKIN_FURIKAE);
		applyInfo.setApplyKbnMaster(applyKbnMaster );
		// 申請内容
		applyInfo.setApplyDetail("休日勤務日：" + CostDateUtils.formatDate(form.getDate(), CommonConstant.YYYY_MM_DD));
		// 状況
		StatusMaster statusMaster = new StatusMaster();
		statusMaster.setCode(STATUS_TEISYUTU);
		applyInfo.setStatusMaster(statusMaster);
		// 氏名
		Users users = new Users();
		users.setId(form.getUserId());
		applyInfo.setUser(users);
		// 申請時間
		applyInfo.setAppYmd(form.getDate().replaceAll("/", ""));
		
		// 休日出勤テーブルを更新
		baseDao.insert(applyInfo);
	}

	/**
	 * 勤務区分が「休日振替勤務」の詳細情報を取得
	 * 
	 * @param userId
	 * 	                           社員番号
	 * @param date
	 *            日付
	 * @return 休日出勤管理詳細情報
	 * @throws ParseException 
	 */
	private AttendanceOnHolidayRecordDetailForm getFurikaeDetail(String userId,
			String date) throws ParseException {
		
		// 画面情報
		AttendanceOnHolidayRecordDetailForm form = new AttendanceOnHolidayRecordDetailForm();

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("atendanceBookDate", date.replaceAll("/", ""));

		HolidayAtendanceYotei holidayAtendanceInfo = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);

		if (holidayAtendanceInfo != null) {
			//　日付
			form.setDate(CostDateUtils.formatDate(holidayAtendanceInfo.getAtendanceBookDate(), CommonConstant.YYYY_MM_DD));
			// 勤務区分名
			form.setWorkKbnName(holidayAtendanceInfo.getWorkDayKbnMaster().getName());
			// 勤務開始時間
			form.setWorkStartTime(CostDateUtils.formatTime(holidayAtendanceInfo.getKinmuStartTime()));
			// 勤務終了時間
			form.setWorkEndTime(CostDateUtils.formatTime(holidayAtendanceInfo.getKinmuEndTime()));
			// 振替日
			form.setExchangeDate(CostDateUtils.formatDate(holidayAtendanceInfo.getFurikaeDate(), CommonConstant.YYYY_MM_DD));
			// プロジェクト名
			form.setProjectName(holidayAtendanceInfo.getProjectMaster().getName());
			// 業務内容
			form.setWorkDetail(holidayAtendanceInfo.getCommont());
		}
		return form;
	}
}
