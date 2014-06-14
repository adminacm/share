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
import argo.cost.common.entity.ProjWorkMaster;
import argo.cost.common.entity.ProjectMaster;
import argo.cost.common.entity.Users;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.ListItemVO;
import argo.cost.common.model.UserVO;

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

		this.setupSession(session, session.getUserInfo().getLoginMailAdress());
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
	
	/**
	 * プロジェクト情報を取得
	 * 
	 * @return  プロジェクトリスト
	 */
	@Override
	public List<ProjectMaster> getProjectNameList() {
		
		// 全てのプロジェクト情報を取得
		List<ProjectMaster> resultList = baseDao.findAll(ProjectMaster.class);
		
		return resultList;
	}
	
	/**
	 * プロジェクトの作業情報を取得
	 * 
	 * @return  作業内容リスト
	 */
	@Override
	public List<ProjWorkMaster> getWorkItemList() {
		
		// 作業内容リストを取得
		List<ProjWorkMaster> resultList = baseDao.findAll(ProjWorkMaster.class);
		
		return resultList;
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
        Users user = baseDao.findSingleResult(condition, Users.class);
        // ユーザー情報を設定する
        UserVO userinfo = new UserVO();
        // ユーザー情報が存在する場合
        if (user != null) {
        	  // 社員番号
            userinfo.setUserId(user.getId());
            // ユーザ名称(表示用)
            userinfo.setUserName(user.getUserName());
            // パスワード
            userinfo.setPassword(user.getPassword());
            // 登録名
            userinfo.setLoginMailAdress(user.getAffiliationMaster().getCode());
            // 代理入力者ID
            userinfo.setDairishaId(user.getDairishaId());
            // 標準ｼﾌﾄ
            userinfo.setStandardShiftCd(user.getStandardShiftCd());
            // 勤務開始時刻
            userinfo.setKinmuStartTime(user.getKinmuStartTime());
            // 勤務終了時刻
            userinfo.setKinmuEndTime(user.getKinmuEndTime());
            // 休業開始日
            userinfo.setKyugyoStartDate(user.getKyugyoStartDate());
            // 休業終了日
            userinfo.setKyugyoEndDate(user.getKyugyoEndDate());
            // 入社日
            userinfo.setNyushaDate(user.getNyushaDate());
            // 退職日
            userinfo.setTaisyokuDate(user.getTaisyokuDate());
        }
        
		session.setUserInfo(userinfo);

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
		
		Users userInfo = baseDao.findById(userId, Users.class);
		
		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		
		// ドロップダウン項目
		// 自分データを設定する
		ListItemVO item = new ListItemVO();
		item.setValue(userId);
		item.setName(userInfo.getUserName());
		resultList.add(item);
		
		// 代理人があり
		if (!userInfo.getDairishaId().isEmpty()) {
			
			Users dairiInfo = baseDao.findById(userInfo.getDairishaId(), Users.class);
			
			// 代理入力データを設定する
			item = new ListItemVO();
			item.setValue(userInfo.getDairishaId());
			item.setName(dairiInfo.getUserName());
			resultList.add(item);
		}
		
		return resultList;
	}

}
