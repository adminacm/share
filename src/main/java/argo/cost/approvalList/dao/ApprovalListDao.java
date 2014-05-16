package argo.cost.approvalList.dao;

import java.util.List;

import argo.cost.common.model.entity.ApprovalListEntity;

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

	/**
	 * 申請区分名を取得
	 * 
	 * @param applyKbnCd
	 *                  申請区分コード
	 * @return
	 *        申請区分名
	 */
	String findApplyKbnName(String applyKbnCd);
}
