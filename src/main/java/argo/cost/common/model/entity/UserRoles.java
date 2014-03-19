package argo.cost.common.model.entity;

/**
 * <p>
 * ユーザと権限関連情報を表現します。
 * </p>
 *
 */
public class UserRoles {
	
	/**
	 * ユーザID
	 */
	private String uid;
	
	/**
	 * 権限ID
	 */
	private String rid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
	

}
