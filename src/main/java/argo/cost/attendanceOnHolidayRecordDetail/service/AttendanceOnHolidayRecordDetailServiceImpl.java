package argo.cost.attendanceOnHolidayRecordDetail.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.attendanceOnHolidayRecordDetail.dao.AttendanceOnHolidayRecordDetailDao;
import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;
import argo.cost.common.constant.CommonConstant;

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
	private AttendanceOnHolidayRecordDetailDao detailDao;

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
	 * @throws ParseException 
	 */
	@Override
	public AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(String userId, String date, String workKbn) throws ParseException {

		// 休日出勤管理詳細情報
		AttendanceOnHolidayRecordDetailForm detailInfo = new AttendanceOnHolidayRecordDetailForm();
		
		// 休日出勤管理詳細情報を取得
		if (CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE.equals(workKbn)) {
			
			// 勤務区分が「休日振替勤務」の詳細情報を取得
			detailInfo = detailDao.getFurikaeDetail(userId, date);
		} else {
			
			// 勤務区分が「休日勤務」の詳細情報を取得
			detailInfo = detailDao.getWorkDetail(userId, date);
		}
		
		return detailInfo;
	}

	/**
	 * 超勤振替申請を提出
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 *            
	 */
	@Override
	public void overWorkPayRequest(AttendanceOnHolidayRecordDetailForm form) {

		// 休日出勤管理詳細の超勤振替申請日がシステム日付になる
		detailDao.updateAttendanceOnHolidayRecordDetail(form);
		
	}
}
