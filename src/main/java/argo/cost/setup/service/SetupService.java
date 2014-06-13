package argo.cost.setup.service;

import argo.cost.setup.model.SetupForm;

/**
 * 個人設定画面サービスのインタフェース
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
	void getSetupInfo(SetupForm form);

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
	 *        Booleanチェック結果(true:エラーがない； false:エラーがある)
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
