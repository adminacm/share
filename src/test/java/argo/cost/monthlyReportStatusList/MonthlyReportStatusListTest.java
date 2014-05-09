package argo.cost.monthlyReportStatusList;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.model.ListItemVO;
import argo.cost.common.model.entity.ApprovalList;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListInfo;
import argo.cost.monthlyReportStatusList.service.MonthlyReportStatusListServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class MonthlyReportStatusListTest {


	// 月報状況一覧サビース
	@Resource
	MonthlyReportStatusListServiceImpl monS;
	
	/**
	 * 月報状況一覧リスト取得をテスト
	 */
	@Test
	public void testGetMonthlyReportStatusList(){

		// 月報状況一覧画面入力情報
		MonthlyReportStatusListForm form = new MonthlyReportStatusListForm();

		List<ApprovalList> appList = new ArrayList<ApprovalList>();
		
		ApprovalList appInfo = new ApprovalList();
		appInfo.setApplyKbn("月報");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("作成中");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setId("aaa");
		appInfo.setName("あｘｘｘｘｘ");
		appList.add(appInfo);
		
		appInfo = new ApprovalList();
		appInfo.setApplyKbn("月報");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("提出");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setId("uuu");
		appInfo.setName("うｘｘｘｘｘ");
		appList.add(appInfo);
		
		// 所属
		form.setAffiliation("01");
		// 状況
		form.setStatus("01");
		// 年
		form.setYear("2014");
		// 月
		form.setMonth("3");
		
		// 月報状況一覧リスト取得
		List<MonthlyReportStatusListInfo> monList = monS.getMonthlyReportStatusList(form);
		
		// 月報状況一覧リストのサイズ
		assertEquals(monList.size(), 2);
		assertEquals(monList.get(0).getAffiliation(), appList.get(0).getAffiliation());
		assertEquals(monList.get(0).getId(), appList.get(0).getId());
		assertEquals(monList.get(0).getName(), appList.get(0).getName());
		assertEquals(monList.get(0).getApplyKbn(), appList.get(0).getApplyKbn());
		assertEquals(monList.get(0).getApplyDetail(), appList.get(0).getApplyDetail());
		assertEquals(monList.get(0).getStatus(), appList.get(0).getStatus());
		
		assertEquals(monList.get(1).getAffiliation(), appList.get(1).getAffiliation());
		assertEquals(monList.get(1).getId(), appList.get(1).getId());
		assertEquals(monList.get(1).getName(), appList.get(1).getName());
		assertEquals(monList.get(1).getApplyKbn(), appList.get(1).getApplyKbn());
		assertEquals(monList.get(1).getApplyDetail(), appList.get(1).getApplyDetail());
		assertEquals(monList.get(1).getStatus(), appList.get(1).getStatus());
		
	}

	/**
	 * 年プルダウンリスト取得をテスト
	 */
	@Test
	public void testGetYearList(){

		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse("2014-03-21");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 年プルダウンリスト取得
		List<ListItemVO> yearMonthList = monS.getYearList(date);
		
		// 年プルダウンリストのサイズ
		assertEquals(yearMonthList.size(), 3);
		assertEquals(yearMonthList.get(0).getValue(), "2014");
		assertEquals(yearMonthList.get(0).getName(), "2014年");
		assertEquals(yearMonthList.get(1).getValue(), "2013");
		assertEquals(yearMonthList.get(1).getName(), "2013年");
		assertEquals(yearMonthList.get(2).getValue(), "2012");
		assertEquals(yearMonthList.get(2).getName(), "2012年");
		
	}

	/**
	 * 月プルダウンリスト取得をテスト
	 */
	@Test
	public void testGetMonthList(){

		// 年プルダウンリスト取得
		List<ListItemVO> yearMonthList = monS.getMonthList();
		
		// 年プルダウンリストのサイズ
		assertEquals(yearMonthList.size(), 12);
		assertEquals(yearMonthList.get(0).getValue(), "1");
		assertEquals(yearMonthList.get(0).getName(), "1月");
		assertEquals(yearMonthList.get(1).getValue(), "2");
		assertEquals(yearMonthList.get(1).getName(), "2月");
		assertEquals(yearMonthList.get(2).getValue(), "3");
		assertEquals(yearMonthList.get(2).getName(), "3月");
		
	}

	/**
	 * 所属プルダウンリスト取得をテスト
	 */
	@Test
	public void testGetAffiliationList(){
		
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = new ListItemVO();
		item.setValue("01");
		item.setName("ＢＳ１");
		resultList.add(item);
		
		item = new ListItemVO();
		item.setValue("02");
		item.setName("ＢＳ２");
		resultList.add(item);
		
		// 所属プルダウンリスト取得
		List<ListItemVO> affiliationList = monS.getAffiliationList();
		
		// 所属プルダウンリストのサイズ
		assertEquals(affiliationList.size(), 3);
		assertEquals(affiliationList.get(1).getValue(), resultList.get(0).getValue());
		assertEquals(affiliationList.get(1).getName(), resultList.get(0).getName());
		assertEquals(affiliationList.get(2).getValue(), resultList.get(1).getValue());
		assertEquals(affiliationList.get(2).getName(), resultList.get(1).getName());
		
	}
}
