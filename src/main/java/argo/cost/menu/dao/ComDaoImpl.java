package argo.cost.menu.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import argo.cost.menu.model.UserInfo;
import argo.cost.menu.model.UserKengen;

@Repository
public class ComDaoImpl implements ComDao {
	
	protected EntityManager em;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserInfo findUserById(String loginId) {
		
		// TODO:DBなし
//		TypedQuery<UserInfo> query = em.createQuery("select e from UserInfo e where e.id = :loginId", UserInfo.class);
//		query.setParameter("loginId", loginId);
//		try {
//			return query.getSingleResult();
//		} catch (NoResultException e) {
//			// 該当データ無し
//			return null;
//		}
		UserInfo user = new UserInfo();
		
		// 権限：30
		if ("caowy".equals(loginId)) {
			user.setName("曹文艶");
			user.setPassword("caowy");
		// 権限：40
		} else if ("liuyj".equals(loginId)) {
			user.setName("劉亜傑");
			user.setPassword("liuyj");
		// 権限：20
		} else if ("xiongyl".equals(loginId)) {
			user.setName("熊燕玲");
			user.setPassword("xiongyl");
		// ユーザが存在しやい
		} else {
			return null;
		}
		return user;
	}


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
