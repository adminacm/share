package argo.cost.attendanceInput;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProject;
import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.attendanceInput.model.WorkTimeDetail;
import argo.cost.attendanceInput.service.AttendanceInputServiceImpl;
import argo.cost.common.model.ListItem;
import argo.cost.common.service.ComService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"}) 
public class AttendanceInputTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	// 勤怠入力
	@Resource
	AttendanceInputServiceImpl attS;

	// 共通
	@Resource
	ComService comService;
	
	/**
	 * 休暇欠勤区分プルダウンリスト取得をテスト
	 */
	@Test
	public void testGetHolidayLackingItem(){
		
		List<ListItem> resultList = new ArrayList<ListItem>();
		
		ListItem item = new ListItem();
		item.setValue("01");
		item.setName("全休(有給休暇)");
		resultList.add(item);

		item = new ListItem();
		item.setValue("02");
		item.setName("半休(有給休暇)");
		resultList.add(item);

		item = new ListItem();
		item.setValue("03");
		item.setName("時間休(有給休暇)");
		resultList.add(item);

		// 休暇欠勤区分プルダウンリスト取得
		List<ListItem> holidayLackingList = attS.getHolidayLackingItem();
		
		// 休暇欠勤区分プルダウンリスト
		assertEquals(holidayLackingList.size(), 3);
		assertEquals(holidayLackingList.get(0).getValue(), resultList.get(0).getValue());
		assertEquals(holidayLackingList.get(0).getName(), resultList.get(0).getName());
		assertEquals(holidayLackingList.get(1).getValue(), resultList.get(1).getValue());
		assertEquals(holidayLackingList.get(1).getName(), resultList.get(1).getName());
		assertEquals(holidayLackingList.get(2).getValue(), resultList.get(2).getValue());
		assertEquals(holidayLackingList.get(2).getName(), resultList.get(2).getName());
	}

	/**
	 * 個人勤怠プロジェクト情報取得をテスト
	 */
	@Test
	public void testGetWorkItemList(){
		

		List<ListItem> resultList = new ArrayList<ListItem>();
		
		ListItem item = new ListItem();
		item.setName("MUT");
		item.setValue("01");
		resultList.add(item);
		
		item = new ListItem();
		item.setName("SI");
		item.setValue("02");
		resultList.add(item);
		
		item = new ListItem();
		item.setName("BD");
		item.setValue("03");
		resultList.add(item);

		// 個人勤怠プロジェクト取得
		List<ListItem> workItemList = attS.getWorkItemList();
		
		// 個人勤怠プロジェクト
		assertEquals(workItemList.size(), 3);
		assertEquals(workItemList.get(0).getValue(), resultList.get(0).getValue());
		assertEquals(workItemList.get(0).getName(), resultList.get(0).getName());
		assertEquals(workItemList.get(1).getValue(), resultList.get(1).getValue());
		assertEquals(workItemList.get(1).getName(), resultList.get(1).getName());
		assertEquals(workItemList.get(2).getValue(), resultList.get(2).getValue());
		assertEquals(workItemList.get(2).getName(), resultList.get(2).getName());
	}

	/**
	 * ロケーション情報取得をテスト
	 */
	@Test
	public void testGetLocationItemList(){
		
		List<ListItem> resultList = new ArrayList<ListItem>();
		
		ListItem itm = new ListItem();
		itm.setName("中国");
		itm.setValue("01");
		resultList.add(itm);
		
		itm = new ListItem();
		itm.setName("日本");
		itm.setValue("02");
		resultList.add(itm);
		
		itm = new ListItem();
		itm.setName("米国");
		itm.setValue("03");
		resultList.add(itm);

		// ロケーション情報取得
		List<ListItem> locationList = attS.getLocationItemList();
		
		// ロケーション情報
		assertEquals(locationList.size(), 3);
		assertEquals(locationList.get(0).getValue(), resultList.get(0).getValue());
		assertEquals(locationList.get(0).getName(), resultList.get(0).getName());
		assertEquals(locationList.get(1).getValue(), resultList.get(1).getValue());
		assertEquals(locationList.get(1).getName(), resultList.get(1).getName());
		assertEquals(locationList.get(2).getValue(), resultList.get(2).getValue());
		assertEquals(locationList.get(2).getName(), resultList.get(2).getName());
	}

	/**
	 * 日付の変換処理をテスト1
	 */
	@Test
	public void testChangeYearMonth1(){
		
		// 変換フラグ
		String changeFlg = "last";
		
		// 現在日付
		String date = "20140205";
		
		// 変換後の年月
		String formatDate = null;
		try {
			formatDate = attS.changeDate(changeFlg, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 変換後日付
		assertEquals(formatDate, "20140204");
	}

	/**
	 * 日付の変換処理をテスト2
	 */
	@Test
	public void testChangeYearMonth2(){
		
		// 変換フラグ
		String changeFlg = "next";
		
		// 現在日付
		String date = "20140205";
		
		// 変換後の年月
		String formatDate = null;
		try {
			formatDate = attS.changeDate(changeFlg, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 変換後日付
		assertEquals(formatDate, "20140206");
	}

	/**
	 * 休日勤務情報取得をテスト
	 */
	@Test
	public void testGetHolidayRecord(){

		HolidayRecord record = new HolidayRecord();
		record.setDate("20140329");
		record.setUserId("user01");
		record.setLimitDate("20140630");
		record.setExchangeDay("20140331");
		record.setTransferAppDay("");
		record.setPayOutYM("");
		record.setProcessKbn(0);
		record.setProcessDate("20140501");
		record.setProjectCode("01");
		record.setProjectName("原価管理");
		record.setWorkNaiyo("定期メンテナンス作業");

		String userId = "user01";
		String date = "20140329";
		// ロケーション情報取得
		HolidayRecord info = attS.getHolidayRecord(userId, date);
		
		// ロケーション情報
		assertEquals(record.getLimitDate(), info.getLimitDate());
		assertEquals(record.getExchangeDay(), info.getExchangeDay());
		assertEquals(record.getTransferAppDay(), info.getTransferAppDay());
		assertEquals(record.getPayOutYM(), info.getPayOutYM());
		assertEquals(record.getProcessKbn(), info.getProcessKbn());
		assertEquals(record.getProcessDate(), info.getProcessDate());
		assertEquals(record.getProjectCode(), info.getProjectCode());
		assertEquals(record.getProjectName(), info.getProjectName());
		assertEquals(record.getWorkNaiyo(), info.getWorkNaiyo());
	}
	
	/**
	 * ユーザ作業情報取得をテスト
	 */
	@Test
	public void testGetProjectList(){

		String userId = "user01";
		String date = "20140329";
		
		// ロケーション情報取得
		List<AttendanceProject> list = null;
		try {
			list = attS.getProjectList(userId, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// ユーザ作業情報
		assertEquals(list.size(), 1);
		assertEquals(String.valueOf(list.get(0).getHours()), "3.5");
		assertEquals(list.get(0).getProjectItemList().size(), 1);
		assertEquals(list.get(0).getProjectId(), "01");
		assertEquals(list.get(0).getWorkId(), "01");
		assertEquals(list.get(0).getWorkItemList().size(), 3);
	}

	/**
	 * 就業データ取得をテスト
	 */
	@Test
	public void testGetWorkTimeDetail(){

		WorkTimeDetail record = new WorkTimeDetail();
		record.setUserId("user01");
		record.setWorkDate("20140329");
		record.setKinmuKbn("01");
		record.setShiftCode("0900");
		record.setKinmuSTime("09:00");
		record.setKinmuEtime("23:30");
		record.setSykaKetukinKbn("");
		record.setBikou("桜美林大学留学生管理システム保守：お客様問合せの対応");
		record.setFurikaeDate("");
		record.setSykaKetukinhours(0.0);
		record.setKinmuHours(0.0);
		record.setTyokinStime("18:00");
		record.setTyokinEtime("23:00");
		record.setTyokinHeijiHours(4.0);
		record.setTyokinHeijiTujyoHours(0.0);
		record.setTyokinKyujiHours(0.0);
		record.setSynyaKinmuHours(1.5);
		record.setStatus("01");

		String userId = "user01";
		String date = "20140329";
		// 就業データ取得
		WorkTimeDetail info = attS.getWorkTimeDetail(userId, date);
		
		// 就業データ
		assertEquals(record.getUserId(), info.getUserId());
		assertEquals(record.getWorkDate(), info.getWorkDate());
		assertEquals(record.getKinmuKbn(), info.getKinmuKbn());
		assertEquals(record.getShiftCode(), info.getShiftCode());
		assertEquals(record.getKinmuSTime(), info.getKinmuSTime());
		assertEquals(record.getKinmuEtime(), info.getKinmuEtime());
		assertEquals(record.getSykaKetukinKbn(), info.getSykaKetukinKbn());
		assertEquals(record.getBikou(), info.getBikou());
		assertEquals(record.getFurikaeDate(), info.getFurikaeDate());
		assertEquals(record.getSykaKetukinhours(), info.getSykaKetukinhours());
		assertEquals(record.getKinmuHours(), info.getKinmuHours());
		assertEquals(record.getTyokinStime(), info.getTyokinStime());
		assertEquals(record.getTyokinEtime(), info.getTyokinEtime());
		assertEquals(record.getTyokinHeijiHours(), info.getTyokinHeijiHours());
		assertEquals(record.getTyokinHeijiTujyoHours(), info.getTyokinHeijiTujyoHours());
		assertEquals(record.getTyokinKyujiHours(), info.getTyokinKyujiHours());
		assertEquals(record.getSynyaKinmuHours(), info.getSynyaKinmuHours());
		assertEquals(record.getStatus(), info.getStatus());
	}
	
	/**
	 * 勤怠入力画面情報設定をテスト
	 */
	@Test
	public void testSetAttForm(){
		
		AttendanceInputForm form = new AttendanceInputForm();
		String userId = "user01";
		String date = "20140328";
		// 勤怠入力画面情報設定
		try {
			attS.setAttForm(form, date, userId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 就業データ
		// 勤怠日付を設定
		assertEquals(form.getAttDate(), "20140328");
		// 勤怠日付（表示）を設定
		assertEquals(form.getAttDateShow(), "2014年03月28日（金）");
		// シフトコード
		assertEquals(form.getShiftCd(), "0900");
		// 勤務開始時刻
		assertEquals(form.getWorkSHour(), "09");
		assertEquals(form.getWorkSMinute(), "00");
		// 勤務終了時刻
		assertEquals(form.getWorkEHour(), "23");
		assertEquals(form.getWorkEMinute(), "30");
		// 休暇欠勤区分
		assertEquals(form.getKyukaKb(), "");
		// 超過勤務開始時刻
		assertEquals(form.getChoSTime(), "18:00");
		// 超過勤務終了時刻
		assertEquals(form.getChoETime(), "23:00");
		// 平日割増
		assertEquals(String.valueOf(form.getChoWeekday()), "4.0");
		// 深夜
		assertEquals(String.valueOf(form.getmNHours()), "1.5");
		// 休暇欠勤区分リスト
		assertEquals(form.getKyukakbList().size(), 3);
		// 個人倦怠プロジェクト情報リスト
		assertEquals(form.getProjectList().size(), 1);
		// ロケーション情報設定
		assertEquals(form.getLocationItemList().size(), 3);
	}

	/**
	 * 就業データを取得をテスト
	 */
	@Test
	public void testUpdateAttdendanceInfo(){
		
		AttendanceInputForm form = new AttendanceInputForm();

		form.setWorkSTime("1000");
		form.setWorkETime("2100");
		Integer updateFlg = attS.updateAttdendanceInfo(form);
		
		// ユーザ作業情報
		assertEquals(updateFlg, Integer.valueOf(1));
	}
}
