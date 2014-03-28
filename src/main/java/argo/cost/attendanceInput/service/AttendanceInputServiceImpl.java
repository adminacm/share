package argo.cost.attendanceInput.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceInput.dao.AttendanceInputDao;
import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProject;
import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.attendanceInput.model.WorkTimeDetail;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.model.ListItem;
import argo.cost.common.service.ComService;
import argo.cost.common.utils.CostDateUtils;

@Service
public class AttendanceInputServiceImpl implements AttendanceInputService {
	
	/** 定数 */
	// 昨日
	private final String LAST = "last";
	
	// 明日
	private final String NEXT = "next";
	/**
	 * 共通サービスです。
	 */
	@Autowired
	protected ComService comService;
	
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

	/**
	 * 
	 * 日付の変換処理
	 * 
	 * @param changeFlg 変換フラグ
	 * @param date 日付
	 * @return 年月
	 * @throws ParseException 
	 */
	@Override
	public String changeDate(String changeFlg, String yyyymmdd) throws ParseException {

		String formatDate = "";
		Calendar calendar = new GregorianCalendar(); 
		Date date = CostDateUtils.toDate(yyyymmdd);
		calendar.setTime(date);
		 
		// 日付の←ボタンを押すと、前の月に表示が切り替わる
		if (LAST.equals(changeFlg)) {
			 
		    calendar.add(Calendar.DATE, -1);
			 
		} else if (NEXT.equals(changeFlg)) {
		 
		// 日付の→ボタンを押すと、次の月に表示が切り替わる
		    calendar.add(Calendar.DATE, 1);
		}
		 
		date = calendar.getTime();
			
		// 日付フォーマット
		SimpleDateFormat sdfYearM = new SimpleDateFormat(CommonConstant.YYYYMMDD);
		
		// 年月設定
		formatDate = sdfYearM.format(date);

		// 年月
		return formatDate;
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
		HolidayRecord record = attDao.getHolidayRecord(userId, yyyymmdd);
		return record;
	}

	/**
	 * ユーザ作業情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param yyyymmdd 日付
	 * @return ユーザ作業情報
	 * @throws ParseException 
	 */
	@Override
	public List<AttendanceProject> getProjectList(String userId, String yyyymmdd) throws ParseException {
		// TODO 自動生成されたメソッド・スタブ　Daoを利用する予定
		List<AttendanceProject> result = new ArrayList<AttendanceProject>();
		
		AttendanceProject pro = null;
		for (int i = 0; i < 1; i++) {
			pro = new AttendanceProject();
			pro.setHours(3.5);
			pro.setProjectItemList(comService.getProjectNameList(userId, CostDateUtils.toDate(yyyymmdd)));
			pro.setProjectId("01");
			pro.setWorkId("01");
			pro.setWorkItemList(getWorkItemList());
			result.add(pro);
		}
		
		return result;
	}

	/**
	 * 就業データを取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param yyyymmdd 日付
	 * @return 就業データ
	 */
	@Override
	public WorkTimeDetail getWorkTimeDetail(String userId, String yyyymmdd) {
		// TODO 自動生成されたメソッド・スタブ Daoへ移動予定
		WorkTimeDetail info = new WorkTimeDetail();
		info.setUserId(userId);
		info.setWorkDate(yyyymmdd);
		info.setKinmuKbn("01");
		info.setShiftCode("0900");
		info.setKinmuSTime("09:00");
		info.setKinmuEtime("23:30");
		info.setSykaKetukinKbn("");
		info.setBikou("桜美林大学留学生管理システム保守：お客様問合せの対応");
		info.setFurikaeDate("");
		info.setSykaKetukinhours(0.0);
		info.setKinmuHours(0.0);
		info.setTyokinStime("18:00");
		info.setTyokinEtime("23:00");
		info.setTyokinHeijiHours(4.0);
		info.setTyokinHeijiTujyoHours(0.0);
		info.setTyokinKyujiHours(0.0);
		info.setSynyaKinmuHours(1.5);
		info.setStatus("01");
		return info;
	}

	/**
	 * 勤怠入力画面情報設定
	 * 
	 * @param form 
	 * 				画面情報
	 * @param newDate 
	 * 				日付
	 * @param userId
	 * 			ユーザID
	 */
	@Override
	public void setAttForm(AttendanceInputForm form, String newDate, String userId) throws ParseException {
		
		// システム日付を取得
		String date = CostDateUtils.getNowDate();
		// 勤怠操作日は存在する場合
		if (!StringUtils.isEmpty(newDate)) {
			date = newDate;
		}
		
		// 就業データを取得
		WorkTimeDetail workDetail = getWorkTimeDetail(userId, date);

		// 勤怠日付を設定
		form.setAttDate(date);
		String attDate = CostDateUtils.formatDate(date,
				CommonConstant.YYYYMMDD_KANJI);
		// 曜日名
		String week = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(date));
		// 勤怠日付（表示）を設定
		form.setAttDateShow(attDate.concat("（").concat(week).concat("）"));
		// 祝日の場合
		if (CostDateUtils.isHoliday(date)) {

			// 休日勤務情報を取得
			HolidayRecord record = getHolidayRecord(userId, date);
			// 休日勤務情報は存在する場合
			if (record != null) {
				form.setHolidayRecord(record);
				form.setKinmuKun(2);
			} else {
				form.setKinmuKun(1);
			}
		}

		// シフトコード
		form.setShiftCd("0900");
		// 勤務開始時刻
		form.setWorkSTime(workDetail.getKinmuSTime());
		form.setWorkSHour(CostDateUtils.getHourOrMinute(workDetail.getKinmuSTime(), 0));
		form.setWorkSMinute(CostDateUtils.getHourOrMinute(workDetail.getKinmuSTime(), 1));
		// 勤務終了時刻
		form.setWorkETime(workDetail.getKinmuEtime());
		form.setWorkEHour(CostDateUtils.getHourOrMinute(workDetail.getKinmuEtime(), 0));
		form.setWorkEMinute(CostDateUtils.getHourOrMinute(workDetail.getKinmuEtime(), 1));
		// 休暇欠勤区分
		form.setKyukaKb(workDetail.getSykaKetukinKbn());
		// 休暇時間数
		form.setKyukaHours(workDetail.getSykaKetukinhours());
		// 勤務時間数
		form.setWorkHours(workDetail.getKinmuHours());
		// 超過勤務開始時刻
		form.setChoSTime(workDetail.getTyokinStime());
		// 超過勤務終了時刻
		form.setChoETime(workDetail.getTyokinEtime());
		// 平日割増
		form.setChoWeekday(workDetail.getTyokinHeijiHours());
		// 平日通常
		form.setChoWeekday_nomal(workDetail.getTyokinHeijiTujyoHours());
		// 休日
		form.setChoHoliday(workDetail.getTyokinKyujiHours());
		// 深夜
		form.setmNHours(workDetail.getSynyaKinmuHours());
		// 休暇欠勤区分リストを取得
		List<ListItem> kyukakbList = getHolidayLackingItem();
		form.setKyukakbList(kyukakbList);
		// 個人倦怠プロジェクト情報リストを作成
		List<AttendanceProject> prjList = getProjectList(userId, date);
		form.setProjectList(prjList);
		// ロケーション情報設定
		form.setLocationId(workDetail.getLocationCode());
		form.setLocationItemList(getLocationItemList());
	}

	/**
	 * 就業データを取得
	 * 
	 * @param form 
	 * 				画面情報
	 * @param newDate 
	 * 				日付
	 * @param userId
	 * 			ユーザID
	 */
	@Override
	public Integer updateAttdendanceInfo(AttendanceInputForm form) {
		// TODO 自動生成されたメソッド・スタブ
		
		// 更新成功の場合
		if (true) {
			return 1;
		}
		return 0;
	}
}
