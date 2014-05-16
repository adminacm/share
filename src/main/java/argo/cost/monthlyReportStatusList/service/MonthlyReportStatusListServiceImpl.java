package argo.cost.monthlyReportStatusList.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.ComDao;
import argo.cost.common.model.ListItemVO;
import argo.cost.common.model.entity.ApprovalListEntity;
import argo.cost.monthlyReportStatusList.dao.MonthlyReportStatusListDao;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListInfo;
import argo.cost.monthlyReportStatusList.model.PayMagistrateCsvInfo;

/**
 * {@inheritDoc}
 */
@Service
public class MonthlyReportStatusListServiceImpl implements MonthlyReportStatusListService {

	/**
	 * 月報状況一覧DAO
	 */
	@Autowired
	MonthlyReportStatusListDao monthlyReportStatusListDao;
	
	/**
	 * 共通DAO
	 */
	@Autowired
	ComDao comDao;

	/**
	 * 月報状況一覧リストを取得
	 * 
	 * @param form
	 *           月報状況一覧情報
	 * @return 月報状況一覧リスト
	 */
	@Override
	public List<MonthlyReportStatusListInfo> getMonthlyReportStatusList(MonthlyReportStatusListForm form) {
		
		// 月報状況一覧リスト
		List<MonthlyReportStatusListInfo> mRSList = new ArrayList<MonthlyReportStatusListInfo>();
		
		// ＤＢから、月報状況一覧リストを取得
		List<ApprovalListEntity> mRSEList = monthlyReportStatusListDao.getMonthlyReportStatusList(form);
		
		if (mRSEList != null && mRSEList.size() > 0) {
			for (int i = 0; i < mRSEList.size(); i++) {
				ApprovalListEntity mRSInfo = mRSEList.get(i);
				MonthlyReportStatusListInfo appInfo = new MonthlyReportStatusListInfo();
				// ID
				appInfo.setId(mRSInfo.getId());
				// 申請区分
				appInfo.setApplyKbn(mRSInfo.getApplyKbn());
				// 申請内容
				appInfo.setApplyDetail(mRSInfo.getApplyDetail());
				// 状況
				String statusName = comDao.findStatusName(mRSInfo.getStatus());
				appInfo.setStatus(statusName);
				// 所属
				appInfo.setAffiliation(mRSInfo.getAffiliation());
				// 氏名
				appInfo.setName(mRSInfo.getName());
				
				mRSList.add(appInfo);
			}
		}
		
		// 月報状況一覧リスト
		return mRSList;
	}

	/**
	 * 年プルダウンリスト取得
	 * 
	 * @param date
	 * 	      　　　　　　日付
	 * @return
	 * 	             年プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getYearList(Date date) {

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// ドロップダウンリスト設定
		for (int i = 0; i < 3; i++) {
			
			item = new ListItemVO();

			// 年を取得
			if (i != 0){
				
				cal.add(Calendar.YEAR, -1); 
			}
			int year = cal.get(Calendar.YEAR);

			// データを設定する
			// 区分値 
			item.setValue(String.valueOf(year));
			// 区分名称
			item.setName(year + "年");

			// リストに追加
			resultList.add(item);
		}
		
		// 年ドロップダウンリストを返却する。
		return resultList;
	
	}

	/**
	 * 月プルダウンリスト取得
	 * 
	 * @return
	 * 	             月プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getMonthList() {

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = null;

		// ドロップダウンリスト設定
		for (int i = 1; i <= 12; i++) {
			
			item = new ListItemVO();

			// データを設定する
			// 区分値 
			item.setValue(String.valueOf(i));
			// 区分名称
			item.setName(String.valueOf(i) + "月");

			// リストに追加
			resultList.add(item);
		}
		
		// 月ドロップダウンリストを返却する。
		return resultList;
	
	}

	/**
	 * 所属プルダウンリスト取得
	 * 
	 * @return 
	 *        所属プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getAffiliationList() {
		
		// ＤＢから、所属プルダウンリスト取得
		 List<ListItemVO> affiliationList = monthlyReportStatusListDao.getAffiliationList();
		
		return affiliationList;
	}

	/**
	 * CSVファイルを作成
	 * 
	 * @param form
	 *           月報状況一覧情報
     * @param response
     *         レスポンス
	 * @return
	 *        CSVファイル情報
	 * @throws Exception 
	 */
	@Override
	public void createCSVFile(MonthlyReportStatusListForm form, HttpServletResponse response) throws Exception {
		
		// 給与奉行向けCSVファイル情報を取得
		List<PayMagistrateCsvInfo> csvDetailList = monthlyReportStatusListDao.getPayMagistrateCsvList(form);
		try {
			String path = "D:\\";
			
			SimpleDateFormat sdfYearM = new SimpleDateFormat("yyyyMMddHHmmss");
			// 日付設定
			String filaName = sdfYearM.format(new Date());
   		 	// CSV ダウンロード
        	exportCsvfiles(path, filaName, getTitleList(), csvDetailList, response);
       } catch (Exception e) {
            e.printStackTrace();
       }
	}

	/**
	 * ヘッダ部データ設定
	 */
	private List<String> getTitleList(){
        List<String> list = new ArrayList<String>();
        list.add("MK01");
        list.add("KN09");
        list.add("KN10");
        list.add("KN11");
        list.add("KN12");
        list.add("KN08");
        list.add("KN13");
        return list;
    }

	/**
	 *  ダウンロード実行
	 *  
	 * @param path
	 *            ＣＳＶファイルが保存されたパス
	 * @param fileName
	 *            ＣＳＶファイルの名前    
	 * @param titleList
	 * 			    ヘッダ部表示するタイトルリスト
	 * @param csvDetailList
	 * 	     	   ＣＳＶファイル詳細データリスト
	 * @param response
	 *           レスポンス
	 * @throws Exception
	 */
	 private void exportCsvfiles(String path, String fileName, List<String> titleList, List<PayMagistrateCsvInfo> csvDetailList, HttpServletResponse response) throws Exception {
		 
		OutputStream out = null;
		PrintWriter pw = null;
		try {
			File file = new File(path + fileName + ".csv");
			out = new FileOutputStream(file);
			pw = new PrintWriter(out);
			for (int j = 0; j < titleList.size(); j++) {
				pw.append(titleList.get(j) + ",");
			}
			for (int i = 0; i < csvDetailList.size(); i++) {

				// 社員番号
				String employeeNo = (csvDetailList.get(i).getEmployeeNo());
				// 超過勤務時間数（平日_割増）
				String overWeekdayHours = (csvDetailList.get(i).getOverWeekdayHours());
				// 超過勤務時間数（休日）
				String overWeekdayNomalHours = (csvDetailList.get(i).getOverWeekdayNomalHours());
				// 超過勤務時間数（深夜）
				String overHolidayChangeWorkHours = (csvDetailList.get(i).getOverHolidayChangeWorkHours());
				// 超過勤務時間数（休日出勤振替分）
				String overHolidayHours = (csvDetailList.get(i).getOverHolidayHours());
				// 欠勤時間数
				String overNightHours = (csvDetailList.get(i).getOverNightHours());
				// 超過勤務時間数（平日_通常）
				String absenceHours = (csvDetailList.get(i).getAbsenceHours());
				pw.append("\n");
				pw.append(employeeNo + ",");
				pw.append(overWeekdayHours + ",");
				pw.append(overWeekdayNomalHours + ",");
				pw.append(overHolidayChangeWorkHours + ",");
				pw.append(overHolidayHours + ",");
				pw.append(overNightHours + ",");
				pw.append(absenceHours + ",");
			}
		} catch (Exception e) {

			System.out.println(fileName + ".csv生成失敗");
		} finally {
			pw.flush();
			pw.close();
			out.flush();
			out.close();
		}
	}
}
