package argo.cost.approvalList.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.approvalList.model.ApprovalListVO;

/**
 * <p>
 * 承認一覧ＤＡＯ
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class ApprovalListDaoImpl implements ApprovalListDao {

	/**
	 * ＤＢから、承認データを取得
	 * 
	 * @param status
	 *              状況
	 * @return 承認データ
	 */
	@Override
	public List<ApprovalListVO> getApprovalList(String status) {
		
		// TODO:仮の値を与える
		List<ApprovalListVO> appList = new ArrayList<ApprovalListVO>();
		return appList;
	}
}
