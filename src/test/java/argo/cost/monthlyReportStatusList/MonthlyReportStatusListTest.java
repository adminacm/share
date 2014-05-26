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
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;
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

		// 所属
		form.setAffiliation("01");
		// 状況
		form.setStatus("01");
		// 年
		form.setYear("2014");
		// 月
		form.setMonth("3");
		
		// 月報状況一覧リスト取得
		List<MonthlyReportStatusListVo> monList = monS.getMonthlyReportStatusList(form);
		
		// 月報状況一覧リストのサイズ
		assertEquals(monList.size(), 4);
		assertEquals(monList.get(0).getApplyNo(), "user01120140500");
		assertEquals(monList.get(0).getAffiliation(), "ＢＳ２");
		assertEquals(monList.get(0).getId(), "aaa");
		assertEquals(monList.get(0).getName(), "あｘｘｘｘｘ");
		assertEquals(monList.get(0).getApplyKbnName(), "月報");
		assertEquals(monList.get(0).getApplyDetail(), "2014年5月分");
		assertEquals(monList.get(0).getStatus(), "作成中");

		assertEquals(monList.get(1).getApplyNo(), "user02120140500");
		assertEquals(monList.get(1).getAffiliation(), "ＢＳ２");
		assertEquals(monList.get(1).getId(), "uuu");
		assertEquals(monList.get(1).getName(), "うｘｘｘｘｘ");
		assertEquals(monList.get(1).getApplyKbnName(), "月報");
		assertEquals(monList.get(1).getApplyDetail(), "2014年5月分");
		assertEquals(monList.get(1).getStatus(), "作成中");

		assertEquals(monList.get(2).getApplyNo(), "user03220140500");
		assertEquals(monList.get(2).getAffiliation(), "ＢＳ２");
		assertEquals(monList.get(2).getId(), "iii");
		assertEquals(monList.get(2).getName(), "えｘｘｘｘｘ");
		assertEquals(monList.get(2).getApplyKbnName(), "超勤振替申請");
		assertEquals(monList.get(2).getApplyDetail(), "休日勤務日：2014/5/5");
		assertEquals(monList.get(2).getStatus(), "申請");

		assertEquals(monList.get(3).getApplyNo(), "user04220140500");
		assertEquals(monList.get(3).getAffiliation(), "ＢＳ２");
		assertEquals(monList.get(3).getId(), "uuu");
		assertEquals(monList.get(3).getName(), "うｘｘｘｘｘ");
		assertEquals(monList.get(3).getApplyKbnName(), "超勤振替申請");
		assertEquals(monList.get(3).getApplyDetail(), "休日勤務日：2014/5/5");
		assertEquals(monList.get(3).getStatus(), "申請");
		
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
