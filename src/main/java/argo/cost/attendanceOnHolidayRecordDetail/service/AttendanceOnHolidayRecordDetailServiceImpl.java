package argo.cost.attendanceOnHolidayRecordDetail.service;

import java.sql.Timestamp;
import java.text.ParseException;

import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.StringUtils;
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
	 * 更新履歴番号
	 */
	private int version = 0;
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
	public void getAttendanceOnHolidayRecordDetail(
								AttendanceOnHolidayRecordDetailForm form, 
								String date,
								String workKbn) throws ParseException {

		// 対象者ID
		String taishoId = form.getTaishoUserId();
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", taishoId);
		// 日付
		condition.addConditionEqual("atendanceDate", date.replaceAll("/", ""));
		// 勤務日区分「休日」をセット
		condition.addConditionEqual("workDayKbnMaster.code", workKbn);
		// 勤務開始時間
		condition.addConditionIsNotNull("kinmuStartTime");
		// 勤怠情報を取得
		KintaiInfo kintaiInfo = baseDao.findSingleResult(condition, KintaiInfo.class);
		
		// 検索条件
		condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", taishoId);
		// 日付
		condition.addConditionEqual("atendanceDate", date.replaceAll("/", ""));
		// 休日勤務予定情報を取得
		HolidayAtendanceYotei holidayAtendanceYoteiInfo = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);

		// 勤怠情報がありの場合
		if (kintaiInfo != null) {
			//　日付
			form.setHolidayWorkDate(CostDateUtils.formatDate(kintaiInfo.getAtendanceDate(), CommonConstant.YYYY_MM_DD));
			// 勤務区分名
			form.setWorkKbn(kintaiInfo.getWorkDayKbnMaster().getCode());
			// 勤務区分名
			form.setWorkKbnName(kintaiInfo.getWorkDayKbnMaster().getName());
			// 勤務開始時間
			form.setWorkStartTime(CostDateUtils.formatTime(kintaiInfo.getKinmuStartTime()));
			// 勤務終了時間
			form.setWorkEndTime(CostDateUtils.formatTime(kintaiInfo.getKinmuEndTime()));
			// 振替日
			form.setExchangeDate(CostDateUtils.formatDate(kintaiInfo.getFurikaeDate(), CommonConstant.YYYY_MM_DD));
			// 代休期限
			form.setTurnedHolidayEndDate(CostDateUtils.formatDate(kintaiInfo.getDaikyuGetShimekiriDate(), CommonConstant.YYYY_MM_DD));
			// 代休日
			if (CostDateUtils.isValidDate(kintaiInfo.getDaikyuDate(), CommonConstant.YYYYMMDD)) {
				form.setTurnedHolidayDate(CostDateUtils.formatDate(kintaiInfo.getDaikyuDate(), CommonConstant.YYYY_MM_DD));
			}
			// 超勤振替申請日
			form.setOverWorkTurnedReqDate(CostDateUtils.formatDate(kintaiInfo.getFurikaeShinseiDate(), CommonConstant.YYYY_MM_DD));
		}
		// 休日勤務予定情報がありの場合
		if (holidayAtendanceYoteiInfo != null) {
			version = holidayAtendanceYoteiInfo.getVersion();
			// プロジェクト名
			form.setProjectName(holidayAtendanceYoteiInfo.getProjectBasic().getProjectName());
			// 業務内容
			form.setWorkDetail(holidayAtendanceYoteiInfo.getCommont());
		}
		// 代休未取得
		if (!CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE.equals(workKbn) 
				&& (StringUtils.isEmpty(form.getTurnedHolidayDate())
						 && StringUtils.isEmpty(form.getOverWorkTurnedReqDate()))) {
			
			// 超勤振替フラグ
			form.setOverWorkFlg(true);
		}

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
	public void overWorkPayRequest(AttendanceOnHolidayRecordDetailForm form) throws ParseException, OptimisticLockException {

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", form.getTaishoUserId());
		// 日付
		condition.addConditionEqual("atendanceDate", form.getHolidayWorkDate().replaceAll("/", ""));
		// 勤怠情報を取得
		KintaiInfo detailInfo = baseDao.findSingleResult(condition, KintaiInfo.class);
		
		// 承認管理データを作成
		ApprovalManage applyInfo = new ApprovalManage();
		// 申請番号
		applyInfo.setApplyNo(form.getUserId() + CommonConstant.APPLY_KBN_CHOKIN_FURIKAE + form.getHolidayWorkDate().replaceAll("/", ""));
		// 申請区分
		ApplyKbnMaster applyKbnMaster = baseDao.findById(CommonConstant.APPLY_KBN_CHOKIN_FURIKAE, ApplyKbnMaster.class);
		applyInfo.setApplyKbnMaster(applyKbnMaster);
		// 申請内容
		applyInfo.setApplyDetail("休日勤務日：" + form.getHolidayWorkDate());
		// 状況
		StatusMaster statusMaster = baseDao.findById(CommonConstant.STATUS_TEISYUTU, StatusMaster.class);
		applyInfo.setStatusMaster(statusMaster);
		// 氏名
		Users users = baseDao.findById(form.getTaishoUserId(), Users.class);
		applyInfo.setUser(users);
		// 申請時間
		applyInfo.setApplyYmd(CostDateUtils.getNowDate());
		// 処理日
		applyInfo.setSyoriYm(CostDateUtils.getDealDate(CostDateUtils.getNowDate(),CommonConstant.APPLY_KBN_CHOKIN_FURIKAE));
		
		applyInfo.setCreatedUserId(form.getUserId());               // 登録者
		applyInfo.setCreatedDate(new Timestamp(System.currentTimeMillis())); // 登録時刻
		applyInfo.setUpdatedUserId(form.getUserId());               // 更新者
		applyInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));  // 更新時刻
		// 承認管理データを作成
		baseDao.insert(applyInfo);
		
		// 代休日
		detailInfo.setDaikyuDate("超勤振替");
		// 振替申請日がシステム日付になる
		detailInfo.setFurikaeShinseiDate(CostDateUtils.getNowDate());
		ApprovalManage approvalManage2 = new ApprovalManage();
		approvalManage2.setApplyNo(form.getUserId() + CommonConstant.APPLY_KBN_CHOKIN_FURIKAE + form.getHolidayWorkDate().replaceAll("/", ""));
		// 申請番号
		detailInfo.setApprovalManage2(approvalManage2);
		detailInfo.setUpdatedUserId(form.getUserId());               // 更新者
		detailInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));  // 更新時刻
		detailInfo.setVersion(version);
		// 休日出勤テーブルを更新
		baseDao.update(detailInfo);
	}
}
