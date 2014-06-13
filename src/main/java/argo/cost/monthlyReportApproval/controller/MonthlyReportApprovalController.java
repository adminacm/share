package argo.cost.monthlyReportApproval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalForm;
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
	protected MonthlyReportApprovalService monthlyReportApprovalService;

	/**
	 * 月報承認画面URL
	 */
	private static final String MONTHLYREPORT_APPROVAL = "monthlyReportApproval";

	/**
	 * 月報承認画面の初期化処理
	 * 
	 * @param model
	 *            画面情報モデル
	 * @param applyNo
	 *            申請番号
	 * @return 
	 *        月報承認画面の初期化の情報
	 * 
	 * @throws Exception 
	 */
	@RequestMapping(value = INIT)
	public String initMonthlyReportApproval(Model model, @RequestParam("applyNo") String applyNo, @RequestParam("backUrl") String backUrl) throws Exception {
		
		// 月報承認画面情報初期化
		MonthlyReportApprovalForm monthlyReportApprovalForm = initForm(MonthlyReportApprovalForm.class);
		
		//　申請番号を設定
		monthlyReportApprovalForm.setApplyNo(applyNo);
		
		//　戻り用画面URL
		monthlyReportApprovalForm.setBackUrl(backUrl);
		
		// 処理状況を取得
		String strProjectStatusCode = monthlyReportApprovalService.getStatusCode(applyNo);
		
		// 処理状況設定
		monthlyReportApprovalForm.setProStatus(strProjectStatusCode);

		
		// 月報承認データ設定
		monthlyReportApprovalService.getMonReApprovalList(monthlyReportApprovalForm,applyNo);

//		// 【PJ別作業時間集計】を取得
//		List<ProjWorkTimeManage> projectList = monthlyReportApprovalService.getProjectList(applyNo);
//		
//		// プロジェクト情報設定
//		form.setProjectList(projectList);
		
		model.addAttribute(monthlyReportApprovalForm);

		return MONTHLYREPORT_APPROVAL;

	}

	/**
	 * 月報承認画面の承認処理
	 * 
	 * @return 承認一覧画面
	 */
	@RequestMapping(value = APPROVAL)
	public String doApproval(MonthlyReportApprovalForm form) {
		
		// 申請状況「承認」
		String proStatusCode = "03";
		
		// 申請状況が承認に更新
		String updateFlg = monthlyReportApprovalService.updateProStatus(form.getApplyNo(), proStatusCode);
		
		if ("1".equals(updateFlg)) {
			System.out.print("月報承認画面申請状況が承認に更新しました");
		}

		// 承認一覧画面へ遷移する
		return REDIRECT + form.getBackUrl() + INIT;

	}

	/**
	 * 月報承認画面の差戻処理
	 * 
	 * @return 承認一覧画面
	 */
	@RequestMapping(value = REMAND)
	public String doRemand(MonthlyReportApprovalForm form) {

		// 申請状況「差戻」
		String proStatus = "04";
		
		// 申請状況が差戻しに更新
		String updateFlg = monthlyReportApprovalService.updateProStatus(form.getApplyNo(), proStatus);
		
		if ("1".equals(updateFlg)) {
			System.out.print("月報承認画面申請状況が差戻に更新しました");
		}
		
		// 差戻ボタンを押すと申請状況が差戻しに更新され、承認一覧画面へ遷移する
		return REDIRECT + form.getBackUrl() + INIT;

	}

	/**
	 * 月報承認画面の戻る処理
	 * 
	 * @return 承認一覧画面
	 */
	@RequestMapping(value = BACK)
	public String doBack(MonthlyReportApprovalForm form) {

		// 戻るボタンを押すと、承認一覧画面へ戻る
		return REDIRECT + form.getBackUrl() + INIT;

	}
}
