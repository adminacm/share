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
	HolidayForOvertimeApprovalServiceImpl serviceImpl;
	
	/**
	 * 処理状況取得をテスト
	 */
	@Test
	public void testGetStatusName() {

		// 申請番号
		String applyNo = "4001220140504";
		
		// 処理状況表示名
		String status = serviceImpl.getStatusName(applyNo);
		
		assertEquals(status, "提出");
	}
	
	/**
	 * 超勤振替申請承認情報取得をテスト
	 */
	@Test
	public void testGetHolidayForOvertimeApproval() {

		// 申請番号
		String applyNo = "4001220140504";
		
		HolidayForOvertimeApprovalForm form = null;
		try {
			form = serviceImpl.getHolidayForOvertimeApproval(applyNo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 日付
		assertEquals(form.getDate(), "2014年05月18日（日）");
		// 勤務区分
		assertEquals(form.getWorkKbnName(), "休日");
		// 勤務開始時間
		assertEquals(form.getWorkStartTime(), "09:00");
		// 勤務終了時間
		assertEquals(form.getWorkEndTime(), "17:30");
		// 代休期限
		assertEquals(form.getTurnedHolidayEndDate(), "2014年07月31日（木）");
		// プロジェクト名
		assertEquals(form.getProjectName(), "KKF基幹システム再構築");
		// 業務内容
		assertEquals(form.getWorkDetail(), "トラブル対応");
		
	}
	
	/**
	 * 承認処理をテスト
	 */
	@Test
	public void testApprovalOverWork() {

		// 申請番号
		String applyNo = "4001220140504";
		
		serviceImpl.approvalOverWork(applyNo);
		
	}
	
	/**
	 * 差戻処理をテスト
	 */
	@Test
	public void testRemandOverWork() {

		// 申請番号
		String applyNo = "4001220140504";
		
		serviceImpl.remandOverWork(applyNo);
		
	}
}
