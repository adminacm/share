package argo.cost.monthlyReport.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.entity.Project;
import argo.cost.monthlyReport.model.MonthlyReportEntity;

/**
 * 月報画面DaoImpl
 * 報画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Repository
public class MonthlyReportDaoImpl implements MonthlyReportDao{

	/**
	 * 
	 * ユーザの最後の月報提出年月を取得処理
	 * 
	 * @param userId 
	 * 				ユーザID
	 * 
	 * @return 最後の月報提出年月
	 */
	@Override
	public String getUserMonth(String userId) {
		
		// TODO DBより、ロジック未定。
		
		return "20140201";
	}
	
	/**
	 * 
	 * ユーザの月報情報を取得処理
	 * 
	 * @param userId 
	 * 				ユーザID
	 * @param date 
	 * 				年月
	 * 
	 * @return ユーザの月報情報
	 */
	@Override
	public List<MonthlyReportEntity> getUserMonthReport(String userId, String date) {
		
		// TODO DBより、ロジック未定。
		List<MonthlyReportEntity> resultList = new ArrayList<MonthlyReportEntity>();
		
		MonthlyReportEntity enty1 = new MonthlyReportEntity();
		enty1.setCommant("桜美林大学留学生管理システム保守：お客様問合せの対応");
		enty1.setDisposeDate("20140305135051");
		enty1.setLocationCode("02");
		enty1.setLocationName("北京");
		enty1.setOverSTime(1830);
		enty1.setOverETime(2200);
		enty1.setOverHours(1.5);
		enty1.setOverHoursHoliday(0.0);
		enty1.setOverHoursNight(0.0);
		enty1.setOverHoursOrdinary(1.5);
		enty1.setRepDate("20140401");
		enty1.setRestHours(1.5);
		enty1.setRestKbn("01");
		enty1.setRestKbnName("時間休(有給休暇)");
		enty1.setShiftCode("0900");
		enty1.setUserId("liuyj");
		enty1.setWorkDate("20140201");
		enty1.setWorkETime(3200);
		enty1.setWorkHours(11.5);
		enty1.setWorkKbn("01");
		enty1.setWorkKbnName("休日振替勤務");
		enty1.setWorkSTime(900);
		resultList.add(enty1);
		
		MonthlyReportEntity enty2 = new MonthlyReportEntity();
		enty2.setCommant("桜美林大学留学生管理システム保守：お客様問合せの対応");
		enty2.setDisposeDate("20140305135051");
		enty2.setLocationCode("01");
		enty2.setLocationName("日本");
		enty2.setOverSTime(1830);
		enty2.setOverETime(2200);
		enty2.setOverHours(1.5);
		enty2.setOverHoursHoliday(0.0);
		enty2.setOverHoursNight(0.0);
		enty2.setOverHoursOrdinary(1.5);
		enty2.setRepDate("20140401");
		enty2.setRestHours(1.5);
		enty2.setRestKbn("04");
		enty2.setRestKbnName("特別休暇");
		enty2.setShiftCode("0900");
		enty2.setUserId("liuyj");
		enty2.setWorkDate("20140202");
		enty2.setWorkETime(2200);
		enty2.setWorkHours(11.5);
		enty2.setWorkKbn("02");
		enty2.setWorkKbnName("休日");
		enty2.setWorkSTime(900);
		resultList.add(enty2);
		
		return resultList;
	}
	
	/**
	 * 【PJ別作業時間集計】情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 * @return
	 *        プロジェクト情報
	 */
	@Override
	public List<Project> getProjectList(String userId, String date) {
		// TODO 自動生成されたメソッド・スタブ
		List<Project> projectList = new ArrayList<Project>();
		Project projectInfo = new Project();
		projectInfo.setProjName("SPA収益計画システム");
		projectInfo.setProjHours(162.0);
		projectInfo.setProjManageHours(53.0);
		projectInfo.setBasicDesignHours(25.0);
		projectInfo.setMeetingHours(10.0);
		projectList.add(projectInfo);
		
		projectInfo = new Project();
		projectInfo.setProjName("桜美林大学留学生管理システム保守");
		projectInfo.setProjHours(100.0);
		projectInfo.setProjManageHours(50.0);
		projectInfo.setBasicDesignHours(20.0);
		projectInfo.setMeetingHours(15.0);
		projectList.add(projectInfo);
		
		projectInfo = new Project();
		projectInfo.setProjName("事務処理・社内会議");
		projectList.add(projectInfo);
		
		return projectList;
	}
}
