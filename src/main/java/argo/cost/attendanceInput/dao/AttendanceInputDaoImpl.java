package argo.cost.attendanceInput.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

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

}
