package argo.cost.common.dao;

import java.util.List;

/**
 * 単一テーブル操作DAO <br />
 * テーブル操作に関する処理を行う。
 * 
 */
public interface BaseDao {

	/**
	 * 検索条件より、テーブル情報リスト取得を行う。
	 * 
	 * @param condition
	 *            検索条件情報
	 * @param cls
	 *            エンティティクラス
	 * @param rowIndex
	 *            件数範囲
	 * @return テーブル情報リスト
	 */
	<E> List<E> findResultList(BaseCondition condition, Class<E> cls, Integer... rowIndex);
	

	/**
	 * 検索条件より、ワークテーブル情報リスト取得を行う。
	 * 
	 * @param condition
	 *            検索条件情報
	 * @param cls
	 *            エンティティクラス
	 * @param rowIndex
	 *            件数範囲
	 * @return テーブル情報リスト
	 */
	<E> List<E> findWorkTableList(BaseCondition condition, Class<E> cls, Integer... rowIndex);
	
	/**
	 * 検索条件なし、全でテーブル情報取得を行う。
	 * 
	 * @param cls
	 *            エンティティクラス
	 * @return 全でテーブル情報
	 */
	<E> List<E> findAll(Class<E> cls);
	
	/**
	 * キーより、テーブル情報取得を行う。
	 * 
	 * @param id
	 *            識別ID
	 * @param cls
	 *            エンティティクラス
	 * @return テーブル情報（識別IDがnullの場合、NULLを返却する）
	 */
	<T> T findById(Long id, Class<T> cls);
	
	/**
	 * 検索条件より、単一テーブル情報取得を行う。
	 * 
	 * @param condition
	 *            検索条件情報
	 * @param cls
	 *            エンティティクラス
	 * @return 単一テーブル情報
	 */
	<T> T findSingleResult(BaseCondition condition, Class<T> cls);
	
	/**
	 * 新規データ作成を行う。
	 * 
	 * @param entity
	 *            新規データ
	 */
	<T> void insert(T entity);
	
	/**
	 * データ更新を行う。
	 * 
	 * @param entity
	 *            更新データ
	 */
	<T> void update(T entity);
	
	/**
	 * データ削除を行う。
	 * 
	 * @param entity
	 *            削除データ
	 */
	<T> void delete(T entity);
	
	/**
	 * 削除条件を設定して、データ削除を行う。
	 * 
	 * @param condition
	 *            削除条件
	 * @param cls
	 *            エンティティクラス
	 * @return 削除件数
	 */
	<T> int deleteByCondition(BaseCondition condition, Class<T> cls);
	
	/**
	 * キーより、データ削除を行う。
	 * 
	 * @param id
	 *            識別ID
	 * @param cls
	 *            エンティティクラス
	 */
	<T> void deleteById(Long id, Class<T> cls);
}
