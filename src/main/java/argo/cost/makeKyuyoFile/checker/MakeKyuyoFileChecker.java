package argo.cost.makeKyuyoFile.checker;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
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
		// ユーザーID
		selectApprovalManageInfoCondition.addConditionEqual("users.id", makeKyuyoFileForm.getUserId());
		// 申請区分（"1" 月報）
		selectApprovalManageInfoCondition.addConditionEqual("applyKbnMaster.code", CommonConstant.APPLY_KBN_GETUHOU);
		ApprovalManage approvalManageRes = baseDao.findSingleResult(selectApprovalManageInfoCondition, ApprovalManage.class);
		int intOption = JOptionPane.DEFAULT_OPTION;
		if (approvalManageRes != null && !StringUtils.equals(approvalManageRes.getStatusMaster().getCode(),CommonConstant.STATUS_SYORIZUMI)) {
			
			Object[] options = { "はい", "いいえ" }; 
			intOption = JOptionPane.showOptionDialog(null, MessageConstants.COSE_E_1104, "Warning", 
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		}
		return intOption;
		
	}
}

