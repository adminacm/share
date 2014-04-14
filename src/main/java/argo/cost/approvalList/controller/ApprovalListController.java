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
 * <p>
 * 承認一覧画面業務クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_APPROVALLIST)
@SessionAttributes(types = { ApprovalListForm.class })
public class ApprovalListController extends AbstractController  {
	
	/**
	 * このアクションが利用するサービスです。
	 */
	@Autowired
	protected ApprovalListService service;

	/**
	 * 承認一覧画面URL
	 */
	private static final String APPROVALLIST = "approvalList";

	/**
	 * 初期化
	 *
	 * @param map
	 *            マップ
	 * @return
	 */
    @RequestMapping(INIT)
    public String init(Model model) {
    	
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
    	List<ApprovalListVo> approvalList = service.getApprovalList(form.getStatus());
    	
    	form.setApprovalList(approvalList);
    	
        return APPROVALLIST;
    }

    /**
     * 表示切替ボタンを押して、表示対象を切り替える
     * 
     * @param form
     *         画面情報
     * @return
     */
    @RequestMapping(value = SEARCH, method = RequestMethod.POST)
    public String search(ApprovalListForm form) {
    	
    	// 承認リストを取得
    	List<ApprovalListVo> approvalList = service.getApprovalList(form.getStatus());
    	
    	form.setApprovalList(approvalList);

        return APPROVALLIST;
    }

    /**
     * リンクをクリック
     * 
     * @param form
     *         画面情報
     * @return
     */
    @RequestMapping(value = APPLYNO_CLICK)
    public String approvalNoClick(ApprovalListForm form, @RequestParam("applyNo") String applyNo,@RequestParam("applyKbnCd") String applyKbnCd) {
    	
    	String str = "";
    	// TODO 申請区分が月報の場合（区分コードが未定です）
    	if ("02".equals(applyKbnCd)) {

        	// 月報承認詳細画面
    		str = REDIRECT + UrlConstant.URL_MONTHLYREPORT_APPROVAL + INIT + QUESTION_MARK + "applyNo=" + applyNo;
    	} else if ("超勤振替申請".equals(applyKbnCd)) {

        	// 超勤振替申請承認詳細画面
    		str = "";
    	}
    	
    	System.out.println("aaaaaaaaaaaaaaaaaa");
    	// 画面へ遷移
    	return str;
    }
}
