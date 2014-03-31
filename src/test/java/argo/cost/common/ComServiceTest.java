package argo.cost.common;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.model.ListItem;
import argo.cost.common.service.ComServiceImpl;

/**
 * プルダウンリスト取得テスト
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class ComServiceTest {

	// プルダウンリスト
	@Resource
	ComServiceImpl service;
	
	/**
	 * 状況プルダウンリスト取得テスト
	 */
	@Test
	public void testGetStatusList() {
		
		//  状況プルダウンリスト取得
		List<ListItem> statusList = service.getStatusList();
		
		assertEquals(statusList.size(), 6);
	}
	
	/**
	 * 氏名プルダウンリスト取得テスト1
	 * 
	 * ユーザＩＤがnullの場合
	 */
	@Test
	public void testGetUserNameList1() {
		
		// ユーザＩＤ
		String userId = null;
		
		// 氏名プルダウンリスト取得
		List<ListItem> userNameList = service.getUserNameList(userId);
		
		assertEquals(userNameList.size(), 2);
	}
	
	/**
	 * 氏名プルダウンリスト取得テスト2
	 * 
	 * ユーザＩＤがnull以外の場合
	 */
	@Test
	public void testGetUserNameList2() {
		
		// ユーザＩＤ
		String userId = "li";
		
		// 氏名プルダウンリスト取得
		List<ListItem> userNameList = service.getUserNameList(userId);
		
		assertEquals(userNameList.size(), 4);
	}
	
	/**
	 * 年度プルダウンリスト取得テスト
	 */
	@Test
	public void testGetYearList() {
		
		// 当年度
		Date date = new Date();
		
		// 氏名プルダウンリスト取得
		List<ListItem> yearList = null;
		try {
			yearList = service.getYearList(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		
		assertEquals(yearList.size(), 4);
	}

	/**
	 * プロジェクト名プルダウンリスト取得テスト
	 */
	@Test
	public void testGetProjectName() {

		// ユーザＩＤ
		String userId = "li";
		// 日付
		Date date = new Date();
		
		// 氏名プルダウンリスト取得
		List<ListItem> projectList = service.getProjectNameList(userId, date);
		
		assertEquals(projectList.size(), 1);
	}
}
