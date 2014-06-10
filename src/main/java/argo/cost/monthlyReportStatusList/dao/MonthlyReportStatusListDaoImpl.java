package argo.cost.monthlyReportStatusList.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;
import argo.cost.monthlyReportStatusList.model.PayMagistrateCsvInfo;

/**
 * <p>
 * 月報状況一覧のアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class MonthlyReportStatusListDaoImpl implements MonthlyReportStatusListDao {
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;
	
	// CSV項目名
	/**
	 * 超過勤務時間数（平日_割増）
	 */
	private static final String CSV_KOUMOKU_KN09 = "KN09";
	/**
	 * 超過勤務時間数（休日）
	 */
	private static final String CSV_KOUMOKU_KN10 = "KN10";
	/**
	 * 超過勤務時間数（深夜）
	 */
	private static final String CSV_KOUMOKU_KN11 = "KN11";
	/**
	 * 超過勤務時間数（休日出勤振替分）
	 */
	private static final String CSV_KOUMOKU_KN12 = "KN12";
	/**
	 * 欠勤時間数
	 */
	private static final String CSV_KOUMOKU_KN08 = "KN08";
	/**
	 * 超過勤務時間数（平日_通常）
	 */
	private static final String CSV_KOUMOKU_KN13 = "KN13";

	/**
	 * 月報状況一覧データを取得
	 * 
	 * @param form
	 *            月報状況一覧画面情報
	 * @return 月報状況一覧データ
	 */
	@Override
	public List<MonthlyReportStatusListVo> getMonthlyReportStatusList(MonthlyReportStatusListForm form) {

		// JPQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                                              ");
		strSql.append(" 	t1.apply_no          AS applyNo                ");
		strSql.append(" 	, t1.app_ym          AS appYm                  ");
		strSql.append(" 	, t4.affiliationName AS affiliationName        ");
		strSql.append(" 	, t1.user_id         AS userId                 ");
		strSql.append(" 	, t4.userName        AS userName               ");
		strSql.append(" 	, t2.code            AS applyKbnCode           ");
		strSql.append(" 	, t2.name            AS applyKbnName           ");
		strSql.append(" 	, t1.apply_detail    AS applyDetail            ");
		strSql.append(" 	, t3.name            AS statusName             ");
		strSql.append("FROM                                                ");
		strSql.append(" 	approval_manage t1                             ");
		strSql.append("LEFT JOIN apply_kbn_master t2                       ");
		strSql.append(" 	ON t1.apply_kbn_code = t2.code                 ");
		strSql.append("LEFT JOIN status_master  t3                         ");
		strSql.append(" 	ON t1.apply_status_code = t3.code              ");
		strSql.append("LEFT JOIN                                           ");
		strSql.append(" 		(SELECT t5.id          AS userId           ");
		strSql.append(" 				, t5.user_name AS userName         ");
		strSql.append(" 				, t6.code      AS affiliationCode  ");
		strSql.append(" 				, t6.name      AS affiliationName  ");
		strSql.append(" 	 	 FROM users t5                             ");
		strSql.append(" 		 	  , affiliation_master t6              ");
		strSql.append(" 	 	 WHERE t5.shozoku_id = t6.code) t4         ");
		strSql.append(" 	ON t1.user_id = t4.userId                      ");
		strSql.append("WHERE                                               ");
		strSql.append(" 	t1.app_ym = ?                                  ");
		strSql.append(" AND t1.apply_status_code = ?                       ");
		strSql.append("ORDER BY                                            ");
		strSql.append(" 	applyNo                                        ");
		// 所属がnull以外の場合
		if (!form.getAffiliation().isEmpty()) { 
			      
			strSql.append(" AND t4.affiliationCode = ?                     ");
		}
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;

		// 年月
		query.setParameter(index++, form.getYear() + form.getMonth());
		// 状況
		query.setParameter(index++, form.getStatus());
		// 所属がnull以外の場合
		if (!form.getAffiliation().isEmpty()) { 
			query.setParameter(index++, form.getAffiliation());
		}
		
		// 月報状況一覧情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 月報状況一覧リスト
		List<MonthlyReportStatusListVo> monthlyReportStatusList = new ArrayList<MonthlyReportStatusListVo>();
		MonthlyReportStatusListVo monthlyReportStatusInfo = null;
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			
			for (int i = 0; i < resultList.size(); i++) {
				monthlyReportStatusInfo = new MonthlyReportStatusListVo();
				Object[] items = (Object[]) resultList.get(i);
				
				index = 0;
				// 検索結果に設定
				//　申請番号
				monthlyReportStatusInfo.setApplyNo((String) items[index++]);
				// 申請年月
				monthlyReportStatusInfo.setApplyYm((String) items[index++]);
				// 所属
				monthlyReportStatusInfo.setAffiliationName((String) items[index++]);
				// ユーザID	
				monthlyReportStatusInfo.setUserId((String) items[index++]);
				// 氏名	
				monthlyReportStatusInfo.setUserName((String) items[index++]);
				// 申請区分
				monthlyReportStatusInfo.setApplyKbnCd((String) items[index++]);
				monthlyReportStatusInfo.setApplyKbnName((String) items[index++]);
				// 申請内容
				monthlyReportStatusInfo.setApplyDetail((String) items[index++]);
				// 状況
				monthlyReportStatusInfo.setStatusName((String) items[index++]);
				
				monthlyReportStatusList.add(monthlyReportStatusInfo);
			}
		}
		
		return monthlyReportStatusList;
	}
	
	/**
	 * 給与奉行向けCSVファイル情報を取得
	 * 
	 * @param form 
	 *            月報状況一覧画面情報
	 * @return 給与奉行向けCSVファイル情報
	 */
	@Override
	public List<PayMagistrateCsvInfo> getPayMagistrateCsvList(MonthlyReportStatusListForm form) {

		//給与奉行向けCSVファイル情報
		List<PayMagistrateCsvInfo> payMagistrateCsvList = new ArrayList<PayMagistrateCsvInfo>();
		// CSV詳細情報
		PayMagistrateCsvInfo csvInfo = null;
		
		// 出力対象一覧情報あり
		if (form.getMonthlyReportStatusList().size() > 0) {
			
			for (MonthlyReportStatusListVo listInfo : form.getMonthlyReportStatusList()) {
				
				// CSV詳細情報
				csvInfo = new PayMagistrateCsvInfo();
				
				// CSV出力検索条件に設定
				// ユーザID
				String userId = listInfo.getUserId();
				// 申請年月
				String applyYm = listInfo.getApplyYm();
				
				// CSV出力条件によって、出力ＣＳＶ情報を取得
				csvInfo = getCsvInfo(userId, applyYm);
				
				if (csvInfo != null) {
					
					payMagistrateCsvList.add(csvInfo);
				}
			}
		}
		
		return payMagistrateCsvList;
	}

	/**
	 * 出力ＣＳＶ情報を取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param applyYm
	 *               申請年月
	 * @return　出力ＣＳＶ情報
	 */
	private PayMagistrateCsvInfo getCsvInfo(String userId, String applyYm) {
		
		// 出力ＣＳＶ情報
		PayMagistrateCsvInfo csvInfo = null;

		// JPQLを作成する
		StringBuilder strSql = new StringBuilder();
		
		strSql.append("SELECT                              ");
		strSql.append(" 	himoku_kbn_code                ");
		strSql.append(" 	, SUM(hours)    AS totleHours  ");
		strSql.append("FROM                                ");
		strSql.append(" 	chokin_kanri                   ");
		strSql.append("WHERE                               ");
		strSql.append(" 	user_id = ?                    ");
		strSql.append("  AND LEFT(chokin_date, 6) = ?      ");
		strSql.append("GROUP BY                            ");
		strSql.append("     himoku_kbn_code                ");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;

		// ユーザID
		query.setParameter(index++, userId);
		// 年月
		query.setParameter(index++, applyYm);
		
		// 出力CSV情報リストを取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			
			csvInfo = new PayMagistrateCsvInfo();
			// 社員番号
			csvInfo.setUserId(userId);
			
			for (int i = 0; i < resultList.size(); i++) {
				
				Object[] items = (Object[]) resultList.get(i);
				
				index = 0;
				// 費目区分
				String himokuKbn = (String) items[index++];
				
				// 時間数
				BigDecimal totleHours = (BigDecimal) items[index++];
				
				// 検索結果に設定
				if (CSV_KOUMOKU_KN09.equals(himokuKbn)) {
					
					// 超過勤務時間数_割増
					csvInfo.setOverWeekdayHours(totleHours.toString());
				} else if (CSV_KOUMOKU_KN10.equals(himokuKbn)) {

					// 超勤休日時間数
					csvInfo.setOverHolidayHours(totleHours.toString());
				} else if (CSV_KOUMOKU_KN11.equals(himokuKbn)) {

					// 深夜勤務時間数		
					csvInfo.setOverNightHours(totleHours.toString());
				} else if (CSV_KOUMOKU_KN12.equals(himokuKbn)) {
					
					// 超勤平日時間数_休日出勤振替分
					csvInfo.setOverHolidayChangeWorkHours(totleHours.toString());
				}  else if (CSV_KOUMOKU_KN08.equals(himokuKbn)) {
					
					// 休暇欠勤時間数
					csvInfo.setAbsenceHours(totleHours.toString());
				} else if (CSV_KOUMOKU_KN13.equals(himokuKbn)) {

					// 超勤平日時間数_通常
					csvInfo.setOverWeekdayNomalHours(totleHours.toString());
				}
			}
		}
		
		return csvInfo;
	}
}
