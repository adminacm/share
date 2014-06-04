package argo.cost.holidayRecord.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import argo.cost.holidayRecord.model.AbsenceVO;
import argo.cost.holidayRecord.model.PayHolidayVO;
import argo.cost.holidayRecord.model.SpecialHolidayVO;


/**
 * <p>
 * 休暇管理画面ＤＡＯ
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class HolidayRecordDaoImpl implements HolidayRecordDao {

	/**
	 * 休暇欠勤区分「全休(有給休暇)」
	 */
	private static final String KYUKA_KEKIN_KBN_01 = "01";
	/**
	 * 休暇欠勤区分「半休(有給休暇)」
	 */
	private static final String KYUKA_KEKIN_KBN_02 = "02";
	/**
	 * 休暇欠勤区分「時間休(有給休暇)」
	 */
	private static final String KYUKA_KEKIN_KBN_03 = "03";
	/**
	 * 休暇欠勤区分「特別休暇」
	 */
	private static final String KYUKA_KEKIN_KBN_04 = "04";
	/**
	 * 休暇欠勤区分「欠勤」
	 */
	private static final String KYUKA_KEKIN_KBN_06 = "06";
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;

	/**
	 * 有給休暇情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 */
	@Override
	public List<PayHolidayVO> getPayHolidayList(String userId, String yearPeriod) {

		// JPQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                                  ");
		strSql.append(" 	t1.kyuka_date                      ");
		strSql.append(" 	, t1.kyuka_kekin_code              ");
		strSql.append(" 	, t1.kyuka_jikansu                 ");
		strSql.append(" 	, t2.name                          ");
		strSql.append("FROM                                    ");
		strSql.append(" 	kyuka_kekin t1                     ");
		strSql.append("LEFT JOIN                               ");
		strSql.append(" 	kyuka_kekin_kbn_master t2          ");
		strSql.append("ON                                      ");
		strSql.append(" 	t1.kyuka_kekin_code = t2.code      ");
		strSql.append("WHERE                                   ");
		strSql.append(" 	t1.user_id = ?                     ");
		strSql.append("  AND LEFT(t1.kyuka_date, 4) = ?        ");
		strSql.append("  AND t1.kyuka_kekin_code IN ( ?, ?, ?) ");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;
		query.setParameter(index++, userId);
		query.setParameter(index++, yearPeriod);

		// 「全休」をセット
		query.setParameter(index++, KYUKA_KEKIN_KBN_01);
		// 「半休」をセット
		query.setParameter(index++, KYUKA_KEKIN_KBN_02);
		// 「時間休」をセット
		query.setParameter(index++, KYUKA_KEKIN_KBN_03);
		
		// 有給休暇情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 有給休暇リスト
		List<PayHolidayVO> payHolidayList = new ArrayList<PayHolidayVO>();
		PayHolidayVO payHolidayInfo = null;
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			
			// 合計日数
			Double totleDayQuantity = 0.0;
			// 合計時間数
			Double totleTimeQuantity = 0.0;
			
			for (int i = 0; i < resultList.size(); i++) {
				
				payHolidayInfo = new PayHolidayVO();
				Object[] items = (Object[]) resultList.get(i);
				
				index = 0;
				// 検索結果に設定
				//　日付
				payHolidayInfo.setPayHolidayDate((String) items[index++]);
				// 休暇欠勤区分コード
				payHolidayInfo.setHolidayKbnCode((String) items[index++]);
				// 時間数
				BigDecimal hourQuantity = (BigDecimal) items[index++];
				payHolidayInfo.setHourQuantity(hourQuantity.toString());
				// 休暇欠勤区分名称
				payHolidayInfo.setHolidayKbnName((String) items[index++]);
				
				if (KYUKA_KEKIN_KBN_01.equals(payHolidayInfo.getHolidayKbnCode())) {

					// 日数
					payHolidayInfo.setDayQuantity("1");
					// 時間数
					payHolidayInfo.setHourQuantity(null);
				} else if (KYUKA_KEKIN_KBN_02.equals(payHolidayInfo.getHolidayKbnCode())) {

					// 日数
					payHolidayInfo.setDayQuantity("0.5");
					// 時間数
					payHolidayInfo.setHourQuantity(null);
				} 

				// 日数がnull以外の場合
				if (payHolidayInfo.getDayQuantity() != null) {

					totleDayQuantity += Double.valueOf(payHolidayInfo.getDayQuantity());
				}
				
				// 時間数がnull以外の場合
				if (payHolidayInfo.getHourQuantity() != null) {
					totleTimeQuantity += Double.valueOf(payHolidayInfo.getHourQuantity());
				}
				
				payHolidayList.add(payHolidayInfo);
			}
			
			// 合計行
			payHolidayInfo = new PayHolidayVO();
			payHolidayInfo.setPayHolidayDate("累計");
			// 日数
			if (totleDayQuantity != 0) {
				payHolidayInfo.setDayQuantity(totleDayQuantity.toString() + "日");
			}
			// 時間数
			if (totleTimeQuantity != 0) {
				payHolidayInfo.setHourQuantity(totleTimeQuantity.toString() + "時間");
			}
			payHolidayList.add(payHolidayInfo);
			
		}
		
		return payHolidayList;
	}

	/**
	 * 欠勤情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 */
	@Override
	public List<AbsenceVO> getAbsenceList(String userId, String yearPeriod) {

		// JPQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                          ");
		strSql.append(" 	t.kyuka_date               ");
		strSql.append(" 	, t.kyuka_jikansu          ");
		strSql.append("FROM                            ");
		strSql.append(" 	kyuka_kekin t              ");
		strSql.append("WHERE                           ");
		strSql.append(" 	t.user_id = ?              ");
		strSql.append("  AND LEFT(t.kyuka_date, 4) = ? ");
		strSql.append("  AND t.kyuka_kekin_code = ?    ");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;
		query.setParameter(index++, userId);
		query.setParameter(index++, yearPeriod);
		// 休暇欠勤区分に「欠勤」をセット
		query.setParameter(index++, KYUKA_KEKIN_KBN_06);
		
		// 欠勤情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 欠勤リスト
		List<AbsenceVO> absenceList = new ArrayList<AbsenceVO>();
		AbsenceVO absenceInfo = null;
		
		// 合計日数
		Double totleDayQuantity = 0.0;
		// 合計時間数
		Double totleTimeQuantity = 0.0;
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			
			for (int i = 0; i < resultList.size(); i++) {
				
				absenceInfo = new AbsenceVO();
				
				Object[] items = (Object[]) resultList.get(i);
				
				index = 0;
				// 検索結果に設定
				//　日付
				absenceInfo.setAbsentDate((String) items[index++]);
				//　時間数
				BigDecimal hourQuantity = (BigDecimal) items[index++];
				absenceInfo.setHourQuantity(hourQuantity.toString());
				
				if (absenceInfo.getHourQuantity().compareTo("7.5") >= 0) {

					//　日数
					absenceInfo.setDayQuantity("1");
					//　時間数
					absenceInfo.setHourQuantity(null);
				}

				// 日数がnull以外の場合
				if (absenceInfo.getDayQuantity() != null) {

					totleDayQuantity += Double.valueOf(absenceInfo.getDayQuantity());
				}
				
				// 時間数がnull以外の場合
				if (absenceInfo.getHourQuantity() != null) {
					totleTimeQuantity += Double.valueOf(absenceInfo.getHourQuantity());
				}
				
				absenceList.add(absenceInfo);
			}

			// 合計行
			absenceInfo = new AbsenceVO();
			absenceInfo.setAbsentDate("累計");
			// 日数
			if (totleDayQuantity != 0) {
				absenceInfo.setDayQuantity(totleDayQuantity.toString() + "日");
			}
			// 時間数
			if (totleTimeQuantity != 0) {
				absenceInfo.setHourQuantity(totleTimeQuantity.toString() + "時間");
			}
			absenceList.add(absenceInfo);
		}
				
		return absenceList;
	}
	
	/**
	 * 特別休暇情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 */
	@Override
	public List<SpecialHolidayVO> getSpecialHolidayList(String userId, String yearPeriod) {

		// JPQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                          ");
		strSql.append(" 	t.kyuka_date               ");
		strSql.append("FROM                            ");
		strSql.append(" 	kyuka_kekin t              ");
		strSql.append("WHERE                           ");
		strSql.append(" 	t.user_id = ?              ");
		strSql.append("  AND LEFT(t.kyuka_date, 4) = ? ");
		strSql.append("  AND t.kyuka_kekin_code = ?    ");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;
		query.setParameter(index++, userId);
		query.setParameter(index++, yearPeriod);
		// 休暇欠勤区分に「特別休暇」をセット
		query.setParameter(index++, KYUKA_KEKIN_KBN_04);
		
		// 特別休暇情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 特別休暇リスト
		List<SpecialHolidayVO> ｓpecialHolidayList = new ArrayList<SpecialHolidayVO>();
		
		SpecialHolidayVO specialHolidayInfo = null;
		
		// 合計日数
		Double totleDayQuantity = 0.0;
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			
			for (int i = 0; i < resultList.size(); i++) {
				specialHolidayInfo = new SpecialHolidayVO();
				
				index = 0;
				// 検索結果に設定
				//　日付
				specialHolidayInfo.setSpecialHolidayDate((String) resultList.get(i));
				//　日数
				specialHolidayInfo.setDayQuantity("1");

				// 日数がnull以外の場合
				if (specialHolidayInfo.getDayQuantity() != null) {

					totleDayQuantity += Double.valueOf(specialHolidayInfo.getDayQuantity());
				}
				
				ｓpecialHolidayList.add(specialHolidayInfo);
			}
		}

		// 合計行
		specialHolidayInfo = new SpecialHolidayVO();
		specialHolidayInfo.setSpecialHolidayDate("累計");
		// 日数
		if (totleDayQuantity != 0) {
			specialHolidayInfo.setDayQuantity(totleDayQuantity.toString() + "日");
		}
		ｓpecialHolidayList.add(specialHolidayInfo);
				
		return ｓpecialHolidayList;
	}
}
