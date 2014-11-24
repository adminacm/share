package argo.cost.monthlyReportStatusList.model;

/**
 * <p>
 * 月報状況一覧リストを記載します。
 * </p>
 *
 */
public class MonthlyReportStatusListVo {
	
	// ********************
	// ** フィールド **
	// ********************
	/**
	 * 申請番号
	 */
	private String applyNo;
	
	/**
	 * 所属
	 */
	private String affiliationName;
	
	/**
	 * ID
	 */
	private String userId;
	
	/**
	 * 申請者
	 */
	private String appliedUserName;
	
	/**
	 * 承認者
	 */
	private String approverName;
	
	/**
	 * 承認日
	 */
	private String approvedYmd;
	
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
	 * 申請年月
	 */
	private String applyYm;

	// ************************
	// ** アクセサメソッド　** 
	// ************************
	
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getAffiliationName() {
		return affiliationName;
	}

	public void setAffiliationName(String affiliationName) {
		this.affiliationName = affiliationName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppliedUserName() {
		return appliedUserName;
	}

	public void setAppliedUserName(String appliedUserName) {
		this.appliedUserName = appliedUserName;
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

	public String getApplyYm() {
		return applyYm;
	}

	public void setApplyYm(String applyYm) {
		this.applyYm = applyYm;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApprovedYmd() {
		return approvedYmd;
	}

	public void setApprovedYmd(String approvedYmd) {
		this.approvedYmd = approvedYmd;
	}
}
