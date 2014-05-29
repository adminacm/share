package argo.cost.approvalList.model;

/**
 * <p>
 * 承認リスト詳細情報クラス
 * </p>
 *
 */
public class ApprovalListVO {
	
	// ********************
	// ** フィールド **
	// ********************
	/**
	 * 申請番号
	 */
	private String applyNo;
	
	/**
	 * 申請区分
	 */
	private String applyKbnCd;
	
	/**
	 * 申請区分表示名
	 */
	private String applyKbnName;
	
	/**
	 * 申請内容
	 */
	private String applyDetail;
	
	/**
	 * 状況
	 */
	private String statusName;
	
	/**
	 * 所属
	 */
	private String affiliationName;
	
	/**
	 * 氏名
	 */
	private String userName;

	// ************************
	// ** アクセサメソッド　** 
	// ************************
	
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getApplyKbnCd() {
		return applyKbnCd;
	}

	public void setApplyKbnCd(String applyKbnCd) {
		this.applyKbnCd = applyKbnCd;
	}

	public String getApplyKbnName() {
		return applyKbnName;
	}

	public void setApplyKbnName(String applyKbnName) {
		this.applyKbnName = applyKbnName;
	}

	public String getApplyDetail() {
		return applyDetail;
	}

	public void setApplyDetail(String applyDetail) {
		this.applyDetail = applyDetail;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAffiliationName() {
		return affiliationName;
	}

	public void setAffiliationName(String affiliationName) {
		this.affiliationName = affiliationName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
