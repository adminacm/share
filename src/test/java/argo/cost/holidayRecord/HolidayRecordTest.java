package argo.cost.holidayRecord;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.KyukaKekin;
import argo.cost.common.entity.KyukaKekinKbnMaster;
import argo.cost.common.entity.Users;
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
		
		KyukaKekin kyukaInfo1 = new KyukaKekin();
		Users users1 = new Users();
		users1.setId("4001");
		kyukaInfo1.setUsers(users1);
		kyukaInfo1.setKyukaDate("20140201");
		KyukaKekinKbnMaster kyukaKekinKbnMaster1 = new KyukaKekinKbnMaster();
		kyukaKekinKbnMaster1.setCode("01");
		kyukaInfo1.setKyukaKekinKbnMaster(kyukaKekinKbnMaster1);
		kyukaInfo1.setKyukaJikansu(new BigDecimal(7.5));
		kyukaInfo1.setSyoriFlag(new BigDecimal(1));

		KyukaKekin kyukaInfo2 = new KyukaKekin();
		Users users2 = new Users();
		users2.setId("4001");
		kyukaInfo2.setUsers(users2);
		kyukaInfo2.setKyukaDate("20140207");
		KyukaKekinKbnMaster kyukaKekinKbnMaster2 = new KyukaKekinKbnMaster();
		kyukaKekinKbnMaster2.setCode("02");
		kyukaInfo2.setKyukaKekinKbnMaster(kyukaKekinKbnMaster2);
		kyukaInfo2.setKyukaJikansu(new BigDecimal(4));
		kyukaInfo2.setSyoriFlag(new BigDecimal(1));

		KyukaKekin kyukaInfo3 = new KyukaKekin();
		Users users3 = new Users();
		users3.setId("4001");
		kyukaInfo3.setUsers(users3);
		kyukaInfo3.setKyukaDate("20140518");
		KyukaKekinKbnMaster kyukaKekinKbnMaster3 = new KyukaKekinKbnMaster();
		kyukaKekinKbnMaster3.setCode("03");
		kyukaInfo3.setKyukaKekinKbnMaster(kyukaKekinKbnMaster3);
		kyukaInfo3.setKyukaJikansu(new BigDecimal(2));
		kyukaInfo3.setSyoriFlag(new BigDecimal(1));

		KyukaKekin kyukaInfo4 = new KyukaKekin();
		Users users4 = new Users();
		users4.setId("4001");
		kyukaInfo4.setUsers(users4);
		kyukaInfo4.setKyukaDate("20140307");
		KyukaKekinKbnMaster kyukaKekinKbnMaster4 = new KyukaKekinKbnMaster();
		kyukaKekinKbnMaster4.setCode("04");
		kyukaInfo4.setKyukaKekinKbnMaster(kyukaKekinKbnMaster4);
		kyukaInfo4.setKyukaJikansu(new BigDecimal(8));
		kyukaInfo4.setSyoriFlag(new BigDecimal(1));

		KyukaKekin kyukaInfo5 = new KyukaKekin();
		Users users5 = new Users();
		users5.setId("4001");
		kyukaInfo5.setUsers(users5);
		kyukaInfo5.setKyukaDate("20140310");
		KyukaKekinKbnMaster kyukaKekinKbnMaster5 = new KyukaKekinKbnMaster();
		kyukaKekinKbnMaster5.setCode("06");
		kyukaInfo5.setKyukaKekinKbnMaster(kyukaKekinKbnMaster5);
		kyukaInfo5.setKyukaJikansu(new BigDecimal(2.5));
		kyukaInfo5.setSyoriFlag(new BigDecimal(1));
		
		baseDao.insert(kyukaInfo1);
		baseDao.insert(kyukaInfo2);
		baseDao.insert(kyukaInfo3);
		baseDao.insert(kyukaInfo4);
		baseDao.insert(kyukaInfo5);
		
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

		assertEquals(form.getAbsenceList().size(), 2);
		assertEquals(form.getAbsenceList().get(0).getAbsentDate(), "2014/03/10");
		assertEquals(form.getAbsenceList().get(0).getDayQuantity(), null);
		assertEquals(form.getAbsenceList().get(0).getHourQuantity(), "2.5");

		assertEquals(form.getSpecialHolidayList().size(), 2);
		assertEquals(form.getSpecialHolidayList().get(0).getSpecialHolidayDate(), "2014/03/07");
		assertEquals(form.getSpecialHolidayList().get(0).getDayQuantity(), "1.0");
		
	}
}
