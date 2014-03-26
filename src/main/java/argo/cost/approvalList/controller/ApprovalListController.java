package argo.cost.approvalList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.approvalList.model.ApprovalListForm;
import argo.cost.approvalList.model.ApprovalListInfo;
import argo.cost.approvalList.service.ApprovalListService;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.ListItem;

@Controller
@RequestMapping("/approvalList")
@SessionAttributes(types = { ApprovalListForm.class })
public class ApprovalListController extends AbstractController  {
	
	/**
	 * このアクションが利用するサービスです。
	 */
	@Autowired
	protected ApprovalListService service;

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
    	ApprovalListForm form = new ApprovalListForm();
    	model.addAttribute(form);
    	
    	// 状況リストを取得
    	List<ListItem> statusList = comService.getStatusList();
    	
    	// 状況リストを設定
    	form.setStatusList(statusList);
    	
    	// 状況の初期値設定
    	form.setStatus("");
    	
    	// 承認リストを取得
    	List<ApprovalListInfo> approvalList = service.getApprovalList(form.getStatus());
    	
    	form.setApprovalList(approvalList);
    	
        return "approvalList";
    }

    /**
     * 表示切替ボタンを押して、表示対象を切り替える
     * 
     * @param form
     *         画面情報
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(ApprovalListForm form) {
    	
    	// 承認リストを取得
    	List<ApprovalListInfo> approvalList = service.getApprovalList(form.getStatus());
    	
    	form.setApprovalList(approvalList);

        return "approvalList";
    }
}
