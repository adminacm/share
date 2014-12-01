package argo.cost.monthlyReportApproval.service;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.ProjWorkTimeManage;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.exception.BusinessException;
import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.common.model.ProjWorkTimeCountVO;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.common.utils.CostStringUtils;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalForm;

@Service
public class MonthlyReportApprovalServiceImpl implements MonthlyReportApprovalService {
	/**
	 * 更新履歴番号
	 */
	private int version = 0;
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;

	/**
	 * 処理状況コードを取得
	 * 
	 * @param form
	 *               画面情報
	 * @return
	 *        処理状況表示名
	 */
	@Override
	public void getStatusCode(MonthlyReportApprovalForm form) {

		// 申請番号によって、処理状况値を取得
		String applyNo = form.getApplyNo();
				
		ApprovalManage approvalManageInfo = baseDao.findById(applyNo, ApprovalManage.class);
		version = approvalManageInfo.getVersion();
		// 処理状況コード取得
		form.setProStatus(approvalManageInfo.getStatusMaster().getCode());
		form.setProStatusName(approvalManageInfo.getStatusMaster().getName());
		
	}

	/**
	 * 月報承認データを取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        月報承認データ一覧リスト
	 * @throws ParseException 
	 */
	@Override
	public void getMonReApprovalList(MonthlyReportApprovalForm form, String applyNo) throws ParseException {
		
		// 月報データを取得
		BaseCondition condition = new BaseCondition();
		condition.addConditionEqual("approvalManage1.applyNo", applyNo);
		condition.addOrderAsc("atendanceDate");
		
		List<KintaiInfo> kintaiInfoList = baseDao.findResultList(condition, KintaiInfo.class);
		String strLatestShinseiDate = kintaiInfoList.get(0).getAtendanceDate();
		
		// 承認情報を取得
		ApprovalManage approval = baseDao.findById(applyNo, ApprovalManage.class); 
		form.setTaishoUserId(approval.getUser().getId());
		form.setTaishoUserName(approval.getUser().getUserName());
		
		// 月報勤務年月
		form.setReportMoth(this.getReportMonth(strLatestShinseiDate));
		// 月報リストを作成
		List<MonthlyReportDispVO> monthlyReportApprovalList = getMonReList(CostDateUtils.toDate(strLatestShinseiDate));
		// 月報情報
		MonthlyReportDispVO monthInfo = null;
		// 勤怠情報
		KintaiInfo kintaiInfo = null;
		for(int j = 0 ;j < monthlyReportApprovalList.size();j++) {
			// 月報情報
			monthInfo = monthlyReportApprovalList.get(j);
			for (int i = 0;i < kintaiInfoList.size(); i++) {

				// 勤怠情報
				kintaiInfo = kintaiInfoList.get(i);
				
				// 対応した日付で、勤務情報を設定する
				if (StringUtils.equals(monthInfo.getDate(), kintaiInfo.getAtendanceDate())) {
					
					// 勤務日区分
					if (kintaiInfo.getWorkDayKbnMaster() != null) {
						// 勤務日区分
						monthInfo.setWorkKbn(kintaiInfo.getWorkDayKbnMaster().getCode());
						// 勤務日区分名
						monthInfo.setWorkKbnName(kintaiInfo.getWorkDayKbnMaster().getName());
					}
					// 休暇欠勤区分
					if (kintaiInfo.getKyukaKekinKbnMaster() != null ) {
						monthInfo.setKyukaKb(kintaiInfo.getKyukaKekinKbnMaster().getCode());
						monthInfo.setKyukaKbName(kintaiInfo.getKyukaKekinKbnMaster().getName());
					}
					if (kintaiInfo.getShiftJikoku() != null ) {
						// ｼﾌﾄ
						monthInfo.setShift(kintaiInfo.getShiftJikoku().getShiftCode().substring(0, 4));
					}
					// 出勤
					monthInfo.setWorkSTime(CostDateUtils.formatTime(kintaiInfo.getKinmuStartTime()));
					// 退勤
					monthInfo.setWorkETime(CostDateUtils.formatTime(kintaiInfo.getKinmuEndTime()));
					// 休暇時間数
					monthInfo.setRestHours(toDouble(kintaiInfo.getKyukaJikansu()));
					// 休暇欠勤区分
					if (kintaiInfo.getKyukaKekinKbnMaster() != null) {
						monthInfo.setKyukaKb(kintaiInfo.getKyukaKekinKbnMaster().getCode());
						monthInfo.setKyukaKbName(kintaiInfo.getKyukaKekinKbnMaster().getName());
					}
					// 勤務時間数
					monthInfo.setWorkHours(toDouble(kintaiInfo.getKinmuJikansu()));
					// 超勤開始
					monthInfo.setChoSTime(CostDateUtils.formatTime(kintaiInfo.getChokinStartTime()));
					// 超勤終了
					monthInfo.setChoETime(CostDateUtils.formatTime(kintaiInfo.getChokinEndTime()));
					// 超勤平増
					monthInfo.setChoWeekday(toDouble(kintaiInfo.getChokinHeijituJikansu()));
					// 超勤平常
					monthInfo.setChoWeekdayNomal(toDouble(kintaiInfo.getChokinHeijituTujyoJikansu()));
					// 超勤休日
					monthInfo.setChoHoliday(toDouble(kintaiInfo.getChokinKyujituJikansu()));
					// 超勤深夜
					monthInfo.setmNHours(toDouble(kintaiInfo.getSinyaKinmuJikansu()));
					// ﾛｹｰｼｮﾝコード
					monthInfo.setLocationCode(kintaiInfo.getLocation().getCode());
					// ﾛｹｰｼｮﾝ名前
					monthInfo.setLocationName(kintaiInfo.getLocation().getName());
					break;
				}
			}
		}
		
		// 合計休暇時間数
		Double totleRestHours = 0.0;
		// 合計勤務時間数
		Double totleWorkHours = 0.0;
		// 合計超勤平増
		Double totleChoWeekday = 0.0;
		// 合計超勤平常
		Double totleChoWeekdayNomal = 0.0;
		// 合計超勤休日
		Double totleChoHoliday = 0.0;
		// 合計超勤深夜
		Double totleMNHours = 0.0;
		// 合計情報
		MonthlyReportDispVO totleInfo = new MonthlyReportDispVO();

		// 月報承認明細情報合計行作成
		for (MonthlyReportDispVO itemInfo : monthlyReportApprovalList) {
			
			if (itemInfo.getRestHours() != null) {
				totleRestHours += itemInfo.getRestHours();
			}
			if (itemInfo.getWorkHours() != null) {
				totleWorkHours += itemInfo.getWorkHours();
			}
			if (itemInfo.getChoWeekday() != null) {
				totleChoWeekday += itemInfo.getChoWeekday();
			}
			if (itemInfo.getChoWeekdayNomal() != null) {
				totleChoWeekdayNomal += itemInfo.getChoWeekdayNomal();
			}
			if (itemInfo.getChoHoliday() != null) {
				totleChoHoliday += itemInfo.getChoHoliday();
			}
			if (itemInfo.getmNHours() != null) {
				totleMNHours += itemInfo.getmNHours();
			}
		}
		
		// 合計フラグ
		totleInfo.setTotleFlg(true);
		totleInfo.setRestHours(formatDouble(totleRestHours));
		totleInfo.setWorkHours(formatDouble(totleWorkHours));
		totleInfo.setChoWeekday(formatDouble(totleChoWeekday));
		totleInfo.setChoWeekdayNomal(formatDouble(totleChoWeekdayNomal));
		totleInfo.setChoHoliday(formatDouble(totleChoHoliday));
		totleInfo.setmNHours(formatDouble(totleMNHours));
		monthlyReportApprovalList.add(totleInfo);
		
		// 月報承認データリストの設定
		form.setMonthlyReportApprovalList(monthlyReportApprovalList);
		
		// プロジェクトリスト
		Map<String, List<ProjWorkTimeCountVO>> projectMap = new HashMap<String, List<ProjWorkTimeCountVO>>();
		// プロジェクトリストを作成する
		for (int k = 0; k < kintaiInfoList.size(); k++) {
			
			//  【PJ別作業時間集計】情報を取得
			condition = new BaseCondition();
			condition.addConditionEqual("kintaiInfo.id", kintaiInfoList.get(k).getId());
			List<ProjWorkTimeManage> entityList = baseDao.findResultList(condition, ProjWorkTimeManage.class);
			List<ProjWorkTimeCountVO> projWorkTimeCountList = null;
			for (ProjWorkTimeManage entity : entityList) {
				ProjWorkTimeCountVO projWorkTimeCountVO = null;
				// プロジェクトコード
				String code = entity.getProjectBasic().getProjectCode();
				// プロジェクト名が存在する場合
				if (projectMap.containsKey(code)) {
					projWorkTimeCountList = projectMap.get(code);
					// 合計値
					Double sum = projWorkTimeCountList.get(0).getPrpjectWorkTotalHours();
					sum += entity.getWorkTimes().doubleValue();
					projWorkTimeCountList.get(0).setPrpjectWorkTotalHours(sum);
					projWorkTimeCountVO = new ProjWorkTimeCountVO();
					// 作業内容
					projWorkTimeCountVO.setWorkContentName(entity.getProjWorkMaster().getName());
					// 作業時間数
					projWorkTimeCountVO.setWorkHoursNum(entity.getWorkTimes().doubleValue());
					projWorkTimeCountList.add(projWorkTimeCountVO);
				} else {
					projWorkTimeCountList = new ArrayList<ProjWorkTimeCountVO>();
					projWorkTimeCountVO = new ProjWorkTimeCountVO();
					// プロジェクト名を設定する
					projWorkTimeCountVO.setProjName(entity.getProjectBasic().getProjectName());
					projWorkTimeCountVO.setPrpjectWorkTotalHours(entity.getWorkTimes().doubleValue());
					projWorkTimeCountList.add(projWorkTimeCountVO);
					projectMap.put(code, projWorkTimeCountList);
					projWorkTimeCountVO = new ProjWorkTimeCountVO();
					// 作業内容
					projWorkTimeCountVO.setWorkContentName(entity.getProjWorkMaster().getName());
					// 作業時間数
					projWorkTimeCountVO.setWorkHoursNum(entity.getWorkTimes().doubleValue());
					projWorkTimeCountList.add(projWorkTimeCountVO);
				}
			}
		}

		// プロジェクトリスト作成
		List<ProjWorkTimeCountVO> projWorkTimeCountList = new ArrayList<ProjWorkTimeCountVO>();
		Iterator<String> iter = projectMap.keySet().iterator();
		
		while (iter.hasNext()) {
			// プロジェクトリストに追加する
			projWorkTimeCountList.addAll(projectMap.get(iter.next()));
		}
		form.setProjectList(projWorkTimeCountList);
		
	}

	
	/**
	 * 月報一覧を取得
	 * 
	 * @param date 日付
	 * 
	 * @return 月報一覧
	 */
	private List<MonthlyReportDispVO> getMonReList(Date date) {
		
		// 月報一覧
		List<MonthlyReportDispVO> monthyReportList = new ArrayList<MonthlyReportDispVO>();
		
		// 日付が空白以外の場合
		if (date != null) {

			// 月報の詳細
			MonthlyReportDispVO monReport;
			
			// カレンダー変換
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Integer size = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			for (int i = 1; i <= size; i++) {
				
				// 日付フォーマット
				SimpleDateFormat sdfYearM = new SimpleDateFormat(CommonConstant.YYYYMMDD);
				monReport = new MonthlyReportDispVO();
				// 日付を設定
				monReport.setDay(CostStringUtils.addZeroForNum(String.valueOf(calendar.get(Calendar.DATE)), 2));
				monReport.setDate(sdfYearM.format(date));
				// 曜日設
				switch (calendar.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.MONDAY:
						monReport.setWeek("月");
						break;
					case Calendar.TUESDAY:
						monReport.setWeek("火");
						break;
					case Calendar.WEDNESDAY:
						monReport.setWeek("水");
						break;
					case Calendar.THURSDAY:
						monReport.setWeek("木");
						break;
					case Calendar.FRIDAY:
						monReport.setWeek("金");
						break;
					case Calendar.SATURDAY:
						monReport.setWeek("土");
						break;
					case Calendar.SUNDAY:
						monReport.setWeek("日");
						break;
					default:
						break;
				}
				
				// 一覧追加
				monthyReportList.add(monReport);
				
				calendar.add(Calendar.DATE, 1);
				date = calendar.getTime();
			}
		}
		
		return monthyReportList;
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
	public void updateProStatus(String applyNo, String proStatusCode,String updateUserId) throws BusinessException {
		
		try {
			// 申請番号によって、承認情報を取得する		
			ApprovalManage approvalManageInfo = baseDao.findById(applyNo, ApprovalManage.class);
			// 申請状況を設定する
			StatusMaster statusMaster = baseDao.findById(proStatusCode, StatusMaster.class);
			approvalManageInfo.setStatusMaster(statusMaster);
			if (CommonConstant.STATUS_SYOUNIN.equals(proStatusCode)) {
				// 承認日付更新
				approvalManageInfo.setApprovedYmd(CostDateUtils.getNowDate());
				approvalManageInfo.setApproveUser(baseDao.findById(updateUserId, Users.class));
			}
			approvalManageInfo.setVersion(version);
			// 承認情報を更新する
			baseDao.update(approvalManageInfo);
		} catch (OptimisticLockException e) {
			
			throw new BusinessException();
		}
				
	}
	
	/**
	 * 年月取得処理
	 * 
	 * @param yyyyMMdd 日付
	 * 
	 * @return フォーマット日付
	 */
	private String getReportMonth (String yyyyMMdd) throws ParseException {
		
		Date date = CostDateUtils.toDate(yyyyMMdd);
		
		String formatDate = StringUtils.EMPTY;
		// 日付が空白以外の場合
		if (date != null) {
			// 日付フォーマット
			SimpleDateFormat sdfYearM = new SimpleDateFormat("yyyy年MM月");
			// 日付設定
			formatDate = sdfYearM.format(date);
		}
		return formatDate;
	}

	/**
	 * 時間数をフォーマットする
	 * 
	 * @param d
	 *               時間数
	 * @return 時間数（0.0の場合、ブランクにする）
	 */
	private Double formatDouble(Double d) {
		
		if (d != 0.0) {
			return d;
		}
 		
		return null;
	}
	
	/**
	 * 時間数をBigDecimalからDoubleにする
	 * 
	 * @param b
	 *               時間数
	 * @return 時間数
	 */
	private Double toDouble(BigDecimal b) {
		// bは０以外の値の場合
		if (b != null && b.compareTo(new BigDecimal(0.0)) != 0) {
			return b.doubleValue();
		}
 		
		return null;
	}
}
