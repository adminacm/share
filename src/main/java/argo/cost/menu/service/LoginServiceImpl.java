package argo.cost.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.menu.dao.ComDao;
import argo.cost.menu.dao.ComDaoImpl;
import argo.cost.menu.model.UserInfo;
import argo.cost.menu.model.UserKengen;


@Service
public class LoginServiceImpl implements LoginService {

	/**
	 * 共通DAO
	 */
	@Autowired
	private ComDao comDao = new ComDaoImpl();
	
	
	@Override
	public UserInfo getUserInfo(UserInfo user) {
		
		// ユーザ情報を取得する。
		UserInfo userInfo = comDao.findUserById(user.getUserId());
		
		return userInfo;
	}


	@Override
	public UserKengen getUserKengen(String loginId) {
		
		// ユーザ権限情報を取得する。
		UserKengen userkg = comDao.findUserKengenById(loginId);
		
		return userkg;
	}

}


