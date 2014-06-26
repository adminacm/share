package argo.cost.common.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import argo.cost.common.entity.AffiliationMaster;
import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.ProjectBasic;
import argo.cost.common.entity.Users;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.ListItemVO;

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
	 *                ユーザ名
	 *
	 * @return セッション情報
	 */
	AppSession initSession(String userName);

	/**
	 * セッション情報リフレッシュ
	 *
	 * @param session
	 *               セッション情報
	 */
	void flushSession(AppSession session);
	
	/**
	 * 年度プルダウンリスト取得
	 * 
	 * @param year
	 *            当年度
	 * @return  プルダウンリスト
	 * @throws ParseException 
	 */
	List<ListItemVO> getYearList(Date date) throws ParseException;
	
	/**
	 * プロジェクト情報を取得
	 * 
	 * @return  プロジェクトリスト
	 */
	List<ProjectBasic> getProjectNameList();
	
	/**
	 * プロジェクトの作業情報を取得
	 * 
	 * @return  作業内容リスト
	 */
	List<ProjWorkMaster> getWorkItemList();
	
	/**
	 * 所属プルダウンリスト取得
	 * 
	 * @return 所属リスト
	 */
	List<AffiliationMaster> getAffiliationList();
	
	/**
	 * 氏名プルダウンリスト取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @return 氏名プルダウンリスト
	 */
	List<Users> getUserNameList(String userId);

	/**
	 * 氏名取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @return 氏名
	 */
	String getUserName(String userId);
}
