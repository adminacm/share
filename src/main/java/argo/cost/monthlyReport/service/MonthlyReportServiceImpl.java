package argo.cost.monthlyReport.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	// 先月
	private final String LAST = "last";
	
	// 来月
	private final String NEXT = "next";

	/**
	 * 年月取得処理
	 * 
	 * @param date 日付
	 * 
	 * @return フォーマット日付
	 */
	@Override
	public String getDateFormat(Date date) {

		String formatDate = StringUtils.EMPTY;
		// 日付が空白以外の場合
		if (date != null) {
			// 日付フォーマット
			SimpleDateFormat sdfYearM = new SimpleDateFormat("yyyy年MM月");
			// 日付設定
			formatDate = sdfYearM.format(date);
		}
		// フォーマット日付
		return formatDate;
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

		String formatDate = StringUtils.EMPTY;
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
		SimpleDateFormat sdfYearM = new SimpleDateFormat(CommonConstant.YYYYMMDD);
		
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
		        
		List<KintaiInfo> monthlyKintaiInfoList = baseDao.findResultList(monthReportInfoSelectCondition, KintaiInfo.class);
		
		// 月報情報
		MonthlyReportDispVO monthInfo = null;
		// 勤怠情報
		KintaiInfo kintaiInfo = null;
		for(int j = 0; j < monthlyReportList.size(); j++) {
			// 月報情報
			monthInfo = monthlyReportList.get(j);
			for (int i = 0; i < monthlyKintaiInfoList.size(); i++) {

				// 勤怠情報
				kintaiInfo = monthlyKintaiInfoList.get(i);
				
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
		// 提出状況を設定する
		this.setStatus(monthlyKintaiInfoList, monthlyReportForm);
		
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
		totleInfo.setRestHours(formatDouble(totleRestHours));
		totleInfo.setWorkHours(formatDouble(totleWorkHours));
		totleInfo.setChoWeekday(formatDouble(totleChoWeekday));
		totleInfo.setChoWeekdayNomal(formatDouble(totleChoWeekdayNomal));
		totleInfo.setChoHoliday(formatDouble(totleChoHoliday));
		totleInfo.setmNHours(formatDouble(totleMNHours));
		monthlyReportList.add(totleInfo);
		
		// プロジェクトリスト
		Map<String, List<ProjWorkTimeCountVO>> projectMap = new HashMap<String, List<ProjWorkTimeCountVO>>();
		// プロジェクトリストを作成する
		for (int k = 0; k < monthlyKintaiInfoList.size(); k++) {
			
			//  【PJ別作業時間集計】情報を取得
			BaseCondition condition = new BaseCondition();
			condition.addConditionEqual("kintaiInfo.id", monthlyKintaiInfoList.get(k).getId());
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
	 * ユーザ最後の月報提出日付を取得
	 * 
	 * @param userId
	 *               社員番号
	 * @return
	 *        ユーザ最後の月報提出日付
	 */
	@Override
	public String getUserLatestShinseiMonth(String userId) {
		
		// 最新の日付
		String lastDate = monthlyReportDao.getUserLatestShinseiMonth(userId);
		// 月の初日を戻る
		return lastDate.substring(0, 6).concat("01");
	}

	/**
	 * 申請一覧テーブルを更新する
	 * 
	 * @param form
	 * 			画面情報
	 * 
	 * @throws Exception
	 */
	@Override
	public void monthyReportCommit(MonthlyReportForm form) throws Exception {
		
		// ログインID
		String loginUserId = form.getUserId();
		// 対象者ID
		String taishoUserId = form.getTaishoUserId();
		// 月報申請データを作成
		ApprovalManage approvalManage = new ApprovalManage();

		// 申請番号：ユーザーID+申請区分+申請日付
		String strApplyNo = taishoUserId.concat(CommonConstant.APPLY_KBN_GETUHOU).concat(form.getYearMonth().substring(0, 6).concat("00"));
		// 申請番号
		approvalManage.setApplyNo(strApplyNo);
		// 申請状況コードを"02"(提出)更新される
		approvalManage.setStatusMaster(baseDao.findById(CommonConstant.STATUS_TEISYUTU, StatusMaster.class));
		// 申請内容
		approvalManage.setApplyDetail("月報申請：".concat(form.getYearMonthHyoji()));
		// 申請区分("1"(月報)を設定する)
		approvalManage.setApplyKbnMaster(baseDao.findById(CommonConstant.APPLY_KBN_GETUHOU, ApplyKbnMaster.class));
		// 社員番号
		approvalManage.setUser(baseDao.findById(taishoUserId, Users.class));
		// 申請日付
		approvalManage.setApplyYmd(form.getYearMonth());
		// 申請者ＩＤ
		approvalManage.setApplicantUser(baseDao.findById(form.getUserId(), Users.class));
		// 処理年月
		approvalManage.setSyoriYm(CostDateUtils.getDealDate(form.getYearMonth(), CommonConstant.APPLY_KBN_GETUHOU));
		approvalManage.setUpdatedUserId(loginUserId);               // 更新者
		approvalManage.setUpdateDate(new Timestamp(System.currentTimeMillis()));  // 更新時刻
		try {
			// 承認情報を取得
			ApprovalManage approval = baseDao.findById(strApplyNo, ApprovalManage.class); 
			// 承認情報が存在しない場合
			if (approval == null) {
				approvalManage.setCreatedUserId(loginUserId);               // 登録者
				approvalManage.setCreatedDate(new Timestamp(System.currentTimeMillis())); // 登録時刻
				// 申請一覧を登録する
				baseDao.insert(approvalManage);
				
			} else {
				// 申請状況コードを"02"(提出)更新される
				approval.setStatusMaster(baseDao.findById(CommonConstant.STATUS_TEISYUTU, StatusMaster.class));
				approval.setUpdatedUserId(loginUserId);               // 更新者
				approval.setUpdateDate(new Timestamp(System.currentTimeMillis()));  // 更新時刻
				// 申請一覧を登録する
				baseDao.update(approval);
			}

			// 勤怠情報の対応した申請番号を更新される
			BaseCondition condition = new BaseCondition();
			condition.addConditionEqual("users.id", taishoUserId);
			condition.addConditionLike("atendanceDate", form.getYearMonth().substring(0, 6) + "%");
			List<KintaiInfo> monthyKintaiInfoList = baseDao.findResultList(condition, KintaiInfo.class);
			
			for (KintaiInfo kintaiInfo : monthyKintaiInfoList) {
				// 申請番号を勤怠テーブルに更新される
				kintaiInfo.setApprovalManage1(approvalManage);
				baseDao.update(kintaiInfo);
			}
		} catch (Exception e) {
			form.putConfirmMsg("月報申請提出失敗しました");
		}
		
	}
	
/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 月報提出状況を設定する
	 * 
	 * @param kintaiList
	 *               勤怠情報リスト
	 * @param form
	 *               画面情報
	 */
	private void setStatus(List<KintaiInfo> kintaiList, MonthlyReportForm form) {
		
		if (kintaiList != null && kintaiList.size() != 0) {
			for (KintaiInfo info : kintaiList) {
				if (info != null && info.getApprovalManage1() != null) {
					form.setProStatus(info.getApprovalManage1().getStatusMaster().getName());
				} else {
					form.setProStatus("作成中");
				}
				break;
			}
		} else {
			form.setProStatus("作成中");
		}
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
