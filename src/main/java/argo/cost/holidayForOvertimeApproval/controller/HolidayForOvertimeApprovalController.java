package argo.cost.holidayForOvertimeApproval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.holidayForOvertimeApproval.model.HolidayForOvertimeApprovalForm;
import argo.cost.holidayForOvertimeApproval.service.HolidayForOvertimeApprovalService;

/**
 * <p>
 * 超勤振替申請承認業務クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_HOLIDAYFOROVERTIME_APPROVAL)
@SessionAttributes(types = {HolidayForOvertimeApprovalForm.class})
public class HolidayForOvertimeApprovalController extends AbstractController {

	/**
	 * 超勤振替申請承認サービス
	 */
	@Autowired
	protected HolidayForOvertimeApprovalService holidayForOvertimeApprovalService;

	/**
	 * 超勤振替申請承認画面URL
	 */
	private static final String HOLIDAYFOROVERTIME_APPROVAL = "holidayForOvertimeApproval";

	/**
	 * 超勤振替申請承認画面の初期化処理
	 * 
	 * @param model
	 *            画面情報モデル
	 * @param applyNo
	 *            申請番号
	 * @return 
	 *        超勤振替申請承認画面の初期化の情報
	 * @throws Exception 
	 */
	@RequestMapping(value = INIT)
	public String initHolidayForOvertimeApproval(Model model, @RequestParam("applyNo") String applyNo, @RequestParam("backUrl") String backUrl) throws Exception {

		
		// 超勤振替申請承認情報初期化
		HolidayForOvertimeApprovalForm holidayForOvertimeApprovalForm = initForm(HolidayForOvertimeApprovalForm.class);
		// 超勤振替申請承認情報を取得
		holidayForOvertimeApprovalForm= holidayForOvertimeApprovalService.getHolidayForOvertimeApproval(holidayForOvertimeApprovalForm, applyNo);
		
		//　申請番号を設定
		holidayForOvertimeApprovalForm.setApplyNo(applyNo);
		
		//　戻り用画面URL
		holidayForOvertimeApprovalForm.setBackUrl(backUrl);
		
		// 処理状況を取得
		String status = holidayForOvertimeApprovalService.getStatusName(applyNo);
		
		// 処理状況設定
		holidayForOvertimeApprovalForm.setProStatus(status);
		
		model.addAttribute(holidayForOvertimeApprovalForm);

		return HOLIDAYFOROVERTIME_APPROVAL;
	}

	/**
	 * 超勤振替申請承認画面の承認処理
	 * 
	 * @return 承認一覧画面
	 */
	@RequestMapping(value = APPROVAL)
	public String doApproval(HolidayForOvertimeApprovalForm form) {
		
		// 承認処理を実行
		holidayForOvertimeApprovalService.approvalOverWork(form.getApplyNo());

		// 承認一覧画面へ遷移する
		return REDIRECT + form.getBackUrl() + INIT;
	}

	/**
	 * 超勤振替申請承認画面の差戻処理
	 * 
	 * @return 承認一覧画面
	 */
	@RequestMapping(value = REMAND)
	public String doRemand(HolidayForOvertimeApprovalForm form) {
		
		// 差戻処理実行
		holidayForOvertimeApprovalService.remandOverWork(form.getApplyNo());
		
		// 差戻ボタンを押すと申請状況が差戻しに更新され、承認一覧画面へ遷移する
		return REDIRECT + form.getBackUrl() + INIT;
	}

	/**
	 * 超勤振替申請承認画面の戻る処理
	 * 
	 * @return 承認一覧画面
	 */
	@RequestMapping(value = BACK)
	public String doBack(HolidayForOvertimeApprovalForm form) {

		// 戻るボタンを押すと、承認一覧画面へ戻る
		return REDIRECT + form.getBackUrl() + INIT;
	}
}
