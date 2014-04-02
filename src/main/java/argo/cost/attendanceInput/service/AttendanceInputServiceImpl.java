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
import argo.cost.attendanceInput.model.ShiftInfo;
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
	 * @param changeFlg
	 *            変換フラグ
	 * @param date
	 *            日付
	 * @return 年月
	 * @throws ParseException
	 */
	@Override
	public String changeDate(String changeFlg, String yyyymmdd)
			throws ParseException {

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
		SimpleDateFormat sdfYearM = new SimpleDateFormat(
				CommonConstant.YYYYMMDD);

		// 年月設定
		formatDate = sdfYearM.format(date);

		// 年月
		return formatDate;
	}

	/**
	 * 休日勤務情報を取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param yyyymmdd
	 *            日付
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
	 *            ユーザID
	 * @param yyyymmdd
	 *            日付
	 * @return ユーザ作業情報
	 * @throws ParseException
	 */
	@Override
	public List<AttendanceProject> getProjectList(String userId, String yyyymmdd)
			throws ParseException {
		// TODO 自動生成されたメソッド・スタブ　Daoを利用する予定
		List<AttendanceProject> result = new ArrayList<AttendanceProject>();

		AttendanceProject pro = null;
		for (int i = 0; i < 1; i++) {
			pro = new AttendanceProject();
			pro.setHours(3.5);
			pro.setProjectItemList(comService.getProjectNameList(userId,
					CostDateUtils.toDate(yyyymmdd)));
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
	 *            ユーザID
	 * @param yyyymmdd
	 *            日付
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
	 *            画面情報
	 * @param newDate
	 *            日付
	 * @param userId
	 *            ユーザID
	 */
	@Override
	public void setAttForm(AttendanceInputForm form, String newDate,
			String userId) throws ParseException {

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
		form.setWorkSHour(CostDateUtils.getHourOrMinute(
				workDetail.getKinmuSTime(), 0));
		form.setWorkSMinute(CostDateUtils.getHourOrMinute(
				workDetail.getKinmuSTime(), 1));
		// 勤務終了時刻
		form.setWorkETime(workDetail.getKinmuEtime());
		form.setWorkEHour(CostDateUtils.getHourOrMinute(
				workDetail.getKinmuEtime(), 0));
		form.setWorkEMinute(CostDateUtils.getHourOrMinute(
				workDetail.getKinmuEtime(), 1));
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
	 *            画面情報
	 * @param newDate
	 *            日付
	 * @param userId
	 *            ユーザID
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

	/**
	 * 各種値算出
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @throws ParseException
	 */
	@Override
	public void calcWorkingRec(AttendanceInputForm form) throws ParseException {

		// シフト情報を取得
		ShiftInfo shiftInfo = getShiftInfo(form.getShiftCd());

		form.setShiftInfo(shiftInfo);

		// 勤務時間取得
		getWHours(form);
		// 休暇時間数を算出する
		getKyukaHours(form);
		// 超勤時間帯の勤務時間数を算出する
		getChokin(form);
		// 深夜勤務時間数を算出する
		getMidnight(form);

	}

	/**
	 * 勤務時間数を取得する
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getWHours(AttendanceInputForm form) throws ParseException {

		// 勤務時間取得
		Double hours = 0.0;
		String shiftCode = form.getShiftCd();
		String wSTime = form.getWorkSHour().concat(form.getWorkSMinute());
		String wETime = form.getWorkEHour().concat(form.getWorkEMinute());

		// 勤務開始から勤務終了が24:00をまたいでいる場合
		if ("2400".compareTo(wSTime) > 0
				&& "2400".compareTo(wETime) < 0) {

			// 勤務開始時刻 ~ 2330 + 0000 ~ 勤務終了時刻
			hours = getHours(wSTime, "2330", shiftCode)
					+ getHours("0000", wETime, shiftCode);
		} else {

			// 勤務開始時刻 ~ 勤務終了時刻
			hours = getHours(wSTime, wETime,
					shiftCode);
		}
		// 勤務時間
		form.setWorkHours(hours);
	}

	/**
	 * 休暇時間を取得する
	 * 
	 * @param form
	 *            勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getKyukaHours(AttendanceInputForm form) throws ParseException {

		// 遅刻時間取得
		Double wVhikoku = 0.0;
		// 早退時間取得
		Double wSotai = 0.0;
		ShiftInfo shift = form.getShiftInfo();
		String wSTime = form.getWorkSHour().concat(form.getWorkSMinute());
		String wETime = form.getWorkEHour().concat(form.getWorkEMinute());

		// 遅刻の時間数をカウントする
		if (shift.getStartTime().compareTo(wSTime) < 0) {

			// 定時出勤時刻から勤務開始時刻が24:00をまたいでいる場合
			if ("2400".compareTo(shift.getStartTime()) > 0
					&& "2400".compareTo(wSTime) < 0) {

				// 定時出勤時刻 ~ 2330 + 0000 ~ 勤務開始時刻
				wVhikoku = getHours(shift.getStartTime(), "2330",
						form.getShiftCd())
						+ getHours("0000", wSTime,
								form.getShiftCd());
			} else {

				// 定時出勤時刻 ~ 勤務開始時刻
				wVhikoku = getHours(shift.getStartTime(), wSTime,
						form.getShiftCd());
			}
		}
		// 早退の時間数をカウントする
		if (shift.getEndTime().compareTo(wETime) > 0) {

			// 24:00をまたいでいる場合
			if ("2400".compareTo(wETime) > 0
					&& "2400".compareTo(shift.getEndTime()) < 0) {

				// TODO form.getStandEtimeStr() -> shiftT.getStandEtime()
				// 勤務終了時刻 ~ 2330 + 0000 ~ 定時退勤時刻
				wSotai = getHours(wETime, "2330",
						form.getShiftCd())
						+ getHours("0000", shift.getEndTime(),
								form.getShiftCd());
			} else {

				// 勤務終了時刻 ~ 定時退勤時刻
				wSotai = getHours(wETime, shift.getEndTime(),
						form.getShiftCd());
			}
		}

		// 休暇時間
		form.setKyukaHours(wVhikoku + wSotai);
	}

	/**
	 * 超勤時間帯の勤務時間を取得する
	 * 
	 * @param form 勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getChokin(AttendanceInputForm form) throws ParseException {

		// 超勤時間
		Double wChokinHours = 0.0;
		ShiftInfo shift = form.getShiftInfo();
		String wETime = form.getWorkEHour().concat(form.getWorkEMinute());
		
		//TODO form.getChokinSTimeStr() -> shiftT.getChokinSTime()
		// シフト超勤開始時刻から勤務終了が24:00をまたいでいる場合
		if ("2400".compareTo(shift.getChokinSTime()) > 0 && "2400".compareTo(wETime) < 0) {
			
			// シフト超勤開始時刻 ~ 2330 + 0000 ~ 勤務終了時刻
			wChokinHours = getHours(shift.getChokinSTime(), "2330", shift.getCode()) + getHours("0000", wETime, shift.getCode());
		} else {
			
			// シフト超勤開始時刻 ~ 勤務終了時刻
			wChokinHours = getHours(shift.getChokinSTime(), wETime, shift.getCode());
		}
		
		// 超勤時間は１時間以上の場合
		if(wChokinHours >= 1) {
			//TODO form.getChokinSTimeStr() -> shiftT.getChokinSTime()
			// 超勤開始時刻＝シフト超勤開始時刻
			form.setChoSTime(shift.getChokinSTime());
			// 超勤終了時刻＝勤務終了時刻
			form.setChoETime(wETime);
				
			// 休日か平日の判断
			if("休日".equals(form.getKinmuKun()) || "振替休日".equals(form.getKinmuKun())){
				
				form.setChoWeekday(0.0);
				form.setChoWeekday_nomal(0.0);
				// 超勤休日を設定する
				form.setChoHoliday(wChokinHours);
				
			} else {
				// 休暇欠勤区分
				if ("".equals(form.getKyukaKb())){
					
					// 超勤平日（割増）を設定する
					form.setChoWeekday(wChokinHours);
					
				} else {
					if ("半休(有給休暇)".equals(form.getKyukaKb())){
						
						if (form.getWorkHours() <= 7.5){
							form.setChoHoliday(0.0);
							form.setChoWeekday(0.0);
							// 勤務時間が7.5時間以下だった場合はすべて通常として扱う
							form.setChoWeekday_nomal(wChokinHours);
						} else {
		
							form.setChoWeekday(form.getWorkHours() - 7.5);
							form.setChoWeekday_nomal(wChokinHours - form.getChoWeekday());
						}
						
					} else if ("時間休(有給休暇)".equals(form.getKyukaKb())){
		
						if (form.getWorkHours() <= 7.5){
							
							// 勤務時間が7.5時間以下だった場合はすべて通常として扱う
							form.setChoWeekday_nomal(wChokinHours);
						} else {
		
							// 勤務時間が7.5時間以上だった場合は休暇時間分を通常として扱う
							form.setChoWeekday_nomal(form.getKyukaHours());
							// 超勤時間 - 通常として扱った時間を割増時間として扱う
							form.setChoWeekday(wChokinHours - form.getKyukaHours());
						}
					}
				}
			}
		} else {
			form.setChoSTime("");
			form.setChoETime("");
			form.setChoHoliday(0.0);
			form.setChoWeekday(0.0);
			form.setChoWeekday_nomal(0.0);
		}
	}

	/**
	 * 深夜勤務時間数を取得する
	 * 
	 * @param form 勤怠入力画面情報
	 * @throws ParseException 
	 */
	private void getMidnight(AttendanceInputForm form) throws ParseException {

		ShiftInfo shift = form.getShiftInfo();
		// 深夜勤務時間
		Double wMNHours = 0.0;
		// 深夜勤務時間0点以前
		Double wMNHoursBe = 0.0;
		// 深夜勤務時間0点以后
		Double wMNHoursAf = 0.0;
		String wSTime = form.getWorkSHour().concat(form.getWorkSMinute());
		String wETime = form.getWorkEHour().concat(form.getWorkEMinute());
		
		// 勤務開始から勤務終了が24:00をまたぐとき
		if ("2400".compareTo(wSTime) > 0 && "2400".compareTo(wETime) < 0) {
			// 深夜勤務時間0点以前
			if ("2200".compareTo(wSTime) >= 0){
				
				wMNHoursBe = getHours("2200", "2330", shift.getCode());
			} else {
				
				wMNHoursBe = getHours(wSTime, "2330", shift.getCode());
			}
			// 深夜勤務時間0点以后
			if ("0430".compareTo(wETime) >= 0) {
				
				wMNHoursAf = getHours("0000", wETime, shift.getCode());
			} else {
				
				wMNHoursAf = getHours("0000", "0430", shift.getCode());
			}
			// 深夜勤務時間
			wMNHours = wMNHoursBe + wMNHoursAf;
		} else {
			// 勤務開始時間と勤務終了時間が0点以前
			if ("2200".compareTo(wSTime) >= 0){
				
				if ("2330".compareTo(wETime) <= 0) {

					wMNHours = getHours("2200", "2330", shift.getCode());
				} else {
					
					wMNHours = getHours("2200", wETime, shift.getCode());
				}
				
			} else {

				if ("2330".compareTo(wETime) <= 0) {

					wMNHours = getHours(wSTime, "2330", shift.getCode());
				} else {
					
					wMNHours = getHours(wSTime, wETime, shift.getCode());
				}
			}
			
			// 勤務開始時間と勤務終了時間が0点以后
			if ("2400".compareTo(wSTime) <= 0) {
				
				if ("2830".compareTo(wETime) >= 0) {

					wMNHours = getHours(wSTime, "2830", shift.getCode());
				} else {
					
					wMNHours = getHours(wSTime, wETime, shift.getCode());
				}
			}
		}
		
		// 深夜を設定する
		if (wMNHours > 0) {
			form.setmNHours(wMNHours);
		} else {
			form.setmNHours(0.0);
		}
	
		
	}
	/**
	 * 時間を取得する
	 * 
	 * @param sTime
	 *            開始時間hhmm
	 * @param eTime
	 *            　終了時間hhmm
	 * @param shiftCode
	 *            　シフトコード
	 * @return 時間数
	 */
	private Double getHours(String sTime, String eTime, String shiftCode) {

		// TODO SQL文を利用しDBから取得する。
		// 仮
		// 終了時間hour
		Double eTimeH = 0.0;
		// 開始時間
		Double sTimeH = 0.0;
		// 時間数
		Double Hours = 0.0;

		// TODO シフト表シートを検索
		if (!eTime.isEmpty()) {
			eTimeH = Double.valueOf(eTime.substring(0, 2))
					+ Double.valueOf(eTime.substring(2, 4)) / 60;
		}
		if (!sTime.isEmpty()) {
			sTimeH = Double.valueOf(sTime.substring(0, 2))
					+ Double.valueOf(sTime.substring(2, 4)) / 60;
		}

		Hours = eTimeH - sTimeH;
		if ((Hours > 7.5
				&& "1300".compareTo(sTime) > 0)
				|| (Hours < 7.5 && "1200".compareTo(sTime) > 0 && "1300"
						.compareTo(eTime) < 0)) {
			Hours = Hours - 1;
		}

		if (Hours >= 8 || "2200".compareTo(eTime) < 0) {
			Hours = Hours - 0.5;
		}

		if ("1000".equals(sTime) || "1100".equals(sTime)
				|| "1300".equals(sTime)) {
			Hours = Hours - 0.5;
		}

		if (Hours >= 12) {
			Hours = Hours - 0.5;

		}
		// 時間差戻る
		return Hours;
	}

	private ShiftInfo getShiftInfo(String shiftCode) {

		// TODO 自動生成されたメソッド・スタブ Dao を必要
		ShiftInfo info = null;
		// シフトコードが存在しないの場合
		if (StringUtils.equals("0900", shiftCode)) {

			info = new ShiftInfo();

			// シフトコード
			info.setCode("0900");
			// 定時出勤時刻
			info.setStartTime("0900");
			// 午前終了時刻
			info.setAmETime("1200");
			// 午後開始時刻
			info.setFmSTime("1300");
			// 定時退勤時刻
			info.setEndTime("1730");
			// 超勤開始時刻
			info.setChokinSTime("1800");
		}
		
		if (StringUtils.equals("1000", shiftCode)) {

			info = new ShiftInfo();

			// シフトコード
			info.setCode("1000");
			// 定時出勤時刻
			info.setStartTime("1000");
			// 午前終了時刻
			info.setAmETime("1200");
			// 午後開始時刻
			info.setFmSTime("1300");
			// 定時退勤時刻
			info.setEndTime("1830");
			// 超勤開始時刻
			info.setChokinSTime("1900");
		}

		return info;

	}

}
