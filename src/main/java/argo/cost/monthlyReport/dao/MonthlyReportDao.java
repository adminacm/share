package argo.cost.monthlyReport.dao;

import java.util.List;

import argo.cost.monthlyReport.model.MonthlyReportEntity;
import argo.cost.monthlyReport.model.ProjectVo;

/**
 * <p>
 * 月報の氏名リストを取得。
 * </p>
 *
 * @author 
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
	List<ProjectVo> getProjectList(String userId, String date);
}
