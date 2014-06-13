package argo.cost.attendanceOnHolidayRecord.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;
import argo.cost.attendanceOnHolidayRecord.model.HolidayExchangeWorkVO;
import argo.cost.attendanceOnHolidayRecord.model.HolidayWorkVO;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;
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
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 休日出勤管理情報をセット
	 * 
	 * @param form
	 *            休日出勤管理画面情報
	 * @throws ParseException 
	 */
	@Override
	public void setAttendanceOnHolidayRecordInfo(AttendanceOnHolidayRecordForm form) throws ParseException {

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", form.getUserName());
		// 日付
		condition.addConditionLike("atendanceBookDate", form.getYearPeriod() + "%");
		// 勤務日区分「休日振替勤務」をセット
		condition.addConditionEqual("workDayKbnMaster.code", CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE);
		// 休日振替勤務情報を取得
		List<HolidayAtendanceYotei> kyukafurikaeList = baseDao.findResultList(condition, HolidayAtendanceYotei.class);

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
		
		
		// 検索条件
		condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", form.getUserName());
		// 日付
		condition.addConditionLike("holidaySyukinDate", form.getYearPeriod() + "%");
		// 休日勤務情報取得
		List<HolidayAtendance> kyukaSyukinList = baseDao.findResultList(condition, HolidayAtendance.class);

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
