package argo.cost.menu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

@Controller
@RequestMapping(UrlConstant.URL_MENU)
@SessionAttributes(types = { MenueForm.class })
public class MenuController extends AbstractController {
    
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
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	// Spring security 情報を取得
    	String loginMale;
    	if (principal instanceof UserDetails) {
    		loginMale = ((UserDetails)principal).getUsername(); 
    		} else {
    			loginMale = principal.toString(); 
    		}
    	
    	request.getSession().setAttribute("loginMail", loginMale);

		// セッション情報初期化
		AppSession session = comService.initSession(loginMale);
		
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
		return "menu";
    }
    
    /**
	 * 勤怠入力画面へ
	 *
	 * @param model
	 *            画面情報
	 * @return　勤怠入力画面
	 */
    @RequestMapping("/att")
    public String goAttdanceInput(Model model) {
    	
		// セッション情報設定
    	getSession().setForm("Menu");

		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK+ "attDate=";
    }

}
