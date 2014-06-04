package argo.cost.attendanceOnHoliday.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.ProjectMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.entity.WorkDayKbnMaster;
import argo.cost.common.model.ListItemVO;
import argo.cost.common.utils.CostCheckUtil;

/**
 * 休日勤務入力画面DAOの実現
 *
 * @author COST argo Corporation.
 */
@Repository
public class AtendanceOnHolidayDaoImpl implements AtendanceOnHolidayDao {
	
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;	

	/**
	 * 当日休日勤務情報取得
	 * 
	 * @param userID
	 *            ユーザID
	 * @param currentDate  
	 *            当前の日付
	 * @return 当日休日勤務情報
	 */
	@Override
	public AtendanceOnHolidayForm atendanceOnHolidayDataGet(String userID, String currentDate) {
		
		// 当前の日付を検索条件として、DBの休日勤務情報有無をチェックする		
		HolidayAtendanceYotei holidayAtendanceYoteiEntity = new HolidayAtendanceYotei();
		BaseCondition selectCurrentDateAtendanceYumuCondition = new BaseCondition();
		// 検索条件：休日勤務の日付
		selectCurrentDateAtendanceYumuCondition.addConditionEqual("atendanceBookDate", currentDate);
		// 検索条件：社員ID
		selectCurrentDateAtendanceYumuCondition.addConditionEqual("users", baseDao.findById(userID, Users.class));
		
		HolidayAtendanceYotei holidayAtendanceYoteiResultinfo = baseDao.findSingleResult(selectCurrentDateAtendanceYumuCondition, HolidayAtendanceYotei.class);
		
		AtendanceOnHolidayForm atendanceOnHoliday = null;
		
		// 当前の日付の休日勤務情報が存在の場合
		if (holidayAtendanceYoteiResultinfo != null) {
			
			// 勤務日付
			atendanceOnHoliday.setStrAtendanceDate(currentDate);
			// 勤務区分
			atendanceOnHoliday.setSelectedAtendanceDayKbn(holidayAtendanceYoteiResultinfo.getWorkDayKbnMaster().getCode());
			// 勤務開始時間
			atendanceOnHoliday.setStrAtendanceTimeStat(holidayAtendanceYoteiResultinfo.getKinmuStartTime());
			// 勤務終了時間
			atendanceOnHoliday.setStrAtendanceTimeEnd(holidayAtendanceYoteiResultinfo.getKinmuEndTime());
			// 振替日
			atendanceOnHoliday.setStrHurikaeDate(holidayAtendanceYoteiResultinfo.getFurikaeDate());
			// プロジェクト名
			atendanceOnHoliday.setSelectedProjCd(holidayAtendanceYoteiResultinfo.getProjectMaster().getCode());
			// 業務内容
			atendanceOnHoliday.setStrCommont(holidayAtendanceYoteiResultinfo.getCommont());
		}

		return atendanceOnHoliday;
	}

	/**
	 * 勤務日区分リストを取得
	 * 
	 * @return 勤務日区分リスト
	 */
	@Override
	public ArrayList<WorkDayKbnMaster> getAtendanceDayKbnList() {


		// データベースから勤務日区分リストを検索する		
		ArrayList<WorkDayKbnMaster> atendanceDayKbnList = (ArrayList<WorkDayKbnMaster>) baseDao.findAll(WorkDayKbnMaster.class);
		
		return atendanceDayKbnList;
	}

	/**
	 * 休日勤務データの削除
	 * 
	 * @param strAtendanceDate
	 *            削除したい勤務データの日付
	 * @param userId
	 *            ユーザーID 
	 * @return　userId
	 *            勤務情報データを削除結果フラグ
	 */
	@Override
	public String deleteAtendanceOnHoliday(String strAtendanceDate,	String userID) {
		
		HolidayAtendanceYotei holidayAtendanceYoteiEntity = new HolidayAtendanceYotei();
				
		String  strDeleteKyujitukimuInfoFlg = "0";
		
		// 当前の日付の休日勤務予定情報を削除する
		BaseCondition deleteAtendanceOnHolidayCondition = new BaseCondition();
		// 検索条件：休日勤務の日付
		deleteAtendanceOnHolidayCondition.addConditionEqual("atendanceBookDate", strAtendanceDate);
		// 検索条件：社員ID
		deleteAtendanceOnHolidayCondition.addConditionEqual("users", baseDao.findById(userID, Users.class));
		int deleteResult = baseDao.deleteByCondition(deleteAtendanceOnHolidayCondition, HolidayAtendanceYotei.class);
		// 削除結果を戻す
		if (deleteResult != 0) {
			strDeleteKyujitukimuInfoFlg = "1";
		} 
		
		return strDeleteKyujitukimuInfoFlg;

	}
	
	/**
	 * 休日勤務データの保存
	 * 
	 * @param atendanceOnHoliday
	 *            入力した勤務情報
	 * @param userId
	 *            ユーザID
	 * @return　userId
	 *            勤務情報データを削除結果フラグ
	 */
	@Override
	public String saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHoliday, String userID) {
		
		HolidayAtendanceYotei holidayAtendanceYoteiEntity = new HolidayAtendanceYotei();
		
		// ユーザーID
		holidayAtendanceYoteiEntity.setUser(baseDao.findById(userID, Users.class));
		// 休日勤務予定日
		holidayAtendanceYoteiEntity.setAtendanceBookDate(atendanceOnHoliday.getStrAtendanceDate());
		// 勤務日の区分
		WorkDayKbnMaster workDayKbnMaster = baseDao.findById(atendanceOnHoliday.getSelectedAtendanceDayKbn(), WorkDayKbnMaster.class);
		workDayKbnMaster.setCode(atendanceOnHoliday.getSelectedAtendanceDayKbn());
		holidayAtendanceYoteiEntity.setWorkDayKbnMaster(workDayKbnMaster);
		// 勤務開始時間
		holidayAtendanceYoteiEntity.setKinmuStartTime(atendanceOnHoliday.getStrAtendanceTimeStat());
		// 勤務終了時間
		holidayAtendanceYoteiEntity	.setKinmuEndTime(atendanceOnHoliday.getStrAtendanceTimeEnd());
		// 振替日
		holidayAtendanceYoteiEntity.setFurikaeDate(atendanceOnHoliday.getStrHurikaeDate());
		
		// プロジェクト名
		ProjectMaster projMaster = baseDao.findById(atendanceOnHoliday.getSelectedProjCd(), ProjectMaster.class);
		projMaster.setCode(atendanceOnHoliday.getSelectedProjCd());
		holidayAtendanceYoteiEntity.setProjectMaster(projMaster);
		
		// 業務内容
		holidayAtendanceYoteiEntity.setCommont(atendanceOnHoliday.getStrCommont());
		
		baseDao.insert(holidayAtendanceYoteiEntity);
		if (null != atendanceOnHoliday && "U0001".equals(userID)) {
			return "1";
		} else {
			return "0";
		}

	}

	/**
	 * プロジェクト作業区分リストを取得
	 * 
	 * @return プロジェクト作業区分リスト
	 */
	@Override
	public ArrayList<ProjWorkMaster> getProjectWorkKbnList() {
		
		// データベースからプロジェクト作業区分リストを検索する		
		ArrayList<ProjWorkMaster> projectWorkKbnList = (ArrayList<ProjWorkMaster>) baseDao.findAll(ProjWorkMaster.class);
				
		return projectWorkKbnList;
	}

}
