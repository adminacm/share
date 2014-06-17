package argo.cost.monthlyReportApproval.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.dao.ComDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.ProjWorkTimeManage;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.common.utils.CostStringUtils;
import argo.cost.monthlyReportApproval.dao.MonthlyReportApprovalDao;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalForm;

@Service
public class MonthlyReportApprovalServiceImpl implements MonthlyReportApprovalService {

	/**
	 * 月報承認DAO
	 */
	@Autowired
	private MonthlyReportApprovalDao monthlyReportApprovalDao;
	
	/**
	 * 共通DAO
	 */	
	@Autowired
	private ComDao comDao;
	
	/** 定数 */
	// YYYYMMDD形式を表す文字列
	private final String YYYYMMDD = "yyyyMMdd";
	
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
	public MonthlyReportApprovalForm getMonReApprovalList(MonthlyReportApprovalForm monthlyReportApprovalForm, String applyNo) throws ParseException {
		
		List<MonthlyReportDispVO> monthlyReportApprovalList = new ArrayList<MonthlyReportDispVO>();
		
		// 最新の申請日付を取得
		String strLatestShinseiDate = monthlyReportApprovalDao.getLatestShinseiDate(monthlyReportApprovalForm.getUserId());
		monthlyReportApprovalList = getMonReList(CostDateUtils.toDate(strLatestShinseiDate.concat("01")));
		// 月報データを取得
		BaseCondition kintaiInfoSelectCondition = new BaseCondition();
		kintaiInfoSelectCondition.addConditionEqual("approvalManage.id", applyNo);
		
		ArrayList<KintaiInfo> kintaiInfoList = (ArrayList<KintaiInfo>) baseDao.findResultList(kintaiInfoSelectCondition, KintaiInfo.class);
		
		List<ProjWorkTimeManage> projWorkTimeManageList = new ArrayList<ProjWorkTimeManage>();
		for(int j = 0 ;j < monthlyReportApprovalList.size();j++) {
			
			for (int i = 0;i < kintaiInfoList.size(); i++) {
				
				// 対応した日付で、勤務情報を設定する
				if (StringUtils.equals(monthlyReportApprovalList.get(j).getDate(), kintaiInfoList.get(i).getAtendanceBookDate())) {
					
					// 区分
					if (kintaiInfoList.get(i).getKyukaKekinKbnMaster() != null ){
						monthlyReportApprovalList.get(j).setWorkKbn(kintaiInfoList.get(i).getKyukaKekinKbnMaster().getCode());
					}
					if (kintaiInfoList.get(i).getShiftJikoku() != null ){
						// ｼﾌﾄ
						monthlyReportApprovalList.get(j).setShift(kintaiInfoList.get(i).getShiftJikoku().getShiftCode());
					}
					// 出勤
					monthlyReportApprovalList.get(j).setWorkSTime(kintaiInfoList.get(i).getKinmuStartTime());
					// 退勤
					monthlyReportApprovalList.get(j).setWorkETime(kintaiInfoList.get(i).getKinmuEndTime());
					// 休暇
					if (kintaiInfoList.get(i).getKyukaJikansu() != null ) {
						monthlyReportApprovalList.get(j).setRestHours(kintaiInfoList.get(i).getKyukaJikansu().doubleValue());
					}
					
					// 勤務時間数
					if (kintaiInfoList.get(i).getKinmuJikansu() != null ) {
						monthlyReportApprovalList.get(j).setWorkHours(kintaiInfoList.get(i).getKinmuJikansu().doubleValue());
					}
					// 超勤開始
					monthlyReportApprovalList.get(j).setChoSTime(kintaiInfoList.get(i).getChokinStartTime());
					// 超勤終了
					monthlyReportApprovalList.get(j).setChoETime(kintaiInfoList.get(i).getChokinEndTime());
					// 超勤平増
					if (kintaiInfoList.get(i).getChokinHeijituJikansu() != null ) {
						monthlyReportApprovalList.get(j).setChoWeekday(kintaiInfoList.get(i).getChokinHeijituJikansu().doubleValue());
					}
					
					// 超勤平常
					if (kintaiInfoList.get(i).getChokinHeijituTujyoJikansu() != null ) {
						monthlyReportApprovalList.get(j).setChoWeekdayNomal(kintaiInfoList.get(i).getChokinHeijituTujyoJikansu().doubleValue());
					}
					
					// 超勤休日
					if (kintaiInfoList.get(i).getChokinKyujituJikansu() != null ) {
						monthlyReportApprovalList.get(j).setChoHoliday(kintaiInfoList.get(i).getChokinKyujituJikansu().doubleValue());
					}
					
					// 超勤深夜
					if (kintaiInfoList.get(i).getSinyaKinmuJikansu() != null ) {
						monthlyReportApprovalList.get(j).setmNHours(kintaiInfoList.get(i).getSinyaKinmuJikansu().doubleValue());
					}
					
					// ﾛｹｰｼｮﾝコード
					monthlyReportApprovalList.get(j).setLocationCode(kintaiInfoList.get(i).getLocation().getCode());
					// ﾛｹｰｼｮﾝ名前
					monthlyReportApprovalList.get(j).setLocationName(kintaiInfoList.get(i).getLocation().getName());
					
				}
				
			    //  【PJ別作業時間集計】情報を取得
				BaseCondition projectWorkTImeSelectCondition = new BaseCondition();
				projectWorkTImeSelectCondition.addConditionEqual("kintaiInfo.id", kintaiInfoList.get(i).getId());
				ProjWorkTimeManage projWorkTimeManageInfo = baseDao.findSingleResult(projectWorkTImeSelectCondition, ProjWorkTimeManage.class);
				projWorkTimeManageList.add(projWorkTimeManageInfo);
				
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
		
		MonthlyReportDispVO totleInfo = new MonthlyReportDispVO();
		// 合計フラグ
		totleInfo.setTotleFlg(true);
		totleInfo.setRestHours(totleRestHours);
		totleInfo.setWorkHours(totleWorkHours);
		totleInfo.setChoWeekday(totleChoWeekday);
		totleInfo.setChoWeekdayNomal(totleChoWeekdayNomal);
		totleInfo.setChoHoliday(totleChoHoliday);
		totleInfo.setmNHours(totleMNHours);
		monthlyReportApprovalList.add(totleInfo);
		// 月報承認データリストの設定
		monthlyReportApprovalForm.setMonthlyReportApprovalList(monthlyReportApprovalList);
		
		// PJ別作業時間集計リストの設定
		monthlyReportApprovalForm.setProjectList(projWorkTimeManageList);
		
		// TODO PJ別作業時間集計のプロジェクト作業時間総計
		return monthlyReportApprovalForm;
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
				SimpleDateFormat sdfYearM = new SimpleDateFormat(YYYYMMDD);
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
	public String updateProStatus(String applyNo, String proStatusCode) {
		
		// 更新フラグ
		String strUpdateFlg = "1";
		// 申請番号によって、承認情報を取得する		
		ApprovalManage approvalManageInfo = baseDao.findById(applyNo, ApprovalManage.class);
		// 申請状況を設定する
		StatusMaster statusMaster = baseDao.findById(proStatusCode, StatusMaster.class);
		approvalManageInfo.setStatusMaster(statusMaster);

		try {

			// 承認情報を更新する
			baseDao.update(approvalManageInfo);
		} catch (Exception e) {

			strUpdateFlg = "0";
		}
				
		return strUpdateFlg;

	}

}
