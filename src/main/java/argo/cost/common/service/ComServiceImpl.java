package argo.cost.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.ComDao;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.entity.Users;

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
	public AppSession initSession(String userName) {

		AppSession session = new AppSession();

		this.setupSession(session, userName);

		return session;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flushSession(AppSession session) {

		this.setupSession(session, session.getUserInfo().getName());
	}

	/**
	 * セッション情報セットアップ
	 *
	 * @param userName
	 *            ユーザ名
	 * @return セッション情報
	 */
	private void setupSession(AppSession session, String userName) {

		// ユーザ情報を取得します。
		Users user = comDao.findByName(userName);
		session.setUserInfo(user);

		if (session.getUserInfo() == null) {

			// 権限なしの異常を表示します。
//			throw new InvalidAuthException();
		}

	}


}
