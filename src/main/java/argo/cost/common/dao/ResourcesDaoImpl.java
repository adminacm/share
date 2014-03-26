package argo.cost.common.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.entity.Resources;

@Repository
public class ResourcesDaoImpl implements ResourcesDao {
	
	/**
	 * 全て資源データ情報を取得します。
	 *
	 * @return 全て資源データ情報
	 */
	@Override
	public List<Resources> findAll() {

		List<Resources> result = new ArrayList<Resources>();

		Resources re1 = new Resources();
		re1.setId("RS001");
		re1.setName("ROLE_USER");
		re1.setMemo("共通業務");
		re1.setUrl("/menu/init");

		Resources re2 = new Resources();
		re2.setId("RS002");
		re2.setName("ROLE_ADMIN");
		re2.setMemo("案件情報");
		re2.setUrl("/opportunity/init");
		

		Resources re3 = new Resources();
		re3.setId("RS000");
		re3.setName("ROLE_USER");
		re3.setMemo("拒否画面");
		re3.setUrl("accessDenied.jsp");
		
		Resources re4 = new Resources();
		re4.setId("RS001");
		re4.setName("ROLE_USER");
		re4.setMemo("月報初期化");
		re4.setUrl("/monthlyReport/init");

		result.add(re1);
		result.add(re2);
		result.add(re3);
		result.add(re4);

		return result;
	}
	
	/**
	 * 権限IDより、資源データ情報を取得します。
	 *
	 * @param roId
	 *            権限ID
	 * 
	 * @return 資源データ情報
	 */
	@Override
	public Set<Resources> findById(String roId) {
		
		Set<Resources> result = new HashSet<Resources>();
		
		Resources re1 = new Resources();
		re1.setId("RS001");
		re1.setName("ROLE_USER");
		re1.setMemo("共通業務");
		re1.setUrl("/menu/init");
		
		Resources re2 = new Resources();
		re2.setId("RS002");
		re2.setName("ROLE_ADMIN");
		re2.setMemo("案件情報");
		re2.setUrl("/opportunity/init");

		Resources re3 = new Resources();
		re3.setId("RS000");
		re3.setName("ROLE_USER");
		re3.setMemo("拒否画面");
		re3.setUrl("accessDenied.jsp");
		
		Resources re4 = new Resources();
		re4.setId("RS001");
		re4.setName("ROLE_USER");
		re4.setMemo("月報初期化");
		re4.setUrl("/monthlyReport/init");
		
		if ("R0001".equals(roId)) {
			result.add(re1);
			result.add(re4);
		}
		if ("R0002".equals(roId)) {
			result.add(re2);
			result.add(re4);
		}
		result.add(re3);
		
		return result;
	}

}
