package argo.cost.attendanceOnHoliday;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.attendanceOnHoliday.service.AtendanceOnHolidayServiceImpl;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.WorkDayKbnMaster;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"}) 
public class attendanceOnHolidayTest {

	/**
	 * 休日勤務入力画面のサビース	
	 */
	@Autowired
	AtendanceOnHolidayServiceImpl atendanceOnHolidayServiceImpl;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 勤務日区分のプルダウンリスト取得をテスト
	 */
	@Test
	public void testGetWorkDayKbnList(){
		
		// 勤務日区分のプルダウンリスト取得テスト
		ArrayList<WorkDayKbnMaster> workDayKbnList = atendanceOnHolidayServiceImpl.getAtendanceDayKbnList();
		
		

		assertEquals(workDayKbnList.size(), 4);
		assertEquals(workDayKbnList.get(0).getCode(), "01");
		assertEquals(workDayKbnList.get(0).getName(), "出勤");
		assertEquals(workDayKbnList.get(1).getCode(), "02");
		assertEquals(workDayKbnList.get(1).getName(), "休日");
		assertEquals(workDayKbnList.get(2).getCode(), "03");
		assertEquals(workDayKbnList.get(2).getName(), "休日振替勤務");
		assertEquals(workDayKbnList.get(3).getCode(), "04");
		assertEquals(workDayKbnList.get(3).getName(), "振替休日");
	}
	
	/**
	 * 休日勤務入力画面の保存処理のテスト
	 */
	@Test
	public void testSaveAtendanceOnHoliday(){
		
		// 休日勤務情報を作成
		AtendanceOnHolidayForm atendanceOnHolidayForm = new AtendanceOnHolidayForm();
		atendanceOnHolidayForm.setSelectedAtendanceDayKbn("02");
		atendanceOnHolidayForm.setUserId("4001");
		atendanceOnHolidayForm.setSelectedProjCd("00001");
		atendanceOnHolidayForm.setStrAtendanceDate("20140607");
		atendanceOnHolidayForm.setStrAtendanceTimeStat("0900");
		atendanceOnHolidayForm.setStrAtendanceTimeEnd("1800");
		
		
		// 休日勤務情報を保存
		assertEquals(atendanceOnHolidayServiceImpl.saveAtendanceOnHoliday(atendanceOnHolidayForm), "1");
	}
	
	/**
	 * 休日勤務入力画面の削除処理をテスト
	 */
	@Test
	public void testGetApprovalList(){
		
		assertEquals(atendanceOnHolidayServiceImpl.deleteAtendanceOnHoliday("20140607", "4001"), new Integer(0));
	}
}
