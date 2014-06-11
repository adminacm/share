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
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.ProjectMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.entity.WorkDayKbnMaster;

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
	public void testgetAttendanceOnHolidayRecordDetail1() {

		// 社員番号
		Users users = new Users();
		users.setId("4002");
		// 勤務日区分コード
		WorkDayKbnMaster workDayKbnMaster = new WorkDayKbnMaster();
		workDayKbnMaster.setCode("03");
		// プロジェクトコード
		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster.setCode("00000000000001");
		
		// 休日勤務予定情報
		HolidayAtendanceYotei yoteiInfo = new HolidayAtendanceYotei();
		// 勤務日区分コード
		yoteiInfo.setWorkDayKbnMaster(workDayKbnMaster);
		// 社員番号
		yoteiInfo.setUser(users);
		// プロジェクトコード
		yoteiInfo.setProjectMaster(projectMaster);
		// 予定出勤休日日付
		yoteiInfo.setAtendanceBookDate("20140501");
		//勤務開始時刻
		yoteiInfo.setKinmuStartTime("0900");
		//勤務終了時刻
		yoteiInfo.setKinmuEndTime("1730");
		// 振替日
		yoteiInfo.setFurikaeDate("20140505");
		// 業務内容
		yoteiInfo.setCommont("トラブル対応");
		
		baseDao.insert(yoteiInfo);
		
		// ユーザＩＤ
		String userId = "4002";
		// 日付
		String date = "2014/05/01";
		// 勤務区分
		String workKbn = "03";
		
		AttendanceOnHolidayRecordDetailForm form = null;
		try {
			form = serviceImpl.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 日付
		assertEquals(form.getDate(), "2014/05/01");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日振替勤務");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "17:30");
		// 振替日
		assertEquals(form.getExchangeDate(), "2014/05/05");
		// プロジェクト名
		assertEquals(form.getProjectName(), "林大学留学生管理システム保守");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
	}

	/**
	 * 休日出勤管理詳細取得をテスト２
	 */
	@Test
	public void testgetAttendanceOnHolidayRecordDetail2() {

		// 休日勤務予定情報
		HolidayAtendanceYotei yoteiInfo = new HolidayAtendanceYotei();
		WorkDayKbnMaster workDayKbnMaster = new WorkDayKbnMaster();
		workDayKbnMaster.setCode("02");
		yoteiInfo.setWorkDayKbnMaster(workDayKbnMaster);
		Users users = new Users();
		users.setId("4002");
		yoteiInfo.setUser(users);
		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster.setCode("00000000000003");
		yoteiInfo.setProjectMaster(projectMaster);
		yoteiInfo.setAtendanceBookDate("20140518");
		yoteiInfo.setKinmuStartTime("0900");
		yoteiInfo.setKinmuEndTime("1730");
		yoteiInfo.setCommont("トラブル対応");
		
		baseDao.insert(yoteiInfo);
		
		// 休日勤務情報
		HolidayAtendance holidayAtendanceInfo = new HolidayAtendance();
		holidayAtendanceInfo.setHolidaySyukinDate("20140518");
		holidayAtendanceInfo.setDaikyuGetShimekiriDate("20140731");
		holidayAtendanceInfo.setUsers(users);

		baseDao.insert(holidayAtendanceInfo);
		
		// ユーザＩＤ
		String userId = "4002";
		// 日付
		String date = "2014/05/18";
		// 勤務区分
		String workKbn = "02";
		
		AttendanceOnHolidayRecordDetailForm form = null;
		try {
			form = serviceImpl.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 日付
		assertEquals(form.getDate(), "2014/05/18");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "17:30");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/07/31");
		// プロジェクト名
		assertEquals(form.getProjectName(), "KKF基幹システム再構築");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
		
	}

	/**
	 * 休日出勤管理詳細取得をテスト３
	 */
	@Test
	public void testgetAttendanceOnHolidayRecordDetail3() {
		
		// 休日勤務予定情報
		HolidayAtendanceYotei yoteiInfo = new HolidayAtendanceYotei();
		WorkDayKbnMaster workDayKbnMaster = new WorkDayKbnMaster();
		workDayKbnMaster.setCode("02");
		yoteiInfo.setWorkDayKbnMaster(workDayKbnMaster);
		Users users = new Users();
		users.setId("4002");
		yoteiInfo.setUser(users);
		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster.setCode("00000000000001");
		yoteiInfo.setProjectMaster(projectMaster);
		yoteiInfo.setAtendanceBookDate("20140413");
		yoteiInfo.setKinmuStartTime("0900");
		yoteiInfo.setKinmuEndTime("1900");
		yoteiInfo.setCommont("トラブル対応");
		
		baseDao.insert(yoteiInfo);
		
		// 休日勤務情報
		HolidayAtendance holidayAtendanceInfo = new HolidayAtendance();
		holidayAtendanceInfo.setHolidaySyukinDate("20140413");
		holidayAtendanceInfo.setDaikyuGetShimekiriDate("20140630");
		holidayAtendanceInfo.setDaikyuDate("20140418");
		holidayAtendanceInfo.setUsers(users);

		baseDao.insert(holidayAtendanceInfo);
		
		// ユーザＩＤ
		String userId = "4002";
		// 日付
		String date = "2014/04/13";
		// 勤務区分
		String workKbn = "01";
		
		AttendanceOnHolidayRecordDetailForm form = null;
		try {
			form = serviceImpl.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 日付
		assertEquals(form.getDate(), "2014/04/13");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "19:00");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/06/30");
		// 代休日
		assertEquals(form.getTurnedHolidayDate(), "2014/04/18");
		// プロジェクト名
		assertEquals(form.getProjectName(), "林大学留学生管理システム保守");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
		
	}

	/**
	 * 休日出勤管理詳細取得をテスト４
	 */
	@Test
	public void testgetAttendanceOnHolidayRecordDetail4() {
		
		// 休日勤務予定情報
		HolidayAtendanceYotei yoteiInfo = new HolidayAtendanceYotei();
		WorkDayKbnMaster workDayKbnMaster = new WorkDayKbnMaster();
		workDayKbnMaster.setCode("02");
		yoteiInfo.setWorkDayKbnMaster(workDayKbnMaster);
		Users users = new Users();
		users.setId("4002");
		yoteiInfo.setUser(users);
		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster.setCode("00000000000002");
		yoteiInfo.setProjectMaster(projectMaster);
		yoteiInfo.setAtendanceBookDate("20140510");
		yoteiInfo.setKinmuStartTime("0900");
		yoteiInfo.setKinmuEndTime("1730");
		yoteiInfo.setCommont("トラブル対応");
		
		baseDao.insert(yoteiInfo);
		
		// 休日勤務情報
		HolidayAtendance holidayAtendanceInfo = new HolidayAtendance();
		holidayAtendanceInfo.setHolidaySyukinDate("20140510");
		holidayAtendanceInfo.setDaikyuGetShimekiriDate("20140731");
		holidayAtendanceInfo.setDaikyuDate("超勤振替");
		holidayAtendanceInfo.setFirikaeShiseiDate("20140520");
		holidayAtendanceInfo.setUsers(users);

		baseDao.insert(holidayAtendanceInfo);
		
		
		// ユーザＩＤ
		String userId = "4002";
		// 日付
		String date = "2014/05/10";
		// 勤務区分
		String workKbn = "01";
		
		AttendanceOnHolidayRecordDetailForm form = null;
		try {
			form = serviceImpl.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 日付
		assertEquals(form.getDate(), "2014/05/10");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "17:30");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014/07/31");
		// 超勤振替申請日
		assertEquals(form.getOverWorkTurnedReqDate(), "2014/05/20");
		// プロジェクト名
		assertEquals(form.getProjectName(), "SPA収益計画システム");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
	}
}
