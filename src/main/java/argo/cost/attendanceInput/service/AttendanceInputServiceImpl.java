package argo.cost.attendanceInput.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceInput.dao.AttendanceInputDao;
import argo.cost.common.model.ListItem;

@Service
public class AttendanceInputServiceImpl implements AttendanceInputService {

	@Autowired
	AttendanceInputDao attDao;
	/**
	 * 休暇欠勤区分プルダウンリスト取得
	 * 
	 * @return 休暇欠勤区分プルダウンリスト
	 */
	@Override
	public List<ListItem> getHolidayLackingItem() {
		
		return attDao.getHolidayLackingItem();
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
		List<ListItem> resultList = attDao.getWorkItemList();
		return resultList;
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
		List<ListItem> resultList = attDao.getLocationItemList();
		return resultList;
	}

}
