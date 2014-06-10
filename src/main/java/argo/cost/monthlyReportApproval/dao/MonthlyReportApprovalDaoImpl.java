package argo.cost.monthlyReportApproval.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.ProjWorkTimeManage;

@Repository
public class MonthlyReportApprovalDaoImpl implements MonthlyReportApprovalDao {
	
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;
	
	/**
	 * エンティティ管理クラス
	 */
	@PersistenceContext
	protected EntityManager em;
	

	/**
	 * 処理状況値を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況値
	 */
	@Override
	public String getStatus(String applyNo) {
		
		// ＤＢから、申請番号による、処理状况値を取得
		BaseCondition approvalStatusSelectCondition = new BaseCondition();
		approvalStatusSelectCondition.addConditionEqual("approvalManage", baseDao.findById(applyNo, ApprovalManage.class));
		
		ApprovalManage approvalStatus = baseDao.findSingleResult(approvalStatusSelectCondition, ApprovalManage.class);
		
		// 処理状況
		String strStatus = "";
		if(approvalStatus.getStatusMaster() != null) {
			
			strStatus = approvalStatus.getStatusMaster().getName();
		}
	
		return strStatus;
	}
	
	/**
	 * 月報承認データを取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        月報承認データ一覧リスト
	*/
	@Override
	public List<KintaiInfo> searchMonthReportApprovalList(String applyNo) {
		
		// TODO 自動生成されたメソッド・スタブ
		BaseCondition approvalStatusSelectCondition = new BaseCondition();
        approvalStatusSelectCondition.addConditionEqual("kintaiInfo", baseDao.findById(applyNo, KintaiInfo.class));
		
		ArrayList<KintaiInfo> monthlyShoninInfoList = (ArrayList<KintaiInfo>) baseDao.findResultList(approvalStatusSelectCondition, KintaiInfo.class);
		
		return monthlyShoninInfoList;
	}

	/**
	 * プロジェクト情報を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        プロジェクト情報リスト
	 */
	@Override
	public List<ProjWorkTimeManage> searchProjectList(String applyNo) {
		
		// 申請番号によって、勤怠情報の勤務IDを取得する
		BaseCondition approvalStatusSelectCondition = new BaseCondition();
		approvalStatusSelectCondition.addConditionEqual("approvalManage", baseDao.findById(applyNo, KintaiInfo.class));
		
		ApprovalManage approvalManageInfo = baseDao.findSingleResult(approvalStatusSelectCondition, ApprovalManage.class);
		
		
		// 取得した勤務IDによって、関連のプロジェクト作業時間を取得する
		BaseCondition projectWorkTImeSelectCondition = new BaseCondition();
		projectWorkTImeSelectCondition.addConditionEqual("approvalManage", baseDao.findById(approvalManageInfo.getApplyNo().toString(), KintaiInfo.class));
		
		ArrayList<ProjWorkTimeManage> projWorkTimeManageInfoList = (ArrayList<ProjWorkTimeManage>) baseDao.findResultList(projectWorkTImeSelectCondition, ProjWorkTimeManage.class);
		
		return projWorkTimeManageInfoList;
	}

	/**
	 * 申請状況更新
	 * 
	 * @param applyNo
	 *               申請番号
	 * @param proStatus
	 *                 申請状況
	 * @return
	 *        更新フラグ
	 */
	@Override
	public String updateProStatus(String applyNo, String proStatus) {
		
		// 更新フラグ
		String strUpdateFlg = "1";
		
		BaseCondition approvalStatusSelectCondition = new BaseCondition();
		
		approvalStatusSelectCondition.addConditionEqual("approvalManage.id", applyNo);
		
		ApprovalManage approvalManageInfo = new ApprovalManage();
		try {
	    // 申請番号によって、承認管理テーブルから承認情報を取得する
		approvalManageInfo = baseDao.findSingleResult(approvalStatusSelectCondition, ApprovalManage.class);
        } catch (Exception ex) {
			
			System.out.print("11111111111");
		}
		// 申請状況を設定する
		approvalManageInfo.getApplyKbnMaster().setCode(proStatus);
		try {
			baseDao.insert(approvalManageInfo);
			
		} catch (Exception e) {
			
			strUpdateFlg = "0";
			System.out.print("11111111111");
		}
		
		return strUpdateFlg;
	}
}
