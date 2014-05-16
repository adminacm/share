package argo.cost.approvalList.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.entity.ApprovalListEntity;

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
	public List<ApprovalListEntity> getApprovalList(String status) {
		
		System.out.print("承認一覧の検索を実行しました");

		// TODO:仮の値を与える
		List<ApprovalListEntity> appList = new ArrayList<ApprovalListEntity>();
		
		ApprovalListEntity appInfo = new ApprovalListEntity();
		appInfo.setApplyNo("user01120140300");
		appInfo.setApplyKbn("1");
		appInfo.setApplyDetail("2014年4月分");
		appInfo.setStatus("02");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("あｘｘｘｘｘ");
		appList.add(appInfo);
		
		appInfo = new ApprovalListEntity();
		appInfo.setApplyNo("user01120140400");
		appInfo.setApplyKbn("1");
		appInfo.setApplyDetail("2014年5月分");
		appInfo.setStatus("02");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("うｘｘｘｘｘ");
		appList.add(appInfo);
		
		appInfo = new ApprovalListEntity();
		appInfo.setApplyNo("user01220140505");
		appInfo.setApplyKbn("2");
		appInfo.setApplyDetail("休日勤務日：2014/5/5");
		appInfo.setStatus("05");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("えｘｘｘｘｘ");
		appList.add(appInfo);

		appInfo = new ApprovalListEntity();
		appInfo.setApplyNo("user01220140405");
		appInfo.setApplyKbn("2");
		appInfo.setApplyDetail("休日勤務日：2014/4/5");
		appInfo.setStatus("05");
		appInfo.setAffiliation("ＢＳ２");
		appInfo.setName("うｘｘｘｘｘ");
		appList.add(appInfo);
		
		return appList;
	}

	/**
	 * 申請区分名を取得
	 * 
	 * @param applyKbnCd
	 *                申請区分コード
	 * @return
	 *        申請区分名
	 */
	@Override
	public String findApplyKbnName(String applyKbnCd) {
		// TODO 自動生成されたメソッド・スタブ
		String name = "";
		
		if ("1".equals(applyKbnCd)) {
			
			name = "月報";
		} else if ("2".equals(applyKbnCd)) {
			
			name = "超勤振替申請";
		}
		return name;
	}

}
