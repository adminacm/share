package argo.cost.monthlyReport.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;

import argo.cost.common.utils.CostDateUtils;
import argo.cost.common.utils.CostStringUtils;
import argo.cost.monthlyReport.dao.MonthlyReportDao;
import argo.cost.monthlyReport.dao.MonthlyReportDaoImpl;
import argo.cost.monthlyReport.model.MonthlyReportEntity;
import argo.cost.monthlyReport.model.MonthlyReportInfo;
import argo.cost.monthlyReport.model.ProjectVo;

@Service
public class MonthlyReportServiceImpl implements MonthlyReportService {
	
	/** 定数 */
	// YYYYMMDD形式を表す文字列
	private final String YYYYMMDD = "yyyyMMdd";
	
	// 先月
	private final String LAST = "last";
	
	// 来月
	private final String NEXT = "next";
	/**
	 * 月報処理Dao
	 */
	MonthlyReportDao dao = new MonthlyReportDaoImpl();

	/**
	 * 年月取得処理
	 * 
	 * @param date 日付
	 * 
	 * @return フォーマット日付
	 */
	@Override
	public String ｇetDateFormat(Date date) {

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
	@Override
	public List<MonthlyReportInfo> getMonReList(Date date) {
		
		// 月報一覧
		List<MonthlyReportInfo> monReList = new ArrayList<MonthlyReportInfo>();
		
		// 日付が空白以外の場合
		if (date != null) {

			// 月報の詳細
			MonthlyReportInfo monReport;
			
			// カレンダー変換
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Integer size = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			for (int i = 1; i <= size; i++) {
				
				// 日付フォーマット
				SimpleDateFormat sdfYearM = new SimpleDateFormat(YYYYMMDD);
				monReport = new MonthlyReportInfo();
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
				monReList.add(monReport);
				
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
		Date date = CostDateUtils.toDate(month);
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
	 * 
	 * ユーザの最後の月報提出年月を取得処理
	 * 
	 * @param userId ユーザID
	 * 
	 * @return 最後の月報提出年月
	 */
	@Override
	public String getUserMonth(String userId) {
		
		return dao.getUserMonth(userId);
	}
	
	/**
	 * ユーザの月報情報を取得し、月報リストに設定する
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 */
	@Override
	public void setUserMonthReport(String userId, String date, List<MonthlyReportInfo> monthList) {
		
		List<MonthlyReportEntity> reportList = dao.getUserMonthReport(userId, date);
		
		for (MonthlyReportEntity rep : reportList) {
			
			for (MonthlyReportInfo info : monthList) {
				
				if (info.getDate().equals(rep.getWorkDate())) {
					
					// シフトコード
					info.setShift(rep.getShiftCode());;
					// 勤務日区分
					info.setWorkKbn(rep.getWorkKbn());
					// 勤務区分名
					info.setWorkKbnName(rep.getWorkKbnName());
					// 出勤
					info.setWorkSTime(CostDateUtils.formatIntegerToTime(rep.getWorkSTime()));
					// 退勤
					info.setWorkETime(CostDateUtils.formatIntegerToTime(rep.getWorkETime()));
					// 休暇欠勤区分
					info.setRestKbn(rep.getRestKbn());
					// 休暇欠勤区分名
					info.setRestKbnName(rep.getRestKbnName());
					// 勤務時間数
					info.setWorkHours(rep.getWorkHours());
					// 超勤開始
					info.setChoSTime(CostDateUtils.formatIntegerToTime(rep.getOverSTime()));
					// 超勤終了
					info.setChoETime(CostDateUtils.formatIntegerToTime(rep.getOverETime()));
					// 超勤平増
					info.setChoWeekday(rep.getOverHours());
					// 超勤平常
					info.setChoWeekdayNomal(rep.getOverHoursOrdinary());
					// 超勤休日
					info.setChoHoliday(rep.getOverHoursHoliday());
					// 超勤深夜
					info.setmNHours(rep.getOverHoursNight());
					// ﾛｹｰｼｮﾝ
					info.setLocationCode(rep.getLocationCode());
					// ロケーション名
					info.setLocationName(rep.getLocationName());
					break;
				}
			}
			
		}
	}

	/**
	 * 【PJ別作業時間集計】情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 * @return
	 *        プロジェクト情報
	 */
	@Override
	public List<ProjectVo> getProjectList(String userId, String date) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.getProjectList(userId, date);
	}

}
