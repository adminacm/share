package argo.cost.attendanceOnHolidayRecordDetail.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.HolidayAtendance;
import argo.cost.common.utils.CostDateUtils;

/**
 * <p>
 * 休日出勤管理詳細画面ＤＡＯ
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class AttendanceOnHolidayRecordDetailDaoImpl implements AttendanceOnHolidayRecordDetailDao {
	
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
	 * 勤務区分が「休日振替勤務」の詳細情報を取得
	 * 
	 * @param userId
	 * 	                           社員番号
	 * @param date
	 *            日付
	 * @return 休日出勤管理詳細情報
	 * @throws ParseException 
	 */
	@Override
	public AttendanceOnHolidayRecordDetailForm getFurikaeDetail(String userId, String date) throws ParseException {

		// SQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                                         ");
		strSql.append(" 	t1.atendance_book_date  AS atendanceDate  ");
		strSql.append(" 	, t2.name               AS workKbnName    ");
		strSql.append(" 	, t1.kinmu_start_time   AS kinmuStartTime ");
		strSql.append(" 	, t1.kinmu_end_time     AS kinmuEndTime   ");
		strSql.append(" 	, t1.furikae_date       AS furikaeDate    ");
		strSql.append(" 	, t3.name               AS projectName    ");
		strSql.append(" 	, t1.commont            AS commont        ");
		strSql.append("FROM                                           ");
		strSql.append(" 	holiday_atendance_yotei t1                ");
		strSql.append("LEFT JOIN work_day_kbn_master t2               ");
		strSql.append("	   ON t1.atendance_date_kbn_cd = t2.code      ");
		strSql.append("LEFT JOIN project_master t3                    ");
		strSql.append("	   ON t1.project_code = t3.code               ");
		strSql.append("WHERE                                          ");
		strSql.append(" 	t1.user_id = ?                            ");
		strSql.append(" 	AND t1.atendance_book_date = ?            ");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;
		// 社員番号
		query.setParameter(index++, userId);
		// 日付
		query.setParameter(index++, date.replaceAll("/", ""));
		
		// 休日出勤管理詳細情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 休日出勤管理詳細画面の情報
		AttendanceOnHolidayRecordDetailForm detailForm = new AttendanceOnHolidayRecordDetailForm();
		detailForm.setUserId(userId);
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
				
			Object[] items = (Object[]) resultList.get(0);
			
			index = 0;
			// 検索結果に設定
			//　日付
			detailForm.setDate(CostDateUtils.formatDate((String) items[index++], CommonConstant.YYYY_MM_DD));
			// 勤務区分
			detailForm.setWorkKbnName((String) items[index++]);
			// 勤務開始時間
			detailForm.setWorkStartTime(CostDateUtils.formatTime((String) items[index++]));
			// 勤務終了時間
			detailForm.setWorkEndTime(CostDateUtils.formatTime((String) items[index++]));
			// 振替日
			detailForm.setExchangeDate(CostDateUtils.formatDate((String) items[index++], CommonConstant.YYYY_MM_DD));
			// プロジェクト名
			detailForm.setProjectName((String) items[index++]);
			// 業務内容
			detailForm.setWorkDetail((String) items[index++]);
		}
		
		return detailForm;
	}

	/**
	 * 勤務区分が「休日勤務」の詳細情報を取得
	 * 
	 * @param userId
	 * 	                           社員番号
	 * @param date
	 *            日付
	 * @return 休日出勤管理詳細情報
	 */
	@Override
	public AttendanceOnHolidayRecordDetailForm getWorkDetail(String userId, String date) throws ParseException {

		// SQLを作成する
		StringBuilder strSql = new StringBuilder();

		strSql.append("SELECT                                                         ");
		strSql.append(" 	t1.holiday_syukin_date          AS holidaySyukinDate      ");
		strSql.append(" 	, t3.name                       AS workKbnName            ");
		strSql.append(" 	, t2.kinmu_start_time           AS kinmuStartTime         ");
		strSql.append(" 	, t2.kinmu_end_time             AS kinmuEndTime           ");
		strSql.append(" 	, t1.daikyu_get_shimekiri_date  AS daikyuGetShimekiriDate ");
		strSql.append(" 	, t1.daikyu_date                AS daikyuDate             ");
		strSql.append(" 	, t1.firikae_shisei_date        AS firikaeShiseiDate      ");
		strSql.append(" 	, t4.name                       AS projectName            ");
		strSql.append(" 	, t2.commont                    AS commont                ");
		strSql.append("FROM                                                           ");
		strSql.append(" 	holiday_atendance t1                                      ");
		strSql.append("INNER JOIN holiday_atendance_yotei t2                          ");
		strSql.append(" 	   ON t2.atendance_book_date = t1.holiday_syukin_date     ");
		strSql.append(" 	  AND t2.user_id = t1.user_id                             ");
		strSql.append("LEFT JOIN work_day_kbn_master t3                               ");
		strSql.append(" 	  ON t2.atendance_date_kbn_cd = t3.code                   ");
		strSql.append("LEFT JOIN project_master t4                                    ");
		strSql.append(" 	  ON t2.project_code = t4.code                            ");
		strSql.append("WHERE                                                          ");
		strSql.append(" 	t1.user_id = ?                                            ");
		strSql.append(" AND t1.holiday_syukin_date = ?                                ");
		
		// クエリー取得
		Query query = this.em.createNativeQuery(strSql.toString());
		
		int index = 1;
		// 社員番号
		query.setParameter(index++, userId);
		// 日付
		query.setParameter(index++, date.replaceAll("/", ""));
		
		// 休日出勤管理詳細情報取得
		@SuppressWarnings("unchecked")
		List<Object> resultList = query.getResultList();

		// 休日出勤管理詳細画面の情報
		AttendanceOnHolidayRecordDetailForm detailForm = new AttendanceOnHolidayRecordDetailForm();
		detailForm.setUserId(userId);
		
		// 出力対象一覧情報あり
		if (resultList.size() > 0) {
				
			Object[] items = (Object[]) resultList.get(0);
			
			index = 0;
			// 検索結果に設定
			//　日付
			detailForm.setDate(CostDateUtils.formatDate((String) items[index++], CommonConstant.YYYY_MM_DD));
			// 勤務区分
			detailForm.setWorkKbnName((String) items[index++]);
			// 勤務開始時間
			detailForm.setWorkStartTime(CostDateUtils.formatTime((String) items[index++]));
			// 勤務終了時間
			detailForm.setWorkEndTime(CostDateUtils.formatTime((String) items[index++]));
			// 代休期限
			detailForm.setTurnedHolidayEndDate(CostDateUtils.formatDate((String) items[index++], CommonConstant.YYYY_MM_DD));
			// 代休日
			String turnedHolidayDate = (String) items[index++];
			if (CostDateUtils.isValidDate(turnedHolidayDate, CommonConstant.YYYYMMDD)) {
				detailForm.setTurnedHolidayDate(CostDateUtils.formatDate(turnedHolidayDate, CommonConstant.YYYY_MM_DD));
			}
			// 超勤振替申請日
			String overWorkTurnedReqDate = (String) items[index++];
			if (overWorkTurnedReqDate != null) {
				detailForm.setOverWorkTurnedReqDate(CostDateUtils.formatDate(overWorkTurnedReqDate, CommonConstant.YYYY_MM_DD));
			}
			// プロジェクト名
			detailForm.setProjectName((String) items[index++]);
			// 業務内容
			detailForm.setWorkDetail((String) items[index++]);
		}
		
		return detailForm;
	}
	
	/**
	 * 休日出勤管理詳細の超勤振替申請情報を更新
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 *            
	 */
	@Override
	public void updateAttendanceOnHolidayRecordDetail(AttendanceOnHolidayRecordDetailForm form) {

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", form.getUserId());
		// 日付
		condition.addConditionEqual("holidaySyukinDate", form.getDate().replaceAll("/", ""));
		// 休日出勤管理詳細情報を取得
		HolidayAtendance detailInfo = baseDao.findSingleResult(condition, HolidayAtendance.class);
		
		// システム日付
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String systemDate = format.format(new Date());
		
		// 代休日
		detailInfo.setDaikyuDate("超勤振替");
		// 振替申請日がシステム日付になる
		detailInfo.setFirikaeShiseiDate(systemDate);
		
		baseDao.update(detailInfo);
	}
	
}
