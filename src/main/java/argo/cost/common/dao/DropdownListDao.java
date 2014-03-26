package argo.cost.common.dao;

import java.util.List;

import argo.cost.common.model.entity.Status;
import argo.cost.common.model.entity.Users;

/**
 * <p>
 * 共通部品に関するドロップダウンリストのアクセスクラスを提供します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface DropdownListDao {
	
	/**
	 * 状況情報取得
	 * 
	 * @return 状況情報リスト
	 */
	public List<Status> getStatusList();

	/**
	 * ユーザ情報取得
	 * 
	 * @param userId
	 *            ユーザＩＤ
	 * @return ユーザ情報リスト
	 */
	public List<Users> getUserList(String userId);

}
