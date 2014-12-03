package argo.cost.attendanceInput;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.service.AttendanceInputServiceImpl;
import argo.cost.common.service.ComService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext_test.xml"}) 
public class AttendanceInputTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	// 勤怠入力
	@Resource
	AttendanceInputServiceImpl attS;

	// 共通
	@Resource
	ComService comService;

	/**
	 * 日付の変換処理をテスト1
	 */
	@Test
	public void testChangeYearMonth1(){

		// 変換フラグ
		String changeFlg = "last";
		
		// 現在日付
		String date = "20140205";
		
		// 変換後の年月
		String formatDate = null;
		try {
			formatDate = attS.changeDate(changeFlg, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 変換後日付
		assertEquals(formatDate, "20140204");
	}

	/**
	 * 日付の変換処理をテスト2
	 */
	@Test
	public void testChangeYearMonth2(){
		
		// 変換フラグ
		String changeFlg = "next";
		
		// 現在日付
		String date = "20140205";
		
		// 変換後の年月
		String formatDate = null;
		try {
			formatDate = attS.changeDate(changeFlg, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 変換後日付
		assertEquals(formatDate, "20140206");
	}

	/**
	 * 勤怠入力画面情報設定をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 */
	@Test
	public void testSetAttForm001() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4001");
		// 勤務日付
		String date = "20140328";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140328");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年03月28日（金）");
		// シフトコード
		assertEquals(form.getShiftCd(), "0900");
		// 勤務開始時刻
		assertEquals(form.getWorkSHour(), null);
		assertEquals(form.getWorkSMinute(), null);
		// 勤務終了時刻
		assertEquals(form.getWorkEHour(), null);
		assertEquals(form.getWorkEMinute(), null);
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 9);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 1);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}

	/**
	 * 勤怠入力画面情報設定をテスト
	 * 
	 * 勤怠情報が存在する、
	 * 		勤務日区分：出勤
	 * 		超勤：無し
	 */
	@Test
	public void testSetAttForm002() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4001");
		// 勤務日付
		String date = "20140531";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140531");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年05月31日（土）");
		// 勤務日区分
		assertEquals(form.getWorkDayKbn(), "01");
		// 勤務日区分名
		assertEquals(form.getWorkDayKbnName(), "出勤");
		// シフトコード
		assertEquals(form.getShiftCd(), "0900");
		// 勤務開始時刻
		assertEquals(form.getWorkSTime(), "0900");
		assertEquals(form.getWorkSHour(), "09");
		assertEquals(form.getWorkSMinute(), "00");
		// 勤務終了時刻
		assertEquals(form.getWorkETime(), "1730");
		assertEquals(form.getWorkEHour(), "17");
		assertEquals(form.getWorkEMinute(), "30");
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), new Double(0.0));
		// 勤務時間数
		assertEquals(form.getWorkHours(), new Double(7.5));
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), new Double(0.0));
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), new Double(0.0));
		// 超勤休日
		assertEquals(form.getChoHoliday(), new Double(0.0));
		// 深夜
		assertEquals(form.getmNHours(), new Double(0.0));
		// ロケーション情報
		assertEquals(form.getLocationId(), "02");
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 9);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 1);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}

	/**
	 * 勤怠入力画面情報設定をテスト
	 * 
	 * 勤怠情報が存在する、
	 * 		勤務日区分：02休日
	 * 		超勤：無し
	 */
	@Test
	public void testSetAttForm003() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4002");
		// 勤務日付
		String date = "20140601";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140601");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年06月01日（日）");
		// 勤務日区分
		assertEquals(form.getWorkDayKbn(), "02");
		// 勤務日区分名
		assertEquals(form.getWorkDayKbnName(), "休日");
		// シフトコード
		assertEquals(form.getShiftCd(), null);
		// 勤務開始時刻
		assertEquals(form.getWorkSTime(), null);
		assertEquals(form.getWorkSHour(), null);
		assertEquals(form.getWorkSMinute(), null);
		// 勤務終了時刻
		assertEquals(form.getWorkETime(), null);
		assertEquals(form.getWorkEHour(), null);
		assertEquals(form.getWorkEMinute(), null);
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// ロケーション情報
		assertEquals(form.getLocationId(), null);
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 9);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 0);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}

	/**
	 * 勤怠入力画面情報設定をテスト
	 * 
	 * 勤怠情報が存在する、
	 * 		勤務日区分：03:休日振替勤務
	 * 		超勤：有る
	 */
	@Test
	public void testSetAttForm004() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4003");
		// 勤務日付
		String date = "20140601";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140601");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年06月01日（日）");
		// 勤務日区分
		assertEquals(form.getWorkDayKbn(), "03");
		// 勤務日区分名
		assertEquals(form.getWorkDayKbnName(), "休日振替勤務");
		// シフトコード
		assertEquals(form.getShiftCd(), "0900");
		// 勤務開始時刻
		assertEquals(form.getWorkSTime(), "0900");
		assertEquals(form.getWorkSHour(), "09");
		assertEquals(form.getWorkSMinute(), "00");
		// 勤務終了時刻
		assertEquals(form.getWorkETime(), "2330");
		assertEquals(form.getWorkEHour(), "23");
		assertEquals(form.getWorkEMinute(), "30");
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), new Double(0.0));
		// 勤務時間数
		assertEquals(form.getWorkHours(), new Double(12.0));
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), "1800");
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), "2330");
		// 平日割増
		assertEquals(form.getChoWeekday(), new Double(4.5));
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), new Double(0.0));
		// 超勤休日
		assertEquals(form.getChoHoliday(), new Double(0.0));
		// 深夜
		assertEquals(form.getmNHours(), new Double(0.5));
		// ロケーション情報
		assertEquals(form.getLocationId(), "02");
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 9);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 1);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}

	/**
	 * 勤怠入力画面情報設定をテスト
	 * 
	 * 勤怠情報が存在する、
	 * 		勤務日区分：04:振替休日
	 * 		超勤：無し
	 */
	@Test
	public void testSetAttForm005() {
		   
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4004");
		// 勤務日付
		String date = "20140603";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140603");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年06月03日（火）");
		// 勤務日区分
		assertEquals(form.getWorkDayKbn(), "04");
		// 勤務日区分名
		assertEquals(form.getWorkDayKbnName(), "振替休日");
		// シフトコード
		assertEquals(form.getShiftCd(), null);
		// 勤務開始時刻
		assertEquals(form.getWorkSTime(), null);
		assertEquals(form.getWorkSHour(), null);
		assertEquals(form.getWorkSMinute(), null);
		// 勤務終了時刻
		assertEquals(form.getWorkETime(), null);
		assertEquals(form.getWorkEHour(), null);
		assertEquals(form.getWorkEMinute(), null);
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// ロケーション情報
		assertEquals(form.getLocationId(), null);
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 9);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 0);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}

	/**
	 * 勤怠入力画面情報設定をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		勤務日区分：02:休日
	 * 		超勤：無し
	 */
	@Test
	public void testSetAttForm006() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4001");
		// 勤務日付
		String date = "20140614";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140614");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年06月14日（土）");
		// 勤務日区分
		assertEquals(form.getWorkDayKbn(), "02");
		// 勤務日区分名
		assertEquals(form.getWorkDayKbnName(), "休日");
		// シフトコード
		assertEquals(form.getShiftCd(), "0900");
		// 勤務開始時刻
		assertEquals(form.getWorkSHour(), null);
		assertEquals(form.getWorkSMinute(), null);
		// 勤務終了時刻
		assertEquals(form.getWorkEHour(), null);
		assertEquals(form.getWorkEMinute(), null);
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 9);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 1);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}

	/**
	 * 勤怠入力画面情報設定をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		勤務日区分：03:休日振替勤務（休日で、振替勤務があります）
	 * 		超勤：無し
	 */
	@Test
	public void testSetAttForm007() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4005");
		// 勤務日付
		String date = "20140614";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140614");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年06月14日（土）");
		// 勤務日区分
		assertEquals(form.getWorkDayKbn(), "03");
		// 勤務日区分名
		assertEquals(form.getWorkDayKbnName(), "休日振替勤務");
		// シフトコード
		assertEquals(form.getShiftCd(), "0900");
		// 勤務開始時刻
		assertEquals(form.getWorkSHour(), null);
		assertEquals(form.getWorkSMinute(), null);
		// 勤務終了時刻
		assertEquals(form.getWorkEHour(), null);
		assertEquals(form.getWorkEMinute(), null);
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 9);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 1);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}

	/**
	 * 勤怠入力画面情報設定をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		勤務日区分：04:振替休日（勤務日で、振替休日を指定される）
	 * 		超勤：無し
	 */
	@Test
	public void testSetAttForm008() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4006");
		// 勤務日付
		String date = "20140613";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140613");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年06月13日（金）");
		// 勤務日区分
		assertEquals(form.getWorkDayKbn(), "04");
		// 勤務日区分名
		assertEquals(form.getWorkDayKbnName(), "振替休日");
		// シフトコード
		assertEquals(form.getShiftCd(), "0900");
		// 勤務開始時刻
		assertEquals(form.getWorkSHour(), null);
		assertEquals(form.getWorkSMinute(), null);
		// 勤務終了時刻
		assertEquals(form.getWorkEHour(), null);
		assertEquals(form.getWorkEMinute(), null);
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 9);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 1);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}
	
	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：0911が存在しない
	 */
	@Test
	public void testCalcWorkingRec001() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0911");   // シフトコード(存在しない)
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "シフトコードを正しく入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「Null」
	 * 		勤務開始時刻：「09AA」
	 */
	@Test
	public void testCalcWorkingRec002() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd(null);     // シフトコード
		form.setWorkSHour("09");
		form.setWorkSMinute("AA");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "勤務開始時刻を正しく入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「NULL」
	 * 		勤務終了時刻：「NULL」
	 * 		勤務区分:"01"(出勤)
	 * 		休暇欠勤区分:「NULL」
	 */
	@Test
	public void testCalcWorkingRec003() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setKyukaKb(null);
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "勤怠が未入力です");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「NULL」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「"02"（半休(有給休暇)）」
	 */
	@Test
	public void testCalcWorkingRec004() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setKyukaKb("02");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "勤務開始時刻・終了時刻を入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「NULL」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「"05"(代休)」
	 * 		休日勤務管理から代休取得期限が対象日以前で代休日がNULLのレコードが取得できない
	 */
	@Test
	public void testCalcWorkingRec005() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setKyukaKb("05");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "取得できる代休はありません");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「NULL」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「01」
	 */
	@Test
	public void testCalcWorkingRec006() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setKyukaKb("01");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "勤務開始時刻・終了時刻は両方入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0800」
	 * 		勤務終了時刻：「1730」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「01」
	 */
	@Test
	public void testCalcWorkingRec007() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("08");
		form.setWorkSMinute("00");
		form.setWorkEHour("17");
		form.setWorkEMinute("30");
		form.setKyukaKb("01");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "勤務開始時刻は定時勤務時間内の時刻を入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「1000」
	 * 		勤務終了時刻：「1000」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「」
	 */
	@Test
	public void testCalcWorkingRec008() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("10");
		form.setWorkSMinute("00");
		form.setWorkEHour("10");
		form.setWorkEMinute("00");
		form.setKyukaKb(null);
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "勤務終了時刻は勤務開始時刻より後の時刻を入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「1000」
	 * 		勤務終了時刻：「1111」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「」
	 */
	@Test
	public void testCalcWorkingRec009() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("10");
		form.setWorkSMinute("00");
		form.setWorkEHour("11");
		form.setWorkEMinute("11");
		form.setKyukaKb(null);
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "勤務終了時刻は30分単位で入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「1011」
	 * 		勤務終了時刻：「1100」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「」
	 */
	@Test
	public void testCalcWorkingRec010() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("10");
		form.setWorkSMinute("11");
		form.setWorkEHour("11");
		form.setWorkEMinute("00");
		form.setKyukaKb(null);
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "勤務開始時刻は30分単位で入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「1100」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「」
	 */
	@Test
	public void testCalcWorkingRec011() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setWorkEHour("11");
		form.setWorkEMinute("00");
		form.setKyukaKb(null);
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "定時時間帯の勤務時間数が7.5h未満です。休暇区分も入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「1100」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「"02"(半休(有給休暇))」
	 */
	@Test
	public void testCalcWorkingRec012() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setWorkEHour("11");
		form.setWorkEMinute("00");
		form.setKyukaKb("02");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "正しい休暇区分を入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「1500」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「"02"(半休(有給休暇))」
	 */
	@Test
	public void testCalcWorkingRec013() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setWorkEHour("15");
		form.setWorkEMinute("00");
		form.setKyukaKb("02");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "有給休暇が余分に取得されています");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「1500」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「"01"(全休(有給休暇))」
	 */
	@Test
	public void testCalcWorkingRec014() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setWorkEHour("15");
		form.setWorkEMinute("00");
		form.setKyukaKb("01");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "終日休暇の日は勤務できません");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「2000」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「"02"(半休(有給休暇))」
	 */
	@Test
	public void testCalcWorkingRec015() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setWorkEHour("20");
		form.setWorkEMinute("00");
		form.setKyukaKb("02");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "休暇欠勤区分が入力されています");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「1000」
	 * 		勤務終了時刻：「2000」
	 * 		勤務区分:「"02"(休日)」
	 * 		休暇欠勤区分:「」
	 */
	@Test
	public void testCalcWorkingRec016() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("02");
		form.setWorkSHour("10");
		form.setWorkSMinute("00");
		form.setWorkEHour("20");
		form.setWorkEMinute("00");
		form.setKyukaKb(null);
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), null);
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), null);
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "休日の勤務開始は定時出勤時刻を入力してください");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「1700」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「"03"(時間休(有給休暇))」
	 */
	@Test
	public void testCalcWorkingRec017() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setWorkEHour("17");
		form.setWorkEMinute("00");
		form.setKyukaKb("03");
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), new Double(7.0));
		// 休暇時間数
		assertEquals(form.getKyukaHours(), new Double(1.0));
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), null);
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), null);
		// 平日割増
		assertEquals(form.getChoWeekday(), null);
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), null);
		// 超勤休日
		assertEquals(form.getChoHoliday(), null);
		// 深夜
		assertEquals(form.getmNHours(), new Double(0.0));
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "");
	}

	/**
	 * 勤怠入力画面各種値算出をテスト
	 * 
	 * 勤怠情報が存在しない場合、画面を初期化する
	 * 		シフトコード：「0900」
	 * 		勤務開始時刻：「0900」
	 * 		勤務終了時刻：「2330」
	 * 		勤務区分:「"01"(出勤)」
	 * 		休暇欠勤区分:「」
	 */
	@Test
	public void testCalcWorkingRec018() {
		
		AttendanceInputForm form = new AttendanceInputForm();
		form.setAttDate("20140611");
		form.setUserId("4001");    // 社員番号
		form.setShiftCd("0900");     // シフトコード
		form.setWorkDayKbn("01");
		form.setWorkSHour("09");
		form.setWorkSMinute("00");
		form.setWorkEHour("23");
		form.setWorkEMinute("30");
		form.setKyukaKb(null);
		
		// 各種値算出
		try {
			attS.calcWorkingRec(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤務時間数
		assertEquals(form.getWorkHours(), new Double(12.0));
		// 休暇時間数
		assertEquals(form.getKyukaHours(), null);
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), "1800");
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), "2330");
		// 平日割増
		assertEquals(form.getChoWeekday(), new Double(4.5));
		// 平日通常
		assertEquals(form.getChoWeekdayNomal(), new Double(0.0));
		// 超勤休日
		assertEquals(form.getChoHoliday(), new Double(0.0));
		// 深夜
		assertEquals(form.getmNHours(), new Double(0.5));
		// 一番のメッセージ
		assertEquals(form.getConfirmMsg(), "");
	}
//
//	/**
//	 * 就業データを取得をテスト
//	 */
//	@Test
//	public void testUpdateAttdendanceInfo(){
//		
//		AttendanceInputForm form = new AttendanceInputForm();
//
//		form.setWorkSTime("1000");
//		form.setWorkETime("2100");
//		Integer updateFlg = attS.updateAttdendanceInfo(form);
//		
//		// ユーザ作業情報
//		assertEquals(updateFlg, Integer.valueOf(1));
//	}
}
