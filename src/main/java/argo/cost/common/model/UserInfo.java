package argo.cost.common.model;

import java.io.Serializable;

/**
 * <p>
 * ユーザ情報を表現します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1;

	// ********************
	// *****フィールド*****
	// ********************
	/**
	 * ユーザIDです。
	 */
	private String userId;

	/**
	 * ユーザ名称です。
	 */
	private String userName;
	
	private String password;
	
	/**
	 * 操作権限区分です。
	 */
	private String opeKbn;

	// ********************
	// **アクセスメッソド**
	// ********************
	/**
	 * ユーザIDを取得します。
	 *
	 * @return ユーザID
	 */
	public String getUserId() {

		return userId;
	}

	/**
	 * ユーザIDを設定します。
	 *
	 * @param ユーザID
	 */
	public void setUserId(String userId) {

		this.userId = userId;
	}

	/**
	 * ユーザ名称を取得します。
	 *
	 * @return ユーザ名称
	 */
	public String getUserName() {

		return userName;
	}

	/**
	 * ユーザ名称を設定します。
	 *
	 * @param ユーザ名称
	 */
	public void setUserName(String userName) {

		this.userName = userName;
	}

	/**
	 * 操作権限区分を取得します。
	 *
	 * @return 操作権限区分
	 */
	public String getOpeKbn() {

		return opeKbn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 操作権限区分を設定します。
	 *
	 * @param 操作権限区分
	 */
	public void setOpeKbn(String opeKbn) {

		this.opeKbn = opeKbn;
	}
}