package argo.cost.makeKyuyoFile.checker;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.MessageConstants;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileForm;

/**
 * 給与システム用ファイル出力画面入力チェッククラス
 *
 * @author COST argo Corporation.
 */
public class MakeKyuyoFileChecker {

	/**
	 * 共通DAO
	 */
	@Autowired
	static BaseDao baseDao;
	
	public static BaseDao getBaseDao() {
		return baseDao;
	}

	public static void setBaseDao(BaseDao baseDao) {
		MakeKyuyoFileChecker.baseDao = baseDao;
	}

	/**
	 * 処理年月対応した月報申請ステータスのチェック
	 * 
	 * @param form
	 *            画面情報オブジェクト
	 */
	public static int chkStartTimeInput(MakeKyuyoFileForm makeKyuyoFileForm) {
		
		BaseCondition selectApprovalManageInfoCondition = new BaseCondition();
		// 処理年月
		selectApprovalManageInfoCondition.addConditionEqual("syoriYm", makeKyuyoFileForm.getDealYearMonth());
		// 申請区分（"1" 月報）
		selectApprovalManageInfoCondition.addConditionEqual("applyKbnMaster.code", CommonConstant.APPLY_KBN_GETUHOU);
		// 月報状況（状況：承認「03」）
		selectApprovalManageInfoCondition.addConditionEqual("statusMaster.code", CommonConstant.STATUS_SYOUNIN);
		List<ApprovalManage> approvalManageResList = baseDao.findResultList(selectApprovalManageInfoCondition, ApprovalManage.class);
		int intOption = JOptionPane.DEFAULT_OPTION;
		
		if (approvalManageResList.size() >= 0) {
			Object[] options = { "はい", "いいえ" }; 
			intOption = JOptionPane.showOptionDialog(null, MessageConstants.COSE_E_1104, "Warning", 
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		}
		return intOption;
		
	}
}

