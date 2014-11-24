package argo.cost.approvalList.dao;

import java.util.List;

import argo.cost.common.entity.ApprovalManage;

/**
 * 承認一覧DAO
 * 承認一覧画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface ApprovalListDao {
	
	/**
	 * 登録したユーザーと同じ部課のユーザ承認管理情報の状況検索
	 * 
	 * @param jyokyoStatus 
	 * 				状況コード
	 * @param userId 
	 * 				ユーザーＩＤ
	 * 
	 * @return 検索した承認管理情報
	 */
	List<ApprovalManage> searchApprovalManageList(String jyokyoStatus,String userId);
	
}
