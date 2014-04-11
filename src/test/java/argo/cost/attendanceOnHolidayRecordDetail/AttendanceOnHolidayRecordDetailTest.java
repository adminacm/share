package argo.cost.attendanceOnHolidayRecordDetail;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;
import argo.cost.attendanceOnHolidayRecordDetail.service.AttendanceOnHolidayRecordDetailServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class AttendanceOnHolidayRecordDetailTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	// 休暇管理
	@Resource
	AttendanceOnHolidayRecordDetailServiceImpl serviceImpl;

	/**
	 * 休日出勤管理詳細取得をテスト１
	 */
	@Test
	public void testAttendanceOnHolidayRecordDetail1() {
		
		// ユーザＩＤ
		String userId = "067";
		// 日付
		String date = "2014/5/5";
		// 勤務区分
		String workKbn = "03";
		
		AttendanceOnHolidayRecordDetailForm form = serviceImpl.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		
		// 日付
		assertEquals(form.getDate(), "2014/5/5");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日振替勤務");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "9:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "19:00");
		// 振替日
		assertEquals(form.getExchangeDate(), "2014/5/9");
		// プロジェクト名
		assertEquals(form.getProjectName(), "経費管理システム運用");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
		
	}

	/**
	 * 休日出勤管理詳細取得をテスト２
	 */
	@Test
	public void testAttendanceOnHolidayRecordDetail2() {
		
		// ユーザＩＤ
		String userId = "067";
		// 日付
		String date = "2014/5/18";
		// 勤務区分
		String workKbn = "01";
		
		AttendanceOnHolidayRecordDetailForm form = serviceImpl.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		
		// 日付
		assertEquals(form.getDate(), "2014/5/18");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "出勤");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "9:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "19:00");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/7/31");
		// プロジェクト名
		assertEquals(form.getProjectName(), "経費管理システム運用");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
		
	}

	/**
	 * 休日出勤管理詳細取得をテスト３
	 */
	@Test
	public void testAttendanceOnHolidayRecordDetail3() {
		
		// ユーザＩＤ
		String userId = "067";
		// 日付
		String date = "2014/4/13";
		// 勤務区分
		String workKbn = "01";
		
		AttendanceOnHolidayRecordDetailForm form = serviceImpl.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		
		// 日付
		assertEquals(form.getDate(), "2014/4/13");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "出勤");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "9:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "19:00");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/6/30");
		// 代休日
		assertEquals(form.getTurnedHolidayDate(), "2014/4/18");
		// プロジェクト名
		assertEquals(form.getProjectName(), "経費管理システム運用");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
		
	}

	/**
	 * 休日出勤管理詳細取得をテスト４
	 */
	@Test
	public void testAttendanceOnHolidayRecordDetail4() {
		
		// ユーザＩＤ
		String userId = "067";
		// 日付
		String date = "2014/5/10";
		// 勤務区分
		String workKbn = "01";
		
		AttendanceOnHolidayRecordDetailForm form = serviceImpl.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		
		// 日付
		assertEquals(form.getDate(), "2014/5/10");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "出勤");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "9:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "19:00");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/7/31");
		// 超勤振替申請日
		assertEquals(form.getOverWorkTurnedReqDate(), "2014/7/15");
		// プロジェクト名
		assertEquals(form.getProjectName(), "経費管理システム運用");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
	}
	
	/**
	 * 超勤振替申請提出をテスト
	 */
	@Test
	public void testOverWorkPayRequest() {

		AttendanceOnHolidayRecordDetailForm form = new AttendanceOnHolidayRecordDetailForm();
		
		form.setDate("2014/05/05");
		
		Integer resultFlg = serviceImpl.overWorkPayRequest(form);

		assertEquals(resultFlg, Integer.valueOf(1));
		
	}

}
