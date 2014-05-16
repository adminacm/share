package argo.cost.monthlyReport;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import argo.cost.common.utils.CostDateUtils;
import argo.cost.monthlyReport.model.MonthlyReportEntity;
import argo.cost.monthlyReport.model.MonthlyReportInfo;
import argo.cost.monthlyReport.service.MonthlyReportServiceImpl;

public class MonthlyReportTest {

	// 月報サビース
	MonthlyReportServiceImpl MonRS = new MonthlyReportServiceImpl();

	/**
	 * 年月取得処理をテスト
	 */
	@Test
	public void testGetYearMonth(){
		
		// 現在日付
		Date date = new Date();
		
		// 年月設定
		String yearMonth = MonRS.getDateFormat(date);
		
		// 年月
		assertEquals(yearMonth, "2014年03月");
	}
	
	/**
	 * 月報一覧取得処理
	 */
	@Test
	public void testGetMonReList(){
		
		// 現在日付
		Date date = null;
		try {
			date = CostDateUtils.toDate("20140201");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 月報一覧取得
		List<MonthlyReportInfo> monReListList = MonRS.getMonReList(date);
		
		// 月報リストのサイズ
		assertEquals(28, monReListList.size());
	}

	/**
	 * 年月の変換処理をテスト
	 * 
	 * 年月の←ボタンを押すと、前の月に表示が切り替わる
	 */
	@Test
	public void testChangeYearMonth1(){
		
		// 変換フラグ
		String changeFlg = "last";
		
		// 月
		String date = "20140201";
		
		// 変換後の年月
		String formatDate = null;
		try {
			formatDate = MonRS.changeYearMonth(changeFlg, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 年月
		assertEquals(formatDate, "20140101");
	}

	/**
	 * 年月の変換処理をテスト
	 * 
	 * 年月の→ボタンを押すと、次の月に表示が切り替わる
	 */
	@Test
	public void testChangeYearMonth2(){
		
		// 変換フラグ
		String changeFlg = "next";
		
		// 現在日付
		String date = "20140201";
		
		// 変換後の年月
		String formatDate = null;
		try {
			formatDate = MonRS.changeYearMonth(changeFlg, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 年月
		assertEquals(formatDate, "20140301");
	}
	
	/**
	 * ユーザの最後の月報提出年月取得処理をテスト
	 */
	@Test
	public void testGetUserMonth(){
		
		// ユーザの最後の月報提出年月を取得
		String userMonth = MonRS.getUserMonth("li");
		
		// 年月
		assertEquals(userMonth, "20140201");
	}
	
	/**
	 * 月報リスト設定をテスト
	 */
	@Test
	public void testSetUserMonthReport(){
		
		// データを追加
		List<MonthlyReportEntity> resultList = new ArrayList<MonthlyReportEntity>();
		MonthlyReportEntity enty1 = new MonthlyReportEntity();
		enty1.setCommant("桜美林大学留学生管理システム保守：お客様問合せの対応");
		enty1.setDisposeDate("20140305135051");
		enty1.setLocationCode("02");
		enty1.setLocationName("北京");
		enty1.setOverSTime(1830);
		enty1.setOverETime(2200);
		enty1.setOverHours(1.5);
		enty1.setOverHoursHoliday(0.0);
		enty1.setOverHoursNight(0.0);
		enty1.setOverHoursOrdinary(1.5);
		enty1.setRepDate("20140401");
		enty1.setRestHours(1.5);
		enty1.setRestKbn("01");
		enty1.setRestKbnName("時間休(有給休暇)");
		enty1.setShiftCode("0900");
		enty1.setUserId("liuyj");
		enty1.setWorkDate("20140201");
		enty1.setWorkETime(3200);
		enty1.setWorkHours(11.5);
		enty1.setWorkKbn("01");
		enty1.setWorkKbnName("休日振替勤務");
		enty1.setWorkSTime(900);
		resultList.add(enty1);
		
		MonthlyReportEntity enty2 = new MonthlyReportEntity();
		enty2.setCommant("桜美林大学留学生管理システム保守：お客様問合せの対応");
		enty2.setDisposeDate("20140305135051");
		enty2.setLocationCode("01");
		enty2.setLocationName("日本");
		enty2.setOverSTime(1830);
		enty2.setOverETime(2200);
		enty2.setOverHours(1.5);
		enty2.setOverHoursHoliday(0.0);
		enty2.setOverHoursNight(0.0);
		enty2.setOverHoursOrdinary(1.5);
		enty2.setRepDate("20140401");
		enty2.setRestHours(1.5);
		enty2.setRestKbn("04");
		enty2.setRestKbnName("特別休暇");
		enty2.setShiftCode("0900");
		enty2.setUserId("liuyj");
		enty2.setWorkDate("20140202");
		enty2.setWorkETime(2200);
		enty2.setWorkHours(11.5);
		enty2.setWorkKbn("02");
		enty2.setWorkKbnName("休日");
		enty2.setWorkSTime(900);
		resultList.add(enty2);
		
		// ユーザID
		String userId = "liuyj";
		// 日付
		String date = "20140401";
		//　月報一覧
		List<MonthlyReportInfo> monthList = new ArrayList<MonthlyReportInfo>();
		MonthlyReportInfo monthlyInfo = new MonthlyReportInfo();
		monthlyInfo.setDay("01");
		monthlyInfo.setWeek("土");
		monthlyInfo.setDate("20140201");
		monthList.add(monthlyInfo);
		
		monthlyInfo = new MonthlyReportInfo();
		monthlyInfo.setDay("02");
		monthlyInfo.setWeek("日");
		monthlyInfo.setDate("20140202");
		monthList.add(monthlyInfo);
		
		// ユーザの月報情報を取得し、月報リストに設定する
		MonRS.setUserMonthReport(userId, date, monthList);

		assertEquals(monthList.size(), 2);
		// 勤務区分
		assertEquals(monthList.get(0).getWorkKbn(), resultList.get(0).getWorkKbn());
		// 勤務区分名
		assertEquals(monthList.get(0).getWorkKbnName(), resultList.get(0).getWorkKbnName());
		// ｼﾌﾄ
		assertEquals(monthList.get(0).getShift(), resultList.get(0).getShiftCode());
		// 出勤
		assertEquals(monthList.get(0).getWorkSTime(), CostDateUtils.formatIntegerToTime(resultList.get(0).getWorkSTime()));
		// 退勤
		assertEquals(monthList.get(0).getWorkETime(), CostDateUtils.formatIntegerToTime(resultList.get(0).getWorkETime()));
		// 休暇時間数
		assertEquals(monthList.get(0).getRestHours(), resultList.get(0).getRestHours());
		// 勤務時間数
		assertEquals(monthList.get(0).getWorkHours(), resultList.get(0).getWorkHours());
		// 超勤開始
		assertEquals(monthList.get(0).getChoSTime(), CostDateUtils.formatIntegerToTime(resultList.get(0).getOverSTime()));
		// 超勤終了
		assertEquals(monthList.get(0).getChoETime(), CostDateUtils.formatIntegerToTime(resultList.get(0).getOverETime()));
		// 超勤平増
		assertEquals(monthList.get(0).getChoWeekday(), resultList.get(0).getOverHours());
		// 超勤平常
		assertEquals(monthList.get(0).getChoWeekday(), resultList.get(0).getOverHoursOrdinary());
		// 超勤休日
		assertEquals(monthList.get(0).getChoHoliday(), resultList.get(0).getOverHoursHoliday());
		// 超勤深夜
		assertEquals(monthList.get(0).getmNHours(), resultList.get(0).getOverHoursNight());
		// ﾛｹｰｼｮﾝコード
		assertEquals(monthList.get(0).getLocationCode(), resultList.get(0).getLocationCode());
		// ﾛｹｰｼｮﾝ名
		assertEquals(monthList.get(0).getLocationName(), resultList.get(0).getLocationName());
		

		
		// 勤務区分
		assertEquals(monthList.get(1).getWorkKbn(), resultList.get(1).getWorkKbn());
		// 勤務区分名
		assertEquals(monthList.get(1).getWorkKbnName(), resultList.get(1).getWorkKbnName());
		// ｼﾌﾄ
		assertEquals(monthList.get(1).getShift(), resultList.get(1).getShiftCode());
		// 出勤
		assertEquals(monthList.get(1).getWorkSTime(), CostDateUtils.formatIntegerToTime(resultList.get(1).getWorkSTime()));
		// 退勤
		assertEquals(monthList.get(1).getWorkETime(), CostDateUtils.formatIntegerToTime(resultList.get(1).getWorkETime()));
		// 休暇時間数
		assertEquals(monthList.get(1).getRestHours(), resultList.get(1).getRestHours());
		// 勤務時間数
		assertEquals(monthList.get(1).getWorkHours(), resultList.get(1).getWorkHours());
		// 超勤開始
		assertEquals(monthList.get(1).getChoSTime(), CostDateUtils.formatIntegerToTime(resultList.get(1).getOverSTime()));
		// 超勤終了
		assertEquals(monthList.get(1).getChoETime(), CostDateUtils.formatIntegerToTime(resultList.get(1).getOverETime()));
		// 超勤平増
		assertEquals(monthList.get(1).getChoWeekday(), resultList.get(1).getOverHours());
		// 超勤平常
		assertEquals(monthList.get(1).getChoWeekday(), resultList.get(1).getOverHoursOrdinary());
		// 超勤休日
		assertEquals(monthList.get(1).getChoHoliday(), resultList.get(1).getOverHoursHoliday());
		// 超勤深夜
		assertEquals(monthList.get(1).getmNHours(), resultList.get(1).getOverHoursNight());
		// ﾛｹｰｼｮﾝコード
		assertEquals(monthList.get(1).getLocationCode(), resultList.get(1).getLocationCode());
		// ﾛｹｰｼｮﾝ名
		assertEquals(monthList.get(1).getLocationName(), resultList.get(1).getLocationName());
		
	}
}
