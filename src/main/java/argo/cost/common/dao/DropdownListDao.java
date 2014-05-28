package argo.cost.common.dao;

import java.util.List;

import argo.cost.common.entity.Project;
import argo.cost.common.entity.Status;
import argo.cost.common.entity.Users;

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
	List<Status> getStatusList();

	/**
	 * ユーザ情報取得
	 * 
	 * @param userId
	 *            ユーザＩＤ
	 * @return ユーザ情報リスト
	 */
	List<Users> getUserList(String userId);

	/**
	 *プロジェクト情報取得
	 * 
	 * @param userId
	 *            ユーザＩＤ
	 * @return プロジェクト名情報
	 */
	List<Project> getProjectList(String userId);

}
