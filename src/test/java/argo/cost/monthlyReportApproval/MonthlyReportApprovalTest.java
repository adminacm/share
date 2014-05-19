package argo.cost.monthlyReportApproval;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.model.entity.Project;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;
import argo.cost.monthlyReportApproval.service.MonthlyReportApprovalServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class MonthlyReportApprovalTest {

	// 月報承認
	@Resource
	MonthlyReportApprovalServiceImpl serviceImpl;
	
	/**
	 * 処理状況取得をテスト
	 */
	@Test
	public void testGetStatus() {

		// 申請番号
		String applyNo = "user01120140300";
		
		// 処理状況表示名
		String status = serviceImpl.getStatus(applyNo);
		
		assertEquals(status, "提出");
	}
	
	/**
	 * 月報承認一覧取得をテスト
	 */
	@Test
	public void testGetMonthlyReportApproval() {
		
		// 申請番号(主なキー)
	    String applyNo = "user01120140300";
		List<MonthlyReportApprovalVo> resultList = serviceImpl.getMonReApprovalList(applyNo);
		
		assertEquals(resultList.size(), 31);
		// 日付(表示用)
		assertEquals(resultList.get(0).getDay(), "01");
		// 曜日
        assertEquals(resultList.get(0).getWeek(), "水");
        // 勤務区分名
        assertEquals(resultList.get(0).getWorkKbnName(), "出勤日");
        // ｼﾌﾄ
        assertEquals(resultList.get(0).getShift(), "0900");
        // 出勤
        assertEquals(resultList.get(0).getWorkSTime(), "10:00");
        // 退勤
        assertEquals(resultList.get(0).getWorkETime(), "20:30");
        // 休暇時間数
        assertEquals(resultList.get(0).getRestHours(), Double.valueOf(1.0));
        // 勤務時間数
        assertEquals(resultList.get(0).getWorkHours(), Double.valueOf(9.0));
        // 超勤開始
        assertEquals(resultList.get(0).getChoSTime(), "18:00");
        // 超勤終了
        assertEquals(resultList.get(0).getChoETime(), "20:30");
        // 超勤平増
        assertEquals(resultList.get(0).getChoWeekday(),  Double.valueOf(1.5));
        // 超勤平常
        assertEquals(resultList.get(0).getChoWeekdayNomal(),  Double.valueOf(1.0));

		// 日付(表示用)
        assertEquals(resultList.get(1).getDay(), "02");
		// 曜日
		assertEquals(resultList.get(1).getWeek(), "木");
        // 勤務区分名
		assertEquals(resultList.get(1).getWorkKbnName(), "出勤日");
        // ｼﾌﾄ
		assertEquals(resultList.get(1).getShift(), "0800");
        // 出勤
		assertEquals(resultList.get(1).getWorkSTime(), "09:00");
        // 退勤
		assertEquals(resultList.get(1).getWorkETime(), "16:30");
        // 勤務時間数
		assertEquals(resultList.get(1).getWorkHours(), Double.valueOf(6.5));

		// 日付(表示用)
		assertEquals(resultList.get(2).getDay(), "03");
		// 曜日
		assertEquals(resultList.get(2).getWeek(), "金");
        // 勤務区分名
		assertEquals(resultList.get(2).getWorkKbnName(), "出勤日");
        // ｼﾌﾄ
		assertEquals(resultList.get(2).getShift(), "0900");
        // 出勤
		assertEquals(resultList.get(2).getWorkSTime(), "09:00");
        // 退勤
		assertEquals(resultList.get(2).getWorkETime(), "19:00");
        // 勤務時間数
		assertEquals(resultList.get(2).getWorkHours(), Double.valueOf(8.5));
        // 超勤開始
		assertEquals(resultList.get(2).getChoSTime(), "18:00");
        // 超勤終了
		assertEquals(resultList.get(2).getChoETime(), "19:00");
        // 超勤平増
		assertEquals(resultList.get(2).getChoWeekday(), Double.valueOf(1.0));
		// ﾛｹｰｼｮﾝ名
		assertEquals(resultList.get(2).getLocationName(), "日本");

		// 日付(表示用)
		assertEquals(resultList.get(3).getDay(), "04");
		// 曜日
		assertEquals(resultList.get(3).getWeek(), "土");
        // 勤務区分名
		assertEquals(resultList.get(3).getWorkKbnName(), "休日");
        // ｼﾌﾄ
		assertEquals(resultList.get(3).getShift(), "0900");
        // 出勤
		assertEquals(resultList.get(3).getWorkSTime(), "09:00");
        // 退勤
		assertEquals(resultList.get(3).getWorkETime(), "23:30");
        // 勤務時間数
		assertEquals(resultList.get(3).getWorkHours(), Double.valueOf(12.0));
        // 超勤開始
		assertEquals(resultList.get(3).getChoSTime(), "18:00");
        // 超勤終了
		assertEquals(resultList.get(3).getChoETime(), "23:30");
		// 超勤休日
		assertEquals(resultList.get(3).getChoHoliday(), Double.valueOf(4.5));
		// 超勤深夜
		assertEquals(resultList.get(3).getmNHours(), Double.valueOf(0.5));
		// ﾛｹｰｼｮﾝ名
		assertEquals(resultList.get(3).getLocationName(), "日本");
	}
	
	/**
	 * PJ別作業時間集計取得をテスト
	 */
	@Test
	public void testGetProject() {

		// 申請番号(主なキー)
	    String applyNo = "user01120140300";
		List<Project> projectList = serviceImpl.getProjectList(applyNo);
		
		assertEquals(projectList.size(), 3);

		assertEquals(projectList.get(0).getProjName(), "SPA収益計画システム");
		assertEquals(projectList.get(0).getProjHours(), Double.valueOf(162.0));
		assertEquals(projectList.get(0).getProjManageHours(), Double.valueOf(53.0));
		assertEquals(projectList.get(0).getBasicDesignHours(), Double.valueOf(25.0));
		assertEquals(projectList.get(0).getMeetingHours(), Double.valueOf(10.0));
		             
		assertEquals(projectList.get(1).getProjName(), "桜美林大学留学生管理システム保守");
		assertEquals(projectList.get(1).getProjHours(), Double.valueOf(100.0));
		assertEquals(projectList.get(1).getProjManageHours(), Double.valueOf(50.0));
		assertEquals(projectList.get(1).getBasicDesignHours(), Double.valueOf(20.0));
		assertEquals(projectList.get(1).getMeetingHours(), Double.valueOf(15.0));
	}
	
	/**
	 * 申請状況更新をテスト
	 */
	@Test
	public void testUpdateProStatus() {

		// 申請番号
		String applyNo = "user01120140300";
		// 申請状況
		String proStatus = "03";
		
		String resultFlg = serviceImpl.updateProStatus(applyNo, proStatus);
		
		assertEquals(resultFlg, "1");
	}

}
