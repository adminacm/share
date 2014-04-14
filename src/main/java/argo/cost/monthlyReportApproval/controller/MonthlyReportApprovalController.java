package argo.cost.monthlyReportApproval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalForm;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;
import argo.cost.monthlyReportApproval.model.ProjectVo;
import argo.cost.monthlyReportApproval.service.MonthlyReportApprovalService;

/**
 * <p>
 * 月報承認業務クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_MONTHLYREPORT_APPROVAL)
@SessionAttributes(types = {MonthlyReportApprovalForm.class})
public class MonthlyReportApprovalController extends AbstractController {

	/**
	 * 月報承認サービス
	 */
	@Autowired
	protected MonthlyReportApprovalService service;

	/**
	 * 月報承認情報
	 */
	private static final String MONTHLYREPORT_APPROVAL_INFO = "monthlyReportApprovalInfo";

	/**
	 * 月報承認画面URL
	 */
	private static final String MONTHLYREPORT_APPROVAL = "monthlyReportApproval";

	/**
	 * 月報承認画面の初期化処理
	 * 
	 * @param map
	 *            画面情報マープ
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            勤怠入力画面から渡した休日の日付
	 * @return 月報承認画面の初期化の情報
	 * 
	 * @throws Exception 
	 */
	@RequestMapping(value = INIT)
	public String init(Model model, @RequestParam("applyNo") String applyNo) throws Exception {
		
		// 月報承認画面情報初期化
		MonthlyReportApprovalForm form = initForm(MonthlyReportApprovalForm.class);

		// 月報承認データを取得
		List<MonthlyReportApprovalVo> monthlyReportApprovalList = service.getMonthReportList(applyNo);
		
		// 月報承認リスト設定
		form.setMonthlyReportApprovalList(monthlyReportApprovalList);

		// 【PJ別作業時間集計】を取得
		List<ProjectVo> projectList = service.getProjectList(applyNo);
		
		// プロジェクト情報設定
		form.setProjectList(projectList);
		
		model.addAttribute(MONTHLYREPORT_APPROVAL_INFO, form);

		return MONTHLYREPORT_APPROVAL;

	}
}
