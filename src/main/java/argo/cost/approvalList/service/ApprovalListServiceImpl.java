package argo.cost.approvalList.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.approvalList.dao.ApprovalListDao;
import argo.cost.approvalList.model.ApprovalListVO;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.StatusMaster;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 承認一覧サービス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Service
public class ApprovalListServiceImpl implements ApprovalListService {
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 承認一覧情報DAO
	 */
	@Autowired
	ApprovalListDao approvalListDao;

	/**
	 * 状況プルダウンリスト取得
	 * 
	 * @return 状況プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getStatusList() {
		
		// 状況情報を取得
		List<StatusMaster> statusList = baseDao.findAll(StatusMaster.class);

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		
		// ドロップダウン項目
		ListItemVO item = new ListItemVO();

		// データを設定する
		item.setValue("");
		item.setName("すべて");
		resultList.add(item);

		// ドロップダウンリスト設定
		for (StatusMaster status : statusList) {
			
			item = new ListItemVO();

			// 区分値 
			item.setValue(status.getCode());
			// 区分名称
			item.setName(status.getName());

			// リストに追加
			resultList.add(item);
		}

		// 状況ドロップダウンリストを返却する。
		return resultList;
	}
	
	/**
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *              状況
	 * @return 承認リスト
	 */
	@Override
	public List<ApprovalListVO> getApprovalList(String jyokyoStatus,String userId) {
		
		// 承認一覧リストを取得
		List<ApprovalListVO> approvalList = new ArrayList<ApprovalListVO>();
		ApprovalListVO approvalInfo = new ApprovalListVO();
		
		// 承認管理情報
		List<ApprovalManage> approvalManageList = new ArrayList<ApprovalManage>();

		// 状況がnull以外の場合
		if (!jyokyoStatus.isEmpty()) { 

			// 承認管理情報取得
			approvalManageList = approvalListDao.searchApprovalManageList(jyokyoStatus, userId);
		} else {
			// 承認管理情報取得
			approvalManageList = baseDao.findAll(ApprovalManage.class);
		}
		
		for (ApprovalManage approvalManageInfo : approvalManageList) {
			
			approvalInfo = new ApprovalListVO();
			//　申請番号
			approvalInfo.setApplyNo(approvalManageInfo.getApplyNo());
			// 申請区分名
			approvalInfo.setApplyKbnCd(approvalManageInfo.getApplyKbnMaster().getCode());
			approvalInfo.setApplyKbnName((approvalManageInfo.getApplyKbnMaster().getName()));
			// 申請内容
			approvalInfo.setApplyDetail((approvalManageInfo.getApplyDetail()));
			// 状況名
			approvalInfo.setStatusName(approvalManageInfo.getStatusMaster().getName());
			// 所属名
			approvalInfo.setAffiliationName(approvalManageInfo.getUser().getAffiliationMaster().getName());
			// 氏名		
			approvalInfo.setUserName(approvalManageInfo.getUser().getUserName());
			// 申請日
			approvalInfo.setApplyYmd(approvalManageInfo.getApplyYmd());
			// 承認日
			approvalInfo.setApproveYmd(approvalManageInfo.getApprovedYmd());
			// 処理年月
			approvalInfo.setSyoriYm(approvalManageInfo.getSyoriYm());
			
			approvalList.add(approvalInfo);
		}
		
		// 承認一覧リストを戻る
		return approvalList;
	}
}
