package argo.cost.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import argo.cost.common.entity.Roles;

@Repository
public class UsersDaoImpl implements UsersDao {
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;	
	
	/**
	 * ユーザIDより、権限情報を取得します。
	 *
	 * @param userName
	 *            ユーザ名
	 * 
	 * @return 権限情報
	 */
	@Override
	public List<Roles> findRoles(String userName) {

		// JPQLを作成する
		StringBuilder q = new StringBuilder();

		q.append("SELECT ");
		q.append("		r.id");
		q.append(",		r.name");
		q.append("	FROM");
		q.append("		users u");
		q.append(",		roles r");
		q.append(",		user_roles ur");
		q.append("	WHERE");
		q.append("	u.id = ur.user_id");
		q.append("	AND");
		q.append("	r.id = ur.role_id");
		q.append("	AND");
		q.append("	u.login_mail_address = ?");
		q.append("	AND");
		q.append("	u.status = '0'");
		q.append("	AND");
		q.append("	r.status = '0'");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(q.toString());
		
		int index = 1;

		query.setParameter(index++, userName);
		
		// 出力対象一覧情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();
		
		List<Roles> roles = new ArrayList<Roles>();
		Roles role = null;
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			roles = new ArrayList<Roles>();
			for (int i = 0; i < resultList.size(); i++) {
				role = new Roles();
				Object[] items = (Object[]) resultList.get(i);
				index = 0;
				// 検索結果に設定
				role.setId((Integer) items[index++]);
				role.setName((String) items[index++]);
				
				roles.add(role);
			}
		}
		return roles;
	}

}
