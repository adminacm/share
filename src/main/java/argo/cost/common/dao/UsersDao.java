package argo.cost.common.dao;

import java.util.List;

import argo.cost.common.entity.Roles;

/**
 * <p>
 * ユーザ情報を提供します。
 * </p>
 *
 */
public interface UsersDao {
	
	/**
	 * ユーザIDより、権限情報を取得します。
	 *
	 * @param userId
	 *            ユーザID
	 * 
	 * @return 権限情報
	 */
	List<Roles> findRoles(String userName);

}
