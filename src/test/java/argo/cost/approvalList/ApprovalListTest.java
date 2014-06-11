package argo.cost.approvalList;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.approvalList.model.ApprovalListVO;
import argo.cost.approvalList.service.ApprovalListServiceImpl;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApplyKbnMaster;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.model.ListItemVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"}) 
public class ApprovalListTest {

	/**
	 * 承認一覧サビース	
	 */
	@Resource
	ApprovalListServiceImpl serviceImpl;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 状況プルダウンリスト取得をテスト
	 */
	@Test
	public void testGetStatusList(){
		
		// テストデータ
		StatusMaster statusInfo1 = new StatusMaster();
		statusInfo1.setCode("01");
		statusInfo1.setName("作成中");

		StatusMaster statusInfo2 = new StatusMaster();
		statusInfo2.setCode("02");
		statusInfo2.setName("提出");

		StatusMaster statusInfo3 = new StatusMaster();
		statusInfo3.setCode("03");
		statusInfo3.setName("承認");
		
		StatusMaster statusInfo4 = new StatusMaster();
		statusInfo4.setCode("04");
		statusInfo4.setName("差戻");
		
		StatusMaster statusInfo5 = new StatusMaster();
		statusInfo5.setCode("05");
		statusInfo5.setName("処理済");
		
		// 状況プルダウンリスト取得
		List<ListItemVO> resultList = serviceImpl.getStatusList();

		assertEquals(resultList.size(), 6);
		assertEquals(resultList.get(0).getValue(), "");
		assertEquals(resultList.get(0).getName(), "すべて");
		assertEquals(resultList.get(1).getValue(), statusInfo1.getCode());
		assertEquals(resultList.get(1).getName(), statusInfo1.getName());
		assertEquals(resultList.get(2).getValue(), statusInfo2.getCode());
		assertEquals(resultList.get(2).getName(), statusInfo2.getName());
		assertEquals(resultList.get(3).getValue(), statusInfo3.getCode());
		assertEquals(resultList.get(3).getName(), statusInfo3.getName());
		assertEquals(resultList.get(4).getValue(), statusInfo4.getCode());
		assertEquals(resultList.get(4).getName(), statusInfo4.getName());
		assertEquals(resultList.get(5).getValue(), statusInfo5.getCode());
		assertEquals(resultList.get(5).getName(), statusInfo5.getName());
	}
	
	/**
	 * 承認一覧リスト取得をテスト
	 */
	@Test
	public void testGetApprovalList1(){

		// テストデータ
		ApprovalManage approvalInfo1 = new ApprovalManage();
		approvalInfo1.setApplyNo("4001120140600");
		StatusMaster statusInfo1 = new StatusMaster();
		statusInfo1.setCode("01");
		approvalInfo1.setStatusMaster(statusInfo1);
		approvalInfo1.setApplyDetail("2014年6月分");
		ApplyKbnMaster applyKbn1 = new ApplyKbnMaster();
		applyKbn1.setCode("1");
		approvalInfo1.setApplyKbnMaster(applyKbn1);
		Users users1 = new Users();
		users1.setId("4001");
		approvalInfo1.setUser(users1);
		approvalInfo1.setAppYm("201406");
		
		ApprovalManage approvalInfo2 = new ApprovalManage();
		approvalInfo2.setApplyNo("4002120140600");
		StatusMaster statusInfo2 = new StatusMaster();
		statusInfo2.setCode("02");
		approvalInfo2.setStatusMaster(statusInfo2);
		approvalInfo2.setApplyDetail("2014年6月分");
		ApplyKbnMaster applyKbn2 = new ApplyKbnMaster();
		applyKbn2.setCode("1");
		approvalInfo2.setApplyKbnMaster(applyKbn2);
		Users user2 = new Users();
		user2.setId("4002");
		approvalInfo2.setUser(user2);
		approvalInfo2.setAppYm("201406");

		ApprovalManage approvalInfo3 = new ApprovalManage();
		approvalInfo3.setApplyNo("4003120140600");
		StatusMaster statusInfo3 = new StatusMaster();
		statusInfo3.setCode("03");
		approvalInfo3.setStatusMaster(statusInfo3);
		approvalInfo3.setApplyDetail("2014年6月分");
		ApplyKbnMaster applyKbn3 = new ApplyKbnMaster();
		applyKbn3.setCode("1");
		approvalInfo3.setApplyKbnMaster(applyKbn3);
		Users user3 = new Users();
		user3.setId("4003");
		approvalInfo3.setUser(user3);
		approvalInfo3.setAppYm("201406");
		
		ApprovalManage approvalInfo4 = new ApprovalManage();
		approvalInfo4.setApplyNo("4004220140605");
		StatusMaster statusInfo4 = new StatusMaster();
		statusInfo4.setCode("02");
		approvalInfo4.setStatusMaster(statusInfo4);
		approvalInfo4.setApplyDetail("休日勤務日：2014/6/5");
		ApplyKbnMaster applyKbn4 = new ApplyKbnMaster();
		applyKbn4.setCode("2");
		approvalInfo4.setApplyKbnMaster(applyKbn4);
		Users user4 = new Users();
		user4.setId("4004");
		approvalInfo4.setUser(user4);
		approvalInfo4.setAppYm("201406");
		
		ApprovalManage approvalInfo5 = new ApprovalManage();
		approvalInfo5.setApplyNo("4005220140605");
		StatusMaster statusInfo5 = new StatusMaster();
		statusInfo5.setCode("02");
		approvalInfo5.setStatusMaster(statusInfo5);
		approvalInfo5.setApplyDetail("休日勤務日：2014/6/5");
		ApplyKbnMaster applyKbn5 = new ApplyKbnMaster();
		applyKbn5.setCode("2");
		approvalInfo5.setApplyKbnMaster(applyKbn5);
		Users user5 = new Users();
		user5.setId("4005");
		approvalInfo5.setUser(user5);
		approvalInfo5.setAppYm("201406");

		baseDao.insert(approvalInfo1);
		baseDao.insert(approvalInfo2);
		baseDao.insert(approvalInfo3);
		baseDao.insert(approvalInfo4);
		baseDao.insert(approvalInfo5);
		
		// 状況
		String status = "";
		// 承認一覧リスト取得
		List<ApprovalListVO> approvalList1 = serviceImpl.getApprovalList(status);
		
		// 承認一覧リストのサイズ
		assertEquals(approvalList1.size(), 5);
		
		assertEquals(approvalList1.get(0).getApplyNo(), "4001120140600");
		assertEquals(approvalList1.get(0).getApplyKbnName(), "月報");
		assertEquals(approvalList1.get(0).getApplyDetail(), "2014年6月分");
		assertEquals(approvalList1.get(0).getStatusName(), "作成中");
		assertEquals(approvalList1.get(0).getAffiliationName(), "BS3");
		assertEquals(approvalList1.get(0).getUserName(), "０１ＰＴＳ");
		
		// 状況
		status = "02";
		// 承認一覧リスト取得
		List<ApprovalListVO> approvalList2 = serviceImpl.getApprovalList(status);
		
		// 承認一覧リストのサイズ
		assertEquals(approvalList2.size(), 3);
		
		assertEquals(approvalList2.get(0).getApplyNo(), "4002120140600");
		assertEquals(approvalList2.get(0).getApplyKbnName(), "月報");
		assertEquals(approvalList2.get(0).getApplyDetail(), "2014年6月分");
		assertEquals(approvalList2.get(0).getStatusName(), "提出");
		assertEquals(approvalList2.get(0).getAffiliationName(), "BS3");
		assertEquals(approvalList2.get(0).getUserName(), "０２ＰＴＳ");
		
	}
}
