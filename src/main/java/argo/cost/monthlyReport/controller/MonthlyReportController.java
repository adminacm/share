package argo.cost.monthlyReport.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.ListItem;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.monthlyReport.model.MonthlyReportForm;
import argo.cost.monthlyReport.model.MonthlyReportInfo;
import argo.cost.monthlyReport.service.MonthlyReportService;

@Controller
@RequestMapping("/monthlyReport")
@SessionAttributes(types = { MonthlyReportForm.class })
public class MonthlyReportController extends AbstractController {
	
	/**
	 * このアクションが利用するサービスです。
	 */
	@Autowired
	protected MonthlyReportService service;

	/**
	 * 初期化
	 *
	 * @param map
	 *            マップ
	 * @param loginId
	 *            ユーザID
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 *             Exception
	 */
    @RequestMapping("/init")
    public String init(Model model, @RequestParam("newMonth") String newMonth) throws Exception {
    	
    	String loginId = getSession().getUserInfo().getId();
    	// フォーム初期化
    	MonthlyReportForm form = initForm(MonthlyReportForm.class);
    	// 画面へ設定します。
    	model.addAttribute(form);
    	
		// セッション情報設定
		getSession().setUrl("monthlyReport");
    	
    	// 氏名リストを取得
    	List<ListItem> userList = comService.getUserNameList(loginId);
    	form.setUserList(userList);
    	
    	// 氏名の初期値設定
    	form.setUserCode(loginId);
    	// 最後の提出年月取得
    	String date = service.getUserMonth(loginId);
    	// 初期以外の場合
    	if (StringUtils.isNotEmpty(newMonth)) {
    		date = newMonth.substring(0,6).concat("01");
    	}
    	form.setYearMonth(date);
    	Date formatDate = CostDateUtils.toDate(date);
    	form.setYearMonthHyoji(service.ｇetDateFormat(formatDate));
    	// 提出状態取得
    	String status = comService.getMonthStatus(loginId, date);
    	form.setProStatus(status);
    	
    	List<MonthlyReportInfo> resultList = service.getMonReList(formatDate);
    	
    	form.setmRList(resultList);
    	
    	// 月報情報の設定
    	service.setUserMonthReport(loginId, date, resultList);
    	
        return "monthlyReport";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(MonthlyReportForm form) throws Exception {
    	
    	String userId = form.getUserCode();
    	List<MonthlyReportInfo> resultList = service.getMonReList(CostDateUtils.toDate(form.getYearMonth()));
    	
    	form.setmRList(resultList);
    	
    	// 月報情報の設定
    	service.setUserMonthReport(userId, form.getYearMonth(), resultList);
    	
        return "monthlyReport";
    }
    
    @RequestMapping(value = "/lastMonth", method = RequestMethod.POST)
    public String getLastMonth(MonthlyReportForm form) throws Exception {
    	
    	// 先月を取得
    	String lastMonth = service.changeYearMonth("last", form.getYearMonth());
    	
        return "redirect:/monthlyReport/init?newMonth=" + lastMonth;
    }
    
    @RequestMapping(value = "/nextMonth", method = RequestMethod.POST)
    public String getNextMonth(MonthlyReportForm form) throws Exception {
    	
    	// 来月を取得
    	String nextMonth = service.changeYearMonth("next", form.getYearMonth());

        return "redirect:/monthlyReport/init?newMonth=" + nextMonth;
    }

}
