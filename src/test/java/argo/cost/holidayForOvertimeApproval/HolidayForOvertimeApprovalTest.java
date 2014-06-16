package argo.cost.holidayForOvertimeApproval;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.holidayForOvertimeApproval.model.HolidayForOvertimeApprovalForm;
import argo.cost.holidayForOvertimeApproval.service.HolidayForOvertimeApprovalServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class HolidayForOvertimeApprovalTest {

	// 超勤振替申請承認
	@Resource
	HolidayForOvertimeApprovalServiceImpl holidayForOvertimeApprovalServiceImpl;
	
	/**
	 * 処理状況取得をテスト
	 */
	@Test
	public void testGetStatus() {

		// 申請番号
		String applyNo = "user01120140300";
		
		// 処理状況表示名
		String status = holidayForOvertimeApprovalServiceImpl.getStatusName(applyNo);
		
		assertEquals(status, "提出");
	}
	
	/**
	 * 超勤振替申請承認情報取得をテスト
	 */
	@Test
	public void testgetHolidayForOvertimeApproval() {

		// 申請番号
		String applyNo = "user01120140300";
		
		HolidayForOvertimeApprovalForm form = null;
		try {
			form = holidayForOvertimeApprovalServiceImpl.getHolidayForOvertimeApproval(applyNo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 日付
		assertEquals(form.getDate(), "2014年05月06日（火）");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "出勤");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "10:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "19:00");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014年07月31日（木）");
		// プロジェクト名
		assertEquals(form.getProjectName(), "プロジェクト名");
		// 業務内容
		assertEquals(form.getWorkDetail(), "システム復旧作業のため");
		
	}
}
