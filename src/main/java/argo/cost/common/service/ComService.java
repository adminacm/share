package argo.cost.common.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import argo.cost.common.model.AppSession;
import argo.cost.common.model.ListItem;

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
	 *            ユーザ名
	 *
	 * @return セッション情報
	 */
	AppSession initSession(String userName);

	/**
	 * セッション情報リフレッシュ
	 *
	 * @param session
	 *            セッション情報
	 */
	void flushSession(AppSession session);
	
	/**
	 * 状況プルダウンリスト取得
	 * 
	 * @return 状況プルダウンリスト
	 */
	List<ListItem> getStatusList();
	
	/**
	 * 氏名プルダウンリスト取得
	 * 
	 * @param userId
	 *            ユーザＩＤ
	 * @return 氏名プルダウンリスト
	 */
	List<ListItem> getUserNameList(String userId);
	
	/**
	 * 年度プルダウンリスト取得
	 * 
	 * @param year
	 *           当年度
	 * @return
	 *           プルダウンリスト
	 * @throws ParseException 
	 */
	List<ListItem> getYearList(Date date) throws ParseException;
	
	/**
	 * プロジェクト名プルダウンリスト取得
	 * 
	 * @param userId
	 *            ユーザＩＤ
	 * @param date
	 * 		           　日付
	 * @return
	 *            プロジェクト名プルダウンリスト
	 */
	List<ListItem> getProjectNameList(String userId, Date date);
	
	/**
	 * 
	 * 月報の提出状態を取得
	 * 
	 * @param userId ユーザID
	 * @param date 日付
	 * @return 月報の提出状態
	 */
	String getMonthStatus(String userId, String date);
}
