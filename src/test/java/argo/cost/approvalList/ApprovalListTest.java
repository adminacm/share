package argo.cost.approvalList;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.approvalList.model.ApprovalListVO;
import argo.cost.approvalList.service.ApprovalListServiceImpl;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.model.ListItemVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class ApprovalListTest {

	// 承認一覧サビース	
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
		 String str="我是{0},我来自{1},今年{2}岁";
	       String[] arr={"中国人","北京","22"};
	       Matcher m=Pattern.compile("\\{(\\d)\\}").matcher(str);
	       while(m.find()){
	            str=str.replace(m.group(),arr[Integer.parseInt(m.group(1))]);
	        }

		System.out.println(str);
		
		// テストデータ
		StatusMaster statusInfo1 = new StatusMaster();
		statusInfo1.setCode("01");
		statusInfo1.setName("作成");

		StatusMaster statusInfo2 = new StatusMaster();
		statusInfo2.setCode("02");
		statusInfo2.setName("申請");

		StatusMaster statusInfo3 = new StatusMaster();
		statusInfo3.setCode("03");
		statusInfo3.setName("差戻");
		
		baseDao.insert(statusInfo1);
		baseDao.insert(statusInfo2);
		baseDao.insert(statusInfo3);


		// 状況プルダウンリスト取得
		List<ListItemVO> resultList = serviceImpl.getStatusList();

		assertEquals(resultList.size(), 4);
		assertEquals(resultList.get(0).getValue(), "");
		assertEquals(resultList.get(0).getName(), "すべて");
		assertEquals(resultList.get(1).getValue(), statusInfo1.getCode());
		assertEquals(resultList.get(1).getName(), statusInfo1.getName());
		assertEquals(resultList.get(2).getValue(), statusInfo2.getCode());
		assertEquals(resultList.get(2).getName(), statusInfo2.getName());
		assertEquals(resultList.get(3).getValue(), statusInfo3.getCode());
		assertEquals(resultList.get(3).getName(), statusInfo3.getName());
	}
	
	/**
	 * 承認一覧リスト取得をテスト
	 */
	@Test
	public void testGetApprovalList(){
		
		// 状況
		String status = "01";
		
		// 承認一覧リスト取得
		List<ApprovalListVO> approvalList = serviceImpl.getApprovalList(status);
		
		// 承認一覧リストのサイズ
		assertEquals(approvalList.size(), 4);
		
		assertEquals(approvalList.get(0).getApplyNo(), "user01120140300");
		assertEquals(approvalList.get(0).getApplyKbnName(), "月報");
		assertEquals(approvalList.get(0).getApplyDetail(), "2014年4月分");
		assertEquals(approvalList.get(0).getStatusName(), "提出");
		assertEquals(approvalList.get(0).getAffiliationName(), "ＢＳ２");
		assertEquals(approvalList.get(0).getUserName(), "あｘｘｘｘｘ");
		assertEquals(approvalList.get(1).getApplyNo(), "user01120140400");
		assertEquals(approvalList.get(1).getApplyKbnName(), "月報");
		assertEquals(approvalList.get(1).getApplyDetail(), "2014年5月分");
		assertEquals(approvalList.get(1).getStatusName(), "提出");
		assertEquals(approvalList.get(1).getAffiliationName(), "ＢＳ２");
		assertEquals(approvalList.get(1).getUserName(), "うｘｘｘｘｘ");
		
	}
}
