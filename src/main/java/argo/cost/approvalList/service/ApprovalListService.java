package argo.cost.approvalList.service;

import java.util.List;

import argo.cost.approvalList.model.ApprovalListVO;

/**
 * <p>
 * 承認一覧サービスのインターフェース
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ApprovalListService {

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *              状況
	 * @return 承認リスト
	 */
	List<ApprovalListVO> getApprovalList(String status);
}
