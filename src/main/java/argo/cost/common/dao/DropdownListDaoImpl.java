package argo.cost.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.entity.Users;

/**
 * <p>
 * 共通部品に関するドロップダウンリストのアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
@Repository
public class DropdownListDaoImpl implements DropdownListDao {

	/**
	 * ユーザ情報取得
	 * 
	 * @param userId
	 *            ユーザＩＤ
	 * @return ユーザ情報リスト
	 */
	@Override
	public List<Users> getUserList(String userId) {
		
		// ユーザ情報リスト
		List<Users> userInfoList = new ArrayList<Users>();
		// ユーザ情報
		Users user = new Users();

		// TODO 仮の値を与える
		if (userId == null) {
			
			user = new Users();
			user.setId("li");
			userInfoList.add(user);
			
			user = new Users();
			user.setId("taro");
			userInfoList.add(user);
		} else {
			
			user = new Users();
			user.setId("li");
			userInfoList.add(user);
			
			user = new Users();
			user.setId("taro");
			userInfoList.add(user);
			
			user = new Users();
			user.setId("hayashi");
			userInfoList.add(user);

			user = new Users();
			user.setId("tanaka");
			userInfoList.add(user);
		}
		
		return userInfoList;
	}
}
