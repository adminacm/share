package argo.cost.common.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.ComDao;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.UserInfo;

/**
 * {@inheritDoc}
 */
@Service
public class ComServiceImpl implements ComService {

	/**
	 * 共通DAO
	 */
	@Autowired
	private ComDao comDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AppSession initSession(String userId, String pwd) {

		AppSession session = new AppSession();

		this.setupSession(session, userId, pwd);

		return session;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flushSession(AppSession session) {

		this.setupSession(session, session.getUserInfo().getUserId(), session.getUserInfo().getPassword());
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String findTempPw() {

		// TODO
		return comDao.findSysSetVal("key");
	}

	/**
	 * セッション情報セットアップ
	 *
	 * @param userId
	 *            ユーザID
	 * @param ksnki
	 *            決算期
	 * @return セッション情報
	 */
	private void setupSession(AppSession session, String userId, String pwd) {

		// ユーザ情報を取得します。
		UserInfo user = comDao.findUserById(userId);
		session.setUserInfo(user);

		if (session.getUserInfo() == null) {

			// 権限なしの異常を表示します。
//			throw new InvalidAuthException();
		} else {
			// ユーザ情報のチェック
			if (true) {
				
			} else {
				// 登録の異常を表示します。
//				throw new InvalidAuthException();
			}
		}

		// 権限持てるフラグを設定します。
		session.setAuthFlg(!StringUtils.equals(session.getUserInfo().getOpeKbn(), "01"));

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkAuthority(AppSession session, boolean msgDispFlg) {

		// セッション情報をリフレシュー
		this.flushSession(session);

		// 初期の場合、更新不可にします。
		session.setUpdFlg(false);

		// 権限なしチェック
		if (!session.isAuthFlg()) {
//			throw new InvalidAuthException();
		}
//
//		if (session.getSoshikiList() == null || session.getSoshikiList().isEmpty()) {
//
//			// 組織なしの場合、権限エラー画面に遷移します。
//			throw new InvalidAuthException();
//		}
//
//		if (ItochuStringUtils.equals(session.getUrl(), UrlConstant.URL_TOP)) {
//			return;
//		}

		// アップロード無しの画面URL配列
		Set<String> noUploadUrls = new HashSet<String>();
		// お知らせ
		noUploadUrls.add("01");
		// ユーザ権限管理
		noUploadUrls.add("02");

		// 参照者チェック
		if (StringUtils.equals(session.getUserInfo().getOpeKbn(), "XX")) {

			if (msgDispFlg) {
//				throw new ApplicationRecoverableException(MessageConstant.W_M0110);
			} else {
				return;
			}
		}

		// 更新可にします。
		session.setUpdFlg(true);
	}


}
