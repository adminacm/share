package argo.cost.monthlyReportApproval.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.entity.Project;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;

@Repository
public class MonthlyReportApprovalDaoImpl implements MonthlyReportApprovalDao {

	/**
	 * 処理状況値を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況値
	 */
	@Override
	public String getStatus(String applyNo) {
		// TODO ＤＢから、申請番号による、処理状况値を取得
		return "02";
	}
	
	/**
	 * 月報承認データを取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        月報承認データ一覧リスト
	*/
	@Override
	public List<MonthlyReportApprovalVo> searchMonthReportApprovalList(String applyNo) {
		// TODO 自動生成されたメソッド・スタブ
		List<MonthlyReportApprovalVo> monthlyReportApprovalList = new ArrayList<MonthlyReportApprovalVo>();
		MonthlyReportApprovalVo monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("01");
		monthlyReportVo.setWeek("水");
		monthlyReportVo.setWorkKbnName("出勤日");
		monthlyReportVo.setShift("0900");
		monthlyReportVo.setWorkSTime("10:00");
		monthlyReportVo.setWorkETime("20:30");
		monthlyReportVo.setRestHours(1.0);;
		monthlyReportVo.setWorkHours(9.0);
		monthlyReportVo.setChoSTime("18:00");
		monthlyReportVo.setChoETime("20:30");
		monthlyReportVo.setChoWeekday(1.5);
		monthlyReportVo.setChoWeekdayNomal(1.0);
		monthlyReportApprovalList.add(monthlyReportVo);
		
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("02");
		monthlyReportVo.setWeek("木");
		monthlyReportVo.setWorkKbnName("出勤日");
		monthlyReportVo.setShift("0800");
		monthlyReportVo.setWorkSTime("09:00");
		monthlyReportVo.setWorkETime("16:30");
		monthlyReportVo.setWorkHours(6.5);
		monthlyReportApprovalList.add(monthlyReportVo);
		
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("03");
		monthlyReportVo.setWeek("金");
		monthlyReportVo.setWorkKbnName("出勤日");
		monthlyReportVo.setShift("0900");
		monthlyReportVo.setWorkSTime("09:00");
		monthlyReportVo.setWorkETime("19:00");
		monthlyReportVo.setWorkHours(8.5);
		monthlyReportVo.setChoSTime("18:00");
		monthlyReportVo.setChoETime("19:00");
		monthlyReportVo.setChoWeekday(1.0);
		monthlyReportVo.setLocationName("日本");
		monthlyReportApprovalList.add(monthlyReportVo);
		
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("04");
		monthlyReportVo.setWeek("土");
		monthlyReportVo.setWorkKbnName("休日");
		monthlyReportVo.setShift("0900");
		monthlyReportVo.setWorkSTime("09:00");
		monthlyReportVo.setWorkETime("23:30");
		monthlyReportVo.setWorkHours(12.0);
		monthlyReportVo.setChoSTime("18:00");
		monthlyReportVo.setChoETime("23:30");
		monthlyReportVo.setChoHoliday(4.5);
		monthlyReportVo.setmNHours(0.5);
		monthlyReportVo.setLocationName("日本");
		monthlyReportApprovalList.add(monthlyReportVo);
		
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("05");
		monthlyReportVo.setWeek("日");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("06");
		monthlyReportVo.setWeek("月");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("07");
		monthlyReportVo.setWeek("火");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("08");
		monthlyReportVo.setWeek("水");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("09");
		monthlyReportVo.setWeek("木");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("10");
		monthlyReportVo.setWeek("金");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("11");
		monthlyReportVo.setWeek("土");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("12");
		monthlyReportVo.setWeek("日");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("13");
		monthlyReportVo.setWeek("月");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("14");
		monthlyReportVo.setWeek("火");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("15");
		monthlyReportVo.setWeek("水");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("16");
		monthlyReportVo.setWeek("木");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("17");
		monthlyReportVo.setWeek("金");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("18");
		monthlyReportVo.setWeek("土");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("19");
		monthlyReportVo.setWeek("日");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("20");
		monthlyReportVo.setWeek("月");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("21");
		monthlyReportVo.setWeek("火");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("22");
		monthlyReportVo.setWeek("水");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("23");
		monthlyReportVo.setWeek("木");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("24");
		monthlyReportVo.setWeek("金");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("25");
		monthlyReportVo.setWeek("土");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo.setDay("26");
		monthlyReportVo.setWeek("日");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("27");
		monthlyReportVo.setWeek("月");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("28");
		monthlyReportVo.setWeek("火");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("29");
		monthlyReportVo.setWeek("水");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("30");
		monthlyReportVo.setWeek("木");
		monthlyReportApprovalList.add(monthlyReportVo);
		monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("31");
		monthlyReportVo.setWeek("金");
		monthlyReportApprovalList.add(monthlyReportVo);
		
		return monthlyReportApprovalList;
	}

	/**
	 * プロジェクト情報を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        プロジェクト情報リスト
	 */
	@Override
	public List<Project> searchProjectList(String applyNo) {
		
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

	/**
	 * 申請状況更新
	 * 
	 * @param applyNo
	 *               申請番号
	 * @param proStatus
	 *                 申請状況
	 * @return
	 *        更新フラグ
	 */
	@Override
	public String updateProStatus(String applyNo, String proStatus) {
		// TODO 自動生成されたメソッド・スタブ
		return "1";
	}
}
