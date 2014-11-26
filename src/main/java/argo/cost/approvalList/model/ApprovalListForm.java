package argo.cost.approvalList.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 承認一覧画面フォーム情報クラス
 * </p>
 *
 */
public class ApprovalListForm extends AbstractForm implements Serializable {
	
	// ********************
	// ** フィールド **
	// ********************
	/**
	 * パジョンID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 状況
	 */
	private String status;
	
	/**
	 * 状況リスト
	 */
	private List<ListItemVO> statusList;
	
	/**
	 * 承認リスト
	 */
	private List<ApprovalListVO> approvalList;
	
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
	public List<ListItemVO> getStatusList() {
		return statusList;
	}

	/**
	 * 状況リストを設定する
	 */
	public void setStatusList(List<ListItemVO> statusList) {
		this.statusList = statusList;
	}

	/**
	 * 承認リストを取得する
	 */
	public List<ApprovalListVO> getApprovalList() {
		return approvalList;
	}

	/**
	 * 承認リストを設定する
	 */
	public void setApprovalList(List<ApprovalListVO> approvalList) {
		this.approvalList = approvalList;
	}
	
}
