package argo.cost.setup.service;

import argo.cost.setup.model.SetupForm;

/**
 * <p>
 * 個人設定に関するサービスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface SetupService {

	/**
	 * 個人設定情報を取得
	 * @param UserId
	 *           ユーザＩＤ
	 * @return
	 *        個人設定情報
	 */
	SetupForm getSetupInfo(String UserId);

	/**
	 * 個人設定変更情報を取得
	 * @param UserId
	 *           ユーザＩＤ
	 */
	void getSetupEditInfo(SetupForm setupInfo);

	/**
	 * 標準ｼﾌﾄ変更
	 *
	 * @param setupInfo
	 *            個人設定情報
	 */  
	void changeShift(SetupForm setupInfo);

	/**
	 * 保存と入力チェック処理
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 * @return
	 *        チェック結果
	 */
	Boolean doSaveCheck(SetupForm setupInfo);

	/**
	 * 保存処理を実行
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 */
	void doSave(SetupForm setupInfo);
}
