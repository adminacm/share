package argo.cost.menu;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHoliday;
import argo.cost.attendanceOnHoliday.model.CodeNameMap;
import argo.cost.attendanceOnHoliday.service.AtendanceOnHolidayServiceImpl;

public class AtendanceOnHolidayTest {

	AtendanceOnHolidayServiceImpl atendanceOnHolidayServiceImpl = new AtendanceOnHolidayServiceImpl();

	@Test
	public void testCheckAtendanceOnHolidayData() {

		assertEquals(false,
				atendanceOnHolidayServiceImpl.atendanceOnHolidayDataChk(
						"xiongyl", "2014/02/27"));
	}

	@Test
	public void testGetAtendanceDayKbnList() {

		ArrayList<CodeNameMap> atendanceDayKbnList = new ArrayList<CodeNameMap>();

		CodeNameMap codeNameMap1 = new CodeNameMap();
		codeNameMap1.setName("出勤");
		codeNameMap1.setCode("01");
		CodeNameMap codeNameMap2 = new CodeNameMap();
		codeNameMap2.setName("休日");
		codeNameMap2.setCode("02");
		CodeNameMap codeNameMap3 = new CodeNameMap();
		codeNameMap3.setName("振替休日");
		codeNameMap3.setCode("03");

		atendanceDayKbnList.add(codeNameMap1);
		atendanceDayKbnList.add(codeNameMap2);
		atendanceDayKbnList.add(codeNameMap3);
		
		ArrayList<CodeNameMap> atendanceDayKbnListR = atendanceOnHolidayServiceImpl.atendanceDayKbnList();
		assertEquals(atendanceDayKbnList.get(0).getCode(),
				atendanceDayKbnListR.get(0).getCode());
		assertEquals(atendanceDayKbnList.get(1).getCode(),
				atendanceDayKbnListR.get(1).getCode());
		assertEquals(atendanceDayKbnList.get(2).getCode(),
				atendanceDayKbnListR.get(2).getCode());
	}

	@Test
	public void testGetProjectKbnList() {

		ArrayList<CodeNameMap> projectKbnMapList = new ArrayList<CodeNameMap>();

		CodeNameMap codeNameMap1 = new CodeNameMap();
		codeNameMap1.setName("原価管理");
		codeNameMap1.setCode("001");
		CodeNameMap codeNameMap2 = new CodeNameMap();
		codeNameMap2.setName("不動産管理");
		codeNameMap2.setCode("002");

		projectKbnMapList.add(codeNameMap1);
		projectKbnMapList.add(codeNameMap2);
		ArrayList<CodeNameMap> projectKbnMapListR = atendanceOnHolidayServiceImpl.projectKbnList();

		
		assertEquals(projectKbnMapList.get(0).getCode(),
				projectKbnMapListR.get(0).getCode());
		assertEquals(projectKbnMapList.get(1).getCode(),
				projectKbnMapListR.get(1).getCode());
	}

	@Test
	public void testSaveAtendanceOnHoliday() {
		AtendanceOnHoliday atendanceOnHoliday = new AtendanceOnHoliday();
		assertEquals("1",
				atendanceOnHolidayServiceImpl
						.saveAtendanceOnHoliday(atendanceOnHoliday,"xiongyl"));
	}
	
	@Test
	public void testDeleteAtendanceOnHoliday() {
		assertEquals("1",
				atendanceOnHolidayServiceImpl
						.deleteAtendanceOnHoliday("20130505", "xiongyl"));
		
							
	}

}
