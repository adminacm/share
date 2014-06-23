package argo.cost.monthlyReportStatusList;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.dao.BaseDao;
import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;
import argo.cost.monthlyReportStatusList.service.MonthlyReportStatusListServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"}) 
public class MonthlyReportStatusListTest {


	// 月報状況一覧サビース
	@Resource
	MonthlyReportStatusListServiceImpl serviceImpl;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

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
		List<ListItemVO> yearMonthList = serviceImpl.getYearList(date);
		
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
		List<ListItemVO> yearMonthList = serviceImpl.getMonthList();
		
		// 年プルダウンリストのサイズ
		assertEquals(yearMonthList.size(), 12);
		assertEquals(yearMonthList.get(0).getValue(), "01");
		assertEquals(yearMonthList.get(0).getName(), "01月");
		assertEquals(yearMonthList.get(1).getValue(), "02");
		assertEquals(yearMonthList.get(1).getName(), "02月");
		assertEquals(yearMonthList.get(2).getValue(), "03");
		assertEquals(yearMonthList.get(2).getName(), "03月");
		
	}
	/**
	 * 月報状況一覧リスト取得をテスト
	 */
	@Test
	public void testGetMonthlyReportStatusList(){

		
		// 月報状況一覧画面入力情報
		MonthlyReportStatusListForm form = new MonthlyReportStatusListForm();

		// 所属
		form.setAffiliation("");
		// 年
		form.setYear("2014");
		// 月
		form.setMonth("05");
		
		// 月報状況一覧リスト取得
		List<MonthlyReportStatusListVo> monList = serviceImpl.getMonthlyReportStatusList(form);
		
		// 月報状況一覧リストのサイズ
		assertEquals(monList.size(), 6);
		assertEquals(monList.get(0).getApplyNo(), "4001120140500");
		assertEquals(monList.get(0).getAffiliationName(), "BS3");
		assertEquals(monList.get(0).getUserId(), "4001");
		assertEquals(monList.get(0).getUserName(), "０１ＰＴＳ");
		assertEquals(monList.get(0).getApplyKbnName(), "月報");
		assertEquals(monList.get(0).getApplyDetail(), "2014年5月分");
		assertEquals(monList.get(0).getStatusName(), "作成中");
		assertEquals(monList.get(1).getApplyNo(), "4002120140500");
		assertEquals(monList.get(1).getAffiliationName(), "BS3");
		assertEquals(monList.get(1).getUserId(), "4002");
		assertEquals(monList.get(1).getUserName(), "０２ＰＴＳ");
		assertEquals(monList.get(1).getApplyKbnName(), "月報");
		assertEquals(monList.get(1).getApplyDetail(), "2014年5月分");
		assertEquals(monList.get(1).getStatusName(), "提出");
		assertEquals(monList.get(2).getApplyNo(), "4003120140500");
		assertEquals(monList.get(2).getAffiliationName(), "総務部");
		assertEquals(monList.get(2).getUserId(), "4003");
		assertEquals(monList.get(2).getUserName(), "０３ＰＴＳ");
		assertEquals(monList.get(2).getApplyKbnName(), "月報");
		assertEquals(monList.get(2).getApplyDetail(), "2014年5月分");
		assertEquals(monList.get(2).getStatusName(), "承認");
		assertEquals(monList.get(3).getApplyNo(), "4001220140504");
		assertEquals(monList.get(3).getAffiliationName(), "BS3");
		assertEquals(monList.get(3).getUserId(), "4001");
		assertEquals(monList.get(3).getUserName(), "０１ＰＴＳ");
		assertEquals(monList.get(3).getApplyKbnName(), "超勤振替申請");
		assertEquals(monList.get(3).getApplyDetail(), "休日勤務日：2014/5/4");
		assertEquals(monList.get(3).getStatusName(), "提出");
		
	}
}
