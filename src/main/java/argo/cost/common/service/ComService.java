package argo.cost.common.service;

import argo.cost.common.model.AppSession;

/**
 * <p>
 * 共通部品に関するサービスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface ComService {

	/**
	 * セッション情報初期化
	 *
	 * @param userInfo
	 *            ユーザID
	 * @param pwd
	 *            パスワード
	 *
	 * @return セッション情報
	 */
	AppSession initSession(String userId, String pwd);

	/**
	 * セッション情報リフレッシュ
	 *
	 * @param session
	 *            セッション情報
	 */
	void flushSession(AppSession session);

	/**
	 * 権限チェック
	 *
	 * @param session
	 *            セッション
	 * @param msgDispFlg
	 *            メッセージ表示フラグ
	 */
	void checkAuthority(AppSession session, boolean msgDispFlg);

//	/** TODO
//	 * グループキーにコードマスタ情報一覧を取得します。
//	 *
//	 * @param domain
//	 *            検索条件
//	 * @return コードマスタ情報一覧
//	 */
//	List<MsCd> listMsCdByGrpKey(String grpKey);
//	/**
//	 * 部門リスト選択内容を取得します。
//	 *
//	 * @param bmnList
//	 *            部門リスト
//	 * @param cmpCd
//	 *            カンパニーコード
//	 * @throws Exception
//	 *             異常
//	 */
//	 String getBmnSel(List<SoshikiItem> bmnList, String cmpCd) throws Exception;
//
//	/**
//	 * 部リスト選択内容を取得します。
//	 *
//	 * @param buList
//	 *            部リスト
//	 * @param cmpCd
//	 *            カンパニーコード
//	 * @param bmnCd
//	 *            カンパニーコード
//	 * @throws Exception
//	 *             異常
//	 */
//	 String getBuSel(List<SoshikiItem> buList, String cmpCd, String bmnCd) throws Exception;

	/**
	 * テンプパスワードを取得します。
	 *
	 * @return テンプパスワード
	 */
	 String findTempPw();

}
