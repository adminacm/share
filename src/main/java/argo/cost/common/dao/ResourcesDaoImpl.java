package argo.cost.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import argo.cost.common.entity.Resources;

@Repository
public class ResourcesDaoImpl implements ResourcesDao {
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;	
	
	/**
	 * 権限名より、資源データ情報を取得します。
	 *
	 * @param name
	 *            権限名
	 * 
	 * @return 資源データ情報
	 */
	@Override
	public List<Resources> findByName(String name) {

		// JPQLを作成する
		StringBuilder q = new StringBuilder();

		q.append("SELECT ");
		q.append("		rs.id");
		q.append(",		rs.name");
		q.append(",		rs.url");
		q.append("	FROM");
		q.append("		resources rs");
		q.append(",		roles r");
		q.append(",		role_resource rr");
		q.append("	WHERE");
		q.append("	rs.id = rr.resource_id");
		q.append("	AND");
		q.append("	r.id = rr.role_id");
		q.append("	AND");
		q.append("	r.name = ?");
		q.append("	AND");
		q.append("	r.status = 0");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(q.toString());
		
		int index = 1;

		query.setParameter(index++, name);
		
		// 出力対象一覧情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		List<Resources> result = new ArrayList<Resources>();
		Resources resource = null;
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			result = new ArrayList<Resources>();
			for (int i = 0; i < resultList.size(); i++) {
				resource = new Resources();
				Object[] items = (Object[]) resultList.get(i);
				index = 0;
				// 検索結果に設定
				resource.setId((Integer) items[index++]);
				resource.setName((String) items[index++]);
				resource.setUrl((String) items[index++]);
				
				result.add(resource);
			}
		}
		
		return result;
	}
}
