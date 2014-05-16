package argo.cost.common.model.entity;

/**
 * <p>
 * 承認リストを記載します。
 * </p>
 *
 */
public class ApprovalListEntity {
	
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
	private String applyKbn;
	
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
	 * ID
	 */
	private String id;
	
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
	
	public String getApplyKbn() {
		return applyKbn;
	}

	public void setApplyKbn(String applyKbn) {
		this.applyKbn = applyKbn;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
