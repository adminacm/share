package argo.cost.monthlyReportStatusList;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApplyKbnMaster;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.ChokinKanri;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.entity.Users;
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
	 * 所属プルダウンリスト取得をテスト
	 */
	@Test
	public void testGetAffiliationList(){
		
		// 所属プルダウンリスト取得
		List<ListItemVO> affiliationList = serviceImpl.getAffiliationList();
		
		// 所属プルダウンリストのサイズ
		assertEquals(affiliationList.size(), 18);
		assertEquals(affiliationList.get(1).getValue(), "10");
		assertEquals(affiliationList.get(1).getName(), "総務部");
		assertEquals(affiliationList.get(2).getValue(), "11");
		assertEquals(affiliationList.get(2).getName(), "総務課");
		assertEquals(affiliationList.get(3).getValue(), "12");
		assertEquals(affiliationList.get(3).getName(), "経理課");
		
	}

	/**
	 * 状況プルダウンリスト取得テスト
	 */
	@Test
	public void testGetStatusList(){
		
		// 状況プルダウンリスト取得
		List<ListItemVO> affiliationList = serviceImpl.getStatusList();
		
		// 状況プルダウンリストのサイズ
		assertEquals(affiliationList.size(), 6);
		assertEquals(affiliationList.get(1).getValue(), "01");
		assertEquals(affiliationList.get(1).getName(), "作成中");
		assertEquals(affiliationList.get(2).getValue(), "02");
		assertEquals(affiliationList.get(2).getName(), "提出");
		
	}
	
	/**
	 * 月報状況一覧リスト取得をテスト
	 */
	@Test
	public void testGetMonthlyReportStatusList(){

		// テストデータ
		ApprovalManage approvalInfo1 = new ApprovalManage();
		approvalInfo1.setApplyNo("4001120140500");
		StatusMaster statusInfo1 = new StatusMaster();
		statusInfo1.setCode("01");
		approvalInfo1.setStatusMaster(statusInfo1);
		approvalInfo1.setApplyDetail("2014年5月分");
		ApplyKbnMaster applyKbn1 = new ApplyKbnMaster();
		applyKbn1.setCode("1");
		approvalInfo1.setApplyKbnMaster(applyKbn1);
		Users users1 = new Users();
		users1.setId("4001");
		approvalInfo1.setUser(users1);
		approvalInfo1.setAppYmd("20140500");
		
		ApprovalManage approvalInfo2 = new ApprovalManage();
		approvalInfo2.setApplyNo("4002120140500");
		StatusMaster statusInfo2 = new StatusMaster();
		statusInfo2.setCode("01");
		approvalInfo2.setStatusMaster(statusInfo2);
		approvalInfo2.setApplyDetail("2014年5月分");
		ApplyKbnMaster applyKbn2 = new ApplyKbnMaster();
		applyKbn2.setCode("1");
		approvalInfo2.setApplyKbnMaster(applyKbn2);
		Users user2 = new Users();
		user2.setId("4002");
		approvalInfo2.setUser(user2);
		approvalInfo2.setAppYmd("20140500");

		ApprovalManage approvalInfo3 = new ApprovalManage();
		approvalInfo3.setApplyNo("4003120140500");
		StatusMaster statusInfo3 = new StatusMaster();
		statusInfo3.setCode("01");
		approvalInfo3.setStatusMaster(statusInfo3);
		approvalInfo3.setApplyDetail("2014年5月分");
		ApplyKbnMaster applyKbn3 = new ApplyKbnMaster();
		applyKbn3.setCode("1");
		approvalInfo3.setApplyKbnMaster(applyKbn3);
		Users user3 = new Users();
		user3.setId("4003");
		approvalInfo3.setUser(user3);
		approvalInfo3.setAppYmd("20140500");
		
		ApprovalManage approvalInfo4 = new ApprovalManage();
		approvalInfo4.setApplyNo("4004220140505");
		StatusMaster statusInfo4 = new StatusMaster();
		statusInfo4.setCode("01");
		approvalInfo4.setStatusMaster(statusInfo4);
		approvalInfo4.setApplyDetail("2014年5月分");
		ApplyKbnMaster applyKbn4 = new ApplyKbnMaster();
		applyKbn4.setCode("2");
		approvalInfo4.setApplyKbnMaster(applyKbn4);
		Users user4 = new Users();
		user4.setId("4004");
		approvalInfo4.setUser(user4);
		approvalInfo4.setAppYmd("20140500");

		baseDao.insert(approvalInfo1);
		baseDao.insert(approvalInfo2);
		baseDao.insert(approvalInfo3);
		baseDao.insert(approvalInfo4);
		
		// 月報状況一覧画面入力情報
		MonthlyReportStatusListForm form = new MonthlyReportStatusListForm();

		// 所属
		form.setAffiliation("");
		// 状況
		form.setStatus("01");
		// 年
		form.setYear("2014");
		// 月
		form.setMonth("05");
		
		// 月報状況一覧リスト取得
		List<MonthlyReportStatusListVo> monList = serviceImpl.getMonthlyReportStatusList(form);
		
		// 月報状況一覧リストのサイズ
		assertEquals(monList.size(), 4);
		assertEquals(monList.get(0).getApplyNo(), "4001120140500");
		assertEquals(monList.get(0).getAffiliationName(), "BS3");
		assertEquals(monList.get(0).getUserId(), "4001");
		assertEquals(monList.get(0).getUserName(), "０１ＰＴＳ");
		assertEquals(monList.get(0).getApplyKbnName(), "月報");
		assertEquals(monList.get(0).getApplyDetail(), "2014年5月分");
		assertEquals(monList.get(0).getStatusName(), "作成中");
		
	}
	
	/**
	 * CSVファイル作成をテスト
	 */
	@Test
	public void testCreateCSVFile(){
		
		// ユーザ情報
		Users users = new Users();
		
		// 超勤管理
		ChokinKanri chokinInfo = new ChokinKanri();
		users.setId("4001");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140501");
		chokinInfo.setHimokuKbnCode("KN09");
		chokinInfo.setHours(new BigDecimal(1.0));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);
		
		chokinInfo = new ChokinKanri();
		users.setId("4001");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140502");
		chokinInfo.setHimokuKbnCode("KN10");
		chokinInfo.setHours(new BigDecimal(4.5));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4001");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140505");
		chokinInfo.setHimokuKbnCode("KN11");
		chokinInfo.setHours(new BigDecimal(1.5));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4001");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140510");
		chokinInfo.setHimokuKbnCode("KN12");
		chokinInfo.setHours(new BigDecimal(5.5));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4001");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140520");
		chokinInfo.setHimokuKbnCode("KN08");
		chokinInfo.setHours(new BigDecimal(1.0));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4001");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140515");
		chokinInfo.setHimokuKbnCode("KN13");
		chokinInfo.setHours(new BigDecimal(3.0));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4001");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140517");
		chokinInfo.setHimokuKbnCode("KN09");
		chokinInfo.setHours(new BigDecimal(1.5));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);
		
		chokinInfo = new ChokinKanri();
		users.setId("4002");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140503");
		chokinInfo.setHimokuKbnCode("KN09");
		chokinInfo.setHours(new BigDecimal(5.0));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);
		
		chokinInfo = new ChokinKanri();
		users.setId("4002");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140504");
		chokinInfo.setHimokuKbnCode("KN10");
		chokinInfo.setHours(new BigDecimal(1.5));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4002");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140506");
		chokinInfo.setHimokuKbnCode("KN11");
		chokinInfo.setHours(new BigDecimal(3.5));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4002");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140511");
		chokinInfo.setHimokuKbnCode("KN12");
		chokinInfo.setHours(new BigDecimal(5.0));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4002");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140520");
		chokinInfo.setHimokuKbnCode("KN12");
		chokinInfo.setHours(new BigDecimal(4.0));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4002");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140515");
		chokinInfo.setHimokuKbnCode("KN09");
		chokinInfo.setHours(new BigDecimal(3.5));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);

		chokinInfo = new ChokinKanri();
		users.setId("4002");
		chokinInfo.setUsers(users);
		chokinInfo.setChokinDate("20140517");
		chokinInfo.setHimokuKbnCode("KN09");
		chokinInfo.setHours(new BigDecimal(1.5));
		chokinInfo.setCsvOutputFlg(new BigDecimal(0));
		baseDao.insert(chokinInfo);
		
		// 月報状況一覧画面入力情報
		MonthlyReportStatusListForm form = new MonthlyReportStatusListForm();
		
		List<MonthlyReportStatusListVo> monthlyReportStatusList = new ArrayList<MonthlyReportStatusListVo>();
		MonthlyReportStatusListVo monthlyReportStatusInfo = new MonthlyReportStatusListVo();
		monthlyReportStatusInfo.setUserId("4001");
		monthlyReportStatusInfo.setApplyYm("201405");
		monthlyReportStatusList.add(monthlyReportStatusInfo);
		monthlyReportStatusInfo = new MonthlyReportStatusListVo();
		monthlyReportStatusInfo.setUserId("4002");
		monthlyReportStatusInfo.setApplyYm("201405");
		monthlyReportStatusList.add(monthlyReportStatusInfo);
		form.setMonthlyReportStatusList(monthlyReportStatusList );
		
		try {
			serviceImpl.createCSVFile(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
