package argo.cost.attendanceOnHolidayRecord.dao;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.entity.HolidayAtendanceYotei;

/**
 * <p>
 * 休日出勤管理ＤＡＯ
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class AttendanceOnHolidayRecordDaoImpl implements AttendanceOnHolidayRecordDao {
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 休日振替勤務情報を取得
	 * 
	 * @param yearPeriod
	 *                  年度
	 * @param userId
	 *              氏名ID
	 * @return
	 *        休日振替勤務情報
	 */
	@Override
	public List<HolidayAtendanceYotei> getHolidayExchangeWorkList(String yearPeriod, String userId) {
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("atendanceBookDate", yearPeriod + "%");
		// 勤務日区分「休日振替勤務」をセット
		condition.addConditionEqual("workDayKbnMaster.code", CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE);
		// 休日振替勤務情報取得
		List<HolidayAtendanceYotei> kyukafurikaeList = baseDao.findResultList(condition, HolidayAtendanceYotei.class);

		return kyukafurikaeList;
	}

	/**
	 * 休日勤務情報を取得
	 * 
	 * @param yearPeriod
	 *                  年度
	 * @param userId
	 *              氏名ID
	 * @return
	 *        休日勤務情報
	 * @throws ParseException 
	 */
	@Override
	public List<HolidayAtendance> getHolidayWorkList(String yearPeriod, String userId) {
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("holidaySyukinDate", yearPeriod + "%");
		// 休日勤務情報取得
		List<HolidayAtendance> kyukaSyukinList = baseDao.findResultList(condition, HolidayAtendance.class);
		
		return kyukaSyukinList;
	}
}
