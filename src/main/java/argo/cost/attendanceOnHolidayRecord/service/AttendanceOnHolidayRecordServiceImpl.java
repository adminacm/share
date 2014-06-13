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
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.Users;
import argo.cost.common.model.ListItemVO;
import argo.cost.common.utils.CostDateUtils;

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
		
		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		
		// ドロップダウン項目
		// 自分データを設定する
		ListItemVO item = new ListItemVO();
		item.setValue(userId);
		item.setName(userInfo.getUserName());
		resultList.add(item);
		
		// 代理人があり
		if (!userInfo.getDairishaId().isEmpty()) {
			
			Users dairiInfo = baseDao.findById(userInfo.getDairishaId(), Users.class);
			
			// 代理入力データを設定する
			item = new ListItemVO();
			item.setValue(userInfo.getDairishaId());
			item.setName(dairiInfo.getUserName());
			resultList.add(item);
		}
		
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
		List<HolidayAtendanceYotei> kyukafurikaeList = attendanceOnHolidayRecordDao.getHolidayExchangeWorkList(strYearPeriod, strUserName);

		// 休日振替勤務リスト
		List<HolidayExchangeWorkVO> holidayExchangeWorkList = new ArrayList<HolidayExchangeWorkVO>();
		HolidayExchangeWorkVO holidayExchangeWork = null;
		
		// 休日振替勤務情報あり
		if (kyukafurikaeList.size() > 0) {
			
			for (HolidayAtendanceYotei kyukafurikaeInfo : kyukafurikaeList) {
				
				holidayExchangeWork = new HolidayExchangeWorkVO();
				
				//　休日振替勤務日付
				holidayExchangeWork.setHolidayTurnedWorkingDate(CostDateUtils.formatDate(kyukafurikaeInfo.getAtendanceBookDate(), CommonConstant.YYYY_MM_DD));
				// 振替休日
				holidayExchangeWork.setWorkingDayTurnedHolidayDate(CostDateUtils.formatDate(kyukafurikaeInfo.getFurikaeDate(), CommonConstant.YYYY_MM_DD));
				
				holidayExchangeWorkList.add(holidayExchangeWork);
			}
		}
		// 休日振替勤務情報をセット
		form.setHolidayExchangeWorkList(holidayExchangeWorkList);
		
		// 休日勤務情報を取得
		List<HolidayAtendance> kyukaSyukinList = attendanceOnHolidayRecordDao.getHolidayWorkList(strYearPeriod, strUserName);

		// 休日勤務リスト
		List<HolidayWorkVO> holidayWorkList = new ArrayList<HolidayWorkVO>();
		HolidayWorkVO holidayWork = null;
		
		// 休日勤務情報あり
		if (kyukaSyukinList.size() > 0) {
			
			for (HolidayAtendance kyukaSyukinInfo : kyukaSyukinList) {

				holidayWork = new HolidayWorkVO();

				//　休日勤務日付
				holidayWork.setHolidayOverWorkDate(CostDateUtils.formatDate(kyukaSyukinInfo.getHolidaySyukinDate(), CommonConstant.YYYY_MM_DD));
				// 代休期限
				holidayWork.setTurnedHolidayEndDate(CostDateUtils.formatDate(kyukaSyukinInfo.getDaikyuGetShimekiriDate(), CommonConstant.YYYY_MM_DD));
				// 代休日
				if (CostDateUtils.isValidDate(kyukaSyukinInfo.getDaikyuDate(), CommonConstant.YYYYMMDD)) {
					holidayWork.setTurnedHolidayDate(CostDateUtils.formatDate(kyukaSyukinInfo.getDaikyuDate(), CommonConstant.YYYY_MM_DD));
				} else {
					holidayWork.setTurnedHolidayDate(kyukaSyukinInfo.getDaikyuDate());
				}
				// 超勤振替申請日
				holidayWork.setOverWorkTurnedReqDate(CostDateUtils.formatDate(kyukaSyukinInfo.getFirikaeShiseiDate(), CommonConstant.YYYY_MM_DD));
				
				holidayWorkList.add(holidayWork);
			}
		}
		// 休日勤務情報をセット
		form.setHolidayOverWorkList(holidayWorkList);
	}
}
