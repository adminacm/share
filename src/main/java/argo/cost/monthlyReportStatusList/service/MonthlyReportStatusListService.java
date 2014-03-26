package argo.cost.monthlyReportStatusList.service;

import java.util.Date;
import java.util.List;

import argo.cost.common.model.ListItem;
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
	 * 年月プルダウンリスト取得
	 * 
	 * @param date
	 * 	      　　　　　　日付
	 * @return
	 * 	             年月プルダウンリスト
	 */
	List<ListItem> getYearMonthList(Date date);

	/**
	 * 所属プルダウンリスト取得
	 * 
	 * @return 
	 *        所属プルダウンリスト
	 */
	List<ListItem> getAffiliationList();

}
