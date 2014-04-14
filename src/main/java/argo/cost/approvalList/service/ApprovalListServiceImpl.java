package argo.cost.approvalList.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.approvalList.dao.ApprovalListDao;
import argo.cost.approvalList.model.ApprovalListVo;
import argo.cost.common.dao.ComDao;
import argo.cost.common.model.entity.ApprovalList;

@Service
public class ApprovalListServiceImpl implements ApprovalListService {

	/**
	 * 承認一覧DAO
	 */	
	@Autowired
	private ApprovalListDao appDao;
	
	/**
	 * 共通DAO
	 */	
	@Autowired
	private ComDao comDao;

	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *           状況
	 * @return 承認リスト
	 */
	@Override
	public List<ApprovalListVo> getApprovalList(String status) {
		
		// 承認一覧リスト
		List<ApprovalListVo> appList = new ArrayList<ApprovalListVo>();
		
		// ＤＢから、承認一覧リストを取得
		List<ApprovalList> appEList = appDao.getApprovalList(status);
		
		// TODO
		if (appEList != null && appEList.size() > 0) {
			for (int i = 0; i < appEList.size(); i++) {
				ApprovalList approval = appEList.get(i);
				ApprovalListVo appInfo = new ApprovalListVo();
				// No.
				appInfo.setApplyNo(approval.getApplyNo());
				// 申請区分コード
				appInfo.setApplyKbnCd(approval.getApplyKbn());
				// 申請区分名
				appInfo.setApplyKbnName(appDao.findApplyKbnName(approval.getApplyKbn()));
				// 申請内容
				appInfo.setApplyDetail(approval.getApplyDetail());
				// 状況
				String statusName = comDao.findStatusName(approval.getStatus());
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
