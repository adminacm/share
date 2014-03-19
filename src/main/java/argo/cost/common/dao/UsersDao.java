package argo.cost.common.dao;

import java.util.Set;

import argo.cost.common.model.entity.Roles;
import argo.cost.common.model.entity.Users;

/**
 * <p>
 * ユーザ情報を提供します。
 * </p>
 *
 */
public interface UsersDao {
	
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
	 * ユーザIDより、権限情報を取得します。
	 *
	 * @param userId
	 *            ユーザID
	 * 
	 * @return 権限情報
	 */
	Set<Roles> findRoles(String userId);

}
