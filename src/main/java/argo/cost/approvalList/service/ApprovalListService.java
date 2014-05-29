package argo.cost.approvalList.service;

import java.util.List;

import argo.cost.approvalList.model.ApprovalListVO;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 承認一覧サービスのインターフェース
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ApprovalListService {

	/**
	 * 状況プルダウンリスト取得
	 * 
	 * @return 状況プルダウンリスト
	 */
	List<ListItemVO> getStatusList();

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *              状況
	 * @return 承認一覧リスト
	 */
	List<ApprovalListVO> getApprovalList(String status);
}
