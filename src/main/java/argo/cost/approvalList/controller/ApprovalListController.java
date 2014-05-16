package argo.cost.approvalList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.approvalList.model.ApprovalListForm;
import argo.cost.approvalList.model.ApprovalListVo;
import argo.cost.approvalList.service.ApprovalListService;
import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.ListItemVO;

/**
 * 承認一覧画面業務クラス
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_APPROVALLIST)
@SessionAttributes(types = { ApprovalListForm.class })
public class ApprovalListController extends AbstractController  {
	
	/**
	 * 承認一覧画面サービス
	 */
	@Autowired
	protected ApprovalListService approvalListService;

	/**
	 * 承認一覧画面ＩＤ
	 */
	private static final String APPROVALLIST = "approvalList";
	
	/**
	 * 申請番号をクリックするアクション
	 */
	private static final String APPLYNOCLICK = "/applyNoClick";

	/**
	 * 初期化処理
	 *
	 * @param model
	 *             モデル
	 * @return 承認一覧画面
	 */
    @RequestMapping(INIT)
    public String initApprovalList(Model model) {
    	
    	// 画面情報を作成
    	ApprovalListForm form = new ApprovalListForm();
    	model.addAttribute(form);
    	
    	// 状況リストを取得
    	List<ListItemVO> statusList = comService.getStatusList();
    	
    	// 状況リストを設定
    	form.setStatusList(statusList);
    	
    	// 状況の初期値設定
    	form.setStatus("");
    	
    	// 承認リストを取得
    	List<ApprovalListVo> approvalList = approvalListService.getApprovalList(form.getStatus());
    	
    	form.setApprovalList(approvalList);
    	
        return APPROVALLIST;
    }

    /**
     * 表示切替ボタンを押して、表示対象を切り替える
     * 
     * @param form
     *            承認一覧画面情報
     * @return 承認一覧画面
     */
    @RequestMapping(value = SEARCH, method = RequestMethod.POST)
    public String searchApprovalList(ApprovalListForm approvalListForm) {
    	
    	// 承認リストを取得
    	List<ApprovalListVo> approvalList = approvalListService.getApprovalList(approvalListForm.getStatus());
    	
    	approvalListForm.setApprovalList(approvalList);

        return APPROVALLIST;
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
    	
    	// 申請区分が月報の場合
    	if ("1".equals(applyKbnCd)) {

        	// 月報承認詳細画面
    		strApprovalDisplay = REDIRECT + UrlConstant.URL_MONTHLYREPORT_APPROVAL + INIT + QUESTION_MARK + strApplyNo + applyNo;
        	// 申請区分が超勤振替の場合
    	} else if ("2".equals(applyKbnCd)) {

        	// 超勤振替申請承認詳細画面
    		strApprovalDisplay = REDIRECT + UrlConstant.URL_HOLIDAYFOROVERTIME_APPROVAL + INIT + QUESTION_MARK + strApplyNo + applyNo;
    	}
    	
    	// 承認詳細画面へ遷移する
    	return strApprovalDisplay;
    }
}
