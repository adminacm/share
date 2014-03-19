package argo.cost.menu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.entity.Users;
import argo.cost.menu.model.MenueForm;


@Controller
@RequestMapping("/menu")
@SessionAttributes(types = { MenueForm.class })
public class MenuController extends AbstractController {
    
    @RequestMapping("/init")
    public String doLogin(Model model, HttpServletRequest request) {

    	// Spring security 情報を取得
    	SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
    	
    	String userName = securityContextImpl.getAuthentication().getName();
    	
    	request.getSession().setAttribute("userName", userName);

		// セッション情報初期化
		AppSession session = comService.initSession(userName);
		
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
    	Users userInfo = getSession().getUserInfo();
    	
    	form.setUserInfo(userInfo);
		return "menu";
    }

}
