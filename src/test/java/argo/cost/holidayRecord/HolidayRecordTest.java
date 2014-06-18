package argo.cost.holidayRecord;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.dao.BaseDao;
import argo.cost.holidayRecord.model.HolidayRecordForm;
import argo.cost.holidayRecord.service.HolidayRecordServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"}) 
public class HolidayRecordTest {
	
	/**
	 *  休暇管理
	 */
	@Resource
	HolidayRecordServiceImpl serviceImpl;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 休暇管理情報取得をテスト
	 */
	@Test
	public void testGetHolidayRecord() {
		
		HolidayRecordForm form = new HolidayRecordForm();
		
		form.setUserId("4001");
		form.setYearPeriod("2014");
		
		try {
			serviceImpl.setHolidayRecordInfo(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertEquals(form.getPayHolidayList().size(), 4);
		assertEquals(form.getPayHolidayList().get(0).getPayHolidayDate(), "2014/02/01");
		assertEquals(form.getPayHolidayList().get(0).getHolidayKbnName(), "全休");
		assertEquals(form.getPayHolidayList().get(0).getDayQuantity(), "1.0");
		assertEquals(form.getPayHolidayList().get(0).getHourQuantity(), null);
		
		assertEquals(form.getPayHolidayList().get(1).getPayHolidayDate(), "2014/02/07");
		assertEquals(form.getPayHolidayList().get(1).getHolidayKbnName(), "半休");
		assertEquals(form.getPayHolidayList().get(1).getDayQuantity(), "0.5");
		assertEquals(form.getPayHolidayList().get(1).getHourQuantity(), null);
		
		assertEquals(form.getPayHolidayList().get(2).getPayHolidayDate(), "2014/03/05");
		assertEquals(form.getPayHolidayList().get(2).getHolidayKbnName(), "時間休");
		assertEquals(form.getPayHolidayList().get(2).getDayQuantity(), null);
		assertEquals(form.getPayHolidayList().get(2).getHourQuantity(), "2.0");
		
		assertEquals(form.getPayHolidayList().get(3).getPayHolidayDate(), "累計");
		assertEquals(form.getPayHolidayList().get(3).getHolidayKbnName(), null);
		assertEquals(form.getPayHolidayList().get(3).getDayQuantity(), "1.5日");
		assertEquals(form.getPayHolidayList().get(3).getHourQuantity(), "2.0時間");

		assertEquals(form.getAbsenceList().size(), 2);
		assertEquals(form.getAbsenceList().get(0).getAbsentDate(), "2014/03/10");
		assertEquals(form.getAbsenceList().get(0).getDayQuantity(), null);
		assertEquals(form.getAbsenceList().get(0).getHourQuantity(), "2.5");
		
		assertEquals(form.getAbsenceList().get(1).getAbsentDate(), "累計");
		assertEquals(form.getAbsenceList().get(1).getDayQuantity(), null);
		assertEquals(form.getAbsenceList().get(1).getHourQuantity(), "2.5時間");

		assertEquals(form.getSpecialHolidayList().size(), 2);
		assertEquals(form.getSpecialHolidayList().get(0).getSpecialHolidayDate(), "2014/03/07");
		assertEquals(form.getSpecialHolidayList().get(0).getDayQuantity(), "1.0");
		
		assertEquals(form.getSpecialHolidayList().get(1).getSpecialHolidayDate(), "累計");
		assertEquals(form.getSpecialHolidayList().get(1).getDayQuantity(), "1.0日");
		
	}
}
