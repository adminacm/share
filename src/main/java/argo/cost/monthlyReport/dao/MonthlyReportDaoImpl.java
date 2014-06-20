package argo.cost.monthlyReport.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.common.dao.BaseDao;

/**
 * 月報画面DaoImpl
 * 報画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Repository
public class MonthlyReportDaoImpl implements MonthlyReportDao{
	
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager entityManager;
	
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;

	/**
	 * 
	 * ユーザの最後の月報提出年月を取得処理
	 * 
	 * @param userId 
	 * 				ユーザID
	 * 
	 * @return 最後の月報提出年月
	 */
	@Override
	public String getUserLatestShinseiMonth(String userId) {
		
		// JPQLを作成する
		StringBuilder stbLatestShinseiDateSql = new StringBuilder();

		stbLatestShinseiDateSql.append("SELECT ");
		stbLatestShinseiDateSql.append("		max(APP_YMD)");
		stbLatestShinseiDateSql.append("	FROM");
		stbLatestShinseiDateSql.append("		APPROVAL_MANAGE");
		stbLatestShinseiDateSql.append("	WHERE");
					
		stbLatestShinseiDateSql.append("	APPLY_KBN_CODE = '1'");
		stbLatestShinseiDateSql.append("	and");
		stbLatestShinseiDateSql.append("	USER_ID = ?");
					
		// クエリー取得
		Query query = this.entityManager.createNativeQuery(stbLatestShinseiDateSql.toString());
		query.setParameter(1, userId);
		String strLatestShinseiYearMonth = "";
		// 出力対象一覧情報取得
		Object res = query.getSingleResult();
		if (res != null) {
					
			strLatestShinseiYearMonth = res.toString();
		}
					
		return strLatestShinseiYearMonth;
		
	}
	
}
