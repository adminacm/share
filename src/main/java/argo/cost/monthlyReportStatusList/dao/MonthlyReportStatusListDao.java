package argo.cost.monthlyReportStatusList.dao;

import java.util.List;

import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;
import argo.cost.monthlyReportStatusList.model.PayMagistrateCsvInfo;

/**
 * <p>
 * 月報状況一覧ＤＡＯのインターフェイス
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface MonthlyReportStatusListDao {

	/**
	 * 月報状況一覧データを取得
	 * 
	 * @param form
	 *            月報状況一覧画面情報
	 * @return 月報状況一覧データ
	 */
	List<MonthlyReportStatusListVo> getMonthlyReportStatusList(MonthlyReportStatusListForm form);

	/**
	 * 給与奉行向けCSVファイル情報を取得
	 * 
	 * @param form 
	 *            月報状況一覧画面情報
	 * @return 給与奉行向けCSVファイル情報
	 */
	List<PayMagistrateCsvInfo> getPayMagistrateCsvList(MonthlyReportStatusListForm form);

	/**
	 * 超勤管理テーブルを更新
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param applyYm
	 *               申請年月
	 */
	void updateChokinKanri(String userId, String applyYm);

}
