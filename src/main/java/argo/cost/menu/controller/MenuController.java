package argo.cost.menu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.UserVO;
import argo.cost.menu.model.MenueForm;

/**
 * メニュー画面業務スクラス
 * メニュー画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_MENU)
@SessionAttributes(types = { MenueForm.class })
public class MenuController extends AbstractController {
    
	/**
	 * メニュー画面URL
	 */
	private static final String MENU = "menu";
    
	/**
	 * 勤怠入力画面へURL
	 */
	private static final String ATT_URL = "/att";
	/**
	 * メニュー画面初期化
	 * 
	 * @param model
	 *            画面情報
	 * @param request
	 *            リクエスト情報
	 *            
	 * @return　メニュー画面Url
	 */
    @RequestMapping(value = INIT)
    public String doLogin(Model model, HttpServletRequest request) {
    	
    	// Spring security 情報を取得
    	String loginMale = (String) request.getSession().getAttribute("loginName");

		// セッション情報初期化
		AppSession session = comService.initSession(loginMale);
		
		// 表示用ユーザー名を設定する
		request.getSession().setAttribute("userName", session.getUserInfo().getUserName());
		
		// セッション情報設定
		RequestContextHolder.getRequestAttributes().setAttribute(SESSION, session, RequestAttributes.SCOPE_SESSION);

    	// フォーム初期化
    	MenueForm form = null;
		try {
			form = initForm(MenueForm.class);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		// 画面へ設定します。
		model.addAttribute(form);

    	// ユーザ情報を取得
    	UserVO userInfo = getSession().getUserInfo();
    	
    	form.setUserInfo(userInfo);
		return MENU;
    }
    
    /**
	 * 勤怠入力画面へ
	 *
	 * @param model
	 *            画面情報
	 * @return　勤怠入力画面
	 */
    @RequestMapping(ATT_URL)
    public String goAttdanceInput(Model model) {
    	
		// セッション情報設定
    	getSession().setForm(MENU);

		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK+ ATTDENDANCE_DATE + EQUAL_SIGN;
    }

}
