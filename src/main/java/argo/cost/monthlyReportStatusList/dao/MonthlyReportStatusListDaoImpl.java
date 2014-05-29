package argo.cost.monthlyReportStatusList.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;
import argo.cost.monthlyReportStatusList.model.PayMagistrateCsvInfo;

/**
 * <p>
 * 月報状況一覧のアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class MonthlyReportStatusListDaoImpl implements MonthlyReportStatusListDao {

	/**
	 * 月報状況一覧データを取得
	 * 
	 * @param monthlyReportStatusListForm
	 *                                   月報状況一覧画面情報
	 * @return 月報状況一覧データ
	 */
	@Override
	public List<MonthlyReportStatusListVo> getMonthlyReportStatusList(MonthlyReportStatusListForm monthlyReportStatusListForm) {

		// TODO:仮の値を与える
		List<MonthlyReportStatusListVo> resultList = new ArrayList<MonthlyReportStatusListVo>();
		
//		ApprovalListEntity itemInfo = new ApprovalListEntity();
//		itemInfo.setApplyNo("user01120140500");
//		itemInfo.setAffiliation("ＢＳ２");
//		itemInfo.setId("aaa");
//		itemInfo.setName("あｘｘｘｘｘ");
//		itemInfo.setApplyKbn("1");
//		itemInfo.setApplyDetail("2014年5月分");
//		itemInfo.setStatus("01");
//		resultList.add(itemInfo);
//		
//		itemInfo = new ApprovalListEntity();
//		itemInfo.setApplyNo("user02120140500");
//		itemInfo.setAffiliation("ＢＳ２");
//		itemInfo.setId("uuu");
//		itemInfo.setName("うｘｘｘｘｘ");
//		itemInfo.setApplyKbn("1");
//		itemInfo.setApplyDetail("2014年5月分");
//		itemInfo.setStatus("01");
//		resultList.add(itemInfo);
//		
//		itemInfo = new ApprovalListEntity();
//		itemInfo.setApplyNo("user03220140500");
//		itemInfo.setAffiliation("ＢＳ２");
//		itemInfo.setId("iii");
//		itemInfo.setName("えｘｘｘｘｘ");
//		itemInfo.setApplyKbn("2");
//		itemInfo.setApplyDetail("休日勤務日：2014/5/5");
//		itemInfo.setStatus("06");
//		resultList.add(itemInfo);
//		
//		itemInfo = new ApprovalListEntity();
//		itemInfo.setApplyNo("user04220140500");
//		itemInfo.setAffiliation("ＢＳ２");
//		itemInfo.setId("uuu");
//		itemInfo.setName("うｘｘｘｘｘ");
//		itemInfo.setApplyKbn("2");
//		itemInfo.setApplyDetail("休日勤務日：2014/5/5");
//		itemInfo.setStatus("06");
//		resultList.add(itemInfo);
//		
		return resultList;
	}

	/**
	 * 所属データ取得
	 * 
	 * @return
	 *        所属データ
	 */
	@Override
	public List<ListItemVO> getAffiliationList() {
		
		//TODO ドロップダウンリスト仮の値を作成
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = new ListItemVO();
		item.setValue("00");
		item.setName("");
		resultList.add(item);
		
		item = new ListItemVO();
		item.setValue("01");
		item.setName("ＢＳ１");
		resultList.add(item);
		
		item = new ListItemVO();
		item.setValue("02");
		item.setName("ＢＳ２");
		resultList.add(item);
		
		return resultList;
	}

	/**
	 * 給与奉行向けCSVファイル情報を取得
	 * 
	 * @param monthlyReportStatusListForm 
	 *                                   画面情報
	 * @return 給与奉行向けCSVファイル情報
	 */
	@Override
	public List<PayMagistrateCsvInfo> getPayMagistrateCsvList(MonthlyReportStatusListForm monthlyReportStatusListForm) {
		
		// TODO 月報状況一覧画面の検索条件「年月」、「所属」、「状況」によって、ＤＢから、給与奉行向データを取得する。
		List<PayMagistrateCsvInfo> csvDataList = new ArrayList<PayMagistrateCsvInfo>();
		PayMagistrateCsvInfo csvInfo  = new PayMagistrateCsvInfo();
		// 社員番号
		csvInfo.setEmployeeNo("001");
		// 超過勤務時間数（平日_割増）
		csvInfo.setOverWeekdayHours("30.0");
		// 超過勤務時間数（休日）
		csvInfo.setOverHolidayHours("12.5");
		// 超過勤務時間数（深夜）
		csvInfo.setOverNightHours("2.5");
		// 超過勤務時間数（休日出勤振替分）
		csvInfo.setOverHolidayChangeWorkHours("8.0");
		// 欠勤時間数
		csvInfo.setAbsenceHours("2.0");
		// 超過勤務時間数（平日_通常）
		csvInfo.setOverWeekdayNomalHours("2.0");
		csvDataList.add(csvInfo);
		
		csvInfo  = new PayMagistrateCsvInfo();
		// 社員番号
		csvInfo.setEmployeeNo("002");
		// 超過勤務時間数（平日_割増）
		csvInfo.setOverWeekdayHours("40.5");
		// 超過勤務時間数（休日）
		csvInfo.setOverHolidayHours("10.0");
		// 超過勤務時間数（深夜）
		csvInfo.setOverNightHours("6.5");
		// 超過勤務時間数（休日出勤振替分）
		csvInfo.setOverHolidayChangeWorkHours("3.0");
		// 欠勤時間数
		csvInfo.setAbsenceHours("5.5");
		// 超過勤務時間数（平日_通常）
		csvInfo.setOverWeekdayNomalHours("5.5");
		csvDataList.add(csvInfo);
		
		csvInfo  = new PayMagistrateCsvInfo();
		// 社員番号
		csvInfo.setEmployeeNo("003");
		// 超過勤務時間数（平日_割増）
		csvInfo.setOverWeekdayHours("10.5");
		// 超過勤務時間数（休日）
		csvInfo.setOverHolidayHours("20.0");
		// 超過勤務時間数（深夜）
		csvInfo.setOverNightHours("1.5");
		// 超過勤務時間数（休日出勤振替分）
		csvInfo.setOverHolidayChangeWorkHours("1.5");
		// 欠勤時間数
		csvInfo.setAbsenceHours("0.5");
		// 超過勤務時間数（平日_通常）
		csvInfo.setOverWeekdayNomalHours("0.5");
		csvDataList.add(csvInfo);
		return csvDataList;
	}
}
