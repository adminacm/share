package argo.cost.attendanceOnHolidayRecordDetail;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;
import argo.cost.attendanceOnHolidayRecordDetail.service.AttendanceOnHolidayRecordDetailServiceImpl;
import argo.cost.common.dao.BaseDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"}) 
public class AttendanceOnHolidayRecordDetailTest {
	
	// 休日出勤管理詳細
	@Resource
	AttendanceOnHolidayRecordDetailServiceImpl serviceImpl;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 休日出勤管理詳細取得をテスト１
	 */
	@Test
	public void testGetAttendanceOnHolidayRecordDetail1() {

		// 日付
		String date = "2014/05/04";
		// 勤務区分
		String workKbn = "03";
		
		AttendanceOnHolidayRecordDetailForm form = new AttendanceOnHolidayRecordDetailForm();
		// ユーザIDを設定する
		form.setUserId("4001");
		// 対象ユーザID
		form.setTaishoUserId("4001");
		// 対象ユーザ氏名
		form.setTaishoUserName("０１ＰＴＳ");
		try {
			serviceImpl.getAttendanceOnHolidayRecordDetail(form, date, workKbn);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 日付
		assertEquals(form.getHolidayWorkDate(), "2014/05/04");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日振替勤務");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "17:30");
		// 振替日
		assertEquals(form.getExchangeDate(), "2014/05/08");
		// プロジェクト名
		assertEquals(form.getProjectName(), "BTMU人事");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
	}

	/**
	 * 休日出勤管理詳細取得をテスト２
	 */
	@Test
	public void testGetAttendanceOnHolidayRecordDetail2() {

		// 日付
		String date = "2014/05/18";
		// 勤務区分
		String workKbn = "02";

		AttendanceOnHolidayRecordDetailForm form = new AttendanceOnHolidayRecordDetailForm();
		// ユーザIDを設定する
		form.setUserId("4001");
		// 対象ユーザID
		form.setTaishoUserId("4001");
		// 対象ユーザ氏名
		form.setTaishoUserName("０１ＰＴＳ");
		try {
			serviceImpl.getAttendanceOnHolidayRecordDetail(form, date, workKbn);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 日付
		assertEquals(form.getHolidayWorkDate(), "2014/05/18");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "17:30");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/07/31");
		// プロジェクト名
		assertEquals(form.getProjectName(), "【PRIME】共通マスタ管理機能");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
		
	}

	/**
	 * 休日出勤管理詳細取得をテスト３
	 */
	@Test
	public void testGetAttendanceOnHolidayRecordDetail3() {
		
		// 日付
		String date = "2014/04/13";
		// 勤務区分
		String workKbn = "02";

		AttendanceOnHolidayRecordDetailForm form = new AttendanceOnHolidayRecordDetailForm();

		// ユーザIDを設定する
		form.setUserId("4001");
		// 対象ユーザID
		form.setTaishoUserId("4001");
		// 対象ユーザ氏名
		form.setTaishoUserName("０１ＰＴＳ");
		try {
			serviceImpl.getAttendanceOnHolidayRecordDetail(form, date, workKbn);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 日付
		assertEquals(form.getHolidayWorkDate(), "2014/04/13");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "17:30");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/06/30");
		// 代休日
		assertEquals(form.getTurnedHolidayDate(), "2014/04/18");
		// プロジェクト名
		assertEquals(form.getProjectName(), "MCDDMBプロジェクト支援");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
		
	}

	/**
	 * 休日出勤管理詳細取得をテスト４
	 */
	@Test
	public void testGetAttendanceOnHolidayRecordDetail4() {

		// 日付
		String date = "2014/05/10";
		// 勤務区分
		String workKbn = "02";

		AttendanceOnHolidayRecordDetailForm form = new AttendanceOnHolidayRecordDetailForm();
		// ユーザIDを設定する
		form.setUserId("4001");
		// 対象ユーザID
		form.setTaishoUserId("4001");
		// 対象ユーザ氏名
		form.setTaishoUserName("０１ＰＴＳ");
		try {
			serviceImpl.getAttendanceOnHolidayRecordDetail(form, date, workKbn);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 日付
		assertEquals(form.getHolidayWorkDate(), "2014/05/10");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "19:30");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/07/31");
		// 超勤振替申請日
		assertEquals(form.getOverWorkTurnedReqDate(), "2014/05/20");
		// プロジェクト名
		assertEquals(form.getProjectName(), "MUIT資産活用・口振");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
	}
	
	/**
	 * 
	 */
	@Test
	public void testOverWorkPayRequest() {
		
		AttendanceOnHolidayRecordDetailForm form = new AttendanceOnHolidayRecordDetailForm();
		
		// ユーザID
		form.setUserId("4001");
		// 対象社員番号
		form.setTaishoUserId("4001");
		// 日付
		form.setHolidayWorkDate("2014/05/24");
		
		try {
			serviceImpl.overWorkPayRequest(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
