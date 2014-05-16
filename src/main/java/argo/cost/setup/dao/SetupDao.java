package argo.cost.setup.dao;

import java.util.List;

import argo.cost.common.model.UserInfo;
import argo.cost.common.model.entity.Shift;
import argo.cost.common.model.entity.ShiftTime;
import argo.cost.setup.model.SetupEntity;
import argo.cost.setup.model.SetupForm;

/**
 * 個人設定画面のDAO
 *
 * @author COST argo Corporation.
 */
public interface SetupDao {
	
	/**
	 * 個人設定情報を取得
	 * @param UserId
	 *           ユーザＩＤ
	 * @return
	 *        個人設定情報
	 */
	public SetupEntity getSetupInfo(String userId);

	/**
	 * 代理入力者リストを取得
	 * 
	 * @return
	 *        代理入力者リスト
	 */
	public List<UserInfo> getAgentList();

	/**
	 *ｼﾌﾄリストを取得
	 * 
	 * @return
	 *        ｼﾌﾄリスト
	 */
	public List<Shift> getShiftList();

	/**
	 * ｼﾌﾄ時刻情報を取得
	 * 
	 * @param standardShift
	 * 					標準ｼﾌﾄ
	 * @return
	 */
	public ShiftTime getshiftTime(String standardShift);

	/**
	 * 保存処理を実行
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 */
	public void doSave(SetupForm setupInfo);
}
