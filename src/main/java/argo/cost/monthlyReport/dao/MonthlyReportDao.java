package argo.cost.monthlyReport.dao;

import java.util.List;

import argo.cost.monthlyReport.model.MonthlyReportEntity;

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
}
