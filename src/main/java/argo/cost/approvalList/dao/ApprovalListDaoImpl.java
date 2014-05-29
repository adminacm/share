package argo.cost.approvalList.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import argo.cost.approvalList.model.ApprovalListVO;

/**
 * <p>
 * 承認一覧ＤＡＯ
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class ApprovalListDaoImpl implements ApprovalListDao {
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *              状況
	 * @return 承認一覧リスト
	 */
	@Override
	public List<ApprovalListVO> getApprovalList(String status) {

		// JPQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                                              ");
		strSql.append(" 	t1.apply_no          AS applyNo                ");
		strSql.append(" 	, t2.code            AS applyKbnCode           ");
		strSql.append(" 	, t2.name            AS applyKbnName           ");
		strSql.append(" 	, t1.apply_detail    AS applyDetail            ");
		strSql.append(" 	, t3.name            AS statusName             ");
		strSql.append(" 	, t4.affiliationName AS affiliationName        ");
		strSql.append(" 	, t4.userName        AS userName               ");
		strSql.append("FROM                                                ");
		strSql.append(" 	approval_manage t1                             ");
		strSql.append("LEFT JOIN apply_kbn_master t2                       ");
		strSql.append(" 	ON t1.apply_kbn_code = t2.code                 ");
		strSql.append("LEFT JOIN status_master  t3                         ");
		strSql.append(" 	ON t1.apply_status_code = t3.code              ");
		strSql.append("LEFT JOIN                                           ");
		strSql.append(" 		(SELECT t5.id          AS userId           ");
		strSql.append(" 				, t5.user_name AS userName         ");
		strSql.append(" 				, t6.name      AS affiliationName  ");
		strSql.append(" 	 	 FROM users t5                             ");
		strSql.append(" 		 	  , affiliation_master t6              ");
		strSql.append(" 	 	 WHERE t5.shozoku_id = t6.code) t4         ");
		strSql.append(" 	ON t1.user_id = t4.userId                      ");
		// 状況がnull以外の場合
		if (!status.isEmpty()) { 
			strSql.append("WHERE                                           ");
			strSql.append(" 	t1.apply_status_code = ?                   ");
		}
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;

		// 状況がnull以外の場合
		if (!status.isEmpty()) { 
			query.setParameter(index++, status);
		}
		
		// 承認一覧情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 承認一覧リスト
		List<ApprovalListVO> approvalList = new ArrayList<ApprovalListVO>();
		ApprovalListVO approvalInfo = null;
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			
			for (int i = 0; i < resultList.size(); i++) {
				approvalInfo = new ApprovalListVO();
				Object[] items = (Object[]) resultList.get(i);
				
				index = 0;
				// 検索結果に設定
				//　申請番号
				approvalInfo.setApplyNo((String) items[index++]);
				// 申請区分
				approvalInfo.setApplyKbnCd((String) items[index++]);
				approvalInfo.setApplyKbnName((String) items[index++]);
				// 申請内容
				approvalInfo.setApplyDetail((String) items[index++]);
				// 状況
				approvalInfo.setStatusName((String) items[index++]);
				// 所属
				approvalInfo.setAffiliationName((String) items[index++]);
				// 氏名		
				approvalInfo.setUserName((String) items[index++]);
				
				approvalList.add(approvalInfo);
			}
		}
		
		return approvalList;
	}
}
