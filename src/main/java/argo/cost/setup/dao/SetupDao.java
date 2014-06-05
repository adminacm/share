package argo.cost.setup.dao;

import java.util.List;

import argo.cost.common.entity.ShiftInfo;
import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
import argo.cost.common.model.UserVO;
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
	public Users getSetupInfo(String userId);

	/**
	 * 代理入力者リストを取得
	 * 
	 * @return
	 *        代理入力者リスト
	 */
	public List<UserVO> getAgentList();

	/**
	 *ｼﾌﾄリストを取得
	 * 
	 * @return
	 *        ｼﾌﾄリスト
	 */
	public List<ShiftInfo> getShiftList();

	/**
	 * ｼﾌﾄ時刻情報を取得
	 * 
	 * @param standardShift
	 * 					標準ｼﾌﾄ
	 * @return
	 */
	public ShiftJikoku getshiftTime(String standardShift);

	/**
	 * 保存処理を実行
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 */
	public void doSave(SetupForm setupInfo);
}
