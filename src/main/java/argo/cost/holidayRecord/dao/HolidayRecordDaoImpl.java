package argo.cost.holidayRecord.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.KyukaKekin;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.holidayRecord.model.PayHolidayVO;


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
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 有給休暇情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 * @return 有給休暇情報
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
		query.setParameter(index++, CommonConstant.KK_KBN_ZENKYU);
		// 「半休」をセット
		query.setParameter(index++, CommonConstant.KK_KBN_HANKYU);
		// 「時間休」をセット
		query.setParameter(index++, CommonConstant.KK_KBN_JIKANKYU);
		
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
				String payHolidayDate = (String) items[index++];
				try {
					payHolidayInfo.setPayHolidayDate(CostDateUtils.formatDate(payHolidayDate, CommonConstant.YYYY_MM_DD));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// 休暇欠勤区分コード
				payHolidayInfo.setHolidayKbnCode((String) items[index++]);
				// 時間数
				BigDecimal hourQuantity = (BigDecimal) items[index++];
				payHolidayInfo.setHourQuantity(hourQuantity.toString());
				// 休暇欠勤区分名称
				payHolidayInfo.setHolidayKbnName((String) items[index++]);
				
				if (CommonConstant.KK_KBN_ZENKYU.equals(payHolidayInfo.getHolidayKbnCode())) {

					// 日数
					payHolidayInfo.setDayQuantity("1.0");
					// 時間数
					payHolidayInfo.setHourQuantity(null);
				} else if (CommonConstant.KK_KBN_HANKYU.equals(payHolidayInfo.getHolidayKbnCode())) {

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
	 * @return 欠勤情報
	 */
	@Override
	public List<KyukaKekin> getAbsenceList(String userId, String yearPeriod) {

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("kyukaDate", yearPeriod + "%");
		// 休暇欠勤区分に「欠勤」をセット
		condition.addConditionEqual("kyukaKekinKbnMaster.code", CommonConstant.KK_KBN_KEKIN);
		// 欠勤情報取得
		List<KyukaKekin> kyukaKekinList = baseDao.findResultList(condition, KyukaKekin.class);
				
		return kyukaKekinList;
	}

	/**
	 * 特別休暇情報取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param yearPeriod
	 *                  年度
	 * @return 特別休暇情報
	 */
	@Override
	public List<KyukaKekin> getSpecialHolidayList(String userId, String yearPeriod) {

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("kyukaDate", yearPeriod + "%");
		// 休暇欠勤区分に「特別休暇」をセット
		condition.addConditionEqual("kyukaKekinKbnMaster.code", CommonConstant.KK_KBN_TOKUBETUKYU);
		// 特別休暇情報取得
		List<KyukaKekin> kyukaKekinList = baseDao.findResultList(condition, KyukaKekin.class);
				
		return kyukaKekinList;
	}
}
