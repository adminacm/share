package argo.cost.attendanceInput.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProjectVO;
import argo.cost.attendanceInput.model.HolidayRecord;

/**
 * 勤怠入力DAOImpl
 * 勤怠入力に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Repository
public class AttendanceInputDaoImpl implements AttendanceInputDao {

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
		HolidayRecord record = new HolidayRecord();
		
		record.setDate("20140329");
		record.setUserId("user01");
		record.setLimitDate("20140630");
		record.setExchangeDay("20140331");
		record.setTransferAppDay("");
		record.setPayOutYM("");
		record.setProcessKbn(0);
		record.setProcessDate("20140501");
		record.setProjectCode("01");
		record.setProjectName("原価管理");
		record.setWorkNaiyo("定期メンテナンス作業");
		
		if (StringUtils.equals("20140329", yyyymmdd)) {
			return record;
		}
		
		return null;
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
	public List<AttendanceProjectVO> getProjectList(String userId, String yyyymmdd)
			throws ParseException {
		// TODO 自動生成されたメソッド・スタブ　Daoを利用する予定
		List<AttendanceProjectVO> result = new ArrayList<AttendanceProjectVO>();

		AttendanceProjectVO pro = null;
		for (int i = 0; i < 1; i++) {
			pro = new AttendanceProjectVO();
			pro.setHours(3.5);
			pro.setProjectId("01");
			pro.setWorkId("01");
			result.add(pro);
		}

		return result;
	}
	
	/**
	 * 就業データを取得
	 * 
	 * @param form 
	 * 				画面情報
	 * 
	 * @return 更新結果　０：更新失敗　１：更新成功
	 */
	@Override
	public Integer updateAttdendanceInfo(AttendanceInputForm form) {
		// TODO 自動生成されたメソッド・スタブ

		// 更新成功の場合
		if ("user01".equals(form.getUserId())) {
			return 1;
		}
		return 0;
	}
}
