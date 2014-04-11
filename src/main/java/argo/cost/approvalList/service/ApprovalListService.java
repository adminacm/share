package argo.cost.approvalList.service;

import java.util.List;

import argo.cost.approvalList.model.ApprovalListVo;

/**
 * <p>
 * 承認一覧に関するサービスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ApprovalListService {

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *           状況
	 * @return 承認リスト
	 */
	List<ApprovalListVo> getApprovalList(String status);

}
