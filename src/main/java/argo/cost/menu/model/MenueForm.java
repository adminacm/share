package argo.cost.menu.model;

import java.io.Serializable;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.entity.Users;

/**
 * <p>
 * Menueの画面入力情報を記載します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public class MenueForm extends AbstractForm implements Serializable {

	private static final long serialVersionUID = 1;
	
	// ********************
	// *****フィールド*****
	// ********************
	private Users userInfo;

	public Users getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Users userInfo) {
		this.userInfo = userInfo;
	}
	
}
