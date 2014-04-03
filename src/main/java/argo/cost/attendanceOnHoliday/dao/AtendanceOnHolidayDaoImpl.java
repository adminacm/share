package argo.cost.attendanceOnHoliday.dao;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.model.ListItemVO;

@Repository
public class AtendanceOnHolidayDaoImpl implements AtendanceOnHolidayDao {

	// protected EntityManager em;

	/**
	 * 当日休日勤務情報有無チャック
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            当前の日付
	 * @return ユーザ情報
	 */
	@Override
	public boolean atendanceOnHolidayDataChk(String userId, String date) {
		// TODO DB取得
		return false;
	}

	/**
	 * 当日休日勤務情報取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            当前の日付
	 * @return 当日休日勤務情報
	 */
	@Override
	public AtendanceOnHolidayForm atendanceOnHolidayDataGet(String userId, String date) {
		
		// TODO ユーザーIDと当前の日付によって、DBから休日勤務情報取得、専用のEntityクラスが必要です。
		AtendanceOnHolidayForm atendanceOnHoliday = null;
		
		// 仮：データが存在する場合
		if (StringUtils.equals("20140404", date)) {
			
			atendanceOnHoliday = new AtendanceOnHolidayForm();
			
			// 勤務日付
			atendanceOnHoliday.setStrAtendanceDate(date);
			// 勤務区分
			atendanceOnHoliday.setSelectedAtendanceDayKbn("03");
			// 勤務開始時間
			atendanceOnHoliday.setStrAtendanceTimeStat("09:00");
			// 勤務終了時間
			atendanceOnHoliday.setStrAtendanceTimeEnd("19:00");
			// 振替日
			atendanceOnHoliday.setStrHurikaeDate("");
			// プロジェクト名
			atendanceOnHoliday.setSelectedProjCd("01");
			// 業務内容
			atendanceOnHoliday.setStrCommont("定期メンテナンス作業");
		}

		return atendanceOnHoliday;
	}

	/**
	 * 勤務日区分リストを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @return ユーザ情報
	 */
	@Override
	public ArrayList<ListItemVO> getAtendanceDayKbnList() {

		ArrayList<ListItemVO> atendanceDayKbnList = new ArrayList<ListItemVO>();

		// TODO データベースから取得
		ListItemVO item = new ListItemVO();
//		item.setValue("01");
//		item.setName("出勤");
//		atendanceDayKbnList.add(item);

		item = new ListItemVO();
		item.setValue("02");
		item.setName("休日");
		atendanceDayKbnList.add(item);
		
		item = new ListItemVO();
		item.setValue("03");
		item.setName("休日振替勤務");
		atendanceDayKbnList.add(item);
		
//		item = new ListItemVO();
//		item.setValue("04");
//		item.setName("振替休日");
//		atendanceDayKbnList.add(item);

		return atendanceDayKbnList;
	}

	/**
	 * 休日勤務データの削除
	 * 
	 * @param strAtendanceDate
	 *            削除した勤務データの日付
	 * @param userId
	 *            ユーザID
	 */
	@Override
	public String deleteAtendanceOnHoliday(String strAtendanceDate,
			String UserID) {
		// TODO Auto-generated method stub

		if ("20140505".equals(strAtendanceDate) && UserID.equals("xiongyl")) {
			return "1";
		} else {
			return "0";
		}

	}
	
	/**
	 * 休日勤務データの保存
	 * 
	 * @param atendanceOnHoliday
	 *            入力した勤務情報
	 * @param userId
	 *            ユーザID
	 */
	@Override
	public String saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHoliday,
			String UserID) {
		// TODO Auto-generated method stub
		if (null != atendanceOnHoliday && "xiongyl".equals(UserID)) {
			return "1";
		} else {
			return "0";
		}

	}

}
