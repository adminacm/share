package argo.cost.menu.dao;

import argo.cost.menu.model.UserInfo;
import argo.cost.menu.model.UserKengen;

/**
 * <p>
 * 共通部品に関するデータへのアクセスクラスを提供します。
 * </p>
 *
 * @author 
 */
public interface ComDao {
	
	/**
	 * ユーザ情報を取得します。
	 *
	 * @param userId
	 *            ユーザID
	 * @return ユーザ情報
	 */
	UserInfo findUserById(String userId);

	/**
	 * ユーザ権限情報を取得します。
	 *
	 * @param userId
	 *            ユーザID
	 * @return ユーザ権限情報
	 */
	UserKengen findUserKengenById(String userId);

}
