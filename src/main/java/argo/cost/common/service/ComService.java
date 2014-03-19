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
	 * @param userName
	 *            ユーザ名
	 *
	 * @return セッション情報
	 */
	AppSession initSession(String userName);

	/**
	 * セッション情報リフレッシュ
	 *
	 * @param session
	 *            セッション情報
	 */
	void flushSession(AppSession session);

}
