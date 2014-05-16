package argo.cost.attendanceOnHoliday.dao;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.model.ListItemVO;

/**
 * 休日勤務入力画面DAOの実現
 *
 * @author COST argo Corporation.
 */
@Repository
public class AtendanceOnHolidayDaoImpl implements AtendanceOnHolidayDao {

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
		
		// TODO ユーザーIDと当前の日付によって、DBから休日勤務情報取得、専用のEntityクラスが必要です
		AtendanceOnHolidayForm atendanceOnHoliday = null;
		
		// 仮：データが存在する場合
		if (StringUtils.equals("20140405", currentDate)) {
			
			atendanceOnHoliday = new AtendanceOnHolidayForm();
			
			// 勤務日付
			atendanceOnHoliday.setStrAtendanceDate(currentDate);
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
	 * @return 勤務日区分リスト
	 */
	@Override
	public ArrayList<ListItemVO> getAtendanceDayKbnList() {

		ArrayList<ListItemVO> atendanceDayKbnList = new ArrayList<ListItemVO>();

		// TODO データベースから勤務日区分リストを検索
		ListItemVO item = new ListItemVO();

		item.setValue("02");
		item.setName("休日");
		atendanceDayKbnList.add(item);
		
		item = new ListItemVO();
		item.setValue("03");
		item.setName("休日振替勤務");
		atendanceDayKbnList.add(item);

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
	public String deleteAtendanceOnHoliday(String strAtendanceDate,
			String userID) {
		// TODO 当前の日付のデータを削除する
		if (userID.equals("U0001")) {
			System.out.println("勤務情報をDBから削除成功しました");
			return "1";
		} else {
			
			System.out.println("勤務情報をDBから失敗");
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
	 * @return　userId
	 *            勤務情報データを削除結果フラグ
	 */
	@Override
	public String saveAtendanceOnHoliday(AtendanceOnHolidayForm atendanceOnHoliday,
			String UserID) {
		// TODO 入力データをDBに保存する
		if (null != atendanceOnHoliday && "U0001".equals(UserID)) {
			return "1";
		} else {
			return "0";
		}

	}

}
