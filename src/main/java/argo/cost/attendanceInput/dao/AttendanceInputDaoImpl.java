package argo.cost.attendanceInput.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import argo.cost.attendanceInput.model.AttendanceProject;
import argo.cost.attendanceInput.model.HolidayRecord;
import argo.cost.attendanceInput.model.WorkTimeDetail;
import argo.cost.common.model.ListItemVO;

@Repository
public class AttendanceInputDaoImpl implements AttendanceInputDao {

	/**
	 * 休暇欠勤区分プルダウンリスト取得
	 * 
	 * @return 休暇欠勤区分プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getHolidayLackingItem() {
		// TODO 自動生成されたメソッド・スタブ
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		
		ListItemVO item1 = new ListItemVO();
		item1.setValue("01");
		item1.setName("全休(有給休暇)");
		resultList.add(item1);

		item1 = new ListItemVO();
		item1.setValue("02");
		item1.setName("半休(有給休暇)");
		resultList.add(item1);

		item1 = new ListItemVO();
		item1.setValue("03");
		item1.setName("時間休(有給休暇)");
		resultList.add(item1);
		
		return resultList;
	}

	/**
	 * 個人勤怠プロジェクト情報取得
	 * 
	 * 
	 * @return 個人勤怠プロジェクト情報
	 */
	@Override
	public List<ListItemVO> getWorkItemList() {
		
		// TODO 自動生成されたメソッド・スタブ
		List<ListItemVO> list = new ArrayList<ListItemVO>();
		
		ListItemVO itm = new ListItemVO();
		itm.setName("MUT");
		itm.setValue("01");
		list.add(itm);
		
		itm = new ListItemVO();
		itm.setName("SI");
		itm.setValue("02");
		list.add(itm);
		
		itm = new ListItemVO();
		itm.setName("BD");
		itm.setValue("03");
		list.add(itm);
		
		return list;
	}

	/**
	 * ロケーション情報取得
	 * 
	 * 
	 * @return ロケーション情報
	 */
	@Override
	public List<ListItemVO> getLocationItemList() {
		// TODO 自動生成されたメソッド・スタブ
		List<ListItemVO> list = new ArrayList<ListItemVO>();
		
		ListItemVO itm = new ListItemVO();
		itm.setName("中国");
		itm.setValue("01");
		list.add(itm);
		
		itm = new ListItemVO();
		itm.setName("日本");
		itm.setValue("02");
		list.add(itm);
		
		itm = new ListItemVO();
		itm.setName("米国");
		itm.setValue("03");
		list.add(itm);
		
		return list;
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
	 * 就業データを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param yyyymmdd
	 *            日付
	 * @return 就業データ
	 */
	@Override
	public WorkTimeDetail getWorkTimeDetail(String userId, String yyyymmdd) {
		// TODO 自動生成されたメソッド・スタブ
		WorkTimeDetail info = new WorkTimeDetail();
		info.setUserId(userId);
		info.setWorkDate(yyyymmdd);
		info.setKinmuKbn("01");
		info.setShiftCode("0900");
		info.setKinmuSTime("09:00");
		info.setKinmuEtime("23:30");
		info.setSykaKetukinKbn("");
		info.setBikou("桜美林大学留学生管理システム保守：お客様問合せの対応");
		info.setFurikaeDate("");
		info.setSykaKetukinhours(0.0);
		info.setKinmuHours(0.0);
		info.setTyokinStime("18:00");
		info.setTyokinEtime("23:00");
		info.setTyokinHeijiHours(4.0);
		info.setTyokinHeijiTujyoHours(0.0);
		info.setTyokinKyujiHours(0.0);
		info.setSynyaKinmuHours(1.5);
		info.setStatus("01");
		return info;
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
	public List<AttendanceProject> getProjectList(String userId, String yyyymmdd)
			throws ParseException {
		// TODO 自動生成されたメソッド・スタブ　Daoを利用する予定
		List<AttendanceProject> result = new ArrayList<AttendanceProject>();

		AttendanceProject pro = null;
		for (int i = 0; i < 1; i++) {
			pro = new AttendanceProject();
			pro.setHours(3.5);
			pro.setProjectId("01");
			pro.setWorkId("01");
			result.add(pro);
		}

		return result;
	}
}
