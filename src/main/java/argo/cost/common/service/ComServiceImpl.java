package argo.cost.common.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.dao.ComDao;
import argo.cost.common.dao.DropdownListDao;
import argo.cost.common.entity.Project;
import argo.cost.common.entity.Status;
import argo.cost.common.entity.Users;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 *  共通サービス
 * </p>
 *
 * @author COST argo Corporation.
 *
 */
@Service
public class ComServiceImpl implements ComService {

	// ############
	// ### 定数 ###
	// ############
	/**
	 * 検索条件：名
	 */
	private static final String LOGIN_MAIL = "loginMailAddress";
	
	/**
	 * 共通DAO
	 */
	@Autowired
	private ComDao comDao;
	
	/**
	 * 単一テーブル操作DAO
	 */
	@Autowired
	private BaseDao baseDao;

	/**
	 * 共通ドロップダウンリストDAO
	 */
	@Autowired
	private DropdownListDao dropdownListDao;

	/**
	 * セッション情報初期化
	 *
	 * @param loginMail
	 *                ユーザログインメールアドレス
	 *
	 * @return セッション情報
	 */
	@Override
	public AppSession initSession(String loginMail) {

		AppSession session = new AppSession();

		this.setupSession(session, loginMail);

		return session;
	}

	/**
	 * セッション情報リフレッシュ
	 *
	 * @param session
	 *               セッション情報
	 */
	@Override
	public void flushSession(AppSession session) {

		this.setupSession(session, session.getUserInfo().getLoginName());
	}

	/**
	 * 状況プルダウンリスト取得
	 * 
	 * @return 状況プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getStatusList() {

		// 状況プルダウンリスト
		List<Status> statusList = dropdownListDao.getStatusList();

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = null;

		// ドロップダウンリスト設定
		for (Status status : statusList) {
			item = new ListItemVO();

			// データを設定する
			// 区分値 
			item.setValue(status.getStatusCode());
			// 区分名称
			item.setName(status.getStatusName());

			// リストに追加
			resultList.add(item);
		}

		// 状況ドロップダウンリストを返却する。
		return resultList;
	}

	/**
	 * 氏名プルダウンリスト取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @return 氏名プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getUserNameList(String userId) {

		// 氏名プルダウンリスト
		List<Users> userList = dropdownListDao.getUserList(userId);

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = null;

		// ドロップダウンリスト設定
		for (Users status : userList) {
			item = new ListItemVO();

			// データを設定する
			// 区分値 
			item.setValue(status.getId());
			// 区分名称
			item.setName(status.getLoginName());

			// リストに追加
			resultList.add(item);
		}

		// 氏名ドロップダウンリストを返却する。
		return resultList;
	}

	/**
	 * 年度プルダウンリスト取得
	 * 
	 * @param year
	 *            当年度
	 * @return プルダウンリスト
	 * 
	 * @throws ParseException 
	 */
	@Override
	public List<ListItemVO> getYearList(Date date) throws ParseException {

		// 年度プルダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = null;

		// ドロップダウンリスト設定
		for (int i = 0; i < 3; i++) {
			item = new ListItemVO();

			// データを設定する
			// 区分値 
			item.setValue(String.valueOf(getYear(date, i)));
			// 区分名称
			item.setName(formatJapanYear(getYear(date, i)));

			// リストに追加
			resultList.add(item);
		}

		// 年度ドロップダウンリストを返却する。
		return resultList;
	}

	/**
	 * プロジェクト名プルダウンリスト取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @return プロジェクト名プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getProjectNameList(String userId) {

		// プロジェクトプルダウンリスト
		List<Project> projectList = dropdownListDao.getProjectList(userId);

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = null;

		// ドロップダウンリスト設定
		for (Project project : projectList) {
			item = new ListItemVO();

			// データを設定する
			// 区分コード
			item.setValue(project.getProjCode());
			// 区分名称
			item.setName(project.getProjName());

			// リストに追加
			resultList.add(item);
		}

		// プロジェクト名ドロップダウンリストを返却する。
		return resultList;
	}

	/**
	 * 月報の提出状態を取得
	 * 
	 * @param userId
	 *              ユーザID
	 * @param date 
	 *            日付
	 * @return 月報の提出状態
	 */
	@Override
	public String getMonthReportStatus(String userId, String date) {
		
		String status = "";
		// 月報の提出状態を取得
		status = comDao.getMonthReportStatus(userId, date);
		
		// 月報の提出状態を戻り
		return status;
	}

	///////////////////////////////////
	///////////////////////////////////

	/**
	 * 西暦年を取得
	 * 
	 * @param date
	 * 		　          日付
	 * @param cnt
	 * 		　       年度差
	 * @return  西暦年
	 */
	private int getYear(Date date, int cnt) {

		// 日付の取得
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -cnt); 
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		// 現在年度の判定
		if (month >= 1 && month <= 3) {
			year = year - 1;
		}

		return year;
	}
	
	/**
	 * 和暦年取得
	 * 
	 * @param year
	 * 　　　　　　　　	   西暦年
	 * @return　 和暦年
	 * 
	 * @throws ParseException 
	 */
	private String formatJapanYear(Integer year) throws ParseException {
		
		SimpleDateFormat parseTime=new SimpleDateFormat("yyyy-MM-dd");
		Date date= parseTime.parse(String.valueOf(year) + "-01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		Locale locale = new Locale("ja", "JP", "JP");
		DateFormat format = new SimpleDateFormat("GGGGyy年度", locale);
		
		return format.format(cal.getTime());
	}
	

	/**
	 * セッション情報セットアップ
	 *
	 * @param loginMail
	 *                ユーザログインメールアドレス
	 * @return セッション情報
	 */
	private void setupSession(AppSession session, String loginMail) {

        // 共通の検索条件クラス
        BaseCondition condition = new BaseCondition();
        condition.addConditionEqual(LOGIN_MAIL, loginMail);
        // ユーザ名より、ユーザ情報を取得する。
        Users users = baseDao.findSingleResult(condition, Users.class);
		// ユーザ情報を取得します。
		Users user = comDao.findByName(loginMail);
		session.setUserInfo(user);

		if (session.getUserInfo() == null) {

			// 権限なしの異常を表示します。
			// throw new InvalidAuthException();
		}
	}
}
