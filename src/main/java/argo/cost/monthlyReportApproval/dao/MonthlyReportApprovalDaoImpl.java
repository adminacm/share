package argo.cost.monthlyReportApproval.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;
import argo.cost.monthlyReportApproval.model.ProjectVo;

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
	public List<MonthlyReportApprovalVo> searchMonthReportList(String applyNo) {
		// TODO 自動生成されたメソッド・スタブ
		List<MonthlyReportApprovalVo> monthlyReportApprovalList = new ArrayList<MonthlyReportApprovalVo>();
		MonthlyReportApprovalVo monthlyReportVo = new MonthlyReportApprovalVo();
		monthlyReportVo.setDay("01");
		monthlyReportVo.setWeek("水");
		monthlyReportVo.setWorkKbnName("出勤日");
		monthlyReportVo.setShift("0900");
		monthlyReportVo.setWorkSTime("10:00");
		monthlyReportVo.setWorkETime("20:30");
		monthlyReportVo.setRestKbnName("時間休(有給休暇)	");
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
	public List<ProjectVo> searchProjectList(String applyNo) {
		
		// TODO 自動生成されたメソッド・スタブ
		List<ProjectVo> projectList = new ArrayList<ProjectVo>();
		ProjectVo projectInfo = new ProjectVo();
		projectInfo.setProjName("SPA収益計画システム");
		projectInfo.setProjHours(162.0);
		projectInfo.setProjManageHours(53.0);
		projectInfo.setBasicDesignHours(25.0);
		projectInfo.setMeetingHours(10.0);
		projectList.add(projectInfo);
		
		projectInfo = new ProjectVo();
		projectInfo.setProjName("桜美林大学留学生管理システム保守");
		projectInfo.setProjHours(100.0);
		projectInfo.setProjManageHours(50.0);
		projectInfo.setBasicDesignHours(20.0);
		projectInfo.setMeetingHours(15.0);
		projectList.add(projectInfo);
		
		return projectList;
	}
}
