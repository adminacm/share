package argo.cost.common.dao;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.UserInfo;
import argo.cost.common.model.UserKengen;

/**
 * <p>
 * 共通部品に関するデータへのアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class ComDaoImpl implements ComDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserInfo findUserById(String userId) {

		// TODO
		UserInfo user = new UserInfo();
		
		// 権限：30
		if ("caowy".equals(userId)) {
			user.setUserName("曹文艶");
			user.setPassword("caowy");
			user.setUserId("caowy");
			user.setOpeKbn("30");
		// 権限：40
		} else if ("liuyj".equals(userId)) {
			user.setUserName("劉亜傑");
			user.setPassword("liuyj");
			user.setUserId("liuyj");
			user.setOpeKbn("40");
		// 権限：20
		} else if ("xiongyl".equals(userId)) {
			user.setUserName("熊燕玲");
			user.setPassword("xiongyl");
			user.setUserId("xiongyl");
			user.setOpeKbn("40");
		// ユーザが存在しやい
		} else {
			return null;
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String findSysSetVal(String setKey) {

		// TODO
		return "key";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserKengen findUserKengenById(String userId) {
		
		UserKengen userkg = new UserKengen();
		// 権限：30
		if ("caowy".equals(userId)) {
			userkg.setKengenCd("30");
		// 権限：40
		} else if ("liuyj".equals(userId)) {
			userkg.setKengenCd("40");
		// 権限：20
		} else if ("xiongyl".equals(userId)) {
			userkg.setKengenCd("20");
		// ユーザが存在しやい
		} else {
			return null;
		}
		return userkg;
	}
}
