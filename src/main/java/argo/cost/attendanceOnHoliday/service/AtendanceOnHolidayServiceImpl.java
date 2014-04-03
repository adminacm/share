package argo.cost.attendanceOnHoliday.service;

import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import argo.cost.attendanceOnHoliday.dao.AtendanceOnHolidayDao;
import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.model.ListItemVO;
import argo.cost.common.utils.CostDateUtils;

@Service
public class AtendanceOnHolidayServiceImpl implements AtendanceOnHolidayService {

	@Autowired
	private AtendanceOnHolidayDao atendanceOnHolidayDao;

	// 勤務日区分のプルダウンリストを取得
	@Override
	public ArrayList<ListItemVO> getAtendanceDayKbnList() {
		// TODO Auto-generated method stub
		return atendanceOnHolidayDao.getAtendanceDayKbnList();
	}

	/**
	 * ユーザーがこの日休日勤務データ設定
	 * 
	 * @param form
	 *            休日勤務画面情報
	 * @param date
	 *            勤怠入力画面から渡した休日の日付
	 *            
	 * @throws ParseException 
	 * 
	 */
	@Override
	public void setAtendanceOnHolidayInfo(AtendanceOnHolidayForm form, String date) throws ParseException {
		
		// TODO Auto-generated method stub
		// 休日勤務データを取得する
		AtendanceOnHolidayForm entity = atendanceOnHolidayDao
				.atendanceOnHolidayDataGet(form.getUserId(), date);

		// 勤務日付
		String attDate = CostDateUtils.formatDate(date, CommonConstant.YYYYMMDD_KANJI);
		String weekday = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(date));
		form.setStrAtendanceDate(attDate.concat("(").concat(weekday).concat(")"));
		// 当然日付のデータが存在する場合
		if (entity != null) {

			// 休日勤務画面情報を更新する
			// 勤務区分
			form.setSelectedAtendanceDayKbn(entity.getSelectedAtendanceDayKbn());
			// 勤務区分リスト
			form.setAtendanceDayKbnList(getAtendanceDayKbnList());
			// 勤務開始時間
			form.setStrAtendanceTimeStat(entity.getStrAtendanceTimeStat());
			// 勤務終了時間
			form.setStrAtendanceTimeEnd(entity.getStrAtendanceTimeEnd());
			// 振替日
			form.setStrHurikaeDate(entity.getStrHurikaeDate());
			// プロジェクト名
			form.setSelectedProjCd(entity.getSelectedProjCd());
			// 業務内容
			form.setStrCommont(entity.getStrCommont());

		}
	}

	// 休日勤務入力画面の保存処理
	public String saveAtendanceOnHoliday(
			AtendanceOnHolidayForm atendanceOnHoliday, String UserID) {

		// TODO DBに保存する
		// em.persist(atendanceOnHoliday);
		return atendanceOnHolidayDao.saveAtendanceOnHoliday(atendanceOnHoliday,
				UserID);
	}

	@Transactional
	public String deleteAtendanceOnHoliday(String strAtendanceDate,
			String userID) {

		// TODO DBで削除する
		// AtendanceOnHoliday atendanceOnHoliday =
		// em.find(AtendanceOnHoliday.class, strAtendanceDate);
		AtendanceOnHolidayForm atendanceOnHoliday = new AtendanceOnHolidayForm();

		if (null != atendanceOnHoliday) {
			// em.remove(atendanceOnHoliday);
			// return
			// atendanceOnHolidayDao.deleteAtendanceOnHoliday(strAtendanceDate,userID);
			return "1";
		} else {
			return "0";
		}

	}

}
