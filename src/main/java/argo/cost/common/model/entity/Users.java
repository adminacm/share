package argo.cost.common.model.entity;


/**
 * <p>
 * ユーザ情報を表現します。
 * </p>
 *
 */
public class Users {
	
	/**
	 * ユーザIDです。
	 */
	private String id;
	
	/**
	 * ユーザ名です。
	 */
	private String name;
	
	/**
	 * ユーザパースです。
	 */
	private String password;
	
	/**
	 * ユーザ状態です。
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	

}
