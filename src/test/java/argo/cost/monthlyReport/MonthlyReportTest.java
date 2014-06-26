package argo.cost.monthlyReport;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.monthlyReport.model.MonthlyReportForm;
import argo.cost.monthlyReport.service.MonthlyReportService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext_test.xml"}) 
public class MonthlyReportTest {

	// 月報サビース
	@Autowired
	MonthlyReportService service;

	/**
	 * 年月取得処理をテスト
	 * @throws ParseException 
	 */
	@Test
	public void testGetYearMonth() throws ParseException{
		
		// 現在日付
		String yyyymmdd = "20140401";
		
		// 年月設定
		String yearMonth = service.getDateFormat(CostDateUtils.toDate(yyyymmdd));
		
		// 年月
		assertEquals(yearMonth, "2014年04月");
	}
	
	/**
	 * 月報一覧取得処理
	 */
	@Test
	public void testGetMonReList() {
		
		// 現在日付
		Date date = null;
		try {
			date = CostDateUtils.toDate("20140201");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 月報一覧取得
		List<MonthlyReportDispVO> monReListList = service.getMonthyReportList(date);
		
		// 月報リストのサイズ
		assertEquals(28, monReListList.size());
	}

	/**
	 * 年月の変換処理をテスト
	 * 
	 * 年月の←ボタンを押すと、前の月に表示が切り替わる
	 */
	@Test
	public void testChangeYearMonth1() {
		
		// 変換フラグ
		String changeFlg = "last";
		
		// 月
		String date = "20140201";
		
		// 変換後の年月
		String formatDate = null;
		try {
			formatDate = service.changeYearMonth(changeFlg, date);
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
	public void testChangeYearMonth2() {
		
		// 変換フラグ
		String changeFlg = "next";
		
		// 現在日付
		String date = "20140201";
		
		// 変換後の年月
		String formatDate = null;
		try {
			formatDate = service.changeYearMonth(changeFlg, date);
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
	public void testGetUserMonth() {
		
		// ユーザの最後の月報提出年月を取得
		String userMonth = service.getUserLatestShinseiMonth("4001");
		
		// 年月
		assertEquals(userMonth, "20140601");
	}
	
	/**
	 * 月報リスト設定をテスト
	 * @throws ParseException 
	 */
	@Test
	public void testSetUserMonthReport() throws ParseException {
		
		MonthlyReportForm form = new MonthlyReportForm();
		
		String userId = "4001";
		String date = "20140601";
		List<MonthlyReportDispVO> monthList = service.getMonthyReportList(CostDateUtils.toDate(date));
		form.setmRList(monthList);
		
		// ユーザの月報情報を取得し、月報リストに設定する
		service.setUserMonthReport(userId, date, form);

		assertEquals(monthList.size(), 31);
		
		for (MonthlyReportDispVO info : monthList) {
			
			if (StringUtils.equals(info.getDate(), "20140622")) {
				assertEquals(info.getChoETime(), null);   // 超勤終了時刻
				assertEquals(info.getChoSTime(), null);   // 超勤開始時刻
				assertEquals(info.getChoHoliday(), new Double(0.0));   // 超勤休日
				assertEquals(info.getChoWeekday(), new Double(0.0));   // 超勤平日割増
				assertEquals(info.getChoWeekdayNomal(), new Double(0.0));   // 超勤平日通常
				assertEquals(info.getDay(), "22");   // 勤務日：日
				assertEquals(info.getKyukaKb(), null);   // 休暇欠勤区分
				assertEquals(info.getLocationCode(), "01");   // ロケーションコード
				assertEquals(info.getLocationName(), "中国");   // ロケーション名
				assertEquals(info.getmNHours(), new Double(0.0));   // 深夜
				assertEquals(info.getRestHours(), new Double(0.0));   // 休暇時間数
				assertEquals(info.getShift(), "090075");   // シフトコード
				assertEquals(info.getTotleFlg(), null);   // 合計フラグ
				assertEquals(info.getWeek(), "日");   // 休日名
				assertEquals(info.getWorkETime(), "1730");   // 勤務終了時刻
				assertEquals(info.getWorkSTime(), "0900");   // 勤務開始時刻
				assertEquals(info.getWorkHours(), new Double(7.5));   // 勤務時間数
				assertEquals(info.getWorkKbn(), "02");   // 勤務日区分
				assertEquals(info.getWorkKbnName(), "休日");   // 勤務日区分名
			}
			
			if (StringUtils.equals(info.getDate(), "20140625")) {
				assertEquals(info.getChoETime(), null);   // 超勤終了時刻
				assertEquals(info.getChoSTime(), null);   // 超勤開始時刻
				assertEquals(info.getChoHoliday(), new Double(0.0));   // 超勤休日
				assertEquals(info.getChoWeekday(), new Double(0.0));   // 超勤平日割増
				assertEquals(info.getChoWeekdayNomal(), new Double(0.0));   // 超勤平日通常
				assertEquals(info.getDay(), "25");   // 勤務日：日
				assertEquals(info.getKyukaKb(), null);   // 休暇欠勤区分
				assertEquals(info.getLocationCode(), "01");   // ロケーションコード
				assertEquals(info.getLocationName(), "中国");   // ロケーション名
				assertEquals(info.getmNHours(), new Double(0.0));   // 深夜
				assertEquals(info.getRestHours(), new Double(0.0));   // 休暇時間数
				assertEquals(info.getShift(), "090075");   // シフトコード
				assertEquals(info.getTotleFlg(), null);   // 合計フラグ
				assertEquals(info.getWeek(), "水");   // 勤務日名
				assertEquals(info.getWorkETime(), "1730");   // 勤務終了時刻
				assertEquals(info.getWorkSTime(), "0900");   // 勤務開始時刻
				assertEquals(info.getWorkHours(), new Double(7.5));   // 勤務時間数
				assertEquals(info.getWorkKbn(), "01");   // 勤務日区分
				assertEquals(info.getWorkKbnName(), "出勤");   // 勤務日区分名
			}
		}
	}
}
