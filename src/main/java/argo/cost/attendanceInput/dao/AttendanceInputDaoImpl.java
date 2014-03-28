package argo.cost.attendanceInput.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.common.model.ListItem;

@Repository
public class AttendanceInputDaoImpl implements AttendanceInputDao {

	/**
	 * 休暇欠勤区分プルダウンリスト取得
	 * 
	 * @return 休暇欠勤区分プルダウンリスト
	 */
	@Override
	public List<ListItem> getHolidayLackingItem() {
		// TODO 自動生成されたメソッド・スタブ
		List<ListItem> resultList = new ArrayList<ListItem>();
		
		ListItem item1 = new ListItem();
		item1.setValue("01");
		item1.setName("全休(有給休暇)");
		resultList.add(item1);

		item1 = new ListItem();
		item1.setValue("02");
		item1.setName("半休(有給休暇)");
		resultList.add(item1);

		item1 = new ListItem();
		item1.setValue("03");
		item1.setName("時間休(有給休暇)");
		resultList.add(item1);
		
		return resultList;
	}

	/**
	 * 個人勤怠プロジェクト情報取得
	 * 
	 * 
	 * @return 個人勤怠プロジェクト情報
	 */
	@Override
	public List<ListItem> getWorkItemList() {
		
		// TODO 自動生成されたメソッド・スタブ
		List<ListItem> list = new ArrayList<ListItem>();
		
		ListItem itm = new ListItem();
		itm.setName("MUT");
		itm.setValue("01");
		list.add(itm);
		
		itm = new ListItem();
		itm.setName("SI");
		itm.setValue("02");
		list.add(itm);
		
		itm = new ListItem();
		itm.setName("BD");
		itm.setValue("03");
		list.add(itm);
		
		return list;
	}

	/**
	 * ロケーション情報取得
	 * 
	 * 
	 * @return ロケーション情報
	 */
	@Override
	public List<ListItem> getLocationItemList() {
		// TODO 自動生成されたメソッド・スタブ
		List<ListItem> list = new ArrayList<ListItem>();
		
		ListItem itm = new ListItem();
		itm.setName("中国");
		itm.setValue("01");
		list.add(itm);
		
		itm = new ListItem();
		itm.setName("日本");
		itm.setValue("02");
		list.add(itm);
		
		itm = new ListItem();
		itm.setName("米国");
		itm.setValue("03");
		list.add(itm);
		
		return list;
	}

	/**
	 * 休日勤務情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param yyyymmdd 日付
	 * @return 休日勤務情報
	 */
	@Override
	public HolidayRecord getHolidayRecord(String userId, String yyyymmdd) {
		// TODO 自動生成されたメソッド・スタブ
		HolidayRecord record = new HolidayRecord();
		
		record.setDate("20140329");
		record.setUserId("user01");
		record.setLimitDate("20140630");
		record.setExchangeDay("20140331");
		record.setTransferAppDay("");
		record.setPayOutYM("");
		record.setProcessKbn(0);
		record.setProcessDate("20140501");
		
		if (StringUtils.equals("20140329", yyyymmdd)) {
			return record;
		}
		
		return null;
	}

}
