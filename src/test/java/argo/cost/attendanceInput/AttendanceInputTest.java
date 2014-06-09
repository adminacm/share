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
		// 休日勤務情報フラグ
		assertEquals(form.getKinmuKun().toString(), "1");
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
