package argo.cost.approvalList.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.approvalList.dao.ApprovalListDao;
import argo.cost.approvalList.model.ApprovalListVo;
import argo.cost.common.dao.ComDao;
import argo.cost.common.model.entity.ApprovalListEntity;

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
	 * 承認一覧リストを取得
	 * 
	 * @param status
	 *              状況
	 * @return 承認リスト
	 */
	@Override
	public List<ApprovalListVo> getApprovalList(String status) {
		
		// 承認一覧リスト
		List<ApprovalListVo> approvalList = new ArrayList<ApprovalListVo>();
		
		// ＤＢから、承認データを取得
		List<ApprovalListEntity> approvalDate = approvalListDao.getApprovalList(status);
		
		// 承認データが一件以上の場合
		if (approvalDate != null && approvalDate.size() > 0) {
			
			for (int i = 0; i < approvalDate.size(); i++) {
				
				// ＤＢ取得の承認データ
				ApprovalListEntity approvalListEntity = approvalDate.get(i);
				
				// 画面表示の承認一覧情報
				ApprovalListVo approvalListVo = new ApprovalListVo();
				
				// 申請No.
				approvalListVo.setApplyNo(approvalListEntity.getApplyNo());
				// 申請区分コード
				approvalListVo.setApplyKbnCd(approvalListEntity.getApplyKbn());
				// 申請区分名
				approvalListVo.setApplyKbnName(approvalListDao.findApplyKbnName(approvalListEntity.getApplyKbn()));
				// 申請内容
				approvalListVo.setApplyDetail(approvalListEntity.getApplyDetail());
				// 状況
				String statusName = comDao.findStatusName(approvalListEntity.getStatus());
				approvalListVo.setStatus(statusName);
				// 所属
				approvalListVo.setAffiliation(approvalListEntity.getAffiliation());
				// 氏名
				approvalListVo.setName(approvalListEntity.getName());
				
				approvalList.add(approvalListVo);
			}
		}
		
		// 承認一覧リストを戻る
		return approvalList;
	}

}
