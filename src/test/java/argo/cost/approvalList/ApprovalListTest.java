package argo.cost.approvalList;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.approvalList.model.ApprovalListVo;
import argo.cost.approvalList.service.ApprovalListServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class ApprovalListTest {

	// 承認一覧サビース	
	@Resource
	ApprovalListServiceImpl appS;

	/**
	 * 承認一覧リスト取得をテスト
	 */
	@Test
	public void testGetApprovalList(){
		
		// 状況
		String status = "01";
		
		// 承認一覧リスト取得
		List<ApprovalListVo> approvalList = appS.getApprovalList(status);
		
		// 承認一覧リストのサイズ
		assertEquals(approvalList.size(), 4);
		
		assertEquals(approvalList.get(0).getApplyNo(), "user01120140300");
		assertEquals(approvalList.get(0).getApplyKbnName(), "月報");
		assertEquals(approvalList.get(0).getApplyDetail(), "2014年4月分");
		assertEquals(approvalList.get(0).getStatus(), "提出");
		assertEquals(approvalList.get(0).getAffiliation(), "ＢＳ２");
		assertEquals(approvalList.get(0).getName(), "あｘｘｘｘｘ");
		assertEquals(approvalList.get(1).getApplyNo(), "user01120140400");
		assertEquals(approvalList.get(1).getApplyKbnName(), "月報");
		assertEquals(approvalList.get(1).getApplyDetail(), "2014年5月分");
		assertEquals(approvalList.get(1).getStatus(), "提出");
		assertEquals(approvalList.get(1).getAffiliation(), "ＢＳ２");
		assertEquals(approvalList.get(1).getName(), "うｘｘｘｘｘ");
		
	}
}
