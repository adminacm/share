package argo.cost.menu.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.AppSession;
import argo.cost.common.model.UserInfo;
import argo.cost.menu.model.MenueForm;


@Controller
@RequestMapping("/top")
@SessionAttributes(types = { MenueForm.class })
public class MenuController extends AbstractController {

    @RequestMapping("/init")
    public String initTop(Map<String, Object> map) {

    	map.put("userInfo", new UserInfo());

        return "top";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute("userInfo") UserInfo user,HttpServletRequest request, BindingResult result) {

    	RequestContextHolder.getRequestAttributes().removeAttribute(SESSION, RequestAttributes.SCOPE_SESSION);
    	
    	request.getSession().setAttribute("userId", user.getUserId());
    	
		// セッション情報初期化
		AppSession session = comService.initSession(user.getUserId(), user.getPassword());

		// セッション情報設定
		RequestContextHolder.getRequestAttributes().setAttribute(SESSION, session, RequestAttributes.SCOPE_SESSION);

		return "forward:/top/initMenu";
    }
    
    @RequestMapping("/initMenu")
    public String initMenu(Model model, HttpServletRequest request) throws Exception {

    	// ユーザ情報を取得
    	UserInfo userInfo = getSession().getUserInfo();
    	
    	// フォーム初期化
    	MenueForm form = initForm(MenueForm.class);
		// 画面へ設定します。
		model.addAttribute(form);
		
    	form.setUserInfo(userInfo);
    	
        return "menu";
    }

}
