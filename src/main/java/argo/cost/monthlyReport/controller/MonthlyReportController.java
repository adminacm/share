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
import argo.cost.common.entity.Users;
import argo.cost.common.model.MonthlyReportDispVO;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.monthlyReport.model.MonthlyReportForm;
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
	private static final String NEXTMONTH = "/nextMonth";
	
	/**
	 * 月報提出URL
	 */
	private static final String MONTHLYREPORTCOMMIT = "/monthlyReportCommit";

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
    	
    	// フォーム初期化
    	MonthlyReportForm monthlyReportForm = initForm(MonthlyReportForm.class);
    	// 画面へ設定します。
    	model.addAttribute(monthlyReportForm);
    	
    	String userId = monthlyReportForm.getUserId();
    	
		// セッション情報設定
		getSession().setUrl("monthlyReport");
    	
    	// 氏名リストを取得 
    	List<Users> userList = comService.getUserNameList(userId);
    	monthlyReportForm.setUserList(userList);
    	
    	// 氏名の初期値設定
    	monthlyReportForm.setUserCode(userId);
    	// 最後の提出年月取得
    	String strLatestShinseiDate = monthlyReportService.getUserLatestShinseiMonth(userId);
    	// 初期以外の場合
    	if (StringUtils.isNotEmpty(newMonth)) {
    		strLatestShinseiDate = newMonth.substring(0,6).concat("01");
    	}
    	monthlyReportForm.setYearMonth(strLatestShinseiDate);
    	Date formatDate = CostDateUtils.toDate(strLatestShinseiDate);
    	monthlyReportForm.setYearMonthHyoji(monthlyReportService.getDateFormat(formatDate));
    	// 提出状態取得
    	String status = comService.getMonthReportStatus(userId, strLatestShinseiDate);
    	monthlyReportForm.setProStatus(status);
    	
    	List<MonthlyReportDispVO> monthlyReportDispVOList = monthlyReportService.getMonthyReportList(formatDate);
    	
    	monthlyReportForm.setmRList(monthlyReportDispVOList);
    	// 月報情報の設定
    	monthlyReportService.setUserMonthReport(userId, strLatestShinseiDate, monthlyReportForm);
    	
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
    	getSession().getUserInfo().setTaishoUserId(form.getUserCode());
    	List<MonthlyReportDispVO> resultList = monthlyReportService.getMonthyReportList(CostDateUtils.toDate(form.getYearMonth()));

    	form.setmRList(resultList);
    	
    	// 月報情報の設定
    	monthlyReportService.setUserMonthReport(userId, form.getYearMonth(), form);
    	
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
    @RequestMapping(value = NEXTMONTH, method = RequestMethod.POST)
    public String getNextMonth(MonthlyReportForm form) throws Exception {
    	
    	// 来月を取得
    	String nextMonth = monthlyReportService.changeYearMonth("next", form.getYearMonth());

        return REDIRECT + UrlConstant.URL_MONTHLYREPORT + INIT + QUESTION_MARK + "newMonth=" + nextMonth;
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
    @RequestMapping(value = MONTHLYREPORTCOMMIT, method = RequestMethod.POST)
    public String monthlyReportCommit(MonthlyReportForm monthlyReportForm) throws Exception {
    	
    	String strMonthyReportCommitFlg = monthlyReportService.monthyReportCommit(monthlyReportForm);
    	if ("1".equals(strMonthyReportCommitFlg)) {
    		return MONTHLYREPORT;
    	} else {
    		
    		// TODO
    		return MONTHLYREPORT;
    	}
    	

    }
}
