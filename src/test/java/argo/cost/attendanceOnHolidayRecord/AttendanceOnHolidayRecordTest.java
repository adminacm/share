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

		// 休日出勤管理情報
		AttendanceOnHolidayRecordForm form = new AttendanceOnHolidayRecordForm();
		// 年度
		form.setYearPeriod("2014");
		// 氏名
		form.setName("4001");
		
		try {
			serviceImpl.setAttendanceOnHolidayRecordInfo(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 休日振替勤務情報
		assertEquals(form.getHolidayExchangeWorkList().size(), 1);
		//　休日振替勤務日付
		assertEquals(form.getHolidayExchangeWorkList().get(0).getHolidayTurnedWorkingDate(), "2014/05/04");
		assertEquals(form.getHolidayExchangeWorkList().get(0).getWorkingDayTurnedHolidayDate(), "2014/05/08");

		// 休日勤務情報
		assertEquals(form.getHolidayOverWorkList().size(), 4);
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
		
		assertEquals(form.getHolidayOverWorkList().get(3).getHolidayOverWorkDate(), "2014/05/24");
		assertEquals(form.getHolidayOverWorkList().get(3).getTurnedHolidayEndDate(), "2014/07/31");
	}
}
