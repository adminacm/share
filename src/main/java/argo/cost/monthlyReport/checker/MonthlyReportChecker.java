package argo.cost.monthlyReport.checker;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.monthlyReport.model.MonthlyReportForm;

/**
 * 休日勤務入力画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class MonthlyReportChecker {

	/**
	 * 勤務開始時刻。
	 */
	private final static String  KINMU_START_TIME = "勤務開始時刻";
	/**
	 * 勤務終了時刻
	 */
	private final static String KINMU_END_TIME = "勤務終了時刻";
	
	/**
	 * 共通DAO
	 */
	@Autowired
	static BaseDao baseDao;
	
	public static BaseDao getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(BaseDao baseDao) {
		MonthlyReportChecker.baseDao = baseDao;
	}

	/**
	 * 勤務開始時刻チェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static void chkKintaiInfoInput(MonthlyReportForm monthlyReportForm) {
		
		List<MonthlyReportDispVO> monthlyReportDispVOList = monthlyReportForm.getmRList();
		
		for (int i = 0; i < monthlyReportDispVOList.size();i ++) {
			
			// 勤務べき日付についての勤務開始時刻
			if("1".equals(monthlyReportDispVOList.get(i).getWorkKbn())) {
				
				if(StringUtils.isEmpty(monthlyReportDispVOList.get(i).getWorkSTime()) || StringUtils.isEmpty(monthlyReportDispVOList.get(i).getWorkETime())){
					
					monthlyReportForm.putConfirmMsg(MessageConstants.COSE_E_1103, new String[] {monthlyReportDispVOList.get(i).getChoSTime(), KINMU_START_TIME});	
					monthlyReportForm.putConfirmMsg(MessageConstants.COSE_E_1103, new String[] {monthlyReportDispVOList.get(i).getChoETime(), KINMU_END_TIME});	
				}
			}
		}
		
		
	}

	
}
