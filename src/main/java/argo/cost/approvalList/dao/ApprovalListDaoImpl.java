package argo.cost.approvalList.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import argo.cost.common.entity.ApprovalManage;

@Repository
public class ApprovalListDaoImpl implements ApprovalListDao {
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalManage> searchApprovalManageList(String jyokyoStatus,String userId) {
		
		// JPQLを作成し、認管理情報取得する
		StringBuilder q = new StringBuilder();
		// 同じ部課のユーザの状況によって、検索する
		q.append("SELECT ");
		q.append("		* ");
		q.append("	FROM");
		q.append("		approval_manage");
		q.append(" 		where");
		q.append("		user_id in");
		q.append("		(select id");
		q.append("		from users");
		q.append("		where shozoku_id =");
		q.append("		(Select  shozoku_id ");
		q.append("		from users where id = ? ))");
		q.append("		and apply_status_code = ?");
		
		Query query = this.em.createNativeQuery(q.toString());
		int index = 1;
		query.setParameter(index++, userId);         // ユーザーＩＤ
		query.setParameter(index++, jyokyoStatus);   // 状況コード
		List<Object> result = query.getResultList();
		
		
		List<ApprovalManage> approvalManageList = new ArrayList<ApprovalManage>();
		ApprovalManage approvalManageInfo = null;
		
		// 出力対象一覧情報あり
		if (approvalManageList.size() > 0) {
			approvalManageList = new ArrayList<ApprovalManage>();
			for (int i = 0; i < approvalManageList.size(); i++) {
				approvalManageInfo = new ApprovalManage();
				Object[] items = (Object[]) result.get(i);
				index = 0;
				// 検索結果に設定
				approvalManageInfo.setApplyNo((String) items[index++]);
				approvalManageInfo.setApplyYmd((String) items[index++]);
				approvalManageInfo.setApplyDetail((String) items[index++]);
				approvalManageInfo.setCreatedDate((Timestamp) items[index++]);
				approvalManageInfo.setCreatedUserId((String) items[index++]);
				approvalManageInfo.setSyoriYm((String) items[index++]);
				approvalManageInfo.setApprovedYmd((String) items[index++]);
				approvalManageInfo.setUpdateDate((Timestamp) items[index++]);
				approvalManageInfo.setUpdatedUserId((String) items[index++]);
				
				approvalManageList.add(approvalManageInfo);
			}
		}
		return approvalManageList;
	}

}
