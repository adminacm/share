package argo.cost.attendanceOnHolidayRecordDetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecordDetail.dao.AttendanceOnHolidayRecordDetailDao;
import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;

/**
 * <p>
 * 休日出勤管理詳細画面サービス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Service
public class AttendanceOnHolidayRecordDetailServiceImpl implements AttendanceOnHolidayRecordDetailService {
	
	@Autowired
	private AttendanceOnHolidayRecordDetailDao attendanceOnHolidayRecordDetailDao;

	/**
	 * 休日出勤管理詳細画面情報を取得
	 * 
	 * @param userId
	 * 	                           ユーザＩＤ
	 * @param date
	 *            日付
	 * @param workKbn
	 *               勤務区分
	 * @return 休日出勤管理詳細画面情報
	 */
	@Override
	public AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(String userId, String date, String workKbn) {

		// 休日出勤管理詳細画面
		AttendanceOnHolidayRecordDetailForm attendanceOnHolidayRecordDetailForm = attendanceOnHolidayRecordDetailDao.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		
		return attendanceOnHolidayRecordDetailForm;
	}

	/**
	 * 超勤振替申請を提出
	 * 
	 * @param attendanceOnHolidayRecordDetailForm
	 *                                           休日出勤管理詳細画面情報
	 * @return 更新結果フラグ
	 */
	@Override
	public Integer overWorkPayRequest(AttendanceOnHolidayRecordDetailForm attendanceOnHolidayRecordDetailForm) {

		// 休日出勤管理詳細の超勤振替申請日がシステム日付になる
		Integer resultFlg = attendanceOnHolidayRecordDetailDao.updateAttendanceOnHolidayRecordDetail(attendanceOnHolidayRecordDetailForm);
		
		return resultFlg;
	}
}
