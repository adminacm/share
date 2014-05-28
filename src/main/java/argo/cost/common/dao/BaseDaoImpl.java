package argo.cost.common.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

/**
 * 単一テーブル操作DAOImpl <br />
 * テーブル操作に関する処理を行う。
 * 
 */
@Repository
public class BaseDaoImpl implements BaseDao {
	
	// ############################
	// ### 依存性注入フィールド ###
	// ############################
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext()
	protected EntityManager em;	
	
	// ######################
	// ### Publicメソッド ###
	// ######################
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
	@Override
	public <E> List<E> findResultList(BaseCondition condition, Class<E> cls, Integer... rowIndex) {

		// JPQL条件文生成
		StringBuilder sbJpql = new StringBuilder(this.getJpqlTemplate(cls));

		// 検索条件設定
		if (condition.getConditonSql().length() > 0) {
			sbJpql.append(" WHERE ").append(condition.getConditonSql());
		}

		// オーダー設定
		if (condition.getOrderSql().length() > 0) {
			sbJpql.append(" ORDER BY ").append(condition.getOrderSql());
		}

		// クエリー取得
		TypedQuery<E> tq = this.em.createQuery(sbJpql.toString(), cls);

		// パラメータ取得
		Iterator<String> it = condition.getConditionMap().keySet().iterator();

		// JPQLパラメータ設定
		while (it.hasNext()) {
			String key = (String) it.next();
			tq.setParameter(key, condition.getConditionMap().get(key));
		}

		if (rowIndex != null) {
			if (rowIndex.length == 1) {
				// 取得最大件数設定
				tq.setMaxResults(rowIndex[0]);
			}

			if (rowIndex.length == 2) {
				// 取得開始件数設定
				tq.setFirstResult(rowIndex[0]);
				// 取得最大件数設定
				tq.setMaxResults(rowIndex[1]);
			}
		}

		this.em.getEntityManagerFactory().getCache().evictAll();
		// 検索結果リスト情報を返却する
		return tq.getResultList();
	}

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
	@Override
	public <E> List<E> findWorkTableList(BaseCondition condition, Class<E> cls, Integer... rowIndex) {

		// JPQL条件文生成
		StringBuilder sbJpql = new StringBuilder();
		sbJpql.append("SELECT t FROM ").append(cls.getSimpleName());

		// 検索条件設定
		if (condition.getConditonSql().length() > 0) {
			sbJpql.append(" WHERE ").append(condition.getConditonSql());
		}

		// オーダー設定
		if (condition.getOrderSql().length() > 0) {
			sbJpql.append(" ORDER BY ").append(condition.getOrderSql());
		}

		// クエリー取得
		TypedQuery<E> tq = this.em.createQuery(sbJpql.toString(), cls);

		// パラメータ取得
		Iterator<String> it = condition.getConditionMap().keySet().iterator();

		// JPQLパラメータ設定
		while (it.hasNext()) {
			String key = (String) it.next();
			tq.setParameter(key, condition.getConditionMap().get(key));
		}

		if (rowIndex != null) {
			if (rowIndex.length == 1) {
				// 取得最大件数設定
				tq.setMaxResults(rowIndex[0]);
			}

			if (rowIndex.length == 2) {
				// 取得開始件数設定
				tq.setFirstResult(rowIndex[0]);
				// 取得最大件数設定
				tq.setMaxResults(rowIndex[1]);
			}
		}

		this.em.getEntityManagerFactory().getCache().evictAll();
		// 検索結果リスト情報を返却する
		return tq.getResultList();
	}

	/**
	 * 検索条件なし、全でテーブル情報取得を行う。
	 * 
	 * @param cls
	 *            エンティティクラス
	 * @return 全でテーブル情報
	 */
	@Override
	public <E> List<E> findAll(Class<E> cls) {

		this.em.getEntityManagerFactory().getCache().evictAll();
		TypedQuery<E> tq = this.em.createQuery(this.getJpqlTemplate(cls), cls);

		// 検索結果リスト情報を返却する
		return tq.getResultList();
	}

	/**
	 * キーより、テーブル情報取得を行う。
	 * 
	 * @param id
	 *            識別ID
	 * @param cls
	 *            エンティティクラス
	 * @return テーブル情報（識別IDがnullの場合、NULLを返却する）
	 */
	@Override
	public <T> T findById(Long id, Class<T> cls) {

		// 識別IDがnullの場合
		if (id == null) {
			return null;
		}

		this.em.getEntityManagerFactory().getCache().evictAll();
		// 検索結果情報を返却する
		return this.em.find(cls, id);
	}

	/**
	 * 検索条件より、単一テーブル情報取得を行う。
	 * 
	 * @param condition
	 *            検索条件情報
	 * @param cls
	 *            エンティティクラス
	 * @return 単一テーブル情報
	 */
	@Override
	public <T> T findSingleResult(BaseCondition condition, Class<T> cls) {

		// JPQL条件文生成
		StringBuilder sbJpql = new StringBuilder(this.getJpqlTemplate(cls));
		// 検索条件設定
		if (condition.getConditonSql().length() > 0) {
			sbJpql.append(" WHERE ").append(condition.getConditonSql());
		}

		// オーダー設定
		if (condition.getOrderSql().length() > 0) {
			sbJpql.append(" ORDER BY ").append(condition.getOrderSql());
		}

		// クエリー取得
		TypedQuery<T> tq = this.em.createQuery(sbJpql.toString(), cls);

		// パラメータ取得
		Iterator<String> it = condition.getConditionMap().keySet().iterator();

		// JPQLパラメータ設定
		while (it.hasNext()) {
			String key = (String) it.next();
			tq.setParameter(key, condition.getConditionMap().get(key));
		}

		T result = null;
		try {
			this.em.getEntityManagerFactory().getCache().evictAll();
			// 検索を行う
			result = tq.getSingleResult();
		} catch (NoResultException noresult) {
		} catch (NonUniqueResultException nounique) {
		}

		// 検索結果情報を返却する
		return result;
	}

	/**
	 * 新規データ作成を行う。
	 * 
	 * @param entity
	 *            新規データ
	 */
	@Override
	public <T> void insert(T entity) {

		// 新規データ作成
		this.em.persist(entity);
	}

	/**
	 * データ更新を行う。
	 * 
	 * @param entity
	 *            更新データ
	 */
	@Override
	public <T> void update(T entity) {

		// データ更新
		this.em.merge(entity);
	}

	/**
	 * データ削除を行う。
	 * 
	 * @param entity
	 *            削除データ
	 */
	@Override
	public <T> void delete(T entity) {

		// データ削除
		this.em.remove(entity);
	}

	/**
	 * 削除条件を設定して、データ削除を行う。
	 * 
	 * @param condition
	 *            削除条件
	 * @param cls
	 *            エンティティクラス
	 * @return 削除件数
	 */
	@Override
	public <T> int deleteByCondition(BaseCondition condition, Class<T> cls) {

		// 削除件数
		int delCnt = 0;

		// 削除データ取得
		List<T> list = this.findResultList(condition, cls);

		for (T entity : list) {
			// データ削除
			this.em.remove(entity);
			delCnt++;
		}

		// 削除件数を返却する
		return delCnt;
	}

	/**
	 * キーより、データ削除を行う。
	 * 
	 * @param id
	 *            識別ID
	 * @param cls
	 *            エンティティクラス
	 */
	@Override
	public <T> void deleteById(Long id, Class<T> cls) {

		// 削除データ取得
		T entity = this.findById(id, cls);

		// データ削除
		this.em.remove(entity);
	}

	// ######################
	// ### 内部メソッド ###
	// ######################
	/**
	 * JPQL文作成
	 * 
	 * @param cls
	 *            エンティティクラス
	 * @return JPQL文
	 */
	private <T> String getJpqlTemplate(Class<T> cls) {

		return "SELECT t FROM " + cls.getSimpleName() + " t ";
	}
}
