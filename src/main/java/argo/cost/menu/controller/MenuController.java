package argo.cost.menu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import argo.cost.menu.model.UserInfo;
import argo.cost.menu.model.UserKengen;
import argo.cost.menu.service.LoginService;


@Controller
@RequestMapping("/top")
public class MenuController {

	/**
	 * このアクションが利用するサービスです。
	 */
	@Autowired
	protected LoginService service;

    @RequestMapping("/init")
    public String initTop(Map<String, Object> map) {

    	map.put("userInfo", new UserInfo());

        return "top";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute("userInfo") UserInfo user, BindingResult result) {

    	// ユーザ情報の取得
    	UserInfo userInfo = service.getUserInfo(user);
		
		// ユーザ情報が存在しない
		if (userInfo == null) {
			return "top";
		// ユーザー名とパスウードが正しい場合
		} else if (userInfo.getPassword().equals(user.getPassword())) {
			return "redirect:/top/initMenu?userId=" + user.getUserId();
		// パスウードが誤りの場合
		} else {
			return "top";
		}
    }
    
    @RequestMapping("/initMenu")
    public String initMenu(Map<String, Object> map,@RequestParam String userId) {

    	UserKengen userkg = service.getUserKengen(userId);
    	map.put("kengenKbn", userkg.getKengenCd());

        return "menu";
    }

}
