package argo.cost.monthlyReportStatusList.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.entity.ApprovalList;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;

/**
 * <p>
 * 承認一覧のアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class MonthlyReportStatusListDaoImpl implements MonthlyReportStatusListDao {

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *           状況
	 * @return 承認リスト
	 */
	@Override
	public List<ApprovalList> getMonthlyReportStatusList(MonthlyReportStatusListForm form) {

		// TODO:仮の値を与える
		List<ApprovalList> appList = new ArrayList<ApprovalList>();
		
		ApprovalList appInfo = new ApprovalList();
		appInfo.setApplyKbn("月報");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("02");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setId("aaa");
		appInfo.setName("あｘｘｘｘｘ");
		appList.add(appInfo);
		
		appInfo = new ApprovalList();
		appInfo.setApplyKbn("月報");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("02");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setId("uuu");
		appInfo.setName("うｘｘｘｘｘ");
		appList.add(appInfo);
		
		return appList;
	}
	
	/**
	 * 状況表示名を取得
	 * 
	 * @param status
	 *           状況
	 * @return 状況表示名
	 */
	@Override
	public String getStatusName(String status) {
		// TODO 仮の値を与える
		return "提出";
	}

}
