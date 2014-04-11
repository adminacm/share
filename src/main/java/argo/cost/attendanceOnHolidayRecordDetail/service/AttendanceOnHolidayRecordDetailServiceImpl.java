package argo.cost.attendanceOnHolidayRecordDetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecordDetail.dao.AttendanceOnHolidayRecordDetailDao;
import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;

@Service
public class AttendanceOnHolidayRecordDetailServiceImpl implements AttendanceOnHolidayRecordDetailService {
	
	@Autowired
	private AttendanceOnHolidayRecordDetailDao recordDao;

	/**
	 * 休日出勤管理詳細画面情報取得
	 * 
	 * @param userId
	 * 	                           ユーザＩＤ
	 * @param date
	 *            日付
	 * @param workKbn
	 *               勤務区分
	 */
	@Override
	public AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(String userId, String date, String workKbn) {

		// 休日出勤管理詳細画面
		AttendanceOnHolidayRecordDetailForm detailForm = recordDao.getAttendanceOnHolidayRecordDetail(userId, date, workKbn);
		
		return detailForm;
	}

	/**
	 * 休日出勤管理詳細の超勤振替申請日がシステム日付になる
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 * 
	 */
	@Override
	public Integer overWorkPayRequest(AttendanceOnHolidayRecordDetailForm form) {

		// 休日出勤管理詳細の超勤振替申請日がシステム日付になる
		Integer resultFlg = recordDao.updateAttendanceOnHolidayRecordDetail(form);
		
		return resultFlg;
	}
}
