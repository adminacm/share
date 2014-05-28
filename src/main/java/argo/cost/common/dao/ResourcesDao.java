package argo.cost.common.dao;

import java.util.List;

import argo.cost.common.entity.Resources;

/**
 * <p>
 * 資源データを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ResourcesDao {

	/**
	 * 権限名より、資源データ情報を取得します。
	 *
	 * @param name
	 *            権限名
	 * 
	 * @return 資源データ情報
	 */
	List<Resources> findByName(String name);

}
