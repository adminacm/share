package argo.cost.attendanceOnHolidayRecordDetail.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;
import argo.cost.common.dao.ComDao;

@Repository
public class AttendanceOnHolidayRecordDetailDaoImpl implements AttendanceOnHolidayRecordDetailDao {

	/**
	 * 共通DAO
	 */
	@Autowired
	private ComDao comDao;
	
	/**
	 * 休日出勤管理詳細画面情報取得
	 * 
	 * @param userId
	 * 	                           ユーザＩＤ
	 * @param date
	 *            日付
	 * @param workKbn
	 *            日付
	 * @return
	 *        休日出勤管理詳細画面情報
	 */
	@Override
	public AttendanceOnHolidayRecordDetailForm getAttendanceOnHolidayRecordDetail(
			String userId, String date, String workKbn) {

		//TODO DBから、休日出勤管理詳細画面の情報を設定
		AttendanceOnHolidayRecordDetailForm detailForm = new AttendanceOnHolidayRecordDetailForm();
		// 休日振替勤務の場合
		if ("2014/5/5".equals(date)) {
			// 日付
			detailForm.setDate(date);
			// 勤務区分名
			detailForm.setWorkKbnName(comDao.findWorkKbnName(workKbn));
			// 勤務開始時間
			detailForm.setWorkStartTime("9:00");
			// 勤務終了時間
			detailForm.setWorkEndTime("19:00");
			// 振替日
			detailForm.setExchangeDate("2014/5/9");
			// プロジェクト名
			detailForm.setProjectName("経費管理システム運用");
			// 業務内容
			detailForm.setWorkDetail("トラブル対応");
			// 代休期限
			detailForm.setTurnedHolidayEndDate("");
			// 代休日
			detailForm.setTurnedHolidayDate("");
			// 超勤振替申請日
			detailForm.setOverWorkTurnedReqDate("");
			
		} else if ("2014/4/13".equals(date)) {
			// 休日勤務の場合 代休取得済みのときは代休日が表示されて「超勤に振替える」ボタンは非表示
			// 日付
			detailForm.setDate(date);
			// 勤務区分名
			detailForm.setWorkKbnName(comDao.findWorkKbnName(workKbn));
			// 勤務開始時間
			detailForm.setWorkStartTime("9:00");
			// 勤務終了時間
			detailForm.setWorkEndTime("19:00");
			// 代休期限
			detailForm.setTurnedHolidayEndDate("2014/6/30");
			// 代休日
			detailForm.setTurnedHolidayDate("2014/4/18");
			// プロジェクト名
			detailForm.setProjectName("経費管理システム運用");
			// 業務内容
			detailForm.setWorkDetail("トラブル対応");
			// 超勤振替申請日
			detailForm.setOverWorkTurnedReqDate("");
			// 振替日
			detailForm.setExchangeDate("");
			
		} else if ("2014/5/10".equals(date)) {
			// 休日勤務の場合 超勤振替申請済みの場合は超勤振替申請日が表示され「超勤に振替える」ボタンは非表示
			// 日付
			detailForm.setDate(date);
			// 勤務区分名
			detailForm.setWorkKbnName(comDao.findWorkKbnName(workKbn));
			// 勤務開始時間
			detailForm.setWorkStartTime("9:00");
			// 勤務終了時間
			detailForm.setWorkEndTime("19:00");
			// 代休期限
			detailForm.setTurnedHolidayEndDate("2014/7/31");
			// 超勤振替申請日
			detailForm.setOverWorkTurnedReqDate("2014/7/15");
			// プロジェクト名
			detailForm.setProjectName("経費管理システム運用");
			// 業務内容
			detailForm.setWorkDetail("トラブル対応");
			// 振替日
			detailForm.setExchangeDate("");
			// 代休日
			detailForm.setTurnedHolidayDate("");
			
		} else if ("2014/5/18".equals(date)) {
			// 休日勤務の場合 代休未取得なら、代休期限までは「超勤に振替える」ボタンが表示される
			// 日付
			detailForm.setDate(date);
			// 勤務区分名
			detailForm.setWorkKbnName(comDao.findWorkKbnName(workKbn));
			// 勤務開始時間
			detailForm.setWorkStartTime("9:00");
			// 勤務終了時間
			detailForm.setWorkEndTime("19:00");
			// 代休期限
			detailForm.setTurnedHolidayEndDate("2014/7/31");
			// プロジェクト名
			detailForm.setProjectName("経費管理システム運用");
			// 業務内容
			detailForm.setWorkDetail("トラブル対応");
			// 振替日
			detailForm.setExchangeDate("");
			// 代休日
			detailForm.setTurnedHolidayDate("");
			// 超勤振替申請日
			detailForm.setOverWorkTurnedReqDate("");
			
		}
		return detailForm;
	}

	/**
	 * 超勤振替申請を提出
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 * 
	 */
	@Override
	public Integer updateAttendanceOnHolidayRecordDetail(AttendanceOnHolidayRecordDetailForm form) {
		
		// TODO 休日出勤管理詳細の超勤振替申請日がシステム日付になる
		return 1;
		
	}
	

}
