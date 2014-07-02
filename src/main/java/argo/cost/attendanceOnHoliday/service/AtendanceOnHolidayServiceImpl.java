package argo.cost.attendanceOnHoliday.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendanceYotei;
import argo.cost.common.entity.ProjectBasic;
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
		BaseCondition condition = new BaseCondition();
		// 検索条件：休日勤務の日付
		condition.addConditionEqual("atendanceDate", currentDate);
		// 検索条件：社員ID
		condition.addConditionEqual("users.id", atendanceOnHolidayForm.getTaishoUserId());
			
		HolidayAtendanceYotei holidayInfo = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
		// 勤務日区分リストの設定
		condition = new BaseCondition();
		// 休日、休日振替勤務
		condition.addConditionIn("code", new String[] {CommonConstant.WORKDAY_KBN_KYUJITU, CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE});
		atendanceOnHolidayForm.setAtendanceDayKbnList(baseDao.findResultList(condition, WorkDayKbnMaster.class));
		// プロジェクトリストの設定
		atendanceOnHolidayForm.setProjCdList(baseDao.findAll(ProjectBasic.class));
		
		// 当前の日付の休日勤務情報が存在の場合
		if (holidayInfo != null) {
					
			// 勤務日付
			atendanceOnHolidayForm.setStrAtendanceDate(currentDate);
			// 勤務区分
			atendanceOnHolidayForm.setSelectedAtendanceDayKbn(holidayInfo.getWorkDayKbnMaster().getCode());
			// 勤務開始時間
			atendanceOnHolidayForm.setStrAtendanceTimeStat(CostDateUtils.formatTime(holidayInfo.getKinmuStartTime()));
			// 勤務終了時間
			atendanceOnHolidayForm.setStrAtendanceTimeEnd(CostDateUtils.formatTime(holidayInfo.getKinmuEndTime()));
			// 振替日
			if (StringUtils.isNotEmpty(holidayInfo.getFurikaeDate())) {
				atendanceOnHolidayForm.setStrHurikaeDate(CostDateUtils.formatDate(holidayInfo.getFurikaeDate(), CommonConstant.YYYY_MM_DD));
				// 曜日
				atendanceOnHolidayForm.setWeek(CostDateUtils.getWeekOfDate(CostDateUtils.toDate(holidayInfo.getFurikaeDate())));
			}
			
			// プロジェクト名
			atendanceOnHolidayForm.setSelectedProjCd(holidayInfo.getProjectBasic().getProjectCode());
			// 業務内容
			atendanceOnHolidayForm.setStrCommont(holidayInfo.getCommont());
			// 削除の表示フラグ
			atendanceOnHolidayForm.setShowFlag("1");
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
		
		// 当前の日付を検索条件として、DBの休日勤務情報有無をチェックする		
		BaseCondition condition = new BaseCondition();
		String loginId = atendanceOnHoliday.getUserId();
		String taishoId = atendanceOnHoliday.getTaishoUserId();
			
		// 検索条件：休日勤務の日付
		condition.addConditionEqual("atendanceDate", atendanceOnHoliday.getStrAtendanceDate().replaceAll("/", ""));
		// 検索条件：社員ID
		condition.addConditionEqual("users.id", taishoId);
			
		HolidayAtendanceYotei entity = baseDao.findSingleResult(condition, HolidayAtendanceYotei.class);
		
		// 更新フラグ
		boolean flag = true;
		// データが存在しない場合
		if (entity == null) {
			flag = false;
			// 休日勤務情報を追加する
			entity = new HolidayAtendanceYotei();
			entity.setCreatedUserId(loginId);               // 登録者
			entity.setCreatedDate(new Timestamp(System.currentTimeMillis())); // 登録時刻
		}
		// 勤務日の区分
		WorkDayKbnMaster workDayKbnMaster = baseDao.findById(atendanceOnHoliday.getSelectedAtendanceDayKbn(), WorkDayKbnMaster.class);
		entity.setWorkDayKbnMaster(workDayKbnMaster);
		// 社員番号:ユーザーIDを設定される
		entity.setUser(baseDao.findById(taishoId, Users.class));
		// プロジェクト名
		ProjectBasic projectBasic = baseDao.findById(atendanceOnHoliday.getSelectedProjCd(), ProjectBasic.class);
		entity.setProjectBasic(projectBasic);
		// 休日勤務予定日格式を変更して、設定される
		entity.setAtendanceDate(atendanceOnHoliday.getStrAtendanceDate().replace("/", ""));
		// 勤務開始時間
		entity.setKinmuStartTime(atendanceOnHoliday.getStrAtendanceTimeStat().replace(":", StringUtils.EMPTY));
		// 勤務終了時間
		entity.setKinmuEndTime(atendanceOnHoliday.getStrAtendanceTimeEnd().replace(":", StringUtils.EMPTY));
		// 振替日
		entity.setFurikaeDate(atendanceOnHoliday.getStrHurikaeDate().replace("/", ""));
		// 業務内容
		entity.setCommont(atendanceOnHoliday.getStrCommont());
		entity.setUpdatedUserId(loginId);               // 更新者
		entity.setUpdateDate(new Timestamp(System.currentTimeMillis())); // 更新時刻
		
		String strHolidayAtendanceSaveFlg = "1";
		try {
			// 更新の場合
			if (flag) {
				baseDao.update(entity);
			} else {
				baseDao.insert(entity);
			}
		} catch (Exception e ) {
			strHolidayAtendanceSaveFlg = "0";
			atendanceOnHoliday.putConfirmMsg("休日勤務入力データ登録失敗しました！");
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

		// 当前の日付の休日勤務予定情報を削除する
		BaseCondition deleteAtendanceOnHolidayCondition = new BaseCondition();
		// 検索条件：休日勤務の日付
		deleteAtendanceOnHolidayCondition.addConditionEqual("atendanceDate", strAtendanceDate.replace("/", ""));
		// 検索条件：社員ID
		deleteAtendanceOnHolidayCondition.addConditionEqual("users.id", userID);
		Integer deleteResult = baseDao.deleteByCondition(deleteAtendanceOnHolidayCondition, HolidayAtendanceYotei.class);
		
		return deleteResult;

	}

}
