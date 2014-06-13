package argo.cost.attendanceOnHoliday.service;

import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.ProjectMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.entity.WorkDayKbnMaster;
import argo.cost.common.utils.CostDateUtils;

/**
 * 休日勤務入力画面サービスのインタフェースの実現
 *
 * @author COST argo Corporation.
 */
@Service
public class AtendanceOnHolidayServiceImpl implements AtendanceOnHolidayService {
	
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;


	// 勤務日区分のプルダウンリストを取得
	@Override
	public ArrayList<WorkDayKbnMaster> getAtendanceDayKbnList() {
		

		// データベースから勤務日区分リストを検索する		
		ArrayList<WorkDayKbnMaster> atendanceDayKbnList = (ArrayList<WorkDayKbnMaster>) baseDao.findAll(WorkDayKbnMaster.class);
		
		return atendanceDayKbnList;
	}

	/**
	 * ユーザーがこの日休日勤務データ設定
	 * 
	 * @param atendanceOnHolidayForm
	 *            休日勤務画面情報
	 * @param currentDate
	 *            勤怠入力画面から渡した休日の日付
	 *            
	 * @throws ParseException 
	 * 
	 */
	@Override
	public void setAtendanceOnHolidayInfo(AtendanceOnHolidayForm atendanceOnHolidayForm, String currentDate) 
			throws ParseException {
		
		// 当前の日付を検索条件として、DBの休日勤務情報有無をチェックする		
		BaseCondition selectCurrentDateAtendanceYumuCondition = new BaseCondition();
		// 検索条件：休日勤務の日付
		selectCurrentDateAtendanceYumuCondition.addConditionEqual("atendanceBookDate", currentDate);
		// 検索条件：社員ID
		selectCurrentDateAtendanceYumuCondition.addConditionEqual("users.id", atendanceOnHolidayForm.getUserId());
			
		HolidayAtendanceYotei holidayAtendanceYoteiResultinfo = baseDao.findSingleResult(selectCurrentDateAtendanceYumuCondition, HolidayAtendanceYotei.class);
		// 勤務日区分リストの設定
		atendanceOnHolidayForm.setAtendanceDayKbnList(baseDao.findAll(WorkDayKbnMaster.class));
		// プロジェクトリストの設定
		atendanceOnHolidayForm.setProjCdList(baseDao.findAll(ProjectMaster.class));
		
		// 当前の日付の休日勤務情報が存在の場合
		if (holidayAtendanceYoteiResultinfo != null) {
					
			// 勤務日付
			atendanceOnHolidayForm.setStrAtendanceDate(currentDate);
			// 勤務区分
			atendanceOnHolidayForm.setSelectedAtendanceDayKbn(holidayAtendanceYoteiResultinfo.getWorkDayKbnMaster().getCode());
			// 勤務開始時間
			atendanceOnHolidayForm.setStrAtendanceTimeStat(holidayAtendanceYoteiResultinfo.getKinmuStartTime());
			// 勤務終了時間
			atendanceOnHolidayForm.setStrAtendanceTimeEnd(holidayAtendanceYoteiResultinfo.getKinmuEndTime());
			// 振替日
			atendanceOnHolidayForm.setStrHurikaeDate(holidayAtendanceYoteiResultinfo.getFurikaeDate());
			// プロジェクト名
			atendanceOnHolidayForm.setSelectedProjCd(holidayAtendanceYoteiResultinfo.getProjectMaster().getCode());
			// 業務内容
			atendanceOnHolidayForm.setStrCommont(holidayAtendanceYoteiResultinfo.getCommont());
			}


		// 休日勤務日付格式の設定
		String attDate = CostDateUtils.formatDate(currentDate, CommonConstant.YYYYMMDD_KANJI);
		String weekday = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(currentDate));
		atendanceOnHolidayForm.setStrAtendanceDate(CostDateUtils.formatDate(currentDate, CommonConstant.YYYY_MM_DD));
		atendanceOnHolidayForm.setStrAtendanceDateShow(attDate.concat("(").concat(weekday).concat(")"));
	}

	/**
	 * 休日勤務入力画面の保存処理
	 * 
	 * @param atendanceOnHoliday
	 *            休日勤務画面情報
	 * @param UserID
	 *            ユーザーID
	 *            
	 * @return　saveFlg
	 *            勤務情報データの保存結果フラグ
	 */
	@Override 
	public String saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHoliday) {
		
	   HolidayAtendanceYotei holidayAtendanceYoteiEntity = new HolidayAtendanceYotei();
		
	   String userId = atendanceOnHoliday.getUserId();
		
		// 勤務日の区分
	   WorkDayKbnMaster workDayKbnMaster = new WorkDayKbnMaster();
	   workDayKbnMaster.setCode(atendanceOnHoliday.getSelectedAtendanceDayKbn());
		holidayAtendanceYoteiEntity.setWorkDayKbnMaster(workDayKbnMaster);
		// 社員番号:ユーザーIDを設定される
		holidayAtendanceYoteiEntity.setUser(baseDao.findById(userId, Users.class));
		// プロジェクト名
		ProjectMaster projMaster = new ProjectMaster();
		projMaster.setCode(atendanceOnHoliday.getSelectedProjCd());
		holidayAtendanceYoteiEntity.setProjectMaster(projMaster);
		// 休日勤務予定日格式を変更して、設定される
		holidayAtendanceYoteiEntity.setAtendanceBookDate(atendanceOnHoliday.getStrAtendanceDate().replace("/", ""));
		// 勤務開始時間
		holidayAtendanceYoteiEntity.setKinmuStartTime(atendanceOnHoliday.getStrAtendanceTimeStat());
		// 勤務終了時間
		holidayAtendanceYoteiEntity	.setKinmuEndTime(atendanceOnHoliday.getStrAtendanceTimeEnd());
		// 振替日
		holidayAtendanceYoteiEntity.setFurikaeDate(atendanceOnHoliday.getStrHurikaeDate().replace("/", ""));
		// 業務内容
		holidayAtendanceYoteiEntity.setCommont(atendanceOnHoliday.getStrCommont());
		
		String strHolidayAtendanceSaveFlg = "0";
		try {
			baseDao.insert(holidayAtendanceYoteiEntity);
			strHolidayAtendanceSaveFlg = "1";
		} catch (Exception e ) {
			strHolidayAtendanceSaveFlg = "0";
			System.out.print("休日勤務入力データ登録失敗しました！");
		}
		
		return strHolidayAtendanceSaveFlg;
	}

	/**
	 * 休日勤務入力画面の削除処理
	 * 
	 * @param strAtendanceDate
	 *            休日勤務日付
	 * @param UserID
	 *            ユーザーID
	 *            
	 * @return　deleteFlg
	 *            勤務情報データの保存結果フラグ
	 */
	@Override
	public Integer deleteAtendanceOnHoliday(String strAtendanceDate, String userID) {

		Integer  intDeleteKyujitukimuInfoFlg = 0;
		
		// 当前の日付の休日勤務予定情報を削除する
		BaseCondition deleteAtendanceOnHolidayCondition = new BaseCondition();
		// 検索条件：休日勤務の日付
		deleteAtendanceOnHolidayCondition.addConditionEqual("atendanceBookDate", strAtendanceDate.replace("/", ""));
		// 検索条件：社員ID
		deleteAtendanceOnHolidayCondition.addConditionEqual("users.id", userID);
		int deleteResult = baseDao.deleteByCondition(deleteAtendanceOnHolidayCondition, HolidayAtendanceYotei.class);
		// 削除結果を戻す
		if (deleteResult != 0) {
			intDeleteKyujitukimuInfoFlg = 1;
		} 
		
		return intDeleteKyujitukimuInfoFlg;

	}

	/**
	 * プロジェクト作業区分リスト取得処理
	 * 
	 *            
	 * @return　projectWorkKbnList
	 *            プロジェクト作業区分リスト
	 */
	@Override
	public ArrayList<ProjWorkMaster> getProjectWorkKbnList() {
		
		// データベースからプロジェクト作業区分リストを検索する		
		ArrayList<ProjWorkMaster> projectWorkKbnList = (ArrayList<ProjWorkMaster>) baseDao.findAll(ProjWorkMaster.class);
						
		return projectWorkKbnList;
	}

}
