package argo.cost.approvalList.model;

/**
 * <p>
 * 承認リスト詳細情報
 * </p>
 *
 */
public class ApprovalListVo {
	
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
	private String status;
	
	/**
	 * 所属
	 */
	private String affiliation;
	
	/**
	 * 氏名
	 */
	private String name;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
