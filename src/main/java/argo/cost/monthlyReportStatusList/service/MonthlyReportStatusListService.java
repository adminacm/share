package argo.cost.monthlyReportStatusList.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListInfo;

/**
 * <p>
 * 月報状況一覧に関するサービスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface MonthlyReportStatusListService {

	/**
	 * 月報状況一覧リストを取得
	 * 
	 * @param form
	 *           月報状況一覧情報
	 * @return 
	 *        月報状況一覧リスト
	 */
	List<MonthlyReportStatusListInfo> getMonthlyReportStatusList(MonthlyReportStatusListForm form);

	/**
	 * 年プルダウンリスト取得
	 * 
	 * @param date
	 * 	      　　　　　　日付
	 * @return
	 * 	             年プルダウンリスト
	 */
	List<ListItemVO> getYearList(Date date);
	
	/**
	 * 月プルダウンリスト取得
	 * 
	 * @return
	 * 	             月プルダウンリスト
	 */
	List<ListItemVO> getMonthList();

	/**
	 * 所属プルダウンリスト取得
	 * 
	 * @return 
	 *        所属プルダウンリスト
	 */
	List<ListItemVO> getAffiliationList();

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
	void createCSVFile(MonthlyReportStatusListForm form, HttpServletResponse response) throws Exception;
}
