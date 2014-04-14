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
	 *              状況
	 * @return 承認リスト
	 */
	List<ApprovalList> getApprovalList(String status);

	/**
	 * 申請区分名を取得
	 * 
	 * @param applyKbnCd
	 *                申請区分コード
	 * @return
	 *        申請区分名
	 */
	String findApplyKbnName(String applyKbnCd);
}
