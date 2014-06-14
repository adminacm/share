package argo.cost.attendanceInput.dao;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import argo.cost.attendanceInput.model.AttendanceInputForm;

/**
 * 勤怠入力DAOImpl
 * 勤怠入力に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Repository
public class AttendanceInputDaoImpl implements AttendanceInputDao {
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;	

	/**
	 * 就業データを取得
	 * 
	 * @param form 
	 * 				画面情報
	 * 
	 * @return 更新結果　０：更新失敗　１：更新成功
	 */
	@Override
	public Integer updateAttdendanceInfo(AttendanceInputForm form) {
		// TODO 自動生成されたメソッド・スタブ

		// 更新成功の場合
		if ("user01".equals(form.getUserId())) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * シフトテーブルから作業時間数を取得する
	 * 
	 * @param shiftCode 
	 * 				シフトコード
	 * @param sTime 
	 * 				作業開始時刻
	 * @param eTime 
	 * 				作業終了時刻
	 * @param flag 
	 * 				計算フラグ(0:Between；1:以上;2:以下)
	 * 
	 * @return 作業時間数
	 */
	@Override
	public Double countWorkTime(String shiftCode, String sTime, String eTime, int flag) {

		// JPQLを作成する
		StringBuilder q = new StringBuilder();

		q.append("SELECT ");
		q.append("		count(");
		q.append(" 		kinmu_flg)");
		q.append("	FROM");
		q.append("		shift_info");
		q.append("	WHERE");
		q.append("	kinmu_flg = '1'");
		q.append("	AND");
		q.append("	shift_code = ?");
		q.append("	AND");
		// 遅刻の場合
		if (flag == 0) {
			q.append("	time_zone_code >= ?");
			q.append("	AND");
			q.append("	time_zone_code <= ?");
		// 以上
		} else if (flag == 1) {
			q.append("	time_zone_code >= ?");
			q.append("	AND");
			q.append("	time_zone_code < ?");
		// 以下
		} else if (flag == 2) {
			q.append("	time_zone_code > ?");
			q.append("	AND");
			q.append("	time_zone_code <= ?");
		}
		
		// クエリー取得
		Query query = this.em.createNativeQuery(q.toString());
		
		int index = 1;

		query.setParameter(index++, shiftCode); // シフトコード
		query.setParameter(index++, sTime);     // 計算開始時刻
		query.setParameter(index++, eTime);     // 計算開始時刻
		
		// 出力対象一覧情報取得
		Double result = ((BigInteger)query.getSingleResult()).doubleValue() * 0.5;
		return result;
	}
	
	/**
	 * 本年度の休暇時間数を取得する
	 * 
	 * @param userId 
	 * 				社員番号
	 * @param yyyymmdd 
	 * 				対象日
	 * @param flag 
	 * 				計算フラグ（０：全ての有給休暇数、1：時間休のみ）
	 * 
	 * @return 対象日までの本年度の休暇時間数
	 */
	@Override
	public Double getSumKyukaTime(String userId, String yyyymmdd, int flag) {

		// JPQLを作成する
		StringBuilder q = new StringBuilder();

		q.append("SELECT ");
		q.append("		sum(");
		q.append(" 		kyuka_jikansu)");
		q.append("	FROM");
		q.append("		kyuka_kekin");
		q.append("	WHERE");
		if (flag == 0) {
			q.append("	(kyuka_kekin_code = '01'");
			q.append("	OR");
			q.append("	kyuka_kekin_code = '02'");
			q.append("	OR");
			q.append("	kyuka_kekin_code = '03')");
			q.append("	AND");
		} else {
			q.append("	kyuka_kekin_code = '03'");
			q.append("	AND");
		}
		q.append("	user_id = ?");
		q.append("	AND");
		q.append("	kyuka_date >= ?");
		q.append("	AND");
		q.append("	kyuka_date < ?");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(q.toString());
		
		int index = 1;
		String yearStart = yyyymmdd.substring(0, 4).concat("0401");

		query.setParameter(index++, userId);        // 社員番号
		query.setParameter(index++, yearStart);     // 計算開始時刻
		query.setParameter(index++, yyyymmdd);      // 計算開始時刻
		
		Double result = 0.0;
		// 出力対象一覧情報取得
		Object res = query.getSingleResult();
		if (res != null) {
			result = ((BigDecimal)res).doubleValue();
		}
		
		return result;
	}
}
