package argo.cost.setup;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.setup.model.SetupForm;
import argo.cost.setup.service.SetupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class SetupTest {

	// 個人設定サビース
	@Autowired
	SetupService setupServiceImpl;
	
	/**
	 * 個人設定情報取得をテスト
	 * @throws ParseException 
	 */
	@Test
	public void testGetSetupInfo() throws ParseException{
		
		SetupForm setupForm = new SetupForm();
		setupForm.setUserId("4001");
		
		// 個人設定情報取得
		setupServiceImpl.getSetupInfo(setupForm);
		
		// 標準ｼﾌﾄ
		assertEquals(setupForm.getStandardShift(), "0900");
		
		// ユーザー名
		assertEquals(setupForm.getTaishoUserName(), "０１ＰＴＳ");
		
		// 勤務開始時刻
		assertEquals(setupForm.getWorkStartTime(), "09:00");
		
		// 勤務終了時刻
		assertEquals(setupForm.getWorkEndTime(), "17:30");
		
		// 入社日
		assertEquals(setupForm.getJoinDate(), "1997/4/1");
		
		// 休業開始日
		assertEquals(setupForm.getHolidayStart(), "2012/5/9");
		
		// 休業終了日
		assertEquals(setupForm.getHolidayEnd(), "2013/4/30");
		
		// 退職日
		assertEquals(setupForm.getOutDate(), "2013/8/30");
		
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
		setupInfo.setWorkStartTime("09:00");
		
		// 勤務終了時刻
		setupInfo.setWorkEndTime("17:30");
		
		// 入社日
		setupInfo.setJoinDate("1997/4/1");
		
		// 休業開始日
		setupInfo.setHolidayStart("2012/5/9");
		
		// 休業終了日
		setupInfo.setHolidayEnd("2013/4/30");
		
		// 退職日
		setupInfo.setOutDate("2013/8/30");
		
		// 個人設定変更情報取得
		setupServiceImpl.getSetupEditInfo(setupInfo);
		
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
		setupServiceImpl.changeShift(setupInfo);
		
		// 勤務開始時刻
		assertEquals(setupInfo.getWorkStartTime(), "0930");
		// 勤務開始時刻
		assertEquals(setupInfo.getWorkEndTime(),"1800");
		
	}

}
