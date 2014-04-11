package argo.cost.approvalList.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.entity.ApprovalList;

/**
 * <p>
 * 承認一覧のアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class ApprovalListDaoImpl implements ApprovalListDao {

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *           状況
	 * @return 承認リスト
	 */
	@Override
	public List<ApprovalList> getApprovalList(String status) {
		
		System.out.print("承認一覧の検索を実行しました");

		// TODO:仮の値を与える
		List<ApprovalList> appList = new ArrayList<ApprovalList>();
		
		ApprovalList appInfo = new ApprovalList();
		appInfo.setApplyNo("0001");
		appInfo.setApplyKbn("月報");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("02");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("あｘｘｘｘｘ");
		appList.add(appInfo);
		
		appInfo = new ApprovalList();
		appInfo.setApplyNo("0002");
		appInfo.setApplyKbn("月報");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("02");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("うｘｘｘｘｘ");
		appList.add(appInfo);
		
		appInfo = new ApprovalList();
		appInfo.setApplyNo("0003");
		appInfo.setApplyKbn("超勤振替申請");
		appInfo.setApplyDetail("休日勤務日：2014/5/5");
		appInfo.setStatus("05");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("えｘｘｘｘｘ");
		appList.add(appInfo);

		appInfo = new ApprovalList();
		appInfo.setApplyNo("0004");
		appInfo.setApplyKbn("超勤振替申請");
		appInfo.setApplyDetail("休日勤務日：2014/5/5");
		appInfo.setStatus("05");
		appInfo.setAffiliation("ＢＳ２");
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
		if ("02".equals(status)) {

			return "提出";
		} else {
			
			return "申請";
		}
	}

}
