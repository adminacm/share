package argo.cost.monthlyReportStatusList.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListInfo;
import argo.cost.monthlyReportStatusList.service.MonthlyReportStatusListService;

/**
 * <p>
 * 月報状況一覧画面業務クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_MONTHLYREPORT_STATUS_LIST)
@SessionAttributes(types = { MonthlyReportStatusListForm.class })
public class MonthlyReportStatusListController extends AbstractController  {
	
	/**
	 * このアクションが利用するサービスです。
	 */
	@Autowired
	protected MonthlyReportStatusListService monthlyReportStatusListService;

	/**
	 * 月報状況一覧画面URL
	 */
	private static final String MONTHLYREPORT_STATUS_LIST = "monthlyReportStatusList";

	/**
	 * 月報状況一覧画面初期化
	 *
	 * @param model
	 *            画面情報モデル
	 * @return
	 */
    @RequestMapping(INIT)
    public String init(Model model) {
    	
    	// 画面情報を作成
    	MonthlyReportStatusListForm form = new MonthlyReportStatusListForm();
    	model.addAttribute(form);
    	
    	// 状況リストを取得
    	List<ListItemVO> statusList = comService.getStatusList();
    	
    	// 状況リストを設定
    	form.setStatusList(statusList);
    	
    	// 年リストを取得
    	List<ListItemVO> yearList = monthlyReportStatusListService.getYearList(new Date());
    	
    	// 年リストを設定
    	form.setYearList(yearList);
    	
    	// 月リストを取得
    	List<ListItemVO> monthList = monthlyReportStatusListService.getMonthList();
    	
    	// 月リストを設定
    	form.setMonthList(monthList);

    	// 所属リストを取得
    	List<ListItemVO> affiliationList = monthlyReportStatusListService.getAffiliationList();
    	
    	// 所属リストを設定
    	form.setAffiliationList(affiliationList);
    	
    	// 月報状況一覧リストを取得
    	List<MonthlyReportStatusListInfo> mRSList = monthlyReportStatusListService.getMonthlyReportStatusList(form);
    	
    	form.setmRSList(mRSList);;

    	//TODO 初期値設定
    	// 状況
    	form.setStatus("");

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
    	// 当年
    	form.setYear(String.valueOf(year));
    	// 当月
    	form.setMonth(String.valueOf(month));
    	// 所属
    	form.setAffiliation("00");
    	
        return MONTHLYREPORT_STATUS_LIST;
    }

    /**
     * 表示切替ボタンを押して、表示対象を切り替える
     * 
     * @param form
     *         月報状況一覧画面情報
     * @return
     */
    @RequestMapping(value = SEARCH, method = RequestMethod.POST)
    public String search(MonthlyReportStatusListForm form) {
    	
    	// 月報状況一覧リストを取得
    	List<MonthlyReportStatusListInfo> mRSList = monthlyReportStatusListService.getMonthlyReportStatusList(form);
    	
    	form.setmRSList(mRSList);

        return MONTHLYREPORT_STATUS_LIST;
    }

    /**
     * 給与奉行向けCSV出力ボタンを押下
     * 
     * @param form
     *         月報状況一覧画面情報
     * @param response
     *         レスポンス
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = CSVOUTPUT, method = RequestMethod.POST)
    public String doCSV(MonthlyReportStatusListForm form, HttpServletResponse response) throws Exception {
    	
		// CSVファイルデータ作成
    	monthlyReportStatusListService.createCSVFile(form, response);

        return MONTHLYREPORT_STATUS_LIST;

    }
}
