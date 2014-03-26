package argo.cost.setup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.controller.AbstractController;
import argo.cost.setup.model.SetupForm;
import argo.cost.setup.service.SetupService;

@Controller
@RequestMapping("/setup")
@SessionAttributes(types = { SetupForm.class })
public class SetupController extends AbstractController {
	
	/**
	 * このアクションが利用するサービスです。
	 */
	@Autowired
	private SetupService service;
	
	/**
	 * 個人設定初期化
	 *
	 * @param model
	 *            モデル
	 * @param loginId
	 *            ユーザID
	 * @return
	 */
    @RequestMapping("/init")
    public String init(Model model) {
    	
    	String loginId = getSession().getUserInfo().getId();
    	
    	// 画面情報を作成
    	SetupForm form = service.getSetupInfo(loginId);
    	model.addAttribute(form);
    	
        return "setup";
    }
    
    /**
     * 編集ボタンを押下
     * 
     * @param setupInfo
	 *            個人設定情報
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String doEdit(SetupForm setupInfo) {

        return "redirect:/setup/initSetupEdit";
    }
	
	/**
	 * 個人設定変更初期化
	 *
	 * @param model
	 *            モデル
	 * @param setupInfo
	 *            個人設定情報
	 * @return
	 */    
    @RequestMapping("/initSetupEdit")
    public String initSetupEdit(Model model, SetupForm setupInfo) {

    	// 画面情報を作成
    	service.getSetupEditInfo(setupInfo);

        return "setupEdit";
    }
	
	/**
	 * 標準ｼﾌﾄ変更の場合
	 *
	 * @param setupInfo
	 *            個人設定情報
	 * @return
	 */    
    @RequestMapping(value = "/shiftChange", method = RequestMethod.POST)
    public String doShiftChange(SetupForm setupInfo) {
    	
    	// 画面情報を作成
    	service.changeShift(setupInfo);

        return "setupEdit";
    }
	
	/**
	 * 個人設定変更「保存」ボッタン押下
	 *
	 * @param setupInfo
	 *            個人設定情報
	 * @return
	 */    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String doSave(SetupForm setupInfo) {
    	
    	// チェックを実行
    	Boolean chkFlg = service.doSaveCheck(setupInfo);
    	
    	if (chkFlg) {

    		// 入力チェックＯｋの場合は入力された内容が保存されて、個人設定画面に遷移する
    		service.doSave(setupInfo);
            return "setup";
    	} else {

    		// エラーが発生した場合はエラーメッセージが表示され個人設定変更画面に戻る
            return "setupEdit";
    	}

    }
	
	/**
	 * 個人設定変更「戻る」ボッタン押下
	 *
	 * @return
	 */    
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String doCancel() {

        return "setup";
    }
}
