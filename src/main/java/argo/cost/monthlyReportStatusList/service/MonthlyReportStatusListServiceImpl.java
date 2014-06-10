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

import argo.cost.common.dao.BaseDao;
import argo.cost.common.dao.ComDao;
import argo.cost.common.entity.AffiliationMaster;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.dao.MonthlyReportStatusListDao;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;
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
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 年プルダウンリストを取得
	 * 
	 * @param date
	 * 	      　　　　　　 日付
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
	 * 月プルダウンリストを取得
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
			if (i < 10) {
				// 区分値 
				item.setValue("0" + i);
				// 区分名称
				item.setName("0" + i + "月");
			} else {
				
				// 区分値 
				item.setValue(String.valueOf(i));
				// 区分名称
				item.setName(String.valueOf(i) + "月");
			}

			// リストに追加
			resultList.add(item);
		}
		
		// 月ドロップダウンリストを返却する。
		return resultList;
	
	}

	/**
	 * 所属プルダウンリストを取得
	 * 
	 * @return 
	 *        所属プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getAffiliationList() {
		
		// ＤＢから、所属プルダウンリスト取得
		List<AffiliationMaster> affiliationList = baseDao.findAll(AffiliationMaster.class);

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = new ListItemVO();
		item.setValue("");
		item.setName(null);
		resultList.add(item);

		// ドロップダウンリスト設定
		for (AffiliationMaster resultInfo : affiliationList) {
			
			item = new ListItemVO();

			// データを設定する
			// 区分値 
			item.setValue(resultInfo.getCode());
			// 区分名称
			item.setName(resultInfo.getName());

			// リストに追加
			resultList.add(item);
		}

		// 所属プルダウンリストを返却する。
		return resultList;
	}

	/**
	 * 状況プルダウンリストを取得
	 * 
	 * @return 
	 *        状況プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getStatusList() {
		
		// ＤＢから、状況プルダウンリスト取得
		List<StatusMaster> StatusList = baseDao.findAll(StatusMaster.class);

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = new ListItemVO();
		item.setValue("");
		item.setName(null);
		resultList.add(item);

		// ドロップダウンリスト設定
		for (StatusMaster resultInfo : StatusList) {
			
			item = new ListItemVO();

			// データを設定する
			// 区分値 
			item.setValue(resultInfo.getCode());
			// 区分名称
			item.setName(resultInfo.getName());

			// リストに追加
			resultList.add(item);
		}

		// 状況プルダウンリストを返却する。
		return resultList;
	}

	/**
	 * 月報状況一覧リストを取得
	 * 
	 * @param form
	 *            月報状況一覧情報
	 * @return 
	 *        月報状況一覧リスト
	 */
	@Override
	public List<MonthlyReportStatusListVo> getMonthlyReportStatusList(MonthlyReportStatusListForm form) {
		
		// 月報状況一覧リスト
		List<MonthlyReportStatusListVo> monthlyReportStatusList = monthlyReportStatusListDao.getMonthlyReportStatusList(form);

		// 月報状況一覧リストを戻り
		return monthlyReportStatusList;
	}

	/**
	 * CSVファイルを作成
	 * 
	 * @param form
	 *            月報状況一覧情報
     * @param response
     *                レスポンス
	 * @return
	 *        CSVファイル情報
	 * @throws Exception 
	 *                  異常
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
	 * 
	 * @return ヘッダ部データ
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
	 *                ＣＳＶファイルの名前    
	 * @param titleList
	 * 			               ヘッダ部表示するタイトルリスト
	 * @param csvDetailList
	 * 	     	                          ＣＳＶファイル詳細データリスト
	 * @param response
	 *                レスポンス
	 * @throws Exception
	 *                  異常
	 */
	 private void exportCsvfiles(String path, String fileName, List<String> titleList, List<PayMagistrateCsvInfo> csvDetailList, HttpServletResponse response) throws Exception {
		 
		OutputStream out = null;
		PrintWriter pw = null;

		// 社員番号
		String employeeNo = "";
		// 超過勤務時間数（平日_割増）
		String overWeekdayHours = "";
		// 超過勤務時間数（休日）
		String overWeekdayNomalHours = "";
		// 超過勤務時間数（深夜）
		String overHolidayChangeWorkHours = "";
		// 超過勤務時間数（休日出勤振替分）
		String overHolidayHours = "";
		// 欠勤時間数
		String overNightHours = "";
		// 超過勤務時間数（平日_通常）
		String absenceHours = "";
		
		try {
			File file = new File(path + fileName + ".csv");
			out = new FileOutputStream(file);
			pw = new PrintWriter(out);
			for (int j = 0; j < titleList.size(); j++) {
				pw.append(titleList.get(j) + ",");
			}
			for (int i = 0; i < csvDetailList.size(); i++) {

				// 社員番号
				employeeNo = (csvDetailList.get(i).getEmployeeNo());
				// 超過勤務時間数（平日_割増）
				overWeekdayHours = (csvDetailList.get(i).getOverWeekdayHours());
				// 超過勤務時間数（休日）
				overWeekdayNomalHours = (csvDetailList.get(i).getOverWeekdayNomalHours());
				// 超過勤務時間数（深夜）
				overHolidayChangeWorkHours = (csvDetailList.get(i).getOverHolidayChangeWorkHours());
				// 超過勤務時間数（休日出勤振替分）
				overHolidayHours = (csvDetailList.get(i).getOverHolidayHours());
				// 欠勤時間数
				overNightHours = (csvDetailList.get(i).getOverNightHours());
				// 超過勤務時間数（平日_通常）
				absenceHours = (csvDetailList.get(i).getAbsenceHours());
				
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
