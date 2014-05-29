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

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.ListItemVO;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.monthlyReport.model.MonthlyReportForm;
import argo.cost.monthlyReport.model.MonthlyReportInfo;
import argo.cost.monthlyReport.service.MonthlyReportService;

/**
 * 月報画面業務スクラス
 * 報画面に関する処理を行う。
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_MONTHLYREPORT)
@SessionAttributes(types = { MonthlyReportForm.class })
public class MonthlyReportController extends AbstractController {
	
	/**
	 * このアクションが利用するサービスです。
	 */
	@Autowired
	protected MonthlyReportService monthlyReportService;
	/**
	 * 月報画面URL
	 */
	private static final String MONTHLYREPORT = "monthlyReport";
	/**
	 * 先月URL
	 */
	private static final String LASTMONTH = "/lastMonth";
	/**
	 * 来月URL
	 */
	private static final String nextMONTH = "/nextMonth";

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
    @RequestMapping(value = INIT)
    public String initMonthlyReport(Model model, @RequestParam("newMonth") String newMonth) throws Exception {
    	
    	String loginId = getSession().getUserInfo().getLoginMailAdress();
    	// フォーム初期化
    	MonthlyReportForm form = initForm(MonthlyReportForm.class);
    	// 画面へ設定します。
    	model.addAttribute(form);
    	
		// セッション情報設定
		getSession().setUrl("monthlyReport");
    	
    	// 氏名リストを取得
    	List<ListItemVO> userList = comService.getUserNameList(loginId);
    	form.setUserList(userList);
    	
    	// 氏名の初期値設定
    	form.setUserCode(loginId);
    	// 最後の提出年月取得
    	String date = monthlyReportService.getUserMonth(loginId);
    	// 初期以外の場合
    	if (StringUtils.isNotEmpty(newMonth)) {
    		date = newMonth.substring(0,6).concat("01");
    	}
    	form.setYearMonth(date);
    	Date formatDate = CostDateUtils.toDate(date);
    	form.setYearMonthHyoji(monthlyReportService.getDateFormat(formatDate));
    	// 提出状態取得
    	String status = comService.getMonthReportStatus(loginId, date);
    	form.setProStatus(status);
    	
    	List<MonthlyReportInfo> resultList = monthlyReportService.getMonReList(formatDate);
    	
    	form.setmRList(resultList);
    	// 月報情報の設定
    	monthlyReportService.setUserMonthReport(loginId, date, resultList);
    	
//		// 【PJ別作業時間集計】を取得
//		List<Project> projectList = monthlyReportService.getProjectList(loginId, date);
//		
//		// プロジェクト情報設定
//		form.setProjectList(projectList);
		
        return MONTHLYREPORT;
    }

    /**
	 * 検索処理
	 *
	 * @param form
	 *            画面フォーム情報
	 * @return 月報画面
	 * @throws Exception
	 *             Exception
	 */
    @RequestMapping(value = SEARCH, method = RequestMethod.POST)
    public String searchMonthlyReportList(MonthlyReportForm form) throws Exception {
    	
    	String userId = form.getUserCode();
    	List<MonthlyReportInfo> resultList = monthlyReportService.getMonReList(CostDateUtils.toDate(form.getYearMonth()));
    	
    	form.setmRList(resultList);
    	
    	// 月報情報の設定
    	monthlyReportService.setUserMonthReport(userId, form.getYearMonth(), resultList);
    	
        return MONTHLYREPORT;
    }
    
    /**
	 * 先月を取得
	 *
	 * @param form
	 *            画面フォーム情報
	 * @return 月報画面
	 * @throws Exception
	 *             Exception
	 */
    @RequestMapping(value = LASTMONTH, method = RequestMethod.POST)
    public String getLastMonth(MonthlyReportForm form) throws Exception {
    	
    	// 先月を取得
    	String lastMonth = monthlyReportService.changeYearMonth("last", form.getYearMonth());
    	
        return REDIRECT + UrlConstant.URL_MONTHLYREPORT + INIT + QUESTION_MARK + "newMonth=" + lastMonth;
    }
    
    /**
	 * 来月を取得
	 *
	 * @param form
	 *            画面フォーム情報
	 * @return 月報画面
	 * @throws Exception
	 *             Exception
	 */
    @RequestMapping(value = nextMONTH, method = RequestMethod.POST)
    public String getNextMonth(MonthlyReportForm form) throws Exception {
    	
    	// 来月を取得
    	String nextMonth = monthlyReportService.changeYearMonth("next", form.getYearMonth());

        return REDIRECT + UrlConstant.URL_MONTHLYREPORT + INIT + QUESTION_MARK + "newMonth=" + nextMonth;
    }

}
