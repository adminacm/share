package argo.cost.common.dao;

import java.util.List;
import java.util.Set;

import argo.cost.common.model.entity.Resources;

/**
 * <p>
 * 資源データを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ResourcesDao {
	
	/**
	 * 全て資源データ情報を取得します。
	 *
	 * @return 全て資源データ情報
	 */
	List<Resources> findAll();

	/**
	 * 権限IDより、資源データ情報を取得します。
	 *
	 * @param roId
	 *            権限ID
	 * 
	 * @return 資源データ情報
	 */
	Set<Resources> findById(String roId);

}
