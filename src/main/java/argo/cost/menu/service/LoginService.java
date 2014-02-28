package argo.cost.menu.service;

import argo.cost.menu.model.UserInfo;
import argo.cost.menu.model.UserKengen;


public interface LoginService {
	
	// ユーザー情報の取得
	public UserInfo getUserInfo(UserInfo user);
	
	public UserKengen getUserKengen(String loginId);
	
//	// ユーザーの権限レベルによって、アクセスできる画面リストを作成
//    public ArrayList getAccess(UserKengen userKengen);
//    
//	//メニュー画面からクリックし、対応した画面に遷移する
//	public String  getLinkIDPath(MenuForm menuPage);

}
