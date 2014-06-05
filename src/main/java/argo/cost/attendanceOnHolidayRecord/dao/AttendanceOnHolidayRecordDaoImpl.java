package argo.cost.attendanceOnHolidayRecord.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import argo.cost.attendanceOnHolidayRecord.model.HolidayExchangeWorkVO;
import argo.cost.attendanceOnHolidayRecord.model.HolidayWorkVO;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.utils.CostDateUtils;

/**
 * <p>
 * 休日出勤管理ＤＡＯ
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class AttendanceOnHolidayRecordDaoImpl implements AttendanceOnHolidayRecordDao {
	
	/**
	 * 勤務日区分「休日振替勤務」
	 */
	private static final String ATENDANCE_DATE_KBN_CD_03 = "03";
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;
	
	/**
	 * 休日振替勤務情報を取得
	 * 
	 * @param yearPeriod
	 *                  年度
	 * @param userName
	 *                氏名
	 * @return
	 *        休日振替勤務情報
	 */
	@Override
	public List<HolidayExchangeWorkVO> getHolidayExchangeWorkList(
			String yearPeriod, String userName) throws ParseException {

		// JPQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                                   ");
		strSql.append(" 	t.atendance_book_date               ");
		strSql.append(" 	,t.furikae_date                     ");
		strSql.append("FROM                                     ");
		strSql.append(" 	holiday_atendance_yotei t           ");
		strSql.append("WHERE                                    ");
		strSql.append(" 	t.atendance_date_kbn_cd = ?         ");
		strSql.append("  AND LEFT(t.atendance_book_date, 4) = ? ");
		strSql.append("  AND t.user_id = ?                      ");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;
		// 勤務日区分「休日振替勤務」
		query.setParameter(index++, ATENDANCE_DATE_KBN_CD_03);
		// 画面選択の年度
		query.setParameter(index++, yearPeriod);
		// 画面選択の氏名
		query.setParameter(index++, userName);
		
		// 休日振替勤務情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 休日振替勤務リスト
		List<HolidayExchangeWorkVO> holidayExchangeWorkList = new ArrayList<HolidayExchangeWorkVO>();
		
		HolidayExchangeWorkVO holidayExchangeWork = null;
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			
			for (int i = 0; i < resultList.size(); i++) {
				
				holidayExchangeWork = new HolidayExchangeWorkVO();
				
				Object[] items = (Object[]) resultList.get(i);
				
				index = 0;
				// 検索結果に設定
				//　休日振替勤務日付
				String date = (String) items[index++];
				holidayExchangeWork.setHolidayTurnedWorkingDate(CostDateUtils.formatDate(date, CommonConstant.YYYY_MM_DD));
				// 振替休日
				String turnedHolidayDate = (String) items[index++];
				holidayExchangeWork.setWorkingDayTurnedHolidayDate(CostDateUtils.formatDate(turnedHolidayDate, CommonConstant.YYYY_MM_DD));
				
				holidayExchangeWorkList.add(holidayExchangeWork);
			}
		}
		
		return holidayExchangeWorkList;
	}

	/**
	 * 休日勤務情報を取得
	 * 
	 * @param yearPeriod
	 *                  年度
	 * @param userName
	 *                氏名
	 * @return
	 *        休日勤務情報
	 * @throws ParseException 
	 */
	@Override
	public List<HolidayWorkVO> getHolidayWorkList(String yearPeriod,
			String userName) throws ParseException {

		// JPQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                                   ");
		strSql.append(" 	t.holiday_syukin_date               ");
		strSql.append(" 	,t.daikyu_get_shimekiri_date        ");
		strSql.append(" 	,t.daikyu_date                      ");
		strSql.append(" 	,t.firikae_shisei_date              ");
		strSql.append("FROM                                     ");
		strSql.append(" 	holiday_atendance t                 ");
		strSql.append("WHERE                                    ");
		strSql.append(" 	LEFT(t.holiday_syukin_date, 4) = ?  ");
		strSql.append(" AND t.user_id = ?                       ");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;
		// 画面選択の年度
		query.setParameter(index++, yearPeriod);
		// 画面選択の氏名
		query.setParameter(index++, userName);
		
		// 休日勤務情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 休日勤務リスト
		List<HolidayWorkVO> holidayWorkList = new ArrayList<HolidayWorkVO>();
		
		HolidayWorkVO holidayWork = null;
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
			
			for (int i = 0; i < resultList.size(); i++) {
				
				holidayWork = new HolidayWorkVO();
				
				Object[] items = (Object[]) resultList.get(i);
				
				index = 0;
				// 検索結果に設定
				//　休日勤務日付
				String date = (String) items[index++];
				holidayWork.setHolidayOverWorkDate(CostDateUtils.formatDate(date, CommonConstant.YYYY_MM_DD));
				// 代休期限
				String turnedHolidayEndDate = (String) items[index++];
				holidayWork.setTurnedHolidayEndDate(CostDateUtils.formatDate(turnedHolidayEndDate, CommonConstant.YYYY_MM_DD));
				// 代休日
				String turnedHolidayDate = (String) items[index++];
				if (CostDateUtils.isValidDate(turnedHolidayDate, "yyyyMMdd")) {
					holidayWork.setTurnedHolidayDate(CostDateUtils.formatDate(turnedHolidayDate, CommonConstant.YYYY_MM_DD));
				} else {
					holidayWork.setTurnedHolidayDate(turnedHolidayDate);
				}
				// 超勤振替申請日
				String overWorkTurnedReqDate = (String) items[index++];
				holidayWork.setOverWorkTurnedReqDate(CostDateUtils.formatDate(overWorkTurnedReqDate, CommonConstant.YYYY_MM_DD));
				
				holidayWorkList.add(holidayWork);
			}
		}
		
		return holidayWorkList;
	}
}
