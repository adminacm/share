package argo.cost.approvalList.model;

import java.util.List;

import argo.cost.common.model.ListItem;

/**
 * <p>
 * 承認一覧画面情報を記載します。
 * </p>
 *
 */
public class ApprovalListForm {
	
	// ********************
	// ** フィールド **
	// ********************
	/**
	 * 状況
	 */
	private String status;
	
	/**
	 * 状況リスト
	 */
	private List<ListItem> statusList;
	
	/**
	 * 承認リスト
	 */
	private List<ApprovalListInfo> approvalList;
	
	// ************************
	// ** アクセサメソッド　** 
	// ************************
	/**
	 * 状況を取得する
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 状況を設定する
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 状況リストを取得する
	 */
	public List<ListItem> getStatusList() {
		return statusList;
	}

	/**
	 * 状況リストを設定する
	 */
	public void setStatusList(List<ListItem> statusList) {
		this.statusList = statusList;
	}

	/**
	 * 承認リストを取得する
	 */
	public List<ApprovalListInfo> getApprovalList() {
		return approvalList;
	}

	/**
	 * 承認リストを設定する
	 */
	public void setApprovalList(List<ApprovalListInfo> approvalList) {
		this.approvalList = approvalList;
	}
	
}
