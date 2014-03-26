package argo.cost.approvalList.dao;

import java.util.List;

import argo.cost.common.model.entity.ApprovalList;

/**
 * <p>
 * 承認一覧のアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ApprovalListDao {

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *           状況
	 * @return 承認リスト
	 */
	public List<ApprovalList> getApprovalList(String status);

	/**
	 * 状況表示名を取得
	 * 
	 * @param status
	 *           状況
	 * @return 状況表示名
	 */
	public String getStatusName(String status);
}
