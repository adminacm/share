package argo.cost.common.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.entity.Roles;
import argo.cost.common.model.entity.Users;

@Repository
public class UsersDaoImpl implements UsersDao {
	
	/**
	 * ユーザ名より、ユーザ情報を取得します。
	 *
	 * @param name
	 *            ユーザ名
	 * 
	 * @return ユーザ情報
	 */
	@Override
	public Users findByName(String name) {
		
		Users user = null;
		
		if ("user01".equals(name)) {
			user = new Users();
			user.setEnable(1);
			user.setId("U0001");
			user.setName(name);
			user.setPassword("user01");
		}
		
		if ("admin".equals(name)) {
			user = new Users();
			user.setEnable(1);
			user.setId("U0002");
			user.setName(name);
			user.setPassword("admin");
		}
		
		return user;
	}
	
	/**
	 * ユーザIDより、権限情報を取得します。
	 *
	 * @param userId
	 *            ユーザID
	 * 
	 * @return 権限情報
	 */
	@Override
	public Set<Roles> findRoles(String userId) {
		
		Set<Roles> roles = new HashSet<Roles>();
		
		Roles ro1 = new Roles();
		ro1.setEnable(1);
		ro1.setId("R0001");
		ro1.setName("システム管理者");
		
		Roles ro2 = new Roles();
		ro2.setEnable(1);
		ro2.setId("R0002");
		ro2.setName("一般ユーザ");
		
		if ("U0001".equals(userId)) {
			roles.add(ro1);
		} 
		if ("U0002".equals(userId)) {
			roles.add(ro1);
			roles.add(ro2);
		}
		
		return roles;
	}

}
