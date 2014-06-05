package argo.cost.attendanceOnHolidayRecord.service;

import java.text.ParseException;
import java.util.List;

import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 休日出勤管理サービスのインターフェース
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface AttendanceOnHolidayRecordService {

	/**
	 * 氏名プルダウンリスト取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @return 氏名プルダウンリスト
	 */
	List<ListItemVO> getUserNameList(String userId);

	/**
	 * 休日出勤管理情報をセット
	 * 
	 * @param form
	 *            休日出勤管理画面情報
	 */
	void setAttendanceOnHolidayRecordInfo(AttendanceOnHolidayRecordForm form) throws ParseException;
}
