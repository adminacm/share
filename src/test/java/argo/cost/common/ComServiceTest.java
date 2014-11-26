package argo.cost.common;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.common.entity.Users;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.ListItemVO;
import argo.cost.common.model.UserVO;
import argo.cost.common.service.ComService;

/**
 * プルダウンリスト取得テスト
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext_test.xml"}) 
public class ComServiceTest {

	// プルダウンリスト
	@Autowired
	ComService service;
	
	/**
	 * 状況プルダウンリスト取得テスト
	 */
	@Test
	public void testInitSession() {
		
		String loginMail = "user01";
		//  状況プルダウンリスト取得
		AppSession session = service.initSession(loginMail);
		
		assertEquals(session.getErrorFilePath(), null);  // エラーファイルパース
		assertEquals(session.getFileName(), null);  // ファイル名称
		assertEquals(session.getForm(), null);  // 当画面フォーム名
		assertEquals(session.getUrl(), null);  // 当画面URL
		
		// ユーザー情報
		UserVO user = session.getUserInfo();
		
		assertEquals(user.getUserId(), "4001");               // 社員番号
		assertEquals(user.getUserName(), "０１ＰＴＳ");           // 社員氏名
		assertEquals(user.getDairishaId(), "");               // 代理者ID
		assertEquals(user.getKinmuStartTime(), "0900");       // 勤務開始時刻
		assertEquals(user.getKinmuEndTime(), "1730");         // 勤務終了時刻
		assertEquals(user.getStandardShiftCd(), "090075");    // 標準シフトコード
		assertEquals(user.getKyugyoStartDate(), "");          // 休職開始日付
		assertEquals(user.getKyugyoEndDate(), "");            // 休職終了日付
		assertEquals(user.getLoginMailAdress(), "user01");     // ログインメール
		assertEquals(user.getNyushaDate(), "19970401");        // 入社日
		assertEquals(user.getPassword(), "123456");            // パース
		assertEquals(user.getTaishoUserId(), "4001");          // 対象者
		assertEquals(user.getTaishoUserName(), null);          // 対象者氏名
		assertEquals(user.getTaisyokuDate(), "");              // 対象日
	}
	
	/**
	 * 氏名プルダウンリスト取得テスト1
	 * 
	 * ユーザＩＤが代理先の場合
	 */
	@Test
	public void testGetUserNameList1() {
		
		// ユーザＩＤ
		String userId = "4001";
		
		// 氏名プルダウンリスト取得
		List<Users> userNameList = service.getUserNameList(userId);
		
		assertEquals(userNameList.size(), 3);
	}
	
	/**
	 * 氏名プルダウンリスト取得テスト2
	 * 
	 * ユーザＩＤが代理先以外の場合
	 */
	@Test
	public void testGetUserNameList2() {
		
		// ユーザＩＤ
		String userId = "4002";
		
		// 氏名プルダウンリスト取得
		List<Users> userNameList = service.getUserNameList(userId);
		
		assertEquals(userNameList.size(), 1);
	}
	
	/**
	 * 年度プルダウンリスト取得テスト
	 */
	@Test
	public void testGetYearList() {
		
		// 当年度
		Date date = new Date();
		
		// 氏名プルダウンリスト取得
		List<ListItemVO> yearList = null;
		try {
			yearList = service.getYearList(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		
		assertEquals(yearList.size(), 3);
	}

}
