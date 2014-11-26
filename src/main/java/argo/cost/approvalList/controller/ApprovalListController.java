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
import argo.cost.approvalList.model.ApprovalListVO;
import argo.cost.approvalList.service.ApprovalListService;
import argo.cost.common.constant.CommonConstant;
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
	 * @throws Exception 
	 */
    @RequestMapping(INIT)
    public String initApprovalList(Model model) throws Exception {
    	
    	// フォーム初期化
    	ApprovalListForm approvalListForm = initForm(ApprovalListForm.class);
    	// 画面情報を作成
    	model.addAttribute(approvalListForm);
    	
    	// 状況リストを取得
    	List<ListItemVO> statusList = approvalListService.getStatusList();
    	
    	// 状況リストを設定
    	approvalListForm.setStatusList(statusList);
    	
    	// 状況の初期値設定
    	approvalListForm.setStatus("");
    	
    	// 承認リストを取得
    	List<ApprovalListVO> approvalList = approvalListService.getApprovalList(approvalListForm.getStatus(),approvalListForm.getUserId());
    	
    	approvalListForm.setApprovalList(approvalList);
    	
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
    	List<ApprovalListVO> approvalList = approvalListService.getApprovalList(approvalListForm.getStatus(),approvalListForm.getUserId());
    	
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
    	
    	// 戻り用画面のURL
    	String backUrl = "backUrl=";
    	
    	// 申請区分が月報の場合
    	if (CommonConstant.APPLY_KBN_GETUHOU.equals(applyKbnCd)) {

        	// 月報承認詳細画面
    		strApprovalDisplay = REDIRECT + UrlConstant.URL_MONTHLYREPORT_APPROVAL + INIT + QUESTION_MARK + strApplyNo + applyNo 
    				+ AND_MARK + backUrl + UrlConstant.URL_APPROVALLIST;
        	// 申請区分が超勤振替の場合
    	} else if (CommonConstant.APPLY_KBN_CHOKIN_FURIKAE.equals(applyKbnCd)) {

        	// 超勤振替申請承認詳細画面
    		strApprovalDisplay = REDIRECT + UrlConstant.URL_HOLIDAYFOROVERTIME_APPROVAL + INIT + QUESTION_MARK + strApplyNo + applyNo
    				+ AND_MARK +  backUrl + UrlConstant.URL_APPROVALLIST;
    	}
    	
    	// 承認詳細画面へ遷移する
    	return strApprovalDisplay;
    }
}
