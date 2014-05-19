package argo.cost.monthlyReport.dao;

import java.util.List;

import argo.cost.common.model.entity.Project;
import argo.cost.monthlyReport.model.MonthlyReportEntity;

/**
 * 月報画面Dao
 * 報画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
public interface MonthlyReportDao {
	
	/**
	 * 
	 * ユーザの最後の月報提出年月を取得処理
	 * 
	 * @param userId 
	 * 				ユーザID
	 * 
	 * @return 最後の月報提出年月
	 */
	String getUserMonth(String userId);
	
	/**
	 * 
	 * ユーザの月報情報を取得処理
	 * 
	 * @param userId 
	 * 				ユーザID
	 * @param date 
	 * 				年月
	 * 
	 * @return ユーザの月報情報
	 */
	List<MonthlyReportEntity> getUserMonthReport(String userId, String date);
	
	/**
	 * 【PJ別作業時間集計】情報を取得
	 * 
	 * @param userId
	 * 			ユーザID
	 * @param date 
	 * 			日付
	 * @return
	 *        プロジェクト情報
	 */
	List<Project> getProjectList(String userId, String date);
}
