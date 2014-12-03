package argo.cost.makeKyuyoFile;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.dao.BaseDao;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileIchiranVO;
import argo.cost.makeKyuyoFile.service.MakeKyuyoFileServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"}) 
public class MakeKyuyoFileTest {

	/**
	 * 給与システム用ファイル出力サビース	
	 */
	@Resource
	MakeKyuyoFileServiceImpl makeKyuyoFileServiceImpl;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 給与システム用出力ファイル一覧取得をテスト
	 */
	@Test
	public void testGetmakeKyuyoFileIchiranList(){
		
		// 状況プルダウンリスト取得
		List<MakeKyuyoFileIchiranVO> makeKyuyoFileIchiranVOList = makeKyuyoFileServiceImpl.getMadeFileNameList("4001");
		

		assertEquals(makeKyuyoFileIchiranVOList.size(), 2);
		assertEquals(makeKyuyoFileIchiranVOList.get(0).getMadeKyuyoFileName(), "201406 20140624192828");
		assertEquals(makeKyuyoFileIchiranVOList.get(0).getDealYearMonth(), "201405");
		assertEquals(makeKyuyoFileIchiranVOList.get(0).getCreatedUserName(), "０１ＰＴＳ");
		assertEquals(makeKyuyoFileIchiranVOList.get(0).getCreatedDateTime(), "2014/06/24 19:28:27.765");
	}
}
