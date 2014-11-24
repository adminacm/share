package argo.cost.monthlyReportStatusList.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.entity.AffiliationMaster;
import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;
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
	 * 月報状況一覧サービス
	 */
	@Autowired
	protected MonthlyReportStatusListService sevice;

	/**
	 * 月報状況一覧画面ID
	 */
	private static final String MONTHLYREPORT_STATUS_LIST = "monthlyReportStatusList";
	
	/**
	 * 申請番号をクリックするアクション
	 */
	private static final String APPLYNOCLICK = "/applyNoClick";

	/**
	 * 月報状況一覧画面初期化
	 *
	 * @param model
	 *             画面情報モデル
	 * @return 月報状況一覧画面
	 * @throws Exception 
	 */
    @RequestMapping(INIT)
    public String initMonthlyReportStatusList(Model model) throws Exception {
    	
    	// 画面情報を作成
    	MonthlyReportStatusListForm form =  initForm(MonthlyReportStatusListForm.class);
    	model.addAttribute(form);
    	
    	// 年リストを取得
    	List<ListItemVO> yearList = sevice.getYearList(new Date());
    	
    	// 年リストを設定
    	form.setYearList(yearList);
    	
    	// 月リストを取得
    	List<ListItemVO> monthList = sevice.getMonthList();
    	
    	// 月リストを設定
    	form.setMonthList(monthList);

    	// 所属リストを取得
		List<AffiliationMaster> affiliationList = comService.getAffiliationList();
    	
    	// 所属リストを設定
    	form.setAffiliationList(affiliationList);

    	// 初期値設定
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
    	// 当年
    	form.setYear(String.valueOf(year));
    	// 当月
    	if (month < 10) {
    		
        	form.setMonth("0" + month);
    	} else {

        	form.setMonth(String.valueOf(month));
    	}
    	
    	// 月報状況一覧リストを取得
    	List<MonthlyReportStatusListVo> monthlyReportStatusList = sevice.getMonthlyReportStatusList(form);
    	
    	form.setMonthlyReportStatusList(monthlyReportStatusList);
    	
    	// 月報状況一覧画面を戻り
        return MONTHLYREPORT_STATUS_LIST;
    }

    /**
     * 表示切替ボタンを押して、表示対象を切り替える
     * 
     * @param form
     *            月報状況一覧画面情報
     * @return 月報状況一覧画面
     */
    @RequestMapping(value = SEARCH, method = RequestMethod.POST)
    public String searchMonthlyReportStatusList(MonthlyReportStatusListForm form) {
    	
    	// 月報状況一覧リストを取得
    	List<MonthlyReportStatusListVo> monthlyReportStatusList = sevice.getMonthlyReportStatusList(form);
    	
    	// 月報状況一覧リストを設定
    	form.setMonthlyReportStatusList(monthlyReportStatusList);

    	// 月報状況一覧画面を戻り
        return MONTHLYREPORT_STATUS_LIST;
    }

    /**
     * 申請番号リンクをクリックし、承認詳細画面へ遷移する
     * 
     * @param applyNo
     *               申請番号
     * @param applyKbnCd
     *                  申請区分
     * @return　承認詳細画面(月報承認詳細画面、超勤振替申請承認詳細画面)
     */
    @RequestMapping(value = APPLYNOCLICK)
    public String approvalNoClick(@RequestParam("applyNo") String applyNo, @RequestParam("applyKbnCd") String applyKbnCd) {
    	
    	// 承認詳細画面
    	String strApprovalDisplay = "";
    	
    	// 承認詳細画面を設定する
    	String strApplyNo = "applyNo=";
    	
    	// 戻り用画面のURL
    	String backUrl = "backUrl=";
    	
    	// 申請区分が月報の場合
    	if (CommonConstant.APPLY_KBN_GETUHOU.equals(applyKbnCd)) {

        	// 月報承認詳細画面
    		strApprovalDisplay = REDIRECT + UrlConstant.URL_MONTHLYREPORT_APPROVAL + INIT + QUESTION_MARK + strApplyNo + applyNo 
    				+ AND_MARK + backUrl + UrlConstant.URL_MONTHLYREPORT_STATUS_LIST;
        	// 申請区分が超勤振替の場合
    	} else if (CommonConstant.APPLY_KBN_CHOKIN_FURIKAE.equals(applyKbnCd)) {

        	// 超勤振替申請承認詳細画面
    		strApprovalDisplay = REDIRECT + UrlConstant.URL_HOLIDAYFOROVERTIME_APPROVAL + INIT + QUESTION_MARK + strApplyNo + applyNo + AND_MARK 
    				+ backUrl + UrlConstant.URL_MONTHLYREPORT_STATUS_LIST;
    	}
    	
    	// 承認詳細画面へ遷移する
    	return strApprovalDisplay;
    }
}
