package argo.cost.attendanceOnHolidayRecord;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;
import argo.cost.attendanceOnHolidayRecord.service.AttendanceOnHolidayRecordServiceImpl;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.ProjectMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.entity.WorkDayKbnMaster;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"}) 
public class AttendanceOnHolidayRecordTest {
	
	// 休暇管理
	@Resource
	AttendanceOnHolidayRecordServiceImpl serviceImpl;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 休日出勤管理情報取得をテスト
	 */
	@Test
	public void testAttendanceOnHolidayRecord() {

		// 社員番号
		Users users = new Users();
		users.setId("4001");
		// 勤務日区分コード
		WorkDayKbnMaster workDayKbnMaster = new WorkDayKbnMaster();
		workDayKbnMaster.setCode("03");
		// プロジェクトコード
		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster.setCode("00000000000001");
		
		// 休日勤務予定情報
		HolidayAtendanceYotei yoteiInfo1 = new HolidayAtendanceYotei();
		// 勤務日区分コード
		yoteiInfo1.setWorkDayKbnMaster(workDayKbnMaster);
		// 社員番号
		yoteiInfo1.setUser(users);
		// プロジェクトコード
		yoteiInfo1.setProjectMaster(projectMaster);
		// 業務内容
		yoteiInfo1.setCommont("トラブル対応");
		// 予定出勤休日日付
		yoteiInfo1.setAtendanceBookDate("20140601");
		//勤務開始時刻
		yoteiInfo1.setKinmuStartTime("0900");
		//勤務終了時刻
		yoteiInfo1.setKinmuEndTime("1730");
		// 振替日
		yoteiInfo1.setFurikaeDate("20140605");
		
		HolidayAtendanceYotei yoteiInfo2 = new HolidayAtendanceYotei();
		yoteiInfo2.setWorkDayKbnMaster(workDayKbnMaster);
		yoteiInfo2.setUser(users);
		yoteiInfo2.setProjectMaster(projectMaster);
		yoteiInfo2.setCommont("トラブル対応");
		yoteiInfo2.setAtendanceBookDate("20140501");
		yoteiInfo2.setKinmuStartTime("0900");
		yoteiInfo2.setKinmuEndTime("1730");
		yoteiInfo2.setFurikaeDate("20140505");
		
		baseDao.insert(yoteiInfo1);
		baseDao.insert(yoteiInfo2);
		
		// 休日勤務情報
		HolidayAtendance holidayAtendanceInfo1 = new HolidayAtendance();
		//　休日勤務日付
		holidayAtendanceInfo1.setHolidaySyukinDate("20140413");
		// 代休期限
		holidayAtendanceInfo1.setDaikyuGetShimekiriDate("20140630");
		// 代休日
		holidayAtendanceInfo1.setDaikyuDate("20140418");
		holidayAtendanceInfo1.setUsers(users);
		
		HolidayAtendance holidayAtendanceInfo2 = new HolidayAtendance();
		holidayAtendanceInfo2.setHolidaySyukinDate("20140510");
		holidayAtendanceInfo2.setDaikyuGetShimekiriDate("20140731");
		holidayAtendanceInfo2.setDaikyuDate("超勤振替");
		// 超勤振替申請日
		holidayAtendanceInfo2.setFirikaeShiseiDate("20140520");
		holidayAtendanceInfo2.setUsers(users);
		
		HolidayAtendance holidayAtendanceInfo3 = new HolidayAtendance();
		holidayAtendanceInfo3.setHolidaySyukinDate("20140518");
		holidayAtendanceInfo3.setDaikyuGetShimekiriDate("20140731");
		holidayAtendanceInfo3.setUsers(users);

		baseDao.insert(holidayAtendanceInfo1);
		baseDao.insert(holidayAtendanceInfo2);
		baseDao.insert(holidayAtendanceInfo3);
		
		// 休日出勤管理情報
		AttendanceOnHolidayRecordForm form = new AttendanceOnHolidayRecordForm();
		// 年度
		form.setYearPeriod("2014");
		// 氏名
		form.setUserName("4001");
		
		try {
			serviceImpl.setAttendanceOnHolidayRecordInfo(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 休日振替勤務情報
		assertEquals(form.getHolidayExchangeWorkList().size(), 2);
		//　休日振替勤務日付
		assertEquals(form.getHolidayExchangeWorkList().get(0).getHolidayTurnedWorkingDate(), "2014/06/01");
		// 振替休日
		assertEquals(form.getHolidayExchangeWorkList().get(0).getWorkingDayTurnedHolidayDate(), "2014/06/05");
		assertEquals(form.getHolidayExchangeWorkList().get(1).getHolidayTurnedWorkingDate(), "2014/05/01");
		assertEquals(form.getHolidayExchangeWorkList().get(1).getWorkingDayTurnedHolidayDate(), "2014/05/05");

		// 休日勤務情報
		assertEquals(form.getHolidayOverWorkList().size(), 3);
		//　休日勤務日付
		assertEquals(form.getHolidayOverWorkList().get(0).getHolidayOverWorkDate(), "2014/04/13");
		// 代休期限
		assertEquals(form.getHolidayOverWorkList().get(0).getTurnedHolidayEndDate(), "2014/06/30");
		// 代休日
		assertEquals(form.getHolidayOverWorkList().get(0).getTurnedHolidayDate(), "2014/04/18");
		
		assertEquals(form.getHolidayOverWorkList().get(1).getHolidayOverWorkDate(), "2014/05/10");
		assertEquals(form.getHolidayOverWorkList().get(1).getTurnedHolidayEndDate(), "2014/07/31");
		assertEquals(form.getHolidayOverWorkList().get(1).getTurnedHolidayDate(), "超勤振替");
		// 超勤振替申請日
		assertEquals(form.getHolidayOverWorkList().get(1).getOverWorkTurnedReqDate(), "2014/05/20");
		
		assertEquals(form.getHolidayOverWorkList().get(2).getHolidayOverWorkDate(), "2014/05/18");
		assertEquals(form.getHolidayOverWorkList().get(2).getTurnedHolidayEndDate(), "2014/07/31");
	}
}
