package argo.cost.approvalList.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.approvalList.dao.ApprovalListDao;
import argo.cost.approvalList.model.ApprovalListInfo;
import argo.cost.common.model.entity.ApprovalList;

/**
 * {@inheritDoc}
 */
@Service
public class ApprovalListServiceImpl implements ApprovalListService {

	/**
	 * 承認一覧DAO
	 */
	@Autowired
	private ApprovalListDao appDao;

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *           状況
	 * @return 承認リスト
	 */
	@Override
	public List<ApprovalListInfo> getApprovalList(String status) {
		
		// 承認一覧リスト
		List<ApprovalListInfo> appList = new ArrayList<ApprovalListInfo>();
		
		// ＤＢから、承認一覧リストを取得
		List<ApprovalList> appEList = appDao.getApprovalList(status);
		
		// TODO
		if (appEList != null && appEList.size() > 0) {
			for (int i = 0; i < appEList.size(); i++) {
				ApprovalList approval = appEList.get(i);
				ApprovalListInfo appInfo = new ApprovalListInfo();
				// No.
				appInfo.setNo(String.valueOf(i+1));
				// 申請区分
				appInfo.setApplyKbn(approval.getApplyKbn());
				// 申請内容
				appInfo.setApplyDetail(approval.getApplyDetail());
				// 状況
				String statusName = appDao.getStatusName(approval.getStatus());
				appInfo.setStatus(statusName);
				// 所属
				appInfo.setAffiliation(approval.getAffiliation());
				// 氏名
				appInfo.setName(approval.getName());
				
				appList.add(appInfo);
			}
		}
		
		// 承認一覧リスト
		return appList;
	}

}
