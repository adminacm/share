package argo.cost.attendanceOnHoliday.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 勤怠入力DAOImpl
 * 勤怠入力に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Repository
@Transactional
public class AtendanceOnHolidayDaoImpl implements AtendanceOnHolidayDao {
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;	

	/**
	 * 就業データを取得
	 * 
	 * @param userId 
	 * 				社員番号
	 * @param date 
	 * 				振替日
	 * 
	 * @return 勤務データの処理状況
	 */
	@Override
	public String getAttendanceInfoStatus(String userId, String date) {

		// JPQLを作成する
		StringBuilder q = new StringBuilder();

		q.append("SELECT ");
		q.append("		a.apply_status_code");
		q.append("	FROM");
		q.append("		kintai_info k");
		q.append("		LEFT JOIN ");
		q.append("		approval_manage a");
		q.append("		ON ");
		q.append("		k.apply_no = a.apply_no");
		q.append("	WHERE");
		q.append("	k.apply_no is not null");
		q.append("	AND");
		q.append("	k.user_id = ?");
		q.append("	AND");
		q.append("	k.atendance_date = ?");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(q.toString());
		
		int index = 1;

		query.setParameter(index++, userId);   // 社員番号
		query.setParameter(index++, date);     // 振替日
		// 出力対象一覧情報取得
		@SuppressWarnings("unchecked")
		List<Object> result = query.getResultList();

		if (result == null || result.size() == 0) {
			return StringUtils.EMPTY;
		} else {
			return (String) result.get(0);
		}
		
	}
}
