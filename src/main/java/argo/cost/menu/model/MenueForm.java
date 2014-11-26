package argo.cost.menu.model;

import java.io.Serializable;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.UserVO;

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
	private UserVO userInfo;

	public UserVO getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserVO userInfo) {
		this.userInfo = userInfo;
	}
	
}
