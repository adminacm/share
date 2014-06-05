package argo.cost.attendanceOnHolidayRecord.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecord.dao.AttendanceOnHolidayRecordDao;
import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;
import argo.cost.attendanceOnHolidayRecord.model.HolidayExchangeWorkVO;
import argo.cost.attendanceOnHolidayRecord.model.HolidayWorkVO;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.Users;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 休日出勤管理サービス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Service
public class AttendanceOnHolidayRecordServiceImpl implements AttendanceOnHolidayRecordService {
	
	/**
	 * 休日出勤管理DAO
	 */
	@Autowired
	private AttendanceOnHolidayRecordDao attendanceOnHolidayRecordDao;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 氏名プルダウンリスト取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @return 氏名プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getUserNameList(String userId) {
		
		Users userInfo = baseDao.findById(userId, Users.class);
		
		Users dairiInfo = baseDao.findById(userInfo.getDairishaId(), Users.class);
		
		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		
		// ドロップダウン項目
		// 自分データを設定する
		ListItemVO item = new ListItemVO();
		item.setValue(userId);
		item.setName(userInfo.getUserName());
		resultList.add(item);

		// 代理入力データを設定する
		item = new ListItemVO();
		item.setValue(userInfo.getDairishaId());
		item.setName(dairiInfo.getUserName());
		resultList.add(item);
		
		return resultList;
	}
	
	/**
	 * 休日出勤管理情報をセット
	 * 
	 * @param form
	 *            休日出勤管理画面情報
	 * @throws ParseException 
	 */
	@Override
	public void setAttendanceOnHolidayRecordInfo(AttendanceOnHolidayRecordForm form) throws ParseException {

		// 年度を取得
		String strYearPeriod = form.getYearPeriod();
		
		// 氏名を取得
		String strUserName = form.getUserName();
		
		// 休日振替勤務情報を取得
		List<HolidayExchangeWorkVO> holidayExchangeWorkList = attendanceOnHolidayRecordDao.getHolidayExchangeWorkList(strYearPeriod, strUserName);
		// 休日勤務情報を取得
		List<HolidayWorkVO> holidayWorkList = attendanceOnHolidayRecordDao.getHolidayWorkList(strYearPeriod, strUserName);

		// 休日振替勤務情報をセット
		form.setHolidayExchangeWorkList(holidayExchangeWorkList);
		// 休日勤務情報をセット
		form.setHolidayOverWorkList(holidayWorkList);
	}
}
