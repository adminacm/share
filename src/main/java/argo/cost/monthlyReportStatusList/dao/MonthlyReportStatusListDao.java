package argo.cost.monthlyReportStatusList.dao;

import java.util.List;

import argo.cost.common.model.ListItemVO;
import argo.cost.common.model.entity.ApprovalList;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.PayMagistrateCsvInfo;

/**
 * <p>
 * 承認一覧のアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface MonthlyReportStatusListDao {

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *           状況
	 * @return 承認リスト
	 */
	List<ApprovalList> getMonthlyReportStatusList(MonthlyReportStatusListForm form);

	/**
	 * 所属プルダウンリスト取得
	 * 
	 * @return
	 *        所属プルダウンリスト
	 */
	List<ListItemVO> getAffiliationList();

	/**
	 * 給与奉行向けCSVファイル情報を取得
	 * 
	 * @param form 
	 *            画面情報
	 * @return
	 *        給与奉行向けCSVファイル情報
	 */
	List<PayMagistrateCsvInfo> getPayMagistrateCsvList(MonthlyReportStatusListForm form);

}
