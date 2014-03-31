package argo.cost.setup;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.setup.model.SetupForm;
import argo.cost.setup.service.SetupServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class SetupTest {

	// 個人設定サビース
	@Resource
	SetupServiceImpl setupS;
	
	/**
	 * 個人設定情報取得をテスト
	 */
	@Test
	public void testGetSetupInfo(){
		
		// 個人設定情報取得
		SetupForm setupInfo = setupS.getSetupInfo("caowy");
		
		// 代理入力者
		assertEquals(setupInfo.getAgentName(), "熊燕玲");
		
		// 標準ｼﾌﾄ
		assertEquals(setupInfo.getStandardShift(), "0900");
		
		// 勤務開始時刻
		assertEquals(setupInfo.getWorkStart(), "09:00");
		
		// 勤務終了時刻
		assertEquals(setupInfo.getWorkEnd(), "17:30");
		
		// 入社日
		assertEquals(setupInfo.getJoinDate(), "1997/4/1");
		
		// 休業開始日
		assertEquals(setupInfo.getHolidayStart(), "2012/5/9");
		
		// 休業終了日
		assertEquals(setupInfo.getHolidayEnd(), "2013/4/30");
		
		// 退職日
		assertEquals(setupInfo.getOutDate(), "2013/8/30");
		
	}
	
	/**
	 * 個人設定変更情報取得をテスト
	 */
	@Test
	public void testGetSetupEditInfo(){

		// 画面の個人設定情報
		SetupForm setupInfo = new SetupForm();
		
		// 代理入力者コード
		setupInfo.setAgentCd("xiongyl");
		
		// 標準ｼﾌﾄ
		setupInfo.setStandardShift("0900");
		
		// 勤務開始時刻
		setupInfo.setWorkStart("09:00");
		
		// 勤務終了時刻
		setupInfo.setWorkEnd("17:30");
		
		// 入社日
		setupInfo.setJoinDate("1997/4/1");
		
		// 休業開始日
		setupInfo.setHolidayStart("2012/5/9");
		
		// 休業終了日
		setupInfo.setHolidayEnd("2013/4/30");
		
		// 退職日
		setupInfo.setOutDate("2013/8/30");
		
		// 個人設定変更情報取得
		setupS.getSetupEditInfo(setupInfo);
		
		// 勤務開始時刻（時）
		assertEquals(setupInfo.getWorkStartH(), "09");
		// 勤務開始時刻（分）
		assertEquals(setupInfo.getWorkStartM(),"00");
		// 勤務終了時刻（時）
		assertEquals(setupInfo.getWorkEndH(), "17");
		// 勤務終了時刻（分）
		assertEquals(setupInfo.getWorkEndM(),"30");
		// 代理入力者リスト取得
		assertEquals(setupInfo.getAgentList().size(), 3);
		// 標準ｼﾌﾄリスト取得
		assertEquals(setupInfo.getStandardShiftList().size(), 7);
		
	}	
	
	/**
	 * 標準ｼﾌﾄ変更をテスト
	 */
	@Test
	public void testChangeShift(){

		// 画面の個人設定情報
		SetupForm setupInfo = new SetupForm();
		
		// 標準ｼﾌﾄ
		setupInfo.setStandardShift("0930");
		
		// 個人設定変更情報取得
		setupS.changeShift(setupInfo);
		
		// 勤務開始時刻（時）
		assertEquals(setupInfo.getWorkStartH(), "09");
		// 勤務開始時刻（分）
		assertEquals(setupInfo.getWorkStartM(),"30");
		// 勤務終了時刻（時）
		assertEquals(setupInfo.getWorkEndH(), "18");
		// 勤務終了時刻（分）
		assertEquals(setupInfo.getWorkEndM(),"00");
		
	}

}
