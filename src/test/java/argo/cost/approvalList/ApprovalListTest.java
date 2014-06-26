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
		
		// 状況プルダウンリスト取得
		List<ListItemVO> resultList = serviceImpl.getStatusList();

		assertEquals(resultList.size(), 6);
		assertEquals(resultList.get(0).getValue(), "");
		assertEquals(resultList.get(0).getName(), "すべて");
		assertEquals(resultList.get(1).getValue(), "01");
		assertEquals(resultList.get(1).getName(), "作成中");
		assertEquals(resultList.get(2).getValue(), "02");
		assertEquals(resultList.get(2).getName(), "提出");
		assertEquals(resultList.get(3).getValue(), "03");
		assertEquals(resultList.get(3).getName(), "承認");
		assertEquals(resultList.get(4).getValue(), "04");
		assertEquals(resultList.get(4).getName(), "差戻");
		assertEquals(resultList.get(5).getValue(), "05");
		assertEquals(resultList.get(5).getName(), "処理済");
	}
	
	/**
	 * 承認一覧リスト取得をテスト
	 */
	@Test
	public void testGetApprovalList1(){
		
		// 状況
		String status = "";
		// 承認一覧リスト取得
		List<ApprovalListVO> approvalList = serviceImpl.getApprovalList(status);
		
		// 承認一覧リストのサイズ
		assertEquals(approvalList.size(), 6);
		
		assertEquals(approvalList.get(0).getApplyNo(), "4001120140500");
		assertEquals(approvalList.get(0).getApplyKbnName(), "月報");
		assertEquals(approvalList.get(0).getApplyDetail(), "2014年5月分");
		assertEquals(approvalList.get(0).getStatusName(), "作成中");
		assertEquals(approvalList.get(0).getAffiliationName(), "BS3");
		assertEquals(approvalList.get(0).getUserName(), "０１ＰＴＳ");
		
		assertEquals(approvalList.get(1).getApplyNo(), "4002120140500");
		assertEquals(approvalList.get(1).getApplyKbnName(), "月報");
		assertEquals(approvalList.get(1).getApplyDetail(), "2014年5月分");
		assertEquals(approvalList.get(1).getStatusName(), "提出");
		assertEquals(approvalList.get(1).getAffiliationName(), "BS3");
		assertEquals(approvalList.get(1).getUserName(), "０２ＰＴＳ");
		
		assertEquals(approvalList.get(2).getApplyNo(), "4003120140500");
		assertEquals(approvalList.get(2).getApplyKbnName(), "月報");
		assertEquals(approvalList.get(2).getApplyDetail(), "2014年5月分");
		assertEquals(approvalList.get(2).getStatusName(), "承認");
		assertEquals(approvalList.get(2).getAffiliationName(), "総務部");
		assertEquals(approvalList.get(2).getUserName(), "０３ＰＴＳ");
		
		assertEquals(approvalList.get(3).getApplyNo(), "4001220140504");
		assertEquals(approvalList.get(3).getApplyKbnName(), "超勤振替申請");
		assertEquals(approvalList.get(3).getApplyDetail(), "休日勤務日：2014/5/4");
		assertEquals(approvalList.get(3).getStatusName(), "提出");
		assertEquals(approvalList.get(3).getAffiliationName(), "BS3");
		assertEquals(approvalList.get(3).getUserName(), "０１ＰＴＳ");
		
		assertEquals(approvalList.get(4).getApplyNo(), "4001220140510");
		assertEquals(approvalList.get(4).getApplyKbnName(), "超勤振替申請");
		assertEquals(approvalList.get(4).getApplyDetail(), "休日勤務日：2014/5/10");
		assertEquals(approvalList.get(4).getStatusName(), "提出");
		assertEquals(approvalList.get(4).getAffiliationName(), "BS3");
		assertEquals(approvalList.get(4).getUserName(), "０１ＰＴＳ");
		
		assertEquals(approvalList.get(5).getApplyNo(), "4001220140518");
		assertEquals(approvalList.get(5).getApplyKbnName(), "超勤振替申請");
		assertEquals(approvalList.get(5).getApplyDetail(), "休日勤務日：2014/5/18");
		assertEquals(approvalList.get(5).getStatusName(), "提出");
		assertEquals(approvalList.get(5).getAffiliationName(), "BS3");
		assertEquals(approvalList.get(5).getUserName(), "０１ＰＴＳ");
	}
	
	/**
	 * 承認一覧リスト取得をテスト
	 */
	@Test
	public void testGetApprovalList2(){
		
		// 状況
		String status = "02";
		// 承認一覧リスト取得
		List<ApprovalListVO> approvalList = serviceImpl.getApprovalList(status);
		
		// 承認一覧リストのサイズ
		assertEquals(approvalList.size(), 4);
		
		assertEquals(approvalList.get(0).getApplyNo(), "4002120140500");
		assertEquals(approvalList.get(0).getApplyKbnName(), "月報");
		assertEquals(approvalList.get(0).getApplyDetail(), "2014年5月分");
		assertEquals(approvalList.get(0).getStatusName(), "提出");
		assertEquals(approvalList.get(0).getAffiliationName(), "BS3");
		assertEquals(approvalList.get(0).getUserName(), "０２ＰＴＳ");
		
		assertEquals(approvalList.get(1).getApplyNo(), "4001220140504");
		assertEquals(approvalList.get(1).getApplyKbnName(), "超勤振替申請");
		assertEquals(approvalList.get(1).getApplyDetail(), "休日勤務日：2014/5/4");
		assertEquals(approvalList.get(1).getStatusName(), "提出");
		assertEquals(approvalList.get(1).getAffiliationName(),  "BS3");
		assertEquals(approvalList.get(1).getUserName(), "０１ＰＴＳ");
		
		assertEquals(approvalList.get(2).getApplyNo(), "4001220140510");
		assertEquals(approvalList.get(2).getApplyKbnName(), "超勤振替申請");
		assertEquals(approvalList.get(2).getApplyDetail(), "休日勤務日：2014/5/10");
		assertEquals(approvalList.get(2).getStatusName(), "提出");
		assertEquals(approvalList.get(2).getAffiliationName(), "BS3");
		assertEquals(approvalList.get(2).getUserName(), "０１ＰＴＳ");
		
		assertEquals(approvalList.get(3).getApplyNo(), "4001220140518");
		assertEquals(approvalList.get(3).getApplyKbnName(), "超勤振替申請");
		assertEquals(approvalList.get(3).getApplyDetail(), "休日勤務日：2014/5/18");
		assertEquals(approvalList.get(3).getStatusName(), "提出");
		assertEquals(approvalList.get(3).getAffiliationName(), "BS3");
		assertEquals(approvalList.get(3).getUserName(), "０１ＰＴＳ");
	}
}
