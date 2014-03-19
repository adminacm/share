package argo.cost.common.dao;

import argo.cost.common.model.UserInfo;
import argo.cost.common.model.UserKengen;
import argo.cost.common.model.entity.Users;

/**
 * <p>
 * 共通部品に関するデータへのアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ComDao {
	
	
	/**
	 * ユーザ名より、ユーザ情報を取得します。
	 *
	 * @param name
	 *            ユーザ名
	 * 
	 * @return ユーザ情報
	 */
	Users findByName(String name);
	

	/**
	 * ユーザ情報を取得します。
	 *
	 * @param userId
	 *            ユーザID
	 * @return ユーザ情報
	 */
	UserInfo findUserById(String userId);

	/**
	 * システム設定値を取得します。
	 *
	 * @param setKey
	 *            設定キー
	 * @return 設定値
	 */
	String findSysSetVal(String setKey);
	
	/**
	 * ユーザ権限情報を取得します。
	 *
	 * @param userId
	 *            ユーザID
	 * @return ユーザ権限情報
	 */
	UserKengen findUserKengenById(String userId);
}
