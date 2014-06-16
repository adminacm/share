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
import argo.cost.common.entity.KyukaKekin;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.entity.YukyuKyukaFuyu;
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

		// 検索条件
		condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("holidaySyukinDate", chokinDate);
		// 休日勤務情報取得
		HolidayAtendance holidayAtendanceInfo = baseDao.findSingleResult(condition, HolidayAtendance.class);
		
		if (holidayAtendanceYoteiInfo != null) {
			// 日付
			form.setDate(getShowDate(holidayAtendanceYoteiInfo.getAtendanceBookDate()));
			// 勤務区分名
			form.setWorkKbnName(holidayAtendanceYoteiInfo.getWorkDayKbnMaster().getName());
			// 勤務開始時間
			form.setWorkStartTime(CostDateUtils.formatTime(holidayAtendanceYoteiInfo.getKinmuStartTime()));
			// 勤務終了時間
			form.setWorkEndTime(CostDateUtils.formatTime(holidayAtendanceYoteiInfo.getKinmuEndTime()));
			// プロジェクト名
			form.setProjectName(holidayAtendanceYoteiInfo.getProjectMaster().getName());
			// 業務内容
			form.setWorkDetail(holidayAtendanceYoteiInfo.getCommont());
		}
		if (holidayAtendanceInfo != null) {
			// 代休期限
			form.setTurnedHolidayEndDate(getShowDate(holidayAtendanceInfo.getDaikyuGetShimekiriDate()));
		}
		
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
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", approvalInfo.getUser().getId());
		// 年度
		condition.addConditionEqual("fuyuYear", approvalInfo.getAppYmd().substring(0, 4));
		// 有給休暇付与情報取得
		YukyuKyukaFuyu yukyuKyukaFuyuInfo = baseDao.findSingleResult(condition, YukyuKyukaFuyu.class);
		
		// 検索条件
		condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", approvalInfo.getUser().getId());
		// 日付
		condition.addConditionEqual("kyukaDate", approvalInfo.getAppYmd());
		// 休暇欠勤区分に「有給休暇」をセット
		String[] vals = {CommonConstant.KK_KBN_ZENKYU, CommonConstant.KK_KBN_HANKYU, CommonConstant.KK_KBN_JIKANKYU};
		condition.addConditionIn("kyukaKekinKbnMaster.code", vals);
		// 有給休暇情報取得
		KyukaKekin kyukaInfo = baseDao.findSingleResult(condition, KyukaKekin.class);
		
		if (yukyuKyukaFuyuInfo != null) {
			
			// 付与日数
			yukyuKyukaFuyuInfo.setHuyuDays(Integer.valueOf(yukyuKyukaFuyuInfo.getHuyuDays()) + 1);
			if (kyukaInfo !=null) {

				// 付与時間数合計
				yukyuKyukaFuyuInfo.setFuyuAllHours(yukyuKyukaFuyuInfo.getFuyuAllHours().add(kyukaInfo.getKyukaJikansu()));
			} else {

				// 付与時間数合計
				yukyuKyukaFuyuInfo.setFuyuAllHours(yukyuKyukaFuyuInfo.getFuyuAllHours());
			}
			
			// 有給休暇付与情報更新実行
			baseDao.update(yukyuKyukaFuyuInfo);
		} else {
			
			yukyuKyukaFuyuInfo = new YukyuKyukaFuyu();
			// ユーザID
			Users users = new Users();
			users.setId(approvalInfo.getUser().getId());
			yukyuKyukaFuyuInfo.setUsers(users );
			// 年度
			yukyuKyukaFuyuInfo.setFuyuYear(approvalInfo.getAppYmd().substring(0, 4));
			// 付与日数
			yukyuKyukaFuyuInfo.setHuyuDays(1);
			if (kyukaInfo !=null) {
				// 付与時間数合計
				yukyuKyukaFuyuInfo.setFuyuAllHours(kyukaInfo.getKyukaJikansu());
			}
			
			// 有給休暇付与情報作成実行
			baseDao.insert(yukyuKyukaFuyuInfo);
		}
	}

	/**
	 * 日付表示名取得（yyyy年mm月dd日(火)）
	 * 
	 * @param date
	 * @return 日付表示名
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
