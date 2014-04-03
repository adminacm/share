package argo.cost.monthlyReportStatusList.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListInfo;
import argo.cost.monthlyReportStatusList.service.MonthlyReportStatusListService;

@Controller
@RequestMapping("/monthlyReportStatusList")
@SessionAttributes(types = { MonthlyReportStatusListForm.class })
public class MonthlyReportStatusListController extends AbstractController  {
	
	/**
	 * このアクションが利用するサービスです。
	 */
	@Autowired
	protected MonthlyReportStatusListService service;

	/**
	 * 初期化
	 *
	 * @param map
	 *            マップ
	 * @return
	 */
    @RequestMapping("/init")
    public String init(Model model) {
    	
    	// 画面情報を作成
    	MonthlyReportStatusListForm form = new MonthlyReportStatusListForm();
    	model.addAttribute(form);
    	
    	// 状況リストを取得
    	List<ListItemVO> statusList = comService.getStatusList();
    	
    	// 状況リストを設定
    	form.setStatusList(statusList);
    	
    	// 状況の初期値設定
    	form.setStatus("");
    	
    	// 年月リストを取得
    	List<ListItemVO> yearMonthList = service.getYearMonthList(new Date());
    	
    	// 年月リストを設定
    	form.setYearMonthList(yearMonthList);
    	
    	// 年月の初期値設定
    	form.setYearMonth("");
    	
    	// 所属リストを取得
    	List<ListItemVO> affiliationList = service.getAffiliationList();
    	
    	// 所属リストを設定
    	form.setAffiliationList(affiliationList);
    	
    	// 所属の初期値設定
    	form.setAffiliation("");
    	
    	// 月報状況一覧リストを取得
    	List<MonthlyReportStatusListInfo> mRSList = service.getMonthlyReportStatusList(form);
    	
    	form.setmRSList(mRSList);;
    	
        return "monthlyReportStatusList";
    }

    /**
     * 表示切替ボタンを押して、表示対象を切り替える
     * 
     * @param form
     *         画面情報
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(MonthlyReportStatusListForm form) {
    	
    	// 月報状況一覧リストを取得
    	List<MonthlyReportStatusListInfo> mRSList = service.getMonthlyReportStatusList(form);
    	
    	form.setmRSList(mRSList);

        return "monthlyReportStatusList";
    }
}
