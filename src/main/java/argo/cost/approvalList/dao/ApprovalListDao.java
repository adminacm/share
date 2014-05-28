package argo.cost.approvalList.dao;

import java.util.List;

import argo.cost.common.entity.ApprovalListEntity;

/**
 * <p>
 * 承認一覧ＤＡＯのインターフェース
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ApprovalListDao {

	/**
	 * ＤＢから、承認データを取得
	 * 
	 * @param status
	 *              状況
	 * @return 承認データ
	 */
	List<ApprovalListEntity> getApprovalList(String status);
}
