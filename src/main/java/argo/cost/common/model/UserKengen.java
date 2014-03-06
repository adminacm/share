package argo.cost.common.model;


// 就業管理の権限取得
public class UserKengen {
	
	// 権限コード
	String kengenCd;
	// ユーザーID
	String userId;
    // ユーザー名
	String userName;
	public String getKengenCd() {
		return kengenCd;
	}

	public void setKengenCd(String kengenCd) {
		this.kengenCd = kengenCd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
