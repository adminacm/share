package argo.cost.common.model.entity;


/**
 * <p>
 * 権限情報を表現します。
 * </p>
 *
 */
public class Roles {
	
	/**
	 * 権限ID
	 */
	private String id;
	
	/**
	 * 権限名
	 */
	private String name;
	
	/**
	 * 権限状態
	 */
	private Integer enable;

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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

}
