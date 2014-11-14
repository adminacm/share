package argo.cost.setup.service;

import java.text.ParseException;

import argo.cost.common.exception.BusinessException;
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
	 * @throws ParseException 
	 */
	void getSetupInfo(SetupForm form) throws ParseException;

	/**
	 * 個人設定変更情報を取得
	 * @param UserId
	 *           ユーザＩＤ
	 */
	void getSetupEditInfo(SetupForm setupInfo) throws ParseException;

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
	 * @throws Exception 
	 *            
	 */
	void doSaveCheck(SetupForm setupInfo) throws BusinessException;

	/**
	 * 保存処理を実行
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 */
	void doSave(SetupForm setupInfo) throws BusinessException;
}
