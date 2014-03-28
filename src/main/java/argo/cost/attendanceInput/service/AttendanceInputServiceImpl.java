package argo.cost.attendanceInput.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceInput.dao.AttendanceInputDao;
import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.model.ListItem;
import argo.cost.common.utils.CostDateUtils;

@Service
public class AttendanceInputServiceImpl implements AttendanceInputService {
	
	/** 定数 */
	// 昨日
	private final String LAST = "last";
	
	// 明日
	private final String NEXT = "next";
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

}
