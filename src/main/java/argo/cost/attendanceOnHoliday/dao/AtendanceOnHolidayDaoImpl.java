package argo.cost.attendanceOnHoliday.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHoliday;
import argo.cost.attendanceOnHoliday.model.CodeNameMap;

@Repository
public class AtendanceOnHolidayDaoImpl implements AtendanceOnHolidayDao {

//	protected EntityManager em;

	@Override
	public boolean atendanceOnHolidayDataChk(String userId, String date) {
		// TODO DB取得
		return false;
	}

	@Override
	public AtendanceOnHoliday atendanceOnHolidayDataGet(String userId,
			String date) {
		// TODO Auto-generated method stub
		AtendanceOnHoliday atendanceOnHoliday = null;
		atendanceOnHoliday.setStrAtendanceDate("2014//02/27");

		return atendanceOnHoliday;
	}

	@Override
	public ArrayList<CodeNameMap> getAtendanceDayKbnList() {
		
		ArrayList<CodeNameMap> atendanceDayKbnList = new ArrayList<CodeNameMap>();
		 
		// TODO データベースから取得
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
		
		return atendanceDayKbnList;
	}

	@Override
	public ArrayList<CodeNameMap> getProjectKbnList() {
		
		ArrayList<CodeNameMap> projectKbnMapList = new ArrayList<CodeNameMap>();

		// TODO データベースから取得
		CodeNameMap codeNameMap1 = new CodeNameMap();
		codeNameMap1.setName("原価管理");
		codeNameMap1.setCode("001");
		CodeNameMap codeNameMap2 = new CodeNameMap();
		codeNameMap2.setName("不動産管理");
		codeNameMap2.setCode("002");
		
		projectKbnMapList.add(codeNameMap1);
		projectKbnMapList.add(codeNameMap2);

		return projectKbnMapList;
	}

	@Override
	public String deleteAtendanceOnHoliday(String strAtendanceDate,String UserID) {
		// TODO Auto-generated method stub
		
		if ("20140505".equals(strAtendanceDate) && UserID.equals("xiongyl")) {
			return "1";
		} else {
			return "0";
		}
		
	}

	@Override
	public String saveAtendanceOnHoliday(AtendanceOnHoliday atendanceOnHoliday,String UserID) {
		// TODO Auto-generated method stub
		if (null != atendanceOnHoliday && "xiongyl".equals(UserID)) {
			return "1";
		} else {
			return "0";
		}
		
	}

}
