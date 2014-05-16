package argo.cost.monthlyReportStatusList.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;

/**
 * <p>
 * 月報状況一覧サービスのインターフェイス
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface MonthlyReportStatusListService {

	/**
	 * 月報状況一覧リストを取得
	 * 
	 * @param monthlyReportStatusListForm
	 *                                   月報状況一覧情報
	 * @return 
	 *        月報状況一覧リスト
	 */
	List<MonthlyReportStatusListVo> getMonthlyReportStatusList(MonthlyReportStatusListForm monthlyReportStatusListForm);

	/**
	 * 年プルダウンリストを取得
	 * 
	 * @param date
	 * 	      　　　　　　日付
	 * @return
	 * 	             年プルダウンリスト
	 */
	List<ListItemVO> getYearList(Date date);
	
	/**
	 * 月プルダウンリストを取得
	 * 
	 * @return
	 * 	             月プルダウンリスト
	 */
	List<ListItemVO> getMonthList();

	/**
	 * 所属プルダウンリストを取得
	 * 
	 * @return 
	 *        所属プルダウンリスト
	 */
	List<ListItemVO> getAffiliationList();

	/**
	 * CSVファイルを作成
	 * 
	 * @param monthlyReportStatusListForm
	 *                                   月報状況一覧情報
     * @param response
     *                レスポンス
	 * @throws Exception 
	 *                  異常
	 */
	void createCSVFile(MonthlyReportStatusListForm monthlyReportStatusListForm, HttpServletResponse response) throws Exception;
}
