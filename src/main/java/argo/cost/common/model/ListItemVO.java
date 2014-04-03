package argo.cost.common.model;

public class ListItemVO {
	/**
	 * 区分値
	 */
	private String value;
	
	/**
	 * 区分名称
	 */
	private String name;

	// ########################
	// ### アクセサメソッド ###
	// ########################
	/**
	 * 区分値を取得する。
	 * 
	 * @return 区分値
	 */
	public String getValue() {

		return this.value;
	}

	/**
	 * 区分値を設定する。
	 * 
	 * @param value
	 *            区分値
	 */
	public void setValue(String value) {

		this.value = value;
	}
	
	/**
	 * 区分名称を取得する。
	 * 
	 * @return 区分名称
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * 区分名称を設定する。
	 * 
	 * @param name
	 *            区分名称
	 */
	public void setName(String name) {

		this.name = name;
	}

}
