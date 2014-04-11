package argo.cost.attendanceOnHolidayRecord;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;
import argo.cost.attendanceOnHolidayRecord.service.AttendanceOnHolidayRecordServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class AttendanceOnHolidayRecordTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	// 休暇管理
	@Resource
	AttendanceOnHolidayRecordServiceImpl serviceImpl;

	/**
	 * 休日出勤管理情報取得をテスト
	 */
	@Test
	public void testAttendanceOnHolidayRecord() {
		
		AttendanceOnHolidayRecordForm form = new AttendanceOnHolidayRecordForm();
		
		form.setYearPeriod("2014");
		form.setUserName("li");
		
		serviceImpl.searchAttendanceOnHolidayRecord(form);
		
		assertEquals(form.getHolidayExchangeWorkList().size(), 1);
		assertEquals(form.getHolidayExchangeWorkList().get(0).getHolidayTurnedWorkingDate(), "2014/5/5");
		assertEquals(form.getHolidayExchangeWorkList().get(0).getWorkingDayTurnedHolidayDate(), "2014/5/9");

		assertEquals(form.getHolidayOverWorkList().size(), 3);
		assertEquals(form.getHolidayOverWorkList().get(0).getHolidayOverWorkDate(), "2014/4/13");
		assertEquals(form.getHolidayOverWorkList().get(0).getTurnedHolidayEndDate(), "2014/6/30");
		assertEquals(form.getHolidayOverWorkList().get(0).getTurnedHolidayDate(), "2014/4/18");
		assertEquals(form.getHolidayOverWorkList().get(0).getOverWorkTurnedReqDate(), "");
		
	}

}
