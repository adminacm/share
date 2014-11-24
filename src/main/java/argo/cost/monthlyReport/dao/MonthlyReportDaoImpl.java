package argo.cost.monthlyReport.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.common.dao.BaseDao;
import argo.cost.common.utils.CostDateUtils;

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
	 * ユーザ最後の月報提出日付を取得
	 * 
	 * @param userId
	 *               社員番号
	 * @return
	 *        ユーザ最後の月報提出日付
	 */
	@Override
	public String getUserLatestShinseiMonth(String userId) {
		
		// JPQLを作成する
		StringBuilder stbLatestShinseiDateSql = new StringBuilder();

		stbLatestShinseiDateSql.append("SELECT ");
		stbLatestShinseiDateSql.append("		max(APPLY_YMD)");
		stbLatestShinseiDateSql.append("	FROM");
		stbLatestShinseiDateSql.append("		APPROVAL_MANAGE");
		stbLatestShinseiDateSql.append("	WHERE");
					
		stbLatestShinseiDateSql.append("	APPLY_KBN_CODE = '1'");
		stbLatestShinseiDateSql.append("	and");
		stbLatestShinseiDateSql.append("	USER_ID = ?");
					
		// クエリー取得
		Query query = this.entityManager.createNativeQuery(stbLatestShinseiDateSql.toString());
		query.setParameter(1, userId);
		// 今のシステム日付を取得
		String strLatestShinseiYearMonth = CostDateUtils.getNowDate();
		// 出力対象一覧情報取得
		Object res = query.getSingleResult();
		if (res != null) {
					
			strLatestShinseiYearMonth = res.toString();
		}
					
		return strLatestShinseiYearMonth;
		
	}
	
}
