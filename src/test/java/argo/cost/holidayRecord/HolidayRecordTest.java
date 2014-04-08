package argo.cost.holidayRecord;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.holidayRecord.model.HolidayRecordForm;
import argo.cost.holidayRecord.service.HolidayRecordServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class HolidayRecordTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	// 休暇管理
	@Resource
	HolidayRecordServiceImpl holidayS;

	/**
	 * 休暇管理情報取得をテスト
	 */
	@Test
	public void testGetHolidayRecord() {
		
		HolidayRecordForm form = new HolidayRecordForm();
		
		form.setYearPeriod("2014");
		
		holidayS.searchHolidayRecord(form);
		
		assertEquals(form.getPayHolidayList().size(), 5);
		assertEquals(form.getPayHolidayList().get(0).getPayHolidayDate(), "2013/5/1");
		assertEquals(form.getPayHolidayList().get(0).getHolidayKbnName(), "全休");
		assertEquals(form.getPayHolidayList().get(0).getDayQuantity(), "1");
		assertEquals(form.getPayHolidayList().get(0).getHourQuantity(), "");

		assertEquals(form.getAbsenceList().size(), 2);
		assertEquals(form.getAbsenceList().get(0).getAbsentDate(), "2013/4/1");
		assertEquals(form.getAbsenceList().get(0).getDayQuantity(), "1");
		assertEquals(form.getAbsenceList().get(0).getHourQuantity(), "");

		assertEquals(form.getSpecialHolidayList().size(), 3);
		assertEquals(form.getSpecialHolidayList().get(0).getSpecialHolidayDate(), "2013/4/25");
		assertEquals(form.getSpecialHolidayList().get(0).getDayQuantity(), "1");
		
	}

}
