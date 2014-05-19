package argo.cost.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.AppSession;
import argo.cost.common.service.ComService;

/**
 * <p>
 * 抽象的なコントローラです。
 * </p>
 *
 * @author COST argo Corporation.
 */
public abstract class AbstractController {

	/**
	 * 共通サービスです。
	 */
	@Autowired
	protected ComService comService;
	
	/**
	 *　疑問符
	 */
	public static final String QUESTION_MARK = "?";
	
	/**
	 *　且つ符
	 */
	public static final String AND_MARK = "&";

	/**
	 *　勤怠日付
	 */
	public static final String ATTDENDANCE_DATE = "attendanceDate";
	/**
	 * セッションの属性名です。
	 */
	public static final String SESSION = "session";
	/**
	 * フォワード属性名
	 */
	public static final String FORWARD = "forward";
	/**
	 * リダイレクト属性名
	 */
	public static final String REDIRECT = "redirect:";
	/**
	 * 初期化
	 */
	public static final String INIT = "/init";
	/**
	 * 保存
	 */
	public static final String SAVE = "/save";
	/**
	 * 削除
	 */
	public static final String DELETE = "/delete";
	/**
	 * 戻る
	 */
	public static final String BACK = "/back";
	/**
	 * 検索
	 */
	public static final String SEARCH = "/search";
	/**
	 * 承認
	 */
	public static final String APPROVAL = "/approval";
	/**
	 * 差戻
	 */
	public static final String REMAND = "/remand";
	
	/**
	 * セッション情報取得
	 *
	 * @return セッション情報
	 */
	public static AppSession getSession() {

		return (AppSession) RequestContextHolder.getRequestAttributes().getAttribute(SESSION, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * フォームを初期化
	 *
	 * @param clazz
	 *            フォームのクラス
	 * @param url
	 *            フォーム所属画面URL
	 * @return フォーム
	 * @throws Exception
	 *             異常
	 */
	protected <T extends AbstractForm> T initForm(Class<T> clazz) throws Exception {

		T form = clazz.newInstance();

		AppSession session = getSession();

		if (session == null) {

			throw new Exception();
		}

		// ユーザIDを設定する
		form.setUserId(session.getUserInfo().getId());

		return form;
	}

}
