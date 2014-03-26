package argo.cost.common.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

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
	 * セッションの属性名です。
	 */
	public static final String SESSION = "session";
	/**
	 * フォワード属性名
	 */
	public static final String FORWARD = "forward";

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

		form.setUserId(session.getUserInfo().getId());

		return form;
	}

	/**
	 * 直ぐに文字列出力
	 *
	 * @param response
	 *            レスポンス
	 * @param data
	 *            データ
	 * @throws IOException
	 *             異常
	 */
	protected void writeSimpleData(HttpServletResponse response, String data) throws IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.write(data);
		out.flush();
		out.close();
	}

}
