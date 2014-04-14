package argo.cost.approvalList;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.approvalList.model.ApprovalListVo;
import argo.cost.approvalList.service.ApprovalListServiceImpl;
import argo.cost.common.model.entity.ApprovalList;

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
		
		List<ApprovalList> appList = new ArrayList<ApprovalList>();
		
		ApprovalList appInfo = new ApprovalList();
		appInfo.setApplyKbn("月報");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("提出");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("あｘｘｘｘｘ");
		appList.add(appInfo);
		
		appInfo = new ApprovalList();
		appInfo.setApplyKbn("月報");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("提出");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("うｘｘｘｘｘ");
		appList.add(appInfo);
		
		// 状況
		String status = "01";
		
		// 承認一覧リスト取得
		List<ApprovalListVo> approvalList = appS.getApprovalList(status);
		
		// 承認一覧リストのサイズ
		assertEquals(approvalList.size(), 4);
		assertEquals(approvalList.get(0).getApplyKbnName(), appList.get(0).getApplyKbn());
		assertEquals(approvalList.get(0).getApplyDetail(), appList.get(0).getApplyDetail());
		assertEquals(approvalList.get(0).getStatus(), appList.get(0).getStatus());
		assertEquals(approvalList.get(0).getAffiliation(), appList.get(0).getAffiliation());
		assertEquals(approvalList.get(0).getName(), appList.get(0).getName());
		
		assertEquals(approvalList.get(1).getApplyKbnName(), appList.get(1).getApplyKbn());
		assertEquals(approvalList.get(1).getApplyDetail(), appList.get(1).getApplyDetail());
		assertEquals(approvalList.get(1).getStatus(), appList.get(1).getStatus());
		assertEquals(approvalList.get(1).getAffiliation(), appList.get(1).getAffiliation());
		assertEquals(approvalList.get(1).getName(), appList.get(1).getName());
		
	}
}
