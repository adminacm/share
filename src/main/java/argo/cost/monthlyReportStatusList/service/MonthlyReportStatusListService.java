package argo.cost.monthlyReportStatusList.service;

import java.util.Date;
import java.util.List;

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
	 * 月報状況一覧リストを取得
	 * 
	 * @param form
	 *            月報状況一覧情報
	 * @return 
	 *        月報状況一覧リスト
	 */
	List<MonthlyReportStatusListVo> getMonthlyReportStatusList(MonthlyReportStatusListForm form);

}
