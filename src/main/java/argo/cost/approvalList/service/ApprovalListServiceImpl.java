package argo.cost.approvalList.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.approvalList.dao.ApprovalListDao;
import argo.cost.approvalList.model.ApprovalListVO;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.dao.ComDao;
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
	 * 承認一覧DAO
	 */	
	@Autowired
	private ApprovalListDao approvalListDao;
	
	/**
	 * 共通DAO
	 */	
	@Autowired
	private ComDao comDao;
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

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
	public List<ApprovalListVO> getApprovalList(String status) {
		
		// 承認一覧リストを取得
		List<ApprovalListVO> approvalList = approvalListDao.getApprovalList(status);
		
		// 承認一覧リストを戻る
		return approvalList;
	}
}
