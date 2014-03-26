package argo.cost.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.UserInfo;
import argo.cost.common.model.entity.Status;
import argo.cost.common.model.entity.Users;

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
	 * 状況情報取得
	 * 
	 * @return 状況情報リスト
	 */
	@Override
	public List<Status> getStatusList() {
		
		// 状況情報リスト
		List<Status> statusList = new ArrayList<Status>();

		// TODO 仮の値を与える
		Status statusInfo = new Status();
		statusInfo.setStatusCode("00");
		statusInfo.setStatusName("すべて");
		statusList.add(statusInfo);
		
		statusInfo = new Status();
		statusInfo.setStatusCode("01");
		statusInfo.setStatusName("作成中");
		statusList.add(statusInfo);
		
		statusInfo = new Status();
		statusInfo.setStatusCode("02");
		statusInfo.setStatusName("提出");
		statusList.add(statusInfo);
		
		statusInfo = new Status();
		statusInfo.setStatusCode("03");
		statusInfo.setStatusName("承認");
		statusList.add(statusInfo);
		
		statusInfo = new Status();
		statusInfo.setStatusCode("04");
		statusInfo.setStatusName("処理済");
		statusList.add(statusInfo);
		
		statusInfo = new Status();
		statusInfo.setStatusCode("05");
		statusInfo.setStatusName("申請");
		statusList.add(statusInfo);
		
		return statusList;
	}

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
			user.setName("李");
			userInfoList.add(user);
			
			user = new Users();
			user.setId("taro");
			user.setName("太郎");
			userInfoList.add(user);
		} else {
			
			user = new Users();
			user.setId("li");
			user.setName("李");
			userInfoList.add(user);
			
			user = new Users();
			user.setId("taro");
			user.setName("太郎");
			userInfoList.add(user);
			
			user = new Users();
			user.setId("hayashi");
			user.setName("林");
			userInfoList.add(user);

			user = new Users();
			user.setId("tanaka");
			user.setName("田中");
			userInfoList.add(user);
		}
		
		return userInfoList;
	}

}
