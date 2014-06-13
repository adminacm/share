package argo.cost.monthlyReportApproval.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class MonthlyReportApprovalDaoImpl implements MonthlyReportApprovalDao {
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager entityManager;
	
	/**
	 * 最新の申請日付を取得
	 * 
	 * @return
	 *        最新の申請日付
	 */
	@Override
	public String getLatestShinseiDate() {
		
		// JPQLを作成する
		StringBuilder stbLatestShinseiDateSql = new StringBuilder();

		stbLatestShinseiDateSql.append("SELECT ");
		stbLatestShinseiDateSql.append("		max(APP_YM)");
		stbLatestShinseiDateSql.append("	FROM");
		stbLatestShinseiDateSql.append("		APPROVAL_MANAGE");
		stbLatestShinseiDateSql.append("	WHERE");
			
		stbLatestShinseiDateSql.append("	APPLY_KBN_CODE = '1'");
		stbLatestShinseiDateSql.append("	and");
		stbLatestShinseiDateSql.append("	APPLY_STATUS_CODE not in ('01','04')");
			
		// クエリー取得
		Query query = this.entityManager.createNativeQuery(stbLatestShinseiDateSql.toString());
		String strLatestShinseiDate = "";
		// 出力対象一覧情報取得
		Object res = query.getSingleResult();
		if (res != null) {
			
			strLatestShinseiDate = res.toString();
		}
			
		return strLatestShinseiDate;
				
	}
	
}
