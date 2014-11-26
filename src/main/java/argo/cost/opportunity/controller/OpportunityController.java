package argo.cost.opportunity.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/opportunity")
public class OpportunityController {
	

    @RequestMapping("/init")
    public String doLogin(Model model, HttpServletRequest request) {
    	
		return "redirect:https://argocostmanager2.herokuapp.com/";
    }

}
