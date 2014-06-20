package argo.cost.monthlyReport.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApplyKbnMaster;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.ProjWorkTimeManage;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.common.model.ProjWorkTimeCountVO;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.common.utils.CostStringUtils;
import argo.cost.monthlyReport.checker.MonthlyReportChecker;
import argo.cost.monthlyReport.dao.MonthlyReportDao;
import argo.cost.monthlyReport.model.MonthlyReportForm;

/**
 * 月報画面サービス実現するクラス
 * 報画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Service
public class MonthlyReportServiceImpl implements MonthlyReportService {
	
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;
	
	/**
	 * 月報処理DAO
	 */
	@Autowired
	private MonthlyReportDao monthlyReportDao;
	
	/** 定数 */
	// YYYYMMDD形式を表す文字列
	private final String YYYYMMDD = "yyyyMMdd";
	
	// 先月
	private final String LAST = "last";
	
	// 来月
	private final String NEXT = "next";

	private Double totalWorkHourNum = null;

	/**
	 * 年月取得処理
	 * 
	 * @param date 日付
	 * 
	 * @return フォーマット日付
	 */
	@Override
	public String getDateFormat(Date date) {

		String formatDate = "";
		// 日付が空白以外の場合
		if (date != null) {
			
			// 日付フォーマット
			SimpleDateFormat sdfYearM = new SimpleDateFormat("YYYY年MM月");
			
			// 日付設定
			formatDate = sdfYearM.format(date);
		}

		// フォーマット日付
		return formatDate;
		
	}

	/**
	 * 月報一覧を取得
	 * 
	 * @param date 日付
	 * 
	 * @return 月報一覧
	 */
	public List<MonthlyReportDispVO> getMonthlyReportList(Date date) {
		
		// 月報一覧
		List<MonthlyReportDispVO> monReList = new ArrayList<MonthlyReportDispVO>();
		
		// 日付が空白以外の場合
		if (date != null) {

			// 月報の詳細
			MonthlyReportDispVO monthlyReportApproval;
			
			// カレンダー変換
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Integer size = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			for (int i = 1; i <= size; i++) {
				
				// 日付フォーマット
				SimpleDateFormat sdfYearM = new SimpleDateFormat(YYYYMMDD);
				monthlyReportApproval = new MonthlyReportDispVO();
				// 日付を設定
				monthlyReportApproval.setDay(CostStringUtils.addZeroForNum(String.valueOf(calendar.get(Calendar.DATE)), 2));
				monthlyReportApproval.setDate(sdfYearM.format(date));
				// 曜日設
				switch (calendar.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.MONDAY:
						monthlyReportApproval.setWeek("月");
						break;
					case Calendar.TUESDAY:
						monthlyReportApproval.setWeek("火");
						break;
					case Calendar.WEDNESDAY:
						monthlyReportApproval.setWeek("水");
						break;
					case Calendar.THURSDAY:
						monthlyReportApproval.setWeek("木");
						break;
					case Calendar.FRIDAY:
						monthlyReportApproval.setWeek("金");
						break;
					case Calendar.SATURDAY:
						monthlyReportApproval.setWeek("土");
						break;
					case Calendar.SUNDAY:
						monthlyReportApproval.setWeek("日");
						break;
					default:
						break;
				}
				
				// 一覧追加
				monReList.add(monthlyReportApproval);
				
				calendar.add(Calendar.DATE, 1);
				date = calendar.getTime();
			}
		}
		
		return monReList;
	}
	
	/**
	 * 
	 * 年月の変換処理
	 * 
	 * @param changeFlg 変換フラグ
	 * @param date 日付
	 * @return 年月
	 * @throws ParseException 
	 */
	@Override
	public String changeYearMonth(String changeFlg, String month) throws ParseException {

		String formatDate = "";
		Calendar calendar = new GregorianCalendar(); 
		Date date = CostDateUtils.toDate(month.substring(0, 6).concat("01"));
		calendar.setTime(date);
		 
		// 年月の←ボタンを押すと、前の月に表示が切り替わる
		if (LAST.equals(changeFlg)) {
			 
		    calendar.add(Calendar.MONTH, -1);
			 
		} else if (NEXT.equals(changeFlg)) {
		 
		// 年月の→ボタンを押すと、次の月に表示が切り替わる
		    calendar.add(Calendar.MONTH, 1);
		}
		 
		date = calendar.getTime();
			
		// 日付フォーマット
		SimpleDateFormat sdfYearM = new SimpleDateFormat(YYYYMMDD);
		
		// 年月設定
		formatDate = sdfYearM.format(date);

		// 年月
		return formatDate;
	}

	
	/**
	 * ユーザの月報情報を取得し、月報リストに設定する
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 * @throws ParseException 
	 */
	@Override
	public void setUserMonthReport(String userId, String date, MonthlyReportForm monthlyReportForm) throws ParseException {
		
		MonthlyReportChecker.chkKintaiInfoInput(monthlyReportForm);
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
		
        List<MonthlyReportDispVO> monthlyReportList = monthlyReportForm.getmRList();
		
		// 月報情報を取得
		BaseCondition monthReportInfoSelectCondition = new BaseCondition();
		// 検索条件：ユーザーID
		monthReportInfoSelectCondition.addConditionEqual("users.id", userId);
		// 検索条件：年月
		monthReportInfoSelectCondition.addConditionLike("atendanceDate",  date.substring(0, 6) + "%");
		        
		ArrayList<KintaiInfo> monthlyKintaiInfoList = (ArrayList<KintaiInfo>) baseDao.findResultList(monthReportInfoSelectCondition, KintaiInfo.class);
		List<ProjWorkTimeManage> projWorkTimeManageList = new ArrayList<ProjWorkTimeManage>();
		
		for(int j = 0; j < monthlyReportList.size(); j++){
			
			
			for (int i = 0; i < monthlyKintaiInfoList.size(); i++) {
				// 対応した日付で、勤務情報を設定する
				if (StringUtils.equals(monthlyReportList.get(j).getDate(), monthlyKintaiInfoList.get(i).getAtendanceDate())) {
					
					
					// 区分
					if (monthlyKintaiInfoList.get(i).getKyukaKekinKbnMaster() != null ){
						monthlyReportList.get(j).setWorkKbn(monthlyKintaiInfoList.get(i).getKyukaKekinKbnMaster().getCode());
					}
					if (monthlyKintaiInfoList.get(i).getShiftJikoku() != null ){
						// ｼﾌﾄ
						monthlyReportList.get(j).setShift(monthlyKintaiInfoList.get(i).getShiftJikoku().getShiftCode());
					}
					// 出勤
					monthlyReportList.get(j).setWorkSTime(monthlyKintaiInfoList.get(i).getKinmuStartTime());
					// 退勤
					monthlyReportList.get(j).setWorkETime(monthlyKintaiInfoList.get(i).getKinmuEndTime());
					// 休暇
					if (monthlyKintaiInfoList.get(i).getKyukaJikansu() != null ) {
						monthlyReportList.get(j).setRestHours(monthlyKintaiInfoList.get(i).getKyukaJikansu().doubleValue());
					}
					
					// 勤務時間数
					if (monthlyKintaiInfoList.get(i).getKinmuJikansu() != null ) {
						monthlyReportList.get(j).setWorkHours(monthlyKintaiInfoList.get(i).getKinmuJikansu().doubleValue());
					}
					// 超勤開始
					monthlyReportList.get(j).setChoSTime(monthlyKintaiInfoList.get(i).getChokinStartTime());
					// 超勤終了
					monthlyReportList.get(j).setChoETime(monthlyKintaiInfoList.get(i).getChokinEndTime());
					// 超勤平増
					if (monthlyKintaiInfoList.get(i).getChokinHeijituJikansu() != null ) {
						monthlyReportList.get(j).setChoWeekday(monthlyKintaiInfoList.get(i).getChokinHeijituJikansu().doubleValue());
					}
					
					// 超勤平常
					if (monthlyKintaiInfoList.get(i).getChokinHeijituTujyoJikansu() != null ) {
						monthlyReportList.get(j).setChoWeekdayNomal(monthlyKintaiInfoList.get(i).getChokinHeijituTujyoJikansu().doubleValue());
					}
					
					// 超勤休日
					if (monthlyKintaiInfoList.get(i).getChokinKyujituJikansu() != null ) {
						monthlyReportList.get(j).setChoHoliday(monthlyKintaiInfoList.get(i).getChokinKyujituJikansu().doubleValue());
					}
					
					// 超勤深夜
					if (monthlyKintaiInfoList.get(i).getSinyaKinmuJikansu() != null ) {
						monthlyReportList.get(j).setmNHours(monthlyKintaiInfoList.get(i).getSinyaKinmuJikansu().doubleValue());
					}
					
					// ﾛｹｰｼｮﾝコード
					monthlyReportList.get(j).setLocationCode(monthlyKintaiInfoList.get(i).getLocation().getCode());
					// ﾛｹｰｼｮﾝ名前
					monthlyReportList.get(j).setLocationName(monthlyKintaiInfoList.get(i).getLocation().getName());
					
					
					
				}
				
			}
			
		}
		
		for (int k = 0; k < monthlyKintaiInfoList.size(); k++) {
			
			//  【PJ別作業時間集計】情報を取得
			BaseCondition projectWorkTImeSelectCondition = new BaseCondition();
			projectWorkTImeSelectCondition.addConditionEqual("kintaiInfo.id", monthlyKintaiInfoList.get(k).getId());
			projWorkTimeManageList = baseDao.findResultList(projectWorkTImeSelectCondition, ProjWorkTimeManage.class);

		}
		
		// 月報承認明細情報合計行作成
		for (MonthlyReportDispVO itemInfo : monthlyReportList) {
			
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
		totleInfo.setRestHours(totleRestHours);
		totleInfo.setWorkHours(totleWorkHours);
		totleInfo.setChoWeekday(totleChoWeekday);
		totleInfo.setChoWeekdayNomal(totleChoWeekdayNomal);
		totleInfo.setChoHoliday(totleChoHoliday);
		totleInfo.setmNHours(totleMNHours);
		monthlyReportList.add(totleInfo);
		
		monthlyReportForm.setmRList(monthlyReportList);
		
		List<ProjWorkTimeCountVO> projWorkTimeCountList = new ArrayList<ProjWorkTimeCountVO>();
		
		for (int m = 0; m < projWorkTimeManageList.size(); m++) {
			
			ProjWorkTimeCountVO projWorkTimeCountVO = new ProjWorkTimeCountVO();
			// プロジェクト名前（漢字）
			projWorkTimeCountVO.setProjName(projWorkTimeManageList.get(m).getProjectMaster().getName());
			// プロジェクトコード
			projWorkTimeCountVO.setProjCode(projWorkTimeManageList.get(m).getProjectMaster().getCode());
			// 作業内容名
			projWorkTimeCountVO.setWorkContentName(projWorkTimeManageList.get(m).getProjWorkMaster().getName());
			
			Double everyWorkHoursNum = projWorkTimeManageList.get(m).getProjWorkMaster().getProjWorkTimeManages().get(m).getWorkTimes().doubleValue();
			// 作業対応した時間数
			projWorkTimeCountVO.setWorkHoursNum(everyWorkHoursNum);
			
			totalWorkHourNum  += everyWorkHoursNum;
			// プロジェクトの総計時間数
			projWorkTimeCountVO.setPrpjectWorkTotalHours(totalWorkHourNum);
		
			projWorkTimeCountList.add(projWorkTimeCountVO);
		}
		monthlyReportForm.setProjWorkTimeCountVOList(projWorkTimeCountList);
	}

	
	/**
	 * 月報一覧を取得
	 * 
	 * @param date 日付
	 * 
	 * @return 月報一覧
	 */
	@Override
	public List<MonthlyReportDispVO> getMonthyReportList(Date date) {
		
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

	@Override
	public String getUserLatestShinseiMonth(String userId) {
		
		return monthlyReportDao.getUserLatestShinseiMonth(userId);
	}

	@Override
	public String monthyReportCommit(MonthlyReportForm monthlyReportForm)
			throws ParseException {
		
		String userId = monthlyReportForm.getUserId();
		ApprovalManage approvalManage = new ApprovalManage();
		
		String strApplyNo = userId.concat("1").concat(monthlyReportForm.getYearMonth().substring(0, 6).concat("00"));
	
		// 申請番号
		approvalManage.setApplyNo(strApplyNo);
		// 申請状況コードを"02"(提出)更新される
		approvalManage.setStatusMaster(baseDao.findById(CommonConstant.STATUS_TEISYUTU, StatusMaster.class));
		
		// 申請内容
		approvalManage.setApplyDetail("2014年5月分");
		
		// 申請区分("1"(月報)を設定する)
		approvalManage.setApplyKbnMaster(baseDao.findById(CommonConstant.APPLY_KBN_GETUHOU, ApplyKbnMaster.class));
		
		// 社員番号
		approvalManage.setUser(baseDao.findById(userId, Users.class));
		
		// 申請日付
		SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyyMMdd");
		approvalManage.setAppYmd(simpleDateFormat.format(new Date()));
		
		// 月報の日付
		approvalManage.setItemDate(monthlyReportForm.getYearMonth());
		String strMonthyReportCommitFlg = "1";
		
		// 勤怠情報の対応した申請番号を更新される
		BaseCondition commitedMonthyReportSelect = new BaseCondition();
		commitedMonthyReportSelect.addConditionEqual("users.id", userId);
		commitedMonthyReportSelect.addConditionLike("atendanceBookDate", monthlyReportForm.getYearMonth().substring(0, 6) + "%");
		List<KintaiInfo> monthyKintaiInfoList = baseDao.findResultList(commitedMonthyReportSelect, KintaiInfo.class);
		
		approvalManage.setKintaiInfos(monthyKintaiInfoList);
		try {
			baseDao.insert(approvalManage);
			
			for (int i = 0; i < monthyKintaiInfoList.size(); i++ ) {
				// 申請番号を勤怠テーブルに更新される
				monthyKintaiInfoList.get(i).setApprovalManage(approvalManage);
				baseDao.update(monthyKintaiInfoList.get(i));
			}
		} catch (Exception e) {
			strMonthyReportCommitFlg = "0";
			System.out.print("1111");
			
		}
		
		return strMonthyReportCommitFlg;
		
		
	}


}
