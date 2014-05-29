package argo.cost.approvalList.dao;

import java.util.List;

import argo.cost.approvalList.model.ApprovalListVO;

/**
 * <p>
 * 承認一覧ＤＡＯのインターフェース
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
	 * @return 承認一覧リスト
	 */
	List<ApprovalListVO> getApprovalList(String status);
}
