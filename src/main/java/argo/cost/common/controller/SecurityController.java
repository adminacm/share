package argo.cost.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * ログイン認証クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Controller
public class SecurityController extends AbstractController {

	/**
	 * ユーザログイン処理
	 * 
	 * @return 休日勤務入力画面の初期化の情報
	 * 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request) throws Exception {
		
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	// Spring security 情報を取得
    	String userName = (String) request.getSession().getAttribute("loginName");
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
			request.getSession().setAttribute("loginName", userName);
		}
		
		if (StringUtils.isEmpty(userName)) {
			return "login";
		} else {
			return REDIRECT +"/menu/init";
		}
    	
	}
	
	/**
	 * ユーザログイン処理
	 * 
	 * @return 休日勤務入力画面の初期化の情報
	 * 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/denied")
	public String denied(HttpServletRequest request) throws Exception {
		
    	return "errors/accessDenied";
    	
	}
	/**
	 * ユーザログイン処理
	 * 
	 * @return 休日勤務入力画面の初期化の情報
	 * 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/timeOut")
	public String timeOut(HttpServletRequest request) throws Exception {
		
    	return "errors/timedout";
    	
	}

}
