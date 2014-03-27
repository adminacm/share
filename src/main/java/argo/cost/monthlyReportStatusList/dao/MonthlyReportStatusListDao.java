package argo.cost.monthlyReportStatusList.dao;

import java.util.List;

import argo.cost.common.model.entity.ApprovalList;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;

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
	 * 状況表示名を取得
	 * 
	 * @param status
	 *           状況
	 * @return 状況表示名
	 */
	String getStatusName(String status);
}
