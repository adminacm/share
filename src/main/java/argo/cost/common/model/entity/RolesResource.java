package argo.cost.common.model.entity;

/**
 * <p>
 * 権限と資源関連情報を表現します。
 * </p>
 *
 */
public class RolesResource {
	
	/**
	 * 権限ID
	 */
	private String rId;
	
	/**
	 * 資源ID
	 */
	private String rsId;

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getRsId() {
		return rsId;
	}

	public void setRsId(String rsId) {
		this.rsId = rsId;
	}
	

}
